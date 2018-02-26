package lvxixiao.mapper;

import java.util.List;

import lvxixiao.pojo.Article;

public interface ArticleMapper {
	void addArticle(Article article);
	Article getArticleByArticle_id(int article_id);
	void delArticleByArticle_id(int article_id);
	List<Article> getArticleByCategory_id(int category_id,int start,int number);
	List<Article> getArticleByTag_id(int tag_id,int start,int number);
	List<Article> getArticleByDate(int start, int number);
	List<Article> getArticleList(int start ,int number);
	Article getArticleContent(int article_id);
	Article getArticleSummary(int article_id);
	int getCtagory_idByArticle_id(int article_id);
	int getTag_idByArticle_id(int article_id);
	void updateContent(Article article);
	void updateSummary(Article article);
	List<Article> search(String searchInput, int start, int number);
	int searchPage(String searchInput);
	Article getTitle(int article_id);

}
