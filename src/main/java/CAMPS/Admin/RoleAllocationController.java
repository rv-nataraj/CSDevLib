package CAMPS.Admin;

import CAMPS.Connect.DBConnect;
import CAMPS.Common.report_process;
import java.util.ArrayList;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
@WebServlet(name = "role_allocation_controller",description = "role_allocation_controller",urlPatterns = {"/JSP/Admin/RoleAllocationController.do"} )

public class RoleAllocationController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        DBConnect db = new DBConnect();
        try (PrintWriter out = response.getWriter()) {
            try {
                db.getConnection();
                HttpSession session = request.getSession();
                if (session.getAttribute("role_name").toString().contains("Role Management")) {
	                if (request.getParameter("option") != null && request.getParameter("option").equalsIgnoreCase("role_select")) {
	                    out.print("<form action='RoleAllocationController.do' method='post'><ul id=\"listSelection\">");
	                    db.read("SELECT rm.role_id,rm.role_name,rm.display_name,IFNULL(rm.description,'')description,IF(um.staff_id IS NOT NULL,'Checked','') checked_data FROM admin.role_master rm LEFT JOIN admin.user_master um ON FIND_IN_SET(rm.role_id,um.roles) AND um.staff_id='" + request.getParameter("staff_id") + "' where rm.status>0 order by rm.display_name");
	                    while (db.rs.next()) {
	                        out.print("<li><strong>" + db.rs.getString("display_name") + "</strong></br><span class=\"fcbkitem_text\">" + db.rs.getString("role_name") + "</span><input type=\"checkbox\" style=\"visibility: hidden\" name=\"role\" id=\"role_" + db.rs.getString("role_id") + "\" value=\"" + db.rs.getString("role_id") + "\" " + db.rs.getString("checked_data") + " /></li>");
	                    }
	                    out.print("</ul><center><button class=\"btn btn-primary waves-effect\" type=\"submit\"  name=\"add_role\" id=\"add_role\" value='Save'>Save</button><script>function add_role(){alert($('[name=role]').val());}</script> </center><input type='hidden' name='staff_id' value='" + request.getParameter("staff_id") + "' /></form>");
	                } else if (request.getParameter("add_role") != null && request.getParameter("add_role").equalsIgnoreCase("Save")) {
	                    out.print(String.join(",", request.getParameterValues("role")));
	                    db.update("UPDATE admin.user_master um SET um.roles='" + String.join(",", request.getParameterValues("role")) + "',um.updated_by='" + session.getAttribute("user_id") + "',um.updated_date=NOW() WHERE um.staff_id='" + request.getParameter("staff_id") + "'");
	                    response.sendRedirect("./role_allocation.jsp?am_c=1");
	                } else if (request.getParameter("option") != null && request.getParameter("option").equalsIgnoreCase("loadfilter_det")) {
						report_process rs = new report_process();
						ArrayList<String> attribute = new ArrayList<>();
						String att = "True ";
						if (request.getParameter("staff_id") != null && !request.getParameter("staff_id").equals("")) {
							att += "AND staff_id like '%" + request.getParameter("staff_id") + "%' ";
						}
						if (request.getParameter("staff_name") != null && !request.getParameter("staff_name").equals("")) {
							att += "AND staff_name like '%" + request.getParameter("staff_name") + "%' ";
						}
						if (request.getParameter("dept_id") != null && !request.getParameter("dept_id").equals("") && !request.getParameter("dept_id").equals("null")) {
							att += "AND department_id in  (" + request.getParameter("dept_id") + ") ";
						}
						if (request.getParameter("cat_id") != null && !request.getParameter("cat_id").equals("") && !request.getParameter("cat_id").equals("null")) {
							att += "AND sc_id in (" + request.getParameter("cat_id") + ")";
						}
						attribute.add(att);
						out.print(rs.report_v1("9", attribute));
					} else if (request.getParameter("option") != null && request.getParameter("option").equalsIgnoreCase("loadpersonal_pdet")) {
						report_process rp = new report_process();
						ArrayList<String> attribute = new ArrayList<>();
						attribute.add(request.getParameter("staff_id"));
						out.print(rp.report_v1("37", attribute));
					}
	                
                }
            } catch (Exception e) {
                out.print(e);
            } finally {
                try {
                    db.closeConnection();
                } catch (SQLException ex) {

                }
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
