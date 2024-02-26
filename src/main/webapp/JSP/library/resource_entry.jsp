<%@include file="../../CommonJSP/pageHeader.jsp" %>
<%@ include file="../upload.jsp" %>
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

<script src="https://cdn.campusstack.in/assets/plugins/bootpag/jquery.bootpag.js"></script>

<script>

	function clearDivisions() {
		$('#loadDetailsUpdateUI').html("");
		$('#addResourceData').html(""); 
	}
	function loadDivisions() {
		loadResourceCount("");
		loadAddResourceData();
		loadResourceDataUpdateUI("",1);
	}
	
	function searchPublisher(resource_id) {
	    var DataString = 'search_string=' + $('#search'+resource_id).val() + '&resource_id=' + resource_id + '&option=searchPublisher';
	    //alert(DataString);
	    $.ajax({
	        url: "ResourceEntryController.do", data: DataString, type: "post",
	        success: function (data)
	        {
	        	//alert(data);
	            $('#student_det').html(data);
	            //$('.js-modal-buttons .btn').on('click', function () {
	            //    var color = $(this).data('color');
	            //    $('#mdModal .modal-content').removeAttr('class').addClass('modal-content modal-col-' + color);
	             //   $('#mdModal').modal('show');
	            //});
	        }
	    });
	}
	
	 function loadResourceCount(search_data) {
	        var DataString = 'search_data=' +search_data+ '&option=loadResourceCount';
	        //alert(DataString);
	        $.ajax({
	            url: "ResourceEntryController.do", data: DataString, type: "post",
	            success: function (data)
	            {
	                $('#pagination-here').bootpag({
	                    total: data,
	                    page: 1,
	                    maxVisible: 5,
	                    leaps: true,
	                    firstLastUse: true,
	                    first: '<',
	                    last: '>',
	                    wrapClass: 'pagination',
	                    activeClass: 'active',
	                    disabledClass: 'disabled',
	                    nextClass: 'next',
	                    prevClass: 'prev',
	                    lastClass: 'last',
	                    firstClass: 'first'
	                }).on("page", function (event, page_num) {
	                	loadResourceDataUpdateUI($("#search_data").val(),page_num)
	                });
	            }
	        });
	    }

    function loadResourceDataUpdateUI(search_data,page_num) {
		$('#loadDetailsUpdateUI').html("Loading Resource Details... pls wait");
	    var DataString =  'search_data=' +search_data+ '&option=loadResourceDataUpdateUI' + '&page_num='+(page_num=== undefined  ?1:page_num);
        //alert(DataString);
        $.ajax({
            url: "ResourceEntryController.do", data: DataString, type: "post",
            success: function (data) {
	         	$('#loadDetailsUpdateUI').html("<input class='form-control' type='text' id='search_data' placeholder='Search for names..' value='"+search_data+"' title='Type in a name'>" + data);
	            $("#search_data").on("focusout", function () {
	            	loadResourceCount($("#search_data").val());
	             	loadResourceDataUpdateUI($("#search_data").val(),1);
	             });
	             //alert(data);
	         }
	     });
	 }
    
    function loadAddResourceData() {
        var DataString =  'option=loadAddResourceData';
        //alert(DataString);
        $.ajax({
            url: "ResourceEntryController.do", data: DataString, type: "post",
            success: function (data)
            {
                //alert(data);
                $('#addResourceData').html(data); 
            }
        });
    }
    
    function addResourceData() {
		if (  $('#title').val() !="" && $('#authors').val() !="" ) {
	        var DataString =  'title=' + $('#title').val() + '&authors='+ $('#authors').val() + '&option=addResourceData';
	        alert(DataString);
	        $.ajax({
	            url: "ResourceEntryController.do", data: DataString, type: "post",
	            success: function (data)
	            {
	                alert(data);
					loadDivisions();
	            }
	        });
		}
		else 
			alert("Please insert all values!!!");
    }
    
    function updateResourceData(field, resource_id) {
		var DataString = 'fname=' + field + '&fvalue='+ $('#' + field + resource_id).val() + '&resource_id='+ resource_id + '&option=updateResourceData';
		//alert(DataString);
		$.ajax({
			url : "ResourceEntryController.do",	data : DataString,		type : "post",
			success : function(data) {
				//alert(data);
				if (data.startsWith("Error")) 
					alert(data);
				
			}
		});
	}
    
    function updateResourcePublisher(fname, fvalue, resource_id) {
		var DataString = 'fname=' + fname + '&fvalue=' + fvalue + '&resource_id='+ resource_id + '&option=updateResourceData';
		//alert(DataString);
		$.ajax({
			url : "ResourceEntryController.do",	data : DataString,		type : "post",
			success : function(data) {
				//alert(data);
				document.getElementById("publisher_id"+resource_id).value = data;					
				if (data.startsWith("Error")) 
					alert(data);
				
			}
		});
	}
    
    function freezeResourceData(resource_id) {
		var DataString = 'resource_id='+ resource_id + '&option=freezeResourceData';
		//alert(DataString);
		$.ajax({
			url : "ResourceEntryController.do",	data : DataString,		type : "post",
			success : function(data) {
				//alert(data);
				if (data.startsWith("Error")) 
					alert(data);
				
			}
		});
	}
    
    function deleteResourceData(resource_id) {
		var DataString = 'resource_id='+ resource_id + '&option=deleteResourceData';
		//alert(DataString);
		$.ajax({
			url : "ResourceEntryController.do",	data : DataString,		type : "post",
			success : function(data) {
				//alert(data);
				if (data.startsWith("Error")) 
					alert(data);
				
			}
		});
	}
    
	function loadResourceAdditionalDetails(resource_id) {
        var DataString =  'resource_id=' + resource_id + '&option=loadResourceAdditionalDetails';
        //alert(DataString);
        $.ajax({
            url: "ResourceEntryController.do", data: DataString, type: "post",
            success: function (data)
            {
                //alert(data);
                $('#student_det').html(data); 
            }
        });
    }
	
	function loadResourceAccessNoDetails(resource_id) {
        var DataString =  'resource_id=' + resource_id + '&option=loadResourceAccessNoDetails';
        //alert(DataString);
        $.ajax({
            url: "ResourceEntryController.do", data: DataString, type: "post",
            success: function (data)
            {
                //alert(data);
                $('#student_det').html(data); 
            }
        });
    }
	
	function loadLibRack() {
        var DataString =  'lfm_id=' + $('#lfm_id').val() + '&option=loadLibRack';
        //alert(DataString);
        $.ajax({
            url: "ResourceEntryController.do", data: DataString, type: "post",
            success: function (data)
            {
                //alert(data);
                $('#lrm_id').html(data); 
            }
        });
    }
	
	function addResourceAccessNoDetails(resource_id) {
		if (  $('#access_no').val() !="" && $('#lib_id').val() >0 && $('#lfm_id').val() >0 && $('#lsm_id').val() >0 && $('#lrm_id').val() >0 && $('#lpm_id').val() >0  ) {
	        var DataString =  'access_no=' + $('#access_no').val() + '&lib_id='+ $('#lib_id').val() + '&lfm_id='+ $('#lfm_id').val() + '&lsm_id='+ $('#lsm_id').val() + '&lrm_id='+ $('#lrm_id').val() + '&lpm_id='+ $('#lpm_id').val() + '&resource_id=' + resource_id + '&option=addResourceAccessNoDetails';
	        //alert(DataString);
	        $.ajax({
	            url: "ResourceEntryController.do", data: DataString, type: "post",
	            success: function (data)
	            {
	                //alert(data);
	            	if (data.startsWith("Error")) 
						alert(data);
	            }
	        });
		}
		else 
			alert("Please insert all values!!!");
    }
	
	function deleteResourceAccessNoDetails(lbm_id) {
		var DataString = 'lbm_id='+ lbm_id + '&option=deleteResourceAccessNoDetails';
		//alert(DataString);
		$.ajax({
			url : "ResourceEntryController.do",	data : DataString,		type : "post",
			success : function(data) {
				//alert(data);
				if (data.startsWith("Error")) 
					alert(data);
				
			}
		});
	}

	//for multi selection
	const multiSelectWithoutCtrl = (elemSelector) => {
        let selectElements = document.querySelectorAll(elemSelector);
        selectElements.forEach(selectElement => {
            selectElement.addEventListener('mousedown', function (e) {
                e.preventDefault();
                let clickedOption = e.target;
                if (clickedOption.tagName === 'OPTION') {
                    clickedOption.selected = !clickedOption.selected;
                    clickedOption.classList.toggle('selected');
                    selectElement.dispatchEvent(new Event('change'));
                }
            });
        });
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
					<h2>Resource Entry</h2>
				</div>
				<div class="body">
					<div class="row clearfix">
						<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
							<div class="row">
								<div class="col-sm-12">
									<div class="form-group form-float" style="text-align: center">
										<button class="btn bg-orange waves-effect"  onclick="loadDivisions()">Load Resources</button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
        
    <div class="row clearfix">
		<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
			<div class="panel-group full-body" id="pgfb1" role="tablist">
				<div class="panel panel-col-blue">
					<div class="panel-heading" role="tab" id="ph1">
						<h4 class="panel-title">
							<a role="button" data-toggle="collapse" href="#pt1"	aria-expanded="true" aria-controls="pt1"> <i class="material-icons">view_comfy</i> Add Resource	</a>
						</h4>
					</div>
					<div id="pt1" class="panel-collapse collapse in">
						<div class="panel-body" id="addResourceData"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
        
        <div class="row clearfix">
		<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
			<div class="panel-group full-body" id="pgfb1" role="tablist">
				<div class="panel panel-col-blue">
					<div class="panel-heading" role="tab" id="ph2">
						<h4 class="panel-title">
							<a role="button" data-toggle="collapse" href="#pt2"	aria-expanded="true" aria-controls="pt2"> <i class="material-icons">view_comfy</i> Resource Details	</a>
						</h4>
					</div>
					<div id="pt2" class="panel-collapse collapse in">
						<div class="panel-body" id="loadDetailsUpdateUI"></div>
						<div><p id="pagination-here" style="text-align: center;"></p></div>
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
                <h4 class="modal-title" id="largeModalLabel">Details</h4>
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