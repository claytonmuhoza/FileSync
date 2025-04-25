package sync.fs;

import java.util.List;

public interface Path {
    List<Entry> listEntries(); // récursivement tous les fichiers/dossiers
    Entry getEntry(String relativePath); // un fichier spécifique
    void copyEntryTo(String relativePath, Path destination); // copie ce fichier vers un autre path
    void deleteEntry(String relativePath);
}