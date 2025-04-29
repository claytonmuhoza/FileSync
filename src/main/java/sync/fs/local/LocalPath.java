package sync.fs.local;

import sync.fs.*;
import sync.fs.Path;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class LocalPath implements Path {
    private final java.nio.file.Path base;
    private final EntryFactory entryFactory;

    public LocalPath(String root, EntryFactory entryFactory) {
        this.base = Paths.get(root);
        this.entryFactory = entryFactory;
    }

    @Override
    public List<Entry> listEntries() {
        return listDirectoryRecursively(base);
    }

    @Override
    public Entry getEntry(RelativePath relativePath) {
        java.nio.file.Path path = base.resolve(relativePath.getPath());
        if (!Files.exists(path)) {
            return null;
        }
        return buildEntryFromPath(path);
    }

    @Override
    public void copyEntryTo(RelativePath relativePath, Path destination) {
        if (!(destination instanceof LocalPath destLocal)) {
            throw new IllegalArgumentException("Destination doit être un LocalPath");
        }
        java.nio.file.Path source = base.resolve(relativePath.getPath());
        java.nio.file.Path target = destLocal.base.resolve(relativePath.getPath());

        try {
            if (Files.isDirectory(source)) {
                Files.createDirectories(target); // Crée le dossier
            } else {
                Files.createDirectories(target.getParent()); // Crée le parent si nécessaire
                Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);
            }
        } catch (IOException e) {
            throw new RuntimeException("Erreur de copie de fichier : " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteEntry(RelativePath relativePath) {
        java.nio.file.Path path = base.resolve(relativePath.getPath());
        try {
            if (Files.isDirectory(path)) {
                Files.walkFileTree(path, new SimpleFileVisitor<>() {
                    @Override
                    public FileVisitResult visitFile(java.nio.file.Path file, BasicFileAttributes attrs)
                            throws IOException {
                        Files.delete(file);
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult postVisitDirectory(java.nio.file.Path dir, IOException exc)
                            throws IOException {
                        Files.delete(dir);
                        return FileVisitResult.CONTINUE;
                    }
                });
            } else {
                Files.deleteIfExists(path);
            }
        } catch (IOException e) {
            throw new RuntimeException("Erreur de suppression : " + e.getMessage(), e);
        }
    }

    // Méthodes privées internes
    private List<Entry> listDirectoryRecursively(java.nio.file.Path current) {
        List<Entry> entries = new ArrayList<>();
        try (DirectoryStream<java.nio.file.Path> stream = Files.newDirectoryStream(current)) {
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
            RelativePath relativePath = new LocalRelativePath(relative);
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
