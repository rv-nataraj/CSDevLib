package CAMPS.curriculum;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import CAMPS.Connect.DBConnect;
import jakarta.servlet.http.HttpSession;


public class CurriculumReports {

	public String curriculumReportView1(String regulationid) {
		String data = "";
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			String prg = "";
            int count = 0; 
            db.read1("SELECT CONCAT(mp.programme_code,' ',mb.branch_name) prg,rm.regulation_id reg ,regulation_name FROM curriculum.regulation_master rm INNER JOIN camps.master_branch mb ON mb.branch_id=rm.branch_id INNER JOIN camps.master_programme mp ON mp.programme_id=mb.programme_id WHERE rm.regulation_id='"+regulationid+"'");
            while (db.rs1.next()) {
                prg = "Curriculum- " + db.rs1.getString("regulation_name") + "-" + db.rs1.getString("prg") + "";
                if (count == 0) {
                    data += "<table align=\"left\" width='90%'><tr><td><h4>" + prg + "</h4></td></tr></table>";
                } else {
                    data += "<table style=\"page-break-before: always;\" width='90%' align=\"left\"><tr><td><h4>" + prg + "</h4></td></tr></table>";
                }
                db.read("SELECT CONCAT('<table align=\"left\" width=\"90%\"><tr><td  align=\"center\"><b>',IF(period<>'-',CONCAT('Semester',' ',period), 'Electives'),'</b></td></tr></table><table width=\"90%\" align=\"left\" border=1 style=\"border-collapse:collapse;\"><tr ><td width=\"15%\" align=\"left\"><b>Display Code</b></td><td width=\"15%\" align=\"left\"><b>Course Code</b></td><td width=\"70%\" align=\"left\"><b title=\"-\">Course Title</b></td><td width=\"4%\" align=\"left\"><b>L</b></td><td width=\"4%\"  ><b>T</b></td><td width=\"4%\"  ><b>P</b></td><td width=\"4%\"  ><b>C</b></td></tr><tr><td>',GROUP_CONCAT(dis_code,'</td><td>',sub_code,'</td><td title=\"',subject_id,'\">',sub_name,'(',subject_id,')','</td><td>',lecture,'</td><td>',tutorial,'</td><td>',practical,'</td><td>',IFNULL(credit,'') ORDER BY IF(period='-','viiii',period),order_no,sub_code SEPARATOR '</td></tr><tr><td>'),'</td></tr></table>' ) sub_code FROM ( (SELECT rsm.subject_id, rsm.regulation_id, sm.dis_code, sm.sub_code, sm.sub_name, IFNULL(mspd.period, '-') period, rsm.order_no, st.type_name,sm.lecture,sm.tutorial,sm.practical,sm.credit FROM curriculum.regulation_master rm INNER JOIN curriculum.regulation_subject_mapping rsm ON rm.regulation_id = rsm.regulation_id AND rm.regulation_id ='" + db.rs1.getString("reg") + "' AND rsm.status > 0 INNER JOIN curriculum.subject_master sm ON rsm.subject_id = sm.subject_id INNER JOIN curriculum.subject_type st ON st.st_id = rsm.st_id LEFT JOIN camps.master_programme_period_det mspd ON mspd.prog_period_id = rsm.prog_period_id ORDER BY IF(period = '-', 'viiii', period), dis_code, order_no) UNION (SELECT em.elective_id subject_id, '' regulation_id, concat('<span style=\"background-color:#ffe821\">','----','</span>') dis_code,concat('<span style=\"background-color:#ffe821\">','----','</span>') sub_code, concat('<span style=\"background-color:#ffe821\">',em.elective_name,'</span>') sub_name, mspd.period period, em.order_no, st.type_name,concat('<span style=\"background-color:#ffe821\">','--','</span>'),concat('<span style=\"background-color:#ffe821\">','--','</span>'),concat('<span style=\"background-color:#ffe821\">','--','</span>'),em.credit FROM curriculum.regulation_master rm INNER JOIN curriculum.elective_master em ON em.regulation_id = rm.regulation_id AND em.status>0 AND rm.regulation_id ='" + db.rs1.getString("reg") + "' INNER JOIN curriculum.subject_type st ON st.st_id = em.st_id LEFT JOIN camps.master_programme_period_det mspd ON mspd.prog_period_id = em.prog_period_id ORDER BY IF(period = '-', 'viiii', period), order_no) ) a GROUP BY IF(period = '-', 'viiii', period) ORDER BY IF(period='-','viiii',period),order_no,sub_code");
                while (db.rs.next()) {
                    data += db.rs.getString("sub_code");
                }
                count++;
            }
			return data;
			
		} catch (Exception e) {
			return e.toString();
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {
				return ex.toString();
			}
		}
	}
	
	public String syllabusCompletenessReport(String regulationid) {
		String data = "",query="";
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			String prg = "";
            int count = 0; 
            db.read1("SELECT CONCAT(mp.programme_code,' ',mb.branch_name) prg,rm.regulation_id reg ,regulation_name FROM curriculum.regulation_master rm INNER JOIN camps.master_branch mb ON mb.branch_id=rm.branch_id INNER JOIN camps.master_programme mp ON mp.programme_id=mb.programme_id WHERE rm.regulation_id='"+regulationid+"'");
            while (db.rs1.next()) {
                prg = "Curriculum- " + db.rs1.getString("regulation_name") + "-" + db.rs1.getString("prg") + "";
                if (count == 0) {
                    data += "<table align=\"left\" width='90%'><tr><td><h4>" + prg + "</h4></td></tr></table>";
                } else {
                    data += "<table style=\"page-break-before: always;\" width='90%' align=\"left\"><tr><td><h4>" + prg + "</h4></td></tr></table>";
                }
                query="SELECT rm.`regulation_name`, CONCAT(mp.`programme_name`,'-',mb.`branch_name`) `programme`,mppd.`period_name` `Semester`,subm.`subject_id` `SubjectID` ,subm.`sub_code` `Course Code` ,subm.`sub_name` `Course Title` ,IFNULL(GROUP_CONCAT(sm.`staff_id`,' - ',TRIM(CONCAT(IFNULL(sm.first_name,''),' ',IFNULL(sm.middle_name,''),' ',IFNULL(sm.last_name,''),' ')) SEPARATOR '<br>'),'<b style=\"color:red\">None Assigned</b>') `Syllabus Content Incharge`, IFNULL(c3.ucount,'<b style=\"color:red\">No Units Entered</b>') `Total Units`,IFNULL(c1.co_count,'<b style=\"color:red\">No COs</b>') `Total COs` FROM curriculum.`regulation_master` rm \r\n"
                		+ "	INNER JOIN camps.`master_branch` mb ON mb.`branch_id`=rm.`branch_id` \r\n"
                		+ "	INNER JOIN curriculum.`regulation_subject_mapping` rsm ON rm.`regulation_id`=rsm.`regulation_id`  AND rsm.`status`=1  \r\n"
                		+ "	INNER JOIN camps.`master_programme` mp ON mp.`programme_id`=mb.`programme_id`\r\n"
                		+ "	INNER JOIN camps.`master_programme_period_det` mppd ON mppd.`prog_period_id`=rsm.`prog_period_id` INNER JOIN curriculum.`subject_master` subm ON subm.`subject_id`=rsm.`subject_id` AND subm.`status`=1  \r\n"
                		+ "	LEFT JOIN curriculum.`subject_staff_mapping` stm ON stm.`subject_id`=subm.`subject_id` AND stm.`status`=1 \r\n"
                		+ "	LEFT JOIN camps.`staff_master` sm ON sm.`staff_id`=stm.`staff_id`\r\n"
                		+ "	LEFT JOIN  (SELECT com.subject_id, COUNT(*) co_count FROM curriculum.`co_master` com WHERE com.`status`=1 GROUP BY com.subject_id) c1 ON c1.subject_id=subm.`subject_id`\r\n"
                		+ "	LEFT JOIN (SELECT com.subject_id, COUNT(*) cpm_count, GROUP_CONCAT(com.co_detail SEPARATOR '<br>') co_texts FROM curriculum.co_master com INNER JOIN curriculum.`co_po_mapping` cpm ON cpm.com_id=com.com_id WHERE com.`status`=1 AND cpm.`status`=1 GROUP BY subject_id) c2 ON c2.subject_id=subm.`subject_id`\r\n"
                		+ "	LEFT JOIN (SELECT scd.subject_id, COUNT(*) ucount FROM curriculum.`subject_cc_details` scd WHERE scd.sccm_id=1 GROUP BY scd.subject_id) c3 ON c3.subject_id=subm.`subject_id`\r\n"
                		+ "	WHERE rm.`regulation_id`='"+regulationid+"'\r\n"
                		+ "	GROUP BY subm.`subject_id` \r\n"
                		+ "	ORDER BY rm.`regulation_id` DESC, mppd.`prog_period_id`,rsm.`order_no`";
                db.read(query);
                data = "<div class='table-responsive text-wrap' ><table class='tbl_new' width=\"100%\" border=\"1\">";
                while (db.rs.next()) {
                    data += "<tr>"+"<td>"+db.rs.getString("regulation_name")+"</td>"+"<td>"+db.rs.getString("programme")+"</td>"+"<td>"+db.rs.getString("Semester")+"</td>"+"<td>"+db.rs.getString("SubjectID")+"</td><td>"+db.rs.getString("Course Code")+"</td>"+"<td>"+db.rs.getString("Course Title")+"</td>"+"<td>"+db.rs.getString("Syllabus Content Incharge")+"</td>"+"<td>"+db.rs.getString("Total Units")+"</td>"+"<td>"+db.rs.getString("Total COs")+"</td>"+"</tr>";
                }
                data += "</table></div>";
                count++;
            }
			return data;
		} catch (Exception e) {
			return e.toString();
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {
				return ex.toString();
			}
		}
	}
	
	public String subjectMasterData(String subjectid) {
		String data = "", query="";
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			query="SELECT * FROM curriculum.subject_master sm WHERE sm.`subject_id`='"+subjectid+"'";
			db.read(query);
			
			data = "<div class='table-responsive text-wrap' ><table class=\"tbl_new\" width=\"100%\" border=\"1\">";
			data+="<tr style=\"background-color:grey\">";
			for(int i=1; i<=db.rs.getMetaData().getColumnCount(); i++) {
				data+="<td title=\""+db.rs.getMetaData().getColumnLabel(i)+"\">"+i+"</td>";
			}
			data+="</tr>";
			while (db.rs.next()) {
				data+="<tr>";
				for(int i=1; i<=db.rs.getMetaData().getColumnCount(); i++) {
					data+="<td title=\""+db.rs.getMetaData().getColumnLabel(i)+"\">"+db.rs.getString(i)+"</td>";
				}
				data+="</tr>";
			}
			
            return data+"</table></div>";
		} catch (Exception e) {
			return e.toString();
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {

			}
		}
	}
	public String subjectEvalPatternData(String subjectid) {
		String data = "", query="";
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			query="SELECT * FROM curriculum.subject_ep_mapping sem WHERE sem.`subject_id`='"+subjectid+"'";
			db.read(query);
			data = "<div class='table-responsive text-wrap' ><table class=\"tbl_new\" width=\"100%\" border=\"1\">";
			data+="<tr style=\"background-color:grey\">";
			for(int i=1; i<=db.rs.getMetaData().getColumnCount(); i++) {
				data+="<td title=\""+db.rs.getMetaData().getColumnLabel(i)+"\">"+i+"</td>";
			}
			data+="</tr>";
			while (db.rs.next()) {
				data+="<tr>";
				for(int i=1; i<=db.rs.getMetaData().getColumnCount(); i++) {
					data+="<td title=\""+db.rs.getMetaData().getColumnLabel(i)+"\">"+db.rs.getString(i)+"</td>";
				}
				data+="</tr>";
			}
			
            return data+"</table></div>";
		} catch (Exception e) {
			return e.toString();
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {

			}
		}
	}
	public String subjectInchargeData(String subjectid) {
		String data = "", query="";
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			query="SELECT * FROM curriculum.`subject_staff_mapping` sem WHERE sem.`subject_id`='"+subjectid+"'";
			db.read(query);
			data = "<div class='table-responsive text-wrap' ><table class=\"tbl_new\" width=\"100%\" border=\"1\">";
			data+="<tr style=\"background-color:grey\">";
			for(int i=1; i<=db.rs.getMetaData().getColumnCount(); i++) {
				data+="<td title=\""+db.rs.getMetaData().getColumnLabel(i)+"\">"+i+"</td>";
			}
			data+="</tr>";
			while (db.rs.next()) {
				data+="<tr>";
				for(int i=1; i<=db.rs.getMetaData().getColumnCount(); i++) {
					data+="<td title=\""+db.rs.getMetaData().getColumnLabel(i)+"\">"+db.rs.getString(i)+"</td>";
				}
				data+="</tr>";
			}
			
            return data+"</table></div>";
		} catch (Exception e) {
			return e.toString();
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {

			}
		}
	}
	public String subjectRegulationMappingData(String subjectid) {
		String data = "", query="";
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			query="SELECT * FROM curriculum.`regulation_subject_mapping` sem WHERE sem.`subject_id`='"+subjectid+"'";
			db.read(query);
			data = "<div class='table-responsive text-wrap' ><table class=\"tbl_new\" width=\"100%\" border=\"1\">";
			data+="<tr style=\"background-color:grey\">";
			for(int i=1; i<=db.rs.getMetaData().getColumnCount(); i++) {
				data+="<td title=\""+db.rs.getMetaData().getColumnLabel(i)+"\">"+i+"</td>";
			}
			data+="</tr>";
			while (db.rs.next()) {
				data+="<tr>";
				for(int i=1; i<=db.rs.getMetaData().getColumnCount(); i++) {
					data+="<td title=\""+db.rs.getMetaData().getColumnLabel(i)+"\">"+db.rs.getString(i)+"</td>";
				}
				data+="</tr>";
			}
			
            return data+"</table></div>";
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
