function getArticeAbout() {
	var path = window.location.href;
	$.ajax({
		type : "POST",
		url  : path,
		dataType : "json",
		async : false,//必须开启同步，否则在editormd加载完时html标签还未加载
		success: function(data) {
			$("#title").val(data.title);
			$("#article_id").val(data.article_id);
			if(typeof(data.content) != "undefined"){
				var text = "<textarea style='display:none;' name='content'>"+data.content+"</textarea>"
				$("#test-editormd").html(text);
			} else {
				var text = "<textarea style='display:none;' name='summary'>"+data.summary+"</textarea>"
				$("#test-editormd").html(text);
			}
		},
		error : function(data){
			console.log(data);
			alert("getArticleAbout ajax err");
		}
	});
}

//获取标签
function getTags(category_id) {
	var host = window.location.host;
	$.ajax({
		type : "POST",
		url  : "http://"+host+"/lvxixiao/tag",
		data : "category_id="+category_id,
		dataType : "json",
		success: function(data) {
			for( var i in data ) {
				var li = "<li><a href='javascript:void(0)' onclick='selectTag(\""+data[i].tag_id+"\",\""+data[i].name+"\")'>"+data[i].name+"</a></li>";
				$("#tagList").append(li);
			}		
		},
		error : function(){
			alert("getTag ajax err");
		}
	});	
}

function selectCategory(category_id,name){
	$("#categoryBtn").html(name);
    $("#cateoryInput").val(category_id);
    $("#tagList").empty();
    $("#tagInput").val(0);
    getTags(category_id);
}

function selectTag(tag_id,name){
	$("#tagBtn").html(name);
    $("#tagInput").val(tag_id);
}

function getCategoryList() {
	var host = window.location.host;
	$.ajax({
		type : "POST",
		url  : "http://"+host+"/lvxixiao/category",
		dataType : "json",
		success: function(data) {	
			for( var i in data ) {
				var li = "<li><a href='javascript:void(0)' onclick='selectCategory(\""+data[i].category_id+"\",\""+data[i].name+"\")'>"+data[i].name+"</a></li>";
				$("#categoryList").append(li);
			}
		},
		error : function(){
			alert("select type ajax err");
		}
	});
}

$(function () {
	var testEditor;
	
	getCategoryList();
	getArticeAbout();
	
	testEditor = editormd("test-editormd", {
    	
    	width     : "100%",
        height    : 640,
        toc       : true,
        todoList  : true,
        path      : '/lvxixiao/static/editormd/lib/'
    });

});