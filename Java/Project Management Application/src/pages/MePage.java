package pages;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import app.Manager;

public class MePage implements Page {
	
	private Pane root;
	private Text title;
	private Button profileButton;
	private Button appButton;
	private Button mainButton;
	
	private Manager manager;
	
	private static final String TITLE_TEXT = "Me";
	private static final String PROFILE_TEXT = "Edit Profile";
	private static final String APP_TEXT = "My Application";
	private static final String MAIN_TEXT = "Back";
	
	private static final int ITEM_SPACING = 10;
	
	public MePage(Manager manager) {
		this.manager = manager;
		initPage();
		initButtons();
	}
	
	private void initPage() {
		title = new Text(TITLE_TEXT);
		profileButton = new Button(PROFILE_TEXT);
		appButton = new Button(APP_TEXT);
		mainButton = new Button(MAIN_TEXT);
		root = new VBox(ITEM_SPACING, title, profileButton, appButton, mainButton);
	}
	
	private void initButtons() {
		profileButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				manager.openProfilePage();
			}
		});
		appButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				manager.openStudentAppPage();
			}
		});
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
