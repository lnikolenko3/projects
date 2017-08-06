package models;

import enums.Status;

public class ProjectApp {

	private String project;
	private Status status;
	private String date;
	
	public ProjectApp(String project, Status status, String date) {
		this.project = project;
		this.status = status;
		this.date = date;
	}
	
	public String getProject() {
		return project;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public String getDate() {
		return date;
	}
}
