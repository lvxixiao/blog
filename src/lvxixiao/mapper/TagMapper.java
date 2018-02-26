package lvxixiao.mapper;

import java.util.List;

import lvxixiao.pojo.Tag;

public interface TagMapper {
	List<Tag> getTagByCategory_id(int category_id);
	List<Tag> getTags();
	void updateNumber(int category_id,int tag_id);
	int getNumberSum();
	int getNumberByTag_id(int tag_id);
	int getTagCount();
	void delTagByTag_id(int tag_id);
	List<Tag> getTagList(int start, int number);
	Tag getTagByName(String name);
	void addTag(Tag tag);
	String getTagByTag_id(int tag_id);
}
