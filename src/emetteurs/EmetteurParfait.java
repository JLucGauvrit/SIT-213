package emetteurs;

import destinations.*;
import information.*;

/**
 * La classe EmetteurParfait modélise un émetteur parfait qui reçoit une information binaire (Booléenne)
 * et la module en un signal analogique (Float) selon différentes techniques de modulation.
 * 
 * <p>
 * Trois types de modulation sont supportés : 
 * <ul>
 *   <li>NRZ (Non Return to Zero)</li>
 *   <li>RZ (Return to Zero)</li>
 *   <li>NRZT (Non Return to Zero en Ternaire)</li>
 * </ul>
 * </p>
 * 
 * <p>Le facteur d'échantillonnage détermine combien de valeurs sont émises par symbole binaire.</p>
 * 
 * <p>Les amplitudes Amax et Amin sont utilisées pour définir l'amplitude du signal analogique en sortie, 
 * en fonction du type de modulation choisi.</p>
 * 
 * @param <Boolean> Le type d'entrée binaire (Booléen) reçu par l'émetteur.
 * @param <Float> Le type de sortie analogique (Flottant) émis par l'émetteur après modulation.
 * 
 */
public class EmetteurParfait extends Emetteur<Boolean,Float> {

    private String modulation = "NRZ";       
    private float Amin;
    private float Amax;
    private int nbElementRecue;

    /**
     * Constructeur de la classe EmetteurParfait qui initialise les paramètres de modulation,
     * le facteur d'échantillonnage, et les amplitudes Amin et Amax.
     * 
     * @param Amax Amplitude maximale du signal en sortie.
     * @param Amin Amplitude minimale du signal en sortie.
     * @param modulation Le type de modulation utilisé (NRZ, NRZT, ou RZ).
     * @throws InformationNonConformeException Si les valeurs d'amplitude ou le type de modulation sont invalides.
     */
    
    
    public EmetteurParfait(float Amax, float Amin, int facteurDEchantillonage, String modulation) throws InformationNonConformeException {
        super();
        informationRecue = new Information<Boolean>();
        informationEmise = new Information<Float>();

        this.modulation = modulation;

        // Validation des amplitudes d'entrée en fonction de la modulation
        if (Amax < 0) {
            throw new InformationNonConformeException("Amax doit être supérieur ou égal à 0");
        }
        if (Amax <= Amin) {
            throw new InformationNonConformeException("Amax doit être strictement supérieur à Amin");
        }

        switch (modulation) {
            case "NRZT":
            case "NRZ":
                if (Amin > 0) {
                    throw new InformationNonConformeException("Amin doit être inférieur ou égal à 0");
                }
                this.Amin = Amin;
                break;

            case "RZ":
                this.Amin = 0;
                break;

            default:
                throw new InformationNonConformeException("Nom de modulation invalide");
        }

        this.Amax = Amax;
    }

    /**
     * Constructeur alternatif pour la classe EmetteurParfait avec des valeurs par défaut pour Amin et Amax.
     * 
     * @param facteurDEchantillonage Le nombre d'échantillons par symbole binaire.
     * @param modulation Le type de modulation utilisé (NRZ, NRZT, ou RZ).
     * @throws InformationNonConformeException Si le type de modulation est invalide.
     */
    public EmetteurParfait(int facteurDEchantillonage, String modulation) throws InformationNonConformeException {
        super();
        informationRecue = new Information<Boolean>();
        informationEmise = new Information<Float>();
        
        this.modulation = modulation;

        switch (modulation) {
            case "NRZT":
            case "NRZ":
                this.Amin = -5;
                break;

            case "RZ":
                this.Amin = 0;
                break;

            default:
                throw new InformationNonConformeException("Nom de modulation invalide");
        }

        this.Amax = 5;
    }

    /**
     * Reçoit une information binaire (Booléenne) en entrée et déclenche la modulation du signal.
     * 
     * @param information L'information binaire reçue par l'émetteur.
     * @throws InformationNonConformeException Si l'information reçue est non conforme.
     */
    @Override
    public void recevoir(Information<Boolean> information) throws InformationNonConformeException {
        for (Boolean i : information) {
            informationRecue.add(i);
        }
        modulation();
        emettre();
    }

    /**
     * Applique la modulation choisie (NRZ, RZ, NRZT) pour transformer l'information binaire en signal analogique.
     * 
     * @throws InformationNonConformeException Si un problème survient lors de la modulation.
     */
    public void modulation() throws InformationNonConformeException {
    	nbElementRecue=informationRecue.nbElements();
    	
    	if (nbElementRecue==0) {
    		throw new InformationNonConformeException("L'information ne peut pas être vide");
    	}
    	
    	switch (modulation) {
    	case "NRZT":
    		modulerNRZT();
    		break;
        case "NRZ":
        	modulerNRZ();
        	break;
        case "RZ":
        	modulerRZ();
        	break;
    	}
    }

    private void modulerNRZ() {
    	for(Boolean i : informationRecue) {
    		if(i.booleanValue()) {
    			informationEmise.add(Amax);
    		}
    		else {    
    			informationEmise.add(Amin);
    		}
    	}
	}

	private void modulerNRZT() {
		for(Boolean i : informationRecue) {
			if(i.booleanValue()) {
				informationEmise.add(Amax/2);
    			informationEmise.add(Amax);
    			informationEmise.add(Amax/2);
    		}
    		else {    
    			informationEmise.add(Amin/2);
    			informationEmise.add(Amin);
    			informationEmise.add(Amin/2);
    		}
		}
		
	}

	private void modulerRZ() {
		for(Boolean i : informationRecue) {
			if(i.booleanValue()) {
				informationEmise.add(Amin);
    			informationEmise.add(Amax);
    			informationEmise.add(Amin);
    		}
    		else {    
    			informationEmise.add(Amin);
    			informationEmise.add(Amin);
    			informationEmise.add(Amin);
    		}
		}
		
	}

	/**
     * Emet l'information modulée sous forme d'un signal analogique.
     * 
     * @throws InformationNonConformeException Si un problème survient lors de l'émission.
     */
    @Override
    public void emettre() throws InformationNonConformeException {
    	for (DestinationInterface<Float> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(informationEmise);
        }
    }
}
