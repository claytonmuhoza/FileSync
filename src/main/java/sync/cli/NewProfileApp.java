package sync.cli;

import sync.fs.SyncPathFactory;
import sync.fs.local.LocalSyncPathFactory;
import sync.logging.ConsoleLogger;
import sync.logging.SyncLogger;
import sync.profile.*;
import sync.registry.RegisterBuilderStd;

/**
 * il permet la Création d'un nouveau profil
 */
public class NewProfileApp {
    public static void main(String[] args) {
        SyncLogger syncLogger = ConsoleLogger.getInstance();
        try {
            if (args.length != 3) {
                syncLogger.message("Usage : java -jar new-profile.jar <nom_profil> <cheminA> <cheminB>");
                return;
            }

            ProfileName name = new ProfileNameStd(args[0]);
            String pathA = args[1];
            String pathB = args[2];
            SyncPathFactory pathFactory = new LocalSyncPathFactory();
            Profile profile = new ProfileBuilderStd()
                    .setName(name)
                    .setPathA(pathFactory.create(pathA))
                    .setPathB(pathFactory.create(pathB))
                    .setRegister(new RegisterBuilderStd().build())
                    .build();

            ProfileSaver persistence = new ProfileXmlSaver();
            persistence.save(profile);

            ConsoleLogger.getInstance().info("Profil '" + name + "' créé et sauvegardé.");
        }catch (Exception e) {
            syncLogger.error(e.getMessage());
        }
    }
}