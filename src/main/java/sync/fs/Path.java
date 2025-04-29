package sync.fs;

import java.util.List;

public interface Path {
    /**
     * Liste récursivement toutes les entrées (fichiers et dossiers) sous ce chemin.
     */
    List<Entry> listEntries();

    /**
     * Récupère une entrée spécifique à partir de son chemin relatif.
     * @param relativePath Le chemin relatif de l'entrée.
     * @return L'entrée trouvée, ou null si elle n'existe pas.
     */
    Entry getEntry(RelativePath relativePath);

    /**
     * Copie une entrée de ce chemin vers un autre Path.
     * @param relativePath Le chemin relatif de l'entrée à copier.
     * @param destination Le système de fichiers cible.
     */
    void copyEntryTo(RelativePath relativePath, Path destination);

    /**
     * Supprime une entrée spécifique de ce chemin.
     * @param relativePath Le chemin relatif de l'entrée à supprimer.
     */
    void deleteEntry(RelativePath relativePath);
}
