package sync.registry;

import sync.profile.ProfileName;

public interface RegisterPersistence {
    void save(Register register, ProfileName profileName);
    Register load(ProfileName profileName);
}
