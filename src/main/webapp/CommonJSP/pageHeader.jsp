<%@page import="CAMPS.Connect.DBConnect"%>
<%@page import="CAMPS.Common.CSMailer"%>
<%@page import="CAMPS.Common.PageHeader"%>
<%@page import="java.util.*"%>
<!DOCTYPE html>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="CAMPS.Admin.menu"%>
<html>
    <head>
        <meta charset="UTF-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
        <title>CampusStack</title>
        <%String host_string = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";%>
        <link rel="icon" href="https://cdn.campusstack.in/favicon.ico" type="image/x-icon" />
        <link href="https://fonts.googleapis.com/css?family=Roboto:400,700&subset=latin,cyrillic-ext&display=swap" rel="stylesheet" type="text/css">
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons&display=swap" rel="stylesheet" type="text/css">
        <link href="https://cdn.campusstack.in/assets/plugins/bootstrap/css/bootstrap.css" rel="stylesheet"/>
        <link href="https://cdn.campusstack.in/assets/plugins/node-waves/waves.css" rel="stylesheet" />
        <link href="https://cdn.campusstack.in/assets/css/additional_style.min.css" rel="stylesheet" />
        <link href="https://cdn.campusstack.in/assets/css/noprint.css" rel="stylesheet" />
        <link href="https://cdn.campusstack.in/assets/plugins/animate-css/animate.css" rel="stylesheet" />
        <link href="https://cdn.campusstack.in/assets/plugins/bootstrap-select/css/bootstrap-select.css" rel="stylesheet" />
        <link href="https://cdn.campusstack.in/assets/plugins/multi-select/css/multi-select.css" rel="stylesheet" /> 
        <link href="https://cdn.campusstack.in/assets/plugins/bootstrap-datepicker-master/css/bootstrap-datepicker3.css" rel="stylesheet" />
        <link href="https://cdn.campusstack.in/assets/plugins/bootstrap-material-datetimepicker/css/bootstrap-material-datetimepicker.css" rel="stylesheet" />
        <link href="https://cdn.campusstack.in/assets/css/style.min.css" rel="stylesheet">
        
        <link href="https://cdn.campusstack.in/assets/css/theme.css" rel="stylesheet" /><!-- AdminBSB Themes. -->
        <script src="https://cdn.campusstack.in/assets/plugins/jquery/jquery.min.js"></script>
        <script src="https://cdn.campusstack.in/assets/plugins/bootstrap/js/bootstrap.js"></script>
        <script src="https://cdn.campusstack.in/assets/plugins/bootstrap-select/js/bootstrap-select.js"></script>
        <script src="https://cdn.campusstack.in/assets/plugins/jquery-slimscroll/jquery.slimscroll.js"></script>
        <script src="https://cdn.campusstack.in/assets/plugins/node-waves/waves.js"></script>
        <script src="https://cdn.campusstack.in/assets/plugins/jquery-countto/jquery.countTo.js"></script>
        <script src="https://cdn.campusstack.in/assets/plugins/bootstrap-notify/bootstrap-notify.js"></script>
        <script src="https://cdn.campusstack.in/assets/plugins/momentjs/moment.js"></script> 
        <script src="https://cdn.campusstack.in/assets/plugins/bootstrap-datepicker-master/js/bootstrap-datepicker.js"></script>
        <script src="https://cdn.campusstack.in/assets/plugins/bootstrap-material-datetimepicker/js/bootstrap-material-datetimepicker.js"></script>
        <script src="https://cdn.campusstack.in/assets/plugins/jquery-validation/jquery-validate.bootstrap-tooltip.js"></script>
        <script src="https://cdn.campusstack.in/assets/js/pages/CommonJSP/disable_back.js"></script>
		        
		        
		<script type="text/javascript" src="https://cdn.campusstack.in/assets/plugins/tableExport.jquery.plugin-master/tableExport.js"></script>
		<script type="text/javascript" src="https://cdn.campusstack.in/assets/plugins/tableExport.jquery.plugin-master/libs/js-xlsx/xlsx.core.min.js"></script>
		<script type="text/javascript" src="https://cdn.campusstack.in/assets/plugins/tableExport.jquery.plugin-master/libs/FileSaver/FileSaver.min.js"></script>
		        
		        <script src="https://cdn.campusstack.in/assets/plugins/bootpag/jquery.bootpag.js"></script>
		        
        <!-- CAMPUS STACK JS FILES -->
        <script src="https://cdn.campusstack.in/assets/js/admin.js"></script>
        <script src="https://cdn.campusstack.in/assets/js/pages/CommonJSP/notifications.js"></script>
        <script type = "text/javascript" >
            $(document).ajaxStart(function () {
                $('.page-loader-wrapper').show();
            });
            $(document).ajaxComplete(function () {
                $('.page-loader-wrapper').fadeOut();
            });
              
        </script>
        
        
        <script>
        function pin_this_resource(rid) {
    		var DataString = 'resource_id=' + rid + '&option=pin_this_resource';
    		$.ajax({
    			url : "../Common/ControllerCommon.do",
    			data : DataString,
    			type : "post",
    			success : function(data) {

    				alert("Success:  Now you may access this resource from your pinned favorites.")  
    			}
    		});
    	}
        
        function unpin_this_resource(rid) {

    		var DataString = 'resource_id=' + rid + '&option=unpin_this_resource';
    		$.ajax({
    			url : "../Common/ControllerCommon.do",
    			data : DataString,
    			type : "post",
    			success : function(data) {

    				alert("Success:  Now you may access this resource from your pinned favorites.")  
    			}
    		});
    	}
        function printDiv(div_id) 
    	{
    	  var divToPrint=document.getElementById(div_id);
    	  var newWin=window.open('','Print-Window');
    	  newWin.document.open();
    	  newWin.document.write('<html><style> @media print{.no-print,.no-print *{display:none!important} footer { position: fixed; bottom: 0; }   header {position: fixed; top: 0; } } </style><body onload="window.print()">'+divToPrint.innerHTML+'</body></html>');
    	  newWin.document.close();
    	  setTimeout(function(){newWin.close();},10);
    	}
  
    	function excelExport(table_id)
    	{
    	    $('#'+table_id).tableExport({filename: "report", type: 'xlsx'});
    	}
        </script>
    </head>
    <body class="theme-blue">
    <div class="page-container only-print">
    Page <span class="page"></span>
  </div>
    
    
        <!-- Page Loader -->
        <div class="page-loader-wrapper">
            <div class="loader">
                <div class="preloader">
                    <div class="spinner-layer pl-red">
                        <div class="circle-clipper left">
                            <div class="circle"></div>
                        </div>
                        <div class="circle-clipper right">
                            <div class="circle"></div>
                        </div>
                    </div>
                </div>
                <p>Please wait...</p>
            </div>
        </div>
        <div class="overlay"></div>
        <%  String resource_id=""; ResourceBundle rb = ResourceBundle.getBundle("CAMPS.properties.software");
            PageHeader ph=new PageHeader();
        	DBConnect con = new DBConnect();
            try {
                if (session.getAttribute("roles") == null) {
                    response.sendRedirect(host_string + "CommonJSP/signin.jsp");
                } else {
                    con.getConnection();

        %>
        <%            if (request.getParameter("am_c") != null) {
                String am_c = request.getParameter("am_c");
        %> 
        <jsp:include page="../../CommonJSP/alert_message.jsp" >
            <jsp:param name="am_c" value="<%=am_c%>"></jsp:param>
        </jsp:include>
        <%
            }
        %>  
        
        <!-- TOP NAVIGATION BAR -->
        <nav class="navbar" <%=	host_string.contains("test")?"style=background-color:green":"" %>>
            <div class="container-fluid">
                <div class="navbar-header">
                    <a href="javascript:void(0);" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar-collapse" aria-expanded="false"></a>
                    <a href="javascript:void(0);" class="bars" style="display: block;"></a>
                    <img class='navbar-brand' src='<%=ph.getLogoImageURL() %> ' /> 
                                                    
                </div>
                <div class="collapse navbar-collapse" id="navbar-collapse">
                    <ul class="nav navbar-nav navbar-right">                    
                        <%
                            String data = "";
                            int count = 0;
                            con.read("SELECT nm.nm_id,nm.nm_title,nm.nm_desc,nm.nm_link,nm.trigger_type,nq.query,nm.trigger_type,CONCAT('\"title\":\"',nm.nm_title,'\",\"msg\":\"',nm.nm_desc,'\",\"url\":\"',nm.nm_link,'\"') DATA,nm.trigger_type FROM camps.notification_master nm LEFT JOIN camps.notification_specific ns ON nm.nm_id=ns.nm_id AND nm.nm_type=ns.nm_type AND (IF(ns.nm_type='role',ns.type_value='" + session.getAttribute("roles") + "' , ns.type_value='" + session.getAttribute("user_id") + "' ) )  LEFT JOIN (SELECT nh.nm_id,nh.user_id,MAX(nh.viewed_date)viewed_date,MAX(nh.delivery_date)delivery_date FROM camps.notification_history nh WHERE nh.user_id='" + session.getAttribute("user_id") + "' GROUP BY nh.nm_id)nh ON nh.nm_id=nm.nm_id  LEFT JOIN camps.notification_query nq ON nq.nm_id=nm.nm_id  WHERE  CASE WHEN nm.notify='once' THEN nh.nm_id IS NULL WHEN nm.notify='until view'  THEN nh.viewed_date IS NULL AND (nh.delivery_date IS NULL OR nh.delivery_date< NOW() - INTERVAL nm.repeat_after MINUTE)WHEN nm.notify='repeat'  THEN (nh.delivery_date IS NULL OR nh.delivery_date< NOW() - INTERVAL nm.repeat_after MINUTE) END AND nm.status=1");
                            while (con.rs.next()) {
                                if (con.rs.getString("trigger_type").equalsIgnoreCase("query")) {
                                    con.read1(con.rs.getString("query").replaceAll("__XXX__", session.getAttribute("ss_id").toString()));
                                    if (con.rs1.next()) {
                                        data += "<li><a href='" + con.rs.getString("nm_link") + "' class=' waves-effect waves-block'><div class='menu-info'><h4>" + con.rs.getString("nm_title") + "</h4><p>" + con.rs1.getString("qdesc") + "</p></div></a> </li>";
                                        count++;
                                    }
                                } else {
                                    data += "<li><a href='" + con.rs.getString("nm_link") + "' class=' waves-effect waves-block'><div class='menu-info'><h4>" + con.rs.getString("nm_title") + "</h4><p>" + con.rs.getString("nm_desc") + "</p></div></a> </li>";
                                    count++;
                                }

                            }
                        %>
                        <!-- NOTIFICATION START -->
                        <li class="dropdown">
                            <a href="javascript:void(0);"  class="dropdown-toggle" data-toggle="dropdown" role="button">
                                <i class="material-icons">notifications</i>
                                <span class="label-count"> <%=count%> </span>
                            </a>
                            <ul class="dropdown-menu">
                                <li class="header">NOTIFICATIONS</li>
                                <li class="body">
                                    <div class="slimScrollDiv" style="position: relative; overflow: hidden; width: auto; height: 254px;"><ul class="menu" style="overflow: hidden; width: auto; height: 254px;">
                                            <%=data%>  
                                        </ul><div class="slimScrollBar" style="background: rgba(0, 0, 0, 0.5) none repeat scroll 0% 0%; width: 4px; position: absolute; top: 0px; opacity: 0.4; border-radius: 0px; z-index: 99; right: 1px; display: block;"></div><div class="slimScrollRail" style="width: 4px; height: 100%; position: absolute; top: 0px; display: none; border-radius: 0px; background: rgb(51, 51, 51) none repeat scroll 0% 0%; opacity: 0.2; z-index: 90; right: 1px;"></div></div>
                                </li>
                                <li class="footer">
                                    <a href="javascript:void(0);" class=" waves-effect waves-block">View All Notifications</a>
                                </li>
                            </ul>
                        </li>
                        <!-- USER PHOTO AND INFORMATION START -->
                        <li>
                            <div class="navbar-header">               
                                <div class="info-container">                    
                                    <div class="user-helper-dropdown">
                                        <i class="material-icons" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">  
                                            <!-- USER PHOTO -->
                                            <div class="profile image">
                                                <img src='<%=ph.getUserPhoto(session.getAttribute("uType").toString(), session.getAttribute("ss_id").toString()) %>' width='48' height='48' alt='User' />
                                            </div>
                                        </i>
                                        <!-- USER PHOTO DROP DOWN MENU -->
                                        <ul class="dropdown-menu ">
                                            <li> <div style="text-align: center;font-size: larger;color: #2196f3;"><% out.print(session.getAttribute("user_name").toString() + " (" + session.getAttribute("ss_id") + ")"+"-"+session.getAttribute("ipaddr"));%></div></li>
                                            <li role="separator" class="divider"></li>
                                            <li><a href="<%=request.getContextPath() + "/"%>JSP/Welcome/change_password.jsp"><i class="material-icons">input</i>Change Password</a></li>
                                            <li role="separator" class="divider"></li>
                                            <li><a href="<%=request.getContextPath() + "/"%>logout"><i class="material-icons">input</i>Sign Out</a></li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
        <!-- SIDE BAR MENU -->
        <section>
            <aside id="leftsidebar" class="sidebar">
                <div class="menu">
                    <%
                        String hostname = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
                        menu menuobj = new menu();
                        out.println("<ul class=\"list\">" + menuobj.menu_generator(0, session.getAttribute("roles").toString(), hostname) + "</ul>");
                    %>
                </div>
                <div class="legal" <%=	host_string.contains("test")?"style=background-color:green":"" %> >
                    <div class="copyright" style="color:white">
                       <img style="width: 10%;" src="https://cdn.campusstack.in/cs-logo.png" type="image/x-icon" /> <a  style="color: white !important;" target="_blank" href="http://campusstack.org">Powered by <b><%= rb.getString("poweredby") %></b></a>.
                    </div>
                </div>
                <%  }
                %>
            </aside>
        </section>

        <section class="content1" title="This is info bar. This bar shows the current resource being accessed on the left side.  On the right side, it shows three icons: The home symbol icon takes the user to the Home->Pinned resources, the pin icon lets user to pin the current resource as their favorite, the info icon displays help text for the current resource.">
            <ol  <%=	host_string.contains("test")?"class=\"breadcrumb breadcrumb-bg-green\"":"class=\"breadcrumb breadcrumb-bg-blue\"" %> >
                <%
                    String data = "";
                    con.read("SELECT @r AS id, (SELECT CONCAT('<li><i class=\"material-icons\" d=\"',@r := c.parent_id,'\">',IFNULL(c.img,'link'),'</i> ',IF(@l=0,concat('<a target=\"_blank\" href=\"https://bitbucket.org/cs/campusstack_v2/src/master/src/main/webapp/',c.link,'\">',c.label,'</a>'),c.label),'</li>') FROM admin.resource_master c  WHERE c.resource_id = id ) AS parent, @l := @l + 1 AS LEVEL,u.label FROM (SELECT @r := (SELECT resource_id FROM admin.resource_master a WHERE a.link='" + request.getServletPath().substring(1) + ((request.getQueryString()!=null)?"?"+request.getQueryString():"") + "'), @l := 0) vars, admin.resource_master u WHERE @r <> 0 ORDER BY LEVEL DESC");
                    while (con.rs.next()) {
                        out.print(con.rs.getString("parent"));
                        data += "$('#menu_" + con.rs.getString("id") + "').addClass(\"active\");";
                        resource_id=con.rs.getString("id");
                    }
                    %>
                    <li style="float: right;"><a title="Goto Home Page" href="<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/JSP/home/pinned_resources.jsp" %>"><i class="material-icons">home</i></a>
                    <%
                    if (!resource_id.equalsIgnoreCase("359"))
                    {
	                    con.read("select resource_id,staff_id,rstatus from camps.pinned_resources pr where pr.rstatus=1 and pr.resource_id='"+resource_id+"' and pr.staff_id='"+session.getAttribute("ss_id").toString()+"'");
	                    if (con.rs.next()) {
	                    	%>
	                    	<a title="unpin this resource" href="#" onclick="unpin_this_resource(<%= resource_id%>)"><i class="material-icons">delete</i></a>
	                    	<%
	                    }
	                    else {
	                    	%>
	                    	<a title="pin this resource" href="#" onclick="pin_this_resource(<%= resource_id%>)"><i class="material-icons">push_pin</i></a>
	                    	<%
	                    }
                    }
                %>
                <a title="Info about this resource - Reserved for future use"  data-toggle="modal" data-target="#largeModalinfo"  onclick='showResourceDetails(<%= resource_id%>)' href="javascript:showResourceDetails(<%= resource_id%>)"><i class="material-icons">info</i></a></li>
            </ol>
            <% 
            out.print("<script>" + data + "</script>");
            %>
        </section>
        
        <section style="display:none" class="content" title="This is quick reports bar. The icons display data (frequently needed for the users) in a dialog box without navigating to other resources.">
            <ol style="text-align:center" <%=	host_string.contains("test")?"class=\"breadcrumb breadcrumb-bg-green\"":"class=\"breadcrumb breadcrumb-bg-blue\"" %> >
                
                    <li style="">
                    		<a title="info1" href="#"><i class="material-icons">diversity_3</i></a>
                    
	                    	<a title="unpin this resource" href="#" onclick=""><i class="material-icons">event_available</i></a>
	                    
	                    	<a title="pin this resource" href="#" onclick=""><i class="material-icons">info</i></a>
	                    	
                			<a title="Info about this resource - Reserved for future use"  data-toggle="modal" data-target="#largeModalinfo"  onclick="" href="#"><i class="material-icons">auto_stories</i></a>
                	</li>
            </ol>
        </section>
        
        
        <%
            } catch (Exception e) {
                throw new Exception(e);
            } finally {
                con.closeConnection();
            }
        %>
        
        
<div class="modal fade" id="largeModalinfo" tabindex="-1" role="dialog">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title" id="largeModalLabelinfo">Resource Details</h4>
			</div>
			<div class="modal-body" id="resource_det" style="color:white"></div>
			<div class="modal-footer" style="background-color:#2460a7">
				<button type="button" class="btn btn-link waves-effect" data-dismiss="modal">CLOSE</button>
			</div>
		</div>
	</div>
</div>

<script>
function showResourceDetails(resource_id) {
	var DataString = 'resource_id=' + resource_id  +  '&option=showResourceInfo';
    //alert(DataString);
    $.ajax({
        url: "../Common/ControllerCommon.do", data: DataString, type: "post",
        success: function (data) {
        	//alert(data);
            $('#resource_det').html(data);
        }
    });
}

</script>
        
        