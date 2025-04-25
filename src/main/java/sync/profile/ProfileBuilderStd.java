package sync.profile;

import sync.fs.Path;
import sync.registry.Register;

public class ProfileBuilderStd implements ProfileBuilder {
    private ProfileName name;
    private Path pathA;
    private Path pathB;
    private Register register;

    public ProfileBuilder setName(ProfileName name) {
        this.name = name;
        return this;
    }

    public ProfileBuilder setPathA(Path pathA) {
        this.pathA = pathA;
        return this;
    }

    public ProfileBuilder setPathB(Path pathB) {
        this.pathB = pathB;
        return this;
    }

    public ProfileBuilder setRegister(Register register) {
        this.register = register;
        return this;
    }

    public Profile build() {
        if (name == null || pathA == null || pathB == null || register == null) {
            throw new IllegalStateException("Tous les champs du profil doivent être renseignés !");
        }
        return new ProfileStd(name, pathA, pathB, register);
    }
}
