package lvxixiao.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import lvxixiao.pojo.Article;
import lvxixiao.service.ArticleService;




@Controller
public class ArticlesController {   
	
	@Resource
	ArticleService articleService;
	
	
	//接收上传的文章，只接收.md格式
	@RequestMapping(value="/admin/uploading",produces = "application/json; charset=utf-8", method=RequestMethod.POST)
	public @ResponseBody String receiveArticle(HttpServletRequest request,Article article,
			MultipartFile[] files) {

		return articleService.addArticle(request,article, files);	
		
	}
	/*
	 * 根据tag_id获取文章
	 * */
	@CrossOrigin(allowedHeaders= "*")
	@RequestMapping(value="/article/{tag_id}/{current}/{number}",produces = "application/json; charset=utf-8")
	public @ResponseBody String findArticleByTag_id(@PathVariable int tag_id,
			@PathVariable int current,@PathVariable int number) {
		
		return articleService.getArticleByTag_id(tag_id, current * number, number);	
		
	}
	
	/*
	 * 根据article_id,向页面传输文章
	 * $("#topTitle").html(data.category_name);
			$("#title").html(data.title)
			$("#date").html("发布时间："+data.date);
			$("#author").html("作者："+data.author);
			$("#content").html(data.content);
	 * */
	@RequestMapping(value="/article/{article_id}",produces = "application/json; charset=utf-8",method=RequestMethod.POST)
	public @ResponseBody String getArticleByArticle_id(HttpServletRequest request,@PathVariable int article_id) {
		
		return articleService.getArticleByArticle_id(request,article_id);
	}
	//向后台管理页面传输文章列
	@CrossOrigin(allowedHeaders= "*")
	@RequestMapping(value="/articleList/{start}/{number}",produces = "application/json; charset=utf-8",method=RequestMethod.POST)
	public @ResponseBody String getArticleList(@PathVariable int start,@PathVariable int number) {
		
		return articleService.getArticleList(start * number,number);
		
	}
	
	//删除文章
	@RequestMapping(value="/admin/delArticle/{article_id}",produces = "application/json; charset=utf-8",method=RequestMethod.POST)
	public @ResponseBody String delArticleByArticle_id(HttpServletRequest request,@PathVariable int article_id) { 
		articleService.delArticleByArticle_id(request,article_id);
		return "{\"status\":\"200\"}";
	}
	//获得文章内容
	@RequestMapping(value="/admin/updateContent/{article_id}",produces = "application/json; charset=utf-8",method=RequestMethod.POST)
	public @ResponseBody String getArticleContent(@PathVariable int article_id) {
		return articleService.getArticleContent(article_id);
	}
	//获得文章摘要
	@RequestMapping(value="/admin/updateSummary/{article_id}",produces = "application/json; charset=utf-8",method=RequestMethod.POST)
	public @ResponseBody String updateSummary(@PathVariable int article_id) {
		return articleService.getArticleSummary(article_id);
	}
	//更新文章
	@RequestMapping(value="/admin/update",method=RequestMethod.POST)
	public String update(HttpServletRequest request,Article article) {
		articleService.update(request,article);
		return "views/admin";
	}

	//文章搜索
	@RequestMapping(value="/search/{searchInput}/{current}/{number}",produces = "application/json; charset=utf-8",method=RequestMethod.POST)
	public @ResponseBody String search(HttpServletRequest request,@PathVariable String searchInput,
			@PathVariable int current,@PathVariable int number) {
		return articleService.search(request,searchInput,current * number,number);
		
	}
	//文章搜索分页
	@RequestMapping(value="/search/{searchInput}/{number}",produces = "application/json; charset=utf-8",method=RequestMethod.POST)
	public @ResponseBody String searchPage(@PathVariable String searchInput,
			@PathVariable int number) {
		System.out.println(articleService.searchPage(searchInput,number));
		return articleService.searchPage(searchInput,number);
	}
}
