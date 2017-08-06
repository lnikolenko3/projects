package pages;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import java.util.List;

import app.Manager;
import models.PopularProject;

public class PopularPage implements Page {

	private Pane root;
	private Text title;
	private TableView<PopularProject> table;
	private Button funcButton;

	private Manager manager;
	private List<PopularProject> projects;

	private static final String TITLE_TEXT = "Popular Project";
	private static final String FUNC_TEXT = "Back";
	private static final String NAME_COL = "Project";
	private static final String APPS_COL = "#of Applicants";

	private static final int ITEM_SPACING = 10;

	public PopularPage(Manager manager, List<PopularProject> projects) {
		this.manager = manager;
		this.projects = projects;
		initPage();
		initButtons();
		initTable();
	}

	private void initPage() {
		title = new Text(TITLE_TEXT);
		table = new TableView<PopularProject>();
		funcButton = new Button(FUNC_TEXT);
		root = new VBox(ITEM_SPACING, title, table, funcButton);
	}

	private void initButtons() {
		funcButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				manager.openFunctionPage();
			}
		});
	}

	private void initTable() {
		table.getItems().addAll(projects);
		TableColumn<PopularProject, String> nameCol = new TableColumn<>(NAME_COL);
		nameCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getName()));
		nameCol.setSortable(false);
		TableColumn<PopularProject, Number> appsCol = new TableColumn<>(APPS_COL);
		appsCol.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getNumApps()));
		appsCol.setSortable(false);
		table.getColumns().add(nameCol);
		table.getColumns().add(appsCol);
	}

	public Pane getRootPane() {
		return root;
	}
}
