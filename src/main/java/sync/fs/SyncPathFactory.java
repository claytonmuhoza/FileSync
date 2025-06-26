package sync.fs;

/**
 * Interface représentant une fabrique de chemins synchronisables.
 *
 */
public interface SyncPathFactory {

    /**
     * Crée un {@link SyncPath} à partir d’un chemin donné.
     *
     * @param path le chemin source sous forme de chaîne
     * @return une instance de {@link SyncPath}
     * @throws IllegalArgumentException si le chemin n’est pas supporté
     */
    SyncPath create(String path);

    /**
     * Indique si cette fabrique peut gérer le chemin fourni.
     *
     * @param path le chemin à tester
     * @return vrai si ce chemin est pris en charge par la fabrique
     */
    boolean supports(String path);
}
