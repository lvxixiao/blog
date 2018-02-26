package lvxixiao.service;

import javax.servlet.http.HttpServletRequest;

import lvxixiao.pojo.Comment;

public interface CommentService {
	void addComment(HttpServletRequest request,Comment comment);

	String getComment(int current, int number);

	String getCommentPage(int number);

	void delCommentByComment_id(HttpServletRequest request,int comment_id);
}
