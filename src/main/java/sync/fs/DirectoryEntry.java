package sync.fs;

import java.time.Instant;
import java.util.List;

public class DirectoryEntry implements Entry {
    private final RelativePath relativePath;
    private final Instant lastModified;
    private final List<Entry> children;

    public DirectoryEntry(RelativePath relativePath, Instant lastModified, List<Entry> children) {
        this.relativePath = relativePath;
        this.lastModified = lastModified;
        this.children = children;
    }

    @Override
    public RelativePath getRelativePath() {
        return relativePath;
    }

    @Override
    public boolean isDirectory() {
        return true;
    }

    @Override
    public Instant getLastModified() {
        return lastModified;
    }

    /**
     * Retourne les enfants du dossier.
     *
     * @return une liste des enfants
     */
    public List<Entry> getChildren() {
        return children;
    }

    @Override
    public void accept(EntryVisitor visitor) {
        visitor.visitDirectory(this);
    }
}
