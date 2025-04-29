package sync.fs.local;

import sync.fs.*;

import java.time.Instant;
import java.util.List;

public class LocalEntryFactory implements EntryFactory {
    @Override
    public FileEntry createFile(RelativePath path, Instant lastModified) {
        return new FileEntry(path, lastModified);
    }

    @Override
    public DirectoryEntry createDirectory(RelativePath path, Instant lastModified, List<Entry> children) {
        return new DirectoryEntry(path, lastModified, children);
    }
}
