<%@include file="../../CommonJSP/pageHeader.jsp" %>
<section class="content">
        <div class="container-fluid">
        <% 
        if (request.getParameter("error_code")!=null) {
       		if (request.getParameter("error_code").equalsIgnoreCase("3"))
            	out.print("<div class=\"block-header\">    <h2>The role required to perform this operation is missing. please contact admin</h2>     </div>");
       		else if (request.getParameter("error_code").equalsIgnoreCase("2"))
            	out.print("<div class=\"block-header\">    <h2>Not Authorized to Access this page - IP Source Restriction Applied.  Your IP Address is "+session.getAttribute("ipaddr")+" </h2>     </div>");
        	else
        		out.print("<div class=\"block-header\">    <h2>Not Authorized </h2>     </div>");
        } else if (request.getParameter("exception_id")!=null) {
        		out.print("<div class=\"block-header\">    <h2>Exception ID:"+request.getParameter("exception_id")+" </h2>     </div>");
        }
        else
        	out.print("<div class=\"block-header\">    <h2>Welcome</h2>     </div>");
        %>
        </div>
</section>
<%@include file="../../CommonJSP/pageFooter.jsp" %>