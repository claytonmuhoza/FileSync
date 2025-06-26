package sync.fs;

/**
 * Interface représentant un visiteur pour les entrées du système de fichiers.
 *
 * Cette interface suit le patron de conception Visiteur, permettant d'appliquer
 * des traitements différents selon qu'une entrée est un fichier ou un dossier.
 */
public interface EntryVisitor {

    /**
     * Traite une entrée de type fichier.
     *
     * @param file le fichier à visiter
     */
    void visitFile(FileEntry file);

    /**
     * Traite une entrée de type dossier.
     *
     * @param directory le dossier à visiter
     */
    void visitDirectory(DirectoryEntry directory);
}
