package lvxixiao.pojo;

public class Category {
	private int category_id;
	private String name;
	private int number;
		
	public Category() {
		super();
	}
	public Category(int category_id, String name,int number) {
		super();
		this.category_id = category_id;
		this.name = name;
		this.setNumber(number);
	}
	public int getCategory_id() {
		return category_id;
	}
	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	@Override
	public String toString() {
		return "Category [category_id=" + category_id + ", name=" + name + ", number=" + number + "]";
	}
}
