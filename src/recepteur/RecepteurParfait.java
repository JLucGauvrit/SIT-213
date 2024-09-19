package recepteur;

import destinations.DestinationInterface;
import information.*;

/**
 * La classe RecepteurParfait est un récepteur parfait qui reçoit des informations
 * modulées et les démodule en fonction du type de modulation (RZ, NRZ, NRZT).
 * Les échantillons reçus sont ensuite convertis en bits booléens (true ou false).
 */
public class RecepteurParfait extends Recepteur<Float, Boolean> {

    /**
     * Le facteur d'échantillonnage utilisé pour la démodulation.
     */
    private int facteurDEchantillonnage = 3;
    
    /**
     * La valeur maximale d'amplitude des signaux reçus.
     */
    private float Amax;
    
    /**
     * La valeur minimale d'amplitude des signaux reçus.
     */
    private float Amin;
    
    /**
     * Le type de modulation utilisé (RZ, NRZ, NRZT).
     */
    private String modulation = "NRZ";
    
    private float esperance;
    
    /**
     * Constructeur du RecepteurParfait qui initialise les paramètres par défaut de Amax et Amin
     * en fonction de la modulation choisie.
     * 
     * @param facteurDEchantillonnage le facteur d'échantillonnage utilisé pour la démodulation
     * @param modulation le type de modulation utilisé (RZ, NRZ, NRZT)
     * @throws InformationNonConformeException si le type de modulation est erroné
     */
    public RecepteurParfait(int facteurDEchantillonnage, String modulation) throws InformationNonConformeException { 
        super();
        informationRecue = new Information<>();
        informationEmise = new Information<>();
        
        this.facteurDEchantillonnage = facteurDEchantillonnage;
        this.modulation = modulation;
        
        switch (modulation) {
        case "RZ" :
            this.Amin = 0;
            break;
            
        case "NRZ" :
        case "NRZT" :
            this.Amin = -5;
            break;
            
        default :
            throw new InformationNonConformeException("Information sur le type de modulation erronée");
        } 
        
        this.Amax = 5;
        this.esperance = (this.Amax + this.Amin) / 2;
    }
    
    /**
     * Constructeur du RecepteurParfait avec des valeurs personnalisées pour Amax et Amin.
     * 
     * @param Amax la valeur maximale de l'amplitude
     * @param Amin la valeur minimale de l'amplitude
     * @param facteurDEchantillonnage le facteur d'échantillonnage utilisé pour la démodulation
     * @param modulation le type de modulation utilisé (RZ, NRZ, NRZT)
     * @throws InformationNonConformeException si les valeurs de Amax, Amin ou la modulation sont invalides
     */
    public RecepteurParfait(float Amax, float Amin, int facteurDEchantillonnage, String modulation) throws InformationNonConformeException { 
        super();
        informationRecue = new Information<>();
        informationEmise = new Information<>();
        
        this.facteurDEchantillonnage = facteurDEchantillonnage;
        this.modulation = modulation;
        
        if (Amax < 0) {
            throw new InformationNonConformeException("Amax doit être supérieure à 0");
        }
        
        if (Amin >= Amax) {
            throw new InformationNonConformeException("Amax doit être strictement supérieur à Amin");
        }
        
        switch (modulation) {
        case "RZ" :
            this.Amin = 0;
            break;
            
        case "NRZ" :
        case "NRZT" :
            if (Amin > 0) {
                throw new InformationNonConformeException("Amin doit être inférieure à 0");
            }
            this.Amin = Amin;
            break;
            
        default :
            throw new InformationNonConformeException("Information sur le type de modulation erronée");
        } 
        
        this.Amax = Amax;
        this.esperance = (this.Amax + this.Amin) / 2;
    }
    
    
    
    
    /**
     * Reçoit les informations modulées, effectue la démodulation en fonction
     * du type de modulation choisi, et émet l'information démodulée.
     * 
     * @param information l'information reçue sous forme d'une liste de valeurs float
     * @throws InformationNonConformeException si les informations reçues sont incohérentes ou malformées
     */
    @Override
    public void recevoir(Information<Float> information) throws InformationNonConformeException {
        for (Float i : information) {
            informationRecue.add(i);
        }
        
        demodulation();
        emettre();
    }

    private void demodulation() throws InformationNonConformeException{
    	switch (modulation) {
		case "NRZ":
			toBoolean((Amax+Amin)/2);
			break;
			
		case "NRZT":
			toBoolean((Amax+Amin)/2);
			break;
			
		case "RZ":
			toBoolean((Amax)/4);
			break;

		default:
			throw new InformationNonConformeException("Aucun type d'encodage ne correspond a l'entrée saisie");
		}
		
	}

	private void toBoolean(float seuil) {
	    float somme = 0;
	    int nbEchantillons = facteurDEchantillonnage; // nombre d'échantillons par bit

	    // Boucle à travers l'information reçue, par pas de nbEchantillons
	    for (int i = 0; i < informationRecue.nbElements(); i += nbEchantillons) {
	        somme = 0;

	        // Calcul de la somme des échantillons pour chaque bit
	        for (int j = i; j < i + nbEchantillons && j < informationRecue.nbElements(); j++) {
	            somme += informationRecue.iemeElement(j);
	        }

	        // Calcul de la moyenne et ajout du résultat converti en booléen
	        float moyenne = somme / nbEchantillons;
	        informationEmise.add(moyenne > seuil);
	    }
		
	}

	/**
     * Émet l'information démodulée vers les destinations connectées.
     * 
     * @throws InformationNonConformeException si une erreur survient lors de l'émission
     */
    @Override
    public void emettre() throws InformationNonConformeException {
        for (DestinationInterface<Boolean> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(informationEmise);
        }        
    }
}
