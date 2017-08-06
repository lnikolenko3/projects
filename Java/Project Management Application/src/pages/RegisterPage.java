package pages;

import app.Manager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class RegisterPage implements Page {
	
	private Pane root;
	private Text title;
	private TextField userField;
	private TextField emailField;
	private TextField passField;
	private TextField confirmField;
	private Button registerButton;
	private Text errorText;
	
	private Manager manager;
	
	private static final String TITLE_TEXT = "New Student Registration";
	private static final String USER_TEXT = "Username";
	private static final String EMAIL_TEXT = "GT Email Address";
	private static final String PASS_TEXT = "Password";
	private static final String CONFIRM_TEXT = "Confirm Password";
	private static final String REGISTER_TEXT = "Create";
	
	private static final String ERROR_TEXT_EMPTY = "Please fill out all fields.";
	private static final String ERROR_TEXT_CONFIRM = "Passwords do not match. Please try again.";
	private static final String ERROR_TEXT_USER = "Username is already taken. Please try again.";
	private static final String ERROR_TEXT_EMAIL = "Email is already registered. Please try again.";
	
	private static final int ITEM_SPACING = 10;
	
	public RegisterPage(Manager manager) {
		this.manager = manager;
		initPage();
		initButtons();
	}
	
	private void initPage() {
		title = new Text(TITLE_TEXT);
		userField = new TextField();
		userField.setPromptText(USER_TEXT);
		emailField = new TextField();
		emailField.setPromptText(EMAIL_TEXT);
		passField = new PasswordField();
		passField.setPromptText(PASS_TEXT);
		confirmField = new PasswordField();
		confirmField.setPromptText(CONFIRM_TEXT);
		registerButton = new Button(REGISTER_TEXT);
		errorText = new Text();
		errorText.setVisible(false);
		root = new VBox(ITEM_SPACING, title, userField, emailField,
				passField, confirmField, registerButton, errorText);
	}
	
	private void initButtons() {
		registerButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				String username = userField.getText();
				String email = emailField.getText();
				String password = passField.getText();
				String confirm = confirmField.getText();
				if (username.equals("") || email.equals("")
						|| password.equals("") || confirm.equals("")) {
					showErrorMessageEmpty();
					return;
				}
				if (!password.equals(confirm)) {
					showErrorMessageConfirm();
					return;
				}
				manager.register(RegisterPage.this, username, email, password);
			}
		});
	}
	
	private void showErrorMessageEmpty() {
		errorText.setVisible(true);
		errorText.setText(ERROR_TEXT_EMPTY);
	}
	
	
	private void showErrorMessageConfirm() {
		errorText.setVisible(true);
		errorText.setText(ERROR_TEXT_CONFIRM);
	}
	
	public void showErrorMessageUser() {
		errorText.setVisible(true);
		errorText.setText(ERROR_TEXT_USER);
	}
	
	public void showErrorMessageEmail() {
		errorText.setVisible(true);
		errorText.setText(ERROR_TEXT_EMAIL);
	}
	
	public Pane getRootPane() {
		return root;
	}
}
