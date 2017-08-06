package pages;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.List;

import app.Manager;
import enums.Status;
import models.AppView;

public class AdminAppPage implements Page {

	private Pane root;
	private Text title;
	private TableView<AppView> table;
	private Button funcButton;
	private Button acceptButton;
	private Button rejectButton;
	private Pane buttonBox;
	private ToggleGroup appToggle;

	private Manager manager;
	private List<AppView> apps;

	private static final String TITLE_TEXT = "Application";
	private static final String FUNC_TEXT = "Back";
	private static final String ACCEPT_TEXT = "Accept";
	private static final String REJECT_TEXT = "Reject";
	private static final String NAME_COL = "Project";
	private static final String MAJOR_COL = "Applicant Major";
	private static final String YEAR_COL = "Applicant Year";
	private static final String STATUS_COL = "Status";

	private static final int ITEM_SPACING = 10;

	public AdminAppPage(Manager manager, List<AppView> apps) {
		this.manager = manager;
		this.apps = apps;
		initPage();
		initButtons();
		initTable();
	}

	private void initPage() {
		title = new Text(TITLE_TEXT);
		table = new TableView<AppView>();
		funcButton = new Button(FUNC_TEXT);
		acceptButton = new Button(ACCEPT_TEXT);
		rejectButton = new Button(REJECT_TEXT);
		buttonBox = new HBox(ITEM_SPACING, funcButton, acceptButton, rejectButton);
		appToggle = new ToggleGroup();
		root = new VBox(ITEM_SPACING, title, table, buttonBox);
	}

	private void initButtons() {
		funcButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				manager.openFunctionPage();
			}
		});
		acceptButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				handleApplication(Status.ACCEPTED);
			}
		});
		rejectButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				handleApplication(Status.REJECTED);
			}
		});
	}

	@SuppressWarnings("unchecked")
	private void initTable() {
		table.getItems().addAll(apps);
		TableColumn<AppView, AppView> radioCol = new TableColumn<>("");
		radioCol.setCellFactory(c -> new RadioCell<>(appToggle));
		radioCol.setCellValueFactory(p -> new SimpleObjectProperty<AppView>(p.getValue()));
		radioCol.setSortable(false);
		TableColumn<AppView, String> nameCol = new TableColumn<>(NAME_COL);
		nameCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getProject()));
		nameCol.setSortable(false);
		TableColumn<AppView, String> majorCol = new TableColumn<>(MAJOR_COL);
		majorCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getMajor()));
		majorCol.setSortable(false);
		TableColumn<AppView, String> yearCol = new TableColumn<>(YEAR_COL);
		yearCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getYear().toString()));
		yearCol.setSortable(false);
		TableColumn<AppView, String> statusCol = new TableColumn<>(STATUS_COL);
		statusCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getStatus().toString()));
		statusCol.setSortable(false);
		table.getColumns().addAll(radioCol, nameCol, majorCol, yearCol, statusCol);
	}
	
	@SuppressWarnings("unchecked")
	private void handleApplication(Status status) {
		RadioButton r = (RadioButton) appToggle.getSelectedToggle();
		AppView a = (AppView) r.getUserData();
		if (manager.handleApplication(a.getProject(), a.getUser(), status)) {
			a.setStatus(status);
			((TableColumn<AppView, AppView>) table.getColumns().get(0)).setVisible(false);
			((TableColumn<AppView, AppView>) table.getColumns().get(0)).setVisible(true);
			((TableColumn<AppView, String>) table.getColumns().get(4)).setVisible(false);
			((TableColumn<AppView, String>) table.getColumns().get(4)).setVisible(true);
		}
	}

	public Pane getRootPane() {
		return root;
	}
	
	public static class RadioCell<S, T> extends TableCell<S, T> {
		
		private ToggleGroup toggle;
		
		public RadioCell(ToggleGroup toggle) {
			this.toggle = toggle;
		}
		
		@Override
		protected void updateItem(T item, boolean empty) {
			AppView result = (AppView) item;
			if (empty || result.getStatus() != Status.PENDING) {
				setGraphic(null);
			} else {
				RadioButton r = new RadioButton();
				r.setToggleGroup(toggle);
				r.setUserData(item);
				setGraphic(r);
			}
		}
	}
}
