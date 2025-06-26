package sync.fs;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

public class FileEntry implements Entry {
    private final RelativePath relativePath;
    private final Instant lastModified;

    public FileEntry(RelativePath relativePath, Instant lastModified) {
        this.relativePath = relativePath;
        this.lastModified = lastModified;
    }

    @Override
    public RelativePath getRelativePath() {
        return relativePath;
    }

    @Override
    public boolean isDirectory() {
        return false;
    }

    @Override
    public Instant getLastModified() {
        return lastModified;
    }


    @Override
    public void accept(EntryVisitor visitor) {
        visitor.visitFile(this);
    }
}
