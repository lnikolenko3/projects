package models;

import java.util.List;

public class Course {
	
	private String number;
	private String name;
	private String instructor;
	private List<String> categories;
	private String designation;
	private int numStudents;
	
	public Course(String number, String name, String instructor, List<String> categories,
			String designation, int numStudents) {
		this.number = number;
		this.name = name;
		this.instructor = instructor;
		this.categories = categories;
		this.designation = designation;
		this.numStudents = numStudents;
	}
	
	public String getNumber() {
		return number;
	}
	
	public String getName() {
		return name;
	}

	public String getInstructor() {
		return instructor;
	}

	public String getDesignation() {
		return designation;
	}

	public List<String> getCategories() {
		return categories;
	}

	public int getNumStudents() {
		return numStudents;
	}
}
