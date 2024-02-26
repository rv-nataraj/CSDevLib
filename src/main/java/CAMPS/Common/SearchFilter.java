package CAMPS.Common;

import CAMPS.Connect.DBConnect;
import java.sql.SQLException;


public class SearchFilter {
	
	public String studentSearchFilter(String student_id,String reg_no, String rollno, String student_name) {
		String data = " ", query = ""; String debuginfo=""; int i=1;  String qrycond=" where true  ";
		
		if (!student_name.equalsIgnoreCase("")) qrycond += " and sm.first_name like '%"+student_name+"%' ";
		if (!student_id.equalsIgnoreCase(""))   qrycond += " and sm.student_id='"+student_id+"' ";
		if (!rollno.equalsIgnoreCase("")) qrycond += " and sp.roll_no like '%"+rollno+"%' ";
		if (!reg_no.equalsIgnoreCase("")) qrycond += " and sm.emi_no like '%"+reg_no+"%' ";
		
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			query="SELECT sm.student_id,sm.emi_no reg_no,sm.student_mobile_no mobile_no,CONCAT('<a href=\"#\" data-dismiss=\"modal\" onclick=\"loadStudentData(',sm.student_id,')\">',TRIM(CONCAT(IFNULL(sm.first_name,''),' ',IFNULL(sm.middle_name,''),' ',IFNULL(sm.last_name,''),' ')),'</a>') stu_name,DATE_FORMAT(sm.dob,'%d-%m-%Y') dob,IFNULL(mr.name,'')fname,IFNULL(sm.per_pincode,'') per_pincode,IFNULL(sp.roll_no,'') roll_no FROM camps.student_master sm INNER JOIN camps.student_admission_master sam ON sm.student_id=sam.student_id left join student_promotion sp on sp.sp_id=sam.sp_id  LEFT JOIN master_relation mr ON mr.student_id=sm.student_id AND mr.relationship='father' AND mr.status>0 "+qrycond+" GROUP BY student_id LIMIT 500 ";
			db.read(query);
			data += "<div class=\"table-responsive text-nowrap\"><table class='table table-bordered table-striped' width='100%'><thead><tr>";
			data += "<th>S.No</th><th>Student ID</th><th>Roll No</th><th>Reg No</th><th>Student Name</th></tr></thead>";
			while (db.rs.next()) {
                data += "<tr><td>"+ i++ +"</td>";
                data += "<td title=\""+"Student ID"+"\"   data-column=\"" + "Student ID" + "\">" +   db.rs.getString("student_id") + "</td>";
                data += "<td title=\""+"Roll No"+"\"      data-column=\"" + "Roll No" + "\">" +      db.rs.getString("roll_no") + "</td>";
                data += "<td title=\""+"Reg No"+"\"       data-column=\"" + "Reg No" + "\">" +       db.rs.getString("reg_no") + "</td>";
                data += "<td title=\""+"Student Name"+"\" data-column=\"" + "Student Name" + "\">" + db.rs.getString("stu_name") + "</td>";
                data += "</tr>";
            }
            data += "</table>";
            if (i==1) data="No Students Found";

			return data;
		} catch (Exception e) {
			return "<b style=\"color:red\" title=\""+e.toString()+"\">Something Went Wrong1</b>"+"<!--"+query+"-->";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {

			}
		}
	}
	
	
}
