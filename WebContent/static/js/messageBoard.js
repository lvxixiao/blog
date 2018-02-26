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
				var li = "<li><a href='/lvxixiao/tag/"+data[i].tag_id+"' >"+data[i].name+"</a></li>";
				$("#"+id).append(li);
			}
			
		},
		error : function(){
			alert("getTechnologyTag ajax err");
		}
	});	
}

//检查textarea的输入数量
function checkCount() {
	var comment = $("#inputComment").val();
	var count = 250-comment.length;
	if(count < 0)
		alert("超过字数长度，请修改留言");
	$("#counter").html(count);
}
//检查留言是否符合长度
function checkComment(comName,comment) {
	if(comName.length > 15) {
		alert("昵称长度不要超过15字");
		return false;
	} else if($.trim(comName).length == 0){
		alert("昵称不能为空");
		return false;
	} else if(comment.length > 250) {
		alert("留言长度不要超过250字");
		return false;
	} else if($.trim(comment).length == 0){
		alert("留言不能为空");
		return false;
	} else {
		return true;
	}
}

//获取页数
function getPageCount() {
	var host = window.location.host;
	$.ajax({
		type : "POST",
		url  : "http://"+host+"/lvxixiao/commentNumber/10",
		dataType : "json",
		success: function(data) {
			$("#page").empty();
			if(data["pageCount"] != 1 && data["pageCount"] !=0){
				var current = parseInt($("#current").val()) + 1;
				$("#commentNumber").val(data["commentNumber"]);
				$("#pageCount").val(data["pageCount"]);
				var page = "页次："+current+"/"+data["pageCount"]+" 每页 10 总数 "+data["commentNumber"]+" <a href=\"javascript:void(0)\" onclick=\"firstPage()\">首页</a><a href=\"javascript:void(0)\" onclick=\"previousPage()\">上一页</a><a href=\"javascript:void(0)\" onclick=\"nextPage()\">下一页</a><a href=\"javascript:void(0)\" onclick=\"lastPage()\">尾页</a>";
				$("#page").append(page);
			}
		},
		error : function(data){
			console.log(data);
			alert("getPageNumber ajax err");
		}
	});
}

//<div class="media">
//<div class="media-body">
//    <h4 class="media-heading">小明</h4>
//    <p>有来过，666666。</p>
//    <p>4楼&nbsp;&nbsp;2017-08-08&nbsp;&nbsp;23:15发表</p>
//    <hr>
//</div>
//</div>
//获取留言
function getComment() {
	var host = window.location.host;
	$.ajax({
		type : "POST",
		url  : "http://"+host+"/lvxixiao/comment/"+$("#current").val()+"/10",
		dataType : "json",
		success: function(data) {
			$("#comment").empty();
			for(var i in data){
			var div = "<div class='media'><div class='media-body'><h4 class=;media-heading;>"+data[i].comName+"</h4><p>"+data[i].comment+"</p><p>"+data[i].comment_id+"楼&nbsp;&nbsp;"+data[i].dateTime+"发表</p><hr></div></div>";
				$("#comment").append(div);
			}
		},
		error : function(data){
			console.log(data);
			alert("getArticleAbout ajax err");
		}
	});
}

//页面转换
function changePage(page) {
	$("#current").val(page);
	var current = parseInt($("#current").val()) + 1;
	$("#page").empty();
	var pageDiv = "页次："+current+"/"+$("#pageCount").val()+" 每页 10 总数 "+$("#commentNumber").val()+" <a href=\"javascript:void(0)\" onclick=\"firstPage()\">首页</a><a href=\"javascript:void(0)\" onclick=\"previousPage()\">上一页</a><a href=\"javascript:void(0)\" onclick=\"nextPage()\">下一页</a><a href=\"javascript:void(0)\" onclick=\"lastPage()\">尾页</a>";
	$("#page").append(pageDiv);
	
	getComment();
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

$(function(){
	//留言表单提交
	$("#sumbitButton").click(function(){
		var comName = $("#comName").val();
		var comment = $("#inputComment").val();
		if(checkComment(comName,comment)) {
			$("#commentForm").ajaxSubmit({
				dataType : "json",
				success : function(data) {
					alert(data.message);
					//提交后刷新页面
					window.location.reload();
				},
				error : function(){
					alert("form ajax err");
				}
			});
		}
	});
	//获取评论
	getComment();
	//获取分页
	getPageCount();
	//获取标签
	getTags("technology",4);
	getTags("book",3);
});