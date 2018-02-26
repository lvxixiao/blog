package lvxixiao.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lvxixiao.pojo.Tag;
import lvxixiao.service.TypeService;

@Controller
public class TypeController {
	@Resource
	TypeService typeService;
	
	/*
	 * 获取页数
	 * */
	@CrossOrigin(allowedHeaders= "*")
	@RequestMapping(value="/page/{tag_id}/{number}",produces = "application/json; charset=utf-8")
	public @ResponseBody String getPageNumber(@PathVariable int tag_id,
							@PathVariable int number) {
		
		return typeService.getPage(tag_id, number);

	}
	/*
	 * 向页面传递文章的所有类型
	 * {"Java" : 1,"MySQL" : 2}
	 */
	@RequestMapping(value="/category" ,produces = "application/json; charset=utf-8")
	public @ResponseBody String type() {
			
		return typeService.getCategorys();	
		
	}
	/*
	 * 向页面传递文章的标签
	 * {"Java核心技术卷" : 1}
	 */
	@CrossOrigin(allowedHeaders= "category_id")
	@RequestMapping(value="/tag",produces = "application/json; charset=utf-8")
	public @ResponseBody String tag(int category_id) {

		return typeService.getTags(category_id);
		
	}
	/*
	 * 获得所有标签的数量，并分页 
	 */
	@RequestMapping(value="/tagPage/{number}",produces="application/json; charset=utf-8")
	public @ResponseBody String tagPage(@PathVariable int number) {
		return typeService.getTagPage(number);
	}
	/*
	 * 删除标签
	 * */
	@RequestMapping(value="/admin/deleteTag/{tag_id}",produces="application/json; charset=utf-8")
	public @ResponseBody String delTagByTag_id(HttpServletRequest request,@PathVariable int tag_id) {
		typeService.delTagByTag_id(request,tag_id);
		return "{\"status\":\"200\"}";
	}
	/*
	 * 获得标签列表
	 * */
	@RequestMapping(value="/getTagList/{current}/{number}",produces="application/json; charset=utf-8")
	public @ResponseBody String getTagList(@PathVariable int current,@PathVariable int number) {
		return typeService.getTagList(current * number,number);
	}
	//添加tag
	@RequestMapping(value="/admin/addTag",produces="application/json; charset=utf-8")
	public @ResponseBody String addTag(HttpServletRequest request,Tag tag) {
		return typeService.addTag(request,tag);
	}
}
