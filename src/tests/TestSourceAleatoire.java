package tests;

import sources.SourceAleatoire;
import information.Information;
import information.InformationNonConformeException;

public class TestSourceAleatoire {
    
    public static void main(String[] args) throws InformationNonConformeException {
        System.out.println("Test de la SourceAleatoire :");
        
        // Instanciation de SourceAleatoire
        SourceAleatoire source = new SourceAleatoire();
        source.emettre();
        
        // Récupération de l'information générée par la source
        Information<Boolean> info = source.getInformationEmise();

        // Affichage du message et de sa longueur
        System.out.println("Message généré par la SourceAleatoire : " + info.toString());
        System.out.println("Longueur du message : " + info.nbElements());

        // Vérification de la longueur du message
        if (info.nbElements() >= 10 && info.nbElements() <= 100) {
            System.out.println("Test réussi : Longueur du message correcte.");
        } else {
            System.out.println("Test échoué : Longueur du message incorrecte.");
        }
    }
}
