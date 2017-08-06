package pages;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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

public class AddCoursePage implements Page {

	private GridPane root;
	private Text title;
	private Text numLabel;
	private Text nameLabel;
	private Text instrLabel;
	private Text desigLabel;
	private Text categLabel;
	private Text estimLabel;
	private TextField numField;
	private TextField nameField;
	private TextField instrField;
	private ComboBox<String> desigField;
	private Pane categBox;
	private TextField estimField;
	private Button addButton;
	private Button removeButton;
	private Button funcButton;
	private Button createButton;
	private Text errorMessage;

	private Manager manager;
	private List<String> designations;
	private List<String> categories;
	private ObservableList<String> obsCategories;

	private static final String TITLE_TEXT = "Add a Course";
	private static final String NUM_TEXT = "Course Number";
	private static final String NAME_TEXT = "Course Name";
	private static final String INSTR_TEXT = "Instructor";
	private static final String DESIG_TEXT = "Designation";
	private static final String CATEG_TEXT = "Category";
	private static final String ESTIM_TEXT = "Estimated #\nof students";
	private static final String ADD_CATEG = "Add a new category";
	private static final String REMOVE_CATEG = "Remove a category";
	private static final String FUNC_TEXT = "Back";
	private static final String CREATE_TEXT = "Submit";
	private static final String ERROR_NOT_INT = "Please enter an valid value for"
			+ " estimated number of students.";
	private static final String ERROR_EMPTY = "Please fill out all fields.";
	private static final String NUM_TAKEN = "Course number is already in use.";
	private static final String NAME_TAKEN = "Course name is already in use.";
	private static final String SUCCESS = "Course added successfully.";

	private static final int ITEM_SPACING = 10;

	public AddCoursePage(Manager manager, List<String> designations,
			List<String> categories) {
		this.manager = manager;
		this.designations = designations;
		this.categories = categories;
		obsCategories = FXCollections.observableArrayList(categories);
		initPage();
		initButtons();
		initDropdowns();
	}

	private void initPage() {
		title = new Text(TITLE_TEXT);
		numLabel = new Text(NUM_TEXT);
		nameLabel = new Text(NAME_TEXT);
		instrLabel = new Text(INSTR_TEXT);
		desigLabel = new Text(DESIG_TEXT);
		categLabel = new Text(CATEG_TEXT);
		estimLabel = new Text(ESTIM_TEXT);
		numField = new TextField();
		nameField = new TextField();
		instrField = new TextField();
		desigField = new ComboBox<String>();
		categBox = new VBox(ITEM_SPACING);
		estimField = new TextField();
		addButton = new Button(ADD_CATEG);
		removeButton = new Button(REMOVE_CATEG);
		funcButton = new Button(FUNC_TEXT);
		createButton = new Button(CREATE_TEXT);
		errorMessage = new Text();
		errorMessage.setVisible(false);
		root = new GridPane();
		root.add(title, 0, 0, 4, 1);
		root.add(numLabel, 0, 1);
		root.add(nameLabel, 0, 2);
		root.add(instrLabel, 0, 3);
		root.add(desigLabel, 0, 4);
		root.add(estimLabel, 0, 5);
		root.add(funcButton, 0, 6);
		root.add(numField, 1, 1);
		root.add(nameField, 1, 2);
		root.add(instrField, 1, 3);
		root.add(desigField, 1, 4);
		root.add(estimField, 1, 5);
		root.add(createButton, 1, 6);
		root.add(categLabel, 2, 1);
		root.add(addButton, 2, 2);
		root.add(removeButton, 2, 3);
		root.add(categBox, 3, 1, 1, 7);
		root.add(errorMessage, 0, 7, 4, 1);
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
				submitCourseParameters();
			}
		});
	}

	private void initDropdowns() {
		desigField.getItems().addAll(designations);
		desigField.setValue(designations.get(0));
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

	private void showErrorNumTaken() {
		errorMessage.setText(NUM_TAKEN);
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
	private void submitCourseParameters() {
		String num = numField.getText();
		String name = nameField.getText();
		String instr = instrField.getText();
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
		if (num.equals("") || name.equals("") || instr.equals("") || estim.equals("")) {
			showErrorEmpty();
			return;
		}
		switch (manager.addCourse(num, name, instr, desig, categList, numStudents)) {
			case 0:	
				showErrorNumTaken();
				break;
			case 1:
				showErrorNameTaken();
				break;
			case 2:
				showMessageSuccess();
				break;
		}
	}

	public Pane getRootPane() {
		return root;
	}
}
