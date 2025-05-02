package sync.profile;

import sync.fs.SyncPath;
import sync.registry.Register;

/**
 * Interface de construction pour un objet {@link Profile}.
 *
 * Permet de définir progressivement les propriétés d'un profil
 * avant de le construire via la méthode {@code build()}.
 */
public interface ProfileBuilder {

    /**
     * Définit le nom du profil.
     *
     * @param profileName nom du profil à affecter
     * @return le builder courant pour chaînage
     */
    ProfileBuilder setName(ProfileName profileName);

    /**
     * Définit le chemin A du profil (dossier source).
     *
     * @param syncPathA objet représentant le dossier A
     * @return le builder courant pour chaînage
     */
    ProfileBuilder setPathA(SyncPath syncPathA);

    /**
     * Définit le chemin B du profil (dossier destination).
     *
     * @param syncPathB objet représentant le dossier B
     * @return le builder courant pour chaînage
     */
    ProfileBuilder setPathB(SyncPath syncPathB);

    /**
     * Définit le registre de synchronisation associé au profil.
     *
     * @param register registre à utiliser
     * @return le builder courant pour chaînage
     */
    ProfileBuilder setRegister(Register register);

    /**
     * Construit le profil à partir des données fournies.
     *
     * @return une instance complète de {@link Profile}
     */
    Profile build();
}

