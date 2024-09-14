package recepteur;

import destinations.DestinationInterface;
import information.*;

public class RecepteurParfait extends Recepteur<Float, Boolean> {
	private int facteurDEchantillonnage = 3;
	private float Amax;
	private float Amin;
	private String modulation = "RZ";
	
	public RecepteurParfait(int facteurDEchantillonnage, String modulation) {
		super();
		informationRecue = new Information<>();
        informationEmise = new Information<>();
        
        this.facteurDEchantillonnage = facteurDEchantillonnage;
        this.modulation = modulation;
        
        if(modulation.equals("NRZT") || modulation.equals("NRZ")) {
        	this.Amax = 5;
        	this.Amin = -5;
        }
        
        if(modulation.equals("RZ")) {
        	this.Amax = 5;
        	this.Amin = 0;
        }
	}
	
	public RecepteurParfait(int facteurDEchantillonnage, String modulation, float Amax, float Amin) {
		super();
		informationRecue = new Information<>();
        informationEmise = new Information<>();
        
        this.facteurDEchantillonnage = facteurDEchantillonnage;
        this.modulation = modulation;
        
        this.Amax = Amax;
        this.Amin = Amin;
	}

	public void demodulation() throws InformationNonConformeException {
		int nbElementRecue = informationRecue.nbElements();
		
		if (modulation.equals("RZ")) {
			// Démodulation RZ : échantillonnage au milieu de chaque intervalle
		    for (int i = 0; i < nbElementRecue; i += facteurDEchantillonnage) {
		        // Calcul de l'indice au milieu de chaque intervalle
		        int milieu = i + facteurDEchantillonnage / 2;

		        // Vérification que l'indice est bien dans les limites de informationRecue
		        if (milieu < nbElementRecue) {
		            // Si la valeur au milieu de l'intervalle est >= seuil, on ajoute true
		            if (informationRecue.iemeElement(milieu) >= (Amax + Amin) / 2) {
		                informationEmise.add(true);
		            } else {
		                informationEmise.add(false);
		            }
		        } else {
		            // Si l'indice dépasse nbElementRecue, on arrête la boucle
		            break;
		        }
		    }
			
		}
		
		if (modulation.equals("NRZ")) {
			int nbElementEmis = nbElementRecue;
			
			for(int i=0 ; i < nbElementEmis ; i++) {
				if(informationRecue.iemeElement(i) >= Amax) {
					informationEmise.add(true);
				}
				else if(informationRecue.iemeElement(i) <= Amin){
					informationEmise.add(false);
				}
				else {
		            // Si la valeur reçue est incohérente, on lance une exception
		            throw new InformationNonConformeException("Valeur incohérente dans informationRecue");
		        }
			}
			
		}
		
		if (modulation.equals("NRZT")) {
			int nbElementEmis = nbElementRecue / facteurDEchantillonnage;

		    // Démodulation NRZT : Parcourir les éléments par groupe de facteurDEchantillonnage
		    for (int i = 0; i < nbElementRecue; i += facteurDEchantillonnage) {
		        boolean atLeastOneAmax = false;
		        boolean atLeastOneAmin = false;
		        
		        // Parcourir les éléments de l'intervalle [i, i + facteurDEchantillonnage)
		        for (int j = 0; j < facteurDEchantillonnage && (i + j) < nbElementRecue; j++) {
		            float valeur = informationRecue.iemeElement(i + j);
		            
		            // Vérifier si une valeur atteint Amax ou Amin
		            if (valeur >= Amax) {
		                atLeastOneAmax = true;
		            }
		            if (valeur <= Amin) {
		                atLeastOneAmin = true;
		            }
		        }

		        // Décider de la valeur à ajouter à informationEmise
		        if (atLeastOneAmax) {
		            informationEmise.add(true);  // Ajout de true si au moins une valeur atteint Amax
		        } else if (atLeastOneAmin) {
		            informationEmise.add(false);  // Ajout de false si aucune valeur n'atteint Amax mais au moins une atteint Amin
		        } else {
		            // Gérer le cas où aucune valeur n'atteint ni Amax ni Amin (optionnel selon la logique attendue)
		            informationEmise.add(false);  // On peut ajouter false par défaut
		        }
		    }
	    }
		
	}
	
	
	
	@Override
	public void recevoir(Information<Float> information) throws InformationNonConformeException {
		for (Float i : information) {
            informationRecue.add(i);
        }
		
		demodulation();
        emettre();
	}

	@Override
	public void emettre() throws InformationNonConformeException {
		for (DestinationInterface<Boolean> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(informationEmise);
        }		
	}

}
