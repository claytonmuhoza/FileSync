# Application de Synchronisation de Fichiers

Ce projet Java permet de synchroniser deux dossiers en utilisant un fichier de configuration XML appelé **profil**. Il est structuré autour de trois programmes indépendants, respectant les principes SOLID et utilisant des patrons de conception tels que **Abstract Factory**, **Builder**, **Composite** et **Visiteur**.

## Structure du Projet

L'application est composée de trois exécutables `.jar`, générés à l’aide de Maven :

- `new-profile.jar` : Création d’un profil de synchronisation
- `sync.jar` : Synchronisation des dossiers
- `syncstat.jar` : Affichage des informations d’un profil

## Compilation

Pour générer les exécutables, utilisez la commande suivante à la racine du projet :

```bash
mvn clean package
```

Les fichiers `.jar` seront générés dans le dossier `target/`.

## Utilisation

### 1. Création d’un profil

Crée un fichier XML `<nom_du_profil>.sync` contenant le nom du profil et les chemins des deux dossiers à synchroniser.

```bash
java -jar target/new-profile.jar <nom_du_profil> <dossier_1> <dossier_2>
```

**Exemple** :

```bash
java -jar target/new-profile.jar projetA /home/user/dossierA /mnt/backup/dossierA
```

---

### 2. Synchronisation

Compare les fichiers présents dans les deux dossiers, en utilisant les données du registre contenu dans le fichier `.sync`. Copie ou supprime les fichiers selon la date de modification.

```bash
java -jar target/sync.jar <nom_du_profil>
```

**Exemple** :

```bash
java -jar target/sync.jar projetA
```

---

### 3. Affichage des informations d’un profil

Charge le fichier `.sync` correspondant au profil et affiche :

- Le nom du profil
- Les chemins des dossiers à synchroniser
- Le contenu du registre (fichiers, dates, etc.)

```bash
java -jar target/syncstat.jar <nom_du_profil>
```

**Exemple** :

```bash
java -jar target/syncstat.jar projetA
```

---

## Architecture Logicielle

Ce projet suit les principes **SOLID** et adopte une architecture modulaire et extensible. Il utilise notamment :

- **Abstract Factory** et **Builder** : pour la création des objets complexes (profil, registre…)
- **Composite** : pour représenter les structures de fichiers de manière récursive
- **Visiteur** : pour appliquer des opérations sur les éléments de la structure de synchronisation

## Auteurs
Clayton Muhoza

