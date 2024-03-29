package CAMPS.Admin;

import CAMPS.Connect.DBConnect;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class menu {

    DBConnect db = new DBConnect();
    String menu_string  = "";

    public String menu_generator (int parent, String role, String hostname) {
        try {  
            db.getConnection();
            db.read("SELECT ifnull(rm.bg,'') bgstyle, ifnull(rm.extraparameters,0) extraparameters,rm.sortorder,rm.resource_id,rm.label,IFNULL(rm.img,'link')img,CASE WHEN rm.link='#' THEN '#' WHEN (rm.extraParameters='1') THEN rm.link ELSE CONCAT('"+hostname+"',rm.link) END link,COUNT(rm1.resource_id) "
                    + "COUNT FROM admin.resource_master rm  "
                    + "INNER JOIN admin.role_resource_mapping rrm ON FIND_IN_SET(rm.resource_id,rrm.resources) AND rrm.role_id IN ("+role+") AND rm.parent_id='"+parent+"' "
                    + "LEFT JOIN (admin.resource_master rm1  INNER JOIN admin.role_resource_mapping rrm1 ON FIND_IN_SET(rm1.resource_id,rrm1.resources) AND rrm1.role_id IN ("+role+")) ON rm.resource_id=rm1.parent_id "
                    + " WHERE ifnull(rm.extraParameters,'0')<>'2' and rm.rstatus=1 GROUP BY rm.resource_id ORDER BY rm.sortorder");
            while (db.rs.next()) {
                if (db.rs.getInt("count") == 0) {
                    menu_string += "<li "+db.rs.getString("bgstyle")+"  id='menu_" + db.rs.getString("resource_id")+ "'><a "+((db.rs.getString("extraparameters").equalsIgnoreCase("3") || db.rs.getString("extraparameters").equalsIgnoreCase("1"))?"target='_blank'":"")+" href='" + db.rs.getString("link")+ "' title='" + db.rs.getString("resource_id")+"-"+db.rs.getString("sortorder") + "' ><i class=\"material-icons\">" + db.rs.getString("img") + "</i><span>" + db.rs.getString("label") + "</span> </a></li>";
                } else {     
                     menu a = new menu();
                    menu_string += "<li "+db.rs.getString("bgstyle")+" id='menu_" + db.rs.getString("resource_id")+ "'><a "+((db.rs.getString("extraparameters").equalsIgnoreCase("3") || db.rs.getString("extraparameters").equalsIgnoreCase("1"))?"target='_blank'":"")+" href='" + db.rs.getString("link") + "' class=\"menu-toggle\" title='" + db.rs.getString("resource_id")+"-"+db.rs.getString("sortorder") + "' ><i class=\"material-icons\">" + db.rs.getString("img") + "</i><span>" + db.rs.getString("label") + " </span></a><ul class=\"ml-menu\">" + a.menu_generator(db.rs.getInt("resource_id"), role, hostname) + "</ul></li>";
                }
            }         
        } catch (Exception e) {  
        	return e.toString();
        	//return "<p title=\" "+ e.toString() + " \">Something went wrong </p>";
        }
        finally{
            try {
                db.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return menu_string;
    }
}