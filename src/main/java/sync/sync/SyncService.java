package sync.sync;

import sync.profile.Profile;

/**
 * Interface représentant un service de synchronisation de fichiers.
 *
 */
public interface SyncService {

    /**
     * Lance la synchronisation du profil donné.
     *
     * @param profile le profil à synchroniser
     */
    void sync(Profile profile);
}
