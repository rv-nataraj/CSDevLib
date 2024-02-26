<%@include file="../../CommonJSP/pageHeader.jsp" %>
<script src="https://cdn.campusstack.in/assets/js/pages/CommonJSP/sign-in.js"></script>
 <script src="https://cdn.campusstack.in/assets/plugins/jquery-validation/jquery.validate.js"></script>
 <script src="https://cdn.campusstack.in/assets/plugins/jquery-validation/additional-methods.js"></script>
<section class="content">
    <script>
        function updatePassword() {
        var DataString = 'oldPassword='+$('#password').val()+'&newPassword='+$('#password1').val()+'&confirmPassword='+$('#password2').val()+'&option=changePassword.do';
        $.ajax({
            url: "changePassword.do", data: DataString, type: "post",
            success: function (data)
            {
                $('#status').html(data);
            }
        });
    }
    </script>
    <div class="container-fluid">
        <div class="block-header">
            <h2>Change Password</h2>
             <div class="card">
                <div class="body">
            <form id="sign_in" action="changePassword.do" method="POST">
                <div class="input-group">
                    <span class="input-group-addon">
                        <i class="material-icons">lock</i>
                    </span>
                    <div class="form-line">
                        <input type="password" class="form-control" name="oldPassword" autocomplete="off" data-rule-varchar="true" id="password"  placeholder="Current Password" required>
                    </div>
                </div>
                <div class="input-group">
                    <span class="input-group-addon">
                        <i class="material-icons">lock</i>
                    </span>
                    <div class="form-line">
                        <input type="password" class="form-control" name="newPassword" minlength="8"  autocomplete="off" data-rule-varchar="true" id="password1" data-rule-checkpasswd="true"  placeholder="New Password" required>
                    </div>
                </div>
                <div class="input-group">
                    <span class="input-group-addon">
                        <i class="material-icons">lock</i>
                    </span>
                    <div class="form-line">
                        <input type="password" class="form-control" name="confirmPassword" minlength="8" autocomplete="off" data-rule-varchar="true" equalTo="#password1" id="password2"  placeholder="Reenter New Password" required>
                    </div>
                </div>
                <div class="msg ">               
                    <center>
                        <button class="btn bg-orange waves-effect btn-group-sm" onclick='updatePassword()' type="button">Reset</button>   
                    </center>
                </div>
            </form>
                    <div id='status'></div>
        </div>
             </div></div>
    </div>
    <br>
    <div style="text-align:center"><a href="../home/pinned_resources.jsp"> <button class="btn bg-orange waves-effect btn-group-sm"> Goto Home Page </button> </a></div>
</section>
<%@include file="../../CommonJSP/pageFooter.jsp" %>