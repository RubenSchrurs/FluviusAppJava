package domein;

import java.util.Collections;
import java.util.List;

public class BerekenMediaan implements BewerkingStrategy{
	private List<Double> lijst;
	private int aantalElementen;
	
	@Override
	public double voerBewerkingUit(List<Double> lijst) {
		this.lijst = lijst;
		Collections.sort(this.lijst);
		aantalElementen = lijst.size();
		if(aantalElementen%2 == 1) {
			return this.lijst.get(aantalElementen / 2);
		}
		else {
			double w1 = this.lijst.get(aantalElementen / 2);
			double w2 = this.lijst.get((aantalElementen / 2) - 1);
			return (w1 + w2) / 2;
		}
	}
	
	public String toString() {
		return "mediaanbewerking";
	}

}
