package tests;  // Déclaration du package

import sources.SourceAleatoire;
import information.Information;
import information.InformationNonConformeException;

public class TestSourceAleatoire {

    public static void main(String[] args) throws InformationNonConformeException {
        System.out.println("Test de la SourceAleatoire :");
        
        // Instanciation de SourceAleatoire avec une longueur de 10 (par exemple)
        int longueurMessage = 10;
        SourceAleatoire source = new SourceAleatoire(longueurMessage); // Assurez-vous que ce constructeur existe et fonctionne
        
        // Récupération de l'information générée par la source
        Information<Boolean> info = source.getInformationEmise(); // Assurez-vous que cette méthode existe

        // Affichage du message et de sa longueur
        System.out.println("Message généré par la SourceAleatoire : " + info.toString());
        System.out.println("Longueur du message : " + info.nbElements());

        // Vérification de la longueur du message
        if (info.nbElements() == longueurMessage) {
            System.out.println("Test réussi : Longueur du message correcte.");
        } else {
            System.out.println("Test échoué : Longueur du message incorrecte.");
        }
    }
}

