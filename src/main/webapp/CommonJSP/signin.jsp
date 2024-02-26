<%@page import="CAMPS.Connect.DBConnect"%>
<%@page import="java.util.ResourceBundle"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
<title>CampusStack</title>
<!-- Favicon-->
<%
String host_string = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
		+ request.getContextPath() + "/";
%>
<!-- Google Fonts -->
<link href="https://fonts.googleapis.com/css?family=Roboto:400,700&subset=latin,cyrillic-ext&display=swap" rel="stylesheet" type="text/css">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons&display=swap" rel="stylesheet" type="text/css">
<link rel="icon" href="https://cdn.campusstack.in/favicon.ico" type="image/x-icon" />
<!-- Bootstrap Core Css -->
<link href="https://cdn.campusstack.in/assets/plugins/bootstrap/css/bootstrap.css" rel="stylesheet">
<!-- Waves Effect Css -->
<link href="https://cdn.campusstack.in/assets/plugins/node-waves/waves.css" rel="stylesheet" />
<!-- Social media icons Css -->
<link href="https://cdn.campusstack.in/assets/css/social.min.css" rel="stylesheet" />
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.6.3/css/all.min.css" rel="stylesheet" />
<link href="https://cdn.campusstack.in/assets/plugins/animate-css/animate.css" rel="stylesheet" />
<!-- Custom Css -->
<link href="https://cdn.campusstack.in/assets/css/style.min.css" rel="stylesheet">
<link href="https://cdn.campusstack.in/assets/css/materialize.css" rel="preload">
<link href="https://cdn.campusstack.in/assets/css/loginstyle.min.css" rel="stylesheet">
<!-- Jquery Core Js -->
<script src="https://cdn.campusstack.in/assets/plugins/jquery/jquery.min.js"></script>
<!-- Bootstrap Core Js -->
<script src="https://cdn.campusstack.in/assets/plugins/bootstrap/js/bootstrap.js"></script>
<!-- Waves Effect Plugin Js -->
<script src="https://cdn.campusstack.in/assets/plugins/node-waves/waves.js"></script>
<!-- Validation Plugin Js -->
<script src="https://cdn.campusstack.in/assets/plugins/jquery-validation/jquery.validate.js"></script>
<!-- Custom Js -->
<script src="https://cdn.campusstack.in/assets/js/admin.js"></script>
<script src="https://cdn.campusstack.in/assets/js/pages/CommonJSP/sign-in.js"></script>
<script src="https://cdn.campusstack.in/assets/js/pages/CommonJSP/notifications.js"></script>
<!-- Bootstrap Notify Plugin Js -->
<script src="https://cdn.campusstack.in/assets/plugins/bootstrap-notify/bootstrap-notify.js"></script>
<script>
	localStorage.setItem('logout-event', 'logout' + Math.random());
</script>
<script type="text/javascript">
	$(document).ready(function() {
		window.history.pushState(null, "", window.location.href);
		window.onpopstate = function() {
			window.history.pushState(null, "", window.location.href);
		};
	});
</script>
<script>
var pinput = document.getElementById("password");
pinput.addEventListener("keypress", function(event) {
  if (event.key === "Enter") {
    event.preventDefault();
    document.getElementById("loginbtn").click();
  }
});
</script>

</head>

<%
String bgcolor = "", logo = "";
DBConnect con = new DBConnect();
try {
	con.getConnection();
	con.read("select IFNULL(signin_page_color,' ') spc,ifnull(org_logo_cdn,'../images/camps-logo.png') org_logo_cdn from camps.master_organization");
	if (con.rs.next())
		bgcolor = con.rs.getString("spc");
	logo = con.rs.getString("org_logo_cdn");
%>

<body class="login-page" <%=	host_string.contains("test")?"style=background-color:green":"style=background-color:"+bgcolor %> >
	<div class="login-box" id="signinForm" style="display: none;">
		<div class="body">
			<form id="sign_in" action="../checkLogin" method="POST">
				<div class="logo" style="text-align: center;">
					<img src="<%=logo%>" style="width: 100%;">
				</div>
				<div class="input-group has-feedback">
					<div class="">
						<input type="text" class="form-control" name="uname" data-rule-alphanumeric="true" id="username" placeholder="Username" required autofocus> <i class="glyphicon glyphicon-user form-control-feedback"></i>
					</div>
				</div>
				<div class="input-group has-feedback">
					<div class="">
						<input type="password" class="form-control" name="pword" autocomplete="off" data-rule-varchar="true" id="password" placeholder="Password" required> <i class="glyphicon glyphicon-lock form-control-feedback"></i>
					</div>
				</div>
				<div class="msg_center">
					<button class="btn btn-block bg-blue waves-effect" type="submit" id="loginbtn">LOGIN</button>
				</div>
				<div class="msg_center">(or)</div>
				<div class="msg_center">
					<img src="https://cdn.campusstack.in/assets/images/gsignin.png" style="width: 100%; height: 40px;" onClick="window.location.href = '../gSignIn'; " />
				</div>
				<div class="row m-t-15 m-b--20">
					<div class="col-xs-6 "></div>
					<div class="col-xs-12 align-right">
						<a href="forgot-password.jsp">Forgot Password?</a>
					</div>
				</div>
			</form>
			
		</div>
	</div>
	<div class="login-box" id="welcome">
		<div class="body">
			<div class="logo" style="text-align: center;">
				<img src="<%=logo%>" style="width: 100%;">
			</div>
			<% if ((request.getParameter("logintype")!=null) && request.getParameter("logintype").equalsIgnoreCase("studentonly") ) ; else { %>
			<div class="msg_center">
				<a class="btn btn-block bg-blue waves-effect" style="border-radius:53px;" href="javascript:shownSignin()" role="button"><b style="color: white; font-size: 24px">Staff Login</b></a>
			</div>
			<% } %>
			<div class="msg_center">
				<br> <br>
			</div>
			<div class="msg_center">
				<a class="btn btn-block bg-blue waves-effect" style="border-radius:53px;" href="../gSignIn" role="button"><b style="color: white; font-size: 24px">Student Login</b></a>
			</div>
		</div>
	</div>
	
	<%
			if (request.getParameter("er_c") != null && request.getParameter("er_c").equalsIgnoreCase("0")) {
				out.print(" <script>  showNotification('alert-danger', 'Incorrect username / Password','bottom', 'center',  '', '',false,'glyphicon glyphicon-remove-circle');</script>");
			} else if (request.getParameter("er_c") != null && request.getParameter("er_c").equalsIgnoreCase("1")) {
				out.print(" <script>  showNotification('bg-deep-orange', 'The account is deactivated contact Administrator','bottom', 'center',  '', '',false,'glyphicon glyphicon-pushpin');</script>");
			}
	%>

		<ul>
		<%
		con.read("SELECT sm.`sm_color`,sm.`sm_icon`,sm.`sm_link` FROM admin.`social_media` sm WHERE sm.`rstatus`>0 ORDER BY `order_no`");
		while(con.rs.next()){
		%>
		  <li>
		    <a target="_blank" href="<%=con.rs.getString("sm_link") %>" style='background: <%=con.rs.getString("sm_color") %> !important;'>
		      <i style="color: white !important;" class="fab <%=con.rs.getString("sm_icon") %> icon"></i>    </a>
		  </li>
		  <%}%>
		</ul>
	<script>
		function shownSignin() {
			$('#signinForm').show();
			$('#welcome').hide();
		}
	</script>
</body>
</html>

<%
} catch (Exception e) {
out.print("<h1 title=\""+e.toString()+"\" style=\"text-align:center;color:red\">Error Code: CS100</h1>");
} finally {
con.closeConnection();
}
%>