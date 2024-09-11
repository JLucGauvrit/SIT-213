package tests;

import transmetteurs.TransmetteurParfait;
import information.Information;
import information.InformationNonConformeException;

public class TestTransmetteurParfait {

    public static void main(String[] args) {
        System.out.println("Test du TransmetteurParfait :");

        try {
            // Crée des messages aléatoires et vérifie leur transmission parfaite
            TransmetteurParfait transmetteur = new TransmetteurParfait();
            Information<Boolean> infoRecue = new Information<>();
            infoRecue.add(true);
            infoRecue.add(false);
            infoRecue.add(true);

            transmetteur.recevoir(infoRecue);

            Information<Boolean> infoEmise = transmetteur.getInformationEmise();
            System.out.println("Message émis par le TransmetteurParfait : " + infoEmise.toString());

            if (infoRecue.equals(infoEmise)) {
                System.out.println("Test réussi : Le message émis est identique au message reçu.");
            } else {
                System.out.println("Test échoué : Le message émis est différent du message reçu.");
            }

        } catch (InformationNonConformeException e) {
            System.out.println("Test échoué : Exception lors de la transmission.");
            e.printStackTrace();
        }
    }
}
