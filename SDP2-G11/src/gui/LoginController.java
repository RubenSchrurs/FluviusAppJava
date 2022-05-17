package gui;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import domein.AanmeldController;
import domein.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Window;

public class LoginController {
	@FXML
	private Label lblLogin;
	@FXML
	private Label lblStatus;
	@FXML
	private TextField txtGebruikersnaam;
	@FXML
	private TextField txtWachtwoord;
	@FXML
	private Button btnLogin;
	@FXML
	private Button btnCancel;
	
	private final AanmeldController aanmeldcontroller = new AanmeldController();
	
	public void Login(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/gui/Login.fxml"));
			Scene scene = new Scene(root, 520, 420);
			scene.getStylesheets().add(getClass().getResource("gui.css").toExternalForm());
			Image icon = new Image(getClass().getResourceAsStream("images/icon.png"));
			primaryStage.getIcons().add(icon);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setMaximized(false);
			primaryStage.centerOnScreen();
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void CancelLogin(ActionEvent event) throws Exception{
		txtGebruikersnaam.clear();
		txtWachtwoord.clear();
	}
	
	public void LoginToMain(ActionEvent event) throws Exception{
		//Checken als gebruikersnaam en wachtwoord correct is
		String naam = txtGebruikersnaam.getText();
		String pass = txtWachtwoord.getText();
		User aangemeldeUser = null;
		try {
			aangemeldeUser = aanmeldcontroller.meldAan(naam, pass);
		}
		catch (IllegalStateException e) {
			Alert alertPermission = new Alert(AlertType.ERROR);
			alertPermission.setTitle("Account Blocked");
			alertPermission.setContentText("U kan momenteel niet meer inloggen.");
			alertPermission.show();
			return;
		}
		
		//Variabelen voor de logging
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now(); 
		FileWriter writer = new FileWriter("Logging.txt",true);

		
		if (aangemeldeUser != null) {
			//Logging
			try {
				writer.write(dtf.format(now) + " -> " + aangemeldeUser.getUsername() + "\t(" + aangemeldeUser.getRoles() + ")\tStatus: Succesvol ingelogd"+ "\n");
				writer.close();
			}
			catch (IOException e) {
			      System.err.println("An error occurred.");
			      e.printStackTrace();
			}
			if (aangemeldeUser.getRoles() == "MVO Co\u00f6rdinator") {
				lblStatus.setText("Succes");
				//Maak nieuwe Scene voor main scherm
				MainController mainController = new MainController();
				mainController.MainScreen(event);
				//Sluit login Scene
				Window stage1 = btnLogin.getScene().getWindow();
				stage1.hide();
			}
			else {
				//Verkeerde role
				Alert alertPermission = new Alert(AlertType.ERROR);
				alertPermission.setTitle("No Permission!");
				alertPermission.setContentText("U heeft geen toestemming tot deze applicatie");
				alertPermission.show();
			}		
		}
		else {
			//Niet kunnen aanmelden met combinatie username/ww
			if(aanmeldcontroller.getPoging(naam) > 0) {
				//Logging
				try {
					writer.write(dtf.format(now) + " -> " + naam  + "\tStatus: Login failed" + "\tResterende pogingen: " + aanmeldcontroller.getPoging(naam) + "\n");
					writer.close();
				}
				catch (IOException e) {
				      System.err.println("An error occurred.");
				      e.printStackTrace();
				}
				//Alert
				Alert alertFailed = new Alert(AlertType.WARNING);
				alertFailed.setTitle("Login failed");
				alertFailed.setContentText("Login and password did not match. \nPlease try again \n\nResterende pogingen: " + aanmeldcontroller.getPoging(naam));
				alertFailed.show();
				lblStatus.setText("Login Failed");
			}
			else {
				// >3 pogingen -> account blokkeren 
				Alert alertPermission = new Alert(AlertType.ERROR);
				alertPermission.setTitle("Account Blocked");
				alertPermission.setContentText("U kan momenteel niet meer inloggen.");
				alertPermission.show();
				
			}
			
		}
	}
}