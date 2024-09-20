package tests;

import information.Information;
import information.InformationNonConformeException;
import emetteurs.EmetteurParfait;

public class TestEmetteurParfait {
	
	private static int errorCompteur;
	private static int testCompteur;
	
	private static void InformationNonConformeException(int numeroConstructeur, int Amax, int Amin, int facteurDEchantillonage, String modulation, String testId, String errorMessage) {
		try {
			if (numeroConstructeur==1) {EmetteurParfait test = new EmetteurParfait(Amax, Amin,facteurDEchantillonage,modulation);}
			if (numeroConstructeur==2) {EmetteurParfait test = new EmetteurParfait(facteurDEchantillonage,modulation);}
			// Reaching this point means that no exception was thrown by EmetteurParfait()
			System.out.println("Err " + testId + " : " + errorMessage); // display the error message
			errorCompteur++;
		}
		catch (InformationNonConformeException e) {}
		catch (Exception e) {
			System.out.println("Err " + testId + " : unexpected exception. "+ e);
			e.printStackTrace(); 
			errorCompteur++;
		}
	}
	private static void displayError(String message) {
		System.out.println(message);
		errorCompteur++;
	}

    public static void main(String[] args) {
        System.out.println("Test de la EmetteurParfait");

        try {
            // Crée un emetteur et lui envoie une information
        	System.out.println("Constructeurs de EmetteurParfait");
        	
        	EmetteurParfait emetteur = new EmetteurParfait(3,"NRZ");
        	if (emetteur.getAmax()!=5) displayError("Test 1.1 - l'amplitude max doit être de 5");
        	testCompteur++;
        	if (emetteur.getAmin()!=-5) displayError("Test 1.2 - l'amplitude min doit être de -5");
        	testCompteur++;
        	if (emetteur.getModulation()!="NRZ") displayError("Test 1.3 - la modulation doit être NRZ");
        	testCompteur++;
        	
        	emetteur = new EmetteurParfait(3,"RZ");
        	if (emetteur.getAmax()!=5) displayError("Test 1.4 - l'amplitude max doit être de 5");
        	testCompteur++;
        	if (emetteur.getAmin()!=0) displayError("Test 1.5 - l'amplitude min doit être de 0");
        	testCompteur++;
        	if (emetteur.getModulation()!="RZ") displayError("Test 1.6 - la modulation doit être RZ");
        	testCompteur++;
        	
        	emetteur = new EmetteurParfait(3,"NRZT");
        	if (emetteur.getAmax()!=5) displayError("Test 1.7 - l'amplitude max doit être de 5");
        	testCompteur++;
        	if (emetteur.getAmin()!=-5) displayError("Test 1.8 - l'amplitude min doit être de -5");
        	testCompteur++;
        	if (emetteur.getModulation()!="NRZT") displayError("Test 1.9 - la modulation doit être NRZT");
        	testCompteur++;
        	
        	InformationNonConformeException(2,0,0,3,"BDSM","1.10","Le constructeur accepte des modulations qui n'existe pas");
        	testCompteur++;
        	
        	emetteur = new EmetteurParfait(1,-1,3,"NRZ");
        	if (emetteur.getAmax()!=1) displayError("Test 1.11 - l'amplitude max doit être de 1");
        	testCompteur++;
        	if (emetteur.getAmin()!=-1) displayError("Test 1.12 - l'amplitude min doit être de -1");
        	testCompteur++;
        	if (emetteur.getModulation()!="NRZ") displayError("Test 1.13 - la modulation doit être NRZ");
        	testCompteur++;
        	
        	emetteur = new EmetteurParfait(1,-1,3,"RZ");
        	if (emetteur.getAmax()!=1) displayError("Test 1.14 - l'amplitude max doit être de 1");
        	testCompteur++;
        	if (emetteur.getAmin()!=0) displayError("Test 1.15 - l'amplitude min doit être de 0");
        	testCompteur++;
        	if (emetteur.getModulation()!="RZ") displayError("Test 1.16 - la modulation doit être RZ");
        	testCompteur++; 
        	
        	InformationNonConformeException(1,1,1,3,"NRZ","1.17","Amax doit être strictement supérieur à Amin");
        	testCompteur++;
        	InformationNonConformeException(1,5,1,3,"NRZ","1.18","Amin doit être inférieur ou égal à 0");
        	testCompteur++;
        	InformationNonConformeException(1,5,-5,3,"BDSM","1.19","Le constructeur accepte des modulations qui n'existe pas");
        	testCompteur++;
        	
            Information<Boolean> infoRecue = new Information<>();
            infoRecue.add(true);
            infoRecue.add(false);
            infoRecue.add(true);
            
            System.out.println("Bilan du test de EmetteurParfait : "+errorCompteur+"/"+testCompteur+" erreurs, taux de réussite: "+(testCompteur-errorCompteur)/testCompteur*100+"%");
            
            /*
            emetteur.recevoir(infoRecue);
            Information<Float> infoDestinee = emetteur.getInformationRecue();

            System.out.println("Message reçu par la DestinationFinale : " + infoDestinee.toString());

            if (infoRecue.equals(infoDestinee)) {
                System.out.println("Test réussi : Le message reçu est identique au message envoyé.");
            } else {
                System.out.println("Test échoué : Le message reçu est différent du message envoyé.");
            }*/

        } catch (InformationNonConformeException e) {
            System.out.println("Test échoué : Exception lors de la réception.");
            e.printStackTrace();
        }
    }
}

