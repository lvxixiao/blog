package lvxixiao.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lvxixiao.pojo.Comment;
import lvxixiao.service.CommentService;

@Controller
public class CommentController {
	@Resource
	CommentService commentService;
	
	//接收留言
	@RequestMapping(value="/comment",produces = "application/json; charset=utf-8",method=RequestMethod.POST)
	public @ResponseBody String addComment(HttpServletRequest request,Comment comment) {
		commentService.addComment(request,comment);
		return "{\"message\":\"留言上传成功\"}";
	}
	//获取留言
	@RequestMapping(value="/comment/{current}/{number}",produces = "application/json; charset=utf-8",method=RequestMethod.POST)
	public @ResponseBody String getComment(@PathVariable int current,
			@PathVariable int number) {
		return commentService.getComment(current * number ,number);
	}
	//获得留言页面数量
	@RequestMapping(value="commentNumber/{number}",produces = "application/json; charset=utf-8",method=RequestMethod.POST)
	public @ResponseBody String getCommentPage(@PathVariable int number) {
		return commentService.getCommentPage(number);
	}
	//删除留言
	@RequestMapping(value="/admin/delComment/{comment_id}",produces = "application/json; charset=utf-8",method=RequestMethod.POST)
	public @ResponseBody String delCommentByComment_id(HttpServletRequest request,@PathVariable int comment_id) { 
		commentService.delCommentByComment_id(request,comment_id);
		return "{\"status\":\"200\"}";
	}
}
