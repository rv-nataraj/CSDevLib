<%@include file="../../CommonJSP/pageHeader.jsp" %>

<script src="https://cdn.campusstack.in/assets/plugins/jquery-validation/jquery.validate.js"></script>
<script src="https://cdn.campusstack.in/assets/plugins/jquery-validation/additional-methods.js"></script>
<script src="https://cdn.campusstack.in/assets/js/pages/CommonJSP/validation.js"></script>
<script src="https://cdn.campusstack.in/assets/plugins/bootstrap-notify/bootstrap-notify.js"></script>
<script src="https://cdn.campusstack.in/assets/plugins/jquery-inputmask/jquery.inputmask.bundle.js"></script>
<script src="https://cdn.campusstack.in/assets/plugins/jquery-validation/jquery-validate.bootstrap-tooltip.js"></script>
<script src="https://cdn.campusstack.in/assets/plugins/bootstrap-notify/bootstrap-notify.js"></script>
<script src="https://cdn.campusstack.in/assets/plugins/jquery-datatable/jquery.dataTables.js"></script>
<script src="https://cdn.campusstack.in/assets/plugins/jquery-datatable/skin/bootstrap/js/dataTables.bootstrap.js"></script>
<script src="https://cdn.campusstack.in/assets/plugins/jquery-datatable/extensions/export/dataTables.buttons.min.js"></script>
<script src="https://cdn.campusstack.in/assets/plugins/jquery-datatable/extensions/export/buttons.flash.min.js"></script>
<script src="https://cdn.campusstack.in/assets/plugins/jquery-datatable/extensions/export/jszip.min.js"></script>
<script src="https://cdn.campusstack.in/assets/plugins/jquery-datatable/extensions/export/pdfmake.min.js"></script>
<script src="https://cdn.campusstack.in/assets/plugins/jquery-datatable/extensions/export/vfs_fonts.js"></script>
<script src="https://cdn.campusstack.in/assets/plugins/jquery-datatable/extensions/export/buttons.html5.min.js"></script>
<script src="https://cdn.campusstack.in/assets/plugins/jquery-datatable/extensions/export/buttons.print.min.js"></script>

<script src="https://cdn.campusstack.in/assets/plugins/jquery-validation/jquery.validate.js"></script>
<script src="https://cdn.campusstack.in/assets/plugins/jquery-validation/additional-methods.js"></script>   
<script src="https://cdn.campusstack.in/assets/js/pages/CommonJSP/validation.js"></script>
<script>

    var count = 1;
    function loaddata() {
        if ($('#form1').valid())
        {

            var title = 'SEARCH RESOURCE';
            var data = $('input[name=data]').map(function ()
            {
                return $(this).val();
            }).get().join(",");
            var field_id = $('select[name=field_id]').map(function ()
            {
                return $(this).val();
            }).get().join(",");
            var DataString = 'data=' + encodeURIComponent(data) + '&condition=' + $('input[name=condition]:checked').val() + '&cat_id=' + $('input[name=category]:checked').val() + '&field_id=' + field_id + '&from_year=' + $('#from_year').val() + '&to_year=' + $('#to_year').val() + '&field_id1=' + $('#field_id1').val() + '&data1=' + encodeURIComponent($('#data1').val()) + '&option=load_resource';
            //      var DataString = 'field_id1=' + $('#field_id1').val() + '&condition=' + $('input[name=condition]:checked').val() + '&cat_id=' + $('input[name=category]:checked').val() + '&data1=' + encodeURIComponent($('#data1').val()) + '&field_id2=' + $('#field_id2').val() + '&data2=' + $('#data2').val() + '&field_id3=' + $('#field_id3').val() + '&data3=' + $('#data3').val() + '&from_year=' + $('#from_year').val() + '&to_year=' + $('#to_year').val() + '&option=load_resource';
            $.ajax({
                url: "ResourceSearchController.do", data: DataString, type: "post",
                success: function (data)
                {
                    $('#load_res').html(data);
                    $('#load_res thead td').each(function () {
                        var title = $(this).text();
                        $(this).html('<input type="text" placeholder="Search ' + title + '" />');
                    });
                    var table = $('#load_res .table').DataTable({
                        "processing": true,
                        "lengthMenu": [[25, 50, 100, -1], [25, 50, 100, "All"]],
                        dom: 'lBfrtip',
                        responsive: true,
                        buttons: [
                            'copy', 'csv', 'excel', {
                                extend: 'pdf',
                                title: title,
                                orientation: 'portrait',
                                pageSize: 'A4'
                            }, {
                                extend: 'print',
                                title: title,
                                orientation: 'portrait',
                                pageSize: 'A4'
                            }
                        ]
                    });

                    // Apply the search
                    table.columns().every(function () {
                        var that = this;

                        $('input', this.header()).on('keyup change clear', function () {
                            if (that.search() !== this.value) {
                                that
                                        .search(this.value)
                                        .draw();
                            }
                        });
                    });
                }
            });
        }

    }

    function addrow(addrow)
    {
        var div = document.getElementById(addrow);
        var row = document.createElement("div");
        row.id = "div_id" + count;
        row.className = "row";
        //      var td1 = document.createElement("div>");
//        $.ajax({
//            type: 'post',
//            url: 'fee_update_log',
//            data: 'drpName=load_feetype' + '&view_type=fee_update',
//            success: function (responseText) {
//                var strHtml1 = "";
//                strHtml1 += "<select name='fee_type[]' id='fee_type" + (count - 1) + "' class='form-control show-tick feetype'  onchange='fixed_amount(" + (count - 1) + ")'><option value=0><--select--></option>";
//                strHtml1 += responseText;
//                strHtml1 += "</select>";
//                td1.innerHTML = strHtml1;
//                validate();
//            }
//        });   
        var div2 = document.createElement("div");
        div2.class = "row";
        $.ajax({
            type: 'post',
            url: 'ResourceSearchController.do',
            data: 'option=load_resource_title',
            success: function (data) {
                var strHtml2 = "<div class='col-sm-6'><div class='form-group form-float'><div class='form-line focused'><select name='field_id' id='field_id" + parseInt(count) + "' class='form-control show-tick' required  data-live-search='true' data-actions-box='true' >";
                strHtml2 += data;
                strHtml2 += "</select><label class='form-label'>Field Name</label></div></div></div></div>";

                div2.innerHTML = strHtml2;
                $("#field_id" + count).selectpicker('refresh');
            }
        });
        var div3 = document.createElement("div");
        var strHtml3 = "  <div class='col-sm-5'><div class='form-group form-float'><div class='form-line focused'><input type='text'class='form-control' required id='data_" + (count + 1) + "' name='data'><label class='form-label'>Data</label></div></div></div></div></div>"
        div3.innerHTML = strHtml3;

        var div4 = document.createElement("div");
        var strHtml4 = "  <div class='col-sm-1'><div class='form-group form-float'><button type='button' class='btn-close bg-orange' aria-label='Close' onclick='remove_row(" + parseInt(count) + ")'> <span aria-hidden='true'>&times;</span></button></div></div></div></div>"
        div4.innerHTML = strHtml4;
//        var td3 = document.createElement("TD");
//        var strHtml3 = "<input type='text' name='amount[]' id='amt" + count + "' style='color:black;background-color:white;'  class='form-control amt' onchange=''>";   
//        td3.innerHTML = strHtml3;
//
//        var td4 = document.createElement("TD");
//        var strHtml4 = "";
//        strHtml4 += "<select  name='acc_type[]' id='acc_type" + count + "' style='color:black;'class='form-control show-tick acc_type' onchange=''><option value=0><--select--></option>";
//        strHtml4 += "<option value='1'>Debit(Receivable)</option><option value='-1'>Credit (Refund)</option>";
//        strHtml4 += "</select>";
//        td4.innerHTML = strHtml4;
//        validate();
//  
//        var td5 = document.createElement("TD");
//        var strHtml5 = "<textArea style='color:black;' name='desc[]' id='desc" + count + "' class='form-control desc'>";
//        td5.innerHTML = strHtml5;
//
//        var td6 = document.createElement("TD");
//        var strHtml6 = "<input type='button' name='delete' id='delete" + count + "' class='btn bg-orange' value='Delete' onclick='delrow(" + count + ")'>";
//        td6.innerHTML = strHtml6;

        //     row.appendChild(td1);
        row.appendChild(div2);
        row.appendChild(div3);
        row.appendChild(div4);
//        row.appendChild(td5);     
//        row.appendChild(td6);
        count = parseInt(count) + 1;
        div.appendChild(row);
        //     $("#submit_sec").show();

    }
    function remove_row(a) {
        $("#div_id" + a).closest("div").remove();
    }

</script>
<%    DBConnect db = new DBConnect();
    try {
        db.getConnection();

%>
<section class="content">

	<div class="row clearfix">
            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                <div class="card">
                    <div class="header" style="background-color:#1a7cab;"> 
                        <h2>
                            <center>Online Public Access Catalogue (OPAC)</center>
                        </h2>
                    </div>
                    </div>
                    </div>
                    </div>
    <form id="form1" method="post" action="ResourceSearchController.do" >
        <div class="row clearfix">
            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                <div class="card">
                    <div class="header"> 
                        <h2>
                            Resource Filter (OPAC)
                        </h2>
                    </div>
                    <div class="body">
                        <div class="row clearfix">
                            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                <div class="row">
                                    <div class="col-sm-6">   
                                        <div class="form-group form-float">
                                            <div class="form-line focused">
                                                Search Condition: 
                                                <input name="condition" type="radio" id="condition1" required value="AND" class="radio-col-lime" />
                                                <label for="condition1">All Fields</label>
                                                <input name="condition" type="radio" id="condition2" required value="OR" class="radio-col-lime" />
                                                <label for="condition2">Any Fields</label>

                                            </div></div></div>
                                    <div class="col-sm-6">
                                        <div class="form-group form-float">
                                            <div class="form-line focused">    
                                                Resource type:
                                                <%	db.read("SELECT cm.cat_id,cm.cat_name FROM library.category_master cm  WHERE cm.status>0");
                                                    while (db.rs.next()) {
                                                        out.print("<input name=\"category\" required type=\"radio\" id=\"" + db.rs.getString("cat_id") + "\" value=\"" + db.rs.getString("cat_id") + "\" class=\"radio-col-lime\"/> <label for=\"" + db.rs.getString("cat_id") + "\">" + db.rs.getString("cat_name") + "</label>");
                                                    }
                                                %>
                                            </div></div></div>    
                                </div>
                            </div>
                            <div class="row"></div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group form-float">
                                        <div class="form-line focused">
                                            <select class="form-control show-tick" name="field" id="field_id1" required  data-live-search="true" data-actions-box="true" >   
                                                <option value="">--select-- </option> 
                                                <!-- 
                                              
                                                   db.read("SELECT CONCAT( '<option value=\"', fa.fa_attribute, '\">', fa.fa_name, '</option>' ) opt FROM camps.form_attribute fa WHERE fa.fm_id=21 AND fa.fa_id NOT IN (147,148,149,152,151,153,154,157,155,194,200,156) ORDER BY fa.order_no AND fa.status>0");
                                                -->
                                                <%
                                                    db.read("SELECT CONCAT( '<option value=\"', fa.fa_attribute, '\">', fa.fa_name, '</option>' ) opt FROM camps.form_attribute fa WHERE fa.fm_id IN (21,18) AND fa.fa_id NOT IN (147,148,149,152,151,153,154,157,155,194,200,156,138) ORDER BY fa.order_no AND fa.status>0");

                                                    while (db.rs.next()) {
                                                       out.print(db.rs.getString("opt"));
                                                    }
                                                %>   
                                            </select><label class="form-label">Field Name</label>
                                        </div>                            
                                    </div>
                                </div>
                                <div class="col-sm-5">
                                    <div class="form-group form-float">   
                                        <div class="form-line focused">    
                                            <input type='text'class="form-control" required id="data1" name='data_val'><label class="form-label">Data</label>
                                        </div>                            
                                    </div>
                                    <div class="col-sm-1"></div>  
                                </div>    


                            </div>
                            <!--                            <div class="row">
                                                            <div class="col-sm-6">
                                                                <div class="form-group form-float">
                                                                    <div class="form-line focused">
                            
                                                                        <select class="form-control show-tick" name="field_id2" id="field_id2"  data-live-search="true" data-actions-box="true" >
                                                                            <option value="">--select-- </option>
                                                                                                                                    db.read("SELECT CONCAT( '<option value=\"', fa.fa_attribute, '\">', fa.fa_name, '</option>' ) opt FROM camps.form_attribute fa WHERE fa.fm_id=21 AND fa.fa_id NOT IN (150,147,152,153,154,157,194,200,156,195) ORDER BY fa.order_no and fa.status>0");
                                                                                while (db.rs.next()) {
                                                                                    out.print(db.rs.getString("opt"));
                                                                                }
                                                                          
                                                                        </select><label class="form-label">Field Name</label>
                                                                    </div>                            
                                                                </div>
                            
                                                            </div>
                                                            <div class="col-sm-6">
                                                                <div class="form-group form-float">
                                                                    <div class="form-line focused">
                                                                        <input type='text'class="form-control"  id="data2" name='data2'><label class="form-label">Data</label>
                                                                    </div>                            
                                                                </div>
                            
                                                            </div>
                            
                            
                                                        </div>-->
                            <!--                            <div class="row">   
                                                            <div class="col-sm-6">
                                                                <div class="form-group form-float">
                                                                    <div class="form-line focused">
                            
                                                                        <select class="form-control show-tick" name="field_id3" id="field_id3"  data-live-search="true" data-actions-box="true" >
                                                                            <option value="">--select-- </option>
                                                                                                                                    db.read("SELECT CONCAT( '<option value=\"', fa.fa_attribute, '\">', fa.fa_name, '</option>' ) opt FROM camps.form_attribute fa WHERE fa.fm_id=21 AND fa.fa_id NOT IN (150,147,152,153,154,157,194,200,156,195) ORDER BY fa.order_no and fa.status>0");
                                                                                while (db.rs.next()) {
                                                                                    out.print(db.rs.getString("opt"));
                                                                                }
                                                                           
                                                                        </select><label class="form-label">Field Name</label>
                                                                    </div>                            
                                                                </div>
                            
                                                            </div>
                            
                                                            <div class="col-sm-6">
                                                                <div class="form-group form-float">
                                                                    <div class="form-line focused">
                            
                                                                        <input type='text'class="form-control"  id="data3" name='data3'><label class="form-label">Data</label>
                                                                    </div>                            
                                                                </div>
                            
                                                            </div>
                            
                            
                                                        </div>-->
                            <div  id="addrow"> 
                            </div>                                            

                        </div>

                        <div><label class="form-label">Publication Year</label><table  ><tr><td>From:&nbsp;&nbsp;</td><td><input style="width: 100%"type='text' class="form-control"   id="from_year" name='from_year'>
                                    </td><td>&nbsp;&nbsp;To:&nbsp;&nbsp;</td><td><input type='text'  class="form-control"  id="to_year" name='to_year'></td><td>&nbsp;&nbsp;</td></tr></table>  </div>  
                        <center><button class="btn btn-primary waves-effect" name="option" id="option" value='resource_search' onclick='loaddata()' type="button">SEARCH</button>
                            <button class="btn btn-primary waves-effect" name="reset" id="reset" value='reset' onclick='reset()' type="button">RESET</button>
                            <button id='addbutn' name='addbutn' class='btn bg-orange' value='ADD' onclick='addrow("addrow")' type="button">ADD</button></center>
                    </div>

                </div>

            </div>

        </div>


    </form>
    <div class="row clearfix">
        <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
            <div class="card">
                <div class="body">
                    <div class="body"><div class="table-responsive"  id='load_res'>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<% } catch (Exception e) {
        out.print(e);
    } finally {
        db.closeConnection();
    }
%>
<script>

$( document ).ready(function() {
    $("body").addClass("ls-closed");
});
</script>
<%@include file="../../CommonJSP/pageFooter.jsp" %>

