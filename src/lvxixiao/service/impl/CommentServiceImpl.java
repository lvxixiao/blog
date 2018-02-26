package lvxixiao.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import lvxixiao.mapper.CommentMapper;
import lvxixiao.pojo.Comment;
import lvxixiao.service.CommentService;
import lvxixiao.util.Tools;
@Service
public class CommentServiceImpl implements CommentService {
	
	private static final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

	
	@Resource
	CommentMapper commentMapper;
	@Resource
	Gson gson;
	
	@Override
	public void addComment(HttpServletRequest request,Comment comment) {
		logger.info("添加评论,添加评论的地址:"+Tools.getIpAddr(request)+"评论者:"+comment.getComName()+",评论内容:"+comment.getComment());
		commentMapper.addComment(comment);
	}
	
	@Override
	public String getComment(int current, int number) {
		List<Comment> comments = commentMapper.getComment(current,number);
		return gson.toJson(comments);
	}

	@Override
	public String getCommentPage(int number) {
		int count = commentMapper.getCommentNumber();
		int pageCount = count/number;
		if(count % number != 0)
			pageCount++;
		String json = "{\"commentNumber\":\""+count+"\",\"pageCount\":\""+pageCount+"\"}";
		return json;
	}

	@Override
	public void delCommentByComment_id(HttpServletRequest request,int comment_id) {
		Comment comment = commentMapper.getCommentByComment_id(comment_id);
		logger.info("删除评论,删除评论的地址:"+Tools.getIpAddr(request)+",评论者:"+comment.getComName()+",评论内容:"+comment.getComment());
		commentMapper.delCommentByComment_id(comment_id);		
	}

}
