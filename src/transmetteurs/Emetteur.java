package transmetteurs;

import sources.*;
import destinations.*;
import transmetteurs.*;
import visualisations.*;
import information.*;

public class Emetteur extends Transmetteur<Boolean,Float> {

	public Emetteur() {
		 super();
	}
	
	public Emetteur(int Amax, int Amin, int echantillon) {
		
		
	}

	@Override
	public void recevoir(Information<Boolean> information) throws InformationNonConformeException {
		for (Boolean i : information) {
            informationRecue.add(i);
        }
        modulation();
        emettre();
	}

	public void modulation() throws InformationNonConformeException{
		
		
	}
	
	@Override
	public void emettre() throws InformationNonConformeException {
		// TODO Auto-generated method stub
		
	}

	
	

}
