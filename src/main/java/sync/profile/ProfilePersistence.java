package sync.profile;

public interface ProfilePersistence {
    public void save(Profile profile);
    public Profile load(ProfileName profileName);
}
