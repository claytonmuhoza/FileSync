package sync.profile;

import sync.fs.SyncPath;
import sync.registry.Register;

/**
 * Représente un profil de synchronisation de fichiers.
 *
 */
public interface Profile {

    /**
     * Retourne le nom du profil.
     *
     * @return le nom du profil
     */
    ProfileName getName();

    /**
     * Retourne le premier dossier de la synchronisation.
     *
     * @return un objet SyncPath représentant le premier dossier
     */
    SyncPath getPathA();

    /**
     * Retourne le second dossier de la synchronisation.
     *
     * @return un objet SyncPath représentant le second dossier
     */
    SyncPath getPathB();

    /**
     * Retourne le registre de synchronisation associé au profil.
     * Ce registre contient les métadonnées de la dernière synchronisation.
     *
     * @return le registre de synchronisation
     */
    Register getRegister();
}
