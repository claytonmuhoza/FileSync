package sync.sync;

import sync.fs.Entry;
import sync.fs.SyncPath;
import sync.profile.Profile;
import sync.profile.ProfilePersistence;
import sync.registry.Register;

public class SyncServiceStd implements SyncService {

    private final ProfilePersistence profilePersistence;

    public SyncServiceStd(ProfilePersistence profilePersistence) {
        this.profilePersistence = profilePersistence;
    }

    @Override
    public void sync(Profile profile) {
        System.out.println("=== Synchronisation du profil : " + profile.getName() + " ===");

        SyncPath pathA = profile.getPathA();
        SyncPath pathB = profile.getPathB();
        Register register = profile.getRegister();

        for (Entry entry : pathA.getExplorer().listEntries()) {
            entry.accept(new SyncVisitor(pathA, pathB, register));
        }

        profilePersistence.save(profile);

        System.out.println("✅ Synchronisation terminée et sauvegardée.");
    }
}

