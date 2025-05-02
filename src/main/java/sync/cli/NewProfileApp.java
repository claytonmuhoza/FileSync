package sync.cli;

import sync.fs.AutoSyncPathFactory;
import sync.fs.SyncPathFactory;
import sync.logging.ConsoleLogger;
import sync.profile.*;
import sync.registry.RegisterBuilderStd;

public class NewProfileApp {
    public static void main(String[] args) {
        try {
            if (args.length != 3) {
                System.out.println("Usage : java -jar new-profile.jar <nom_profil> <cheminA> <cheminB>");
                return;
            }

            ProfileName name = new ProfileNameStd(args[0]);
            String pathA = args[1];
            String pathB = args[2];
            SyncPathFactory pathFactory = new AutoSyncPathFactory();
            Profile profile = new ProfileBuilderStd()
                    .setName(name)
                    .setPathA(pathFactory.create(pathA))
                    .setPathB(pathFactory.create(pathB))
                    .setRegister(new RegisterBuilderStd().build())
                    .build();

            ProfilePersistence persistence = new ProfileXmlPersistence();
            persistence.save(profile);

            ConsoleLogger.getInstance().info("Profil '" + name + "' créé et sauvegardé.");
        }catch (Exception e) {
            ConsoleLogger.getInstance().error( e.getMessage() + "");
        }
    }
}