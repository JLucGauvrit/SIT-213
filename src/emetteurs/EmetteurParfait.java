package emetteurs;

import sources.*;
import destinations.*;
import transmetteurs.*;
import visualisations.*;
import information.*;


public class EmetteurParfait extends Emetteur<Boolean,Float> {
	
		private String modulation = "NRZ";
		
		private int fct_echantillon = 3;
		
		

		
		public EmetteurParfait(int Amax, int Amin, int echantillon,String fct_modulation) {
			super();
			informationRecue = new Information<>();
	        informationEmise = new Information<>();
			echantillon = this.fct_echantillon;
			fct_modulation = this.modulation;
			
		}

		@Override
		public void recevoir(Information<Boolean> information) throws InformationNonConformeException {
			for (Boolean i : information) {
	            informationRecue.add(i);
	        }
	        modulation();
	        emettre();
		}

		public void modulation() throws InformationNonConformeException{
			if (modulation == "RZ") {
				
				
			}
			if (modulation == "NRZ") {
				
				
			}
			if (modulation == "NRZT") {
				
				
			}
			
		}
		
		@Override
		public void emettre() throws InformationNonConformeException {
		
			
		}
	}

