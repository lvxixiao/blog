package lvxixiao.pojo;

import java.sql.Date;

public class Article {
	private int article_id;
	private String author;
	private String title;
	private String content;
	private Date date;
	private String summary;
	private int category_id;
	private int tag_id;
	public Article() {
		super();
	}
	public Article(int article_id, String author, String title, String content, Date date, String summary,
			int category_id, int tag_id) {
		super();
		this.article_id = article_id;
		this.author = author;
		this.title = title;
		this.content = content;
		this.date = date;
		this.summary = summary;
		this.category_id = category_id;
		this.tag_id = tag_id;
	}
	public int getArticle_id() {
		return article_id;
	}
	public void setArticle_id(int article_id) {
		this.article_id = article_id;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public int getCategory_id() {
		return category_id;
	}
	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}
	public int getTag_id() {
		return tag_id;
	}
	public void setTag_id(int tag_id) {
		this.tag_id = tag_id;
	}
	@Override
	public String toString() {
		return "article [article_id=" + article_id + ", author=" + author + ", title=" + title + ", content=" + content
				+ ", date=" + date + ", summary=" + summary + ", category_id=" + category_id +", tag_id=" +tag_id +"]";
	}
	
	
}
