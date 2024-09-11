package tests;

import sources.SourceFixe;
import information.Information;
import information.InformationNonConformeException;

public class TestSourceFixe {

    public static void main(String[] args) throws InformationNonConformeException {
        System.out.println("Test de la SourceFixe :");
        
        SourceFixe source = new SourceFixe();
        source.emettre();
        
        Information<Boolean> info = source.getInformationEmise();

        System.out.println("Message généré par la SourceFixe : " + info.toString());
        System.out.println("Longueur du message : " + info.nbElements());

        if (info.nbElements() == 7) {
            System.out.println("Test réussi : Longueur du message correcte.");
        } else {
            System.out.println("Test échoué : Longueur du message incorrecte.");
        }
    }
}
