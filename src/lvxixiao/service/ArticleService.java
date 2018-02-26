package lvxixiao.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import lvxixiao.pojo.Article;

public interface ArticleService {
	String addArticle(HttpServletRequest request,Article article,MultipartFile[] files);
	String getArticleByTag_id(int tag_id,int start,int end);
	String getArticleByArticle_id(HttpServletRequest request,int article_id);
	String getArticleList(int start,int number);
	void delArticleByArticle_id(HttpServletRequest request,int article_id);
	String getArticleContent(int article_id);
	String getArticleSummary(int article_id);
	void update(HttpServletRequest request,Article article);
	String search(HttpServletRequest request,String searchInput, int start, int number);
	String searchPage(String searchInput, int number);


}
