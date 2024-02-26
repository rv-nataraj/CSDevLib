<%@include file="../../CommonJSP/pageHeader.jsp" %>

<style>
textarea:focus, input:focus,select:focus {
  outline: none; 
  border: 3px solid red;
}</style>

<style>
/* Chrome, Safari, Edge, Opera */
input::-webkit-outer-spin-button,
input::-webkit-inner-spin-button {
  -webkit-appearance: none;
  margin: 0;
}

/* Firefox */
input[type=number] { -moz-appearance: textfield;
}
select {
  -webkit-appearance: none;
  -moz-appearance: none;
  text-indent: 1px;
  text-overflow: '';
}
</style>

<script>
	function clearDivisions() {
		$('#loadRegulationCourses').html("");
	}
	function loadDivisions() {
		loadRegulationCourses();
	}

    function loadRegulationCourses() {
    	if  ($('#regulation_id').val().length>0) {
        var DataString = 'regulation_id=' + $('#regulation_id').val() + '&option=loadRegulationCourses';
        $.ajax({
            url: "CurriculumController.do", data: DataString, type: "post",
            success: function (data) {
                $('#loadRegulationCourses').html(data);
            }
        });
    	}
    	else
    		alert("Select Regulation and Programme!!!");
    }
    
    function updateCourseAttributes(subject_id,field_id) {
        var DataString = 'regulation_id=' + $('#regulation_id').val() +'&field_id='+field_id+'&subject_id='+subject_id+ '&option=updateCourseAttributes';
        if (field_id==1) DataString=DataString + '&field_value=' + $('#dc_'+subject_id).val();
        if (field_id==2) DataString=DataString + '&field_value=' + $('#sc_'+subject_id).val();
        if (field_id==3) DataString=DataString + '&field_value=' + $('#sn_'+subject_id).val();
        if (field_id==4) DataString=DataString + '&field_value=' + $('#l_'+subject_id).val();
        if (field_id==5) DataString=DataString + '&field_value=' + $('#t_'+subject_id).val();
        if (field_id==6) DataString=DataString + '&field_value=' + $('#p_'+subject_id).val();
        if (field_id==7) DataString=DataString + '&field_value=' + $('#c_'+subject_id).val();
        if (field_id==8) DataString=DataString + '&field_value=' + $('#iim_'+subject_id).val();
        if (field_id==9) DataString=DataString + '&field_value=' + $('#mim_'+subject_id).val();
        if (field_id==10) DataString=DataString + '&field_value=' + $('#lem_'+subject_id).val();
        if (field_id==11) DataString=DataString + '&field_value=' + $('#mem_'+subject_id).val();
        if (field_id==12) DataString=DataString + '&field_value=' + $('#o_'+subject_id).val();
        if (field_id==13) DataString=DataString + '&field_value=' + $('#sem_sub_'+subject_id).val();
        if (field_id==14) DataString=DataString + '&field_value=' + $('#st_'+subject_id).val();
        //alert(DataString);
        $.ajax({
            url: "CurriculumController.do", data: DataString, type: "post",
            success: function (data) {
       
            }
        });
    }
    
    function updateElectiveAttributes(subject_id,field_id) {
        var DataString = 'regulation_id=' + $('#regulation_id').val() +'&field_id='+field_id+'&subject_id='+subject_id+ '&option=updateElectiveAttributes';
        if (field_id==1) DataString=DataString + '&field_value=' + $('#ce_'+subject_id).val();
        if (field_id==2) DataString=DataString + '&field_value=' + $('#oe_'+subject_id).val();
        if (field_id==3) DataString=DataString + '&field_value=' + $('#sem_sub_e'+subject_id).val();
        if (field_id==4) DataString=DataString + '&field_value=' + $('#ste_'+subject_id).val();
        if (field_id==5) DataString=DataString + '&field_value=' + $('#sne_'+subject_id).val();
       
        //alert(DataString);
        $.ajax({
            url: "CurriculumController.do", data: DataString, type: "post",
            success: function (data) {
       			
            }
        });
    }
    
    function insertCourse() {
    	if ( $('#dc_sub').val().length>0 && $('#sc_sub').val().length>0 && $('#sn_sub').val().length>0 && $('#l_sub').val().length>0 && $('#t_sub').val().length>0 && $('#p_sub').val().length>0 && $('#c_sub').val().length>0 && $('#iim_sub').val().length>0 &&
    			$('#mim_sub').val().length>0 && $('#lem_sub').val().length>0 && $('#mem_sub').val().length>0 && $('#o_sub').val().length>0 && $('#sem_sub').val().length>0 && $('#st_sub').val().length>0 ) {
	        var DataString = 'regulation_id=' + $('#regulation_id').val() + '&option=insertCourse';
	        DataString = DataString + '&dc_sub=' + $('#dc_sub').val();
	        DataString = DataString + '&sc_sub=' + $('#sc_sub').val();
	        DataString = DataString + '&sn_sub=' + $('#sn_sub').val();
	        DataString = DataString + '&l_sub=' + $('#l_sub').val();
	        DataString = DataString + '&t_sub=' + $('#t_sub').val();
	        DataString = DataString + '&p_sub=' + $('#p_sub').val();
	        DataString = DataString + '&c_sub=' + $('#c_sub').val();
	        DataString = DataString + '&iim_sub=' + $('#iim_sub').val();
	        DataString = DataString + '&mim_sub=' + $('#mim_sub').val();
	        DataString = DataString + '&lem_sub=' + $('#lem_sub').val();
	        DataString = DataString + '&mem_sub=' + $('#mem_sub').val();
	        DataString = DataString + '&o_sub=' + $('#o_sub').val();
	        DataString = DataString + '&sem_sub=' + $('#sem_sub').val();
	        DataString = DataString + '&st_sub=' + $('#st_sub').val();
	        DataString = DataString + '&row_id=sub';
	        //alert(DataString);
	        $.ajax({
	            url: "CurriculumController.do", data: DataString, type: "post",
	            success: function (data) {
	                //alert(data);
	                //$('#debuginfo').html(data);
	                loadRegulationCourses();
	            }
	        });
    	}
    	else
    		alert("Enter values for all fields!!!");
    }
    
    function deleteCourse(subject_id) {
    	if (confirm("Are you sure want to remove this course from this regulation?")) {
	        var DataString = 'regulation_id=' + $('#regulation_id').val() +'&subject_id=' + subject_id + '&option=deleteCourse';
	        //alert(DataString);
	        $.ajax({
	            url: "CurriculumController.do", data: DataString, type: "post",
	            success: function (data) {
	                //alert(data);
	                loadRegulationCourses();
	            }
	        });
    	}
    }
    
    function insertElectiveSlot() {
    	if ($('#sn_ele').val().length>0 && $('#c_ele').val().length>0 && $('#o_ele').val().length>0 && $('#sem_ele').val().length>0 && $('#st_ele').val().length>0 ) {
	        var DataString = 'regulation_id=' + $('#regulation_id').val() + '&option=insertElectiveSlot';
	        DataString = DataString + '&sn_ele=' + $('#sn_ele').val();
	        DataString = DataString + '&c_ele=' + $('#c_ele').val();
	        DataString = DataString + '&o_ele=' + $('#o_ele').val();
	        DataString = DataString + '&sem_ele=' + $('#sem_ele').val();
	        DataString = DataString + '&st_ele=' + $('#st_ele').val();
	        DataString = DataString + '&row_id=ele';
	        //alert(DataString);
	        $.ajax({
	            url: "CurriculumController.do", data: DataString, type: "post",
	            success: function (data) {
	                //alert(data);
	                loadRegulationCourses();
	            }
	        });
    	}
    	else
    		alert("Enter values for all elective fields!!!");
    }
    
    function deleteElectiveSlot(elective_id) {
    	if (confirm("Are you sure want to remove this Elective Slot from this regulation?")) {
	        var DataString = 'regulation_id=' + $('#regulation_id').val() +'&elective_id=' + elective_id + '&option=deleteElectiveSlot';
	        //alert(DataString);
	        $.ajax({
	            url: "CurriculumController.do", data: DataString, type: "post",
	            success: function (data)  {
	                //alert(data);
	                loadRegulationCourses();
	            }
	        });
    	}
    }
    
    function freezeElectiveSlot(elective_id) {
    	if (confirm("Are you sure want to freeze this Elective Slot?")) {
	        var DataString = 'regulation_id=' + $('#regulation_id').val() +'&elective_id=' + elective_id + '&option=freezeElectiveSlot';
	        //alert(DataString);
	        $.ajax({
	            url: "CurriculumController.do", data: DataString, type: "post",
	            success: function (data) {
	                //alert(data);
	                loadRegulationCourses();
	            }
	        });
    	}
    }
    
    function freezeCourse(subject_id) {
    	if (confirm("Are you sure want to freeze this course?")) {
	        var DataString = 'regulation_id=' + $('#regulation_id').val() +'&subject_id=' + subject_id + '&option=freezeCourse';
	        //alert(DataString);
	        $.ajax({
	            url: "CurriculumController.do", data: DataString, type: "post",
	            success: function (data) {
	                //alert(data);
	                loadRegulationCourses();
	            }
	        });
    	}
    }
</script>
<%    
    DBConnect db = new DBConnect();
    try {
        db.getConnection();
%>
<section class="content">
        <div class="row form-horizontal clearfix">
		<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
			<div class="card">
				<div class="header">
					<h2>Regulation Selection</h2>
				</div>
				<div class="body">
					<div class="row clearfix">
						<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">

							<%
							String query="SELECT CONCAT('<option  value=\"',rm.regulation_id,'\" ',' >',CONCAT(rm.regulation_name,' ', mp.`programme_name`,' ',mb.`branch_name`,' (',rm.`regulation_id`,') '),'</option>') val FROM curriculum.regulation_master rm INNER JOIN camps.`master_branch` mb ON mb.`branch_id`=rm.`branch_id` INNER JOIN camps.`master_programme` mp ON mp.`programme_id`=mb.`programme_id`  ORDER BY rm.regulation_id desc, mp.`programme_level` DESC ,mb.`branch_id`";
							db.read(query);
							if (db.rs.next()) {
								db.rs.beforeFirst();
							%>
							<div class="row">
								<div class="col-sm-10">
									<div class="form-group form-float">
										<label for="Subject" class="control-label col-sm-4">Regulation<super style="color: red;">*</super>
										</label>
										<div class="col-sm-8">
											<select class="form-control show-tick" name="regulation_id"		id="regulation_id" onchange="clearDivisions()"	data-live-search="true"> <option value="">-- Select Regulation --</option>
												<%
												while (db.rs.next()) {
													out.print(db.rs.getString("val"));
												}
												%>
											</select>
										</div>
									</div>
								</div>

							</div>

							<div class="row">
								<div class="col-sm-10">
									<div class="form-group form-float" style="text-align: center">
										<button class="btn bg-orange waves-effect"  onclick="loadDivisions()">Load Data</button>
									</div>
								</div>
							</div>

							<%
							} else
							out.print("No Regulation Available for Syllabus Incharge Entry");
							%>
						</div>
					</div>
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
                                <i class="material-icons">perm_contact_calendar</i> Course Details </a>
                        </h4>
                    </div>
                    <div id="collapseOne_19" class="panel-collapse collapse in"  role="tabpanel" aria-labelledby="headingOne_19">
                        <div class="panel-body" id='loadRegulationCourses'>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <div style="display:none" id="debuginfo">  </div>
</section>
<% } catch (Exception e) {
        out.print(e);
    } finally {
        db.closeConnection();
    }
%>

<%@include file="../../CommonJSP/pageFooter.jsp" %>
