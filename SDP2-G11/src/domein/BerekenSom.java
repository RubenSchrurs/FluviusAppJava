package domein;

import java.util.List;

public class BerekenSom implements BewerkingStrategy{
	private double tussenResultaat;
	
	@Override
	public double voerBewerkingUit(List<Double> lijst) {
		lijst.stream().forEach(e -> tussenResultaat += e);
		return tussenResultaat;
	}
	
	public String toString() {
		return "sombewerking";
	}

}
