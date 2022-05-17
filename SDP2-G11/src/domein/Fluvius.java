package domein;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import repository.CategorieDao;
import repository.CategorieDaoJpa;
import repository.DatasourceDao;
import repository.DatasourceDaoJpa;
import repository.DoelstellingDao;
import repository.DoelstellingDaoJpa;
import repository.SDGDao;
import repository.SDGDaoJpa;
import repository.UserDao;
import repository.UserDaoJpa;

public class Fluvius {
	// Repo's
	private CategorieDao categorieDao;
	private DoelstellingDao doelstellingDao;
	private DatasourceDao datasourceDao;
	private SDGDao SDGDao;
	private UserDao userDao;

	private List<ICategorie> categories;
	private List<IDoelstelling> doelstellingenLijst;
	private List<ISDG> sdgs = new ArrayList<>();
	private List<IDatasource> datasources = new ArrayList<>();
	private ISDG sdg = null;
	private ObservableList<ADoelstelling> alleDoelstellingen;

	public Fluvius() {
		setCategorieDao(new CategorieDaoJpa());
		setDoelstellingDao(new DoelstellingDaoJpa());
		setDatasourceDao(new DatasourceDaoJpa());
		setUserDao(new UserDaoJpa());
		setSDGDao(new SDGDaoJpa());
		alleDoelstellingen = FXCollections.observableArrayList(doelstellingDao.findAll());
	}
	
	public ObservableList<ADoelstelling> getAlleDoelstellingen(){
		return alleDoelstellingen;
	}

	public void setCategorieDao(CategorieDao categorieDao) {
		this.categorieDao = categorieDao;
	}

	public void setDoelstellingDao(DoelstellingDao doelstellingDao) {
		this.doelstellingDao = doelstellingDao;
	}

	public void setDatasourceDao(DatasourceDao datasourceDao) {
		this.datasourceDao = datasourceDao;
	}

	public void setSDGDao(SDGDao sDGDao) {
		SDGDao = sDGDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void addCategorie(String c, String d) {
		CategorieDaoJpa.startTransaction();
		List<ADoelstelling> doelstellingen = new ArrayList<>();
		doelstellingen.add((ADoelstelling) doelstellingDao.getDoelstellingByName(d));
		Categorie cat = new Categorie(c, doelstellingen);
		categorieDao.insert(cat);
		CategorieDaoJpa.commitTransaction();
	}

	public void removeCategorie(String c) {
		ICategorie cat = categorieDao.getCategorieByName(c);
		if (cat != null) {
			CategorieDaoJpa.startTransaction();
			categorieDao.delete((Categorie) cat);
			CategorieDaoJpa.commitTransaction();
		}
	}

	public List<Categorie> getAllCategories() {
		return categorieDao.findAll();
	}

	public List<User> getAllUsers() {
		return userDao.findAll();
	}

	public User getUserByName(String name) {
		return userDao.getUserByName(name);
	}

	public List<ADoelstelling> getAllDoelstellingen() {
		return doelstellingDao.findAll();
	}

	public List<IDoelstelling> getSubDoelstellingenByMain(String name) {
		try {
			doelstellingenLijst = doelstellingDao.getSubDoelstellingenByNameMain(name);
		} catch (EntityNotFoundException e) {
			System.err.println("Er zijn geen SubDoelstellingen gevonden voor main doelstelling " + name);
		}
		return doelstellingenLijst;
	}

	public ADoelstelling getDoelstellingByName(String d) {
		return doelstellingDao.getDoelstellingByName(d);
	}

	public List<IDoelstelling> getMainDoelstellingen() {
		try {
			doelstellingenLijst = doelstellingDao.getMainDoelstellingen();
		} catch (EntityNotFoundException e) {
			System.err.println("Er zijn geen Doelstellingen gevonden");
		}
		return doelstellingenLijst;
	}

	public void addDoelstelling(String naam, double drempelwaarde, String sdg, String subSDG, String mainDoelstelling, List<String> datasources) {
		DoelstellingDaoJpa.startTransaction();
		ADoelstelling doel = null;
		if(datasources.isEmpty()) {
			doel = new DoelstellingComposite(naam, drempelwaarde);
		}
		else if(!datasources.isEmpty()) {
			doel = new DoelstellingLeaf(naam, drempelwaarde);
		}
		doel.setMainSDG((SDG) SDGDao.getSDGByName(sdg));
		System.out.printf("In fluvius: %s", mainDoelstelling);
		System.out.printf("In fluvius: %s", datasources);
		if(!datasources.isEmpty()) {
			List<Datasource> nieuw = new ArrayList<>();
			datasources.forEach(data -> nieuw.add(getDatasourceByName(data)));
			doel.setDatasources(nieuw);
		}
		if(mainDoelstelling != null) {
			doel.setMainDoelstelling(getDoelstellingByName(mainDoelstelling));
			getDoelstellingByName(mainDoelstelling).setSubDoelstelling(doel);
		}
		if(subSDG != null) {
			doel.setSubSDG((SDG) SDGDao.getSDGByName(subSDG));
		}
		doelstellingDao.insert(doel);
		DoelstellingDaoJpa.commitTransaction();
	}

	public void removeDoelstelling(String naam) {
		IDoelstelling doel = doelstellingDao.getDoelstellingByName(naam);
		if (doel != null) {
			CategorieDaoJpa.startTransaction();
			doelstellingDao.delete((ADoelstelling) doel);
			CategorieDaoJpa.commitTransaction();
		}
	}

	public List<IDoelstelling> getUnusedDoelstellingen() {
		return doelstellingDao.getUnusedDoelstellingen();
	}

	public ObservableList<IDoelstelling> getDoelstellingenByCategorie(String c) {
		return FXCollections.observableArrayList(doelstellingDao.getDoelstellingenByCategorie(c));
	}
	
	public List<IDoelstelling> getDoelstellingByDatasourceName(String name){
		List<IDoelstelling> lijst = null;
		try {
			lijst = doelstellingDao.getDoelstellingenByDatasourceName(name);
		}
		catch(EntityNotFoundException e) {
			System.err.println("Geen doelstellingen gevonden voor de Datasource " + name);
		}
		return lijst;
	}

	public List<IDatasource> getAllDatasources() {
		return datasourceDao.findAll().stream().map(d -> (IDatasource) d).collect(Collectors.toList());
	}

	public List<SDG> getAllSDGs() {
		return SDGDao.findAll();
	}

	public List<ISDG> getVrijeSDGs() {
		try {
			sdgs = SDGDao.getFreeSDGs();
		} catch (EntityNotFoundException e) {
			System.err.println("Er zijn geen vrije SDGs");
		}
		return sdgs;
	}

	public List<ISDG> getMainSDGs() {
		return SDGDao.findAll().stream().filter(sdg -> sdg.getMainSDG() == null).map(s -> (ISDG) s)
				.collect(Collectors.toList());
	}

	public List<ISDG> getSubSDGsByNameMainSDG(String name) {
		try {
			sdgs = SDGDao.getSubSDGsByNameMainSDG(name);
		} catch (EntityNotFoundException e) {
			System.err.println("Er zijn geen SubSDGs gevonden voor main sdg " + name);
		}
		return sdgs;
	}

	public List<ISDG> getSDGsByCategorieName(String name) {
		try {
			sdgs = SDGDao.getSDGsByCategorieName(name);
		} catch (EntityNotFoundException e) {
			System.err.println("Er zijn geen SDGs gevonden voor categorie " + name);
		}
		return sdgs;
	}

	public SDG getSDGByName(String s) {
		return SDGDao.getSDGByName(s);
	}

	public ICategorie getCategorieByName(String cat) {
		return categorieDao.getCategorieByName(cat);
	}

	public Datasource getDatasourceByName(String d) {
		return datasourceDao.getDatasourceByName(d);
	}

	public List<ICategorie> getCategorieByDoelstellingName(String name) {
		try {
			categories = categorieDao.getCategorieByDoelstellingName(name);
		} catch (EntityNotFoundException e) {
			System.err.println("Er zijn geen categorie�n gevonden voor doelstelling " + name);
		}
		return categories;
	}

	public void updateDoelstelling(String name) {
		DoelstellingDaoJpa.startTransaction();
		doelstellingDao.update((ADoelstelling) doelstellingDao.getDoelstellingByName(name));
		DoelstellingDaoJpa.commitTransaction();
	}

	public void updateSDG(String sdgNaam) {

		SDGDaoJpa.startTransaction();
		SDGDao.update((SDG) SDGDao.getSDGByName(sdgNaam));
		SDGDaoJpa.commitTransaction();
	}

	public ObservableList<ISDG> getSDGsByCategorie(String Cat) {
		return FXCollections.observableArrayList(SDGDao.getSDGsByCategorieName(Cat));
	}

	public void updateCategorie(String catNaam) {
		SDGDaoJpa.startTransaction();

		categorieDao.update((Categorie) getCategorieByName(catNaam));

		SDGDaoJpa.commitTransaction();
	}

	public void removeDatasource(String dataNaam) throws Exception {
		IDatasource d = datasourceDao.getDatasourceByName(dataNaam);
		if (d != null) {
				DatasourceDaoJpa.startTransaction();
				Datasource data = (Datasource) d;
				datasourceDao.delete(data);
				DatasourceDaoJpa.commitTransaction();
		}
	}

	public void addDatasource(String name, File f) throws Exception {
		try {
			Datasource d = new Datasource(f);
			d.setName(name);
			DatasourceDaoJpa.startTransaction();
			datasourceDao.insert(d);
			DatasourceDaoJpa.commitTransaction();
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void addDatasource(String name, double waarde, String eenheid) throws Exception{
		try {
			Datasource d = new Datasource(name, waarde);
			d.setName(name);
			d.setWaarde(waarde);
			d.setEenheidData(eenheid);
			DatasourceDaoJpa.startTransaction();
			datasourceDao.insert(d);
			DatasourceDaoJpa.commitTransaction();
		} catch (Exception e) {
			throw e;
		}
	}
}
