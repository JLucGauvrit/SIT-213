package transmetteurs;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;

public class TransmetteurAnalogiqueParfait extends Transmetteur<Float, Float> {

	/**
     * Constructeur par défaut de la classe TransmetteurParfait.
     * Initialise les informations reçues et émises comme des informations vides.
     */
    public TransmetteurAnalogiqueParfait() {
        super();
        informationRecue = new Information<>();
        informationEmise = new Information<>();
    }

    /**
     * Méthode permettant de recevoir une information booléenne en entrée et de la stocker
     * pour émission ultérieure.
     *
     * @param information L'information booléenne reçue.
     * @throws InformationNonConformeException Si l'information reçue est non conforme.
     */
    @Override
    public void recevoir(Information<Float> information) throws InformationNonConformeException {
        for (Float i : information) {
            informationRecue.add(i);
        }
        emettre();
    }

    /**
     * Méthode permettant d'émettre l'information stockée vers toutes les destinations connectées.
     *
     * @throws InformationNonConformeException Si l'information émise est non conforme.
     */
    @Override
    public void emettre() throws InformationNonConformeException {
        for (DestinationInterface<Float> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(informationRecue);
        }
        this.informationEmise = informationRecue;
    }
}
