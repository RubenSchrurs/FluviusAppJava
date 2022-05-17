package domein;

import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import repository.SDGDao;
import repository.SDGDaoJpa;

public class SDGBeheer {

	private ObservableList<String> sdgLijst;
	SDGDao dc = new SDGDaoJpa();

	public SDGBeheer() {

		List<String> sdgs = dc.findAll().stream().filter(sdg -> sdg.getMainSDG() == null).map(SDG::getName).collect(Collectors.toList());

		sdgLijst = FXCollections.observableArrayList(sdgs);
	}

	public ObservableList<String> getSDGs() {
		return FXCollections.unmodifiableObservableList(sdgLijst);
	}
	
	public ObservableList<String> getSubSdgsByMainSdg(String sdg){
		return FXCollections.observableArrayList(dc.getSubSDGsByNameMainSDG(sdg).stream().map(ISDG::getName).collect(Collectors.toList()));
	}
	
	public ObservableList<String> getBeschrijving(String sdg, String subSdg){
		return FXCollections.observableArrayList(dc.getSubSDGsByNameMainSDG(sdg).stream().filter(x -> x.getName() == subSdg).findFirst().get().getDescription());
	}
	
	public ObservableList<String> getFreeSDGs() {
		return FXCollections.observableArrayList(dc.getFreeSDGs().stream().map(ISDG::getName).collect(Collectors.toList()));
	}
	
	public ObservableList<String> getSDGsByCategorie(String Cat) {
		return FXCollections.observableArrayList(dc.getSDGsByCategorieName(Cat).stream().map(ISDG::getName).collect(Collectors.toList()));
	}
	
	public ISDG getSDGByName(String sdg) {
		return dc.getSDGByName(sdg);
	}
	
	public void addSDG(String c) {
		if (c != null && !c.trim().isEmpty()) {
			sdgLijst.add(c);
			
			SDGDaoJpa.startTransaction();

			ISDG s = new SDG(7, c);
			dc.insert((SDG) s);
			
			SDGDaoJpa.commitTransaction();

		}
	}

	public void removeSDG(String c) {
		sdgLijst.remove(c);
		ISDG s = dc.getSDGByName(c);
		if (s != null) {
			
			SDGDaoJpa.startTransaction();
			
			dc.delete((SDG) s);
			
			SDGDaoJpa.commitTransaction();
		}
	}
	
	public void updateSDG(String sdgNaam){
		SDGDaoJpa.startTransaction();
		ISDG s = dc.getSDGByName(sdgNaam);
		dc.update((SDG) s);
		
		SDGDaoJpa.commitTransaction();
	}

	
	public boolean noSDGs() {
		return sdgLijst.isEmpty();
	}

	public void addObserver(ListChangeListener<String> listener) {
		sdgLijst.addListener(listener);
	}
	
	
}
