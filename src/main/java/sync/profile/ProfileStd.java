package sync.profile;

import sync.fs.Path;
import sync.registry.Register;

public class ProfileStd implements Profile {
    private final ProfileName name;
    private final Path pathA;
    private final Path pathB;
    private final Register register;

    public ProfileStd(ProfileName name, Path pathA, Path pathB, Register register) {
        this.name = name;
        this.pathA = pathA;
        this.pathB = pathB;
        this.register = register;
    }

    @Override
    public ProfileName getName() {
        return name;
    }

    @Override
    public Path getPathA() {
        return pathA;
    }

    @Override
    public Path getPathB() {
        return pathB;
    }

    @Override
    public Register getRegister() {
        return register;
    }
}

