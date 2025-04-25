package sync.profile;

import sync.fs.Path;
import sync.registry.Register;

public interface ProfileBuilder {
    public ProfileBuilder setPathA(Path pathA);
    public ProfileBuilder setPathB(Path pathB);
    public ProfileBuilder setRegister(Register register);
    public Profile build();
}

