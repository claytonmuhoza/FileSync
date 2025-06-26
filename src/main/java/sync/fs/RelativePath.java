package sync.fs;

/**
 * Interface représentant un chemin relatif par rapport à une racine de synchronisation.
 *
 */
public interface RelativePath {

    /**
     * Retourne le chemin sous forme de chaîne relative (ex. : {@code documents/note.txt}).
     *
     * @return le chemin relatif
     */
    String getPath();

    /**
     * Compare ce chemin relatif à un autre objet.
     *
     * @param other l’objet à comparer
     * @return vrai si les deux objets représentent le même chemin
     */
    boolean equals(Object other);

    /**
     * Calcule le code de hachage de ce chemin relatif.
     *
     * @return le hashcode du chemin
     */
    int hashCode();

    /**
     * Retourne une représentation textuelle du chemin relatif.
     *
     * @return le chemin sous forme de chaîne
     */
    String toString();
}
