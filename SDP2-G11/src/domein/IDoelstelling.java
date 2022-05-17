package domein;

import java.util.List;

public interface IDoelstelling {
	public String getName();
	
	public Number getValue(BewerkingStrategy bewerking);
	
	public double getThreshold();
	
	public List<IDatasource> getDatasources();
	
	public ISDG getMainSDG();
	
	public ISDG getSubSDG();

}
