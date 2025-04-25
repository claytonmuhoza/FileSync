package sync.profile;

import sync.fs.Path;
import sync.registry.Register;

public interface Profile {
    ProfileName getName();
    Path getPathA();
    Path getPathB();
    Register getRegister();
}
