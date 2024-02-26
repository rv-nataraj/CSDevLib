package CAMPS.Admin;

import CAMPS.Connect.DBConnect;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class authorizationFilter implements Filter {

    private FilterConfig filterConfig = null;

    public authorizationFilter() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        DBConnect db = new DBConnect();
        try {
            HttpSession session = ((HttpServletRequest) request).getSession();
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;
            String url = null;
            if (req.getQueryString() != null) {
                url = req.getServletPath() + "?" + req.getQueryString();
            } else {
                url = req.getServletPath();
            }
            boolean reRirect = false;
            if (!url.contains("welcomePage.jsp")) {
                if (session.getAttribute("login") == null || session.getAttribute("login") == "false") {
                   // res.sendRedirect(req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + "/" + "index.jsp");
                    res.sendRedirect( req.getContextPath() + "/" + "index.jsp");
                    
                    reRirect = true;
                }
                String roles = null;
                if (session.getAttribute("roles") != null) {
                    roles = session.getAttribute("roles").toString();
                }
                db.getConnection();
                //db.read("SELECT *                                 FROM admin.role_resource_mapping rrm INNER JOIN admin.resource_master rm ON FIND_IN_SET(rm.resource_id,rrm.resources) AND rrm.role_id IN (" + roles + ") AND rm.link ='" + url.substring(1) + "'");
                db.read("SELECT *, GROUP_CONCAT(rim.ipaddr) ips FROM admin.role_resource_mapping rrm INNER JOIN admin.resource_master rm ON FIND_IN_SET(rm.resource_id,rrm.resources) AND rrm.role_id IN (" + roles + ") AND rm.link ='" + url.substring(1) + "' LEFT JOIN admin.resource_ip_mapping rim ON rim.resource_id=rm.resource_id and rim.rstatus=1 GROUP BY rm.resource_id");
                if (db.rs.first()) {
                    if (db.rs.getString("critical").equalsIgnoreCase("1") && "0".equalsIgnoreCase(session.getAttribute("OTPstatus").toString())) {
                        //res.sendRedirect(req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + "/" + "JSP/Welcome/otp_verification.jsp");
                        res.sendRedirect( req.getContextPath() + "/" + "JSP/Welcome/otp_verification.jsp");
                        
                        reRirect = true;
                    }
                    if (db.rs.getString("ips") !=null) {
                    	if (!db.rs.getString("ips").contains(session.getAttribute("ipaddr").toString().split(",")[0])) {
                    		db.insert("INSERT INTO admin.authorization_log (`resource`,`path`,`accesstime`,`status`,`user_id`,`roles`) VALUES('page','" + url.substring(1) + "',NOW(),'NA-IP RES','" + session.getAttribute("user_id").toString() + "','" + session.getAttribute("roles") + "');");
                    		//res.sendRedirect(req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + "/" + "JSP/Welcome/welcomePage.jsp?error_code=2");
                    		res.sendRedirect( req.getContextPath() + "/" + "JSP/Welcome/welcomePage.jsp?error_code=2");
                    		
                    		reRirect = true;
                    	}
                   }
                    session.setAttribute("resource_id", db.rs.getString("resource_id"));
                    if (session.getAttribute("adminlogin").toString().equalsIgnoreCase("false"))
                    {
	                    if (session.getAttribute("uType").toString().equalsIgnoreCase("staff"))
	                    	db.insert("INSERT INTO camps.pinned_recents (staff_id,resource_id,`count`,access_date) VALUES ('"+session.getAttribute("ss_id").toString()+"','"+db.rs.getString("resource_id")+"','1',NOW()) ON DUPLICATE KEY UPDATE `access_date`=NOW(), `count`=`count`+1");
	                    else
	                    	db.insert("INSERT INTO camps.pinned_recents (student_id,resource_id,`count`,access_date) VALUES ('"+session.getAttribute("ss_id").toString()+"','"+db.rs.getString("resource_id")+"','1',NOW()) ON DUPLICATE KEY UPDATE `access_date`=NOW(), `count`=`count`+1");
	                }
                    db.insert("INSERT INTO admin.authorization_log (`resource`,`path`,`accesstime`,`status`,`user_id`,`roles`) VALUES('page','" + url.substring(1) + "',NOW(),'Authorized','" + session.getAttribute("user_id").toString() + "','" + session.getAttribute("roles") + "');");                    
                } else {
                    if (req.getHeader("Referer") == null) {
                        db.insert("INSERT INTO admin.authorization_log (`resource`,`path`,`accesstime`,`status`,`user_id`,`roles`) VALUES('page','" + url.substring(1) + "',NOW(),'Not  Authorized','" + session.getAttribute("user_id").toString() + "','" + session.getAttribute("roles") + "');");
                        //res.sendRedirect(req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + "/" + "JSP/Welcome/welcomePage.jsp?error_code=0");
                        res.sendRedirect( req.getContextPath() + "/" + "JSP/Welcome/welcomePage.jsp?error_code=0");
                        
                        reRirect = true;
                    } else {
                        db.insert("INSERT INTO admin.authorization_log (`resource`,`path`,`accesstime`,`status`,`user_id`,`roles`) VALUES('" + req.getHeader("Referer") + "','" + url.substring(1) + "',NOW(),'AJAX','" + session.getAttribute("user_id").toString() + "','" + session.getAttribute("roles") + "');");
                    }
                }
            }
            if (!reRirect) {               
                chain.doFilter(new XSSRequestWrapper((HttpServletRequest) request), response);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                db.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(authorizationFilter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    public void destroy() {
    }

    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }
}
