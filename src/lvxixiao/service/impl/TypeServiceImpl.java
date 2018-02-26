package lvxixiao.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import lvxixiao.mapper.CategoryMapper;
import lvxixiao.mapper.TagMapper;
import lvxixiao.pojo.Category;
import lvxixiao.pojo.Tag;
import lvxixiao.service.TypeService;
import lvxixiao.util.Tools;

@Service
public class TypeServiceImpl implements TypeService {
	
	private static final Logger logger = LoggerFactory.getLogger(TypeServiceImpl.class);

	
	@Resource
	TagMapper tagMapper;
	
	@Resource
	CategoryMapper categoryMapper;
	
	@Resource
	Gson gson;
	
	@Override
	public String getCategorys() {
		List<Category> categorys = categoryMapper.getCategorys();
	
		return gson.toJson(categorys);
	}

	@Override
	public String getTags(int category_id) {
		List<Tag> tags = null;
		if(category_id == 0) {
			tags = tagMapper.getTags(); 
		} else {
			tags = tagMapper.getTagByCategory_id(category_id);
		}
		
		return gson.toJson(tags);
	}

	@Override
	public String getPage(int tag_id, int number) {
		int articleNumber = 0;
		int pageCount = 0;
		if(tag_id != 0) {
			articleNumber = tagMapper.getNumberByTag_id(tag_id);
		} else {
			articleNumber = tagMapper.getNumberSum();
		}
		pageCount = articleNumber/number;
		if(articleNumber % number != 0)
			pageCount++;
		
		return "{\"pageCount\":"+pageCount+",\"articleNumber\":"+articleNumber+"}";
	}

	@Override
	public String getTagPage(int number) {
		int tagNumber = tagMapper.getTagCount();
		int pageCount = 0;
		pageCount = tagNumber / number;
		if(tagNumber % number != 0)
			pageCount++;
		return "{\"pageCount\":"+pageCount+",\"tagNumber\":"+tagNumber+"}";
	}

	@Override
	public void delTagByTag_id(HttpServletRequest request,int tag_id) {
		String tagName = tagMapper.getTagByTag_id(tag_id);
		logger.info("删除标签:"+tagName+",删除地址:"+Tools.getIpAddr(request));
		tagMapper.delTagByTag_id(tag_id);
		
	}

	@Override
	public String getTagList(int start, int number) {
		/*
		 * 此处是一个设计失误。tags表只有category_id的列而没有categoryName的列
		 * tagName与categoryName经常一起使用，导致查询时经常需要两张表查询。
		 * 此项目可以只有一张tags表而不需要categorys表
		 * */
		List<Tag> tags = tagMapper.getTagList(start,number);
		List<Category> categorys = categoryMapper.getCategorys();
		Map<Integer,String> categoryMap = new HashMap<Integer,String>();
		categorys.forEach(category ->{
			categoryMap.put(category.getCategory_id(), category.getName());
		});
		tags.forEach(tag -> {
			tag.setCategoryName(categoryMap.get(tag.getCategory_id()));
		});
		return gson.toJson(tags);
	}

	@Override
	public String addTag(HttpServletRequest request,Tag tag) {
		Tag oldTag = tagMapper.getTagByName(tag.getName());
		if(oldTag != null) {
			return "{\"message\":\"标签已存在\"}";
		} else {
			tagMapper.addTag(tag);
			logger.info("添加标签:"+tag.getName()+",添加地址:"+Tools.getIpAddr(request));
		}
		return "{\"message\":\"标签添加成功\"}";
	}

}
