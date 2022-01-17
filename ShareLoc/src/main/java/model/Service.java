package model;

public class Service {
	private String id;
	private String title;
	private String description;
	private int cost;
	
	public Service(String id, String title, String description, int cost) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.cost = cost;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}
	

}
