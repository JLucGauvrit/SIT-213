package destinations;

import information.Information;
import information.InformationNonConformeException;

public class DestinationFinale extends Destination <Boolean> {

	public DestinationFinale() {
		super();
		informationRecue = new Information<Boolean>();
	}
	

	@Override
	public void recevoir(Information<Boolean> information) throws InformationNonConformeException {

		for (Boolean i : information) {
			informationRecue.add(i);
		}

	}

}
