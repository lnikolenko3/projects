package pages;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TableRow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import app.Manager;
import enums.Year;
import models.Searchable;

public class MainPage implements Page {
	
	private GridPane root;
	private Text title;
	private Text nameLabel;
	private TextField nameField;
	private Text designationLabel;
	private ComboBox<String> designationField;
	private Text majorLabel;
	private ComboBox<String> majorField;
	private Text yearLabel;
	private ComboBox<String> yearField;
	private Text categoryLabel;
	private Pane categoryBox;
	private Button meButton;
	private Button addCategoryButton;
	private Button removeCategoryButton;
	private Button searchButton;
	private Button resetButton;
	private ToggleGroup typeRadio;
	private Pane radioBox;
	private TableView<Searchable> table;
	
	private Manager manager;
	private List<String> majors;
	private List<String> designations;
	private List<String> categories;
	private List<Searchable> results;
	private ObservableList<String> obsCategories;
	
	private static final String TITLE_TEXT = "Main Page";
	private static final String ME_TEXT = "Me";
	private static final String NAME_TEXT = "Title";
	private static final String DESIGNATION_TEXT = "Designation";
	private static final String MAJOR_TEXT = "Major";
	private static final String YEAR_TEXT = "Year";
	private static final String CATEGORY_TEXT = "Category";
	private static final String ADD_CATEGORY = "Add a category";
	private static final String REMOVE_CATEGORY = "Remove a category";
	private static final String PROJECT_RADIO = "Project";
	private static final String COURSE_RADIO = "Course";
	private static final String BOTH_RADIO = "Both";
	private static final String SEARCH_TEXT = "Apply filter";
	private static final String RESET_TEXT = "Reset filter";
	private static final String DROPDOWN_DEFAULT = "Please Select";
	private static final String NAME_COL = "Name";
	private static final String TYPE_COL = "Type";
	private static final String PROJECT_TYPE = "Project";
	private static final String COURSE_TYPE = "Course";
	
	private static final int ITEM_SPACING = 10;
	
	public MainPage(Manager manager, List<String> majors, List<String> designations,
			List<String> categories, List<Searchable> results) {
		this.manager = manager;
		this.majors = majors;
		this.designations = designations;
		this.categories = categories;
		this.results = results;
		initPage();
		initButtons();
		initDropdowns();
		initTable();
	}
	
	private void initPage() {
		title = new Text(TITLE_TEXT);
		nameLabel = new Text(NAME_TEXT);
		nameField = new TextField();
		designationLabel = new Text(DESIGNATION_TEXT);
		designationField = new ComboBox<String>();
		majorLabel = new Text(MAJOR_TEXT);
		majorField = new ComboBox<String>();
		yearLabel = new Text(YEAR_TEXT);
		yearField = new ComboBox<String>();
		categoryLabel = new Text(CATEGORY_TEXT);
		categoryBox = new VBox(ITEM_SPACING, categoryLabel);
		meButton = new Button(ME_TEXT);
		typeRadio = new ToggleGroup();
		RadioButton projectRadioButton = new RadioButton(PROJECT_RADIO);
		projectRadioButton.setToggleGroup(typeRadio);
		RadioButton courseRadioButton = new RadioButton(COURSE_RADIO);
		courseRadioButton.setToggleGroup(typeRadio);
		RadioButton bothRadioButton = new RadioButton(BOTH_RADIO);
		bothRadioButton.setToggleGroup(typeRadio);
		bothRadioButton.setSelected(true);
		radioBox = new HBox(ITEM_SPACING, projectRadioButton, courseRadioButton, bothRadioButton);
		addCategoryButton = new Button(ADD_CATEGORY);
		removeCategoryButton = new Button(REMOVE_CATEGORY);
		searchButton = new Button(SEARCH_TEXT);
		resetButton = new Button(RESET_TEXT);
		table = new TableView<Searchable>();
		root = new GridPane();
		root.add(meButton, 0, 0);
		root.add(title, 1, 0, 3, 1);
		root.add(nameLabel, 0, 1);
		root.add(nameField, 1, 1);
		root.add(designationLabel, 0, 2);
		root.add(designationField, 1, 2);
		root.add(majorLabel, 0, 3);
		root.add(majorField, 1, 3);
		root.add(yearLabel, 0, 4);
		root.add(yearField, 1, 4);
		root.add(radioBox, 0, 5, 2, 1);
		root.add(searchButton, 0, 6);
		root.add(resetButton, 1, 6);
		root.add(categoryBox, 2, 1, 1, 7);
		root.add(addCategoryButton, 3, 1);
		root.add(removeCategoryButton, 3, 2);
		root.add(table, 0, 7, 2, 1);
	}
	
	private void initButtons() {
		addCategoryButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				addCategoryField();
			}
		});
		removeCategoryButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				removeCategoryField();
			}
		});
		meButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				manager.openMePage();
			}
		});
		searchButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				sendSearchParameters();
			}
		});
		resetButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				manager.openMainPage();
			}
		});
	}
	
	private void initDropdowns() {
		designationField.getItems().add(DROPDOWN_DEFAULT);
		designationField.getItems().addAll(designations);
		designationField.setValue(DROPDOWN_DEFAULT);
		majorField.getItems().add(DROPDOWN_DEFAULT);
		majorField.getItems().addAll(majors);
		majorField.setValue(DROPDOWN_DEFAULT);
		yearField.getItems().add(DROPDOWN_DEFAULT);
		for (Year year : Year.values()) {
			yearField.getItems().add(year.toString());
		}
		yearField.setValue(DROPDOWN_DEFAULT);
		obsCategories = FXCollections.observableArrayList(categories);
		obsCategories.add(0, DROPDOWN_DEFAULT);
		addCategoryField();
	}
	
	@SuppressWarnings("unchecked")
	private void initTable() {
		setSearchResults(results);
		TableColumn<Searchable, String> nameCol = new TableColumn<>(NAME_COL);
		nameCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getName()));
		nameCol.setSortable(false);
		TableColumn<Searchable, String> typeCol = new TableColumn<>(TYPE_COL);
		typeCol.setCellValueFactory(p -> new SimpleStringProperty(
				p.getValue().isProject() ? PROJECT_TYPE : COURSE_TYPE));
		typeCol.setSortable(false);
		table.getColumns().addAll(nameCol, typeCol);
		table.setRowFactory(tv -> {
			TableRow<Searchable> row = new TableRow<>();
		    row.setOnMouseClicked(event -> {
		        if (!row.isEmpty()) {
		            Searchable rowData = row.getItem();
		            if (rowData.isProject()) {
		            	manager.openProjectPage(rowData.getName());
		            } else {
		            	manager.openCoursePage(rowData.getName());
		            }
		        }
		    });
		    return row;
		});
	}
	
	private void addCategoryField() {
		if (categoryBox.getChildren().size() <= categories.size()) {
			ComboBox<String> field = new ComboBox<String>(obsCategories);
			categoryBox.getChildren().add(field);
			field.setValue(DROPDOWN_DEFAULT);
		}
	}
	
	private void removeCategoryField() {
		if (categoryBox.getChildren().size() > 2) {
			categoryBox.getChildren().remove(categoryBox.getChildren().size() - 1);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void sendSearchParameters() {
		String name = nameField.getText().equals("") ? null : nameField.getText();
		String designation = designationField.getValue().equals(DROPDOWN_DEFAULT) ?
				null : designationField.getValue();
		String major = majorField.getValue().equals(DROPDOWN_DEFAULT) ?
				null : majorField.getValue();
		Year year = yearField.getValue().equals(DROPDOWN_DEFAULT) ?
				null : Year.getYear(yearField.getValue());
		Set<String> categorySet = new HashSet<>();
		for (int i = 1; i < categoryBox.getChildren().size(); i += 1) {
			String category = ((ComboBox<String>) categoryBox.getChildren().get(i)).getValue();
			if (!category.equals(DROPDOWN_DEFAULT)) {
				categorySet.add(category);
			}
		}
		List<String> categoryList = new ArrayList<>(categorySet);
		int type = 0;
		String radioType = ((RadioButton) typeRadio.getSelectedToggle()).getText();
		if (radioType.equals(BOTH_RADIO)) {
			type = 0;
		} else if (radioType.equals(PROJECT_RADIO)) {
			type = 1;
		} else if (radioType.equals(COURSE_RADIO)) {
			type = 2;
		}
		manager.searchMain(this, name, designation, categoryList, major, year, type);
	}

	public void setSearchResults(List<Searchable> results) {
		table.getItems().clear();
		table.getItems().addAll(results);
	}

	public Pane getRootPane() {
		return root;
	}
}
