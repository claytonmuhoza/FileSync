package sync.fs;

/**
 * il permet d'appliquer les opérations d'écriture sur les fichiers contenu dans les chemins des dossiers à synchroniser
 */
public interface SyncPathOperator {
    /**
     * Copie une entrée de ce chemin vers un autre SyncPath.
     * @param relativePath Le chemin relatif de l'entrée à copier.
     * @param destination Le système de fichiers cible.
     */
    void copyEntryTo(RelativePath relativePath, SyncPath destination);

    /**
     * Supprime une entrée spécifique de ce chemin.
     * @param relativePath Le chemin relatif de l'entrée à supprimer.
     */
    void deleteEntry(RelativePath relativePath);
}
