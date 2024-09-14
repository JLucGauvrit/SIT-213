package recepteur;

import information.Information;
import information.InformationNonConformeException;

public class RecepteurParfait extends Recepteur<Float, Boolean> {
	private int facteurDEchantillonnage = 3;
	private int Amax = 5;
	private int Amin = 0;
	private String modulation = "RZ";
	
	public RecepteurParfait(int Amax, int Amin, int facteurDEchantillonnage, String modulation) {
		super();
		informationRecue = new Information<>();
        informationEmise = new Information<>();
        
        facteurDEchantillonnage = this.facteurDEchantillonnage;
        Amax = this.Amax;
        Amin = this.Amin;
        modulation = this.modulation;
	}

	public void demodulation(Information<Float> information, String modulation) {
		
	}
	
	
	
	@Override
	public void recevoir(Information<Float> information) throws InformationNonConformeException {
		for (Float i : information) {
            informationRecue.add(i);
        }
		
	}

	@Override
	public void emettre() throws InformationNonConformeException {
		
		
	}

}
