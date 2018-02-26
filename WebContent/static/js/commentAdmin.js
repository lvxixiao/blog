//获取页数
function getCpmPageCount() {
	var host = window.location.host;
	$.ajax({
		type : "POST",
		url  : "http://"+host+"/lvxixiao/commentNumber/15",
		dataType : "json",
		success: function(data) {
			if(data["pageCount"] != 1){
				var current = parseInt($("#current").val()) + 1;
				$("#commentNumber").val(data["commentNumber"]);
				$("#pageCount").val(data["pageCount"]);
				$("#page").empty();
				var page = "页次："+current+"/"+data["pageCount"]+" 每页 15 总数 "+data["commentNumber"]+" <a href=\"javascript:void(0)\" onclick=\"firstComPage()\">首页</a><a href=\"javascript:void(0)\" onclick=\"previousComPage()\">上一页</a><a href=\"javascript:void(0)\" onclick=\"nextComPage()\">下一页</a><a href=\"javascript:void(0)\" onclick=\"lastComPage()\">尾页</a>";
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
function deleteComment(comment_id) {
	var host = window.location.host;
	if(window.confirm("删除文章")){
		$.ajax({
			type : "POST",
			url  : "http://"+host+"/lvxixiao/admin/delComment/"+comment_id,
			dataType : "json",
			success: function(data) {
				if(data.status =="200"){
					alert("留言删除成功");
					getComment();
					getCpmPageCount();
				}
			},
			error : function(data){
				console.log(data);
				alert("deleteComment ajax err");
			}
		});
	}
}

//获取留言列表
function getComment() {
	$("#optionHeader").html("留言板管理");
	$("#adminList").empty();
	var host = window.location.host;
	$.ajax({
		type : "POST",
		url  : "http://"+host+"/lvxixiao/comment/"+$("#current").val()+"/15",
		dataType : "json",
		success: function(data) {
			var table = "<table class='table table-striped'><thead><tr><th>编号</th><th>昵称</th><th>内容</th><th>删除</th></tr></thead><tbody>";
			for(var i in data) {
				table = table +"<tr>";
				var td = "<td>"+data[i].comment_id+"</td><td>"+data[i].comName+"</td><td>"+data[i].comment+"</td>";
				var del = "<td><a href='javascript:void(0)' onclick='deleteComment("+data[i].comment_id+")'>删除</a></td>";
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
			alert("getComment ajax err");
		}
	});
};

function changeComPage(page) {
	$("#current").val(page);
	var current = parseInt($("#current").val()) + 1;
	$("#page").empty();
	var pageDiv = "页次："+current+"/"+$("#pageCount").val()+" 每页 15 总数 "+$("#commentNumber").val()+" <a href=\"javascript:void(0)\" onclick=\"firstComPage()\">首页</a><a href=\"javascript:void(0)\" onclick=\"previousComPage()\">上一页</a><a href=\"javascript:void(0)\" onclick=\"nextComPage()\">下一页</a><a href=\"javascript:void(0)\" onclick=\"lastComPage()\">尾页</a>";
	$("#page").append(pageDiv);
	
	getComment();
	//回到顶部
	window.scrollTo(0,0);
}

//首页
function firstComPage() {
	if($("#current").val() != 0)
		changeComPage("0");
}
//尾页
function lastComPage() {
	var last = parseInt($("#pageCount").val()) - 1;
	if($("#current").val() != last)
		changeComPage(last);
}
//上一页
function previousComPage() {
	if($("#current").val() != 0)
		changeComPage(parseInt($("#current").val()) - 1);
}
//下一页
function nextComPage() {
	if($("#current").val() != (parseInt($("#pageCount").val()) - 1))
		changeComPage(parseInt($("#current").val()) + 1);
}


function commentAdmin() {
	$("#current").val(0);
	$("#page").empty();
	getComment();
	getCpmPageCount();
}