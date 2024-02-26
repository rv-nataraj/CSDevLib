package CAMPS.Common;

import java.sql.SQLException;

import CAMPS.Connect.DBConnect;
import jakarta.servlet.http.HttpSession;

public class Model_Selects {

	public String templateMethod(HttpSession session, String regulationid, String staffid) {
		String data = "", query = "", flag = "false";
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			query = "SELECT 1";
			db.read(query);

			while (db.rs1.next()) {
				flag = "true";
			}
			data += "</table></div>";
			if (flag.equalsIgnoreCase("false"))
				data = "No Data Found";

		} catch (Exception e) {
			return e.toString();
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {

			}
		}
		return data;
	}

	public String load_Subject_Staff_Allocation_Details(String term_id, String branch_id, String prog_period_id, String section) {
		String data = "", query = "", flag = "false";
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			query = "SELECT concat(sm.staff_id,'-',TRIM(CONCAT(IFNULL(sm.first_name,''),' ',IFNULL(sm.middle_name,''),' ',IFNULL(sm.last_name,''),' ')) ,'-',sbm.`sub_code`,' : ',sbm.`sub_name`,'(',ssa.ssa_id,')') dis,ssa.ssa_id FROM studacad.`core_subject_master` csm INNER JOIN studacad.`subject_staff_allocation` ssa ON ssa.`csm_id`=csm.`csm_id` AND ssa.`prog_period_id`='" + prog_period_id + "' AND ssa.`branch_id`='" + branch_id + "' AND ssa.`section`='" + section + "' AND csm.`term_id`='" + term_id + "' INNER JOIN curriculum.`subject_master` sbm ON sbm.`subject_id`=csm.`subject_id` INNER JOIN camps.staff_master sm ON sm.`staff_id`=ssa.`staff_id`";
			db.read(query);

			data += "<option value=\"\" disabled>-- Core Subjects --</option>";
			while (db.rs.next()) {
				data +="<option value=\"" + db.rs.getString("ssa_id") + "\">" + db.rs.getString("dis") + "</option>";
			}
			
			query="SELECT CONCAT(sm.staff_id,'-',TRIM(CONCAT(IFNULL(sm.first_name,''),' ',IFNULL(sm.middle_name,''),' ',IFNULL(sm.last_name,''),' ')) ,'-',nsm.`sub_code`,' : ',nsm.`sub_name`,'(',ssa.ssa_id,')') dis,ssa.ssa_id FROM studacad.`noncore_subject_master` nsm INNER JOIN studacad.`subject_staff_allocation` ssa ON ssa.`nsm_id`=nsm.`nsm_id` AND ssa.`prog_period_id`='" + prog_period_id + "' AND ssa.`branch_id`='" + branch_id + "' AND ssa.`section`='" + section + "' AND nsm.`term_id`='" + term_id + "'  INNER JOIN camps.staff_master sm ON sm.`staff_id`=ssa.`staff_id`";
			db.read(query);
			data += "<option value=\"\" disabled>-- Non Core Subjects --</option>";
			while (db.rs.next()) {
				data +="<option value=\"" + db.rs.getString("ssa_id") + "\">" + db.rs.getString("dis") + "</option>";
			}
		 
		} catch (Exception e) {
			return e.toString();
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {
				
			}
		}
		return data;
	}
	
	public String loadStudentIDs(String term_id) {
		String data = "", query = ""; String debuginfo="";
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			data+="<table class=\"tbl_new\"> <tr><th>Student Data</th></tr>";
			db.read("SELECT CONCAT('<table>',CONCAT('<tr><td style=\"background-color:#4356bc;color:white\"><b>',may.acc_year,'-',mt.term_name,'-',mp.programme_name,' ',mb.branch_name,'-',mppd.period,'</b></td></tr>') ,'<tr><td>', GROUP_CONCAT('<input style=\"position:relative;opacity:1;left:unset\" type=\"checkbox\" onchange=\"showvalues()\" id=\"',sp.student_id,'\" name=\"',sp.student_id,'\" value=\"',sp.student_id,'\"><b>',sp.student_id,'</b>','-',sp.roll_no separator ',  '),'</td>','</td></tr></table>') `Student Data` FROM camps.`student_promotion` sp INNER JOIN camps.`student_admission_master` sam ON sam.student_id=sp.student_id INNER JOIN camps.`master_branch` mb ON mb.branch_id=sam.branch_id INNER JOIN camps.`master_programme` mp ON mp.programme_id=mb.programme_id INNER JOIN camps.`master_programme_period_det` mppd ON mppd.prog_period_id=sp.prog_period_id INNER JOIN camps.master_term mt ON mt.term_id=sp.term_id INNER JOIN camps.`master_academic_year` may ON may.ay_id=mt.ay_id WHERE sp.term_id='"+term_id+"' GROUP BY mb.branch_id,sp.prog_period_id");
			while (db.rs.next()) {
				data+="<tr>";
				data+="<td>"+db.rs.getString("Student Data")+"</td>";
				data+="</tr>";
			}
			
			data+="</table>";
			
			return data;
		} catch (Exception e) {
			return e.toString();
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {

			}
		}
	}

}
