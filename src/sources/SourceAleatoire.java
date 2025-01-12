package sources;

import information.Information;
import java.util.Random;

public class SourceAleatoire extends Source<Boolean> {

    /**
     * Une source qui génère un message aléatoire de booleans,
     * dont la longueur est comprise entre 10 et 100.
     */
    public SourceAleatoire(int nbBitsMess) {
        Random random = new Random();
        
        // Générer une longueur aléatoire entre 10 et 100
        int longueur = nbBitsMess;  // nextInt(91) génère un nombre entre 0 et 90

        informationGeneree = new Information<Boolean>();
        
        // Remplir l'information avec des valeurs aléatoires
        for (int i = 0; i < longueur; i++) {
            informationGeneree.add(random.nextBoolean());
        }
    }
    
    public SourceAleatoire(int nbBitsMess, long germe) {
        Random random = new Random(germe);
        
        // Générer une longueur aléatoire entre 10 et 100
        int longueur = nbBitsMess;  // nextInt(91) génère un nombre entre 0 et 90

        informationGeneree = new Information<Boolean>();
        
        // Remplir l'information avec des valeurs aléatoires
        for (int i = 0; i < longueur; i++) {
            informationGeneree.add(random.nextBoolean());
        }
    }

}
