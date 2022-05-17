package domein;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class DoelstellingComposite extends ADoelstelling 
{
	private static final long serialVersionUID = 1L;
	
	@OneToMany(mappedBy = "mainDoelstelling", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<ADoelstelling> subDoelstellingen;

	public DoelstellingComposite(String name, double threshold) {
		super(name, threshold);
		this.subDoelstellingen = new ArrayList<>();

	}
	
	protected DoelstellingComposite() {
		
	}
	
	public void addSubDoelstelling(ADoelstelling d) {
		subDoelstellingen.add(d);
	}
	
	public List<ADoelstelling> getSubDoelstelling(){
		return subDoelstellingen.stream().map(d -> (ADoelstelling) d).collect(Collectors.toList());
	}
	
	public void removeDoelstelling(ADoelstelling doel) {
		subDoelstellingen.remove(doel);
	}
	
	public void setSubDoelstellingen(List<ADoelstelling> doel) {
		this.subDoelstellingen = doel;
	}
	public void setSubDoelstelling(ADoelstelling doel) {
		subDoelstellingen.add(doel);
	}
	
	@Override
	public String toString() {
		return super.toString() + "Doelstelling [subDoelstellingen=" + subDoelstellingen + "]";
	}

	
}