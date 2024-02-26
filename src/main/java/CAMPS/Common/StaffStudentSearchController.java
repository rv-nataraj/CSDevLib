package CAMPS.Common;

import CAMPS.Connect.DBConnect;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "staff_student_search_controller",description = "ss_search_controller",urlPatterns = {"/JSP/Common/StaffStudentSearchController.do"} )

public class StaffStudentSearchController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        DBConnect db = new DBConnect();
        PrintWriter out = response.getWriter();
        try {
            db.getConnection();
            if (request.getParameter("option") != null && request.getParameter("option").equalsIgnoreCase("loadSearchResults")) {
            	int i=1;
            	String query="SELECT 'Staff' sstype, sm.staff_id,CONCAT('<a href=\"#\" data-dismiss=\"modal\" onclick=\"\">',TRIM(CONCAT(IFNULL(sm.first_name,''),' ',IFNULL(sm.middle_name,''),' ',IFNULL(sm.last_name,''),' ')),'</a>') staff_name,'Staff',md.dept_name FROM camps.staff_master sm INNER JOIN camps.master_department md ON sm.department_id=md.department_id  WHERE sm.first_name LIKE '%"+request.getParameter("search_string")+"%' or sm.staff_id='"+request.getParameter("search_string")+"'  UNION SELECT 'Student' sstype, sm.student_id,CONCAT('<a href=\"#\" data-dismiss=\"modal\" onclick=\"\">',TRIM(CONCAT(IFNULL(sm.first_name,''),' ',IFNULL(sm.middle_name,''),' ',IFNULL(sm.last_name,''),' ')),'</a>') staff_name,'Student',CONCAT(mp.programme_name,' ',mb.branch_name) FROM camps.student_master sm INNER JOIN camps.student_admission_master sam ON sam.student_id=sm.student_id INNER JOIN camps.`master_branch` mb ON mb.branch_id=sam.branch_id INNER JOIN camps.`master_programme` mp ON mp.programme_id=mb.programme_id WHERE sm.first_name LIKE '%"+request.getParameter("search_string")+"%' or sm.student_id='"+request.getParameter("search_string")+"' LIMIT 50";
				db.read(query);	
				String data = "<div class=\"table-responsive text-nowrap\"><table class='table table-bordered table-striped' width='100%'>";
				data += "<thead><tr><th>S.No</th><th>Type</th><th>ID</th><th>Name</th><th></th><th>Dept / Programme</th></tr></thead>";
				while (db.rs.next()) {
					data += "<tr>";
					data += "<td>"+ i++ +"</td>";
					data += "<td>"+ db.rs.getString("sstype") +"</td>";
					data += "<td>"+db.rs.getString("staff_id")+"</td>";
					data += "<td>"+db.rs.getString("staff_name")+"</td>";
					data += "<td><button onclick=\"mapSS()\">Add</button></td>";
					data += "<td>"+db.rs.getString("dept_name")+"</td>";
					
					data += "</tr>";
				}
				data += "</table></div>";
				if (i==1) data = "No Matching Records Found";
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

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
