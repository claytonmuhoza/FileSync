package sync.fs;

import java.time.Instant;
import java.util.List;

public interface Entry {
    RelativePath getRelativePath();           // relatif à la racine
    boolean isDirectory();
    Instant getLastModified();

    List<Entry> getChildren();          // Vide si fichier
    void accept(EntryVisitor visitor);  // Patron Visiteur
    String toString();
}


