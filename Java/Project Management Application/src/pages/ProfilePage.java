package pages;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import app.Manager;
import models.Profile;
import enums.Year;

public class ProfilePage implements Page {

	private GridPane root;
	private Text title;
	private Text majorText;
	private ComboBox<String> majorField;
	private Text yearText;
	private ComboBox<String> yearField;
	private Text depText;
	private Text depField;
	private Button editButton;
	private Button meButton;
	private Text editMessage;
	
	private Manager manager;
	private Profile profile;
	private List<String> majors;
	
	private static final String TITLE_TEXT = "Edit Profile";
	private static final String MAJOR_TEXT = "Major";
	private static final String YEAR_TEXT = "Year";
	private static final String DEP_TEXT = "Department";
	private static final String EDIT_TEXT = "Edit";
	private static final String ME_TEXT = "Back";
	private static final String EDIT_MSG = "Profile changes saved.";
	private static final String DROP_DEFAULT = "Please Select";

	public ProfilePage(Manager manager, Profile profile, List<String> majors) {
		this.manager = manager;
		this.profile = profile;
		this.majors = majors;
		initPage();
		initButtons();
		initDropdowns();
	}
	
	private void initPage() {
		title = new Text(TITLE_TEXT);
		majorText = new Text(MAJOR_TEXT);
		majorField = new ComboBox<String>();
		yearText = new Text(YEAR_TEXT);
		yearField = new ComboBox<String>();
		depText = new Text(DEP_TEXT);
		depField = new Text();
		editButton = new Button(EDIT_TEXT);
		meButton = new Button(ME_TEXT);
		editMessage = new Text(EDIT_MSG);
		editMessage.setVisible(false);
		root = new GridPane();
		root.add(title, 0, 0);
		root.add(majorText, 0, 1);
		root.add(majorField, 1, 1);
		root.add(yearText, 0, 2);
		root.add(yearField, 1, 2);
		root.add(depText, 0, 3);
		root.add(depField, 1, 3);
		root.add(editButton, 0, 4);
		root.add(meButton, 1, 4);
		root.add(editMessage, 0, 5, 2, 1);
	}
	
	private void initButtons() {
		editButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				String major = majorField.getValue().equals(DROP_DEFAULT) ?
						null : majorField.getValue();
				Year year = yearField.getValue().equals(DROP_DEFAULT) ?
						null : Year.getYear(yearField.getValue());
				if (major != null || year != null) {
					manager.editProfile(ProfilePage.this, major, year);
				}
			}
		});
		meButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				manager.openMePage();
			}
		});
	}
	
	private void initDropdowns() {
		majorField.getItems().addAll(majors);
		for (Year year : Year.values()) {
			yearField.getItems().add(year.toString());
		}
		if (profile.getMajor() != null) {
			majorField.setValue(profile.getMajor());
		} else {
			majorField.getItems().add(0, DROP_DEFAULT);
			majorField.setValue(DROP_DEFAULT);
		}
		if (profile.getYear() != null) {
			yearField.setValue(profile.getYear().toString());
		} else {
			yearField.getItems().add(0, DROP_DEFAULT);
			yearField.setValue(DROP_DEFAULT);
		}
		if (profile.getDepartment() != null) {
			depField.setText(profile.getDepartment());
		}
	}
	
	public void setNewDepartment(String dep) {
		editMessage.setVisible(true);
		depField.setText(dep == null ? "" : dep);
		if (profile.getMajor() != null
				&& majorField.getItems().get(0).equals(DROP_DEFAULT)) {
			majorField.getItems().remove(0);
		}
		if (profile.getYear() != null
				&& yearField.getItems().get(0).equals(DROP_DEFAULT)) {
			yearField.getItems().remove(0);
		}
	}
	
	public Pane getRootPane() {
		return root;
	}
}
