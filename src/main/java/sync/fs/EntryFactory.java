package sync.fs;

import java.time.Instant;
import java.util.List;

/**
 * Interface représentant une fabrique d’objets {@link Entry}.
 *
 */
public interface EntryFactory {

    /**
     * Crée une nouvelle entrée représentant un fichier.
     *
     * @param path le chemin relatif du fichier
     * @param lastModified la date de dernière modification
     * @return une instance de {@link FileEntry}
     */
    FileEntry createFile(RelativePath path, Instant lastModified);

    /**
     * Crée une nouvelle entrée représentant un dossier avec ses enfants.
     *
     * @param path le chemin relatif du dossier
     * @param lastModified la date de dernière modification
     * @param children la liste des entrées enfants (fichiers ou dossiers)
     * @return une instance de {@link DirectoryEntry}
     */
    DirectoryEntry createDirectory(RelativePath path, Instant lastModified, List<Entry> children);
}
