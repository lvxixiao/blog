document.write("<script type='text/javascript' src='http://127.0.0.1:8080/lvxixiao/js/md5.js'></script>");
function checkCookie() {
	var host = window.location.host;
	$.ajax({
		type : "POST",
		url  : "http://"+host+"/lvxixiao/admin/checkToken",
		dataType : "json",
		success: function(data) {
			console.log(data);
			if(data.status == "400") {
				alert("token验证错误");
			} else if (data.status == "401") {
				alert("登陆过期，请重新登陆");
			} else if( data.status == "200") {
				window.location.replace("http://"+host+"/lvxixiao/admin");
			}
		},
		error : function(data){
			console.log(data);
			alert("checkCookie ajax err");
		}
	});
}
$(function(){
	checkCookie();
	$("#submitForm").click(function(){
		var password = $("#inputPassword").val();
		$("#inputPassword").val(hex_md5(password));
		$("#userForm").ajaxSubmit({

			dataType : "json",
			success : function(data) {
				if(data.status == "400"){
					alert("用户名或者密码错误");
					window.location.reload();
				} else if(data.status == "200") {
					alert("登陆成功");
					window.location.reload();
				}
			},
			error : function(){
				alert("form ajax err");
			}
			
		});
	});
});