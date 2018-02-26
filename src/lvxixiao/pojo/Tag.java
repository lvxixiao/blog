package lvxixiao.pojo;

public class Tag {
	private int tag_id;
	private String name;
	private String categoryName;
	private int category_id;
	private int number;
	public Tag() {
		super();
	}

	public int getTag_id() {
		return tag_id;
	}

	public void setTag_id(int tag_id) {
		this.tag_id = tag_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCategory_id() {
		return category_id;
	}

	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	@Override
	public String toString() {
		return "Tag [tag_id=" + tag_id + ", name=" + name + ", categoryName=" + categoryName + ", category_id="
				+ category_id + ", number=" + number + "]";
	}
}
