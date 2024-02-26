package CAMPS.Admin;

import CAMPS.Connect.DBConnect;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ResourceBundle;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
@WebServlet(name = "change_password_controller",description = "change_password_controller",urlPatterns = {"/JSP/Welcome/changePassword.do"} )

public class changePassword extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession ses = request.getSession(false);
        PrintWriter out = response.getWriter();
        try {
            DBConnect db = new DBConnect();
            db.getConnection();
            ResourceBundle rb = ResourceBundle.getBundle("CAMPS.properties.private_key");
            if (request.getParameter("option") != null && request.getParameter("option").equalsIgnoreCase("changePassword")) {
            	if (ses.getAttribute("role_name").toString().contains("Staff Password Reset")) {
       
	                String newPassword = request.getParameter("newPassword");
	                String confirmPassword = request.getParameter("confirmPassword");
	                String userId = request.getParameter("staff_id");
	                
	                if (newPassword.equals(confirmPassword)) {
	                    db.update("UPDATE admin.user_master SET pwdSalt=SHA1(MD5(SHA1(RAND()))),user_password=SHA1(SHA1(MD5(CONCAT(pwdSalt,'" + rb.getString("next_key.first") + "','" + newPassword + "','" + rb.getString("next_key.last") + "',pwdSalt)))),updated_date=NOW(),status=1 WHERE staff_id='" + request.getParameter("staff_id") + "' ");
	                    out.print("<script>showNotification('alert-success', 'Password Chaged Successfully','top', 'right',  '', '',false,'glyphicon glyphicon-cd glyphicon-spin');</script>");
	                } else {
	                    out.print("<script>showNotification('alert-danger', 'Password Does Not Match','top', 'right',  '', '',false,'glyphicon glyphicon-cd glyphicon-spin');</script>");
	                }
            	}
            } else {
            	
                String oldPassword = request.getParameter("oldPassword");
                String newPassword = request.getParameter("newPassword");
                String confirmPassword = request.getParameter("confirmPassword");
                String userId = ses.getAttribute("user_id").toString();
                
                if (newPassword.equals(confirmPassword)) {
                    db.read("SELECT * FROM admin.user_master um WHERE (um.user_password=SHA1(SHA1(MD5(CONCAT(um.pwdSalt,'" + rb.getString("current_key.first") + "','" + oldPassword + "','" + rb.getString("current_key.last") + "',um.pwdSalt)))) OR  um.user_password=SHA1(SHA1(MD5(CONCAT(um.pwdSalt,'" + rb.getString("next_key.first") + "','" + oldPassword + "','" + rb.getString("next_key.last") + "',um.pwdSalt)))) ) AND um.user_id='" + userId + "'");
                    if (db.rs.first() && db.rs.getRow() == 1)//check username password exits
                    {
                        db.update("UPDATE admin.user_master SET pwdSalt=SHA1(MD5(SHA1(RAND()))),user_password=SHA1(SHA1(MD5(CONCAT(pwdSalt,'" + rb.getString("next_key.first") + "','" + newPassword + "','" + rb.getString("next_key.last") + "',pwdSalt)))),updated_date=NOW(),status=1 WHERE user_id='" + userId + "' ");
                        //response.sendRedirect(request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/" + "logout");
                        out.print("<script>showNotification('alert-success', 'Password Chaged Successfully','top', 'right',  '', '',false,'glyphicon glyphicon-cd glyphicon-spin');window.location.href =\"" + request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/logout\";</script>");
                    } else {
                        out.print("<script>showNotification('alert-danger', 'Old Password Incorrect','top', 'right',  '', '',false,'glyphicon glyphicon-cd glyphicon-spin');</script>");
                    }
                } else {
                    out.print("<script>showNotification('alert-danger', 'Password Does Not Match','top', 'right',  '', '',false,'glyphicon glyphicon-cd glyphicon-spin');</script>");
                }
            }
            db.closeConnection();
        } catch (Exception e) {
            e.printStackTrace(out);
        } finally {
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
