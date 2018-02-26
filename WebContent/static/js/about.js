function getArticeAbout() {
	var path = window.location.href;
	$.ajax({
		type : "POST",
		url  : path,
		dataType : "json",
		success: function(data) {
			$("#title").html(data.title)
			$("#date").html("发布时间："+data.date+"    <a href='#' id='author'>作者："+data.author+"</a>");
			$("#article").append(data.content);
		},
		error : function(data){
			console.log(data);
			alert("getArticleAbout ajax err");
		}
	});
}
$(function(){
	getArticeAbout();
});