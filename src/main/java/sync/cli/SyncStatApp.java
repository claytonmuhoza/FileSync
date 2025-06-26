package sync.cli;

import sync.logging.ConsoleLogger;
import sync.logging.SyncLogger;
import sync.profile.*;
import sync.fs.Entry;
import sync.registry.Register;
/**
 * Affiche les informations d’un profil et le contenu du registre
 */
public class SyncStatApp {
    public static void main(String[] args) {
        SyncLogger syncLogger = ConsoleLogger.getInstance();
        try {
            if (args.length != 1) {
                syncLogger.message("Usage : java -jar syncstat.jar <nom_profil>");
                return;
            }

            ProfileName name = new ProfileNameStd(args[0]);
            ProfileLoader profileLoader = new ProfileXMLLoader();
            Profile profile = profileLoader.load(name);
            Register register = profile.getRegister();

            syncLogger.message("=== Informations du profil ===");
            syncLogger.message("Nom       : " + profile.getName());
            syncLogger.message("Chemin A  : " + profile.getPathA());
            syncLogger.message("Chemin B  : " + profile.getPathB());

           syncLogger.message("\n=== Entrées du registre ===");
            for (Entry entry : register.getAllEntries()) {
                syncLogger.message("- " + entry.getRelativePath().getPath()
                        + " | " + (entry.isDirectory() ? "Dossier" : "Fichier")
                        + " | Dernière modif : " + entry.getLastModified());
            }
        }catch (Exception e) {
            syncLogger.error(e.getMessage());
        }

    }
}