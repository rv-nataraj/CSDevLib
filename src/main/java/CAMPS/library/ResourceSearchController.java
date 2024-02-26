package CAMPS.library;


import CAMPS.Connect.DBConnect;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "resource_search_controller_19234",description = "resource_search_controller_19234",urlPatterns = {"/JSP/library/ResourceSearchController.do"} )

public class ResourceSearchController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        DBConnect db = new DBConnect();
        PrintWriter out = response.getWriter();
        try {
            db.getConnection(); 

            if (request.getParameter("option") != null && request.getParameter("option").equalsIgnoreCase("load_resource")) {
                String att = "", yr = "", res = "", rm = "";
                String[] data1 = request.getParameter("data").split(",");
                String[] field_id = request.getParameter("field_id").split(",");
                for (int i = 0; i < data1.length; i++) {
                    for (int j = 0; j < field_id.length; j++) {
                        if (!field_id[j].equalsIgnoreCase("") && !data1[i].equalsIgnoreCase("")) {
                            if (i == j && field_id[j].equalsIgnoreCase("author_name") && !data1[i].equalsIgnoreCase("")) {
                                res = " " + request.getParameter("condition") + " am." + field_id[j] + " LIKE '%" + data1[i] + "%'  ";
                                att += res;
                            } else if (i == j && !field_id[j].equalsIgnoreCase("") && !data1[i].equalsIgnoreCase("")) {
                                res = " " + request.getParameter("condition") + " rm." + field_id[j] + " LIKE '%" + data1[i] + "%'  ";
                                att += res;
                            }
                        }
                    }
                }
                if (request.getParameter("field_id1").equalsIgnoreCase("author_name")) {
                    rm = "am";
                } else {
                    rm = "rm";
                }
                if (!request.getParameter("from_year").equalsIgnoreCase("") && !request.getParameter("to_year").equalsIgnoreCase("")) {
                    yr += " and (rm.publication_year IS NULL OR rm.publication_year between '" + request.getParameter("from_year") + "' and '" + request.getParameter("to_year") + "') ";
                }
                db.read("SELECT concat(rm.resource_id,'-',lrm.access_no) access_no,ifnull(rm.title,'') title,ifnull(rm.sub_title,'') sub_title,ifnull(GROUP_CONCAT(am.author_name),'') author_name,ifnull(concat(lm.library_name,'/',lfm.floor_name,'/',lsm.section_name,'/',lram.rack_name,'/',lpm.partition_name),'') location,ifnull(pm.publisher_name,'') publisher_name,ras.status_name,ifnull(rm.publication_year,'') publication_year FROM library.library_resource_mapping lrm LEFT JOIN library.resource_master rm ON lrm.resource_id = rm.resource_id and rm.cat_id=" + request.getParameter("cat_id") + "  LEFT JOIN library.lib_location_mapping llm ON llm.llm_id=lrm.llm_id LEFT JOIN library.lib_floor_master lfm ON lfm.lfm_id = llm.lfm_id AND lfm.status > 0 LEFT JOIN library.lib_section_master lsm ON lsm.lsm_id = llm.lsm_id AND lsm.status > 0 LEFT JOIN library.lib_rack_master lram ON lram.lrm_id = llm.lrm_id AND lrm.status > 0 LEFT JOIN library.lib_partition_master lpm ON lpm.lpm_id = llm.lpm_id AND lpm.status > 0 LEFT JOIN library.resource_author_mapping ram ON ram.resource_id=rm.resource_id LEFT JOIN library.author_master am ON am.author_id=ram.author_id LEFT JOIN library.library_master lm ON lm.library_id=lrm.library_id LEFT JOIN library.resource_access_status ras ON ras.ras_id=lrm.status left join library.publisher_master pm on pm.publisher_id=rm.publisher_id WHERE lrm.status NOT IN (0) " + yr + " and " + rm + "." + request.getParameter("field_id1") + " LIKE '%" + request.getParameter("data1") + "%' " + att + "  GROUP BY lrm.access_no ORDER BY lrm.resource_id,lrm.access_no,rm.publication_year");
                String data = "<table  class=\"table tbl_new  table-bordered table-striped table-hover dataTable js-exportable\"><thead><tr><td>Res.Id/Access No</td><td>Title</td><td>Sub Title</td><td>Author</td><td>Publisher</td><td>Publication Year</td><td>Location</td><td>Status</td></tr></thead><tfoot><tr><td>Res.Id/Access No</td><td>Title</td><td>Sub Title</td><td>Author</td><td>Publisher</td><td>Publication Year</td><td>Location</td><td>Status</td></tr></tfoot><tbody>";
                while (db.rs.next()) {
                    data += "<tr ><td  style=\"text-align:center;\" data-column='Resource ID/Access No'>" + db.rs.getString("access_no") + "</td><td data-column='Title'>" + db.rs.getString("title") + "</td><td data-column='Sub Title'>" + db.rs.getString("sub_title") + "</td><td data-column='Author'>" + db.rs.getString("author_name") + "</td><td data-column='Publisher'>" + db.rs.getString("publisher_name") + "</td><td style=\"text-align:center;width:5%\" data-column='Publication Year'>" + db.rs.getString("publication_year") + "</td><td data-column='Location'>" + db.rs.getString("location") + "</td><td data-column='Status'>" + db.rs.getString("status_name") + "</td></tr>";
                }
                data += "</tbody></table>";
                out.print(data);

            }      
            if (request.getParameter("option") != null && request.getParameter("option").equalsIgnoreCase("load_resource_title")) {
                String data = "";
                out.print("<option value=\"\" >-- Select--</option>");
                //    db.read("SELECT CONCAT( '<option value=\"', fa.fa_attribute, '\">', fa.fa_name, '</option>' ) opt FROM camps.form_attribute fa WHERE fa.fm_id=21 AND fa.fa_id NOT IN (147,148,149,152,151,153,154,157,155,194,200,156) ORDER BY fa.order_no and fa.status>0");
                db.read("SELECT CONCAT( '<option value=\"', fa.fa_attribute, '\">', fa.fa_name, '</option>' ) opt FROM camps.form_attribute fa WHERE fa.fm_id IN (21,18) AND fa.fa_id NOT IN (147,148,149,152,151,153,154,157,155,194,200,156,138) ORDER BY fa.order_no AND fa.status>0");
                while (db.rs.next()) {
                    out.print(db.rs.getString("opt"));
                }
                out.print(data);
            }
        } catch (Exception e) {
            out.print(e);
        } finally {
            db.closeConnection();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            //processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ResourceSearchController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ResourceSearchController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
