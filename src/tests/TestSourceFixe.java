package tests;

import sources.SourceFixe;
import information.Information;
import information.InformationNonConformeException;

public class TestSourceFixe {

    public static void main(String[] args) {
        try {
            System.out.println("Test de la SourceFixe :");

            // Instancier SourceFixe avec une chaîne binaire
            SourceFixe source = new SourceFixe("1010101");
            source.emettre();  // Vérifiez que la méthode émet l'information correctement

            // Récupération de l'information émise
            Information<Boolean> info = source.getInformationEmise();

            // Affichage des résultats
            System.out.println("Message généré par la SourceFixe : " + info.toString());
            System.out.println("Longueur du message : " + info.nbElements());

            // Vérification de la longueur du message
            if (info.nbElements() == 7) {
                System.out.println("Test réussi : Longueur du message correcte.");
            } else {
                System.out.println("Test échoué : Longueur du message incorrecte.");
            }
        } catch (InformationNonConformeException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }
}
