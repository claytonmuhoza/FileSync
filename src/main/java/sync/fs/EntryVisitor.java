package sync.fs;

public interface EntryVisitor {
    void visitFile(FileEntry file);
    void visitDirectory(DirectoryEntry directory);
}

