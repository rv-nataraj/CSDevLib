package CAMPS.curriculum;

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
@WebServlet(name = "curriculum_controller",description = "curriculum_controller",urlPatterns = {"/JSP/curriculum/CurriculumController.do"} )

public class CurriculumController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        DBConnect db = new DBConnect();
        PrintWriter out = response.getWriter();
        try {
            db.getConnection();
                        
            if (request.getParameter("option") != null && request.getParameter("option").equalsIgnoreCase("loadRegulationCourses")) {
            	CurriculumTransactions ct=new CurriculumTransactions();
            	if (session.getAttribute("role_name").toString().contains("Curriculum Incharge")) {
            		out.print(ct.loadRegulationCourses(request.getParameter("regulation_id").replaceAll("[^0-9]", ""),"Edit"));
            	}
            	else
            		out.print(ct.loadRegulationCourses(request.getParameter("regulation_id").replaceAll("[^0-9]", ""),"View"));
            }
            else if (session.getAttribute("role_name").toString().contains("Curriculum Incharge")) {
	            if (request.getParameter("option") != null && request.getParameter("option").equalsIgnoreCase("insertCourse")) {
	            	CurriculumTransactions ct=new CurriculumTransactions();
	            	out.print(ct.insertCourse(request.getParameter("regulation_id").replaceAll("[^0-9]", ""),request.getParameter("dc_" + request.getParameter("row_id")), request.getParameter("sc_" + request.getParameter("row_id")), request.getParameter("sn_" + request.getParameter("row_id")).substring(0,Math.min(request.getParameter("sn_" + request.getParameter("row_id")).length(), 120)), request.getParameter("l_" + request.getParameter("row_id")), request.getParameter("t_" + request.getParameter("row_id")), request.getParameter("p_" + request.getParameter("row_id")), request.getParameter("c_" + request.getParameter("row_id")), request.getParameter("iim_" + request.getParameter("row_id")), request.getParameter("mim_" + request.getParameter("row_id")), request.getParameter("lem_" + request.getParameter("row_id")), request.getParameter("mem_" + request.getParameter("row_id")),request.getParameter("sem_" + request.getParameter("row_id")),request.getParameter("st_" + request.getParameter("row_id")),request.getParameter("o_" + request.getParameter("row_id")), session.getAttribute("ss_id").toString()));
	            }
	            else if (request.getParameter("option") != null && request.getParameter("option").equalsIgnoreCase("deleteCourse")) {
	            	CurriculumTransactions ct=new CurriculumTransactions();
	            	out.print(ct.deleteCourse(request.getParameter("regulation_id").replaceAll("[^0-9]", ""),request.getParameter("subject_id").replaceAll("[^0-9]", ""),session.getAttribute("ss_id").toString()));
	            }
	            else if (request.getParameter("option") != null && request.getParameter("option").equalsIgnoreCase("freezeCourse")) {
	            	CurriculumTransactions ct=new CurriculumTransactions();
	            	out.print(ct.freezeCourse(request.getParameter("subject_id").replaceAll("[^0-9]", ""),session.getAttribute("ss_id").toString()));
	            }
	            else if (request.getParameter("option") != null && request.getParameter("option").equalsIgnoreCase("insertElectiveSlot")) {
	            	CurriculumTransactions ct=new CurriculumTransactions();
	            	out.print(ct.insertElectiveSlot(request.getParameter("regulation_id").replaceAll("[^0-9]", ""),request.getParameter("sn_" + request.getParameter("row_id")).substring(0,Math.min(request.getParameter("sn_" + request.getParameter("row_id")).length(), 120)), request.getParameter("st_" + request.getParameter("row_id")),request.getParameter("sem_"+request.getParameter("row_id")),request.getParameter("c_" + request.getParameter("row_id")),request.getParameter("o_" + request.getParameter("row_id")), session.getAttribute("ss_id").toString()));
	            }
	            else if (request.getParameter("option") != null && request.getParameter("option").equalsIgnoreCase("deleteElectiveSlot")) {
	            	CurriculumTransactions ct=new CurriculumTransactions();
	            	out.print(ct.deleteElectiveSlot(request.getParameter("regulation_id").replaceAll("[^0-9]", ""),request.getParameter("elective_id").replaceAll("[^0-9]", ""),session.getAttribute("ss_id").toString()));
	            }
	            else if (request.getParameter("option") != null && request.getParameter("option").equalsIgnoreCase("freezeElectiveSlot")) {
	            	CurriculumTransactions ct=new CurriculumTransactions();
	            	out.print(ct.freezeElectiveSlot(request.getParameter("elective_id").replaceAll("[^0-9]", ""),session.getAttribute("ss_id").toString()));
	            }   
	            else if (request.getParameter("option") != null && request.getParameter("option").equalsIgnoreCase("updateCourseAttributes")) {
	            	CurriculumTransactions ct=new CurriculumTransactions();
	            	out.print(ct.updateCourseAttributes(request.getParameter("regulation_id").replaceAll("[^0-9]", ""),request.getParameter("subject_id").replaceAll("[^0-9]", ""), request.getParameter("field_id").replaceAll("[^0-9]", ""), request.getParameter("field_value").substring(0,Math.min(request.getParameter("field_value").length(), 120)), session.getAttribute("ss_id").toString()));	
	            }
	            else if (request.getParameter("option") != null && request.getParameter("option").equalsIgnoreCase("updateElectiveAttributes")) {
	            	CurriculumTransactions ct=new CurriculumTransactions();
	            	out.print(ct.updateElectiveAttributes(request.getParameter("regulation_id").replaceAll("[^0-9]", ""),request.getParameter("subject_id").replaceAll("[^0-9]", ""), request.getParameter("field_id").replaceAll("[^0-9]", ""), request.getParameter("field_value").substring(0,Math.min(request.getParameter("field_value").length(), 120)), session.getAttribute("ss_id").toString()));	
	            }
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
            Logger.getLogger(CurriculumController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(CurriculumController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
