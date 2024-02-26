
<%@include file="../../CommonJSP/pageHeader.jsp"%>
<%@page import="CAMPS.Common.NoticeBoardReports"%>



<style>
.button2 {
  border: none;
  color: white;
  padding: 20px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin: 4px 2px;
  cursor: pointer;
  border-radius: 8px;
}

.buttoncolor1 {
	background-color: #b57412;
}
.buttoncolor2 {
	background-color: #0f7a24;
}
.buttoncolor3 {
	background-color: #a15a9d;
}
</style>

<%
NoticeBoardReports nbr=new NoticeBoardReports();
DBConnect db = new DBConnect();
try {
	db.getConnection();
%>

<section class="content">

<div class="row clearfix">
<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
<div style="background-color:#0a3153" id="load_3433"> 
            <p style="padding:10px;text-align:center;color:white;font-size:16px	" > <%= nbr.todayDayOrder()  %> </p>
    </div>
    </div>
</div>

<div class="row clearfix">
		<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
			<div class="panel-group full-body" id="pgfb1" role="tablist">
				<div class="panel">
					<div class="panel-heading" role="tab" id="ph12">
						<h4 class="panel-title" title="This panel shows the list of recenly accessed menus (top 5).">
							<a role="button" data-toggle="collapse" href="#pt12"
								aria-expanded="true" aria-controls="pt1"> <i
								class="material-icons">view_comfy</i> Recently Accessed <i style="float:right" "class="material-icons">?</i>
							</a>
						</h4>
					</div>

					<div id="pt12" class="panel-collapse collapse in">
						<div class="panel-body">
								<% 
								db.read("SELECT pr.`resource_id`,rm.`link`,ifnull(rht.display_label,rm.label) label,pr.count,pr.access_date FROM camps.`pinned_recents` pr INNER JOIN admin.`resource_master` rm ON rm.`resource_id`=pr.`resource_id` left join admin.resource_help_text rht on rht.resource_id=rm.resource_id WHERE rm.resource_id<>359 and pr.`staff_id`='"+session.getAttribute("ss_id").toString()+"' ORDER BY pr.access_date DESC LIMIT 5");
								while (db.rs.next()) {
									out.print("<a href=\""+request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/"+db.rs.getString("link")+"\" ><button class=\"button2 buttoncolor1\">"+db.rs.getString("label")+"</button></a>");
								}
								%>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div class="row clearfix">
		<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
			<div class="panel-group full-body" id="pgfb1" role="tablist">
				<div class="panel">
					<div class="panel-heading" role="tab" id="ph1">
						<h4 class="panel-title" title="This panel shows the list of frequenly accessed menus (top 5)">
							<a role="button" data-toggle="collapse" href="#pt1"
								aria-expanded="true" aria-controls="pt1"> <i
								class="material-icons">view_comfy</i> Frequently Accessed <i style="float:right" "class="material-icons">?</i>
							</a>
						</h4>
					</div>

					<div id="pt1" class="panel-collapse collapse in">
						<div class="panel-body">
								<% db.read("SELECT pr.`resource_id`,rm.`link`,ifnull(rht.display_label,rm.label) label, pr.count,pr.access_date FROM camps.`pinned_recents` pr INNER JOIN admin.`resource_master` rm ON rm.`resource_id`=pr.`resource_id` left join admin.resource_help_text rht on rht.resource_id=rm.resource_id WHERE rm.resource_id<>359 and pr.`staff_id`='"+session.getAttribute("ss_id").toString()+"' ORDER BY pr.count DESC LIMIT 5");
								while (db.rs.next()) {
									out.print("<a href=\""+request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/"+db.rs.getString("link")+"\" ><button class=\"button2 buttoncolor2\">"+db.rs.getString("label")+"</button></a>");
								}
								%>							
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="row clearfix">
		<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
			<div class="panel-group full-body" id="pgfb199" role="tablist">
				<div class="panel ">
					<div class="panel-heading" role="tab" id="ph199">
						<h4 class="panel-title" title="This panel shows the list of pinned resources.  User may pin a resource using the pin symbol available in the top right corner after home button.">
							<a role="button" data-toggle="collapse" href="#pt199"
								aria-expanded="true" aria-controls="pt199"> <i
								class="material-icons">view_comfy</i> Pinned Favorites <i style="float:right" "class="material-icons">?</i>
							</a>
						</h4>
					</div>

					<div id="pt199" class="panel-collapse collapse in">
						<div class="panel-body">
								<% db.read("select pr.resource_id,rm.link,ifnull(rht.display_label,rm.label) label from camps.pinned_resources pr inner join admin.resource_master rm on rm.resource_id=pr.resource_id left join admin.resource_help_text rht on rht.resource_id=rm.resource_id where rm.resource_id<>359 and staff_id='"+session.getAttribute("ss_id").toString()+"' and pr.rstatus=1");
								int count=0;
								while (db.rs.next()) {
									count++;
									out.print("<a href=\""+request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/"+db.rs.getString("link")+"\" ><button class=\"button2 buttoncolor3\">"+db.rs.getString("label")+"</button></a>");
								}
								if (count==0) out.print("You may pin resources here by clicking on the pin symbol available in top right corner");
								%>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>



</section>

<%
} catch (Exception e) {
} finally {
db.closeConnection();
}
%>
<%@include file="../../CommonJSP/pageFooter.jsp"%>