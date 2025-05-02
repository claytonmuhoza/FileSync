package sync.profile;

import sync.fs.SyncPath;
import sync.registry.Register;
/**
 * Classe implémentant l'interface {@link Profile} qui permet d'instancier des objects de type {@link Profile}.
 *
 */
public class ProfileStd implements Profile {
    private final ProfileName name;
    private final SyncPath syncPathA;
    private final SyncPath syncPathB;
    private final Register register;

    public ProfileStd(ProfileName name, SyncPath syncPathA, SyncPath syncPathB, Register register) {
        this.name = name;
        this.syncPathA = syncPathA;
        this.syncPathB = syncPathB;
        this.register = register;
    }

    @Override
    public ProfileName getName() {
        return name;
    }

    @Override
    public SyncPath getPathA() {
        return syncPathA;
    }

    @Override
    public SyncPath getPathB() {
        return syncPathB;
    }

    @Override
    public Register getRegister() {
        return register;
    }
}

