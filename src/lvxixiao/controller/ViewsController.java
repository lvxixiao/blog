package lvxixiao.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lvxixiao.util.Tools;

@Controller
public class ViewsController {
	
	private static final Logger logger = LoggerFactory.getLogger(ArticlesController.class); 
	
	//主页
	@RequestMapping("/")
	public String index(HttpServletRequest request) {
		logger.info("主页被访问,访问地址:"+Tools.getIpAddr(request));
		return "views/index";
	}
	
	//上传页面
	@RequestMapping(value="/uploading", method=RequestMethod.GET)
	public String uploading() {
		return "views/uploading";
	}
	
	// 文章详情页
	@RequestMapping(value="/article/{article_id}",method=RequestMethod.GET)
	public String getAbout(@PathVariable int article_id) {

		return "views/about";
	}
	
	// 文章详情页
	@RequestMapping(value="/admin",method=RequestMethod.GET)
	public String getAdmin() {

		return "views/admin";
	}
	
	//文章内容修改页
	@RequestMapping(value="/admin/updateContent/{article_id}",method=RequestMethod.GET)
	public String updateContent(@PathVariable int article_id) {
		return "views/update";
	}
	
	//文章摘要修改页
	@RequestMapping(value="/admin/updateSummary/{article_id}",method=RequestMethod.GET)
	public String updateSummary(@PathVariable int article_id) {
		return "views/update";
	}
	
	//留言板
	@RequestMapping(value="/comment",method=RequestMethod.GET)
	public String messageBoard() {
		return "views/messageBoard";
	}
	
	//从留言板切换到文章分类显示页
	@RequestMapping(value="/tag/{tag_id}",method=RequestMethod.GET)
	public String articleList() {
		return "views/index";
	}
	
	//登陆页面
	@RequestMapping(value="/signin",method=RequestMethod.GET)
	public String login() {
		return "views/signin";
	}
}
