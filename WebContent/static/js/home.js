//获取文章
function getArticle() {
	var host = window.location.host;
	$.ajax({
		type : "POST",
		url  : "http://"+host+"/lvxixiao/article/"+$("#tag_id").val()+"/"+$("#current").val()+"/8",
		dataType : "json",
		success: function(data) {
			$("#articleList").empty();
			for(var i in data){
				var div ="<div class='jumbotron'><h3>"+data[i].title+"</h3><p>"+data[i].summary+"</p><p><a class='btn btn-lg btn-primary' href='http://"+host+"/lvxixiao/article/"+data[i].article_id+"' role='button'>查看文章详情 &raquo;</a></p></div>";
				$("#articleList").append(div);
			}
			
		},
		error : function(data){
			console.log(data);
			alert("getArticle ajax err");
		}
	});
}
//获取标签
function getTags(id,category_id) {
	var host = window.location.host;
	$.ajax({
		type : "POST",
		url  : "http://"+host+"/lvxixiao/tag",
		data : "category_id="+category_id,
		dataType : "json",
		success: function(data) {
			//$("#technology").find('ul:first').append("<li><a href=\"newslistpic.html\">Java</a></li>");
			$("#"+id).empty();
			for( var i in data ) {
				var li = "<li><a href='javascript:void(0)' onclick='getArticlesByTag_id("+data[i].tag_id+")'>"+data[i].name+"</a></li>";
				$("#"+id).append(li);
			}
			
		},
		error : function(){
			alert("getTechnologyTag ajax err");
		}
	});	
}
//获取页数
function getPageCount() {
	var host = window.location.host;
	$.ajax({
		type : "POST",
		url  : "http://"+host+"/lvxixiao/page/"+$("#tag_id").val()+"/8",
		dataType : "json",
		success: function(data) {
			$("#page").empty();
			if(data["pageCount"] != 1 && data["pageCount"] !=0){
				var current = parseInt($("#current").val()) + 1;
				$("#articleNumber").val(data["articleNumber"]);
				$("#pageCount").val(data["pageCount"]);
				var page = "页次："+current+"/"+data["pageCount"]+" 每页 8 总数 "+data["articleNumber"]+" <a href=\"javascript:void(0)\" onclick=\"firstPage()\">首页</a><a href=\"javascript:void(0)\" onclick=\"previousPage()\">上一页</a><a href=\"javascript:void(0)\" onclick=\"nextPage()\">下一页</a><a href=\"javascript:void(0)\" onclick=\"lastPage()\">尾页</a>";
				$("#page").append(page);
			}
		},
		error : function(data){
			console.log(data);
			alert("getPageNumber ajax err");
		}
	});
}

//页面转换
function changePage(page) {
	$("#current").val(page);
	var current = parseInt($("#current").val()) + 1;
	$("#page").empty();
	var pageDiv = "页次："+current+"/"+$("#pageCount").val()+" 每页 8 总数 "+$("#articleNumber").val()+" <a href=\"javascript:void(0)\" onclick=\"firstPage()\">首页</a><a href=\"javascript:void(0)\" onclick=\"previousPage()\">上一页</a><a href=\"javascript:void(0)\" onclick=\"nextPage()\">下一页</a><a href=\"javascript:void(0)\" onclick=\"lastPage()\">尾页</a>";
	$("#page").append(pageDiv);
	
	getArticle();
	//回到顶部
	window.scrollTo(0,0);
}

//根据tag_id获取文章，并修改$("#tag_id")的值
function getArticlesByTag_id(tag_id) {
	
	$("#tag_id").val(tag_id);
	$("#current").val(0);
	$("#pageCount").val(0);
	getPageCount();
	getArticle();
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

$(function() {
	getTags("technology",4);
	getTags("book",3);
});
