package domein;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

@Entity
@NamedQueries({
	@NamedQuery(name = "ADoelstelling.findByName", query = "SELECT d FROM ADoelstelling d WHERE d.name = :name"),
	
	@NamedQuery(name = "ADoelstelling.findSubDoelstellingenByName", 
				query = "select d from ADoelstelling d where d.mainDoelstelling = (select d.doelstellingID from ADoelstelling d where d.name = :name)"),
	
	@NamedQuery(name = "ADoelstelling.findUnusedDoelstellingen",
				query = "select d from ADoelstelling d where d.doelstellingID not in (select d.doelstellingID from Categorie c join c.doelstellingen d)"),
	
	@NamedQuery(name = "ADoelstelling.findDoelstellingenByCategorie", 
				query = "select d from ADoelstelling d where d.doelstellingID in (select d.doelstellingID from Categorie c join c.doelstellingen d where c.name = :name)"),
	
	@NamedQuery(name = "ADoelstelling.findMainDoelstelling", query = "SELECT d FROM ADoelstelling d WHERE d.mainDoelstelling IS NULL"),
})
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class ADoelstelling implements IDoelstelling,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int doelstellingID;
	
	private String name;
	private double threshold;
	
	@OneToOne
	private SDG mainSDG;
	
	@OneToOne
	private SDG subSDG;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "mainDoelstellingId")
	private ADoelstelling mainDoelstelling;
	
	public ADoelstelling(String name, double threshold) {
		this.name = name;
		this.threshold = threshold;
	}
	
	protected ADoelstelling() {
		
	}
	
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getThreshold() {
		return threshold;
	}

	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}
	
	public void setMainSDG(SDG main) {
		this.mainSDG = main;
	}
	
	public SDG getMainSDG() {
		return this.mainSDG;
	}
	
	public void setSubSDG(SDG sub) throws Error{
		if (sub.getMainSDG().getSDGid() == sub.getSDGid()) {
			this.subSDG = sub;
		}
		else {
			throw new Error("De subSDG behoort niet tot de mainSDG");
		}
	}
	
	public SDG getSubSDG() {
		return this.subSDG;
	}
	
	public Number getValue(BewerkingStrategy bewerking) {
		throw new UnsupportedOperationException();
	}
	
	public void addSubDoelstelling(ADoelstelling d) {
		throw new UnsupportedOperationException();
	}
	
	public List<ADoelstelling> getSubDoelstelling(){
		throw new UnsupportedOperationException();
	}
	
	public void removeDoelstelling(ADoelstelling doel) {
		throw new UnsupportedOperationException();
	}
	
	public void setSubDoelstellingen(List<ADoelstelling> doel) {
		throw new UnsupportedOperationException();
	}
	public void setSubDoelstelling(ADoelstelling doel) {
		throw new UnsupportedOperationException();
	}
	
	public IDoelstelling getMainDoelstelling() {
		return mainDoelstelling;
	}

	public void setMainDoelstelling(ADoelstelling mainDoelstelling) {
		this.mainDoelstelling = mainDoelstelling;
	}
	
	public List<IDatasource> getDatasources(){
		throw new UnsupportedOperationException();
	}
	
	public void removeDatasource(IDatasource d) {
		throw new UnsupportedOperationException();
	}
	
	public void addDatasource(Datasource d) {
		throw new UnsupportedOperationException();
	}
	
	public void setDatasources(List<Datasource> d) {
		try {
			System.out.println("Remove Datasources");
		}catch(Exception e) {
			
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ADoelstelling other = (ADoelstelling) obj;
		return Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "ADoelstelling [name=" + name + ", threshold=" + threshold + "]";
	}

}
