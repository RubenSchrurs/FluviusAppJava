package gui;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;


import java.util.TreeMap;

import domein.DomeinController;
import domein.IDatasource;
import domein.IDoelstelling;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

public class DatasourceController {
	// Menu controls
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

	// Alle controls op het datasourceScherm
	@FXML
	private Label lblNaam;
	@FXML
	private TextField txtNaam;
	@FXML
	private Label lblBestand;
	@FXML
	private TextField txtBestandsNaam;
	@FXML
	private Button btnBestandKiezen;

	private File selectedFile;

	@FXML
	private Button btnAddDatasource;
	@FXML
	private Button btnRemoveDatasource;

	@FXML
	private Label lblDatasource;
	@FXML
	private ListView<String> listDatasources;

	@FXML
	private Label lblDatasourceDetails;

	@FXML
	private TableView<Map.Entry<String, Double>> datasourceDetailsTable;
	@FXML
	private TableColumn<Map.Entry<String, Double>, String> jaartalCol;
	@FXML
	private TableColumn<Map.Entry<String, Double>, String> waardeCol;
	@FXML
	private TableColumn<Map.Entry<String, Double>, String> eenheidCol;
	@FXML
	private TextField txtWaardeData;
	@FXML
	private ComboBox<String> cbEenheidWaarde;
	@FXML
	private Label lblSingleValue;
	
	private ObservableList<String> eenheden = FXCollections.observableArrayList("kWh","km/h","J","Ton");

	private DomeinController dc;

	public void initialize() {
		dc = new DomeinController();

		// datasources inladen
		ObservableList<String> list = dc.getDatasources().stream().map(s -> s.getName())
				.collect(Collectors.toCollection(FXCollections::observableArrayList));
		listDatasources.getItems().setAll(list);

		// table view opvullen
		listDatasources.setOnMouseClicked(eventHandler);
		
		cbEenheidWaarde.setItems(eenheden);
		txtBestandsNaam.setDisable(true);
		btnRemoveDatasource.setVisible(false);
	}

	EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent click) {
			try {
				String selected = listDatasources.getSelectionModel().getSelectedItem();
				btnRemoveDatasource.setVisible(true);
				IDatasource d = dc.getDatasourceByName(selected);
				if(d.getFileName() != null && !d.getFileName().trim().isBlank()) {
					jaartalCol.setCellValueFactory(
							new Callback<TableColumn.CellDataFeatures<Map.Entry<String, Double>, String>, ObservableValue<String>>() {
								@Override
								public ObservableValue<String> call(
										TableColumn.CellDataFeatures<Map.Entry<String, Double>, String> p) {
									return new SimpleStringProperty(p.getValue().getKey());
								}
							});

					waardeCol.setCellValueFactory(
							new Callback<TableColumn.CellDataFeatures<Map.Entry<String, Double>, String>, ObservableValue<String>>() {
								@Override
								public ObservableValue<String> call(
										TableColumn.CellDataFeatures<Map.Entry<String, Double>, String> p) {
									return new SimpleStringProperty(p.getValue().getValue().toString());
								}
							});

					eenheidCol.setCellValueFactory(c -> new SimpleStringProperty(d.getEenheidData()));

					TreeMap<String, Double> m = new TreeMap<String, Double>(d.getGegevens());
					
					
					ObservableList<Entry<String, Double>> items = FXCollections.observableArrayList(m.entrySet());

					datasourceDetailsTable.setItems(items);
					if(!eenheden.contains(d.getEenheidData())) {
						cbEenheidWaarde.getItems().add(d.getEenheidData());
					}	
					lblSingleValue.setText("");
				}
				else if(d.getWaarde() != 0 && d.getEenheidData() != null && !d.getEenheidData().trim().isBlank()) {
					datasourceDetailsTable.getItems().clear();
					lblSingleValue.setText(d.getWaarde() + " " + d.getEenheidData());
				}
			} catch (Exception e) {
				Alert alert = new Alert(AlertType.ERROR, e.getMessage());
				alert.setTitle("Error");
				alert.showAndWait();
				return;
			}
		}
	};

	public void Logout(ActionEvent event) throws Exception {
		MainController mainController = new MainController();
		mainController.Logout(event);
	}

	public void SwitchToDashboard(ActionEvent event) throws Exception {
		MainController mainController = new MainController();
		mainController.SwitchToDashboard(event);
	}

	public void SwitchToCategorie(ActionEvent event) throws Exception {
		MainController mainController = new MainController();
		mainController.SwitchToCategorie(event);
	}

	public void SwitchToDoelstelling(ActionEvent event) throws Exception {
		MainController mainController = new MainController();
		mainController.SwitchToDoelstelling(event);
	}

	public void SwitchToSdg(ActionEvent event) throws Exception {
		MainController mainController = new MainController();
		mainController.SwitchToSdg(event);
	}

	@FXML
	private void addDatasource(ActionEvent event) {
		String naamDatasource = txtNaam.getText();
		File file = selectedFile;
		String waarde = txtWaardeData.getText();
		String eenheid = cbEenheidWaarde.getValue();
		String fileName = txtBestandsNaam.getText();
		
		if(isNumeric(waarde) && file != null && fileName != null) {
			Alert alert = new Alert(AlertType.WARNING, "Je moet kiezen tussen een waarde of een bestand.");
			alert.setTitle("Invalid data");
			alert.showAndWait();
		}
		else if (naamDatasource != null && !naamDatasource.trim().isBlank()
				&& !listDatasources.getItems().contains(naamDatasource) && file != null && fileName != null) {
			try {
				dc.addDatasource(naamDatasource, file);

				Alert alertSucces = new Alert(AlertType.INFORMATION, "Datasource succesvol toegevoegd");
				alertSucces.setTitle("Succes");
				alertSucces.show();

				this.initialize();
			} 
			catch (Exception e) {
				Alert alert = new Alert(AlertType.ERROR, e.getMessage());
				alert.setTitle("Invalid data");
				alert.showAndWait();
				
				clearFields();
			}
		}
		else if(waarde != null && eenheid != null && !eenheid.trim().isBlank() && isNumeric(waarde)) {
			try {
				dc.addDatasource(naamDatasource, Double.parseDouble(waarde), eenheid);
				
				Alert alertSucces = new Alert(AlertType.INFORMATION, "Datasource succesvol toegevoegd");
				alertSucces.setTitle("Succes");
				alertSucces.show();

				this.initialize();
			}
			catch(Exception e){
				Alert alert = new Alert(AlertType.ERROR, e.getMessage());
				alert.setTitle("Invalid data");
				alert.showAndWait();
				
				clearFields();
			}
		} 
		else if (listDatasources.getItems().contains(naamDatasource)) {
			Alert alert = new Alert(AlertType.ERROR, "Deze naam is al in gebruik");
			alert.setTitle("Invalid data");
			alert.showAndWait();
		} 
		else if (naamDatasource == null || naamDatasource.trim().isBlank()) {
			Alert alert = new Alert(AlertType.ERROR, "Kies een naam");
			alert.setTitle("Invalid data");
			alert.showAndWait();
		}
		else if ((waarde == null || waarde.trim().isBlank() || !isNumeric(waarde) || eenheid == null|| eenheid.trim().isBlank()) && file == null){
			Alert alert = new Alert(AlertType.ERROR, "Kies een waarde +  een eenheid OF een geldig bestand");
			alert.setTitle("Invalid data");
			alert.showAndWait();
		}
		else if (waarde == null || waarde.trim().isBlank() || !isNumeric(waarde) || eenheid == null|| eenheid.trim().isBlank()){
			Alert alert = new Alert(AlertType.ERROR, "Kies een waarde en een eenheid");
			alert.setTitle("Invalid data");
			alert.showAndWait();
		}
		else {

			Alert alert = new Alert(AlertType.ERROR, "Selecteer een geldig csv bestand");
			alert.setTitle("Invalid data");
			alert.showAndWait();
		}
		clearFields();
}
	

	@FXML
	private void removeDatasource(ActionEvent event) {
		String selectedDatasource = listDatasources.getSelectionModel().getSelectedItem();
		if (selectedDatasource != null) {
			Alert alert = new Alert(AlertType.CONFIRMATION, "Delete " + selectedDatasource + " ?", ButtonType.YES,
					ButtonType.NO);
			alert.showAndWait();

			if (alert.getResult() == ButtonType.YES) {
				listDatasources.getSelectionModel().clearSelection();
				List<IDoelstelling> l = dc.getDoelstellingenByDatasourceName(selectedDatasource);
				if(l.size() == 0) {
					try {
						dc.removeDatasource(selectedDatasource);
						clearFields();
						this.initialize();
					}
					catch(Exception e) {
						System.err.println(e.getMessage());
						Alert alert2 = new Alert(AlertType.WARNING, "De huidige datasource kan momenteel niet verwijderd worden");
						alert2.setTitle("Invalid data");
						alert2.showAndWait();
					}	
				}
				else {
					Alert alert2 = new Alert(AlertType.WARNING, 
							"De huidige datasource kan momenteel niet verwijderd worden doordat het nog gelinkt is aan doelstelling " + l.get(0).getName());
					alert2.setTitle("Linked datasource");
					alert2.showAndWait();
				}	
			}
		}
	}

	@FXML
	public void bestandKiezen(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		File file = fileChooser.showOpenDialog(stage);

		txtBestandsNaam.setText(file.getName());
		this.selectedFile = file;
	}
	
	public boolean isNumeric(String str) { 
		  try {  
		    Double.parseDouble(str);  
		    return true;
		  } catch(NumberFormatException e){  
		    return false;  
		  }  
		}
	
	public void clearFields() {
		txtBestandsNaam.clear();
		txtWaardeData.clear();
		cbEenheidWaarde.setValue("");
		this.selectedFile = null;
		txtNaam.clear();
		datasourceDetailsTable.getItems().clear();
		lblSingleValue.setText("");
	}

}
