package sync.fs.local;

import sync.fs.*;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class LocalSyncPathOperator implements SyncPathOperator {
    Path base;
    EntryFactory entryFactory;
    public LocalSyncPathOperator(SyncPath root) {
        this.entryFactory = new LocalEntryFactory();
        base = Paths.get(root.toString());
    }
    @Override
    public void copyEntryTo(RelativePath relativePath, SyncPath destination) {

        Path source = base.resolve(relativePath.getPath());
        Path target = Paths.get(destination.toString()).resolve(relativePath.getPath());

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


}
