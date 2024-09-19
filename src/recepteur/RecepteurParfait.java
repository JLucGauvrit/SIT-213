package recepteur;

import destinations.DestinationInterface;
import information.*;

/**
 * La classe RecepteurParfait est un récepteur parfait qui reçoit des informations
 * modulées et les démodule en fonction du type de modulation (RZ, NRZ, NRZT).
 * Les échantillons reçus sont ensuite convertis en bits booléens (true ou false).
 *
 * @author 
 * @version 
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
    
    
    private int nbElementRecue;
    
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
    }

    /**
     * Méthode pour démoduler les signaux en utilisant la modulation NRZT.
     * 
     * @throws InformationNonConformeException si une erreur survient lors de la démodulation
     */
    public void demodulerNRZT() throws InformationNonConformeException {
    	for(int i=0 ; i < informationRecue.nbElements() ; i+=facteurDEchantillonnage) {
        	if(informationRecue.iemeElement(i+1) >= Amax) {
        		informationEmise.add(true);
        	}
        	else if(informationRecue.iemeElement(i+1) <= Amin){
        		informationEmise.add(false);
        	}
        }
    }

    /**
     * Méthode pour démoduler les signaux en utilisant la modulation NRZ.
     * 
     * @throws InformationNonConformeException si une valeur incohérente est détectée dans les informations reçues
     */
    public void demodulerNRZ() throws InformationNonConformeException {
        int nbElementEmis = nbElementRecue;
        
        for (int i = 0; i < nbElementEmis; i++) {
            if (informationRecue.iemeElement(i) >= Amax) {
                informationEmise.add(true);
            } else if (informationRecue.iemeElement(i) <= Amin) {
                informationEmise.add(false);
            } else {
                // Si la valeur reçue est incohérente, on lance une exception
                throw new InformationNonConformeException("Valeur incohérente dans informationRecue");
            }
        }
    }

    /**
     * Méthode pour démoduler les signaux en utilisant la modulation RZ.
     * 
     * @throws InformationNonConformeException si une erreur survient lors de la démodulation
     */
    public void demodulerRZ() throws InformationNonConformeException {
        for(int i=0 ; i < informationRecue.nbElements() ; i+=facteurDEchantillonnage) {
        	if(informationRecue.iemeElement(i+1) >= Amax) {
        		informationEmise.add(true);
        	}
        	else if(informationRecue.iemeElement(i+1) <= Amin){
        		informationEmise.add(false);
        	}
        }
    }
    
    
    public void demodulation() throws InformationNonConformeException{
    	this.nbElementRecue = informationRecue.nbElements(); // le this. n'est pas très utile ici
    	
    	if(nbElementRecue == 0) {
    		throw new InformationNonConformeException("L'information ne peut pas être vide");
    	}
    	
    	switch (modulation) {
        case "RZ" :
        	demodulerRZ();
        	break;
        	
        case "NRZ" :
        	demodulerNRZ();
        	break;
        	
        case "NRZT" :
        	demodulerNRZT();
        	break;
    }
    }
    
    
    /**
     * Reçoit les informations modulées, effectue la démodulation en fonction
     * du type de modulation choisi, et émet l'information démoulée.
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
