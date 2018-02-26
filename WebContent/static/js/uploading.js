
function upload() { 
	var host = window.location.host;
	$("#optionHeader").html("文章上传");
	$("#page").empty();
	$("#adminList").empty();
	var form = "<form class='form-horizontal' role='form' id='articleForm' action='http://"+host+"/lvxixiao/admin/uploading'  method='POST' enctype ='multipart/form-data'><div class='col-sm-8'>作者：<input type='text' id='author' name='author' value='吕系小'></div><div class='col-sm-8'>分类：<select name='category_id' id='category_id'></select><select name='tag_id' id='tag_id' hidden='true' ><option value='4'>个人简介</option></select></div><div class='col-sm-8'>文章上传：<input type='file' name='files' multiple='multiple' id='file'></div><div class='col-sm-8'><input class='btn btn-default' type='button' id='sumbitButton' value='submit'></div></form>";
	$("#adminList").append(form);
	
	//ajax获取类型
	$.ajax({
		type : "POST",
		url  : "http://"+host+"/lvxixiao/category",
		dataType : "json",
		success: function(data) {			
			for( var i in data ) {
				var option = "<option value=\"" +data[i].category_id+"\">"+ data[i].name +"</option>"
				$("#category_id").append(option);
			}
		},
		error : function(){
			alert("select type ajax err");
		}
	});
	
	//ajax获取标签
	$("#category_id").change(function(){
		var val = $(this).val();
		var text = $("#category_id>option:selected").text();
		var host = window.location.host;
		$("#tag_id").hide();
		if(text == "读书笔记" || text == "技术文章") {
			$.ajax({
				type : "POST",
				url  : "http://"+host+"/lvxixiao/tag",
				data : "category_id="+val,
				dataType : "json",
				success: function(data) {
					$("#tag_id").empty();
					for( var i in data ) {
						var option = "<option value=\"" +data[i].tag_id+"\">"+ data[i].name +"</option>"
						$("#tag_id").append(option);
					}
					$("#tag_id").show();
				},
				error : function(){
					alert("select tag ajax err");
				}
			});
		}
		if(text == "个人简介") {
			$("#tag_id").empty();
			var option = "<option value=\"4\">个人简介</option>"
			$("#tag_id").append(option);				
		}
	});
	
	//表单提交
	$("#sumbitButton").click(function(){
		
		if($("#file").val() == ""){
			alert("上传文章不能为空");	
		}
		if($("#file").val()!=""){
			$("#articleForm").ajaxSubmit({
				dataType : "json",
				success : function(data) {
					alert(data.message);
					//提交后重置表单
					upload();
				},
				error : function(){
					alert("form ajax err");
				}
			});
		}
		
	});
}

