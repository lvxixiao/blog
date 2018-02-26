$(function(){
	var pathname = window.location.pathname;
	var parameter = pathname.charAt(pathname.length - 1);
	if(!isNaN(parameter)) {
		$("#tag_id").val(parameter);
	}
	//获取文章
	getArticle();
	//获取页数
	getPageCount();
});