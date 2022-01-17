package model;

public class Colocation {
	
	private String id;
	private String nameColocation;
	private String userAdmin;
	
	public Colocation(String id, String nameColocation, String userAdmin) {
		super();
		this.id = id;
		this.nameColocation = nameColocation;
		this.userAdmin = userAdmin;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNameColocation() {
		return nameColocation;
	}

	public void setNameColocation(String nameColocation) {
		this.nameColocation = nameColocation;
	}

	public String getUserAdmin() {
		return userAdmin;
	}

	public void setUserAdmin(String userAdmin) {
		this.userAdmin = userAdmin;
	}
	

}
