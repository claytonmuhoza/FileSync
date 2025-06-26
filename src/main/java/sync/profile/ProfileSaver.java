package sync.profile;

/**
 * Interface définissant la sauvegarde d’un {@link Profile}.
 *
 */
public interface ProfileSaver {

    /**
     * Sauvegarde le profil fourni.
     *
     * @param profile le profil à sauvegarder
     * @throws RuntimeException en cas d’échec de l’opération
     */
    public void save(Profile profile);
}