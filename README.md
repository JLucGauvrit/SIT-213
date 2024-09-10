# TransmissionProjetTP1

## Description
**TransmissionProjetTP1** est un projet qui simule une chaîne de transmission d'informations. Il inclut un simulateur qui permet de générer des messages, de les transmettre via des transmetteurs parfaits et d'utiliser des sondes pour visualiser le processus. Le projet inclut également un ensemble de scripts de déploiement, de compilation, et de génération de documentation.

## Installation

Pour installer et exécuter le projet, suivez les étapes ci-dessous :

1. Cloner le dépôt Git :
    ```bash
    git clone https://gitlab-df.imt-atlantique.fr/f23belie/transmissionprojettp1.git
    ```

2. Se déplacer dans le répertoire du projet :
    ```bash
    cd transmissionprojettp1
    ```

3. Exécuter le script de déploiement pour extraire et préparer l'environnement :
    ```bash
    ./deploiement.sh <nom_archive_sans_tar_gz>
    ```

4. Compiler le projet :
    ```bash
    ./compile
    ```

5. Générer la documentation :
    ```bash
    ./genDoc
    ```

## Usage

### Simulateur
Pour exécuter le simulateur avec différentes options, utilisez le script `simulateur` :
```bash
./simulateur [options]
```
Options disponibles :
- `-mess m` : Précise le message à transmettre (soit une chaîne binaire de longueur ≥7, soit un entier pour spécifier la longueur d'un message généré aléatoirement).
- `-s` : Active l'utilisation des sondes.
- `-seed v` : Spécifie une valeur de seed pour les générateurs aléatoires.
- `-help` : Affiche l'aide.

Exemple :
```bash
./simulateur -mess 1010101 -s -seed 12345
```

### Exécution des tests
Pour exécuter les tests du projet, utilisez le script `runTests` :
```bash
./runTests
```

## Fonctionnalités
- Génération de messages aléatoires ou prédéfinis.
- Transmission via des transmetteurs parfaits.
- Utilisation de sondes pour visualiser les étapes de la transmission.
- Déploiement, compilation, nettoyage et génération de documentation automatisés.

## Arborescence du Projet
- **src/** : Contient le code source Java.
- **bin/** : Dossier pour les fichiers compilés.
- **docs/** : Documentation générée (Javadoc).
- **scripts/** : Scripts d'automatisation (`simulateur`, `compile`, `cleanAll`, etc.).

## Contribution

Si vous souhaitez contribuer à ce projet :
1. Forkez le projet.
2. Créez une branche pour votre fonctionnalité (`git checkout -b feature/NouvelleFonctionnalite`).
3. Effectuez vos modifications et tests.
4. Soumettez une pull request sur GitLab.

## Auteurs

Projet initialement développé par Jean-Luc Gauvrit et Fabien Béliers dans le cadre d'un projet à l'IMT Atlantique.

## Licence

Ce projet est sous licence MIT. Pour plus d'informations, consultez le fichier `LICENSE`.
