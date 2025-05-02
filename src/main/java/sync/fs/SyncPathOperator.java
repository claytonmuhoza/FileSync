package sync.fs;

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
