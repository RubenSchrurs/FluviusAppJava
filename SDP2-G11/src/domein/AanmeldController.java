package domein;

import java.util.HashMap;

import javax.persistence.EntityNotFoundException;

public class AanmeldController 
{
	private Fluvius fl;
	private int poging;
	//Map bijhouden zodat elke gebruiker 3 aanmeldpogingen heeft
	private HashMap<String, Integer> aanmeldPogingen;
	private final int aantalPogingen = 3;

	public AanmeldController() 
	{
		fl = new Fluvius();
		aanmeldPogingen = new HashMap<String, Integer>();
		poging = 3;
	}

	public User meldAan(String username, String password) throws IllegalStateException
	{
		User user = null;
		if (aanmeldPogingen.containsKey(username)) {
			poging = aanmeldPogingen.get(username);
		}
		else {
			aanmeldPogingen.put(username, aantalPogingen);
		}
		if(poging > 0) {
			try {
				user = fl.getUserByName(username);
			}
			catch(EntityNotFoundException ex) {
				aanmeldPogingen.put(username, aanmeldPogingen.get(username) - 1);
				System.err.println("User not found");
			}
			if(user != null) {
				if(user.getIsBlocked()) {
					throw new IllegalStateException("User is blocked");
				}
				if(user.getPassword().equals(password)) {
					aanmeldPogingen.put(username, aantalPogingen);
					return user;
				}
				else {
					aanmeldPogingen.put(username, aanmeldPogingen.get(username) - 1);
				}
			}
		}
		
		return user;
	}
	
	public int getPoging(String name) {
		return aanmeldPogingen.get(name);
	}
	
}