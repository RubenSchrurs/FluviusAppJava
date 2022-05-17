package domein;

import java.util.List;

public class BerekenGemiddelde implements BewerkingStrategy{
	private double tussenResultaat;
	private int aantalElementen;

	@Override
	public double voerBewerkingUit(List<Double> lijst) {
		aantalElementen = lijst.size();
		lijst.stream().forEach(e -> tussenResultaat += e);
		return tussenResultaat/aantalElementen;
	}
	
	public String toString() {
		return "gemiddeldebewerking";
	}

}
