package sync.cli;

import sync.logging.ConsoleLogger;
import sync.logging.SyncLogger;
import sync.profile.*;
import sync.sync.*;

/**
 * Lance la synchronisation pour un profil existant
 */
public class SyncApp {
    public static void main(String[] args) {
        SyncLogger syncLogger = ConsoleLogger.getInstance();
        try {
            if (args.length != 1) {
                syncLogger.message("Usage : java -jar sync.jar <nom_profil>");
                return;
            }

            ProfileName name = new ProfileNameStd(args[0]);
            ProfileSaver profileSaver = new ProfileXmlSaver();
            ProfileLoader profileLoader = new ProfileXMLLoader();
            Profile profile = profileLoader.load(name);

            SyncService service = new SyncServiceStd(profileSaver);
            service.sync(profile);
        }
        catch (Exception e) {
            syncLogger.error(e.getMessage());
        }
    }
}