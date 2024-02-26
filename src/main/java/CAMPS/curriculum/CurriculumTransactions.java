package CAMPS.curriculum;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import CAMPS.Connect.DBConnect;

import jakarta.servlet.http.HttpSession;

public class CurriculumTransactions {
	
	public String loadRegulationCourses(String regulation_id, String mode) {
		String data = " ", query = ""; String debuginfo=""; int i=1;  
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			
			String elective_header="1"; String prev="";
            db.read1("SELECT st.st_id,st.type_name FROM curriculum.subject_type st WHERE st.status=1"); 
            String opt = "<option value=\"\">--Course Type--</option>";
            while (db.rs1.next()) {
                opt += "<option value='" + db.rs1.getString("st_id") + "'>" + db.rs1.getString("type_name") + "</option>";
            }
            
            db.read1("SELECT mppd.`prog_period_id`,mppd.`period`,mppd.`period_name` FROM curriculum.`regulation_master` rm INNER JOIN camps.`master_branch` mb ON mb.`branch_id`=rm.`branch_id` INNER JOIN camps.`master_programme` mp ON mp.`programme_id`=mb.`programme_id` INNER JOIN camps.`master_programme_period_det` mppd ON mppd.`prog_pattern_id`=mb.`prog_pattern_id` WHERE rm.`regulation_id`='"+regulation_id+"'"); 
            String opt2 = "<option value=\"\">Sem</option>";
            while (db.rs1.next()) {
                opt2 += "<option value='" + db.rs1.getString("prog_period_id") + "'>" + db.rs1.getString("period") + "</option>";
            }
            opt2 += "<option value='" + "Elective" + "'>" + "Elective" + "</option>";
            
            //Course Entry 
             data = "<form><table class='tbl_new' width='100%'>";
             data+= "<tr ><th>Display code</th><th>Course Code</th><th>Title of the Course</th><th title=\"Lecture\">L</th><th title=\"Tutorial\">T</th><th title=\"Practical\">P</th><th title=\"Credit\">C</th><th title=\"Minimum Internal Mark\">MnIM</th><th title=\"Maximum Internal Mark\">MxIM</th><th title=\"Minimum External Mark\">MnEM</th><th title=\"Maximum External Mark\">MxEM</th><th title=\"Order of Display\">Order</th><th>Sem</th><th>Course Type</th>";
             data += (mode.equalsIgnoreCase("edit")?"<th>Action</th><th>Freeze</th></tr>":"</tr>");
             if (mode.equalsIgnoreCase("edit")) {
	             data+= "<tr style=\"background:green;\" id='tr_sub'>";
	             data+= "<td data-column=\"Display Code\">		<input  style='background-color:#c7e2c4; 		width:90px;text-align:center' 	type=\"text\" 	name=\"dc_sub\" 	id=\"dc_sub\"  value=''  maxlength=\"12\"	/></td>";
	             data+= "<td data-column=\"Course Code\">		<input  style='background-color:#c7e2c4; 		width:90px;text-align:center' 	type=\"text\"   name=\"sc_sub\" 	id=\"sc_sub\"  value=''  maxlength=\"12\"	/></td>";
	             data+= "<td data-column=\"Course Title\">		<input  style='background-color:#c7e2c4; 		width:250px;text-align:center'  type=\"text\"  	name=\"sn_sub\" 	id=\"sn_sub\"  value=''  maxlength=\"120\"	/></td>";
				 data+= "<td data-column=\"Lecture\">			<input  style='background-color:#c7e2c4; 		width:30px;text-align:center'	type=\"number\"  min=\"0\" max=\"50\"	name=\"l_sub\" 		id=\"l_sub\"   value='' 	/></td>";
				 data+= "<td data-column=\"Tutorial\">			<input  style='background-color:#c7e2c4; 		width:30px;text-align:center'  	type=\"number\"  min=\"0\" max=\"50\"	name=\"t_sub\" 		id=\"t_sub\"   value='' 	/></td>";
				 data+= "<td data-column=\"Practical\">			<input  style='background-color:#c7e2c4; 		width:30px;text-align:center' 	type=\"number\"  min=\"0\" max=\"50\"	name=\"p_sub\" 		id=\"p_sub\"   value='' 	/></td>";
				 data+= "<td data-column=\"Credit\">			<input  style='background-color:#c7e2c4; 		width:30px;text-align:center' 	type=\"number\"  min=\"0\" max=\"50\"	name=\"c_sub\" 		id=\"c_sub\"   value='' 	/></td>";
				 data+= "<td data-column=\"Minimum IM\">		<input  style='background-color:#c7e2c4; 		width:30px;text-align:center' 	type=\"number\"  min=\"0\" max=\"500\"	name=\"iim_sub\" 	id=\"iim_sub\"  value='' 	/></td>";
				 data+= "<td data-column=\"Maximum IM\">		<input  style='background-color:#c7e2c4; 		width:30px;text-align:center' 	type=\"number\"  min=\"0\" max=\"500\"	name=\"mim_sub\" 	id=\"mim_sub\"  value='' 	/></td>";
	             data+= "<td data-column=\"Minimum EM\">		<input  style='background-color:#c7e2c4; 		width:30px;text-align:center' 	type=\"number\"  min=\"0\" max=\"500\"	name=\"lem_sub\" 	id=\"lem_sub\"  value='' 	/></td>";
		 		 data+= "<td data-column=\"Maximum EM\">		<input  style='background-color:#c7e2c4; 		width:30px;text-align:center' 	type=\"number\"  min=\"0\" max=\"500\"	name=\"mem_sub\" 	id=\"mem_sub\"  value='' 	/></td>";
		 		 data+= "<td data-column=\"Order\"><input  style='background-color:#c7e2c4;				width:30px;text-align:center'   type=\"number\"  min=\"0\" max=\"5000\"   name=\"o_sub\"       id=\"o_sub\"    value='' /></td>";
		 		 data+= "<td data-column=\"Semester\"> 	<select style=\"width:35px\"  	name=\"sem_sub\" 	id=\"sem_sub\" >" + opt2 + 	"</select></td>";
		 		 data+= "<td data-column=\"Type\"> 		<select style=\"width:200px\"  	name=\"st_sub\" 	id=\"st_sub\" >" + opt + 	"</select></td>";
		 		 data+= "<td data-column=\"Add\" colspan='2' style=\"text-align:left;\"><button class=\"btn bg-orange waves-effect\" type=\"button\" value=\"add\" onclick=\"insertCourse('sub')\" name=\"curriculum_report\">Add Subject</button></td>";
		 		 data+= "</tr>";
	            //Elective Entry
	            data += "<tr style=\"background:green;\" id='tr_ele'><td></td><td></td>";
	            data+= "<td data-column=\"Elective Name\"><input type=\"text\" name=\"sn_ele\" id=\"sn_ele\" style='background-color:#c7e2c4; 	width:250px;text-align:left'  value='' maxlength=\"120\" /></td><td></td><td></td><td></td>";
	            data+= "<td data-column=\"Credit\"><input type=\"number\" min=\"0\" max=\"50\" name=\"c_ele\" id=\"c_ele\" style='background-color:#c7e2c4; 		width:30px;text-align:center' value='' /></td><td></td><td></td><td></td><td></td>";
	            data+= "<td data-column=\"Order\"><input  type=\"number\" min=\"0\" max=\"5000\" name=\"o_ele\" id=\"o_ele\" style='background-color:#c7e2c4; 		width:30px;text-align:center'  value='' /></td>";
	            
	            data+= "<td data-column=\"Semester\"> <select style=\"width:35px\"  name=\"sem_ele\" id=\"sem_ele\" >" + opt2 + "</select></td>";
	            data+= "<td data-column=\"Type\"> <select style=\"width:200px\"  name=\"st_ele\" id=\"st_ele\" >" + opt + "</select></td>";
	            data+= "<td colspan='2' style=\"text-align:left;\"><button class=\"btn bg-orange  waves-effect\" onclick=\"insertElectiveSlot()\" type=\"button\" value=\"add\" name=\"curriculum_report\">Add Elective</button></td>";
	            data+= "</tr>";
             }
             db.read("SELECT * FROM ( (SELECT  rsm.subject_id, rsm.regulation_id, sm.dis_code,IFNULL(sm.sub_code,'') sub_code, sm.sub_name, IFNULL(mspd.period, '-') period,mspd.prog_period_id,sm.lecture,sm.tutorial,sm.practical,ifnull(sm.credit,0)credit, rsm.order_no, st.type_name,st.st_id,sm.min_int_mark,sm.max_int_mark,sm.min_ext_mark,sm.max_ext_mark,sm.status FROM curriculum.regulation_master rm INNER JOIN curriculum.regulation_subject_mapping rsm ON rm.regulation_id = rsm.regulation_id AND  rm.regulation_id ='" + regulation_id + "' AND rsm.status > 0 INNER JOIN curriculum.subject_master sm ON rsm.subject_id = sm.subject_id INNER JOIN curriculum.subject_type st ON st.st_id = rsm.st_id LEFT JOIN camps.master_programme_period_det mspd ON mspd.prog_period_id = rsm.prog_period_id ORDER BY IF(period = '-', 'viiii', period), dis_code, order_no) UNION (SELECT CONCAT(em.elective_id) subject_id, '' regulation_id,'Elective', '' sub_code, em.elective_name sub_name, mspd.period period,mspd.prog_period_id,'','','',ifnull(em.credit,0)credit, em.order_no, st.type_name,st.st_id,'','','','',em.status FROM curriculum.regulation_master rm INNER JOIN curriculum.elective_master em ON em.regulation_id = rm.regulation_id AND rm.regulation_id ='" + regulation_id + "' and em.status>0  INNER JOIN curriculum.subject_type st ON st.st_id = em.st_id LEFT JOIN camps.master_programme_period_det mspd ON mspd.prog_period_id = em.prog_period_id ORDER BY IF(period = '-', 'viiii', period), order_no) ) a  ORDER BY IF(period='-','viiii',period),order_no,sub_code");
                
            while (db.rs.next()) {
            	if ( db.rs.getString("prog_period_id") !=null && !db.rs.getString("prog_period_id").equalsIgnoreCase(prev)) {
            		prev=db.rs.getString("prog_period_id");
            		data += "<tr style=\"background-color:#2c4b8a;color:white\"><td colspan=\"16\" style=\"text-align:center\">Semester "+db.rs.getString("period")+"</td></tr>";
            	}
                if (!db.rs.getString("dis_code").equalsIgnoreCase("Elective")) {
                	if (elective_header.equalsIgnoreCase("1") && (db.rs.getString("prog_period_id")==null)) {
                		elective_header="0";
                		data += "<tr style=\"background-color:#00bcd4\"><td colspan=\"15\" style=\"text-align:center\">Elective Courses</td></tr>";
                	}
                	
                    data += "<tr id='tr_" + db.rs.getString("subject_id") + "'>";  
                    if (db.rs.getString("status").equalsIgnoreCase("2") || mode.equalsIgnoreCase("view")) {
                    	data += "<td data-column=\"Display Code\" 		style=\"text-align:center\">"	+db.rs.getString("dis_code")+	"</td>";
                    	data += "<td data-column=\"Course Code\" 		style=\"text-align:center\">"	+db.rs.getString("sub_code")+	"</td>";
                    	data += "<td data-column=\"Course Title\" 		style=\"text-align:left\">"	 +"<input type=\"text\" readonly style=\"width:100%;border:none;background:transparent\"  value=\""+db.rs.getString("sub_name")+"\"	></td>";
                    	data += "<td data-column=\"Lecture\" 			style=\"text-align:center\">"	+db.rs.getString("lecture")+	"</td>";
                    	data += "<td data-column=\"Tutorial\" 			style=\"text-align:center\">"	+db.rs.getString("tutorial")+	"</td>";
                    	data += "<td data-column=\"Practical\" 			style=\"text-align:center\">"	+db.rs.getString("practical")+	"</td>";
                    	data += "<td data-column=\"Credit\" 			style=\"text-align:center\">"	+db.rs.getString("credit")+		"</td>";
                    	data += "<td data-column=\"Minimum IM\" 		style=\"text-align:center\">"	+db.rs.getString("min_int_mark")+"</td>";
                    	data += "<td data-column=\"Maximum IM\" 		style=\"text-align:center\">"	+db.rs.getString("max_int_mark")+"</td>";
                    	data += "<td data-column=\"Minimum EM\" 		style=\"text-align:center\">"	+db.rs.getString("min_ext_mark")+"</td>";
                    	data += "<td data-column=\"Maximum EM\" 		style=\"text-align:center\">"	+db.rs.getString("max_ext_mark")+"</td>";
                    	data += "<td data-column=\"Order No\" 			style=\"text-align:center\">"	+db.rs.getString("order_no")+"</td>";
                    	if (db.rs.getString("prog_period_id")!=null)   	data += "<td data-column=\"Semester\" style=\"text-align:center\">"+db.rs.getString("period")+"</td>";
                    	else data+="<td data-column=\"Type\" style=\"background-color:#f2f0ae;text-align:center\">Elective</td>";
                    	data += "<td style=\"text-align:left\"><input type=\"text\" value=\""+db.rs.getString("type_name")+"\" readonly style=\"width:100%;border:none;background:transparent\"  ></td>";
                    }
                    else {
                    	data += "<td data-column=\"Display Code\" 	style=\"text-align:center\"><input  type=\"text\" maxlength=\"12\" 			style='width:90px;text-align:center' 							onchange=\"updateCourseAttributes("+db.rs.getString("subject_id")+",1)\" 	name=\"dc_" + db.rs.getString("subject_id") + "\" 	id=\"dc_" + db.rs.getString("subject_id") + "\" 	value='" + db.rs.getString("dis_code") + "' required  /></td>";
                        data += "<td data-column=\"Course Code\" 	style=\"text-align:center\"><input  type=\"text\" maxlength=\"12\"			style='width:90px;text-align:center' 							onchange=\"updateCourseAttributes("+db.rs.getString("subject_id")+",2)\" 	name=\"sc_" + db.rs.getString("subject_id") + "\" 	id=\"sc_" + db.rs.getString("subject_id") + "\"  	value='" + db.rs.getString("sub_code") + "' /></td>";
                        data += "<td data-column=\"Course Title\">								<input  type=\"text\" maxlength=\"120\"			style='width:250px;text-align:left'  							onchange=\"updateCourseAttributes("+db.rs.getString("subject_id")+",3)\" 	name=\"sn_" + db.rs.getString("subject_id") + "\" 	id=\"sn_" + db.rs.getString("subject_id") + "\"  	value='" + db.rs.getString("sub_name") + "' /></td>";
                        data += "<td data-column=\"Lecture\" style=\"text-align:center\">		<input  type=\"number\" min=\"0\" max=\"50\" 	style='width:30px;text-align:center' 	onchange=\"updateCourseAttributes("+db.rs.getString("subject_id")+",4)\" 	name=\"l_" + db.rs.getString("subject_id") + "\" 	id=\"l_" + db.rs.getString("subject_id") + "\"  	value='" + db.rs.getString("lecture") + "' /></td>";
                        data += "<td data-column=\"Tutorial\" style=\"text-align:center\">		<input  type=\"number\" min=\"0\" max=\"50\" 	style='width:30px;text-align:center' 	onchange=\"updateCourseAttributes("+db.rs.getString("subject_id")+",5)\" 	name=\"t_" + db.rs.getString("subject_id") + "\" 	id=\"t_" + db.rs.getString("subject_id") + "\"  	value='" + db.rs.getString("tutorial") + "' /></td>";
                        data += "<td data-column=\"Practical\" style=\"text-align:center\">		<input  type=\"number\" min=\"0\" max=\"50\" 	style='width:30px;text-align:center' 	onchange=\"updateCourseAttributes("+db.rs.getString("subject_id")+",6)\" 	name=\"p_" + db.rs.getString("subject_id") + "\" 	id=\"p_" + db.rs.getString("subject_id") + "\"  	value='" + db.rs.getString("practical") + "' /></td>";
                        data += "<td data-column=\"Credit\" style=\"text-align:center\">		<input  type=\"number\" min=\"0\" max=\"50\" 	style='width:30px;text-align:center' 	onchange=\"updateCourseAttributes("+db.rs.getString("subject_id")+",7)\" 	name=\"c_" + db.rs.getString("subject_id") + "\" 	id=\"c_" + db.rs.getString("subject_id") + "\"  	value='" + db.rs.getString("credit") + "' /></td>";
                        data += "<td data-column=\"Minimum IM\" style=\"text-align:center\">	<input  type=\"number\" min=\"0\" max=\"500\" 	style='width:30px;text-align:center' 	onchange=\"updateCourseAttributes("+db.rs.getString("subject_id")+",8)\" 	name=\"iim_" + db.rs.getString("subject_id") + "\" 	id=\"iim_" + db.rs.getString("subject_id") + "\"  	value='" + db.rs.getString("min_int_mark") + "' /></td>";
                        data += "<td data-column=\"Maximum IM\" style=\"text-align:center\">	<input  type=\"number\" min=\"0\" max=\"500\" 	style='width:30px;text-align:center' 	onchange=\"updateCourseAttributes("+db.rs.getString("subject_id")+",9)\" 	name=\"mim_" + db.rs.getString("subject_id") + "\" 	id=\"mim_" + db.rs.getString("subject_id") + "\"  	value='" + db.rs.getString("max_int_mark") + "' /></td>";
                        data += "<td data-column=\"Minimum EM\" style=\"text-align:center\">	<input  type=\"number\" min=\"0\" max=\"500\" 	style='width:30px;text-align:center' 	onchange=\"updateCourseAttributes("+db.rs.getString("subject_id")+",10)\" 	name=\"lem_" + db.rs.getString("subject_id") + "\" 	id=\"lem_" + db.rs.getString("subject_id") + "\"  	value='" + db.rs.getString("min_ext_mark") + "' /></td>";
                        data += "<td data-column=\"Maximum EM\" style=\"text-align:center\">	<input  type=\"number\" min=\"0\" max=\"500\" 	style='width:30px;text-align:center' 	onchange=\"updateCourseAttributes("+db.rs.getString("subject_id")+",11)\" 	name=\"mem_" + db.rs.getString("subject_id") + "\" 	id=\"mem_" + db.rs.getString("subject_id") + "\"  	value='" + db.rs.getString("max_ext_mark") + "' /></td>";
                        data += "<td data-column=\"Order\" style=\"text-align:center\"><input  type=\"number\" min=\"0\" max=\"5000\" 			style='width:30px;text-align:center' 			onchange=\"updateCourseAttributes("+db.rs.getString("subject_id")+",12)\" 	name=\"o_" + db.rs.getString("subject_id") + "\" 	id=\"o_" + db.rs.getString("subject_id") + "\"  	value='" + db.rs.getString("order_no") + "' /></td>";
                        if (db.rs.getString("prog_period_id")!=null) {
                        	data+= "<td data-column=\"Semester\" style=\"text-align:center\"> <select style=\"width:30px;text-indent: 50%\"   name=\"sem_sub_" + db.rs.getString("subject_id") + "\" id=\"sem_sub_" + db.rs.getString("subject_id") + "\" onchange=\"updateCourseAttributes("+db.rs.getString("subject_id")+",13)\" >" + opt2 + "</select>  <script> $('#sem_sub_" + db.rs.getString("subject_id") + "').val(" + db.rs.getString("prog_period_id") + ");</script> </td>";
                        }
                        else
                        	data+="<td style=\"background-color:#f2f0ae;text-align:center\">Elective</td>";
       		 		 
                        data += "<td data-column=\"Type\"> <select  style=\"width:200px\" name=\"st_" + db.rs.getString("subject_id") + "\" id=\"st_" + db.rs.getString("subject_id") + "\" onchange=\"updateCourseAttributes("+db.rs.getString("subject_id")+",14)\" >" + opt + "</select><script> $('#st_" + db.rs.getString("subject_id") + "').val(" + db.rs.getString("st_id") + ");</script></td>";
                        //data += "<td><button class=\"btn btn-success waves-effect\" type=\"button\" value=\"update\" name=\"update_" + db.rs.getString("subject_id") + "\" onclick=\"edit_grid(" +   db.rs.getString("subject_id") + ")\" >Update</button></td>";
                        data += "<td data-column=\"Delete\"><button class=\"btn btn-danger waves-effect\"  type=\"button\" value=\"delete\" name=\"de_" +     db.rs.getString("subject_id") + "\" onclick=\"deleteCourse(" + db.rs.getString("subject_id") + ")\" >X</button></td>";
                        data += "<td data-column=\"Freeze\"><button class=\"btn btn-success waves-effect\"  type=\"button\" value=\"delete\" name=\"fz_" +     db.rs.getString("subject_id") + "\" onclick=\"freezeCourse(" + db.rs.getString("subject_id") + ")\" >Fz</button></td>";
                    }
                    data += "</tr>";
                } else {  
                	data += "<tr style=\"background-color:#f2f0ae\" id='tr_e" + db.rs.getString("subject_id") + "'><td></td><td></td>";
                	if (db.rs.getString("status").equalsIgnoreCase("2") || mode.equalsIgnoreCase("view")) {
                		data += "<td data-column=\"Elective Name\" style=\"text-align:left\"> <input type=\"text\" value=\""+db.rs.getString("sub_name")+"\" readonly style=\"width:100%;border:none;background:transparent\"></td>";
                		data += "<td></td><td></td><td></td>";
                		data += "<td data-column=\"Credit\" style=\"text-align:center\">"+db.rs.getString("credit")+"</td>";
                		data += "<td></td><td></td><td></td><td></td>";
                		data += "<td data-column=\"Order No\" style=\"text-align:center\">"+db.rs.getString("order_no")+"</td>";
                		data += "<td data-column=\"Semester\" style=\"text-align:center\">"+db.rs.getString("period")+"</td>";
                    	data += "<td data-column=\"Type\" style=\"text-align:left\"> <input type=\"text\" value=\""+db.rs.getString("type_name")+"\" readonly style=\"width:100%;border:none;background:transparent\"></td>";
                	}
                	else {                        
                        data += "<td data-column=\"Elective Name\"><input type=\"text\" maxlength=\"100\" style='background-color:yellow;width:250px;' name=\"sne_" + db.rs.getString("subject_id") + "\" id=\"sne_" + db.rs.getString("subject_id") + "\" required value='" + db.rs.getString("sub_name") + "'  /></td>";
                        data += "<td></td><td></td><td></td>";
                        data += "<td style=\"text-align:center\"> <input type=\"number\" onchange=\"updateElectiveAttributes("+db.rs.getString("subject_id")+",1)\" min=\"0\" max=\"50\"   name=\"ce_" + db.rs.getString("subject_id") + "\" id=\"ce_" + db.rs.getString("subject_id") + "\" style='width:30px;text-align:center' value='" + db.rs.getString("credit") + "' /></td>";
                        data += "<td></td><td></td><td></td><td></td>";
                        data += "<td data-column=\"Credit\" style=\"text-align:center\"> 	<input type=\"number\" onchange=\"updateElectiveAttributes("+db.rs.getString("subject_id")+",2)\" min=\"0\" max=\"5000\" name=\"oe_" + db.rs.getString("subject_id") + "\" id=\"oe_" + db.rs.getString("subject_id") + "\" style='width:30px;text-align:center'  value='" + db.rs.getString("order_no") + "' /></td>";
                        data += "<td data-column=\"Order No\" style=\"text-align:center\"> 	<select style=\"width:35px;text-indent: 50%\"  onchange=\"updateElectiveAttributes("+db.rs.getString("subject_id")+",3)\"  name=\"sem_sub_e" + db.rs.getString("subject_id") + "\" id=\"sem_sub_e" + db.rs.getString("subject_id") + "\" >" + opt2 + "</select>  <script> $('#sem_sub_e" + db.rs.getString("subject_id") + "').val(" + db.rs.getString("prog_period_id") + ");</script> </td>";
                        data += "<td data-column=\"Type\"> 									<select style=\"width:200px\" onchange=\"updateElectiveAttributes("+db.rs.getString("subject_id")+",4)\"  name=\"ste_" + db.rs.getString("subject_id") + "\"      id=\"ste_" + db.rs.getString("subject_id") + "\" >" + opt + "</select>        <script> $('#ste_" + db.rs.getString("subject_id") + "').val(" + db.rs.getString("st_id") + ");</script></td>";
                        data += "<td data-column=\"Delete\"><button class=\"btn btn-danger waves-effect\" type=\"button\" value=\"delete\" onclick=\"deleteElectiveSlot(" + db.rs.getString("subject_id") + ")\" name=\"curriculum_report\">X</button></td>";
                        data += "<td data-column=\"Freeze\"><button class=\"btn btn-success waves-effect\"  type=\"button\" value=\"delete\" name=\"fz_" +     db.rs.getString("subject_id") + "\" onclick=\"freezeElectiveSlot(" + db.rs.getString("subject_id") + ")\" >Fz</button></td>";
                	}
                    data += "</tr>";
                }
            }   
			return data;
			
		} catch (Exception e) {
			return "<b style=\"color:red\" title=\""+e.toString()+"\">Something Went Wrong</b>";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {

			}
		}
	}
	
	public String updateCourseAttributes(String regulation_id,String subject_id,String field_id, String field_value,String ss_id) {
		String data = " ", query = ""; String debuginfo=""; int i=1;  String ay_id="";String field="";
		DBConnect db = new DBConnect();
		try {
			db.getConnection();		
        	if (field_id.equalsIgnoreCase("1")) field="dis_code";
        	else if (field_id.equalsIgnoreCase("2")) field="sub_code";
        	else if (field_id.equalsIgnoreCase("3")) field="sub_name";  
        	else if (field_id.equalsIgnoreCase("4")) field="lecture";
        	else if (field_id.equalsIgnoreCase("5")) field="tutorial"; 
        	else if (field_id.equalsIgnoreCase("6")) field="practical";
        	else if (field_id.equalsIgnoreCase("7")) field="credit"; 
        	else if (field_id.equalsIgnoreCase("8")) field="min_int_mark";
        	else if (field_id.equalsIgnoreCase("9")) field="max_int_mark"; 
        	else if (field_id.equalsIgnoreCase("10")) field="min_ext_mark";
        	else if (field_id.equalsIgnoreCase("11")) field="max_ext_mark"; 
        	else if (field_id.equalsIgnoreCase("12")) field="order_no"; 
        	else if (field_id.equalsIgnoreCase("13")) field="prog_period_id";
        	else if (field_id.equalsIgnoreCase("14")) field="st_id";
        	query="UPDATE curriculum.`subject_master` sm SET `"+field+"`='"+field_value+"',sm.updated_date=now(),sm.updated_by='"+ss_id+"' WHERE sm.`subject_id`='"+subject_id+"' and sm.status=1";
        	
        	if (field_id.equalsIgnoreCase("12") || field_id.equalsIgnoreCase("13") || field_id.equalsIgnoreCase("14")) {
        		query="UPDATE curriculum.`regulation_subject_mapping` rsm SET rsm."+field+"='"+field_value+"',rsm.updated_date=now(),rsm.updated_by='"+ss_id+"' WHERE rsm.`subject_id`='"+subject_id+"' AND rsm.`regulation_id`='"+regulation_id+"'";
        	}  	
        	db.update(query);
			return "0";
			
		} catch (Exception e) {
			return "<b style=\"color:red\" title=\""+e.toString()+"\">Something Went Wrong</b>";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {

			}
		}
	}
	
	public String updateElectiveAttributes(String regulation_id,String subject_id,String field_id, String field_value,String ss_id) {
		String data = " ", query = ""; String debuginfo=""; int i=1;  String ay_id="";String field="";
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
        	if (field_id.equalsIgnoreCase("1")) field="credit";
        	else if (field_id.equalsIgnoreCase("2")) field="order_no";
        	else if (field_id.equalsIgnoreCase("3")) field="prog_period_id";  
        	else if (field_id.equalsIgnoreCase("4")) field="st_id";
        	else if (field_id.equalsIgnoreCase("5")) field="elective_name";
        	query="UPDATE curriculum.`elective_master` em SET `"+field+"`='"+field_value+"',em.updated_date=NOW(),em.updated_by='"+ss_id+"'  WHERE em.`elective_id`='"+subject_id+"' and em.status=1";
        	db.update(query);
			return "0";
		} catch (Exception e) {
			return "<b style=\"color:red\" title=\""+e.toString()+"\">Something Went Wrong</b>";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {

			}
		}
	}
	
	public String insertCourse(String regulation_id,String display_code, String course_code, String title, String lecture, String tutorial, String practical, String credit, String min_im, String max_im, String min_em, String max_em,String semester, String type, String order_no, String ss_id) {
		String data = " ", query1 = "",query2=""; String debuginfo=""; int i=1;  String ay_id="";String field="";
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
        	
			query1="INSERT INTO curriculum.subject_master (subject_id,   dis_code,   sub_code,   sub_name,   lecture,   tutorial,   practical,   credit,   min_int_mark,   max_int_mark,   min_ext_mark,   max_ext_mark,   min_pass,   STATUS,   inserted_by,   inserted_date,   updated_by,   updated_date ) VALUES   (     null,     '"+display_code+"','"+course_code+"','"+title+"','"+lecture+"','"+tutorial+"','"+practical+"','"+credit+"','"+min_im+"','"+max_im+"','"+min_em+"','"+max_em+"','40','1','" + ss_id + "',     now(),     '" + ss_id + "',     now()  )ON DUPLICATE KEY UPDATE STATUS=1,updated_by=VALUES(updated_by),updated_date=NOW()";
            String sm_id = db.insertAndGetAutoGenId(query1);
            query2="INSERT INTO curriculum.regulation_subject_mapping (rsm_id, regulation_id,   subject_id,   st_id,   prog_period_id,   order_no,   STATUS,   inserted_by,   inserted_date,   updated_by,   updated_date ) SELECT NULL,'"+regulation_id+"','" + sm_id + "','" + type + "',"+ (semester.equalsIgnoreCase("Elective")?"NULL":semester) +",'" + order_no + "',1,'" + ss_id + "',NOW(),'" + ss_id + "',NOW() ON DUPLICATE KEY UPDATE STATUS=1,prog_period_id=values(prog_period_id),updated_by=VALUES(updated_by),updated_date=NOW()";
            db.insert(query2);
			return "0";
		} catch (Exception e) {
			return "<b style=\"color:red\" title=\""+e.toString()+"\">Something Went Wrong</b>"+"<!-- "+query1+query2+"-->";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {

			}
		}
	}
	
	public String deleteCourse(String regulation_id,String subject_id,String ss_id) {
		String data = " ", query = ""; String debuginfo=""; int i=1;  String ay_id="";String field="";
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			query="UPDATE curriculum.regulation_subject_mapping  rsm INNER JOIN curriculum.regulation_master rm ON rsm.regulation_id=rm.regulation_id SET rsm.status=0,rsm.updated_by='" + ss_id + "',rsm.updated_date=NOW() WHERE rsm.subject_id='" + subject_id + "' AND rm.regulation_id='" + regulation_id + "'" ;
            db.update(query);
			return "0";
		} catch (Exception e) {
			return "<b style=\"color:red\" title=\""+e.toString()+"\">Something Went Wrong</b>";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {

			}
		}
	}
	
	public String insertElectiveSlot(String regulation_id,String elective_name, String type, String semester, String credit, String order_no, String ss_id) {
		String data = " ", query1 = "",query2=""; String debuginfo=""; int i=1;  String ay_id="";String field="";
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			query1="INSERT INTO curriculum.elective_master (elective_id,elective_name,st_id,regulation_id,prog_period_id,credit,order_no,STATUS,inserted_by,inserted_date,   updated_by,   updated_date )  SELECT NULL,'" + elective_name + "','" + type + "','"+regulation_id+"','"+semester+"','" + credit + "','" + order_no + "',1,'" + ss_id + "',NOW(),'" + ss_id + "',NOW()  ON DUPLICATE KEY UPDATE STATUS=1,updated_by=VALUES(updated_by),updated_date=NOW() ";
            db.insert(query1);
			
			return "0";
		} catch (Exception e) {
			return "<b style=\"color:red\" title=\""+e.toString()+"\">Something Went Wrong</b>";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {

			}
		}
	}
	
	public String deleteElectiveSlot(String regulation_id,String elective_id,String ss_id) {
		String data = " ", query = ""; 
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			query="UPDATE curriculum.elective_master em SET em.status=0,em.updated_by='" + ss_id + "',em.updated_date=NOW() WHERE em.elective_id='" + elective_id + "'";
			db.update(query);
			return "0";
		} catch (Exception e) {
			return "<b style=\"color:red\" title=\""+e.toString()+"\">Something Went Wrong</b>";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {

			}
		}
	}
	
	public String freezeCourse(String subject_id,String ss_id) {
		String query = "";
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			query="UPDATE curriculum.subject_master sm set status=2,updated_by='"+ss_id+"',updated_date=now() where sm.subject_id='"+subject_id+"'";
            db.update(query);
			return "0";
		} catch (Exception e) {
			return "<b style=\"color:red\" title=\""+e.toString()+"\">Something Went Wrong</b>";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {

			}
		}
	}
	
	public String freezeElectiveSlot(String elective_id,String ss_id) {
		String query = "";
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			query="UPDATE curriculum.elective_master em SET em.status=2,em.updated_by='" + ss_id + "',em.updated_date=NOW() WHERE em.elective_id='" + elective_id + "'";
            db.update(query);
			return "0";
		} catch (Exception e) {
			return "<b style=\"color:red\" title=\""+e.toString()+"\">Something Went Wrong</b>";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {

			}
		}
	}
}
