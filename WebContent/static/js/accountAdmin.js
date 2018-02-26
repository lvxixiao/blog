document.write("<script type='text/javascript' src='http://127.0.0.1:8080/lvxixiao/js/md5.js'></script>");
function updateAccount() {
	var host = window.location.host;
	$("#optionHeader").html("账号管理");
	$("#page").empty();
	$("#adminList").empty();
	var form = "<form class='form-horizontal' role='form' id='accountForm' action='http://"+host+"/lvxixiao/admin/updateAccount'  method='POST'><div class='col-sm-8'>账号：<input type='text' id='username' name='username' value='root@lvxixiao'></div><div class='col-sm-8'>旧密码：<input type='password' id='password' name='password'></div><div class='col-sm-8'>新密码：<input type='password' id='newPassword' name='newPassword'></div><div class='col-sm-8'><input class='btn btn-default' type='button' id='subAccountbtn' value='submit'></div></form>";
	$("#adminList").append(form);
	
	$("#subAccountbtn").click(function(){
		if($("#username") != "" && $("#password") != "" &&$("#oldPassword") != "") {
			var password = $("#password").val();
			$("#password").val(hex_md5(password));
			var oldPassword = $("#newPassword").val();
			$("#newPassword").val(hex_md5(oldPassword));
			$("#accountForm").ajaxSubmit({
				dataType : "json",
				success : function(data) {
					if(data.status == "400"){
						alert("用户名或者密码错误");
						updateAccount();
					} else if(data.status == "200") {
						alert("修改成功成功");
						window.location.replace("http://"+host+"/lvxixiao/admin");
					}
				},
				error : function(){
					alert("form ajax err");
				}
			});
		}
	});
}