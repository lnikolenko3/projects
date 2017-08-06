package models;

import enums.Status;
import enums.Year;

public class AppView {
	
	private String project;
	private String major;
	private String user;
	private Year year;
	private Status status;
	
	public AppView(String project, String user, String major,
			Year year, Status status) {
		this.project = project;
		this.user = user;
		this.major = major;
		this.year = year;
		this.status = status;
	}
	
	public String getProject() {
		return project;
	}
	
	public String getUser() {
		return user;
	}
	
	public String getMajor() {
		return major;
	}
	
	public Year getYear() {
		return year;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
}
