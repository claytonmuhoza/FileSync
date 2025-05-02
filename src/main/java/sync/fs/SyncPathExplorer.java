package sync.fs;

import java.util.List;

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

