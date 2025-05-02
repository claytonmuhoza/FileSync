package sync.fs.local;

import sync.fs.*;

import java.nio.file.*;

public class LocalSyncPath implements SyncPath {
    private final Path base;

    public LocalSyncPath(String root) {
        this.base = Paths.get(root);

        // Validation
        if (!Files.exists(base)) {
            throw new IllegalArgumentException("Le chemin '" + root + "' n'existe pas.");
        }

        if (!Files.isDirectory(base)) {
            throw new IllegalArgumentException("Le chemin '" + root + "' n'est pas un dossier.");
        }

        if (!Files.isReadable(base)) {
            throw new IllegalArgumentException("Le dossier '" + root + "' n'est pas accessible en lecture.");
        }
    }

    @Override
    public SyncPath getSyncPath() {
        return this;
    }

    @Override
    public SyncPathOperator getOperator() {
        return new LocalSyncPathOperator(this);
    }

    @Override
    public SyncPathExplorer getExplorer() {
        return new LocalSyncPathExplorer(this);
    }

    @Override
    public String toString() {
        return base.toString();
    }

    public Path getBasePath() {
        return base;
    }
}
