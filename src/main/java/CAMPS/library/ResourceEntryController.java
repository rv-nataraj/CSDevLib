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

@WebServlet(name = "resource_entry_controller_1984",description = "resource_entry_controller_1984",urlPatterns = {"/JSP/library/ResourceEntryController.do"} )

public class ResourceEntryController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        DBConnect db = new DBConnect();
        PrintWriter out = response.getWriter();
        try {
            db.getConnection(); 

            if (request.getParameter("option") != null && request.getParameter("option").equalsIgnoreCase("searchPublisher")) {
				ResourceEntryTransactions pt=new ResourceEntryTransactions();
                out.print(pt.searchPublisher(request.getParameter("search_string"),request.getParameter("resource_id").replaceAll("[^0-9]", "")));
            }
            
            else if (request.getParameter("option") != null && request.getParameter("option").equalsIgnoreCase("loadResourceDataUpdateUI")) {
                ResourceEntryTransactions pt=new ResourceEntryTransactions();
                out.print(pt.loadResourceDataUpdateUI(request.getParameter("search_data"),request.getParameter("page_num").replaceAll("[^0-9]", "")));
			}
	        
            else if (request.getParameter("option") != null && request.getParameter("option").equalsIgnoreCase("loadResourceCount")) {
                ResourceEntryTransactions pt=new ResourceEntryTransactions();
                out.print(pt.loadResourceCount(request.getParameter("search_data")));
            }
            
	        else if (request.getParameter("option") != null && request.getParameter("option").equalsIgnoreCase("loadAddResourceData")) {
	                ResourceEntryTransactions pt=new ResourceEntryTransactions();
	                out.print(pt.loadAddResourceData());
			}
	        
	        else if (request.getParameter("option") != null && request.getParameter("option").equalsIgnoreCase("addResourceData")) {
	            ResourceEntryTransactions pt=new ResourceEntryTransactions();
	            out.print(pt.addResourceData(request.getParameter("title"),request.getParameter("authors"),session.getAttribute("ss_id").toString()));
	        }
	        
	        else if (request.getParameter("option") != null && request.getParameter("option").equalsIgnoreCase("updateResourceData")) {
	                ResourceEntryTransactions pt=new ResourceEntryTransactions();
	                out.print(pt.updateResourceData(request.getParameter("resource_id").replaceAll("[^0-9]", ""),request.getParameter("fname"),request.getParameter("fvalue"),session.getAttribute("ss_id").toString()));
			}
	        
	        else if (request.getParameter("option") != null && request.getParameter("option").equalsIgnoreCase("freezeResourceData")) {
	                ResourceEntryTransactions pt=new ResourceEntryTransactions();
	                out.print(pt.freezeResourceData(request.getParameter("resource_id").replaceAll("[^0-9]", ""),session.getAttribute("ss_id").toString()));
			}
	        
	        else if (request.getParameter("option") != null && request.getParameter("option").equalsIgnoreCase("deleteResourceData")) {
	                ResourceEntryTransactions pt=new ResourceEntryTransactions();
	                out.print(pt.deleteResourceData(request.getParameter("resource_id").replaceAll("[^0-9]", ""),session.getAttribute("ss_id").toString()));
	        }

            else if (request.getParameter("option") != null && request.getParameter("option").equalsIgnoreCase("loadResourceAdditionalDetails")) {
				ResourceEntryTransactions pt=new ResourceEntryTransactions();
                out.print(pt.loadResourceAdditionalDetails(request.getParameter("resource_id").replaceAll("[^0-9]", "")));
            }
            
            else if (request.getParameter("option") != null && request.getParameter("option").equalsIgnoreCase("loadResourceAccessNoDetails")) {
				ResourceEntryTransactions pt=new ResourceEntryTransactions();
                out.print(pt.loadResourceAccessNoDetails(request.getParameter("resource_id").replaceAll("[^0-9]", "")));
            }
            
            else if (request.getParameter("option") != null && request.getParameter("option").equalsIgnoreCase("loadLibRack")) {
				ResourceEntryTransactions pt=new ResourceEntryTransactions();
                out.print(pt.loadLibRack(request.getParameter("lfm_id").replaceAll("[^0-9]", "")));
            }
            
            else if (request.getParameter("option") != null && request.getParameter("option").equalsIgnoreCase("deleteResourceAccessNoDetails")) {
                ResourceEntryTransactions pt=new ResourceEntryTransactions();
                out.print(pt.deleteResourceAccessNoDetails(request.getParameter("lbm_id").replaceAll("[^0-9]", ""),session.getAttribute("ss_id").toString()));
            }
            
            else if (request.getParameter("option") != null && request.getParameter("option").equalsIgnoreCase("addResourceAccessNoDetails")) {
	            ResourceEntryTransactions pt=new ResourceEntryTransactions();
	            out.print(pt.addResourceAccessNoDetails(request.getParameter("resource_id").replaceAll("[^0-9]", ""), request.getParameter("access_no"),request.getParameter("lib_id").replaceAll("[^0-9]", ""),request.getParameter("lfm_id").replaceAll("[^0-9]", ""),request.getParameter("lsm_id").replaceAll("[^0-9]", ""),request.getParameter("lrm_id").replaceAll("[^0-9]", ""),request.getParameter("lpm_id").replaceAll("[^0-9]", ""),session.getAttribute("ss_id").toString()));
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
            Logger.getLogger(ResourceEntryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ResourceEntryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
