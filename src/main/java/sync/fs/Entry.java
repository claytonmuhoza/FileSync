package sync.fs;

import java.time.Instant;

public interface Entry {
    String getRelativePath();
    boolean isDirectory();
    Instant getLastModified(); //date de dernière modification
}
