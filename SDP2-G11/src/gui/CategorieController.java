package gui;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import domein.DomeinController;
import domein.ICategorie;
import domein.IDoelstelling;
import domein.ISDG;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.TextFlow;

public class CategorieController implements Initializable {
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
	private Button btnLogout;
	@FXML
	private ImageView imgLogo;
	@FXML
	private Label txtDisplayCurrentTab;
	@FXML
	private TextFlow txtCurrentUser;
	// Alle controls op het categorieScherm
	@FXML
	private Button btnAddCategorie;
	@FXML
	private Button btnRemoveCategorie;
	@FXML
	private Button btnEditCategorie;
	@FXML
	private Button btnAddSDGToCategorie;
	@FXML
	private Button btnAddDoelstelling;
	@FXML
	private ListView<String> listCategories;
	@FXML
	private ListView<String> listSDGs;
	@FXML
	private ListView<String> listLinkedSDGs;
	@FXML
	private ListView<String> listDoelstellingen;
	@FXML
	private ListView<String> listVrijeDoelstellingen;
	@FXML
	private ListView<String> listDoelstellingToevoegen;
	@FXML
	private ListView<String> listAlleSDGs;
	@FXML
	private TextField txtCategorieNaamFieldAdd;
	@FXML
	private TextField txtCategorieNaamFieldEdit;
	@FXML
	private Label lblCategorieNaam;

	private DomeinController dc;

	private ObservableList<ISDG> sdgs;
	private ObservableList<IDoelstelling> vrijeDoelstellingen;
	private ObservableList<ICategorie> categories;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		dc = new DomeinController();


		sdgs = dc.getSDGs();
		vrijeDoelstellingen = dc.geefVrijeDoelstellingen();
		categories = dc.getCategories();

		// SDG's inladen
		listSDGs.setItems(sdgs.stream().map(s -> s.getName())
				.collect(Collectors.toCollection(FXCollections::observableArrayList)));
		listAlleSDGs.setItems(dc
				.getSDGsWithSubs(dc.getSDGsByCategorie(lblCategorieNaam.getText()).stream().map(ISDG::getName)
						.collect(Collectors.toList()))
				.stream().map(s -> s.getName()).collect(Collectors.toCollection(FXCollections::observableArrayList)));
		// Categories inladen
		listCategories.setItems(categories.stream().map(c -> c.getName())
				.collect(Collectors.toCollection(FXCollections::observableArrayList)));
		// Vrije Doelstellingen inladen
		listVrijeDoelstellingen.setItems(vrijeDoelstellingen.stream().map(d -> d.getName())
				.collect(Collectors.toCollection(FXCollections::observableArrayList)));
		listDoelstellingToevoegen.setItems(vrijeDoelstellingen.stream().map(d -> d.getName())
				.collect(Collectors.toCollection(FXCollections::observableArrayList)));
		showButtons(false);
	}

	public void Logout(ActionEvent event) throws Exception {
		MainController mainController = new MainController();
		mainController.Logout(event);
	}

	// switch naar andere scenes
	public void SwitchToDashboard(ActionEvent event) throws Exception {
		MainController mainController = new MainController();
		mainController.SwitchToDashboard(event);
	}

	public void SwitchToDoelstelling(ActionEvent event) throws Exception {
		MainController mainController = new MainController();
		mainController.SwitchToDoelstelling(event);
	}

	public void SwitchToDatasource(ActionEvent event) throws Exception {
		MainController mainController = new MainController();
		mainController.SwitchToDatasource(event);
	}

	public void SwitchToSdg(ActionEvent event) throws Exception {
		MainController mainController = new MainController();
		mainController.SwitchToSdg(event);
	}

	// add categorie
	@FXML
	private void addCategorie(ActionEvent event) throws Exception {

		String naamCategorie = txtCategorieNaamFieldAdd.getText();
		String selectedSDG = listSDGs.getSelectionModel().getSelectedItem();
		String selectedDoelstelling = listVrijeDoelstellingen.getSelectionModel().getSelectedItem();

		if (naamCategorie != null && !naamCategorie.trim().isBlank()
				&& !listCategories.getItems().contains(naamCategorie) && selectedSDG != null
				&& selectedDoelstelling != null) {

			dc.addCategorie(naamCategorie, selectedDoelstelling);
			dc.linkCategorieAanSDG(selectedSDG, naamCategorie);
			dc.updateSDG(selectedSDG);

			listCategories.getSelectionModel().selectLast();
			txtCategorieNaamFieldAdd.clear();

			Alert alertSucces = new Alert(AlertType.INFORMATION, "Categorie succesvol toegevoegd");
			alertSucces.setTitle("Succes");
			alertSucces.show();

			this.initialize(null, null);
		} else if (selectedSDG == null || selectedDoelstelling == null) {
			Alert alert = new Alert(AlertType.ERROR, "Selecteer minstens een SDG en een MVO doelstelling");
			alert.setTitle("Invalid data");
			alert.showAndWait();
		} else if (listCategories.getItems().contains(naamCategorie)) {
			Alert alert = new Alert(AlertType.ERROR, "Deze naam is al in gebruik");
			alert.setTitle("Invalid data");
			alert.showAndWait();
		} else if (naamCategorie == null || naamCategorie.trim().isBlank()
				|| listCategories.getItems().contains(naamCategorie)) {
			Alert alert = new Alert(AlertType.ERROR, "Kies een naam");
			alert.setTitle("Invalid data");
			alert.showAndWait();
		}

	}

	// remove categorie
	@FXML
	private void removeCategorie(ActionEvent event) {
		String selectedCategorie = listCategories.getSelectionModel().getSelectedItem();
		if (selectedCategorie != null) {
			Alert alert = new Alert(AlertType.CONFIRMATION, "Delete " + selectedCategorie + " ?", ButtonType.YES,
					ButtonType.NO);
			alert.showAndWait();

			if (alert.getResult() == ButtonType.YES) {
				listCategories.getSelectionModel().clearSelection();
				dc.removeCategorie(selectedCategorie);
				lblCategorieNaam.setText("Naam");
				this.initialize(null, null);
			}
		}
	}

	@FXML
	private void handleListViewAction(MouseEvent event) {
		String selectedCategorie = listCategories.getSelectionModel().getSelectedItem();
		ObservableList<String> alleTeTonenSDGs = FXCollections.observableArrayList();

		if (selectedCategorie != null) {
			showButtons(true);
			lblCategorieNaam.setText(selectedCategorie);
			listLinkedSDGs.setItems(dc.getSDGsByCategorie(selectedCategorie).stream().map(s -> s.getName())
					.collect(Collectors.toCollection(FXCollections::observableArrayList)));

			alleTeTonenSDGs.addAll(dc
					.getSDGsWithSubs(dc.getSDGsByCategorie(selectedCategorie).stream().map(ISDG::getName)
							.collect(Collectors.toList()))
					.stream().map(s -> s.getName())
					.collect(Collectors.toCollection(FXCollections::observableArrayList)));

			listDoelstellingen.setItems(dc.getDoelstellingenByCategorie(selectedCategorie).stream()
					.map(d -> d.getName()).collect(Collectors.toCollection(FXCollections::observableArrayList)));
			listDoelstellingToevoegen.setItems(dc.geefVrijeDoelstellingen().stream().map(d -> d.getName())
					.collect(Collectors.toCollection(FXCollections::observableArrayList)));
			listAlleSDGs.setItems(alleTeTonenSDGs);
		}
	}

	@FXML
	private void addSdgToCategorie() {
		String selectedSDG = listAlleSDGs.getSelectionModel().getSelectedItem();
		String naamCategorie = lblCategorieNaam.getText();
		dc.linkCategorieAanSDG(selectedSDG, naamCategorie);
		dc.updateSDG(selectedSDG);
		listLinkedSDGs.setItems(dc.getSDGsByCategorie(naamCategorie).stream().map(s -> s.getName())
				.collect(Collectors.toCollection(FXCollections::observableArrayList)));
		listCategories.getSelectionModel().clearSelection();
		this.initialize(null, null);
	}

	@FXML
	private void addDoelstellingToCategorie() {
		String selectedDoelstelling = listDoelstellingToevoegen.getSelectionModel().getSelectedItem();
		String naamCategorie = lblCategorieNaam.getText();
		dc.addDoelstellingToCategorie(naamCategorie, selectedDoelstelling);
		dc.updateCategorie(naamCategorie);
		listDoelstellingen.setItems(dc.getDoelstellingenByCategorie(naamCategorie).stream().map(d -> d.getName())
				.collect(Collectors.toCollection(FXCollections::observableArrayList)));
		listCategories.getSelectionModel().clearSelection();
		this.initialize(null, null);
	}

	@FXML
	private void editCategorieNaam(ActionEvent event) {
		String newCategorieName = txtCategorieNaamFieldEdit.getText();
		String oldCategorieName = listCategories.getSelectionModel().getSelectedItem();
		dc.editCategorieName(newCategorieName, oldCategorieName);
		dc.updateCategorie(newCategorieName);
		lblCategorieNaam.setText(newCategorieName);
		txtCategorieNaamFieldEdit.clear();
		listCategories.getSelectionModel().clearSelection();
		this.initialize(null, null);
	}
	
	public void showButtons(boolean tonen) {
		btnAddSDGToCategorie.setVisible(tonen);
		btnAddDoelstelling.setVisible(tonen);
		btnEditCategorie.setVisible(tonen);
		btnRemoveCategorie.setVisible(tonen);
		
	}

}
