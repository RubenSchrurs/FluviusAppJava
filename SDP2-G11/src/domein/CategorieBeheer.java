package domein;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import repository.CategorieDao;
import repository.CategorieDaoJpa;
import repository.SDGDaoJpa;

public class CategorieBeheer {

	private ObservableList<String> categories;
	CategorieDao categorieRepo = new CategorieDaoJpa();

	public CategorieBeheer() {

		List<String> catss = categorieRepo.findAll().stream().map(Categorie::getName).collect(Collectors.toList());
		categories = FXCollections.observableArrayList(catss);
		
	}

	public ObservableList<String> getCategories() {
		return FXCollections.unmodifiableObservableList(categories);
	}

	public void addCategorie(String c) {
		if (c != null && !c.trim().isEmpty()) {
			categories.add(c);
			
			CategorieDaoJpa.startTransaction();
			
			List<ADoelstelling> doelstelling = new ArrayList<>();
			Categorie cat = new Categorie(c, doelstelling);
			categorieRepo.insert(cat);
			
			CategorieDaoJpa.commitTransaction();

		}
	}

	public void removeCategorie(String c) {
		categories.remove(c);
		ICategorie cat = categorieRepo.getCategorieByName(c);
		if (cat != null) {
			
			CategorieDaoJpa.startTransaction();
			Categorie categorie = (Categorie) cat;
			categorieRepo.delete(categorie);
			
			CategorieDaoJpa.commitTransaction();
		}
	}
	
	public boolean noCategories() {
		return categories.isEmpty();
	}

	public void addObserver(ListChangeListener<String> listener) {
		categories.addListener(listener);
	}
	public ICategorie getCategorieByName(String cat) {
		return categorieRepo.getCategorieByName(cat);
	}
	public void editCategorieName(String newCategorieName, String oldCategorieName) {
		ICategorie cat = getCategorieByName(oldCategorieName);
		Categorie c = (Categorie) cat;
		c.setName(newCategorieName);
	}
	public void updateCategorie(String catNaam){
		SDGDaoJpa.startTransaction();
		ICategorie cat = getCategorieByName(catNaam);
		Categorie c = (Categorie) cat;
		categorieRepo.update(c);
		
		SDGDaoJpa.commitTransaction();
	}
}
