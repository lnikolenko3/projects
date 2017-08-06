package pages;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import app.Manager;
import models.Course;

public class CoursePage implements Page {
	
	private Pane root;
	private Text title;
	private Text body;
	private Button mainButton;
	
	private Manager manager;
	private Course course;
	
	private static final String BODY_TEXT = "Course Name: %s\n Instructor: %s\n"
			+ "Designation: %s\nCategories: %s\nEstimated number of students: %d";
	private static final String MAIN_TEXT = "Back";
	
	private static final int ITEM_SPACING = 10;
	private static final int MAX_LENGTH = 300;
	
	public CoursePage(Manager manager, Course course) {
		this.manager = manager;
		this.course = course;
		initPage();
		initButtons();
	}
	
	private void initPage() {
		title = new Text(course.getNumber());
		body = new Text(fillBody());
		body.setWrappingWidth(MAX_LENGTH);
		mainButton = new Button(MAIN_TEXT);
		root = new VBox(ITEM_SPACING, title, body, mainButton);
	}
	
	private String fillBody() {
		String categStr = "";
		List<String> categories = course.getCategories();
		for (int i = 0; i < categories.size(); i += 1) {
			categStr += categories.get(i) + ", ";
		}
		categStr = categStr.substring(0, categStr.length() - 2);
		if (categories.size() == 0) {
			categStr = "None";
		}
		return String.format(BODY_TEXT, course.getName(), course.getInstructor(), 
				course.getDesignation(), categStr, course.getNumStudents());
	}
	
	private void initButtons() {
		mainButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				manager.openMainPage();
			}
		});
	}
	
	public Pane getRootPane() {
		return root;
	}
}
