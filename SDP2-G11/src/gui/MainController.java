package gui;

import java.net.URL;
import java.util.ResourceBundle;

import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class MainController implements Initializable
{
	@FXML
	private Button btnDashboard;
	@FXML
	private Button btnCategorie;
	@FXML
	private Button btnDoelstelling;
	@FXML
	private Button btnDatasource;
	@FXML
	private Button btnSdg;
	@FXML
	private ImageView imgLogo;
	@FXML
	private Label txtDisplayCurrentTab;
	@FXML
	private TextFlow txtCurrentUser;
	@FXML
	private Button btnLogout;
	//Dashboard
	@FXML
	private Button btnCategorieDisplay;
	@FXML
	private Button btnDatasourceDisplay;
	@FXML
	private Button btnDoelstellingDisplay;
	@FXML
	private Button btnSDGDisplay;
	@FXML
	private Label lblInfoCategorieen;
	@FXML
	private Label lblInfoDoelstellingen;
	@FXML
	private Label lblInfoDatasources;
	@FXML
	private Label lblInfoSDGs;
	
	private DomeinController dc;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		dc = new DomeinController();
		lblInfoCategorieen.setText("Aantal categorie\u00ebn:  " + dc.geefCategorieNamen().size());
		lblInfoDoelstellingen.setText("Aantal doelstellingen:  " + dc.geefDoelstellingNamen().size());
		lblInfoDatasources.setText("Aantal datasources:  " + dc.geefDatasourceNamen().size());
		lblInfoSDGs.setText("Aantal SDG's:  " + dc.geefSDGNamen().size());
	}
	
	public void MainScreen(ActionEvent event) throws Exception 
	{
		try {
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/gui/Main.fxml"));
			Scene scene = new Scene(root, 1920, 1080);
			scene.getStylesheets().add(getClass().getResource("gui.css").toExternalForm());
			Image icon = new Image(getClass().getResourceAsStream("images/icon.png"));
			primaryStage.getIcons().add(icon);
			primaryStage.setScene(scene);
			primaryStage.setMaximized(true);
			primaryStage.show();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void SwitchToDashboard(ActionEvent event) throws Exception
	{
		Parent root = FXMLLoader.load(getClass().getResource("/gui/Main.fxml"));
	    ((Node) event.getSource()).getScene().setRoot(root);
	}
	
	public void SwitchToCategorie(ActionEvent event) throws Exception
	{	
		Parent root = FXMLLoader.load(getClass().getResource("/gui/Categorie.fxml"));
	    ((Node) event.getSource()).getScene().setRoot(root);
	}
	
	public void SwitchToDoelstelling(ActionEvent event) throws Exception
	{
		Parent root = FXMLLoader.load(getClass().getResource("/gui/Doelstelling.fxml"));
	    ((Node) event.getSource()).getScene().setRoot(root);
	}
	
	public void SwitchToDatasource(ActionEvent event) throws Exception
	{
		Parent root = FXMLLoader.load(getClass().getResource("/gui/Datasource.fxml"));
	    ((Node) event.getSource()).getScene().setRoot(root);
	}
	
	public void SwitchToSdg(ActionEvent event) throws Exception
	{
		Parent root = FXMLLoader.load(getClass().getResource("/gui/Sdg.fxml"));
	    ((Node) event.getSource()).getScene().setRoot(root);
	}
	
	public void Logout(ActionEvent event) throws Exception 
	{
		LoginController login = new LoginController();
		Stage newStage = (Stage)((Node)event.getSource()).getScene().getWindow();
		newStage.setMaximized(false);
		newStage.setResizable(false);
		login.Login(newStage);
	}
	
}