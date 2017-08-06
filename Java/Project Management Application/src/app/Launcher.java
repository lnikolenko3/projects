package app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Launcher extends Application {

	private static final String APP_NAME = "SLS Application";
	
	private Manager manager;

	@Override
	public void start(Stage stage) {
		stage.setTitle(APP_NAME);
		Pane root = new StackPane();
		manager = new Manager(root);
		manager.initialize();
		stage.setScene(new Scene(root, 1000, 500));
		stage.show();
	}
	
	@Override
	public void stop() {
		manager.finish();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
