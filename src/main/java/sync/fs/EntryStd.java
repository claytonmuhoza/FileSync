package sync.fs;
import java.time.Instant;

public class EntryStd implements Entry {
    private final String relativePath;
    private final Instant lastModified;
    private final boolean isDirectory;

    public EntryStd(String relativePath, Instant lastModified, boolean isDirectory) {
        this.relativePath = relativePath;
        this.lastModified = lastModified;
        this.isDirectory = isDirectory;
    }

    @Override
    public String getRelativePath() {
        return relativePath;
    }

    @Override
    public boolean isDirectory() {
        return isDirectory;
    }

    @Override
    public Instant getLastModified() {
        return lastModified;
    }
}