package CAMPS.Common;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;


import CAMPS.Connect.DBConnect;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
@WebServlet(name = "controller_common",description = "controller_common",urlPatterns = {"/JSP/Common/ControllerCommon.do"} )

public class ControllerCommon extends HttpServlet {

	private static final long serialVersionUID = 1L;
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        DBConnect db = new DBConnect();
        PrintWriter out = response.getWriter();
        try {
            db.getConnection();
            if (request.getParameter("option") != null && request.getParameter("option").equalsIgnoreCase("pin_this_resource")) {
            	db.insert("INSERT ignore INTO camps.`pinned_resources` (staff_id,resource_id,`rstatus`) values ('"+session.getAttribute("ss_id").toString()+"','"+request.getParameter("resource_id").replaceAll("[^0-9]", "")+"','1')");
            	
            } 
            else if (request.getParameter("option") != null && request.getParameter("option").equalsIgnoreCase("unpin_this_resource")) {
            	db.insert("update camps.`pinned_resources` pr set rstatus=0 where pr.staff_id= '"+session.getAttribute("ss_id").toString()+"' and pr.resource_id='"+request.getParameter("resource_id").replaceAll("[^0-9]", "")+"' and pr.rstatus='1'");
            } 
            else if (request.getParameter("option") != null && request.getParameter("option").equalsIgnoreCase("load_student_ids")) {
            	Model_Selects ms=new Model_Selects();
            	out.print(ms.loadStudentIDs(request.getParameter("term_id")));

            } 
            else if (request.getParameter("option") != null && request.getParameter("option").equalsIgnoreCase("showResourceInfo")) {
            	db.read("select ifnull(help_text,'No Details Found for this resource') help_text from admin.resource_help_text rht where rht.resource_id="+request.getParameter("resource_id").replaceAll("[^0-9]", ""));
            	if (db.rs.next()) {
            		out.print(db.rs.getString("help_text"));
            	}
            	else
            		out.print("Resource Information Not Available");
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
            Logger.getLogger(ControllerCommon.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ControllerCommon.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
