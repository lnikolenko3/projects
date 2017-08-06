package pages;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import app.Manager;

public class LoginPage implements Page {
	
	private Pane root;
	private Text title;
	private TextField userField;
	private TextField passField;
	private Pane buttonBox;
	private Button loginButton;
	private Button registerButton;
	private Text errorText;
	
	private Manager manager;
	
	private static final String USER_TEXT = "Username";
	private static final String PASS_TEXT = "Password";
	private static final String LOGIN_TEXT = "Login";
	private static final String REGISTER_TEXT = "Register";
	private static final String ERROR_INVALID = "Invalid username or password. Please try again.";
	private static final String ERROR_EMPTY = "Please fill out all fields.";
	
	private static final int BUTTON_SPACING = 10;
	private static final int ITEM_SPACING = 10;
	
	public LoginPage(Manager manager) {
		this.manager = manager;
		initPage();
		initButtons();
	}
	
	private void initPage() {
		title = new Text(LOGIN_TEXT);
		userField = new TextField();
		userField.setPromptText(USER_TEXT);
		passField = new PasswordField();
		passField.setPromptText(PASS_TEXT);
		loginButton = new Button(LOGIN_TEXT);
		registerButton = new Button(REGISTER_TEXT);
		buttonBox = new HBox(BUTTON_SPACING, loginButton, registerButton);
		errorText = new Text();
		errorText.setVisible(false);
		root = new VBox(ITEM_SPACING, title, userField, passField, buttonBox, errorText);
	}
	
	private void initButtons() {
		loginButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				String username = userField.getText();
				String password = passField.getText();
				if (username.equals("") || password.equals("")) {
					showErrorEmpty();
				} else {
					manager.login(LoginPage.this, username, password);
				}
			}
		});
		registerButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				manager.openRegisterPage();
			}
		});
	}
	
	public void showErrorInvalid() {
		errorText.setText(ERROR_INVALID);
		errorText.setVisible(true);
	}
	
	private void showErrorEmpty() {
		errorText.setText(ERROR_EMPTY);
		errorText.setVisible(true);
	}
	
	public Pane getRootPane() {
		return root;
	}
}
