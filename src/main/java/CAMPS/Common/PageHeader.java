package CAMPS.Common;

import CAMPS.Connect.DBConnect;
import java.sql.SQLException;


public class PageHeader {  
	
	public String getLogoImageURL() {
		String data = "";
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			db.read("SELECT ifnull(org_logo_cdn,'https://cdn.campusstack.in/clients/csc/logo.png') logoimg FROM camps.master_organization mo");
        	if (db.rs.next())
        		data+=db.rs.getString("logoimg");
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
	
	public String getUserPhoto(String utype,String ss_id) {
		String data = "";
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			if (utype.equalsIgnoreCase("student")) {
		        db.read("SELECT TO_BASE64(sp.photo) photo FROM camps.student_photo sp WHERE sp.student_id='" + ss_id + "' AND sp.status=1 ");
		    } else {
		        db.read("SELECT TO_BASE64(sp.photo) photo FROM camps.staff_photo sp WHERE sp.staff_id='" + ss_id + "' AND sp.status=1 ");
		    }
			if (db.rs.next()) {
				data="data:image/jpeg;base64,"+db.rs.getString("photo");
			}
			else
				data="https://cdn.campusstack.in/assets/images/user.png";
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
	
	
	
	
	
	
	
	
	
	
		

}
