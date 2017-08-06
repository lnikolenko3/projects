package pages;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import app.Manager;
import models.Project;

public class ProjectPage implements Page {
	
	private Pane root;
	private Text title;
	private Text body;
	private Button mainButton;
	private Button applyButton;
	private Pane buttonBox;
	private Text message;
	
	private Manager manager;
	private Project project;
	
	private static final String BODY_TEXT = "Advisor: %s (%s)\n"
			+ "Description: %s\nDesignation: %s\nCategories: %s\n"
			+ "Major Requirement: %s\nYear Requirement: %s\n"
			+ "Department Requirement: %s\nEstimated number of students: %d";
	private static final String REQ_TEXT = "%s students only";
	private static final String NO_REQ_TEXT = "No requirement";
	private static final String MAIN_TEXT = "Back";
	private static final String APPLY_TEXT = "Apply";
	private static final String NO_MAJOR = "You must declare your major and year before applying for a project.";
	private static final String REQ_NOT_MET = "You do not meet the requirements for this project.";
	private static final String ALREADY_APPLIED = "You have already applied to this project.";
	private static final String APPLICATION_SUBMITTED = "Your application has been submitted.";
	
	private static final int BUTTON_SPACING = 10;
	private static final int ITEM_SPACING = 10;
	private static final int MAX_LENGTH = 300;
	
	public ProjectPage(Manager manager, Project project) {
		this.manager = manager;
		this.project = project;
		initPage();
		initButtons();
	}
	
	private void initPage() {
		title = new Text(project.getName());
		body = new Text(fillBody());
		body.setWrappingWidth(MAX_LENGTH);
		mainButton = new Button(MAIN_TEXT);
		applyButton = new Button(APPLY_TEXT);
		buttonBox = new HBox(BUTTON_SPACING, mainButton, applyButton);
		message = new Text();
		message.setVisible(false);
		root = new VBox(ITEM_SPACING, title, body, buttonBox, message);
	}
	
	private String fillBody() {
		String categStr = "";
		List<String> categories = project.getCategories();
		for (int i = 0; i < categories.size(); i += 1) {
			categStr += categories.get(i);
			if (i == categories.size() - 1) {
				categStr += ", ";
			}
		}
		if (categStr.equals("")) {
			categStr = "None";
		}
		String majorReqStr = project.getMajorReq() != null ? String.format(REQ_TEXT,
				project.getMajorReq()) : NO_REQ_TEXT;
		String yearReqStr = project.getYearReq() != null ? String.format(REQ_TEXT,
				project.getYearReq().toString()) : NO_REQ_TEXT;
		String depReqStr = project.getDepartmentReq() != null ? String.format(REQ_TEXT,
				project.getDepartmentReq()) : NO_REQ_TEXT;
		return String.format(BODY_TEXT, project.getAdvisor(), project.getAdvisorEmail(),
				project.getDescription(), project.getDesignation(), categStr,
				majorReqStr, yearReqStr, depReqStr, project.getNumStudents());
	}
	
	private void initButtons() {
		mainButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				manager.openMainPage();
			}
		});
		applyButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				manager.apply(ProjectPage.this, project);
			}
		});
	}
	
	public void showMessageNoMajor() {
		message.setVisible(true);
		message.setText(NO_MAJOR);
	}
	
	public void showMessageRequirementsNotMet() {
		message.setVisible(true);
		message.setText(REQ_NOT_MET);
	}
	
	public void showMessageAlreadyApplied() {
		message.setVisible(true);
		message.setText(ALREADY_APPLIED);
	}
	
	public void showMessageApplicationSubmitted() {
		message.setVisible(true);
		message.setText(APPLICATION_SUBMITTED);
	}
	
	public Pane getRootPane() {
		return root;
	}
}
