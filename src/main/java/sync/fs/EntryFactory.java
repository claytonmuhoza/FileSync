package sync.fs;

import java.time.Instant;
import java.util.List;

public interface EntryFactory {
    FileEntry createFile(RelativePath path, Instant lastModified);
    DirectoryEntry createDirectory(RelativePath path, Instant lastModified, List<Entry> children);
}

