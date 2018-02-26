package lvxixiao.mapper;

import java.util.List;

import lvxixiao.pojo.Comment;

public interface CommentMapper {
	void addComment(Comment comment);

	List<Comment> getComment(int current, int number);

	int getCommentNumber();

	void delCommentByComment_id(int comment_id);

	Comment getCommentByComment_id(int comment_id);
}
