package domein;

import java.io.File;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;

@Entity
@NamedQueries({
	@NamedQuery(name = "Datasource.findByName", query = "SELECT d FROM Datasource d WHERE d.name = :name"),
})


public class Datasource implements Serializable, IDatasource
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int datasourceID;
	
	private String name;
	
	private double waarde;
	
	private String fileName;
	
	private int aantalKolommen;
	
	private String eenheidData;
	
	@Transient
	private Map<String, Double> gegevens;

	public Datasource(File file) throws Exception {
		this.gegevens = new HashMap<String, Double>();
		try {
			readFile(file);
		} 
		catch (Exception e) {
			throw e;
		}
	}
	
	public Datasource(String name, double waarde) throws Exception{
		try {
			setName(name);
			setWaarde(waarde);
		}
		catch (Exception e) {
			throw e;
		}
	}
	
	protected Datasource() {}
	
	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void readFile(File file) throws Exception {
		File csvFile = new File("Test.csv");
		if (file != null && file.getName().contains(".csv")) {
			csvFile = file;
			this.fileName = file.getName();
		}
		else {
			System.err.println("Geen geldig csv bestand gevonden");
			throw new Exception("Geen geldig csv bestand gevonden");
		}
		Scanner scanner = new Scanner(csvFile);
		if(scanner.hasNextLine()) {
			String eersteLijn = scanner.nextLine();
			String[] stringDelen = eersteLijn.split(";");
			if(stringDelen.length != 2) {
				throw new Exception("Ongeldig csv bestand");
			}
			this.name = stringDelen[0];
			this.aantalKolommen = Integer.parseInt(stringDelen[1]);
		}
		while(scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] regel = line.split(";");
			if(regel.length != aantalKolommen) {
				throw new Exception("Fout in csv bestand! Kolommen kloppen niet!");
			}
			if(this.eenheidData == null) {
				this.eenheidData = regel[aantalKolommen - 1];
			}
			else{
				if(!this.eenheidData.equals(regel[aantalKolommen - 1])) {
					throw new Exception("Fout in csv bestand! Eenheden komen  niet overeen");
				}
			}
			regel[1] = regel[1].replace(",", ".");
			this.gegevens.put(regel[0], Double.parseDouble(regel[1]));
		}
		scanner.close();
	}

	@Override
	public double getWaarde() {
		return waarde;
	}

	public void setWaarde(double waarde) {
		this.waarde = waarde;
	}

	@Override
	public String getFileName() {
		return fileName;
	}

	@Override
	public int getAantalKolommen() {
		return aantalKolommen;
	}

	@Override
	public String getEenheidData() {
		return eenheidData;
	}
	
	public void setEenheidData(String e) {
		this.eenheidData = e;
	}

	@Override
	public Map<String, Double> getGegevens() {
		return Collections.unmodifiableMap(gegevens);
	}

	@Override
	public String toString() {
		return "Datasource [name=" + name + "]";
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
		Datasource other = (Datasource) obj;
		return Objects.equals(name, other.name);
	}
	
}
