package sync.fs;

/**
 * Interface représentant un chemin racine synchronisable dans un système de fichiers.
 *
 * Il peut représenter un dossier local, distant (WebDAV), ou toute autre source abstraite.
 */
public interface SyncPath {

    /**
     *
     * @return le chemin synchronisable courant
     */
    SyncPath getSyncPath();

    /**
     * Fournit l’opérateur de manipulation (copie, suppression) pour ce chemin.
     *
     * @return l’opérateur d’écriture associé
     */
    SyncPathOperator getOperator();

    /**
     * Fournit l’explorateur de lecture pour ce chemin.
     *
     * @return l’explorateur permettant de lister et accéder aux fichiers
     */
    SyncPathExplorer getExplorer();
}
