package sync.fs;

import java.time.Instant;
import java.util.List;

/**
 * Interface représentant une entrée dans un système de fichiers synchronisable.
 *
 */
public interface Entry {

    /**
     * Retourne le chemin relatif de cette entrée, par rapport à la racine du profil.
     *
     * @return le chemin relatif
     */
    RelativePath getRelativePath();

    /**
     * Indique si cette entrée est un dossier.
     *
     * @return vrai si l’entrée est un dossier, faux si c’est un fichier
     */
    boolean isDirectory();

    /**
     * Retourne la date de dernière modification du fichier ou dossier.
     *
     * @return l’instant de la dernière modification
     */
    Instant getLastModified();

    /**
     * Accepte un visiteur pour appliquer une opération sur cette entrée.
     *
     * @param visitor le visiteur à appliquer
     */
    void accept(EntryVisitor visitor);

    /**
     * Retourne une représentation textuelle de l’entrée.
     *
     * @return une chaîne lisible décrivant cette entrée
     */
    String toString();
}

