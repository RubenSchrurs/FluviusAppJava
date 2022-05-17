package gui;

import javafx.application.Application;
import javafx.stage.Stage;

public class StartGUI extends Application
{	
	@Override
	public void start(Stage primaryStage) {
		LoginController login = new LoginController();
		login.Login(primaryStage);
	}
	
	public void run() {
		launch();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
