package domein;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class DomeinController {
	private Fluvius fluvius;

	private ObservableList<ISDG> sdgs;
	private ObservableList<IDoelstelling> doelstellingen;
	
	private ObservableList<IDoelstelling> vrijeDoelstellingen;

	private ObservableList<ICategorie> categories;
	private ObservableList<IDatasource> datasources;
	private ObservableList<IDoelstelling> alleDoelstellingen;
	
	public DomeinController() {
		this(false);
	}

	public DomeinController(boolean fillDb) {
		if (fillDb) {
			PopulateDB.run();
		}
		fluvius = new Fluvius();

		categories = FXCollections.observableArrayList(fluvius.getAllCategories().stream().map(c -> (ICategorie) c ).collect(Collectors.toList()));
		sdgs = FXCollections.observableArrayList(fluvius.getVrijeSDGs());
		vrijeDoelstellingen = FXCollections.observableArrayList(
				fluvius.getUnusedDoelstellingen().stream().collect(Collectors.toList()));
		alleDoelstellingen = FXCollections.observableArrayList(fluvius.getAllDoelstellingen());
	}

	public ObservableList<ICategorie> getCategories() {
		return categories;
	}

	public ObservableList<ISDG> getSDGs() {
		return sdgs;
	}

	public ObservableList<ISDG> getSDGsWithSubs(List<String> linkedSDGs) {
		if (linkedSDGs != null) {
			for (String sdg : linkedSDGs) {
				for (ISDG s : fluvius.getSubSDGsByNameMainSDG(sdg)) {
					if(!linkedSDGs.contains(s.getName()))
						sdgs.add(s);
				}
			}
		}
		return sdgs;
	}

	public ISDG getSDGByName(String s) {
		return fluvius.getSDGByName(s);
	}

	public ICategorie getCategorieByName(String c) {
		return fluvius.getCategorieByName(c);
	}
	
	public Datasource getDatasourceByName(String d) {
		return fluvius.getDatasourceByName(d);
	}

	public ObservableList<ISDG> getSDGsByCategorie(String cat) {
		return fluvius.getSDGsByCategorie(cat);
	}

	public ObservableList<IDoelstelling> getDoelstellingenByCategorie(String cat) {
		doelstellingen = FXCollections.observableArrayList(fluvius.getDoelstellingenByCategorie(cat));
		return doelstellingen;
	}

	public void updateSDG(String s) {
		fluvius.updateSDG(s);
	}

	public boolean noCategories() {
		return categories.isEmpty();
	}

	public boolean noSDGs() {
		return sdgs.isEmpty();
	}

	public void addDoelstellingToCategorie(String cat, String d) {
		ICategorie categorie = fluvius.getCategorieByName(cat);
		IDoelstelling doelstelling = fluvius.getDoelstellingByName(d);
		Categorie c = (Categorie) categorie;
		c.addDoelstelling((ADoelstelling) doelstelling);
	}

	public void addCategorie(String c, String d) {
		if (c != null && !c.trim().isEmpty()) {
			fluvius.addCategorie(c, d);
		}
	}

	public void removeCategorie(String c) {
		List<ISDG> lijst = fluvius.getSDGsByCategorieName(c);
		lijst.stream().forEach(sdg -> {
			SDG s = (SDG) sdg;
			s.setCategorie(null);
			fluvius.updateSDG(s.getName());
		});
		ICategorie cat = fluvius.getCategorieByName(c);
		Categorie categorie = (Categorie) cat;
		categorie.setDoelstellingen(null);
		fluvius.updateCategorie(c);
		fluvius.removeCategorie(c);
	}

	public void linkCategorieAanSDG(String s, String cat) {
		ISDG sdg = fluvius.getSDGByName(s);
		ICategorie categorie = fluvius.getCategorieByName(cat);
		SDG sdgCopy = (SDG) sdg;
		sdgCopy.setCategorie((Categorie) categorie);
	}

	public ObservableList<IDoelstelling> geefDoelstellingen() {
		return doelstellingen;
	}

	public ObservableList<IDoelstelling> geefVrijeDoelstellingen() {
		return vrijeDoelstellingen;
	}

	public ObservableList<IDoelstelling> geefMainDoelstellingen() {
		doelstellingen = FXCollections.observableArrayList(fluvius.getMainDoelstellingen());
		if (!doelstellingen.isEmpty()) {
			return doelstellingen;
		}
		return null;
	}
	public List<IDoelstelling> geefAlleDoelstellingen(){
		if(!alleDoelstellingen.isEmpty()) {
			return alleDoelstellingen;
		}
		return null;
	}

	public void addDoelstelling(String naam, double drempelwaarde, String sdg, String subSDG, String mainDoelstelling, List<String> datasources) {
		if (naam != null && !naam.isBlank()) {
			fluvius.addDoelstelling(naam, drempelwaarde, sdg, subSDG, mainDoelstelling, datasources);
		}
	}

	public void removeDoelstelling(String naam) {
		IDoelstelling doel = fluvius.getDoelstellingByName(naam);
		ADoelstelling d = (ADoelstelling) doel;
		d.setDatasources(null);
		d.setMainDoelstelling(null);
		List<ICategorie> c = fluvius.getCategorieByDoelstellingName(naam);
		c.stream().forEach(cat -> {
			Categorie deelCat = (Categorie) cat;
			deelCat.setDoelstellingen(null);
			fluvius.updateCategorie(deelCat.getName());
		});
		List<IDoelstelling> l = fluvius.getSubDoelstellingenByMain(naam);
		l.stream().forEach(subDoel -> {
			ADoelstelling subD = (ADoelstelling) subDoel;
			subD.setDatasources(null);
			subD.setMainDoelstelling(null);
			fluvius.updateDoelstelling(subD.getName());
			fluvius.removeDoelstelling(subD.getName());
		});
		fluvius.updateDoelstelling(naam);
		fluvius.removeDoelstelling(naam);
	}

	public boolean noDoelstellingen() {
		return doelstellingen.isEmpty();
	}

	public void addObserver(ListChangeListener<String> listener, ObservableList<String> li) {
		li.addListener(listener);
	}

	public List<String> geefCategorieNamen() {
		return fluvius.getAllCategories().stream().map(Categorie::getName).collect(Collectors.toList());
	}

	public List<String> geefDoelstellingNamen() {
		return fluvius.getAllDoelstellingen().stream().map(ADoelstelling::getName).collect(Collectors.toList());
	}

	public List<IDatasource> geefDatasourceNamen() {
		return fluvius.getAllDatasources();
	}
	
	public ADoelstelling getDoelstellingByName(String name) {
		return fluvius.getDoelstellingByName(name);
	}

	public ObservableList<IDoelstelling> geefSubDoelstellingNamenVanMain(String name) {
		doelstellingen = FXCollections.observableArrayList(fluvius.getSubDoelstellingenByMain(name));
		if (!doelstellingen.isEmpty()) {
			return doelstellingen;
		}
		return null;
	}

	public List<String> geefSDGNamen() {
		return fluvius.getAllSDGs().stream().map(SDG::getName).collect(Collectors.toList());
	}

	public ObservableList<ISDG> geefSubSDGsByNameMainSDG(String name) {
		sdgs = FXCollections.observableArrayList(fluvius.getSubSDGsByNameMainSDG(name));
		if (!sdgs.isEmpty()) {
			return sdgs;
		}
		return null;
	}

	public ObservableList<ISDG> geefVrijeSDGs() {
		sdgs = FXCollections.observableArrayList(fluvius.getVrijeSDGs());
		if (!sdgs.isEmpty()) {
			return sdgs;
		}
		return null;
	}

	public ObservableList<ISDG> geefSDGsByCategorieName(String name) {
		sdgs = FXCollections.observableArrayList(fluvius.getSDGsByCategorieName(name));
		if (!sdgs.isEmpty()) {
			return sdgs;
		}
		return null;
	}

	public ObservableList<ISDG> geefMainSDG() {
		return FXCollections.observableArrayList(fluvius.getMainSDGs());
	}

	public void editCategorieName(String newCategorieName, String oldCategorieName) {
		ICategorie c = getCategorieByName(oldCategorieName);
		Categorie cat = (Categorie) c;
		cat.setName(newCategorieName);

	}

	public void updateCategorie(String catNaam) {
		fluvius.updateCategorie(catNaam);

	}
	
	public void editDoelstelling(String oldName, String newName, String sdg, double drempel) {
		IDoelstelling d = getDoelstellingByName(oldName);
		ADoelstelling doel = (ADoelstelling) d;
		if(newName != oldName) {
			doel.setName(newName);
		}
		doel.setThreshold(drempel);
		doel.setMainSDG((SDG) getSDGByName(sdg));
	}
	public void updateDoelstelling(String catNaam) {
		fluvius.updateDoelstelling(catNaam);
	}
	
	public List<IDoelstelling> getDoelstellingenByDatasourceName(String name){
		return fluvius.getDoelstellingByDatasourceName(name);
	}

	public ObservableList<IDatasource> getDatasources() {
		datasources = FXCollections.observableArrayList(geefDatasourceNamen());
		return datasources;
	}
	
	public void addDatasource(String name, File f) throws Exception {
		if (name != null && !name.trim().isEmpty() && f != null) {
			try {
				fluvius.addDatasource(name, f);
			} catch (Exception e) {
				throw e;
			}
		}
	}
	
	public void addDatasource(String name, double waarde, String eenheid) throws Exception {
		if (name != null && !name.trim().isEmpty() && waarde != 0 && eenheid != null ) {
			try {
				fluvius.addDatasource(name, waarde, eenheid);
			} catch (Exception e) {
				throw e;
			}
		}
	}
	
	public void removeDatasource(String c) throws Exception{
		try {
			fluvius.removeDatasource(c);
		}
		catch(Exception e) {
			throw e;
		}
	}
}
