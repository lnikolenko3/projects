package models;

import enums.Year;

public class Profile {
	
	private String username;
	private String major;
	private Year year;
	private String department;
	private boolean isStudent;

	public Profile(String username, String major, Year year, String department, boolean isStudent) {
		this.username = username;
		this.major = major;
		this.year = year;
		this.department = department;
		this.isStudent = isStudent;
	}
	
	public String getUsername() {
		return username;
	}

	public String getMajor() {
		return major;
	}
	
	public void setMajor(String major) {
		this.major = major;
	}

	public Year getYear() {
		return year;
	}
	
	public void setYear(Year year) {
		this.year = year;
	}

	public String getDepartment() {
		return department;
	}
	
	public void setDepartment(String department) {
		this.department = department;
	}
	
	public boolean isStudent() {
		return isStudent;
	}
}
