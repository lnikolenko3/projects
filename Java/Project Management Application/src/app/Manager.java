package app;

import java.util.List;

import javafx.scene.layout.Pane;

import database.Connector;
import enums.Status;
import enums.Year;
import models.Course;
import models.Profile;
import models.Project;
import models.ProjectApp;
import models.Report;
import models.Searchable;
import pages.Page;
import pages.PopularPage;
import pages.ProfilePage;
import pages.ProjectPage;
import pages.AddCoursePage;
import pages.AddProjectPage;
import pages.AdminAppPage;
import pages.CoursePage;
import pages.FunctionPage;
import pages.LoginPage;
import pages.MainPage;
import pages.MePage;
import pages.RegisterPage;
import pages.ReportPage;
import pages.StudentAppPage;

public class Manager {

	private Pane root;

	private Profile currentUser;
	private Connector c;

	private static final String TEAM_DB = "use cs4400_Team_13;";

	public Manager(Pane root) {
		this.root = root;
		currentUser = null;
		c = new Connector();
	}

	public void initialize() {
		try {
			c.connect();
			c.execute(TEAM_DB);
			openLoginPage();
		} catch (Exception e) {
			System.out.println("Error while connecting to DB");
		}
	}
	
	public void finish() {
		c.close();
	}

	private void loadPage(Page page) {
		root.getChildren().clear();
		root.getChildren().add(page.getRootPane());
	}

	public void openLoginPage() {
		loadPage(new LoginPage(this));
	}

	public void login(LoginPage page, String username, String password) {
		currentUser = Querier.getLoginProfile(c, username, password);
		if (currentUser == null) {
			page.showErrorInvalid();
		} else if (currentUser.isStudent()) {
			openMainPage();
		} else {
			loadPage(new FunctionPage(this));
		}
	}

	public void openRegisterPage() {
		loadPage(new RegisterPage(this));
	}

	public void register(RegisterPage page, String username, String email, String password) {
		switch (Querier.registerAccount(c, username, email, password)) {
			case 0:
				page.showErrorMessageUser();
				break;
			case 1:
				page.showErrorMessageEmail();
				break;
			case 2:
				openLoginPage();
				break;
		}
	}

	public void openMainPage() {
		List<String> majors = Querier.getMajorList(c);
		List<String> designations = Querier.getDesignationList(c);
		List<String> categories = Querier.getCategoryList(c);
		List<Searchable> results = Querier.getSearchResults(c, null, null, null, null, null, 0);
		loadPage(new MainPage(this, majors, designations, categories, results));
	}

	public void searchMain(MainPage page, String name, String designation,
			List<String> categories, String major, Year year, int type) {
		page.setSearchResults(Querier.getSearchResults(c, name, designation,
				categories, major, year, type));
	}

	public void openMePage() {
		loadPage(new MePage(this));
	}

	public void openProfilePage() {
		Querier.refreshProfile(c, currentUser);
		List<String> majors = Querier.getMajorList(c);
		loadPage(new ProfilePage(this, currentUser, majors));
	}

	public void editProfile(ProfilePage page, String major, Year year) {
		Querier.updateUser(c, currentUser, major, year);
		page.setNewDepartment(currentUser.getDepartment());
	}

	public void openStudentAppPage() {
		List<ProjectApp> apps = Querier.getStudentAppList(c, currentUser.getUsername());
		loadPage(new StudentAppPage(this, apps));
	}

	public void openProjectPage(String projectName) {
		Project project = Querier.getProject(c, projectName);
		loadPage(new ProjectPage(this, project));
	}

	public void apply(ProjectPage page, Project project) {
		Querier.refreshProfile(c, currentUser);
		if (currentUser.getMajor() == null || currentUser.getYear() == null) {
			page.showMessageNoMajor();
		} else if ((project.getMajorReq() != null
				&& !currentUser.getMajor().equals(project.getMajorReq())
				&& project.getDepartmentReq() != null
				&& !currentUser.getDepartment().equals(project.getDepartmentReq()))
				|| (project.getMajorReq() == null
				&& project.getDepartmentReq() != null
				&& !project.getDepartmentReq().equals(currentUser.getDepartment()))
				|| (project.getDepartmentReq() == null
				&& project.getMajorReq() != null
				&& !project.getMajorReq().equals(currentUser.getMajor()))
				|| (project.getYearReq() != null
				&& currentUser.getYear() != project.getYearReq())) {
			page.showMessageRequirementsNotMet();
		} else {
			if (Querier.createApplication(c, currentUser.getUsername(), project.getName())) {
				page.showMessageApplicationSubmitted();
			} else {
				page.showMessageAlreadyApplied();
			}
		}
	}

	public void openCoursePage(String courseName) {
		Course course = Querier.getCourse(c, courseName);
		loadPage(new CoursePage(this, course));
	}

	public void openFunctionPage() {
		loadPage(new FunctionPage(this));
	}

	public void openAdminAppPage() {
		loadPage(new AdminAppPage(this, Querier.getApplications(c)));
	}

	public boolean handleApplication(String project, String user, Status status) {
		return Querier.handleApplication(c, project, user, status);
	}

	public void openPopularPage() {
		loadPage(new PopularPage(this, Querier.getPopularProjects(c)));
	}

	public void openReportPage() {
		int numApps = Querier.getNumApps(c);
		int numAccepted = Querier.getNumAcceptedApps(c);
		List<Report> reports = Querier.getProjectReports(c);
		loadPage(new ReportPage(this, numApps, numAccepted, reports));
	}

	public void openAddProjectPage() {
		List<String> majors = Querier.getMajorList(c);
		List<String> designations = Querier.getDesignationList(c);
		List<String> categories = Querier.getCategoryList(c);
		List<String> departments = Querier.getDepartmentList(c);
		loadPage(new AddProjectPage(this, categories, designations,
				majors, departments));
	}

	public boolean addProject(String name, String adv, String email, String descrip,
			List<String> categories, String designation, int numStudents,
			String majorReq, Year yearReq, String depReq) {
		return Querier.addProject(c, name, adv, email, descrip, categories,
				designation, numStudents, majorReq, yearReq, depReq);
	}

	public void openAddCoursePage() {
		List<String> designations = Querier.getDesignationList(c);
		List<String> categories = Querier.getCategoryList(c);
		loadPage(new AddCoursePage(this, designations, categories));
	}

	public int addCourse(String num, String name, String instructor,
			String designation, List<String> categories, int numStudents) {
		return Querier.addCourse(c, num, name, instructor, designation, categories, numStudents);
	}
	
//	public void testProfilePage() {
//		List<String> majors = Arrays.asList("CS", "ME", "MS", "EE");
//		Profile profile = new Profile("student1", "CS", Year.SOPHOMORE, "COC", true);
//		loadPage(new ProfilePage(this, profile, majors));
//	}
//
//	
//	public void testCoursePage() {
//		Course course = new Course("CS3790", "Cognitive Science", "Arriaga",
//				Arrays.asList("Adaptive learning", "Computing for good"),
//				"Community", 40);
//		loadPage(new CoursePage(this, course));
//	}
//	
//	public void testMainPage() {
//		List<String> majors = Arrays.asList("Computer Science", "Mechanical Engineering");
//		List<String> designations = Arrays.asList("Sustainable Communities", "Community");
//		List<String> categories = Arrays.asList("Adaptive learning", "Computing for good");
//		List<Searchable> results = Arrays.asList(new Searchable("Environmental Policy and Politics", false),
//				new Searchable("Excel Current Events", true),
//				new Searchable("Introduction to User Interface Design", false));
//		loadPage(new MainPage(this, majors, designations, categories, results));
//	}
//
//	public void testAppPage() {
//		ProjectApp app1 = new ProjectApp("Project 1", Status.ACCEPTED, "01/01/2000");
//		ProjectApp app2 = new ProjectApp("Project 2", Status.PENDING, "01/02/2000");
//		ProjectApp app3 = new ProjectApp("Project 3", Status.REJECTED, "01/03/2000");
//		loadPage(new StudentAppPage(this, Arrays.asList(app1, app2, app3)));
//	}
//
//	public void testAddCoursePage() {
//		List<String> designations = Arrays.asList("Sustainable Communities", "Community");
//		List<String> categories = Arrays.asList("Adaptive learning", "Computing for good");
//		loadPage(new AddCoursePage(this, designations, categories));
//	}
//
//	public void testAddProjectPage() {
//		List<String> designations = Arrays.asList("Sustainable Communities", "Community");
//		List<String> categories = Arrays.asList("Adaptive learning", "Computing for good");
//		List<String> majors = Arrays.asList("Computer Science", "Mechanical Engineering");
//		List<String> departments = Arrays.asList("Computing", "Physics");
//		loadPage(new AddProjectPage(this, categories, designations, majors, departments));
//	}
//
//	public void testPopularPage() {
//		List<PopularProject> projectList = new ArrayList<>();
//		for (int i = 0; i < 30; i += 1) {
//			projectList.add(new PopularProject("Project " + i, 30 - i));
//		}
//		loadPage(new PopularPage(this, projectList));
//	}
//
//	public void testReportPage() {
//		int numApps = 152;
//		int numAccepted = 89;
//		Report report1 = new Report("Epic Intentions", 25, 72, Arrays.asList("CS", "Math"));
//		Report report2 = new Report("Excel Current Events", 35, 57.1, Arrays.asList("ECE", "CM"));
//		Report report3 = new Report("Know Your Water", 20, 30, Arrays.asList("CS"));
//		loadPage(new ReportPage(this, numApps, numAccepted, Arrays.asList(report1, report2, report3)));
//	}
//
//	public void testAdminAppPage() {
//		AppView v1 = new AppView("Project 1", "Student 1", "CS", Year.FRESHMAN, Status.PENDING);
//		AppView v2 = new AppView("Project 2", "Student 2", "EE", Year.SOPHOMORE, Status.PENDING);
//		AppView v3 = new AppView("Project 3", "Student 3", "ME", Year.JUNIOR, Status.PENDING);
//		loadPage(new AdminAppPage(this, Arrays.asList(v1, v2, v3)));
//	}
}
