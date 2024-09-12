package transmetteurs;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;

public class Recepteur extends Transmetteur<Float, Boolean> {
	protected Information<Float> informationRecue = null;
	protected Information<Boolean> informationEmise = null;
	
	public Recepteur(int N) {
		super();
		
		
		
	}

	@Override
	public void recevoir(Information<Float> information) throws InformationNonConformeException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void emettre() throws InformationNonConformeException {
		for (DestinationInterface<Boolean> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(informationRecue);
        }
        this.informationEmise = informationRecue;
		
	}
}
