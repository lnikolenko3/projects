package pages;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import app.Manager;

public class FunctionPage implements Page {
	
	private Pane root;
	private Text title;
	private Button appButton;
	private Button popularButton;
	private Button reportButton;
	private Button projectButton;
	private Button courseButton;
	private Button logoutButton;
	
	private Manager manager;
	
	private static final String TITLE_TEXT = "Choose Functionality";
	private static final String VIEW_APPS = "View Applications";
	private static final String VIEW_POPULAR = "View popular project report";
	private static final String VIEW_REPORT = "View Application report";
	private static final String ADD_PROJECT = "Add a Project";
	private static final String ADD_COURSE = "Add a Course";
	private static final String LOGOUT = "Log out";
	
	private static final int ITEM_SPACING = 10;
	
	public FunctionPage(Manager manager) {
		this.manager = manager;
		initPage();
		initButtons();
	}
	
	private void initPage() {
		title = new Text(TITLE_TEXT);
		appButton = new Button(VIEW_APPS);
		popularButton = new Button(VIEW_POPULAR);
		reportButton = new Button(VIEW_REPORT);
		projectButton = new Button(ADD_PROJECT);
		courseButton = new Button(ADD_COURSE);
		logoutButton = new Button(LOGOUT);
		root = new VBox(ITEM_SPACING, title, appButton, popularButton,
				reportButton, projectButton, courseButton, logoutButton);
	}
	
	private void initButtons() {
		appButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				manager.openAdminAppPage();
			}
		});
		popularButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				manager.openPopularPage();
			}
		});
		reportButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				manager.openReportPage();
			}
		});
		projectButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				manager.openAddProjectPage();
			}
		});
		courseButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				manager.openAddCoursePage();
			}
		});
		logoutButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				manager.openLoginPage();
			}
		});
	}
	
	public Pane getRootPane() {
		return root;
	}
}
