package domein;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({
	@NamedQuery(name = "Categorie.findByName", query = "SELECT c FROM Categorie c WHERE c.name = :name"),
	@NamedQuery(name = "Categorie.findCategorieByDoelstellingName", 
		query = "select c from Categorie c join c.doelstellingen d where d.name = :name")
})
public class Categorie implements Serializable //, CatInterface
, ICategorie
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int categorieID;
	
	private String name;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<ADoelstelling> doelstellingen;
	
	protected Categorie() {}

	public Categorie(String name, List<ADoelstelling> doelstellingen) {
		this.name = name;
		this.doelstellingen = doelstellingen;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public List<ADoelstelling> getDoelstellingen() {
		return Collections.unmodifiableList(doelstellingen);
	}
	
	public void addDoelstelling(ADoelstelling doelstelling) {
		doelstellingen.add(doelstelling);
	}
	
	public void removeDoelstelling(ADoelstelling doelstelling) {
		doelstellingen.remove(doelstelling);
	}
	
	public void setDoelstellingen(List<ADoelstelling> doelstelling) {
		this.doelstellingen = doelstelling;
	}

	@Override
	public String toString() {
		return "Categorie [doelstellingen=" + doelstellingen + ", categorieID=" + categorieID + ", name=" + name + "]";
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
		Categorie other = (Categorie) obj;
		return Objects.equals(name, other.name);
	}
	
}
