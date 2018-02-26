package lvxixiao.service.impl;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.youbenzi.mdtool.tool.MDTool;

import lvxixiao.mapper.ArticleMapper;
import lvxixiao.mapper.TagMapper;
import lvxixiao.pojo.Article;
import lvxixiao.service.ArticleService;
import lvxixiao.util.Tools;
@Service
public class ArticleServiceImpl implements ArticleService {
	
	private static final Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);
	
	@Resource
	ArticleMapper articleMapper;
	@Resource
	TagMapper tagMapper;
	@Resource
	Gson gson;
	
	@Override
	public String addArticle(HttpServletRequest request,Article article,MultipartFile[] files) {
	
		try {
			for (int i = 0; i < files.length; i++) {
				String name = files[i].getOriginalFilename();
				if(name.contains(".md")) {
					String content = new String(files[i].getBytes(),"UTF-8");
					article.setTitle(name.replaceFirst(".md",""));
					article.setContent(content);
					article.setSummary(Tools.getSummary(content));
					articleMapper.addArticle(article);
					logger.info("上传文章,上传的地址:"+Tools.getIpAddr(request)+",文章名:"+article.getTitle());
				} else {
					tagMapper.updateNumber(article.getCategory_id(),article.getTag_id());
					logger.info("上传文章失败,因为格式错误,上传的地址:"+Tools.getIpAddr(request));
					return "{\"status\":400,\"message\":\""+name+"格式错误,请上传markdown文件\"}";
				}
				if(i == files.length - 1) {
					tagMapper.updateNumber(article.getCategory_id(),article.getTag_id());
					return "{\"status\":200,\"message\":\"上传文章成功！\"}";
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "{\"status\":500,\"message\":\"服务器错误\"}";
	}
	
	@Override
	public String getArticleByTag_id(int tag_id, int start, int number) {
		List<Article> articles = null;
		if(tag_id != 0)
			articles = articleMapper.getArticleByTag_id(tag_id, start, number);
		else
			articles = articleMapper.getArticleByDate(start,number);

		articles.forEach(article ->{						
			String summary = MDTool.markdown2Html(article.getSummary());
			article.setSummary(summary);		
		});
		return gson.toJson(articles);		
	}


	@Override
	public String getArticleByArticle_id(HttpServletRequest request,int article_id) {
		Article article = articleMapper.getArticleByArticle_id(article_id);
		String content = MDTool.markdown2Html(article.getContent());
		article.setContent(content);
		logger.info("文章被访问,访问地址:"+Tools.getIpAddr(request)+",被访问的文章名:"+article.getTitle());
		return gson.toJson(article);
	}

	@Override
	public String getArticleList(int start,int number) {
		List<Article> articles = articleMapper.getArticleList(start,number);
		return gson.toJson(articles);
	}

	@Override
	public void delArticleByArticle_id(HttpServletRequest request,int article_id) {
		//此处查询title,category_id,tag_id
		Article article = articleMapper.getTitle(article_id);
		tagMapper.updateNumber(article.getCategory_id(),article.getTag_id());
		logger.info("文章被删除,访问地址:"+Tools.getIpAddr(request)+",被删除的文章名:"+title);
		articleMapper.delArticleByArticle_id(article_id);
		
	}

	@Override
	public String getArticleContent(int article_id) {
		Article article = articleMapper.getArticleContent(article_id);
		return gson.toJson(article);
	}

	@Override
	public String getArticleSummary(int article_id) {
		Article article = articleMapper.getArticleSummary(article_id);
		return gson.toJson(article);
	}

	@Override
	public void update(HttpServletRequest request,Article article) {
		
		if(article.getCategory_id() == 0 || article.getTag_id() ==0) {
			article.setCategory_id(articleMapper.getCtagory_idByArticle_id(article.getArticle_id()));
			article.setTag_id(articleMapper.getTag_idByArticle_id(article.getArticle_id()));
		}
		if(article.getContent() != null) {
			articleMapper.updateContent(article);
			logger.info("文章内容被更新,访问地址:"+Tools.getIpAddr(request)+",被更新的文章名:"+article.getTitle());
		} else {
			if(article.getSummary().length() > 500)
				article.setSummary(article.getSummary().substring(0, 500));
			articleMapper.updateSummary(article);
			logger.info("文章摘要被更新,访问地址:"+Tools.getIpAddr(request)+",被更新的文章名:"+article.getTitle());
			
		}
	}

	@Override
	public String search(HttpServletRequest request,String searchInput, int start, int number) {
		List<Article> articles = articleMapper.search(searchInput,start,number);
		logger.info("地址:"+Tools.getIpAddr(request)+",搜索文章,搜索内容:"+searchInput);
		return gson.toJson(articles);
	}

	@Override
	public String searchPage(String searchInput, int number) {
		int articleNumber = articleMapper.searchPage(searchInput);
		int pageCount = articleNumber / number;
		if(articleNumber % number != 0)
			pageCount++;
		return "{\"pageCount\":"+pageCount+",\"articleNumber\":"+articleNumber+"}";
	}
	
}
