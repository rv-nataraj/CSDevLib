package CAMPS.library;

import java.sql.SQLException;
import CAMPS.Connect.DBConnect;
import jakarta.servlet.http.HttpSession;

public class ResourceEntryTransactions { 

	public String searchPublisher(String search_string,String resource_id) { 
		String data = "", query = ""; int i=1;
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			query="SELECT pm.publisher_id,CONCAT('<a href=\"#\" data-dismiss=\"modal\" onclick=\"updateResourcePublisher(''publisher_id'',',pm.publisher_id,',"+resource_id+")\">',TRIM(CONCAT(IFNULL(pm.publisher_name,''))),'</a>') publisher_name FROM library.`publisher_master` pm WHERE pm.publisher_id>0 AND pm.publisher_name LIKE '%"+search_string+"%' LIMIT 50";
			db.read(query);	
			data = "<div class=\"table-responsive text-nowrap\"><table class='table table-bordered table-striped' width='100%'>";
			data += "<thead><tr><th>S.No</th><th>ID</th><th>Name</th></tr></thead>";
			while (db.rs.next()) {
				data += "<tr>";
				data += "<td>"+ i++ +"</td>";
				data += "<td>"+db.rs.getString("publisher_id") +"</td>";
				data += "<td>"+db.rs.getString("publisher_name")+"</td>";
				data += "</tr>";
			}
			data += "</table></div>";
			if (i==1) {
				data = "No Matching Records Found";
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
	
	public String loadResourceCount(String search_data) { 
		String data = "", query = "", count=""; String fix_id="";  int i=1;
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
            query="SELECT CEIL(count(*)/50) count FROM library.`resource_master` rm WHERE rm.`status`>0 AND rm.`title` LIKE '%"+search_data+"%' ORDER BY rm.resource_id";
            db.read(query);
        	while(db.rs.next()) {
        		count=db.rs.getString("count");
    		}
			return count;
		} catch (Exception e) {
			return "<b style=\"color:red\" title=\""+e.toString()+"\">Something Went Wrong</b>" + "<!--"+query+"-->";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {
	
			}
		}
	}
		
	public String loadResourceDataUpdateUI(String search_data,String page_num) { 
		String data = "", query = "";int i=1;
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			
            db.read("SELECT sm.`spec_id`, IFNULL(sm.`specialization`,'') specialization FROM library.`specialization_master` sm WHERE sm.`status`>0");
			String opt1 = "<option selected disabled value=\"\">---Specialization---</option>";
            while (db.rs.next()) {
            	opt1 += "<option value='" + db.rs.getString("spec_id") + "'>" + db.rs.getString("specialization") + "</option>";
            }
            db.read("SELECT md.department_id, CONCAT(IFNULL(md.dept_code,''),' - ',IFNULL(md.dept_name,'')) dept_name FROM camps.`master_department` md WHERE md.status>0 ORDER BY md.dept_order_no");
			String opt4 = "<option selected disabled value=\"\">---Department---</option>";
            while (db.rs.next()) {
            	opt4 += "<option value='" + db.rs.getString("department_id") + "'>" + db.rs.getString("dept_name") + "</option>";
            }

			query="SELECT rm.`resource_id`, IFNULL(rm.`title`,'') title, IFNULL(rm.`authors`,'') AUTHORS, IFNULL(rm.`spec_id`,'') spec_id, IFNULL(sm.`specialization`,'') specialization, IFNULL(rm.`publisher_id`,'') publisher_id, IFNULL(pum.publisher_name,'') publisher_name, IFNULL(rm.`status`,'') `status`, IFNULL(GROUP_CONCAT(rdm.dep_id),'') dep_id, GROUP_CONCAT(IFNULL(md.dept_name,'')) dept_name FROM library.`resource_master` rm LEFT JOIN library.`specialization_master` sm ON sm.`spec_id`=rm.`spec_id` AND sm.`status`>0 LEFT JOIN library.`publisher_master` pum ON pum.`publisher_id`=rm.`publisher_id` AND pum.`status`>0 LEFT JOIN library.`resource_department_mapping` rdm ON rdm.resource_id=rm.resource_id AND rdm.status>0 LEFT JOIN camps.`master_department` md ON md.department_id=rdm.dep_id AND md.status>0 WHERE rm.`status`>0 AND rm.`title` LIKE '%"+search_data+"%' GROUP BY rm.resource_id ORDER BY rm.`resource_id` DESC LIMIT "+(Integer.parseInt(page_num)-1)*50+",50";
            db.read(query);
            data = "<div class='table-responsive text-wrap' ><div><table class='tbl_new'  border=\"1\" style=\"width:100%\">";
			data+="<tr><th style=\"width:70px\">ID</th><th style=\"width:60%\">Resource Details</th><th style=\"width:30%\">Departments</th><th style=\"width:70px\">Actions</th></tr>";

        	while(db.rs.next()) {
        		if(db.rs.getString("status").equalsIgnoreCase("1")) {
                    data+="<tr>";
					data+="<td  data-column=\"ID\">"+ db.rs.getString("resource_id") +"</td>";
					data+="<td title=\"Resource Details\" data-column=\"Details\">	<input placeholder=\"Book Title\" 	title=\"Book Title\" 	type=\"text\" 	id=\"title"+db.rs.getString("resource_id")+"\" 			style=\"width:100%\" 	value=\""+db.rs.getString("title")+"\" 			onchange=\"updateResourceData('title',"+db.rs.getString("resource_id")+");		this.style.background = '#f4e88e'\" ><br>";
					data+="															<input placeholder=\"Authors\" 		title=\"Authors\" 		type=\"text\"	id=\"authors"+db.rs.getString("resource_id")+"\" 		style=\"width:100%\" 	value=\""+db.rs.getString("authors")+"\"		onchange=\"updateResourceData('authors',"+db.rs.getString("resource_id")+");	this.style.background = '#f4e88e'\" ><br>";
					data+="<table style=\"width:100%\"> <td style=\"width:60%\">	<input placeholder=\"Publisher\" 	title=\"Publisher\" 	type=\"text\"	id=\"publisher_id"+db.rs.getString("resource_id")+"\" 	style=\"width:100%\" 	value=\""+db.rs.getString("publisher_name")+"\" readonly	></td>";
					data+="								<td style=\"width:40%\">	<input placeholder=\"Search...\" 	title=\"Search\" 		type=\"text\"	id=\"search"+db.rs.getString("resource_id")+"\" 		style=\"width:80%\" 	><button type=\"button\" style=\"background-color:orange;width:20%;\" data-toggle=\"modal\" data-target=\"#largeModal\" onclick=\"searchPublisher("+db.rs.getString("resource_id")+")\">Search</button></td></table>";
					data+="<table style=\"width:100%\"> <td style=\"width:70%\">	<select 							title=\"Specialization\"				id=\"spec_id"+db.rs.getString("resource_id")+"\" 		style=\"width:100%\" 	onchange=\"updateResourceData('spec_id',"+db.rs.getString("resource_id")+");	this.style.background = '#f4e88e'\" >"+ opt1	+"</select> <script> $('#spec_id" + db.rs.getString("resource_id") + "').val('" + db.rs.getString("spec_id") + "');</script> </td>";
					data+="								<td style=\"width:20%\">	<button class=\"btn btn-warning waves-effect\"  data-toggle=\"modal\" data-target=\"#largeModal\" type=\"button\" style=\"padding: 0px 15px\" value=\"Additional\" onclick=\"loadResourceAdditionalDetails("+db.rs.getString("resource_id")+")\" >Additional Details</button></td>";
					data+="								<td style=\"width:10%\">	<button class=\"btn btn-warning waves-effect\"  data-toggle=\"modal\" data-target=\"#largeModal\" type=\"button\" style=\"padding: 0px 15px\" value=\"Access No\" onclick=\"loadResourceAccessNoDetails("+db.rs.getString("resource_id")+")\" >Access No</button></td></table></td>";
					data+="<td title=\"Department\" data-column=\"Department\">  	<select size=\"5\" multiple	class=\"multiSelect\"			title=\"Department\" id=\"dep_id"+db.rs.getString("resource_id")+"\" 	style=\"width:100%\" 	onchange=\"updateResourceData('dep_id',"+db.rs.getString("resource_id")+");	this.style.background = '#f4e88e'\" >"+ opt4 +"</select><script> $('#dep_id" + db.rs.getString("resource_id") + "').val([" + db.rs.getString("dep_id") + "]);</script></td>";
					data+="<td><button title=\"Freeze\" class=\"btn btn-success waves-effect\"	 type=\"button\" style=\"padding: 5px\" value=\"freeze\" onclick=\"freezeResourceData("+db.rs.getString("resource_id")+");setTimeout(function() { loadResourceDataUpdateUI('"+search_data+"',"+page_num+")}, 1000);\" >Fz</button>";
					data+="<button title=\"Delete\" 	class=\"btn btn-danger waves-effect\" 	 type=\"button\" style=\"padding: 5px\" value=\"delete\" onclick=\"deleteResourceData("+db.rs.getString("resource_id")+");setTimeout(function() { loadResourceDataUpdateUI('"+search_data+"',"+page_num+")}, 1000);\">X</button></td>";
					data+="</tr>"; 
            	}
        		else if(db.rs.getString("status").equalsIgnoreCase("2")) {
                    data+="<tr>";
                    data+="<td  data-column=\"ID\">"+ db.rs.getString("resource_id") +"</td>";
					data+="<td title=\"Resource Details\" data-column=\"Details\">	<input readonly title=\"Book Title\" 	type=\"text\" 	id=\"title"+db.rs.getString("resource_id")+"\" 			style=\"width:100%;border:none;background:transparent\" 	value=\""+db.rs.getString("title")+"\" 			 ><br>";
					data+="															<input readonly title=\"Authors\" 		type=\"text\"	id=\"authors"+db.rs.getString("resource_id")+"\" 		style=\"width:100%;border:none;background:transparent\" 	value=\""+db.rs.getString("authors")+"\"		><br>";
					data+="															<input readonly title=\"Publisher\" 	type=\"text\"	id=\"publisher_id"+db.rs.getString("resource_id")+"\" 	style=\"width:100%;border:none;background:transparent\" 	value=\""+db.rs.getString("publisher_name")+"\" 	>";
					data+="<table style=\"width:100%\"> <td style=\"width:70%\">	<input readonly title=\"Specialization\"type=\"text\"	id=\"spec_id"+db.rs.getString("resource_id")+"\" 		style=\"width:100%;border:none;background:transparent\" 	value=\""+db.rs.getString("specialization")+"\" ></td>";
					data+="								<td style=\"width:20%\">	<button class=\"btn btn-warning waves-effect\" data-toggle=\"modal\" data-target=\"#largeModal\" type=\"button\" style=\"padding: 0px 15px\" value=\"Additional\" onclick=\"loadResourceAdditionalDetails("+db.rs.getString("resource_id")+")\" >Additional Details</button></td>";
					data+="								<td style=\"width:10%\">	<button class=\"btn btn-warning waves-effect\"  data-toggle=\"modal\" data-target=\"#largeModal\" type=\"button\" style=\"padding: 0px 15px\" value=\"Access No\" onclick=\"loadResourceAccessNoDetails("+db.rs.getString("resource_id")+")\" >Access No</button></td></table>";
					data+="<td title=\"Department\" data-column=\"Department\">  	<textarea readonly title=\"Departments\" rows=\"5\" cols=\"50\"	id=\"dep_id"+db.rs.getString("resource_id")+"\" style=\"width:100%;border:none;background:transparent\"  >"+db.rs.getString("dept_name")+"</textarea></td>";
					data+="<td></td></tr>"; 
            	}
    		}
        	data +="</table></div></div><script>multiSelectWithoutCtrl('.multiSelect');</script>";

			return data + "<!--"+query+"-->";
		} catch (Exception e) {
			return "<b style=\"color:red\" title=\""+e.toString()+"\">Something Went Wrong</b>" + "<!--"+query+"-->";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {
	
			}
		}
	}
		

	public String loadAddResourceData() { 
		String data = "", query = ""; String fix_id="";  int i=1;
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
            data = "<div class='table-responsive text-wrap' ><div><table class='tbl_new'  border=\"1\" style=\"margin-left:auto;margin-right:auto;\">";
			data+="<tr><th>Resource Title</th><th>Authors</th><th>Actions</th></tr>";
			data+="<tr>";
			data+="<td style=\"background-color:green\" title=\"Resource Title\" 	data-column=\"Resource Title\" >" + 	"<input type=\"text\" id=\"title\" 		style=\"width:300px;background-color:#c5ecd1\" placeholder=\"Resource Title\">" +"</td>";
			data+="<td style=\"background-color:green\" title=\"Authors\" 			data-column=\"Authors\" >" + 			"<input type=\"text\" id=\"authors\" 	style=\"width:300px;background-color:#c5ecd1\" placeholder=\"Authors\">" 		+"</td>";
			data+="<td data-column=\"Add\" >" + "<button class=\"btn btn-warning waves-effect\" onclick=\"addResourceData()\">Add</button>" + "</td>";
			data+="</tr>"; 
			data+="</table></div></div>";
			return data + "<!--"+query+"-->";
		} catch (Exception e) {
			return "<b style=\"color:red\" title=\""+e.toString()+"\">Something Went Wrong</b>" + "<!--"+query+"-->";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {

			}
		}
	}
	
	public String addResourceData(String title, String authors, String ss_id) {
		String data = "", query = ""; String debuginfo=""; String p_id="";
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			query="INSERT INTO library.`resource_master` (`resource_id`,`title`,`authors`,`status`,`inserted_by`,`inserted_date`,`updated_by`,`updated_date`) VALUES (NULL,'"+title+"','"+authors+"',1,'"+ss_id+"',NOW(),'"+ss_id+"',NOW())"; 	
            db.insert(query);
            
            return ("Added Successfully...");
		} catch (Exception e) {
			return "<b style=\"color:red\" title=\""+e.toString()+"\">Something Went Wrong1</b>";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {

			}
		}
	}
	
	public String updateResourceData(String resource_id, String fname, String fvalue, String ss_id) {
		String data = "", query = ""; String debuginfo=""; 
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			if(fname.equalsIgnoreCase("dep_id")) {
				db.update("UPDATE library.`resource_department_mapping` SET status='0', updated_by='"+ss_id+"', updated_date=NOW() WHERE resource_id='"+resource_id+"'");
				query="INSERT INTO library.`resource_department_mapping` (`bdm_id`,`resource_id`,`dep_id`,`status`,`inserted_by`,`inserted_date`,`updated_by`,`updated_date`) SELECT NULL,'"+resource_id+"',md.department_id,'1','"+ss_id+"',NOW(),'"+ss_id+"',NOW() FROM camps.`master_department` md WHERE md.department_id IN("+fvalue+") AND md.status>0 ON DUPLICATE KEY UPDATE STATUS=1,resource_id=VALUES(resource_id),dep_id=VALUES(dep_id),updated_by='"+ss_id+"',updated_date=NOW()";
				db.insert(query);
				return fvalue;
			}
			else {
				query=" UPDATE library.`resource_master` set "+fname+"='"+fvalue+"', updated_by='"+ss_id+"', updated_date=NOW() where resource_id='"+resource_id+"'";
				db.update(query);
				if(fname.equalsIgnoreCase("publisher_id")) {
					db.read("SELECT pm.publisher_id,IFNULL(pm.publisher_name,'') publisher_name FROM library.`publisher_master` pm WHERE pm.publisher_id='"+fvalue+"' AND pm.status>0");
					if(db.rs.next()) {
						return db.rs.getString("publisher_name");
					}
				}
			}
			return query;
		} catch (Exception e) {
			return "<b style=\"color:red\" title=\""+e.toString()+"\">Something Went Wrong1</b>";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {

			}
		}
	}
	
	public String freezeResourceData(String resource_id, String ss_id) {
		String data = "", query = ""; String debuginfo=""; 
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			query=" UPDATE library.`resource_master` set status='2', updated_by='"+ss_id+"', updated_date=NOW() where resource_id='"+resource_id+"'";
			db.update(query);
			return query;
		} catch (Exception e) {
			return "<b style=\"color:red\" title=\""+e.toString()+"\">Something Went Wrong1</b>";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {

			}
		}
	}
	
	public String deleteResourceData(String resource_id, String ss_id) {
		String data = "", query = ""; String debuginfo=""; 
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			query=" UPDATE library.`resource_master` set status='0', updated_by='"+ss_id+"', updated_date=NOW() where resource_id='"+resource_id+"'";
			db.update(query);
			return query;
		} catch (Exception e) {
			return "<b style=\"color:red\" title=\""+e.toString()+"\">Something Went Wrong1</b>";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {

			}
		}
	}

	public String loadResourceAdditionalDetails(String resource_id) {
		String data = "", query = ""; String debuginfo=""; 
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			db.read("SELECT cm.`cat_id`, IFNULL(cm.`cat_name`,'') cat_name FROM library.`category_master` cm WHERE cm.`status`>0");
			String opt = "<option selected disabled value=\"\">---Category---</option>";
            while (db.rs.next()) {
            	opt += "<option value='" + db.rs.getString("cat_id") + "'>" + db.rs.getString("cat_name") + "</option>";
            }
            db.read("SELECT lm.`id`,IFNULL(lm.`name`,'') lang_name FROM camps.`languages_master` lm WHERE lm.record_status>0");
			String opt2 = "<option selected disabled value=\"\">---Language---</option>";
            while (db.rs.next()) {
            	opt2 += "<option value='" + db.rs.getString("id") + "'>" + db.rs.getString("lang_name") + "</option>";
            }
            db.read("SELECT curm.`id`,IFNULL(curm.`name`,'') cur_name FROM camps.`currency_master` curm WHERE curm.record_status>0");
			String opt3 = "<option selected disabled value=\"\">Month</option>";
            while (db.rs.next()) {
            	opt3 += "<option value='" + db.rs.getString("id") + "'>" + db.rs.getString("cur_name") + "</option>";
            }
            
			query="SELECT rm.`resource_id`, IFNULL(rm.`title`,'') title, IFNULL(rm.`volume`,'') volume, IFNULL(rm.`cat_id`,'') cat_id,IFNULL(cm.`cat_name`,'') cat_name, IFNULL(rm.`isbn`,'') isbn, IFNULL(rm.`publication_year`,'') publication_year, IFNULL(rm.`language_id`,'') language_id, IFNULL(lm.`name`,'') lang_name, IFNULL(rm.`no_of_pages`,'') no_of_pages, IFNULL(rm.`price`,'') price, IFNULL(rm.`currency_type`,'') currency_type, IFNULL(curm.`name`,'') cur_name, IFNULL(rm.`edition`,'') edition, IFNULL(rm.`bill_no`,'') bill_no, IFNULL(rm.`bill_date`,'') bill_date, IFNULL(rm.`status`,'') `status` FROM library.`resource_master` rm LEFT JOIN library.`category_master` cm ON cm.`cat_id`=rm.`cat_id` AND cm.`status`>0 LEFT JOIN camps.`languages_master` lm ON lm.`id`=rm.`language_id` AND lm.record_status>0 LEFT JOIN camps.`currency_master` curm ON curm.`id`=rm.`currency_type` AND curm.`record_status`>0 WHERE rm.`status`>0 AND rm.resource_id='"+resource_id+"'";
			db.read(query);	
			data = "<table class='table table-bordered table-striped' width='100%'>";
			while (db.rs.next()) {
				if(db.rs.getString("status").equalsIgnoreCase("1")) {
					
					data+="<tr><th style=\"text-align: center\" colspan=\"2\">"+db.rs.getString("Title")+"</th></tr>";
					
					data+="<tr><th title=\"Volume\" data-column=\"Volume\" style=\"width:40%\">Volume</th>";
					data+="<td title=\"Volume\" data-column=\"Volume\" style=\"width:60%\">	<input placeholder=\"Volume\" title=\"Volume\" type=\"number\" id=\"volume"+db.rs.getString("resource_id")+"\" 	style=\"width:100%\" value=\""+db.rs.getString("volume")+"\" onchange=\"updateResourceData('volume',"+db.rs.getString("resource_id")+"); this.style.background = '#f4e88e'\" ></td></tr>";
					
					data+="<tr><th title=\"Category\" data-column=\"Category\" style=\"width:40%\">Category</th>";
					data+="<td title=\"Category\" data-column=\"Category\" style=\"width:60%\">	<select title=\"Category\"	id=\"cat_id"+db.rs.getString("resource_id")+"\" style=\"width:100%\" onchange=\"updateResourceData('cat_id',"+db.rs.getString("resource_id")+"); this.style.background = '#f4e88e'\" >"+ opt +"</select> <script> $('#cat_id" + db.rs.getString("resource_id") + "').val('" + db.rs.getString("cat_id") + "');</script> </td></tr>";
					
					data+="<tr><th title=\"ISBN\" data-column=\"ISBN\" style=\"width:40%\">ISBN</th>";
					data+="<td title=\"ISBN\" data-column=\"ISBN\" style=\"width:60%\">	<input placeholder=\"ISBN\" title=\"ISBN\" type=\"text\" id=\"isbn"+db.rs.getString("resource_id")+"\" style=\"width:100%\" value=\""+db.rs.getString("isbn")+"\" onchange=\"updateResourceData('isbn',"+db.rs.getString("resource_id")+");	this.style.background = '#f4e88e'\" ></td></tr>";
					
					data+="<tr><th title=\"Publication Year\" data-column=\"Year\" style=\"width:40%\">Publication Year</th>";
					data+="<td title=\"Publication Year\" data-column=\"Year\" style=\"width:60%\">	<input placeholder=\"Publication Year\" title=\"Publication Year\" type=\"number\" id=\"publication_year"+db.rs.getString("resource_id")+"\" style=\"width:100%\" value=\""+db.rs.getString("publication_year")+"\" onchange=\"updateResourceData('publication_year',"+db.rs.getString("resource_id")+");	this.style.background = '#f4e88e'\" ></td></tr>";
					
					data+="<tr><th title=\"Language\" data-column=\"Language\" style=\"width:40%\">Language</th>";
					data+="<td title=\"Language\" data-column=\"Language\" style=\"width:60%\">	<select title=\"Language\"	id=\"language_id"+db.rs.getString("resource_id")+"\" style=\"width:100%\" onchange=\"updateResourceData('language_id',"+db.rs.getString("resource_id")+"); this.style.background = '#f4e88e'\" >"+ opt2 +"</select> <script> $('#language_id" + db.rs.getString("resource_id") + "').val('" + db.rs.getString("language_id") + "');</script> </td></tr>";
					
					data+="<tr><th title=\"No of Pages\" data-column=\"Pages\" style=\"width:40%\">No of Pages</th>";
					data+="<td title=\"No of Pages\" data-column=\"Pages\" style=\"width:60%\">	<input placeholder=\"No of Pages\" title=\"No of Pages\" type=\"number\" id=\"no_of_pages"+db.rs.getString("resource_id")+"\" style=\"width:100%\" value=\""+db.rs.getString("no_of_pages")+"\" onchange=\"updateResourceData('no_of_pages',"+db.rs.getString("resource_id")+");	this.style.background = '#f4e88e'\" ></td></tr>";
					
					data+="<tr><th title=\"Currency Type\" data-column=\"Currency Type\" style=\"width:40%\">Currency Type</th>";
					data+="<td title=\"Currency Type\" data-column=\"Currency Type\" style=\"width:60%\">	<select title=\"Currency Type\"	id=\"currency_type"+db.rs.getString("resource_id")+"\" style=\"width:100%\" onchange=\"updateResourceData('currency_type',"+db.rs.getString("resource_id")+"); this.style.background = '#f4e88e'\" >"+ opt3 +"</select> <script> $('#currency_type" + db.rs.getString("resource_id") + "').val('" + db.rs.getString("currency_type") + "');</script> </td></tr>";
					
					data+="<tr><th title=\"Price\" data-column=\"Price\" style=\"width:40%\">Price</th>";
					data+="<td title=\"Price\" data-column=\"Price\" style=\"width:60%\">	<input placeholder=\"Price\" title=\"Price\" type=\"number\" id=\"price"+db.rs.getString("resource_id")+"\" 	style=\"width:100%\" value=\""+db.rs.getString("price")+"\" onchange=\"updateResourceData('price',"+db.rs.getString("resource_id")+"); this.style.background = '#f4e88e'\" ></td></tr>";
					
					data+="<tr><th title=\"Edition\" data-column=\"Edition\" style=\"width:40%\">Edition</th>";
					data+="<td title=\"Edition\" data-column=\"Edition\" style=\"width:60%\">	<input placeholder=\"Edition\" title=\"Edition\" type=\"text\" id=\"edition"+db.rs.getString("resource_id")+"\" style=\"width:100%\" value=\""+db.rs.getString("edition")+"\" onchange=\"updateResourceData('edition',"+db.rs.getString("resource_id")+");	this.style.background = '#f4e88e'\" ></td></tr>";
					
					data+="<tr><th title=\"Bill No\" data-column=\"Bill No\" style=\"width:40%\">Bill No</th>";
					data+="<td title=\"Bill No\" data-column=\"Bill No\" style=\"width:60%\">	<input placeholder=\"Bill No\" title=\"Bill No\" type=\"number\" id=\"bill_no"+db.rs.getString("resource_id")+"\" 	style=\"width:100%\" value=\""+db.rs.getString("bill_no")+"\" onchange=\"updateResourceData('bill_no',"+db.rs.getString("resource_id")+"); this.style.background = '#f4e88e'\" ></td></tr>";
					
					data+="<tr><th title=\"Bill Date\" data-column=\"Bill Date\" style=\"width:40%\">Bill Date</th>";
					data+="<td title=\"Bill Date\" data-column=\"Bill Date\" style=\"width:60%\">	<input placeholder=\"Bill Date\" title=\"Bill Date\" type=\"date\" id=\"bill_date"+db.rs.getString("resource_id")+"\" 	style=\"width:100%\" value=\""+db.rs.getString("bill_date")+"\" onchange=\"updateResourceData('bill_date',"+db.rs.getString("resource_id")+"); this.style.background = '#f4e88e'\" ></td></tr>";
					
            	}
        		else if(db.rs.getString("status").equalsIgnoreCase("2")) {
					
        			data+="<tr><th style=\"text-align: center\" colspan=\"2\">"+db.rs.getString("Title")+"</th></tr>";
					
        			data+="<tr><th title=\"Volume\" data-column=\"Volume\" style=\"width:40%\">Volume</th>";
					data+="<td title=\"Volume\" data-column=\"Volume\" style=\"width:60%\">	<input readonly title=\"Volume\" type=\"number\" id=\"volume"+db.rs.getString("resource_id")+"\" 	style=\"width:100%;border:none;background:transparent\" value=\""+db.rs.getString("volume")+"\"  ></td></tr>";
					
					data+="<tr><th title=\"Category\" data-column=\"Category\" style=\"width:40%\">Category</th>";
					data+="<td title=\"Category\" data-column=\"Category\" style=\"width:60%\">	<input readonly title=\"Category\" type=\"text\" id=\"cat_id"+db.rs.getString("resource_id")+"\" style=\"width:100%;border:none;background:transparent\" value=\""+db.rs.getString("resource_id")+"\"> </td></tr>";
					
					data+="<tr><th title=\"ISBN\" data-column=\"ISBN\" style=\"width:40%\">ISBN</th>";
					data+="<td title=\"ISBN\" data-column=\"ISBN\" style=\"width:60%\">	<input readonly title=\"ISBN\" type=\"text\" id=\"isbn"+db.rs.getString("resource_id")+"\" style=\"width:100%;border:none;background:transparent\" value=\""+db.rs.getString("isbn")+"\"  ></td></tr>";
					
					data+="<tr><th title=\"Publication Year\" data-column=\"Year\" style=\"width:40%\">Publication Year</th>";
					data+="<td title=\"Publication Year\" data-column=\"Year\" style=\"width:60%\">	<input readonly  title=\"Publication Year\" type=\"number\" id=\"publication_year"+db.rs.getString("resource_id")+"\" style=\"width:100%;border:none;background:transparent\" value=\""+db.rs.getString("publication_year")+"\" ></td></tr>";
					
					data+="<tr><th title=\"Language\" data-column=\"Language\" style=\"width:40%\">Language</th>";
					data+="<td title=\"Language\" data-column=\"Language\" style=\"width:60%\">	<input readonly title=\"Language\" type=\"text\"	id=\"language_id"+db.rs.getString("resource_id")+"\" style=\"width:100%;border:none;background:transparent\" value=\""+db.rs.getString("resource_id")+"\" > </td></tr>";
					
					data+="<tr><th title=\"No of Pages\" data-column=\"Pages\" style=\"width:40%\">No of Pages</th>";
					data+="<td title=\"No of Pages\" data-column=\"Pages\" style=\"width:60%\">	<input readonly  title=\"No of Pages\" type=\"number\" id=\"no_of_pages"+db.rs.getString("resource_id")+"\" style=\"width:100%;border:none;background:transparent\" value=\""+db.rs.getString("no_of_pages")+"\"  ></td></tr>";
					
					data+="<tr><th title=\"Currency Type\" data-column=\"Currency Type\" style=\"width:40%\">Currency Type</th>";
					data+="<td title=\"Currency Type\" data-column=\"Currency Type\" style=\"width:60%\">	<input readonly title=\"Currency Type\"	type=\"text\" id=\"currency_type"+db.rs.getString("resource_id")+"\" style=\"width:100%;border:none;background:transparent\" value=\""+db.rs.getString("resource_id")+"\" > </td></tr>";
					
					data+="<tr><th title=\"Price\" data-column=\"Price\" style=\"width:40%\">Price</th>";
					data+="<td title=\"Price\" data-column=\"Price\" style=\"width:60%\">	<input readonly title=\"Price\" type=\"number\" id=\"price"+db.rs.getString("resource_id")+"\" 	style=\"width:100%;border:none;background:transparent\" value=\""+db.rs.getString("price")+"\"  ></td></tr>";
					
					data+="<tr><th title=\"Edition\" data-column=\"Edition\" style=\"width:40%\">Edition</th>";
					data+="<td title=\"Edition\" data-column=\"Edition\" style=\"width:60%\">	<input readonly title=\"Edition\" type=\"text\" id=\"edition"+db.rs.getString("resource_id")+"\" style=\"width:100%;border:none;background:transparent\" value=\""+db.rs.getString("edition")+"\"  ></td></tr>";
					
					data+="<tr><th title=\"Bill No\" data-column=\"Bill No\" style=\"width:40%\">Bill No</th>";
					data+="<td title=\"Bill No\" data-column=\"Bill No\" style=\"width:60%\">	<input readonly title=\"Bill No\" type=\"number\" id=\"bill_no"+db.rs.getString("resource_id")+"\" 	style=\"width:100%;border:none;background:transparent\" value=\""+db.rs.getString("bill_no")+"\"  ></td></tr>";
					
					data+="<tr><th title=\"Bill Date\" data-column=\"Bill Date\" style=\"width:40%\">Bill Date</th>";
					data+="<td title=\"Bill Date\" data-column=\"Bill Date\" style=\"width:60%\">	<input readonly title=\"Bill Date\" type=\"date\" id=\"bill_date"+db.rs.getString("resource_id")+"\" 	style=\"width:100%;border:none;background:transparent\" value=\""+db.rs.getString("bill_date")+"\"  ></td></tr>";
            	}
    		}
        	data +="</table></div></div>";

			return data + "<!--"+query+"-->";
		} catch (Exception e) {
			return "<b style=\"color:red\" title=\""+e.toString()+"\">Something Went Wrong1</b>";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {

			}
		}
	}
	
	public String loadResourceAccessNoDetails(String resource_id) {
		String data = " ", query = "", access_no=""; String debuginfo=""; int i=1;  
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			
			db.read("SELECT lm.`library_id` AS lib_id,IFNULL(lm.`library_name`,'') library_name FROM library.`library_master` lm WHERE lm.`status`>0");
			String opt = "<option selected disabled value=\"\">---Library---</option>";
            while (db.rs.next()) {
            	opt += "<option value='" + db.rs.getString("lib_id") + "'>" + db.rs.getString("library_name") + "</option>";
            }
            db.read("SELECT lfm.lfm_id, IFNULL(lfm.floor_name,'') floor_name FROM library.`lib_floor_master` lfm WHERE lfm.status>0");
			String opt1 = "<option selected disabled value=\"\">---Floor---</option>";
            while (db.rs.next()) {
            	opt1 += "<option value='" + db.rs.getString("lfm_id") + "'>" + db.rs.getString("floor_name") + "</option>";
            }
            db.read("SELECT lsm.lsm_id, IFNULL(lsm.section_name,'') section_name FROM library.`lib_section_master` lsm WHERE lsm.status>0");
			String opt2 = "<option selected disabled value=\"\">---Section---</option>";
            while (db.rs.next()) {
            	opt2 += "<option value='" + db.rs.getString("lsm_id") + "'>" + db.rs.getString("section_name") + "</option>";
            }
            db.read("SELECT lpm.lpm_id, IFNULL(lpm.partition_name,'') partition_name FROM library.`lib_partition_master` lpm WHERE lpm.status>0");
			String opt4 = "<option selected disabled value=\"\">Partition</option>";
            while (db.rs.next()) {
            	opt4 += "<option value='" + db.rs.getString("lpm_id") + "'>" + db.rs.getString("partition_name") + "</option>";
            }
            db.read("SELECT MAX(CAST(lrm.access_no AS INT))+1 access_no FROM library.`library_resource_mapping` lrm");
            if(db.rs.next()) {
            	access_no=db.rs.getString("access_no");
            }
            
			query="SELECT rm.resource_id, IFNULL(rm.title,'') title FROM library.resource_master rm WHERE rm.status>0 AND rm.resource_id='"+resource_id+"'";
			db.read(query);
			if(db.rs.next()) {			
				data = "<div class='table-responsive text-wrap' style=\"color: white;font-size: 18px; font-weight: bold\" >Book Title : "+db.rs.getString("title")+" <br><br><div><table class='tbl_new' style=\"width:100%\" border=\"1\">";
				data+="<tr><th>S.No</th><th>Access No</th><th>Library</th><th>Floor</th><th>Section</th><th>Rack</th><th>Partition</th><th>Actions</th></tr>";
				data+="<tr><td></td>";
				data+="<td style=\"background-color:green\" title=\"Access No\" data-column=\"Access No\" >"+	"<input type=\"number\" 	id=\"access_no\" 		style=\"width:100%;background-color:#c5ecd1\" 	placeholder=\"Next No: "+access_no+"\" 	 >" +"</td>";
				data+="<td style=\"background-color:green\" title=\"Library\"   data-column=\"Library\">" + 	"<select id=\"lib_id\" style=\"width:100%;background-color:#c5ecd1\"  >"+ opt+"</select> </td>";
				data+="<td style=\"background-color:green\" title=\"Floor\"   	data-column=\"Floor\">" + 		"<select id=\"lfm_id\" style=\"width:100%;background-color:#c5ecd1\"  onchange=\"loadLibRack()\">"+ opt1+"</select> </td>";
				data+="<td style=\"background-color:green\" title=\"Section\"   data-column=\"Section\">" + 	"<select id=\"lsm_id\" style=\"width:100%;background-color:#c5ecd1\"  >"+ opt2+"</select> </td>";
				data+="<td style=\"background-color:green\" title=\"Rack\"   	data-column=\"Rack\">" + 		"<select id=\"lrm_id\" style=\"width:100%;background-color:#c5ecd1\"  >			</select> </td>";
				data+="<td style=\"background-color:green\" title=\"Partition\" data-column=\"Partition\">" + 	"<select id=\"lpm_id\" style=\"width:100%;background-color:#c5ecd1\"  >"+ opt4+"</select> </td>";
				data+="<td data-column=\"Add\" >" + "<button class=\"btn btn-warning waves-effect\" onclick=\"addResourceAccessNoDetails("+resource_id+");loadResourceAccessNoDetails("+resource_id+")\">Add</button>" + "</td>";
				data+="</tr>"; 
			}
			query="SELECT lrrm.lbm_id, IFNULL(lrrm.resource_id,'') resource_id, IFNULL(lrrm.access_no,'') access_no, IFNULL(lrrm.llm_id,'') llm_id, IFNULL(lm.library_id,'') library_id, IFNULL(lm.library_name,'') library_name, IFNULL(lfm.lfm_id,'') lfm_id, IFNULL(lfm.floor_name,'') floor_name, IFNULL(lsm.lsm_id,'') lsm_id, IFNULL(lsm.section_name,'') section_name, IFNULL(lrm.lrm_id,'') lrm_id, IFNULL(lrm.rack_name,'') rack_name, IFNULL(lpm.lpm_id,'') lpm_id, IFNULL(lpm.partition_name,'') partition_name, IFNULL(lrrm.status,'') STATUS FROM library.`library_resource_mapping` lrrm LEFT JOIN library.`lib_location_mapping` llm ON llm.llm_id=lrrm.llm_id AND llm.status>0 INNER JOIN library.`library_master` lm ON lm.library_id=llm.lib_id AND lm.status>0 INNER JOIN library.`lib_floor_master` lfm ON lfm.lfm_id=llm.lfm_id AND lfm.status>0 INNER JOIN library.`lib_section_master` lsm ON lsm.lsm_id=llm.lsm_id AND lsm.status>0 INNER JOIN library.`lib_rack_master` lrm ON lrm.lrm_id=llm.lsm_id AND lsm.status>0 INNER JOIN library.`lib_partition_master` lpm ON lpm.lpm_id=llm.lpm_id AND lpm.status>0 WHERE lrrm.status>0 AND lrrm.resource_id='"+resource_id+"'";
			db.read(query);
			while (db.rs.next()) {
				data+="<tr>";
				data+="<td  data-column=\"S.No\">"+ i++ +"</td>";
				data+="<td  data-column=\"Access No\">"+ db.rs.getString("access_no") +"</td>";
				data+="<td  data-column=\"Library\">"+ db.rs.getString("library_name") +"</td>";
				data+="<td  data-column=\"Floor\">"+ db.rs.getString("floor_name") +"</td>";
				data+="<td  data-column=\"Section\">"+ db.rs.getString("section_name") +"</td>";
				data+="<td  data-column=\"Rack\">"+ db.rs.getString("rack_name") +"</td>";
				data+="<td  data-column=\"Partition\">"+ db.rs.getString("partition_name") +"</td>";
				data+="<td data-column=\"Delete\" >" + "<button class=\"btn btn-danger waves-effect\" onclick=\"deleteResourceAccessNoDetails("+db.rs.getString("lbm_id")+");loadResourceAccessNoDetails("+resource_id+")\">X</button>";
				data+="</tr>"; 
			}
			data +="</table></div></div>";
			return data + "<!--"+query+"-->";
		} catch (Exception e) {
			return "<b style=\"color:red\" title=\""+e.toString()+"\">Something Went Wrong</b>";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {

			}
		}
	}

	public String addResourceAccessNoDetails(String resource_id, String access_no, String lib_id, String lfm_id, String lsm_id, String lrm_id, String lpm_id, String ss_id) {
		String data = "", query = "", llm_id=""; String debuginfo=""; 
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			db.read("SELECT llm.llm_id FROM library.`lib_location_mapping` llm WHERE llm.lib_id='"+lib_id+"' AND llm.lfm_id='"+lfm_id+"' AND llm.lsm_id='"+lsm_id+"' AND llm.lrm_id='"+lrm_id+"' AND llm.lpm_id='"+lpm_id+"' AND llm.status>0");
			if(db.rs.next()) {
				llm_id=db.rs.getString("llm_id");
			}
			else {
				llm_id=db.insertAndGetAutoGenId("INSERT INTO library.`lib_location_mapping` (`llm_id`,`lib_id`,`lfm_id`,`lsm_id`,`lrm_id`,`lpm_id`,`order_no`,`status`,`inserted_by`,`inserted_date`,`updated_by`,`updated_date`) VALUES (NULL,'"+lib_id+"','"+lfm_id+"','"+lsm_id+"','"+lrm_id+"','"+lpm_id+"','1',1,'"+ss_id+"',NOW(),'"+ss_id+"',NOW()) ON DUPLICATE KEY UPDATE STATUS=1,updated_by='"+ss_id+"',updated_date=NOW()");
			}
			query="INSERT INTO library.`library_resource_mapping` (`lbm_id`,`library_id`,`resource_id`,`access_no`,`llm_id`,`status`,`inserted_by`,`inserted_date`,`updated_by`,`updated_date`) VALUES (NULL,'"+lib_id+"','"+resource_id+"','"+access_no+"','"+llm_id+"',1,'"+ss_id+"',NOW(),'"+ss_id+"',NOW()) ON DUPLICATE KEY UPDATE STATUS=1, llm_id=VALUES(llm_id),updated_by='"+ss_id+"',updated_date=NOW()";
			db.update(query);
			return query;
		} catch (Exception e) {
			return "<b style=\"color:red\" title=\""+e.toString()+"\">Something Went Wrong1</b>";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {

			}
		}
	}
	
	public String loadLibRack(String lfm_id) {
		String data = "", query = "", opt=""; String debuginfo=""; 
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			query="SELECT lrm.lrm_id, IFNULL(lrm.rack_name,'') rack_name FROM library.`lib_rack_master` lrm WHERE lrm.status>0 AND lrm.lfm_id='"+lfm_id+"'";
			db.read(query);
			while (db.rs.next()) {
            	opt += "<option value='" + db.rs.getString("lrm_id") + "'>" + db.rs.getString("rack_name") + "</option>";
            }
			return opt;
		} catch (Exception e) {
			return "<b style=\"color:red\" title=\""+e.toString()+"\">Something Went Wrong1</b>";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {

			}
		}
	}
	
	public String deleteResourceAccessNoDetails(String lbm_id, String ss_id) {
		String data = "", query = ""; String debuginfo=""; 
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			query="UPDATE library.`library_resource_mapping` SET status='0', updated_by='"+ss_id+"', updated_date=NOW() WHERE lbm_id='"+lbm_id+"'";
			db.update(query);
			return query;
		} catch (Exception e) {
			return "<b style=\"color:red\" title=\""+e.toString()+"\">Something Went Wrong1</b>";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {

			}
		}
	}
}
	
	
	
	
