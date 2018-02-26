package lvxixiao.mapper;

import java.util.List;

import lvxixiao.pojo.Category;

public interface CategoryMapper {
	void addCategory(Category category);
	Category getCategoryByCategory_id(int category_id);
	List<Category> getCategorys();
}
