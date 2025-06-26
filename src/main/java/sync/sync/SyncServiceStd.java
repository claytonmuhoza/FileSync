package sync.sync;

import sync.fs.Entry;
import sync.fs.SyncPath;
import sync.logging.ConsoleLogger;
import sync.logging.SyncLogger;
import sync.profile.Profile;
import sync.profile.ProfileSaver;
import sync.registry.Register;

public class SyncServiceStd implements SyncService {

    private final ProfileSaver profileSaver;
    public SyncServiceStd(ProfileSaver profileSaver) {
        this.profileSaver = profileSaver;
    }

    @Override
    public void sync(Profile profile) {
        SyncLogger syncLogger = ConsoleLogger.getInstance();
        syncLogger.message("=== Synchronisation du profil : " + profile.getName() + " ===");

        SyncPath pathA = profile.getPathA();
        SyncPath pathB = profile.getPathB();
        Register register = profile.getRegister();

        for (Entry entry : pathA.getExplorer().listEntries()) {
            entry.accept(new SyncVisitor(pathA, pathB, register));
        }

        profileSaver.save(profile);

        syncLogger.success("Synchronisation terminée et sauvegardée.");
    }
}

