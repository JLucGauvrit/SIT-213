package tests;

import destinations.DestinationFinale;
import information.Information;
import information.InformationNonConformeException;

public class TestDestinationFinale {

    public static void main(String[] args) {
        System.out.println("Test de la DestinationFinale :");

        try {
            // Crée une destination et lui envoie une information
            DestinationFinale destination = new DestinationFinale();
            Information<Boolean> infoRecue = new Information<>();
            infoRecue.add(true);
            infoRecue.add(false);
            infoRecue.add(true);

            destination.recevoir(infoRecue);
            Information<Boolean> infoDestinee = destination.getInformationRecue();

            System.out.println("Message reçu par la DestinationFinale : " + infoDestinee.toString());

            if (infoRecue.equals(infoDestinee)) {
                System.out.println("Test réussi : Le message reçu est identique au message envoyé.");
            } else {
                System.out.println("Test échoué : Le message reçu est différent du message envoyé.");
            }

        } catch (InformationNonConformeException e) {
            System.out.println("Test échoué : Exception lors de la réception.");
            e.printStackTrace();
        }
    }
}
