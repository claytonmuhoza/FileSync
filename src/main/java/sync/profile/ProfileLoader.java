package sync.profile;

/**
 * Interface représentant le chargement d’un profil existant depuis une source de persistance.
 *
 * Fournit une méthode unique pour restaurer un {@link Profile} à partir de son nom.
 */
public interface ProfileLoader {

    /**
     * Charge un profil existant à partir de son nom.
     *
     * @param profileName le nom du profil à charger
     * @return le profil correspondant
     * @throws RuntimeException si le profil est introuvable ou invalide
     */
    public Profile load(ProfileName profileName);
}