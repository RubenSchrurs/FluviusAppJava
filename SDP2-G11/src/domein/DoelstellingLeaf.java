package domein;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
	@NamedQuery(name = "DoelstellingLeaf.findDoelstellingenByDatasource", 
			query = "select d from DoelstellingLeaf d where d.doelstellingID in (select d.doelstellingID from DoelstellingLeaf d join d.datasources data where data.name = :name)")
})
public class DoelstellingLeaf extends ADoelstelling{

	private static final long serialVersionUID = 1L;
	
	@ManyToMany
	private List<Datasource> datasources;
	
	public DoelstellingLeaf(String name, double threshold) {
		super(name, threshold);
		datasources = new ArrayList<>();
	}
	
	protected DoelstellingLeaf() {
		
	}
	
	public List<IDatasource> getDatasources(){
		return datasources.stream().map(d -> (Datasource) d).collect(Collectors.toList());
	}
	
	public void removeDatasource(IDatasource d) {
		datasources.remove(d);
	}
	
	public void addDatasource(Datasource d) {
		Datasource data = (Datasource) d;
		datasources.add(data);
	}
	
	public void setDatasources(List<Datasource> d) {
		if(d == null) {
			this.datasources.clear();
		}
		else{
			List<Datasource> dataLijst = d.stream().map(data -> (Datasource) data).collect(Collectors.toList());
			this.datasources = dataLijst;
		}
	}
	
	public Number getValue(BewerkingStrategy bewerking) {
		List<Double> l = new ArrayList<>();
		datasources.stream().forEach(datasource -> {
			if(datasource.getFileName().contains(".csv")) {
				l.addAll(datasource.getGegevens().values());
			}
			else {
				l.add(datasource.getWaarde());
			}
		});
		return bewerking.voerBewerkingUit(l);
	}

}
