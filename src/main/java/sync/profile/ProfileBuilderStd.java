package sync.profile;

import sync.fs.SyncPath;
import sync.registry.Register;

public class ProfileBuilderStd implements ProfileBuilder {
    private ProfileName name;
    private SyncPath syncPathA;
    private SyncPath syncPathB;
    private Register register;

    public ProfileBuilder setName(ProfileName name) {
        this.name = name;
        return this;
    }

    public ProfileBuilder setPathA(SyncPath syncPathA) {
        this.syncPathA = syncPathA;
        return this;
    }

    public ProfileBuilder setPathB(SyncPath syncPathB) {
        this.syncPathB = syncPathB;
        return this;
    }

    public ProfileBuilder setRegister(Register register) {
        this.register = register;
        return this;
    }

    public Profile build() {
        if (name == null || syncPathA == null || syncPathB == null || register == null) {
            throw new IllegalStateException("Tous les champs du profil doivent être renseignés !");
        }
        return new ProfileStd(name, syncPathA, syncPathB, register);
    }
}
