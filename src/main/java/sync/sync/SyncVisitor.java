package sync.sync;

import sync.fs.*;

public class SyncVisitor implements EntryVisitor {

    @Override
    public void visitFile(FileEntry file) {
        // logique de synchronisation spécifique à un fichier
        System.out.println("Synchroniser fichier : " + file.getRelativePath());
    }

    @Override
    public void visitDirectory(DirectoryEntry directory) {
        // logique récursive sur les enfants
        System.out.println("Entrée dossier : " + directory.getRelativePath());
        for (Entry child : directory.getChildren()) {
            child.accept(this); // appel récursif
        }
    }
}
