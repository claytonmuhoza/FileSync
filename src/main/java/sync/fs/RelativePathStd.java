package sync.fs;

import java.util.Objects;

public class RelativePathStd implements sync.fs.RelativePath {
    private final String path;

    public RelativePathStd(String path) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RelativePathStd other)) return false;
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

