package sync.cli;

import sync.profile.*;
import sync.sync.*;

public class SyncApp {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage : java -jar sync.jar <nom_profil>");
            return;
        }

        ProfileName name = new ProfileNameStd(args[0]);
        ProfilePersistence persistence = new ProfileXmlPersistence();
        Profile profile = persistence.load(name);

        SyncService service = new SyncServiceStd(persistence);
        service.sync(profile);
    }
}