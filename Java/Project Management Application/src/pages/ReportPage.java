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
import models.Report;

public class ReportPage implements Page {

	private Pane root;
	private Text title;
	private Text statsText;
	private TableView<Report> table;
	private Button funcButton;

	private Manager manager;
	private int numApps;
	private int numAccepted;
	private List<Report> reports;

	private static final String TITLE_TEXT = "Application Report";
	private static final String FUNC_TEXT = "Back";
	private static final String STATS_TEXT = "%d applications in total, accepted %d applications";
	private static final String NAME_COL = "Project";
	private static final String APPS_COL = "#of Applicants";
	private static final String RATE_COL = "accept rate";
	private static final String MAJOR_COL = "top 3 major";

	private static final int ITEM_SPACING = 10;

	public ReportPage(Manager manager, int numApps, int numAccepted,
			List<Report> reports) {
		this.manager = manager;
		this.numApps = numApps;
		this.numAccepted = numAccepted;
		this.reports = reports;
		initPage();
		initButtons();
		initTable();
	}

	private void initPage() {
		title = new Text(TITLE_TEXT);
		statsText = new Text(String.format(STATS_TEXT, numApps, numAccepted));
		table = new TableView<Report>();
		funcButton = new Button(FUNC_TEXT);
		root = new VBox(ITEM_SPACING, title, statsText, table, funcButton);
	}

	private void initButtons() {
		funcButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				manager.openFunctionPage();
			}
		});
	}

	@SuppressWarnings("unchecked")
	private void initTable() {
		table.getItems().addAll(reports);
		TableColumn<Report, String> nameCol = new TableColumn<>(NAME_COL);
		nameCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getProject()));
		nameCol.setSortable(false);
		TableColumn<Report, Number> appsCol = new TableColumn<>(APPS_COL);
		appsCol.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getNumApps()));
		appsCol.setSortable(false);
		TableColumn<Report, String> rateCol = new TableColumn<>(RATE_COL);
		rateCol.setCellValueFactory(p -> new SimpleStringProperty(getRateString(p.getValue().getAcceptRate())));
		rateCol.setSortable(false);
		TableColumn<Report, String> majorCol = new TableColumn<>(MAJOR_COL);
		majorCol.setCellValueFactory(p -> new SimpleStringProperty(getMajorString(p.getValue().getTopMajors())));
		majorCol.setSortable(false);
		table.getColumns().addAll(nameCol, appsCol, rateCol, majorCol);
	}
	
	private static String getRateString(double rate) {
		return rate + "%";
	}
	
	private static String getMajorString(List<String> topMajors) {
		String result = "";
		if (topMajors.size() > 0) {
			for (String s : topMajors) {
				result += s + "/";
			}
			result = result.substring(0, result.length() - 1);
		}
		return result;
	}

	public Pane getRootPane() {
		return root;
	}
}
