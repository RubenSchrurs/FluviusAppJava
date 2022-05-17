package domein;

import java.util.Map;

public interface IDatasource {

	String getName();

	double getWaarde();

	String getFileName();

	int getAantalKolommen();

	String getEenheidData();

	Map<String, Double> getGegevens();

}