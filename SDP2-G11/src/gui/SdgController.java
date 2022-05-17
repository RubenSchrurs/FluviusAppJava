package gui;

import java.util.Comparator;

import domein.SDGBeheer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.TextFlow;


public class SdgController {
	//Menu controls
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
	private Button show;
	@FXML
	private ImageView imgLogo;
	@FXML
	private Label txtDisplayCurrentTab;
	@FXML
	private TextFlow txtCurrentUser;
	@FXML
	private Button btnAddCategorie;
	@FXML
	private Button btnDeleteCategorie;
	@FXML
	private Button btnLogout;
	@FXML
	private ListView<String> allSdgView;
	@FXML
	private ListView<String> subSdgView;
	@FXML
	private Label lblBeschrijving;
	
	private SDGBeheer sdgBeheer = new SDGBeheer();
	
	public void initialize() 
	{
        allSdgView.setItems(sdgBeheer.getSDGs().sorted());
        sdgBeheer.addObserver(e
                -> btnDeleteCategorie.setDisable(sdgBeheer.noSDGs()));
        
        allSdgView.setOnMouseClicked(SubSDGHandler);
    }
	EventHandler<MouseEvent> SubSDGHandler = new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent arg0) {
			lblBeschrijving.setText("");
	    	String mainSdg = allSdgView.getSelectionModel().getSelectedItem().toString();
	        subSdgView.setItems(sdgBeheer.getSubSdgsByMainSdg(mainSdg).sorted(new Comparator<String>() {
				
				@Override
				public int compare(String o1, String o2) {             
			        if (o1.length()!=o2.length()) {
			            return o1.length()-o2.length(); 
			        }
			        return o1.compareTo(o2);
			    }
			}));
	        subSdgView.setOnMouseClicked(beschrijvingenHandler);
		}};
	
	
	EventHandler<MouseEvent> beschrijvingenHandler = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent click) {
			String mainSdg = allSdgView.getSelectionModel().getSelectedItem().toString();
			lblBeschrijving.setText(sdgBeheer.getBeschrijving(mainSdg,subSdgView.getSelectionModel().getSelectedItem().toString()).get(0));
        	lblBeschrijving.setWrapText(true);
		}
	};
	
	public void Logout(ActionEvent event) throws Exception {
		MainController mainController = new MainController();
        mainController.Logout(event);
	}
	
	//switch naar andere scenes
	public void SwitchToDashboard(ActionEvent event) throws Exception{
		MainController mainController = new MainController();
		mainController.SwitchToDashboard(event);
	}
	public void SwitchToDoelstelling(ActionEvent event) throws Exception{
		MainController mainController = new MainController();
		mainController.SwitchToDoelstelling(event);
	}
	public void SwitchToDatasource(ActionEvent event) throws Exception{
		MainController mainController = new MainController();
		mainController.SwitchToDatasource(event);
	}
	public void SwitchToCategorie(ActionEvent event) throws Exception{
		MainController mainController = new MainController();
		mainController.SwitchToCategorie(event);
	}
	
	
}
