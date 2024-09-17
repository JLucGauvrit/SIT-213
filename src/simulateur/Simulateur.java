package simulateur;

import sources.*;
import destinations.*;
import emetteurs.*;
import transmetteurs.*;
import recepteur.*;
import visualisations.*;
import information.*;

/** La classe Simulateur permet de construire et simuler une chaîne de
 * transmission composée d'une Source, d'un nombre variable de
 * Transmetteur(s) et d'une Destination.
 * @author cousin
 * @author prou
 *
 */
public class Simulateur {
      	
    /** indique si le Simulateur utilise des sondes d'affichage */
    private boolean affichage = false;
    
    /** indique si le Simulateur utilise un message généré de manière aléatoire (message imposé sinon) */
    private boolean messageAleatoire = true;
    
    /** indique si le Simulateur utilise un germe pour initialiser les générateurs aléatoires */
    private boolean aleatoireAvecGerme = false;
    
    /** la valeur de la semence utilisée pour les générateurs aléatoires */
    private Integer seed = null; // pas de semence par défaut
    
    /** la longueur du message aléatoire à transmettre si un message n'est pas imposé */
    private int nbBitsMess = 100; 
    
    /** la chaîne de caractères correspondant à m dans l'argument -mess m */
    private String messageString = "100";
    
    private Boolean analogique = false;
   
   	
    /** le  composant Source de la chaine de transmission */
    private Source <Boolean>  source = null;
    
    /** le  composant Destination de la chaine de transmission */
    private Destination <Boolean>  destination = null;
   	
   
    /** Le constructeur de Simulateur construit une chaîne de
     * transmission composée d'une Source <Boolean>, d'une Destination
     * <Boolean> et de Transmetteur(s) [voir la méthode
     * analyseArguments]...  <br> Les différents composants de la
     * chaîne de transmission (Source, Transmetteur(s), Destination,
     * Sonde(s) de visualisation) sont créés et connectés.
     * @param args le tableau des différents arguments.
     *
     * @throws ArgumentsException si un des arguments est incorrect
     * @throws InformationNonConformeException 
     *
     */   
    public  Simulateur(String [] args) throws ArgumentsException, InformationNonConformeException {
    	// analyser et récupérer les arguments   	
    	analyseArguments(args);
      


     // Création des composants de la chaîne
    	// Création d'une source aléatoire ou pas
    	if(messageAleatoire) {source = new SourceAleatoire(nbBitsMess);} 
    	else if(aleatoireAvecGerme){source = new SourceAleatoire(nbBitsMess, seed);}
    	else {source = new SourceFixe(messageString);}
    	
    	// Crée une destination pour recevoir l'information émise par le transmetteur elle ne change jamais
        destination = new DestinationFinale();
        
        
        
        //choix du type de simulateur en fonction de l'avancement du projet
        if (!(analogique)) {simulateurParfait();}
        else simulateurAnalogiqueParfait();
    	}
    
    
        
        private void simulateurParfait(){
        	// cas d'une transmition parfaite en binaire
        	
        	// Instanciation du TransmetteurParfait (sans génériques ici)
            Transmetteur <Boolean, Boolean>  transmetteurLogique = null;
            transmetteurLogique = new TransmetteurParfait();
            
            // Connecter la source au transmetteur
            source.connecter(transmetteurLogique);

            // Connecter le transmetteur à la destination
            transmetteurLogique.connecter(destination); 

            
            if(affichage) {
            	source.connecter(new SondeLogique("Source", 200));
            	transmetteurLogique.connecter(new SondeLogique("Transmetteur", 200));
            }
        }
        
        private void simulateurAnalogiqueParfait() throws InformationNonConformeException{
        	// cas d'une transmition parfaite en float, analogique
        	Emetteur<Boolean,Float> emetteurParfait  = null;
        	Transmetteur <Float, Float>  transmetteurAnalogiqueParfait = null;
        	Recepteur<Float,Boolean> recepteurParfait = null ;
        	
        	emetteurParfait = new EmetteurParfait(5f, 0, 3, "NRZ");
        	transmetteurAnalogiqueParfait = new TransmetteurAnalogiqueParfait();
        	recepteurParfait = new RecepteurParfait(5f, 0, 3, "NRZ");
        	
        	source.connecter(emetteurParfait);
        	emetteurParfait.connecter(transmetteurAnalogiqueParfait);
        	transmetteurAnalogiqueParfait.connecter(recepteurParfait);
        	recepteurParfait.connecter(destination);
        	
        	if(affichage) {
            	source.connecter(new SondeLogique("Source", 200));
            	emetteurParfait.connecter(new SondeAnalogique("Emetteur"));
            	transmetteurAnalogiqueParfait.connecter(new SondeAnalogique("Transmetteur"));
            	recepteurParfait.connecter(new SondeLogique("Recepteur", 200));

            }    
        }
 
   
   
   
    /** La méthode analyseArguments extrait d'un tableau de chaînes de
     * caractères les différentes options de la simulation.  <br>Elle met
     * à jour les attributs correspondants du Simulateur.
     *
     * @param args le tableau des différents arguments.
     * <br>
     * <br>Les arguments autorisés sont : 
     * <br> 
     * <dl>
     * <dt> -mess m  </dt><dd> m (String) constitué de 7 ou plus digits à 0 | 1, le message à transmettre</dd>
     * <dt> -mess m  </dt><dd> m (int) constitué de 1 à 6 digits, le nombre de bits du message "aléatoire" à transmettre</dd> 
     * <dt> -s </dt><dd> pour demander l'utilisation des sondes d'affichage</dd>
     * <dt> -seed v </dt><dd> v (int) d'initialisation pour les générateurs aléatoires</dd> 
     * </dl>
     *
     * @throws ArgumentsException si un des arguments est incorrect.
     *
     */   
    private  void analyseArguments(String[] args)  throws  ArgumentsException {

    	for (int i=0;i<args.length;i++){ // traiter les arguments 1 par 1

    		if (args[i].matches("-s")){
    			affichage = true;
    		}
    		
    		else if (args[i].matches("-seed")) {
    			aleatoireAvecGerme = true;
    			i++; 
    			// traiter la valeur associee
    			try { 
    				seed = Integer.valueOf(args[i]);
    			}
    			catch (Exception e) {
    				throw new ArgumentsException("Valeur du parametre -seed  invalide :" + args[i]);
    			}           		
    		}

    		else if (args[i].matches("-mess")){
    			i++; 
    			// traiter la valeur associee
    			messageString = args[i];
    			if (args[i].matches("[0,1]{7,}")) { // au moins 7 digits
    				messageAleatoire = false;
    				nbBitsMess = args[i].length();
    			} 
    			else if (args[i].matches("[0-9]{1,6}")) { // de 1 à 6 chiffres
    				messageAleatoire = true;
    				nbBitsMess = Integer.valueOf(args[i]);
    				if (nbBitsMess < 1) 
    					throw new ArgumentsException ("Valeur du parametre -mess invalide : " + nbBitsMess);
    			}
    			else 
    				throw new ArgumentsException("Valeur du parametre -mess invalide : " + args[i]);
    		}
    		
    			else if (args[i].matches("-analog")){
    				analogique = true;
    			}

    		else throw new ArgumentsException("Option invalide :"+ args[i]);
    	}
      
    }
     
    
   	
    /** La méthode execute effectue un envoi de message par la source
     * de la chaîne de transmission du Simulateur.
     *
     * @throws Exception si un problème survient lors de l'exécution
     *
     */ 
    public void execute() throws Exception {      
         
    	// TODO : typiquement source.emettre(); 
    	source.emettre();
    	
    }
   
   	   	
   	
    /** La méthode qui calcule le taux d'erreur binaire en comparant
     * les bits du message émis avec ceux du message reçu.
     *
     * @return  La valeur du Taux dErreur Binaire.
     */   	   
    public float  calculTauxErreurBinaire() {
    	// Compare les informations émises et reçues
        Information<Boolean> emise = source.getInformationEmise();
        Information<Boolean> recue = destination.getInformationRecue();

        int erreurs = 0;
        for (int i = 0; i < emise.nbElements(); i++) {
            if (!emise.iemeElement(i).equals(recue.iemeElement(i))) {
                erreurs++;
            }
        }
        return (float) erreurs / emise.nbElements();
    }
   
   
   
   
    /** La fonction main instancie un Simulateur à l'aide des
     *  arguments paramètres et affiche le résultat de l'exécution
     *  d'une transmission.
     *  @param args les différents arguments qui serviront à l'instanciation du Simulateur.
     */
    public static void main(String [] args) { 

    	Simulateur simulateur = null;

    	try {
    		simulateur = new Simulateur(args);
    	}
    	catch (Exception e) {
    		System.out.println(e); 
    		System.exit(-1);
    	} 

    	try {
    		simulateur.execute();
    		String s = "java  Simulateur  ";
    		for (int i = 0; i < args.length; i++) { //copier tous les paramètres de simulation
    			s += args[i] + "  ";
    		}
    		System.out.println(s + "  =>   TEB : " + simulateur.calculTauxErreurBinaire());
    	}
    	catch (Exception e) {
    		System.out.println(e);
    		e.printStackTrace();
    		System.exit(-2);
    	}              	
    }
}

