package sync.cli;

import sync.profile.*;
import sync.fs.Entry;
import sync.registry.Register;

public class SyncStatApp {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage : java -jar syncstat.jar <nom_profil>");
            return;
        }

        ProfileName name = new ProfileNameStd(args[0]);
        ProfilePersistence persistence = new ProfileXmlPersistence();
        Profile profile = persistence.load(name);
        Register register = profile.getRegister();

        System.out.println("=== Informations du profil ===");
        System.out.println("Nom       : " + profile.getName());
        System.out.println("Chemin A  : " + profile.getPathA());
        System.out.println("Chemin B  : " + profile.getPathB());

        System.out.println("\n=== Entrées du registre ===");
        for (Entry entry : register.getAllEntries()) {
            System.out.println("- " + entry.getRelativePath().getPath()
                    + " | " + (entry.isDirectory() ? "Dossier" : "Fichier")
                    + " | Dernière modif : " + entry.getLastModified());
        }
    }
}