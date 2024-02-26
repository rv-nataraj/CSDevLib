package CAMPS.library;


import CAMPS.Connect.DBConnect;
import CAMPS.Documentation.PublicationTransactions;

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

@WebServlet(name = "author_entry_controller_2353",description = "author_entry_controller_2353",urlPatterns = {"/JSP/library/AuthorEntryController.do"} )

public class AuthorEntryController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        DBConnect db = new DBConnect();
        PrintWriter out = response.getWriter();
        try {
            db.getConnection(); 
            
            if (request.getParameter("option") != null && request.getParameter("option").equalsIgnoreCase("loadAuthorsCount")) {
					AuthorEntryTransactions pt=new AuthorEntryTransactions();
	                out.print(pt.loadAuthorsCount(request.getParameter("search_data")));
			}
            else if (request.getParameter("option") != null && request.getParameter("option").equalsIgnoreCase("loadAuthors")) {
				AuthorEntryTransactions pt=new AuthorEntryTransactions();
	            out.print(pt.loadAuthors(request.getParameter("search_data"),request.getParameter("page_num")));
			}
            
            else if (request.getParameter("option") != null && request.getParameter("option").equalsIgnoreCase("loadAddAuthors")) {
	            AuthorEntryTransactions pt=new AuthorEntryTransactions();
	            out.print(pt.loadAddAuthors());
			}

            else if (request.getParameter("option") != null && request.getParameter("option").equalsIgnoreCase("addAuthors")) {
                AuthorEntryTransactions pt=new AuthorEntryTransactions();
                out.print(pt.addAuthors(request.getParameter("author_name"),session.getAttribute("ss_id").toString()));
            }
            
            else if (request.getParameter("option") != null && request.getParameter("option").equalsIgnoreCase("updateAuthors")) {
	                AuthorEntryTransactions pt=new AuthorEntryTransactions();
	                out.print(pt.updateAuthors(request.getParameter("author_id").replaceAll("[^0-9]", ""),request.getParameter("fname"),request.getParameter("fvalue"),session.getAttribute("ss_id").toString()));
			}
            
            else if (request.getParameter("option") != null && request.getParameter("option").equalsIgnoreCase("freezeAuthors")) {
	                AuthorEntryTransactions pt=new AuthorEntryTransactions();
	                out.print(pt.freezeAuthors(request.getParameter("author_id").replaceAll("[^0-9]", ""),session.getAttribute("ss_id").toString()));
			}
            
            else if (request.getParameter("option") != null && request.getParameter("option").equalsIgnoreCase("deleteAuthors")) {
	                AuthorEntryTransactions pt=new AuthorEntryTransactions();
	                out.print(pt.deleteAuthors(request.getParameter("author_id").replaceAll("[^0-9]", ""),session.getAttribute("ss_id").toString()));
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
            Logger.getLogger(AuthorEntryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(AuthorEntryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
