package gui;

import java.util.List;
import java.util.stream.Collectors;

import domein.ADoelstelling;
import domein.DomeinController;
import domein.IDatasource;
import domein.IDoelstelling;
import domein.ISDG;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.TextFlow;

public class DoelstellingController 
{
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
	private ImageView imgLogo;
	@FXML
	private Label txtDisplayCurrentTab;
	@FXML
	private TextFlow txtCurrentUser;
	@FXML
	private Button btnLogout;
	
	//DoelstellingScherm controls listView
	@FXML
	private Label lblNaam;
	@FXML
	private TextField txtNaam;
	@FXML
	private Label lblSDG;
	@FXML
	private TextField txtSDG;
	@FXML
	private TextField txtDrempelwaarde;
	@FXML
	private Label lblDrempelwaarde;
	@FXML
	private TextField txtSubSDG;
	@FXML
	private Button btnTerug;
	@FXML
	private Button btnAddDoelstelling;
	@FXML
	private Button btnEditDoelstelling;
	@FXML
	private Button btnRemoveDoelstelling;
	@FXML
	private ListView<String> listDoelstellingen;
	@FXML
	private ListView<String> subDoelstelling;
	@FXML
	private ListView<String> listSDG;
	
	
	//DoelstellingScherm controls of selected doelstelling
	@FXML
	private Label lblSelectedDoelstelling;
	@FXML
	private ComboBox<String> eenheidMenu;
	@FXML
	private ComboBox<String> mainDoelstelling;
	@FXML
	private Button removeSDG;
	@FXML
	private ListView<String> alleDatasources;
	@FXML
	private ListView<String> mvoDatasources;
	
	private ObservableList<String> eenheden = FXCollections.observableArrayList("kWh","km/h","J","Ton");
	
	private DomeinController dc;
	
	public void initialize() {
		dc = new DomeinController();
		ObservableList<String> listDatasources = dc.getDatasources().stream().map(s -> s.getName())
				.collect(Collectors.toCollection(FXCollections::observableArrayList));
        //ListView opvullen met elementen van ObservableList
		ObservableList<String> list = dc.geefMainDoelstellingen().stream().map(s -> s.getName())
										.collect(Collectors.toCollection(FXCollections::observableArrayList));
		ObservableList<String> listAlleDoelstellingen = dc.geefAlleDoelstellingen().stream().map(s -> s.getName())
				.collect(Collectors.toCollection(FXCollections::observableArrayList));
		
		mainDoelstelling.getItems().setAll(listAlleDoelstellingen);
		alleDatasources.getItems().setAll(listDatasources);
		listDoelstellingen.getItems().setAll(list);
		listSDG.setItems(dc.geefMainSDG().stream().map(s -> s.getName())
										.collect(Collectors.toCollection(FXCollections::observableArrayList)));
		eenheidMenu.getItems().addAll(eenheden);
		
		//Handler om details van maindoelstelling te tonen of om bijhorende subdoelstellingen te tonen
		listDoelstellingen.setOnMouseClicked(handleMaindoelDetails);
		
		//Handler om (Sub-)SDG's toe te voegen
		listSDG.setOnMouseClicked(handleSDGDetails);
		
		//Handler om details subdoelstelling te tonen of om verder te gaan id hiërarchie van subdoelstellingen
		subDoelstelling.setOnMouseClicked(handleSubdoelDetails);
		
		alleDatasources.setOnMouseClicked(handleDatasourceToDoelstelling);
		
		mvoDatasources.setOnMouseClicked(handleDoelstellingDatasource);
		
		mainDoelstelling.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
			txtSDG.clear();
			txtSubSDG.clear();
			IDoelstelling doel = dc.getDoelstellingByName(newValue);
			ISDG sdg = dc.getSDGByName(doel.getMainSDG().getName());
			listSDG.setItems(dc.geefSubSDGsByNameMainSDG(sdg.getName()).stream().map(s -> s.getName())
									.collect(Collectors.toCollection(FXCollections::observableArrayList)));
		}); 
//    	listDoelstellingen.setOnMouseClicked((EventHandler<? super MouseEvent>) new EventHandler<MouseEvent>() {
//
//		    @Override
//		    public void handle(MouseEvent click) {
//		    	if(click.getClickCount() == 2) {
//		    		String naam = listDoelstellingen.getSelectionModel().getSelectedItem().toString();
//		    		ObservableList<String> nList = FXCollections.observableArrayList(dc.geefSDGByDoelstellingNaam(naam));
//		    		sdgDoelstelling.setItems(nList);
//		    	}
//		}});
	}
	
	EventHandler<MouseEvent> handleMaindoelDetails = new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent click) {
			if(click.getClickCount() == 2) {
	    		String naam = listDoelstellingen.getSelectionModel().getSelectedItem().toString();
	    		ObservableList<String> nList = dc.geefSubDoelstellingNamenVanMain(naam).stream().map(s -> s.getName())
													.collect(Collectors.toCollection(FXCollections::observableArrayList));
	    		subDoelstelling.setItems(nList);
	    	}
	    	
	    	ADoelstelling doel = dc.getDoelstellingByName(listDoelstellingen.getSelectionModel().getSelectedItem());
	    	if(click.getClickCount() == 1) {
	    		txtNaam.setText(doel.getName());
		    	txtSDG.setText(doel.getMainSDG().getName());
		    	txtDrempelwaarde.setText(String.format("%.2f", doel.getThreshold()));
		    	subDoelstelling.getItems().clear();
		    	if(doel.getSubSDG() != null) {
		    		txtSubSDG.setText(doel.getSubSDG().getName());
		    	}
		    	//txtSubSDG.setText(doel.getSubSDG().getName());
		    	System.out.println(doel.getSubSDG());
	    	}
	    	showDatasources(doel);
		}	
	};
	
	EventHandler<MouseEvent> handleSubdoelDetails = new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent click) {
			listDoelstellingen.getSelectionModel().clearSelection();
			IDoelstelling d = dc.getDoelstellingByName(subDoelstelling.getSelectionModel().getSelectedItem());
	    	ADoelstelling doel = (ADoelstelling) d;
	    	if(click.getClickCount() == 1) {
		    	txtNaam.setText(doel.getName());
		    	txtSDG.setText(doel.getMainSDG().getName());
		    	txtDrempelwaarde.setText(String.format("%.2f", doel.getThreshold()));
	    	}
	    	
	    	else if(click.getClickCount() == 2 && !doel.getSubDoelstelling().isEmpty()) {
	    		ObservableList<String> nList = dc.geefSubDoelstellingNamenVanMain(doel.getName()).stream().map(s -> s.getName())
													.collect(Collectors.toCollection(FXCollections::observableArrayList));
	    		subDoelstelling.setItems(nList);
	    	}
	    	showDatasources(doel);
		}
	};
	
	EventHandler<MouseEvent> handleSDGDetails = new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent click) {
			ISDG sdg = dc.getSDGByName(listSDG.getSelectionModel().getSelectedItem());
			System.out.println(mainDoelstelling.getSelectionModel().getSelectedItem());
	    	if(sdg.getName().contains("Target") && mainDoelstelling.getSelectionModel().getSelectedItem() == null) {
	    		txtSubSDG.setText(sdg.getName());
	    	}
	    	else {
	    		txtSDG.setText(sdg.getName());
		    	listSDG.setItems(dc.geefSubSDGsByNameMainSDG(sdg.getName()).stream().map(s -> s.getName())
									.collect(Collectors.toCollection(FXCollections::observableArrayList)));
	    	}
		}
		
	};
	EventHandler<MouseEvent> handleDatasourceToDoelstelling = new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent click) {
			IDatasource data = dc.getDatasourceByName(alleDatasources.getSelectionModel().getSelectedItem());
	    	if(click.getClickCount() == 1) {
	    		mvoDatasources.getItems().add(data.getName());
	    	}
		}	
	};
	EventHandler<MouseEvent> handleDoelstellingDatasource = new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent click) {
			IDatasource data = dc.getDatasourceByName(alleDatasources.getSelectionModel().getSelectedItem());
	    	if(click.getClickCount() == 1) {
	    		mvoDatasources.getItems().remove(mvoDatasources.getSelectionModel().getSelectedItem());
	    	}
		}	
	};
	
	public void showDatasources(ADoelstelling d) {
        try {
            List<IDatasource> datasources = d.getDatasources();
            ObservableList<String> nList = FXCollections.observableArrayList(
                                                datasources.stream().map(IData -> IData.getName()).collect(Collectors.toList())
                                                );
            mvoDatasources.setItems(nList);
            System.out.println(nList);
            datasources.stream().forEach(IData -> {
                List<String> eenhedenCopy = eenheden.stream().map(e -> e.toLowerCase()).collect(Collectors.toList());
                if(!eenhedenCopy.contains(IData.getEenheidData().toLowerCase())) {
                    addEenheid(IData.getEenheidData());
                }
            });
        }
        catch(UnsupportedOperationException e) {
        	mvoDatasources.getItems().clear();
        }
    }
	
	public void removeSDG(ActionEvent event) {
		listSDG.setItems(dc.geefMainSDG().stream().map(s -> s.getName())
							.collect(Collectors.toCollection(FXCollections::observableArrayList)));
		txtSDG.clear();
		txtSubSDG.clear();
	}
	
	public void Logout(ActionEvent event) throws Exception {
		MainController mainController = new MainController();
        mainController.Logout(event);
	}
	
	public void terug(ActionEvent event) {
		listDoelstellingen.setItems(dc.geefMainDoelstellingen().stream().map(s -> s.getName())
										.collect(Collectors.toCollection(FXCollections::observableArrayList)));
		subDoelstelling.getItems().clear();
		txtNaam.setText("");
    	txtSDG.setText("");
    	txtDrempelwaarde.setText("");
	}
	
	public void addEenheid(String s) {
		eenheden.add(s);
		eenheidMenu.setItems(eenheden);
	}
	
	public void removeEenheid(String s) {
		eenheden.remove(s);
		eenheidMenu.setItems(eenheden);
	}
	
	public void SwitchToDashboard(ActionEvent event) throws Exception
	{
		MainController mainController = new MainController();
		mainController.SwitchToDashboard(event);
	}
	
	public void SwitchToCategorie(ActionEvent event) throws Exception
	{
		MainController mainController = new MainController();
		mainController.SwitchToCategorie(event);
	}
	
	public void SwitchToDoelstelling(ActionEvent event) throws Exception
	{
		MainController mainController = new MainController();
		mainController.SwitchToDoelstelling(event);
	}
	
	public void SwitchToDatasource(ActionEvent event) throws Exception
	{
		MainController mainController = new MainController();
		mainController.SwitchToDatasource(event);
	}
	
	public void SwitchToSdg(ActionEvent event) throws Exception
	{
		MainController mainController = new MainController();
		mainController.SwitchToSdg(event);
	}
	
	@FXML
	public void addDoelstelling(ActionEvent event) 
	{
		String naam;
		String sdg;
		double drempelwaarde;
		String subSDG;
		String mainD;
		List<String> datasources;
		
		try {
			naam = txtNaam.getText();
			sdg = txtSDG.getText();
			drempelwaarde = txtDrempelwaarde.getText().isBlank() ? 0 : Double.parseDouble(txtDrempelwaarde.getText().replaceAll(",","\\."));
			subSDG = txtSubSDG.getText();
			mainD = mainDoelstelling.getValue();
			datasources = mvoDatasources.getItems();
			System.out.println(mainD);
			System.out.println(datasources);
			
			if(naam.isEmpty() || sdg.isEmpty()) {
				throw new IllegalArgumentException();
			}
		}
		catch(Exception e) {
			Alert alert = new Alert(AlertType.ERROR, "Niet alle velden zijn correct ingevuld");
			alert.setTitle("Invalid data");
			alert.showAndWait();
			return;
		}
		if(txtSubSDG.getText().isBlank()) {
			System.out.println("Er is geen sub SDG!");
			dc.addDoelstelling(naam, drempelwaarde, sdg, null, mainD, datasources);
		}
		else {
			System.out.printf("Dit is de sub SDG: %s", subSDG);
			dc.addDoelstelling(naam, drempelwaarde, sdg, subSDG, mainD, datasources);
		}
		if (listDoelstellingen.getItems().contains(naam) || subDoelstelling.getItems().contains(naam)) {
			Alert alert = new Alert(AlertType.ERROR, "Deze naam is al in gebruik");
			alert.setTitle("Invalid data");
			alert.showAndWait();
		}
		
		txtNaam.clear();
		txtSDG.clear();
		txtDrempelwaarde.clear();
		listSDG.getSelectionModel().clearSelection();
		txtSubSDG.clear();
		mvoDatasources.getItems().clear();
		initialize();
		
		Alert alertSucces = new Alert(AlertType.INFORMATION, "Doelstelling succesvol toegevoegd");
		alertSucces.setTitle("Succes");
		alertSucces.show();
	}
	
	boolean aanwezig = false;
	@FXML
	public void removeDoelstelling(ActionEvent event) 	
	{
		String selected = listDoelstellingen.getSelectionModel().getSelectedItem();
		String selected2 = subDoelstelling.getSelectionModel().getSelectedItem();
		
		dc.getCategories().forEach(cat -> {
			if(cat.getDoelstellingen().stream().map(ADoelstelling::getName).collect(Collectors.toList()).contains(selected) || cat.getDoelstellingen().stream().map(ADoelstelling::getName).collect(Collectors.toList()).contains(selected2)) {
				aanwezig = true;
			}
		});
		System.out.println(aanwezig);
		if(selected != null && !aanwezig) {
			Alert alert = new Alert(AlertType.CONFIRMATION, "Delete " + selected + " ?", ButtonType.YES,
					ButtonType.NO);
			alert.showAndWait();

			if (alert.getResult() == ButtonType.YES) {
				listDoelstellingen.getSelectionModel().clearSelection();
				dc.removeDoelstelling(selected);
				txtNaam.setText("");
		    	txtSDG.setText("");
		    	txtDrempelwaarde.setText("");
		    	txtSubSDG.setText("");
		    	mvoDatasources.getItems().clear();
		    	initialize();
			}
		}
		else if(selected2 != null && !aanwezig){
			Alert alert = new Alert(AlertType.CONFIRMATION, "Delete " + selected2 + " ?", ButtonType.YES,
					ButtonType.NO);
			alert.showAndWait();

			if (alert.getResult() == ButtonType.YES) {
				subDoelstelling.getSelectionModel().clearSelection();
				dc.removeDoelstelling(selected2);
				txtNaam.setText("");
		    	txtSDG.setText("");
		    	txtDrempelwaarde.setText("");
		    	txtSubSDG.setText("");
		    	initialize();
			}
		}
		else if(selected == null && selected2 == null) {
			Alert alertNoRemove = new Alert(AlertType.ERROR, "Er is geen doelstelling aangewezen.");
			alertNoRemove.setTitle("Niet succesvol");
			alertNoRemove.show();
		}
		else {
			Alert alertNoRemove = new Alert(AlertType.ERROR, "Deze doelstelling is nog verbonden met een categorie en kan dus niet verwijderd worden.");
			alertNoRemove.setTitle("Niet succesvol");
			alertNoRemove.show();
		}
		aanwezig = false;
		
	}
	
	@FXML
	public void editDoelstelling(ActionEvent event) {
		IDoelstelling doel = dc.getDoelstellingByName(listDoelstellingen.getSelectionModel().getSelectedItem());
		String oldName = doel.getName();
		String newName = txtNaam.getText();
		String sdg = txtSDG.getText();
		double drempelwaarde = txtDrempelwaarde.getText().isBlank() ? 0 : Double.parseDouble(txtDrempelwaarde.getText().replaceAll(",","\\."));
		System.out.println(txtDrempelwaarde.getText());
		
		dc.editDoelstelling(oldName, newName, sdg, drempelwaarde);
		dc.updateDoelstelling(newName);
		
		txtNaam.clear();
		txtSDG.clear();
		txtDrempelwaarde.clear();
		txtSubSDG.clear();
		initialize();
	}
	@FXML
	public void vulVelden(ActionEvent event) {
		IDoelstelling d = dc.getDoelstellingByName(listDoelstellingen.getSelectionModel().getSelectedItem());
		ADoelstelling doel = (ADoelstelling) d;
		if(doel.getSubDoelstelling().isEmpty()) {
			txtNaam.setText(doel.getName());
			txtSDG.setText(doel.getMainSDG().getName());
			txtDrempelwaarde.setText(String.format("%.2f", doel.getThreshold()));
		}
	}

}