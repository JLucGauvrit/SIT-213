package sources;

import information.Information;

public class SourceFixe extends Source<Boolean> {
    
    /**
     * Une source qui génère des informations en fonction d'une chaîne binaire donnée.
     * @param messageString La chaîne de caractères binaire à parcourir (exemple : "1001").
     */
    public SourceFixe(String messageString) {
        informationGeneree = new Information<Boolean>();
        
        // Parcourir la chaîne et ajouter true pour '1', false pour '0'
        for (int i = 0; i < messageString.length(); i++) {
            char c = messageString.charAt(i);
            if (c == '1') {
                informationGeneree.add(true);
            } else if (c == '0') {
                informationGeneree.add(false);
            } else {
                // Si le caractère n'est ni '0' ni '1', lever une exception ou gérer le cas
                throw new IllegalArgumentException("La chaîne doit contenir uniquement des '0' ou '1'.");
            }
        }
    }
}
