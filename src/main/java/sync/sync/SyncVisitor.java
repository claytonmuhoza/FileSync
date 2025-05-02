package sync.sync;

import sync.fs.*;
import sync.fs.local.LocalEntryFactory;
import sync.fs.local.LocalSyncPathExplorer;
import sync.fs.local.LocalSyncPathOperator;
import sync.logging.ConsoleLogger;
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
        RelativePath relativePath = fileA.getRelativePath();
        Entry entryB = syncPathB.getExplorer().getEntry(relativePath);
        Optional<Entry> entryRegOpt = register.get(relativePath.getPath());
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
            System.out.println("[→] Nouveau dans A → Copier vers B : " + relativePath);
            syncPathA.getOperator().copyEntryTo(relativePath, syncPathB);
            register.put(fileA);

        } else if (existsA && !existsB && existsReg && !modifA) {
            // Supprimé dans B → supprimer dans A
            System.out.println("[✘] Supprimé dans B → Supprimer dans A : " + relativePath);
            syncPathA.getOperator().deleteEntry(relativePath);
            register.remove(relativePath.getPath());

        } else if (!existsA && existsB && !existsReg) {
            // Nouveau dans B → Copier vers A
            System.out.println("[←] Nouveau dans B → Copier vers A : " + relativePath);
            syncPathB.getOperator().copyEntryTo(relativePath, syncPathA);
            register.put(entryB);

        } else if (modifA && !modifB) {
            // A plus récent
            System.out.println("[→] A plus récent → Copier vers B : " + relativePath);
            syncPathA.getOperator().copyEntryTo(relativePath, syncPathB);
            register.put(fileA);

        } else if (!modifA && modifB) {
            // B plus récent
            System.out.println("[←] B plus récent → Copier vers A : " + relativePath);
            syncPathB.getOperator().copyEntryTo(relativePath, syncPathA);
            register.put(entryB);

        } else if (modifA && modifB) {
            // Conflit
            ConsoleLogger.getInstance().warning("CONFLIT sur : " + relativePath);
            System.out.print("Copier A → B (a) ou B → A (b) ? ");
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

        } else {
            System.out.println("[✓] Aucun changement : " + relativePath);
        }
    }


    @Override
    public void visitDirectory(DirectoryEntry dirA) {
        RelativePath relativePath = dirA.getRelativePath();

        Entry entryB = syncPathB.getExplorer().getEntry(relativePath);
        Optional<Entry> entryRegOpt = register.get(relativePath.getPath());

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
            register.remove(relativePath.getPath());
            return; // ne pas visiter les enfants supprimés

        } else if (modifA) {
            // Modifié dans A → recopier dans B
            System.out.println("  → MAJ dossier dans B");
            syncPathA.getOperator().copyEntryTo(relativePath, syncPathB);
            register.put(dirA);
        } else {
            System.out.println("  ✓ Aucun changement pour le dossier");
        }

        // Récursion sur les enfants
        for (Entry child : dirA.getChildren()) {
            child.accept(this);
        }
    }

}
