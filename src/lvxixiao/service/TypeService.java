package lvxixiao.service;

import javax.servlet.http.HttpServletRequest;

import lvxixiao.pojo.Tag;

public interface TypeService {
	String getCategorys();
	String getTags(int category_id);
	String getPage(int tag_id,int number);
	String getTagPage(int number);
	void delTagByTag_id(HttpServletRequest request,int tag_id);
	String getTagList(int start, int number);
	String addTag(HttpServletRequest request,Tag tag);
}
