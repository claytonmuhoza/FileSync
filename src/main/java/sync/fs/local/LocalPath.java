package sync.fs.local;

import sync.fs.Entry;
import sync.fs.EntryStd;
import sync.fs.Path;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class LocalPath implements Path {
    private final java.nio.file.Path base;

    public LocalPath(String root) {
        this.base = Paths.get(root);
    }

    @Override
    public List<Entry> listEntries() {
        List<Entry> entries = new ArrayList<>();
        try {
            Files.walk(base).forEach(path -> {
                if (!Files.isDirectory(path)) {
                    entries.add(fromPath(path));
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return entries;
    }

    @Override
    public Entry getEntry(String relativePath) {
        java.nio.file.Path path = base.resolve(relativePath);
        if (!Files.exists(path)) return null;
        return fromPath(path);
    }

    @Override
    public void copyEntryTo(String relativePath, Path destination) {
        Entry entry = getEntry(relativePath);
        if (entry == null || entry.isDirectory()) return;
        if (!(destination instanceof LocalPath target)) return;

        java.nio.file.Path source = base.resolve(relativePath);
        java.nio.file.Path targetPath = target.base.resolve(relativePath);

        try {
            Files.createDirectories(targetPath.getParent());
            Files.copy(source, targetPath, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteEntry(String relativePath) {
        try {
            Files.deleteIfExists(base.resolve(relativePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Entry fromPath(java.nio.file.Path path) {
        try {
            BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);
            String rel = base.relativize(path).toString();
            return new EntryStd(rel, attrs.lastModifiedTime().toInstant(), attrs.isDirectory());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
