package sync.fs.local;

import sync.fs.*;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class LocalSyncPathExplorer implements SyncPathExplorer {
    private final java.nio.file.Path base;
    private final EntryFactory entryFactory;
    public LocalSyncPathExplorer(SyncPath root) {
        this.base = java.nio.file.Paths.get(root.toString());
        this.entryFactory = new LocalEntryFactory();
    }
    @Override
    public List<Entry> listEntries() {
        return listDirectoryRecursively(base);
    }

    @Override
    public Entry getEntry(sync.fs.RelativePath relativePath) {
        java.nio.file.Path path = base.resolve(relativePath.getPath());
        if (!Files.exists(path)) {
            return null;
        }
        return buildEntryFromPath(path);
    }
    // Méthodes privées internes
    private List<Entry> listDirectoryRecursively(java.nio.file.Path current) {
        List<Entry> entries = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(current)) {
            for (java.nio.file.Path child : stream) {
                entries.add(buildEntryFromPath(child));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return entries;
    }


    private Entry buildEntryFromPath(java.nio.file.Path path) {
        try {
            BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);
            String relative = base.relativize(path).toString().replace("\\", "/");
            sync.fs.RelativePath relativePath = new RelativePathStd(relative);
            Instant lastModified = attrs.lastModifiedTime().toInstant();

            if (attrs.isDirectory()) {
                List<Entry> children = listDirectoryRecursively(path);
                return entryFactory.createDirectory(relativePath, lastModified, children);
            } else {
                return entryFactory.createFile(relativePath, lastModified);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
