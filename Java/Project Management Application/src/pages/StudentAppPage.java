package pages;

import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import models.ProjectApp;
import app.Manager;

public class StudentAppPage implements Page {
	
	private Pane root;
	private Text title;
	private TableView<ProjectApp> appBox;
	private Button meButton;
	
	private Manager manager;
	private List<ProjectApp> apps;
	
	private static final String TITLE_TEXT = "My Application";
	private static final String ME_TEXT = "Back";
	private static final String DATE_COL = "Date";
	private static final String PROJECT_COL = "Project Name";
	private static final String STATUS_COL = "Status";
	
	private static final int ITEM_SPACING = 10;
	
	public StudentAppPage(Manager manager, List<ProjectApp> apps) {
		this.manager = manager;
		this.apps = apps;
		initPage();
		initButtons();
		initTable();
	}
	
	private void initPage() {
		title = new Text(TITLE_TEXT);
		appBox = new TableView<ProjectApp>();
		meButton = new Button(ME_TEXT);
		root = new VBox(ITEM_SPACING, title, appBox, meButton);
	}
	
	@SuppressWarnings("unchecked")
	private void initTable() {
		appBox.getItems().addAll(apps);
		TableColumn<ProjectApp, String> dateCol = new TableColumn<>(DATE_COL);
		dateCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getDate()));
		TableColumn<ProjectApp, String> projectCol = new TableColumn<>(PROJECT_COL);
		projectCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getProject()));
		TableColumn<ProjectApp, String> statusCol = new TableColumn<>(STATUS_COL);
		statusCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getStatus().toString()));
		appBox.getColumns().addAll(dateCol, projectCol, statusCol);
	}
	
	private void initButtons() {
		meButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				manager.openMePage();
			}
		});
	}
	
	public Pane getRootPane() {
		return root;
	}
}