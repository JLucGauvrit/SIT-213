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
    
    private Boolean form = false;
   
   	
    /** le  composant Source de la chaine de transmission */
    private Source <Boolean>  source = null;
    
    /** le  composant Destination de la chaine de transmission */
    private Destination <Boolean>  destination = null;
    
    private String modulation = "NRZ";
    
    private float Amin = -5;
    
    private float Amax = 5;
   	
    private int facteurDEchantillonnage = 3;
   
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
        if (!(form)) {simulateurParfait();}
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
        	
        	emetteurParfait = new EmetteurParfait(Amax, Amin, facteurDEchantillonnage, modulation);
        	transmetteurAnalogiqueParfait = new TransmetteurAnalogiqueParfait();
        	recepteurParfait = new RecepteurParfait(Amax, Amin, facteurDEchantillonnage, modulation);
        	
        	source.connecter(emetteurParfait);
        	emetteurParfait.connecter(transmetteurAnalogiqueParfait);
        	transmetteurAnalogiqueParfait.connecter(recepteurParfait);
        	recepteurParfait.connecter(destination);
        	
        	if(affichage) {
            	source.connecter(new SondeLogique("Source", 200));
            	emetteurParfait.connecter(new SondeAnalogique("Emetteur", 100));
            	transmetteurAnalogiqueParfait.connecter(new SondeAnalogique("Transmetteur", 100));
            	recepteurParfait.connecter(new SondeLogique("Recepteur", 200));

            }    
        }
 
   
   
   
        /**
         * Analyse les arguments de simulation et met à jour les attributs du simulateur.
         * 
         * Arguments supportés :
         * <ul>
         *   <li><b>-mess m</b> : Chaîne binaire (7+ caractères) ou entier (1-6 chiffres) pour définir le message.</li>
         *   <li><b>-s</b> : Active l'affichage des sondes.</li>
         *   <li><b>-seed v</b> : Initialise les générateurs aléatoires avec la valeur v.</li>
         *   <li><b>-mod m</b> : Mode de modulation (RZ, NRZ, NRZT).</li>
         *   <li><b>-analog</b> : Active le mode analogique.</li>
         * </ul>
         * 
         * @param args Les arguments de simulation.
         * @throws ArgumentsException Si un argument est incorrect.
         */

    private void analyseArguments(String[] args) throws ArgumentsException {

            for (int i = 0; i < args.length; i++) { // traiter les arguments un par un

                if (args[i].matches("-s")) {
                    affichage = true;
                }

                else if (args[i].matches("-seed")) {
                    aleatoireAvecGerme = true;
                    i++;
                    // traiter la valeur associée
                    try {
                        seed = Integer.valueOf(args[i]);
                    } catch (Exception e) {
                        throw new ArgumentsException("Valeur du parametre -seed invalide : " + args[i]);
                    }
                }

                else if (args[i].matches("-mess")) {
                    i++;
                    // traiter la valeur associée
                    messageString = args[i];
                    if (args[i].matches("[0,1]{7,}")) { // au moins 7 digits
                        messageAleatoire = false;
                        nbBitsMess = args[i].length();
                    } else if (args[i].matches("[0-9]{1,6}")) { // de 1 à 6 chiffres
                        messageAleatoire = true;
                        nbBitsMess = Integer.valueOf(args[i]);
                        if (nbBitsMess < 1)
                            throw new ArgumentsException("Valeur du parametre -mess invalide : " + nbBitsMess);
                    } else
                        throw new ArgumentsException("Valeur du parametre -mess invalide : " + args[i]);
                }


                else if (args[i].matches("-form")) {
                	form = true;
                    i++;
                    // Vérifier si le mode de modulation est valide
                    if (args[i].equals("RZ") || args[i].equals("NRZ") || args[i].equals("NRZT")) {
                        modulation = args[i];
                    } else {
                        throw new ArgumentsException("Option de modulation invalide : " + args[i]);
                    }
                }

                else throw new ArgumentsException("Option invalide : " + args[i]);
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
            if (!(emise.iemeElement(i)).equals(recue.iemeElement(i))) {
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


