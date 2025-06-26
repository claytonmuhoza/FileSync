package sync.fs;

import java.util.List;

/**
 * il permet de lire les chemins des dossiers à synchroniser
 */
public interface SyncPathExplorer {
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
}

