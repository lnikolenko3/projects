package pages;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import app.Manager;
import enums.Year;

public class AddProjectPage implements Page {

	private GridPane root;
	private Text title;
	private Text nameLabel;
	private Text advLabel;
	private Text emailLabel;
	private Text descripLabel;
	private Text desigLabel;
	private Text categLabel;
	private Text estimLabel;
	private Text majorReqLabel;
	private Text yearReqLabel;
	private Text depReqLabel;
	private TextField nameField;
	private TextField advField;
	private TextField emailField;
	private TextArea descripField;
	private ComboBox<String> desigField;
	private Pane categBox;
	private TextField estimField;
	private ComboBox<String> majorReqField;
	private ComboBox<String> yearReqField;
	private ComboBox<String> depReqField;
	private Button addButton;
	private Button removeButton;
	private Button funcButton;
	private Button createButton;
	private Text errorMessage;

	private Manager manager;
	private List<String> designations;
	private List<String> categories;
	private List<String> majors;
	private List<String> departments;
	private ObservableList<String> obsCategories;

	private static final String TITLE_TEXT = "Add a Project";
	private static final String NAME_TEXT = "Project Name";
	private static final String ADV_TEXT = "Advisor";
	private static final String EMAIL_TEXT = "Advisor Email";
	private static final String DESCRIP_TEXT = "Description";
	private static final String DESIG_TEXT = "Designation";
	private static final String CATEG_TEXT = "Category";
	private static final String ESTIM_TEXT = "Estimated #\nof students";
	private static final String MAJOR_REQ = "Major Requirement";
	private static final String YEAR_REQ = "Year Requirement";
	private static final String DEP_REQ = "Department Requirement";
	private static final String ADD_CATEG = "Add a new category";
	private static final String REMOVE_CATEG = "Remove a category";
	private static final String FUNC_TEXT = "Back";
	private static final String CREATE_TEXT = "Submit";
	private static final String REQ_DEFAULT = "No Requirement";
	private static final String ERROR_NOT_INT = "Please enter an valid value for"
			+ " estimated number of students.";
	private static final String ERROR_EMPTY = "Please fill out all fields.";
	private static final String NAME_TAKEN = "Project name is already in use.";
	private static final String SUCCESS = "Project added successfully.";

	private static final int ITEM_SPACING = 10;

	public AddProjectPage(Manager manager, List<String> categories,
			List<String> designations, List<String> majors,
			List<String> departments) {
		this.manager = manager;
		this.designations = designations;
		this.categories = categories;
		this.majors = majors;
		this.departments = departments;
		obsCategories = FXCollections.observableArrayList(categories);
		initPage();
		initButtons();
		initDropdowns();
	}

	private void initPage() {
		title = new Text(TITLE_TEXT);
		nameLabel = new Text(NAME_TEXT);
		advLabel = new Text(ADV_TEXT);
		emailLabel = new Text(EMAIL_TEXT);
		descripLabel = new Text(DESCRIP_TEXT);
		desigLabel = new Text(DESIG_TEXT);
		categLabel = new Text(CATEG_TEXT);
		majorReqLabel = new Text(MAJOR_REQ);
		yearReqLabel = new Text(YEAR_REQ);
		depReqLabel = new Text(DEP_REQ);
		estimLabel = new Text(ESTIM_TEXT);
		nameField = new TextField();
		advField = new TextField();
		emailField = new TextField();
		descripField = new TextArea();
		desigField = new ComboBox<String>();
		categBox = new VBox(ITEM_SPACING);
		estimField = new TextField();
		majorReqField = new ComboBox<String>();
		yearReqField = new ComboBox<String>();
		depReqField = new ComboBox<String>();
		addButton = new Button(ADD_CATEG);
		removeButton = new Button(REMOVE_CATEG);
		funcButton = new Button(FUNC_TEXT);
		createButton = new Button(CREATE_TEXT);
		errorMessage = new Text();
		errorMessage.setVisible(false);
		root = new GridPane();
		root.add(title, 0, 0, 2, 1);
		root.add(nameLabel, 0, 1);
		root.add(advLabel, 0, 2);
		root.add(emailLabel, 0, 3);
		root.add(descripLabel, 0, 4);
		root.add(desigLabel, 0, 5);
		root.add(estimLabel, 0, 6);
		root.add(majorReqLabel, 0, 7);
		root.add(yearReqLabel, 0, 8);
		root.add(depReqLabel, 0, 9);
		root.add(funcButton, 0, 10);
		root.add(nameField, 1, 1);
		root.add(advField, 1, 2);
		root.add(emailField, 1, 3);
		root.add(descripField, 1, 4);
		root.add(desigField, 1, 5);
		root.add(estimField, 1, 6);
		root.add(majorReqField, 1, 7);
		root.add(yearReqField, 1, 8);
		root.add(depReqField, 1, 9);
		root.add(createButton, 1, 10);
		root.add(categLabel, 2, 1);
		root.add(addButton, 2, 2);
		root.add(removeButton, 2, 3);
		root.add(categBox, 3, 1, 1, 11);
		root.add(errorMessage, 0, 11, 4, 1);
	}

	private void initButtons() {
		addButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				addCategory();
			}
		});
		removeButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				removeCategory();
			}
		});
		funcButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				manager.openFunctionPage();
			}
		});
		createButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				submitProjectParameters();
			}
		});
	}

	private void initDropdowns() {
		desigField.getItems().addAll(designations);
		desigField.setValue(designations.get(0));
		majorReqField.getItems().add(REQ_DEFAULT);
		majorReqField.getItems().addAll(majors);
		majorReqField.setValue(REQ_DEFAULT);
		depReqField.getItems().add(REQ_DEFAULT);
		depReqField.getItems().addAll(departments);
		depReqField.setValue(REQ_DEFAULT);
		yearReqField.getItems().add(REQ_DEFAULT);
		for (Year year : Year.values()) {
			yearReqField.getItems().add(year.toString());
		}
		yearReqField.setValue(REQ_DEFAULT);
		addCategory();
	}

	private void addCategory() {
		if (categBox.getChildren().size() < categories.size()) {
			ComboBox<String> field = new ComboBox<String>(obsCategories);
			categBox.getChildren().add(field);
			field.setValue(categories.get(0));
		}
	}

	private void removeCategory() {
		if (categBox.getChildren().size() > 1) {
			categBox.getChildren().remove(categBox.getChildren().size() - 1);
		}
	}

	private void showErrorNotInt() {
		errorMessage.setText(ERROR_NOT_INT);
		errorMessage.setVisible(true);
	}

	private void showErrorEmpty() {
		errorMessage.setText(ERROR_EMPTY);
		errorMessage.setVisible(true);
	}

	private void showErrorNameTaken() {
		errorMessage.setText(NAME_TAKEN);
		errorMessage.setVisible(true);
	}

	private void showMessageSuccess() {
		errorMessage.setText(SUCCESS);
		errorMessage.setVisible(true);
	}

	@SuppressWarnings("unchecked")
	private void submitProjectParameters() {
		String name = nameField.getText();
		String adv = advField.getText();
		String email = emailField.getText();
		String descrip = descripField.getText();
		String desig = desigField.getValue();
		Set<String> categSet = new HashSet<>();
		for (int i = 0; i < categBox.getChildren().size(); i += 1) {
			categSet.add(((ComboBox<String>) categBox.getChildren().get(i)).getValue());
		}
		List<String> categList = new ArrayList<>(categSet);
		int numStudents = 0;
		String estim = estimField.getText();
		try {
			if (!estim.equals("")) {
				numStudents = Integer.valueOf(estim);
			}
			if (numStudents < 0) {
				showErrorNotInt();
				return;
			}
		} catch (NumberFormatException e) {
			showErrorNotInt();
			return;
		}
		String majorReq = majorReqField.getValue().equals(REQ_DEFAULT) ?
				null : majorReqField.getValue();
		String depReq = depReqField.getValue().equals(REQ_DEFAULT) ?
				null : depReqField.getValue();
		Year yearReq = yearReqField.getValue().equals(REQ_DEFAULT) ?
				null : Year.getYear(yearReqField.getValue());
		if (name.equals("") || adv.equals("") || email.equals("")
				|| descrip.equals("") || estim.equals("")) {
			showErrorEmpty();
			return;
		}
		if (manager.addProject(name, adv, email, descrip, categList,
				desig, numStudents, majorReq, yearReq, depReq)) {
			showMessageSuccess();
		} else {
			showErrorNameTaken();
		}
	}


	public Pane getRootPane() {
		return root;
	}
}
