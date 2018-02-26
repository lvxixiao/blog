package lvxixiao.pojo;

import java.sql.Date;

public class Comment {
	private int comment_id;
	private String comName;
	private String comment;	
	private Date dateTime;
	public Comment() {
		super();
	}
	public int getComment_id() {
		return comment_id;
	}
	public void setComment_id(int comment_id) {
		this.comment_id = comment_id;
	}
	public String getComName() {
		return comName;
	}
	public void setComName(String comName) {
		this.comName = comName;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Date getDateTime() {
		return dateTime;
	}
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	@Override
	public String toString() {
		return "Comment [comment_id=" + comment_id + ", comName=" + comName + ", comment=" + comment + ", dateTime="
				+ dateTime + "]";
	}
	
}
