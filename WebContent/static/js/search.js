function search(searchInput) {
	var host = window.location.host;

	$.ajax({
		type : "POST",
		url  : "http://"+host+"/lvxixiao/search/"+searchInput+"/"+$("#current").val()+"/8",
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
			alert("getSearch ajax err");
		}
	});

}
function getSearchPage(searchInput) {
	var host = window.location.host;
	$.ajax({
		type : "POST",
		url  : "http://"+host+"/lvxixiao/search/"+searchInput+"/8",
		dataType : "json",
		success: function(data) {
			$("#page").empty();
			if(data["pageCount"] != 1 && data["pageCount"] !=0){
				var current = parseInt($("#current").val()) + 1;
				$("#articleNumber").val(data["articleNumber"]);
				$("#pageCount").val(data["pageCount"]);
				var page = "页次："+current+"/"+data["pageCount"]+" 每页 8 总数 "+data["articleNumber"]+" <a href=\"javascript:void(0)\" onclick=\"firstSearchPage()\">首页</a><a href=\"javascript:void(0)\" onclick=\"previousSearchPage()\">上一页</a><a href=\"javascript:void(0)\" onclick=\"nextSearchPage()\">下一页</a><a href=\"javascript:void(0)\" onclick=\"lastSearchPage()\">尾页</a>";
				$("#page").append(page);
			}
		},
		error : function(data){
			console.log(data);
			alert("getSearchPageNumber ajax err");
		}
	});
}

//页面转换
function changeSearchPage(page) {
	$("#current").val(page);
	var current = parseInt($("#current").val()) + 1;
	$("#page").empty();
	var pageDiv = "页次："+current+"/"+$("#pageCount").val()+" 每页 8 总数 "+$("#articleNumber").val()+" <a href=\"javascript:void(0)\" onclick=\"firstSearchPage()\">首页</a><a href=\"javascript:void(0)\" onclick=\"previousSearchPage()\">上一页</a><a href=\"javascript:void(0)\" onclick=\"nextSearchPage()\">下一页</a><a href=\"javascript:void(0)\" onclick=\"lastSearchPage()\">尾页</a>";
	$("#page").append(pageDiv);
	
	search(searchInput);
	//回到顶部
	window.scrollTo(0,0);
}

//首页
function firstSearchPage() {
	if($("#current").val() != 0)
		changeSearchPage("0");
}
//尾页
function lastSearchPage() {
	var last = parseInt($("#pageCount").val()) - 1;
	if($("#current").val() != last)
		changeSearchPage(last);
}
//上一页
function previousSearchPage() {
	if($("#current").val() != 0)
		changeSearchPage(parseInt($("#current").val()) - 1);
}
//下一页
function nextSearchPage() {
	if($("#current").val() != (parseInt($("#pageCount").val()) - 1))
		changeSearchPage(parseInt($("#current").val()) + 1);
}

$(function(){
	$("#btnsearch").click(function(){
		//全局变量
		searchInput = $("#searchinput").val();
		if(searchInput.length > 50)
			searchInput = searchInput.subString(0,50);
		if(searchInput != "" && $.trim(searchInput) != 0){
			//搜索初始化
			$("#current").val(0);

			//搜索
			search(searchInput);
			getSearchPage(searchInput);
		} else {
			alert("搜索不能为空");
		}
	});		
});