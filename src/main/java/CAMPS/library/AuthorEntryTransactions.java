package CAMPS.library;

import java.sql.SQLException;
import CAMPS.Connect.DBConnect;
import jakarta.servlet.http.HttpSession;

public class AuthorEntryTransactions { 
	
	public String loadAuthorsCount(String search_data) { 
		String data = "", query = "", count=""; String fix_id="";  int i=1;
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
            query="SELECT CEIL(count(*)/50) count FROM library.`author_master` am WHERE am.`status`>0 AND am.`author_name` LIKE '%"+search_data+"%' ORDER BY am.author_id";
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

	public String loadAuthors(String search_data, String page_num) { 
		String data = "", query = ""; String fix_id="";  int i=1;
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
            query="SELECT am.`author_id`, IFNULL(am.`author_name`,'') author_name, IFNULL(am.`email`,'') email, IFNULL(am.`status`,'') status FROM library.`author_master` am WHERE am.`status`>0 AND am.`author_name` LIKE '%"+search_data+"%' ORDER BY am.`author_id` DESC  limit "+(Integer.parseInt(page_num)-1)*50+",50";
            db.read(query);
            data = "<div class='table-responsive text-wrap' ><div><table class='tbl_new' style=\"width:100%\" border=\"1\">";
			data+="<tr><th style=\"width:40px\">ID</th><th>Author Name</th><th>Email</th><th style=\"width:64px\">Actions</th></tr>";

        	while(db.rs.next()) {
        		if (db.rs.getString("status").equalsIgnoreCase("1")) {
					data+="<tr>";
					data+="<td  data-column=\"S.No\">"+ db.rs.getString("author_id") +"</td>";
					data+="<td title=\"Author Name\" 	data-column=\"Author Name\" >"+		"<input type=\"text\" 	id=\"author_name"+db.rs.getString("author_id")+"\"  style=\"width:100%\" 	value=\""+db.rs.getString("author_name")+"\" 	onchange=\"updateAuthors('author_name',"+db.rs.getString("author_id")+");	this.style.background = '#f4e88e'\" >"		+"</td>";
					data+="<td title=\"Email\" 			data-column=\"Email\" >"+			"<input type=\"text\" 	id=\"email"+db.rs.getString("author_id")+"\" 		style=\"width:100%\" 	value=\""+db.rs.getString("email")+"\" 			onchange=\"updateAuthors('email',"+db.rs.getString("author_id")+");	this.style.background = '#f4e88e'\" >"		+"</td>";
					data+="<td data-column=\"Actions\" >" + "<button style=\"padding:3px 10px\" class=\"btn btn-danger waves-effect\" onclick=\"deleteAuthors("+db.rs.getString("author_id")+")\">X</button>";
					data+="<button style=\"padding:3px 10px\" class=\"btn btn-success waves-effect\" onclick=\"freezeAuthors("+ db.rs.getString("author_id")+")\">Fz</button>" + "</td>";
					data+="</tr>"; 
				}
				else if (db.rs.getString("status").equalsIgnoreCase("2")) {
					data+="<tr>";
					data+="<td  data-column=\"S.No\">"+ db.rs.getString("author_id") +"</td>";
					data+="<td title=\"Author Name\" data-column=\"Author Name\" >"+	"<input readonly type=\"text\" 	 id=\"author_name"+db.rs.getString("author_id")+"\" style=\"width:100%;border:none;background:transparent\" 	value=\""+db.rs.getString("author_name")+"\" 	 >"		+"</td>";
					data+="<td title=\"Email\" 		 data-column=\"Email\" >"+			"<input readonly type=\"text\" 	 id=\"email"+db.rs.getString("author_id")+"\" style=\"width:100%;border:none;background:transparent\" 	value=\""+db.rs.getString("email")+"\" 	 >"		+"</td>";
					data+="<td></td></tr>";
				}
    		}
        	data +="</table></div></div>";

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
		
	public String loadAddAuthors() { 
		String data = "", query = ""; String fix_id="";  int i=1;
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			
			data = "<div class='table-responsive text-wrap' ><div><table class='tbl_new'  border=\"1\" style=\"margin-left:auto;margin-right:auto;\">";
			data+="<tr><th>Author Name</th><th>Actions</th></tr>";
			data+="<tr>";
			data+="<td style=\"background-color:green\" title=\"Author Name\" 	data-column=\"Author Name\" >" + 	"<input type=\"text\" id=\"author_name\" 	style=\"width:300px;background-color:#c5ecd1\" placeholder=\"Author Name\">" 	+"</td>";
			
			data+="<td data-column=\"Add\" >" + "<button class=\"btn btn-warning waves-effect\" onclick=\"addAuthors()\">Add</button>" + "</td>";
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
	
	public String addAuthors(String author_name, String ss_id) {
		String data = "", query = ""; String debuginfo=""; String p_id="";
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			query="INSERT INTO library.`author_master` (`author_id`,`author_name`,`email`,`status`,`inserted_by`,`inserted_date`,`updated_by`,`updated_date`) VALUES (NULL,'"+author_name+"',NULL,1,'"+ss_id+"',NOW(),'"+ss_id+"',NOW())"; 	
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
	
	public String updateAuthors(String author_id, String fname, String fvalue, String ss_id) {
		String data = "", query = ""; String debuginfo=""; 
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			query="UPDATE library.`author_master` SET "+fname+"='"+fvalue+"', updated_by='"+ss_id+"', updated_date=NOW() WHERE author_id='"+author_id+"'";
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
	
	public String freezeAuthors(String author_id, String ss_id) {
		String data = "", query = ""; String debuginfo=""; 
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			query="UPDATE library.`author_master` SET status='2', updated_by='"+ss_id+"', updated_date=NOW() WHERE author_id='"+author_id+"'";
			db.update(query);
			return ("Freezed Successfully...");
		} catch (Exception e) {
			return "<b style=\"color:red\" title=\""+e.toString()+"\">Something Went Wrong1</b>";
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {

			}
		}
	}
	
	public String deleteAuthors(String author_id, String ss_id) {
		String data = "", query = ""; String debuginfo=""; 
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			query="UPDATE library.`author_master` SET status='0', updated_by='"+ss_id+"', updated_date=NOW() WHERE author_id='"+author_id+"'";
			db.update(query);
			return ("Deleted Successfully...");
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
	
	
	
	
