//获取页数
function getPageCount() {
	var host = window.location.host;
	$.ajax({
		type : "POST",
		url  : "http://"+host+"/lvxixiao/page/0/15",
		dataType : "json",
		success: function(data) {
			if(data["pageCount"] != 1){
				var current = parseInt($("#current").val()) + 1;
				$("#number").val(data["articleNumber"]);
				$("#pageCount").val(data["pageCount"]);
				$("#page").empty();
				var page = "页次："+current+"/"+data["pageCount"]+" 每页 15 总数 "+data["articleNumber"]+" <a href='javascript:void(0)' onclick='firstPage()'>首页</a><a href='javascript:void(0)' onclick='previousPage()'>上一页</a><a href='javascript:void(0)' onclick='nextPage()'>下一页</a><a href='javascript:void(0)' onclick='lastPage()'>尾页</a>";
				$("#page").append(page);
			}
		},
		error : function(data){
			console.log(data);
			alert("getPageNumber ajax err");
		}
	});
}
//删除文章
function deleteArticle(article_id) {
	var host = window.location.host;
	if(window.confirm("删除文章")){
		$.ajax({
			type : "POST",
			url  : "http://"+host+"/lvxixiao/admin/delArticle/"+article_id,
			dataType : "json",
			success: function(data) {
				if(data.status =="200"){
					alert("文章删除成功");
					window.location.replace("http://"+host+"/lvxixiao/admin");
				}
			},
			error : function(data){
				console.log(data);
				alert("deleteArticle ajax err");
			}
		});
	}
}

//获取文章列表
function getArticleList() {
	$("#optionHeader").html("文章管理");
	$("#adminList").empty();
	var host = window.location.host;
	$.ajax({
		type : "POST",
		url  : "http://"+host+"/lvxixiao/articleList/"+$("#current").val()+"/15",
		dataType : "json",
		success: function(data) {
			/*$("#adminList").append("<table class='table table-striped'>");
			$("#adminList").append("<thead><tr><th>编号</th><th>标题</th><th>修改</th><th>删除</th></tr></thead>");
			$("#adminList").append("<tbody>");
			for(var i in data) {
				$("#adminList").append("<tr>");
				var td = "<td>"+data[i].article_id+"</td><td>"+data[i].title+"</td><td>";
				var update = "<td><a href='http://127.0.0.1:8080/update/"+data[i].article_id+"'></td>";
				var del = "<td><a href='#' onclick='deleteArticle("+data[i].article_id+")'></td>";
				$("#adminList").append(td);
				$("#adminList").append(update);
				$("#adminList").append(del);
				$("#adminList").append("</tr>");
			}
			$("#adminList").append("</tbody>");
			$("#adminList").append("</table>");*/
			//append方法会自动补齐标签.
			var table = "<table class='table table-striped'><thead><tr><th>编号</th><th>标题</th><th>修改</th><th>删除</th></tr></thead><tbody>";
			for(var i in data) {
				table = table +"<tr>";
				var td = "<td>"+data[i].article_id+"</td><td>"+data[i].title+"</td>";
				var update = "<td><a href='http://"+host+"/lvxixiao/admin/updateContent/"+data[i].article_id+"'>修改文章/</a><a href='http://"+host+"/lvxixiao/admin/updateSummary/"+data[i].article_id+"'>摘要</a></td>";
				var del = "<td><a href='javascript:void(0)' onclick='deleteArticle("+data[i].article_id+")'>删除</a></td>";
				table = table + td;
				table = table + update;
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

function changePage(page) {
	$("#current").val(page);
	var current = parseInt($("#current").val()) + 1;
	$("#page").empty();
	var pageDiv = "页次："+current+"/"+$("#pageCount").val()+" 每页 15 总数 "+$("#number").val()+" <a href=\"javascript:void(0)\" onclick=\"firstPage()\">首页</a><a href=\"javascript:void(0)\" onclick=\"previousPage()\">上一页</a><a href=\"javascript:void(0)\" onclick=\"nextPage()\">下一页</a><a href=\"javascript:void(0)\" onclick=\"lastPage()\">尾页</a>";
	$("#page").append(pageDiv);
	
	getArticleList();
	//回到顶部
	window.scrollTo(0,0);
}

//首页
function firstPage() {
	if($("#current").val() != 0)
		changePage("0");
}
//尾页
function lastPage() {
	var last = parseInt($("#pageCount").val()) - 1;
	if($("#current").val() != last)
		changePage(last);
}
//上一页
function previousPage() {
	if($("#current").val() != 0)
		changePage(parseInt($("#current").val()) - 1);
}
//下一页
function nextPage() {
	if($("#current").val() != (parseInt($("#pageCount").val()) - 1))
		changePage(parseInt($("#current").val()) + 1);
}

function articleAdmin() {
	$("#current").val(0);
	getArticleList();
	getPageCount();
}

$(function() {
	getArticleList();
	getPageCount();
});