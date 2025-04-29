package sync.fs;

public interface RelativePath {
    String getPath();                          // chemin sous forme de string
    RelativePath resolve(String child);        // résout un sous-chemin
    String getParent();                        // nom du dossier parent
    boolean equals(Object other);
    int hashCode();
    String toString();
}
