<%@include file="../../CommonJSP/pageHeader.jsp" %>

<script src="https://cdn.campusstack.in/assets/js/listselection.js"></script>
<link href="https://cdn.campusstack.in/assets/css/listselection.css" rel="stylesheet"/>
<%    
	DBConnect db = new DBConnect();
    try {
        db.getConnection();
%>
<style>.bg-orange  {margin-top: -8px;}</style>
<script>

    function load_role_det(staff_id) {
        var DataString = 'staff_id='+staff_id+'&option=role_select';
        $.ajax({
            url: "RoleAllocationController.do", data: DataString, type: "post",
            success: function (data)
            {
                $('#load_branch_det').html(data);
                $.listSelection("#listSelection", "100", "50", "3");
            }
        });
    }
     function load_stu(stu_id) {
        var DataString = 'staff_id=' + stu_id + '&option=loadpersonal_pdet';
        $.ajax({
            url: "RoleAllocationController.do", data: DataString, type: "post",
            success: function (data)
            {
                $('#loadstu_det').html(data);
            }
        });
    }
    function addstu(staff_id) {
        load_stu(staff_id);
        load_role_det(staff_id);

    }
    function showDetails() {
        var DataString = 'staff_id=' + $('#staff_id').val() + '&staff_name=' + $('#staff_name').val() + '&dept_id=' + $('#dept_id').val() + '&cat_id=' + $('#cat_id').val() + '&option=loadfilter_det';
        $.ajax({
            url: "RoleAllocationController.do", data: DataString, type: "post",
            success: function (data)
            {
                $('#student_det').html(data);
                $('.js-modal-buttons .btn').on('click', function () {
                    var color = $(this).data('color');
                    $('#mdModal .modal-content').removeAttr('class').addClass('modal-content modal-col-' + color);
                    $('#mdModal').modal('show');
                });
            }
        });

    }
</script>


<section class="content">

    <div class="row clearfix">
        <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
            <div class="card">
                <div class="header bg-blue">
                    <h2>
                       Select Staff 
                    </h2>
                </div>
              
                <div class="body panel-body">
    <form class="form-horizontal" > 
        <div class="row clearfix">
            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                <div class="row">
                    <div class="col-md-3">
                        <div class="input-group input-group-sm">                            
                            <div class="form-line">
                                <input class="form-control" id="staff_id" placeholder="Staff Id" type="text">
                            </div>
                            <span class="input-group-addon">
                                <button type="button" class="btn bg-orange waves-effect" data-toggle="modal" data-target="#largeModal"  onclick='showDetails()'>
                                    <i class="material-icons">search</i>
                                </button>
                            </span>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="input-group input-group-sm">                            
                            <div class="form-line">
                                <input class="form-control" id="staff_name" placeholder="Staff Name" type="text">
                            </div>
                            <span class="input-group-addon">
                                <button type="button" class="btn bg-orange waves-effect" data-toggle="modal" data-target="#largeModal"  onclick='showDetails()'>
                                    <i class="material-icons">search</i>
                                </button>
                            </span>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <span class="input-group input-group-sm">                            
                            <div class="form-group form-float">
                                <div class="form-line">
                                    <select class="form-control show-tick" data-selected-text-format="count" data-count-selected-text="{0} Selected  " name="dept_id" multiple id="dept_id" >

                                        <option value="" disabled selected>-- Department --</option>
                                        <%                                                    db.read("SELECT CONCAT('<option value=\"',md.department_id,'\" >',md.dept_name,'</option>') val FROM camps.master_department md WHERE md.status>0");
                                            while (db.rs.next()) {
                                                out.print(db.rs.getString("val"));
                                            }
                                        %>
                                    </select>
                                </div>                            
                            </div>
                            <span class="input-group-addon">
                                <button type="button" class="btn bg-orange waves-effect" data-toggle="modal" data-target="#largeModal"  onclick='showDetails()'>
                                    <i class="material-icons">search</i>
                                </button>
                            </span>
                        </span>
                    </div>
                    <div class="col-md-3">
                        <div class="input-group input-group-sm">                            

                            <div class="form-group form-float">
                                <div class="form-line">
                                    <select class="form-control show-tick" name="cat_id" multiple id="cat_id" >

                                        <option value="" selected disabled>-- Category --</option>
                                        <%
                                            db.read("SELECT CONCAT('<option value=\"',msc.sc_id,'\" >',msc.category_name,'</option>')val FROM camps.master_staff_category msc WHERE msc.status>0");
                                            while (db.rs.next()) {
                                                out.print(db.rs.getString("val"));
                                            }
                                        %>
                                    </select>
                                </div>                            
                            </div>

                            <span class="input-group-addon">
                                <button type="button" class="btn bg-orange waves-effect"  onclick='showDetails()'>
                                    <i class="material-icons">search</i>
                                </button>
                            </span>
                        </div>
                    </div>
                </div>

            </div>

        </div>
    </form>
</div>
               
            </div>
        </div>
    </div>
    <div class="row clearfix">
	    <div class="col-xs-12 ol-sm-12 col-md-12 col-lg-12">
	        <div class="panel-group full-body" id="accordion_19" role="tablist" aria-multiselectable="true">
	            <div class="panel panel-col-blue">
	                <div class="panel-heading" role="tab" id="headingOne_19">
	                    <h4 class="panel-title">
	                        <a role="button" data-toggle="collapse" href="#collapseOne_19" aria-expanded="true" aria-controls="collapseOne_19">
	                            <i class="material-icons">perm_contact_calendar</i> Personal Details </a>
	                    </h4>
	                </div>
	                <div id="collapseOne_19" class="panel-collapse collapse in"  role="tabpanel" aria-labelledby="headingOne_19">
	                    <div class="panel-body" id='loadstu_det'>
	
	                    </div>
	                </div>
	            </div>
	        </div>
	    </div>
    </div>
    <div class="row clearfix">
	    <div class="col-xs-12 ol-sm-12 col-md-12 col-lg-12">
	        <div class="panel-group full-body" id="accordion_23" role="tablist" aria-multiselectable="true">
	            <div class="panel panel-col-blue">
	                <div class="panel-heading" role="tab" id="headingOne_23">
	                    <h4 class="panel-title">
	                        <a role="button" data-toggle="collapse" href="#collapseOne_23" aria-expanded="true" aria-controls="collapseOne_23">
	                            <i class="material-icons">person_add</i> Role Staff Mapping </a>
	                    </h4>
	                </div>
	                <div id="collapseOne_23" class="panel-collapse collapse in"  role="tabpanel" aria-labelledby="headingOne_23">
	                    <div class="panel-body" id='load_branch_det'>
	
	                    </div> 
	                </div>
	            </div>
	        </div>
	    </div>
    </div>


</section>
<% } catch (Exception e) {
    } finally {
        db.closeConnection();
    }
%>
<div class="modal fade" id="largeModal" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="largeModalLabel">Staff Details</h4>
            </div>
            <div class="modal-body" id="student_det">

            </div>
            <div class="modal-footer">                
                <button type="button" class="btn btn-link waves-effect" data-dismiss="modal">CLOSE</button>
            </div>
        </div>
    </div>
</div>

<%@include file="../../CommonJSP/pageFooter.jsp" %>
