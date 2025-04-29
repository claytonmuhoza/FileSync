package sync.fs.local;

import sync.fs.RelativePath;

import java.util.Objects;

public class LocalRelativePath implements RelativePath {
    private final String path;

    public LocalRelativePath(String path) {
        if (path == null || path.isEmpty() || path.startsWith("/") || path.contains("..")) {
            throw new IllegalArgumentException(
                    "Chemin relatif invalide : ne doit pas commencer par '/', ni contenir '..'."
            );
        }
        this.path = path.replace("\\", "/");
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public RelativePath resolve(String child) {
        if (child == null || child.isEmpty()) {
            throw new IllegalArgumentException("Nom d'enfant invalide.");
        }
        return new LocalRelativePath(path + "/" + child);
    }

    @Override
    public String getParent() {
        int index = path.lastIndexOf('/');
        return (index > 0) ? path.substring(0, index) : "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LocalRelativePath other)) return false;
        return path.equals(other.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path);
    }

    @Override
    public String toString() {
        return path;
    }
}

