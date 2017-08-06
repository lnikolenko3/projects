package app;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

import database.Connector;
import enums.Year;
import enums.Status;
import models.AppView;
import models.Course;
import models.PopularProject;
import models.Profile;
import models.Project;
import models.ProjectApp;
import models.Report;
import models.Searchable;

public class Querier {
	private static String DBEntry(Object o) {
		if (o == null) {
			return "NULL";
		}
		if (o.toString().equals("NULL")) {
			return o.toString();
		}
		return "'" + o.toString() + "'";
	}
	private static String DBEntryWildcard(Object o) {
		if (o == null) {
			return "NULL";
		}
		if (o.toString().equals("NULL")) {
			return o.toString();
		}
		return "'%" + o.toString() + "%'";

	}

	/**
	 * Checks User table for tuple with matching username and password.
	 *
	 * @param c Connection to database
	 * @param user
	 * @param pass
	 * @return A Profile with the username and isStudent fields, or null if no match
	 */
	public static Profile getLoginProfile(Connector c, String user, String pass) {
		String q = "SELECT * FROM USER LEFT JOIN MAJOR ON USER.Major = MAJOR.Mname WHERE Uname = '" + user + "' AND Password = '" + pass + "';";
		ResultSet rs = null;
		try {
			rs = c.execute(q);
			if (!rs.isBeforeFirst()) {
				//the tuple was not found
				return null;
			}
			rs.next();
			Year year = Year.getYear(rs.getString("Year"));
			String major = rs.getString("Major");
			int flag  = rs.getInt("Student");
			String department = rs.getString("Department");
			boolean isStudent = false;
			if (flag == 1) {
				isStudent = true;
			}
			return new Profile(user, major, year, department, isStudent);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Error while logging in");
			return null;
		}
	}

	/**
	 * Adds new tuple to User table if no User is already using the same username
	 * or email.
	 *
	 * @param c Connection to database
	 * @param username
	 * @param email
	 * @param password
	 * @return 0 if username is taken, 1 if email is taken, 2 if registered successfully
	 * @return -1 if an unpredicted Exception occurred
	 */
	public static int registerAccount(Connector c, String user, String email, String pass) {
		try {
			String q = "INSERT INTO USER(Uname, Password, GTEmail, Student) VALUES ('" + user + "', '" + pass + "', '" + email + "', " + "1);";
			c.update(q);
			return 2;
		} catch(Exception e) {
			if (e.getMessage().contains("PRIMARY")) {
				return 0;
			}
			if (e.getMessage().contains("for key")) {
				return 1;
			}
			System.out.println("Something else went wrong");
			System.out.println(e.getMessage());
			return -1;
		}
	}

	/**
	 * Given a username, retrieve the matching User tuple from the User table.
	 * Place the major, year, and department from the tuple into the given profile.
	 *
	 * The student's username is profile.getUsername()
	 * Year is an enum. Call Year.getYear(String) to get the appropriate enum.
	 *
	 * @param c Connection to database
	 * @param profile
	 */
	public static void refreshProfile(Connector c, Profile profile) {
		//Check user tuple in database
		String username = profile.getUsername();
		String major = null;
		String year = null;
		String department = null;
		try {
			username = DBEntry(username);
			ResultSet rs = c.execute("select * from USER LEFT JOIN MAJOR ON USER.Major = MAJOR.Mname where Uname = " + username + ";");
			rs.next();
			major = rs.getString("Major");
			year = rs.getString("Year");
			department = rs.getString("Department");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Error while refreshing profile");
		}
		profile.setMajor(major);
		profile.setYear(Year.getYear(year));
		profile.setDepartment(department);
	}

	/**
	 * Return a list of all majors from the Major table.
	 *
	 * @param c Connection to database
	 * @return List of majors.
	 */
	public static List<String> getMajorList(Connector c) {
		ArrayList<String> arr = new ArrayList<>();
		try {
			ResultSet rs = c.execute("SELECT Mname FROM MAJOR;");
			while (rs.next()) {
				arr.add(rs.getString("Mname"));
			}
		} catch (Exception e) {
			System.out.println("Error while retrieving majors");
		}


		return arr;
	}

	/**
	 * Return a list of all departments from the Department table.
	 *
	 * @param c Connection to database
	 * @return List of departments.
	 */
	public static List<String> getDepartmentList(Connector c) {
		ArrayList<String> arr = new ArrayList<>();
		try {
			ResultSet rs = c.execute("SELECT Dname from DEPARTMENT;");
			while (rs.next()) {
				arr.add(rs.getString("Dname"));
			}
		} catch (Exception e) {
			System.out.println("Error while retrieving departments");
		}


		return arr;
	}

	/**
	 * Updates tuple in User table to given major and year.
	 * Updates given profile to updated values, including department.
	 * 
	 * If either major or year is null, do not update the value.
	 * They will not both be null.
	 *
	 * Year is an enum. To get the String value, call Year.toString().
	 * Ex: Year.JUNIOR.toString() = "Junior"
	 *
	 * The User's username can be retrieved with profile.getUsername()
	 *
	 * @param c Connection to database
	 * @param profile
	 * @param major
	 * @param year
	 */
	public static void updateUser(Connector c, Profile profile, String major, Year year) {
		String username = DBEntry(profile.getUsername());
		String major_str = DBEntry(major);
		String department = null;
		try {
			if (major != null && year != null) {
				String year_str = DBEntry(year.toString());
				c.update("UPDATE USER SET Major = " + major_str + ", Year = " + year_str + " WHERE Uname = " + username + ";");
				String q = "SELECT Department FROM MAJOR WHERE Mname = " + major_str + ";";
				ResultSet rs = c.execute(q);
				rs.next();
				department = rs.getString("Department");	
			} else if (major != null) {
				c.update("UPDATE USER SET Major = " + major_str + " WHERE Uname = " + username + ";");
				String q = "SELECT Department FROM MAJOR WHERE Mname = " + major_str + ";";
				ResultSet rs = c.execute(q);
				rs.next();
				department = rs.getString("Department");	

			} else if (year != null) {
				String year_str = DBEntry(year.toString());
				c.update("UPDATE USER SET Year = " + year_str + " WHERE Uname = " + username + ";");
				department = profile.getDepartment();	
			}
			profile.setMajor(major);
			profile.setYear(year);
			profile.setDepartment(department);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Error while updating profile in the DB");
		}
	}

	/**
	 * Retrieves all Application with a foreign key to the given student.
	 *
	 * ProjectApp is constructed by calling 'new ProjectApp(pName, status, date)'
	 * pName is the name of the project
	 * status is an enum, you can convert to status by calling Status.getStatus(String)
	 * Ex: Status.getStatus("Accepted") = Status.ACCEPTED
	 * date is a String (for now, I might change it later)
	 *
	 * @param c Connection to database
	 * @param user
	 * @return List of applications
	 */
	public static List<ProjectApp> getStudentAppList(Connector c, String user) {
		ArrayList<ProjectApp> result = new ArrayList<>();
		try {
			ResultSet rs = c.execute("SELECT Pname, Status, Date FROM APPLICATION WHERE Uname = "
					+ DBEntry(user) + ";");
			while (rs.next()) {
				Status s = Status.getStatus(rs.getString("Status"));
				ProjectApp pa = new ProjectApp(rs.getString("Pname"), s, rs.getString("Date"));
				result.add(pa);
			}
		} catch (Exception e) {
			System.out.println("Error while getting applications for a given student");
		}
		return result;
	}

	/**
	 * Finds the given project in the Project able.
	 *
	 * @param c Connection to database
	 * @param projectName
	 * @return Info on the project
	 */
	public static Project getProject(Connector c, String projectName) {
		Project project = null;
		try {
			String project_DB = DBEntry(projectName);
			ResultSet rs = c.execute("SELECT * FROM PROJECT WHERE Pname = " + project_DB + ";");
			rs.next();
			String advisor = rs.getString("Aname");
			String advisorEmail = rs.getString("Aemail");
			String description = rs.getString("Description");
			String designation = rs.getString("Designation");
			String majorReq = rs.getString("Mreq");
			Year yearReq = Year.getYear(rs.getString("Yreq"));
			String departmentReq = rs.getString("Dreq");
			int numStudents = rs.getInt("Num_students");
			ArrayList<String> categories = new ArrayList<>();
			rs = c.execute("SELECT * FROM P_CATEGORY WHERE Project = " + project_DB + ";");
			while (rs.next()) {
				categories.add(rs.getString("Category"));
			}
			project = new Project(projectName, advisor, advisorEmail, description, categories, designation, majorReq, 
					yearReq, departmentReq, numStudents);

		} catch (Exception e) {
			System.out.println("Error while getting projects");
		}
		return project;
	}

	/**
	 * Creates a new tuple on the Application table if one does not
	 * already exist for the given user and project combination.
	 *
	 * You do not need to check major, year, and department requirements here.
	 * That has already been done when this method is called.
	 * Just check if the student has already applied to the project.
	 *
	 * Don't forget to set the date on the application to the current date.
	 *
	 * @param c Connection to database
	 * @param user
	 * @param project
	 * @return Whether application was successfully created.
	 */
	public static boolean createApplication(Connector c, String user, String project) {
		boolean flag = false;
		try {
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyy");
			Date date = new Date();
			String strDate = dateFormat.format(date);
			c.update("INSERT INTO APPLICATION VALUES(" + DBEntry(user) + ", " + DBEntry(project) + ", " 
					+ DBEntry(strDate) + ", " + DBEntry(Status.PENDING.toString()) + ");");
			flag = true;
		} catch (Exception e) {
			if (e.getMessage().contains("PRIMARY")) {
				flag = false;
				System.out.println("Application was not created due to duplication");
			} else {
				System.out.println("Error while creating an application");
			}
		}
		return flag;
	}

	/**
	 * Finds the given course in the Course table given its name (not number)
	 *
	 * @param c Connection to database
	 * @param courseName Name, not number (both are unique)
	 * @return Info on the course
	 */
	public static Course getCourse(Connector c, String courseName) {
		Course course = null;
		try {
			String course_DB = DBEntry(courseName);
			ResultSet rs = c.execute("SELECT * FROM COURSE WHERE Cname = " + course_DB + ";");
			rs.next();
			String Cnum = rs.getString("Cnum");
			String instructor = rs.getString("Instructor");
			String designation = rs.getString("Designation");
			int numStudents = rs.getInt("Num_students");
			ArrayList<String> categories = new ArrayList<>();
			rs = c.execute("SELECT * FROM C_CATEGORY WHERE Course = " + course_DB + ";");
			while (rs.next()) {
				categories.add(rs.getString("Category"));
			}
			course = new Course(Cnum, courseName, instructor, categories, designation, numStudents);

		} catch (Exception e) {
			//System.out.println(e.getMessage());
			System.out.println("Error while getting a course");
		}
		return course;
	}

	/**
	 * Retrieves the appropriate projects and courses from the database
	 * using the given parameters.
	 *
	 * Year is an enum. To get the String value, call Year.toString().
	 * Ex: Year.JUNIOR.toString() = "Junior"
	 *
	 * A type will always be given.
	 * Type is the type of result. 0 for both, 1 for project, 2 for course.
	 *
	 * The category parameter indicates which categories to be included.
	 * This means that if a project/course has any of the categories on the list,
	 * it can be included in the search results. It does not have to have all of them.
	 * (CATEGORY 1 OR CATEGORY 2), not (CATEGORY 1 AND CATEGORY 2)
	 *
	 * A list of categories will always be given, but it may be empty.
	 * If the category list is empty, then all categories may be included in
	 * the results. (Effectively the same as a list containing all categories).
	 *
	 * If any of the other parameters are null, then do not check that parameter
	 * in the query.
	 * For example, if major is null, then there is no major requirement.
	 * 
	 * If the search includes a major parameter, you must also search for projects
	 * with department requirements that fit the major parameter.
	 * Ex: Searching for CS would include projects with a COC department requirement.
	 *
	 * If the type allows for courses ('courses' or 'both'), then courses will be
	 * included in the results regardless of the parameters exclusive to projects.
	 * Major req and year req are the parameters exclusive to projects.
	 * For example, if there is a year requirement of junior, and the type is
	 * 'both', then all courses will still be included in the search results
	 * (barring any other parameters that would exclude them).
	 *
	 * Each result should be returned in a wrapper called a Searchable.
	 * Construct with 'new Searchable(String name, boolean isProject)'
	 *
	 * @param c Connection to database
	 * @param name
	 * @param designation
	 * @param categories
	 * @param majorReq
	 * @param yearReq
	 * @param type
	 * @return A list of the search results.
	 */
	public static List<Searchable> getSearchResults(Connector c, String name, String designation,
			List<String> categories, String majorReq, Year yearReq, int type) {
		ArrayList<Searchable> result = new ArrayList<>();
		if ((majorReq != null && majorReq.length() > 0) || (yearReq != null && yearReq.toString().length() > 0)) {
			if (type == 2) {
				return result;
			}
			if (type == 0) {
				type = 1;
			}
		}
		if (name == null) {
			name = "";
		}
		//search for courses
		if (type == 2 || type == 0) {
			try {
				ResultSet rs = null;
				String q = null;
				if (designation == null || designation.length() <= 0) {
					q = "SELECT DISTINCT Cname FROM COURSE LEFT JOIN C_CATEGORY" +
							" ON COURSE.Cname = C_CATEGORY.Course " + "WHERE Cname LIKE "
							+ DBEntryWildcard(name);
				} else {
					q = "SELECT DISTINCT Cname FROM COURSE LEFT JOIN C_CATEGORY"
							+ " ON COURSE.Cname = C_CATEGORY.Course"
							+ " WHERE Cname LIKE " + DBEntryWildcard(name)
							+ " AND Designation = " + DBEntry(designation);
				}
				if (categories != null && !categories.isEmpty()) {
					for (int i = 0; i < categories.size(); i++) {
						if (i == 0) {
							q += " AND (Category = " + DBEntry(categories.get(i));
						} else {
							q += " OR Category = " + DBEntry(categories.get(i));
						}
						if (i == categories.size() - 1) {
							q += ")";
						}
					}
				}
				q += ";";
				rs = c.execute(q);
				while(rs.next()) {
					result.add(new Searchable(rs.getString("Cname"), false));
				}

			} catch (Exception e) {
				System.out.println("Error while searching for courses");
				System.out.println(e.getMessage());
			}

		}
		if (type == 1 || type == 0) {
			String q  = "SELECT DISTINCT Pname FROM PROJECT LEFT JOIN P_CATEGORY ON PROJECT.Pname = P_CATEGORY.Project"
					+ " WHERE Pname LIKE " + DBEntryWildcard(name);
			if (designation != null && designation.length() != 0) {
				q += " AND Designation = " + DBEntry(designation);
			}
			if (majorReq != null && majorReq.length() != 0) {
				try {
					ResultSet rs_major = c.execute("SELECT Department FROM MAJOR "
							+ "WHERE Mname = " + DBEntry(majorReq) + ";");
					rs_major.next();
					q += " AND (Mreq = " + DBEntry(majorReq) + " OR Dreq = "
							+ DBEntry(rs_major.getString("Department")) + ")";
				} catch (Exception e) {
					System.out.println(e.getMessage());
					System.out.println("Error while retrieving the department for search");
				}
			}
			if (yearReq != null && yearReq.toString().length() != 0) {
				q += " AND Yreq = " + DBEntry(yearReq);
			}
			if (categories != null && !categories.isEmpty()) {
				for (int i = 0; i < categories.size(); i++) {
					if (i == 0) {
						q += " AND (Category = " + DBEntry(categories.get(i));
					} else {
						q += " OR Category = " + DBEntry(categories.get(i));
					}
					if (i == categories.size() - 1) {
						q += ")";
					}
				}
			}
			q += ";";
			try {
				ResultSet rs2 = c.execute(q);
				while(rs2.next()) {
					result.add(new Searchable(rs2.getString("Pname"), true));
				}

			} catch (Exception e) {
				System.out.println(e.getMessage());
				System.out.println("Error while searching for projects");
			}
		}
		return result;
	}

	/**
	 * Retrieves the list of all designations from the Designation table.
	 *
	 * @param c Connection to database
	 * @return List of designations
	 */
	public static List<String> getDesignationList(Connector c) {
		ArrayList<String> arr = new ArrayList<>();
		try {
			ResultSet rs = c.execute("SELECT * FROM DESIGNATION;");
			while (rs.next()) {
				arr.add(rs.getString("Dname"));
			}
		} catch (Exception e) {
			System.out.println("Error while retrieving designations");
		}

		return arr;
	}

	/**
	 * Retrieves the list of all categories from the Category table.
	 *
	 * @param c Connection to database
	 * @return List of categories.
	 */
	public static List<String> getCategoryList(Connector c) {
		ArrayList<String> arr = new ArrayList<>();
		try {
			ResultSet rs = c.execute("SELECT * FROM CATEGORY;");
			while (rs.next()) {
				arr.add(rs.getString("Cname"));
			}
		} catch (Exception e) {
			System.out.println("Error while retrieving categories");
		}
		return arr;
	}

	/**
	 * Adds a new Project tuple to the Project table, using the given attributes.
	 *
	 * Year is an enum. To get the String value, call Year.toString().
	 * Ex: Year.JUNIOR.toString() = "Junior"
	 *
	 * majorReq, yearReq, and depReq can be null. If they are, there is no requirement.
	 * 
	 * The major required does not have to be in the department required. If
	 * there is both a major and department requirement, a student may apply if they
	 * fulfill the major requirement OR the department requirement.
	 * 
	 * This means there is no longer an error in adding the tuple if there is a
	 * conflict between the major and department requirements.
	 *
	 * If the project name is already being used by an existing project,
	 * do not add the tuple, and return false.
	 * 
	 * If the tuple is added successfully, return true.
	 *
	 * @param c Connector for the database
	 * @param name
	 * @param adv
	 * @param email
	 * @param descrip
	 * @param categories
	 * @param designation
	 * @param numStudents
	 * @param majorReq
	 * @param yearReq1
	 * @param depReq
	 * @return Whether the tuple has been added
	 */
	public static boolean addProject(Connector c, String name, String adv, String email,
			String descrip, List<String> categories, String designation, int numStudents,
			String majorReq, Year yearReq1, String depReq) {
		//ResultSet rs = null;
		if (majorReq == null) {
			majorReq = "NULL";
		}
		String yearReq = null;
		if (yearReq1 == null) {
			yearReq = "NULL";
		} else {
			yearReq = yearReq1.toString();
		}
		if (depReq == null) {
			depReq = "NULL";
		}
		try {
			c.update("INSERT INTO PROJECT VALUES(" + DBEntry(name) + ", " + DBEntry(numStudents) + ", "
					+ DBEntry(adv) +", "+ DBEntry(email) + ", " + DBEntry(descrip) + ", " + DBEntry(designation)
					+ ", " + DBEntry(yearReq) + ", " + DBEntry(depReq) + ", " + DBEntry(majorReq) + ");");
			for (String c1: categories) {
				c.update("INSERT INTO P_CATEGORY VALUES(" + DBEntry(name) + ", " + DBEntry(c1) + ");");
			}
			return true;
		} catch (Exception e) {
			if (e.getMessage().contains("PRIMARY")) {
				return false;
			}
			System.out.println("Error while inserting a project");
			return false;
		}
	}

	/**
	 * Adds a new Course tuple to the Course table, using the given attributes.
	 *
	 * If the course number is already being used by an existing course,
	 * do not add the tuple, and return 0.
	 *
	 * If the course name is already being used by an existing course,
	 * do not add the tuple, and return 1.
	 *
	 * If the tuple is successfully added, return 2.
	 *
	 * @param c Connector to the database
	 * @param num
	 * @param name
	 * @param instructor
	 * @param designation
	 * @param categories
	 * @param numStudents
	 * @return 0 for number error, 1 for name error, 2 for success
	 */
	public static int addCourse(Connector c, String num, String name, String instructor,
			String designation, List<String> categories, int numStudents) {
		try {
			c.update("INSERT INTO COURSE VALUES(" + DBEntry(num) + ", " + DBEntry(name) + ", "
					+ DBEntry(numStudents) +", "+ DBEntry(instructor) + ", " + DBEntry(designation) + ");");
			for (String c1: categories) {
				c.update("INSERT INTO C_CATEGORY VALUES(" + DBEntry(name) + ", " + DBEntry(c1) + ");");
			}
			return 2;
		} catch (Exception e) {
			if (e.getMessage().contains("PRIMARY")) {
				return 0;
			}
			if (e.getMessage().contains("for key")) {
				return 1;
			}
			System.out.println("Error while inserting a course");
			return -1;
		}
	}

	/**
	 * Retrieves the 10 most popular projects from the Project table, sorted
	 * by number of apps (highest first).
	 *
	 * The most popular project is the project with the most applications
	 * that have a foreign key to it.
	 *
	 * PopularProject is a wrapper class, containing the project name and number
	 * of applicants.
	 * Construct a PopularProject with 'new PopularProject(String name, int numApps)'
	 *
	 * Remember, the sorting of the list needs to be done by the query itself,
	 * not in the Java portion of the method.
	 *
	 * @param c Connection to database
	 * @return List containing the 10 most popular projects, sorted by # of apps
	 */
	public static List<PopularProject> getPopularProjects(Connector c) {
		ArrayList<PopularProject> result = new ArrayList<>();
		ResultSet rs = null;
		try {
			rs = c.execute("SELECT * FROM NumAppsPerProject;");
			int counter = 0;
			while (rs.next() && counter < 10) {
				result.add(new PopularProject(rs.getString("Pname"), rs.getInt("TotalApps")));
				counter++;
			}
		} catch (Exception e) {
			System.out.println("Error while retrieving the most popular projects");
		}
		return result;

	}

	/**
	 * Retrieves a list of reports from the Project table, sorted by the
	 * acceptance rate, highest first.
	 *
	 * Each report needs the project name, the total number of applicants, the
	 * acceptance rate, and the top 3 majors among the applicants.
	 *
	 * Report is a wrapper class for each project.
	 * Construct a Report with
	 * 'new Report(String name, int numApps, double acceptRate, List<String> topMajors)'
	 *
	 * Remember, the sorting of the list needs to be done by the query itself,
	 * not in the Java portion of the method.
	 *
	 * @param c Connection to database
	 * @return List containing reports for each project, sorted by acceptance rate
	 */
	public static List<Report> getProjectReports(Connector c) {
		ArrayList<Report> result = new ArrayList<>();
		try {
			ResultSet rs = c.execute("select P.Pname, N.AcceptanceRate, N.TotalApps from PROJECT"
					+ " P LEFT JOIN ProjectAcceptanceRate1 N ON P.Pname = N.Pname ORDER BY AcceptanceRate DESC;");
			while (rs.next()) {
				String Pname = rs.getString("Pname");
				double acceptanceRate = rs.getDouble("AcceptanceRate");
				int totalApps = rs.getInt("TotalApps");
				ArrayList<String> major = new ArrayList<>();
				if (totalApps != 0) {
					ResultSet rs1 = c.execute("select Major," +
							"COUNT(*) from USER NATURAL JOIN APPLICATION " +
							"WHERE Pname = " + DBEntry(Pname)
							+ " GROUP BY Major ORDER BY COUNT(*) DESC;");
					int count = 0;
					while (rs1.next() && count < 3) {
						count++;
						major.add(rs1.getString("Major"));
					}
				}
				Report report = new Report(Pname, totalApps, acceptanceRate, major);
				result.add(report);

			}
			return result;
		} catch (Exception e) {
			//System.out.println(e.getMessage());
			System.out.println("Error while getting project report");
			return result;
		}
	}

	/**
	 * Retrieves the total number of tuples in the Application table.
	 *
	 * @param c Connection to database
	 * @return Total number of applications
	 */
	public static int getNumApps(Connector c) {
		ResultSet rs = null;
		int count = 0;
		try {
			rs = c.execute("SELECT COUNT(*) FROM APPLICATION");
			rs.next();
			count = rs.getInt("Count(*)");
		} catch (Exception e) {
			System.out.println("Error while counting applications");
		}
		return count;
	}

	/**
	 * Retrieves the total number of tuples with Status = 'Accepted' in the
	 * Application table.
	 *
	 * @param c Connection to database
	 * @return Total number of accepted applications.
	 */
	public static int getNumAcceptedApps(Connector c) {
		int count = 0;
		try {
			ResultSet rs = c.execute("SELECT COUNT(*) FROM APPLICATION WHERE Status = 'Accepted';");
			rs.next();
			count = rs.getInt("COUNT(*)");
		} catch (Exception e) {
			System.out.println("Error while retrieving number of accepted applications");
		}
		return count;
	}

	/**
	 * Retrieves a list of all applications.
	 *
	 * Each item in the list should contain the project name, applicant username
	 * applicant major, applicant year, and app status.
	 *
	 * Construct an AppView with 'new AppView(String name, String username,
	 * String major, Year year, Status status)'
	 *
	 * Year is an enum. Get the relevant Year from a string by calling
	 * Year.getYear(String).
	 *
	 * Status is an enum. Get the relevant Status from a string by calling
	 * Status.getStatus(String).
	 *
	 * @param c Connection to the database
	 * @return A list containing all applications.
	 */
	public static List<AppView> getApplications(Connector c) {
		ArrayList<AppView> result = new ArrayList<>();
		try {
			ResultSet rs = c.execute("SELECT * FROM USER NATURAL JOIN APPLICATION;");
			while (rs.next()) {
				String project = rs.getString("Pname");
				String user = rs.getString("Uname");
				String major = rs.getString("Major");
				Status status = Status.getStatus(rs.getString("Status"));
				Year year = Year.getYear(rs.getString("Year"));
				AppView av = new AppView(project, user, major, year, status);
				result.add(av);
			}
		} catch (Exception e) {
			System.out.println("Error while getting applications");
		}

		return result;
	}

	/**
	 * Changes a tuple within the Application table to the given status.
	 *
	 * Project and user are strings that identify the tuple.
	 *
	 * Status is an enum. Get the corresponding string with Status.toString().
	 * Ex: Status.REJECTED.toString() == "Rejected"
	 *
	 * @param c Connection to the database
	 * @param project
	 * @param user
	 * @param status
	 * @return Whether the tuple was successfully changed.
	 */
	public static boolean handleApplication(Connector c, String project,
			String user, Status status) {
		boolean flag = false;
		try {
			c.update("UPDATE APPLICATION SET Status = " + DBEntry(status) + "WHERE Uname = "
					+ DBEntry(user) + "AND Pname = " + DBEntry(project) + ";");
			flag = true;
		} catch(Exception e) {
			System.out.println("Error while updating status of the application");
			flag = false;
		}
		return flag;
	}
}
