package domein;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({
	@NamedQuery(name = "SDG.findByName", query = "select s from SDG s where s.name = :name"),
	@NamedQuery(name = "SDG.findSubSDGsByNameMainSDG", 
			query = " select s from SDG s where s.mainSDG = (select s.SDGid from SDG s where s.name = :name)"),
	@NamedQuery(name = "SDG.findFreeSDGs", query = "select s from SDG s where s.categorie is null and s.mainSDG is null"),
	@NamedQuery(name = "SDG.findMainSDGsByCategorieName", 
				query = "select s from SDG s where s.categorie = (select c.categorieID from Categorie c where c.name = :name)"),
	@NamedQuery(name = "SDG.findSDGByDoelstellingName", query = "select s from SDG s join ADoelstelling d on s.dbId = d.doelstellingID where d.name = :name")

})
public class SDG implements Serializable, ISDG{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int dbId;
	private int SDGid;
	
	private String name;
	
	@Column(columnDefinition = "TEXT")
	private String description = "";
	
	@OneToMany(mappedBy = "mainSDG", cascade = CascadeType.ALL)
	private List<SDG> subSDGs;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "mainSDGid")
	private SDG mainSDG;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Categorie categorie;
	
	
	public SDG(int nr, String name) {
		this.SDGid = nr;
		this.name = name;
		this.subSDGs = new ArrayList<>();
	}
	
	protected SDG() {
		
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String descr) {
		this.description = descr;
	}
	
	public void setMainSDG(SDG mainSDG) {
		this.mainSDG = mainSDG;
	}
	
	@Override
	public ISDG getMainSDG() {
		return this.mainSDG;
	}
	
	public void addSubSDG(SDG subSDG) {
		this.subSDGs.add(subSDG);
	}
	
	@Override
	public List<SDG> getSubSDG(){
		return Collections.unmodifiableList(this.subSDGs);
	}
	
	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}
	
	@Override
	public ICategorie getCategorie() {
		return this.categorie;
	}
	
	public void removeCategorie(ICategorie categorie) {
        this.categorie = null;
    }
	
	@Override
	public int getSDGid() {
		return this.SDGid;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.name);
		if (this.description != null) {
			builder.append(" -> " + this.description);
		}
		this.subSDGs.stream().forEach(sub -> builder.append("\n\t" + sub.toString()));
		return builder.toString();
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
		SDG other = (SDG) obj;
		return Objects.equals(name, other.name);
	}
	
	

}
