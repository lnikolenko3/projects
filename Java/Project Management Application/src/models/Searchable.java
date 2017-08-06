package models;

public class Searchable {
	
	private String name;
	private boolean isProject;
	
	public Searchable(String name, boolean isProject) {
		this.name = name;
		this.isProject = isProject;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean isProject() {
		return isProject;
	}
}
