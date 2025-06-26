package sync.sync;

import sync.fs.*;
import sync.fs.local.LocalEntryFactory;
import sync.fs.local.LocalSyncPathExplorer;
import sync.fs.local.LocalSyncPathOperator;
import sync.logging.ConsoleLogger;
import sync.logging.SyncLogger;
import sync.registry.Register;

import java.io.IOException;
import java.time.Instant;
import java.util.Optional;

public class SyncVisitor implements EntryVisitor {

    private final SyncPath syncPathA;
    private final SyncPath syncPathB;
    private final Register register;
    public SyncVisitor(SyncPath syncPathA, SyncPath syncPathB, Register register) {
        this.syncPathA = syncPathA;
        this.syncPathB = syncPathB;
        this.register = register;
    }

    @Override
    public void visitFile(FileEntry fileA) {
        SyncLogger syncLogger = ConsoleLogger.getInstance();
        RelativePath relativePath = fileA.getRelativePath();
        Entry entryB = syncPathB.getExplorer().getEntry(relativePath);
        Optional<Entry> entryRegOpt = register.get(relativePath);
        Instant dateA = fileA.getLastModified();
        Instant dateB = entryB != null ? entryB.getLastModified() : null;
        Instant dateReg = entryRegOpt.map(Entry::getLastModified).orElse(null);

        boolean existsA = true;
        boolean existsB = entryB != null;
        boolean existsReg = entryRegOpt.isPresent();

        boolean modifA = !dateA.equals(dateReg);
        boolean modifB = existsB && !dateB.equals(dateReg);

        if (existsA && !existsB && !existsReg) {
            // Nouveau fichier dans A
            syncLogger.message("[→] Nouveau dans A → Copier vers B : " + relativePath);
            syncPathA.getOperator().copyEntryTo(relativePath, syncPathB);
            register.put(fileA);

        } else if (existsA && !existsB && existsReg && !modifA) {
            // Supprimé dans B → supprimer dans A
            syncLogger.message("[✘] Supprimé dans B → Supprimer dans A : " + relativePath);
            syncPathA.getOperator().deleteEntry(relativePath);
            register.remove(relativePath);

        } else if (!existsA && existsB && !existsReg) {
            // Nouveau dans B → Copier vers A
            syncLogger.message("[←] Nouveau dans B → Copier vers A : " + relativePath);
            syncPathB.getOperator().copyEntryTo(relativePath, syncPathA);
            register.put(entryB);

        } else if (modifA && !modifB) {
            // A plus récent
            syncLogger.message("[→] A plus récent → Copier vers B : " + relativePath);
            syncPathA.getOperator().copyEntryTo(relativePath, syncPathB);
            register.put(fileA);

        } else if (!modifA && modifB) {
            // B plus récent
            syncLogger.message("[←] B plus récent → Copier vers A : " + relativePath);
            syncPathB.getOperator().copyEntryTo(relativePath, syncPathA);
            register.put(entryB);

        } else if (modifA && modifB) {
            // Conflit
            ConsoleLogger.getInstance().warning("CONFLIT sur : " + relativePath);
            syncLogger.message("Copier A → B (a) ou B → A (b) ? ");
            try {
                char choice = (char) System.in.read();
                if (choice == 'a') {
                    syncPathA.getOperator().copyEntryTo(relativePath, syncPathB);
                    register.put(fileA);
                } else {
                    syncPathB.getOperator().copyEntryTo(relativePath, syncPathA);
                    register.put(entryB);
                }
            } catch (IOException e) {
                throw new RuntimeException("Erreur de lecture utilisateur pour résolution de conflit", e);
            }
            detectDeletions();
        } else {
            syncLogger.message("[✓] Aucun changement : " + relativePath);
        }
    }


    @Override
    public void visitDirectory(DirectoryEntry dirA) {
        RelativePath relativePath = dirA.getRelativePath();

        Entry entryB = syncPathB.getExplorer().getEntry(relativePath);
        Optional<Entry> entryRegOpt = register.get(relativePath);

        Instant dateA = dirA.getLastModified();
        Instant dateB = entryB != null ? entryB.getLastModified() : null;
        Instant dateReg = entryRegOpt.map(Entry::getLastModified).orElse(null);

        boolean existsB = entryB != null;
        boolean existsReg = entryRegOpt.isPresent();
        boolean modifA = !dateA.equals(dateReg);

        System.out.println("[📁 DOSSIER] " + relativePath);

        if (!existsB && !existsReg) {
            // Nouveau dossier dans A → créer dans B
            System.out.println("  → Créer dossier dans B");
            syncPathA.getOperator().copyEntryTo(relativePath, syncPathB);
            register.put(dirA);

        } else if (!existsB && existsReg && !modifA) {
            // Supprimé dans B → supprimer dans A
            System.out.println("  ✘ Supprimé dans B → Supprimer dans A");
            syncPathA.getOperator().deleteEntry(relativePath);
            register.remove(relativePath);
            return; // ne pas visiter les enfants supprimés

        } else if (modifA) {
            // Modifié dans A → recopier dans B
            System.out.println("  → MAJ dossier dans B");
            syncPathA.getOperator().copyEntryTo(relativePath, syncPathB);
            register.put(dirA);
        } else {
            System.out.println("  ✓ Aucun changement pour le dossier");
        }
        detectDeletions();
        // Récursion sur les enfants
        for (Entry child : dirA.getChildren()) {
            child.accept(this);
        }
    }
    private void detectDeletions() {
        for (Entry previous : register.getAllEntries()) {
            RelativePath path = previous.getRelativePath();

            Entry currentA = syncPathA.getExplorer().getEntry(path);
            Entry currentB = syncPathB.getExplorer().getEntry(path);

            boolean existsA = currentA != null;
            boolean existsB = currentB != null;
            Instant lastSynced = previous.getLastModified();

            if (!existsA && existsB && currentB.getLastModified().equals(lastSynced)) {
                ConsoleLogger.getInstance().message("[✘] Supprimé dans A → Supprimer dans B : " + path);
                syncPathB.getOperator().deleteEntry(path);
                register.remove(path);

            } else if (!existsB && existsA && currentA.getLastModified().equals(lastSynced)) {
                ConsoleLogger.getInstance().message("[✘] Supprimé dans B → Supprimer dans A : " + path);
                syncPathA.getOperator().deleteEntry(path);
                register.remove(path);
            }
        }
    }

}
