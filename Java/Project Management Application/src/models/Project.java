package models;

import java.util.List;

import enums.Year;

public class Project {
	
	private String name;
	private String advisor;
	private String advisorEmail;
	private String description;
	private List<String> categories;
	private String designation;
	private String majorReq;
	private Year yearReq;
	private String departmentReq;
	private int numStudents;
	
	public Project(String name, String advisor, String advisorEmail, String description,
			List<String> categories, String designation, String majorReq,
			Year yearReq, String departmentReq, int numStudents) {
		this.name = name;
		this.advisor = advisor;
		this.advisorEmail = advisorEmail;
		this.description = description;
		this.categories = categories;
		this.designation = designation;
		this.majorReq = majorReq;
		this.yearReq = yearReq;
		this.departmentReq = departmentReq;
		this.numStudents = numStudents;
	}
	
	public String getName() {
		return name;
	}

	public String getAdvisor() {
		return advisor;
	}

	public String getDescription() {
		return description;
	}

	public String getDesignation() {
		return designation;
	}

	public String getAdvisorEmail() {
		return advisorEmail;
	}

	public List<String> getCategories() {
		return categories;
	}

	public String getMajorReq() {
		return majorReq;
	}

	public Year getYearReq() {
		return yearReq;
	}

	public String getDepartmentReq() {
		return departmentReq;
	}

	public int getNumStudents() {
		return numStudents;
	}
}
