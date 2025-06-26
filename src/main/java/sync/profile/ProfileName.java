package sync.profile;

/**
 * Interface représentant un nom de profil.
 *
 */
public interface ProfileName {

    /**
     * Retourne le nom du profil encapsulé
     *
     * @return le nom du profil encapsulé
     */
    public ProfileName getName();

    /**
     * Compare ce nom de profil à un autre objet.
     *
     * @param obj l’objet à comparer
     * @return vrai si les deux objets représentent le même nom
     */
    public boolean equals(Object obj);

    /**
     * Calcule le code de hachage du nom de profil.
     *
     * @return le hashcode du nom
     */
    public int hashCode();

    /**
     * Retourne une représentation textuelle du nom du profil.
     *
     * @return le nom du profil sous forme de chaîne
     */
    public String toString();
}
