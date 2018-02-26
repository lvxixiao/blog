//获取页数
function getTagPageCount() {
	var host = window.location.host;
	$.ajax({
		type : "POST",
		url  : "http://"+host+"/lvxixiao/tagPage/15",
		dataType : "json",
		success: function(data) {
			if(data["pageCount"] != 1){
				var current = parseInt($("#current").val()) + 1;
				$("#number").val(data["tagNumber"]);
				$("#pageCount").val(data["pageCount"]);
				$("#page").empty();
				var page = "页次："+current+"/"+data["pageCount"]+" 每页 15 总数 "+data["tagNumber"]+" <a href='javascript:void(0)' onclick='firstTagPage()'>首页</a><a href='javascript:void(0)' onclick='previousTagPage()'>上一页</a><a href='javascript:void(0)' onclick='nextTagPage()'>下一页</a><a href='javascript:void(0)' onclick='lastTagPage()'>尾页</a>";
				$("#page").append(page);
			}
		},
		error : function(data){
			console.log(data);
			alert("getPageNumber ajax err");
		}
	});
}
//删除标签
function deleteTag(tag_id) {
	var host = window.location.host;
	if(window.confirm("删除标签")){
		$.ajax({
			type : "POST",
			url  : "http://"+host+"/lvxixiao/admin/deleteTag/"+tag_id,
			dataType : "json",
			success: function(data) {
				if(data.status =="200"){
					alert("标签删除成功");
					getTagList();
					getTagPageCount();
				}
			},
			error : function(data){
				console.log(data);
				alert("deleteArticle ajax err");
			}
		});
	}
}

//获取标签列表
function getTagList() {
	$("#adminList").empty();
	var host = window.location.host;
	$.ajax({
		type : "POST",
		url  : "http://"+host+"/lvxixiao/getTagList/"+$("#current").val()+"/15",
		dataType : "json",
		success: function(data) {
			var table = "<table class='table table-striped'><thead><tr><th>分类</th><th>标签</th><th>删除</th></tr></thead><tbody>";
			for(var i in data) {
				table = table +"<tr>";
				var td = "<td>"+data[i].tag_id+"</td><td>"+data[i].categoryName+"</td><td>"+data[i].name+"</td>";
				var del = "<td><a href='javascript:void(0)' onclick='deleteTag("+data[i].tag_id+")'>删除</a></td>";
				table = table + td;
				table = table + del;
				table = table + "</tr>";
			}
			table = table + "</tbody>";
			table = table + "</table>";
			$("#adminList").append(table);
			
		},
		error : function(data){
			console.log(data);
			alert("getArticleList ajax err");
		}
	});
};

function changeTagPage(page) {
	$("#current").val(page);
	var current = parseInt($("#current").val()) + 1;
	$("#page").empty();
	var pageDiv = "页次："+current+"/"+$("#pageCount").val()+" 每页 15 总数 "+$("#number").val()+" <a href=\"javascript:void(0)\" onclick=\"firstTagPage()\">首页</a><a href=\"javascript:void(0)\" onclick=\"previousTagPage()\">上一页</a><a href=\"javascript:void(0)\" onclick=\"nextTagPage()\">下一页</a><a href=\"javascript:void(0)\" onclick=\"lastTagPage()\">尾页</a>";
	$("#page").append(pageDiv);
	
	getTagList();
	//回到顶部
	window.scrollTo(0,0);
}

//首页
function firstTagPage() {
	if($("#current").val() != 0)
		changeTagPage("0");
}
//尾页
function lastTagPage() {
	var last = parseInt($("#pageCount").val()) - 1;
	if($("#current").val() != last)
		changeTagPage(last);
}
//上一页
function previousTagPage() {
	if($("#current").val() != 0)
		changeTagPage(parseInt($("#current").val()) - 1);
}
//下一页
function nextTagPage() {
	if($("#current").val() != (parseInt($("#pageCount").val()) - 1))
		changeTagPage(parseInt($("#current").val()) + 1);
}

//添加标签
function tagAdd() {
	$("#optionHeader").html("标签管理");
	var host = window.location.host;
	var form = "<form class='form-horizontal' role='form' id='tagForm' action='http://"+host+"/lvxixiao/admin/addTag'  method='POST' enctype ='multipart/form-data'><div class='col-sm-2'><select name='category_id' id='category_id'></select></div><div class='col-sm-2'><input class='form-control' type='text' id='name' name='name' placeholder='输入新的标签名'></div><div class='col-sm-2'><input class='btn btn-default' type='button' id='sumbitButton' value='submit'></div></form>";
	$("#page").empty();
	$("#adminList").empty();
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
	//新类型提交
	$("#sumbitButton").click(function(){
		if($("#name").val() != "" && $.trim($("#name").val()) != "") {
			$("#tagForm").ajaxSubmit({
				dataType : "json",
				success : function(data) {
					alert(data.message);
					//提交后重置表单
					tagAdd();
				},
				error : function(){
					alert("form ajax err");
				}
			});
		} else {
			alert("新的标签名不能为空")
		}
	});
}

function tagAdmin() {
	$("#optionHeader").html("标签管理");
	$("#current").val(0);
	$("#page").empty();
	getTagList();
	getTagPageCount();
}
