package sync.registry;

import sync.fs.Entry;
import sync.fs.RelativePath;

import java.util.List;
import java.util.Optional;

/**
 * Interface représentant un registre de synchronisation.
 *
 * Le registre conserve l’état (date de dernière modification, type) des fichiers ou dossiers
 * connus lors de la dernière synchronisation. Il permet de comparer les états actuels à ceux mémorisés.
 */
public interface Register {

    /**
     * Recherche une entrée dans le registre à partir d’un chemin relatif.
     *
     * @param relativePath le chemin relatif du fichier ou dossier
     * @return un {@link Optional} contenant l’entrée si elle existe, sinon vide
     */
    Optional<Entry> get(RelativePath relativePath);

    /**
     * Ajoute ou met à jour une entrée dans le registre.
     *
     * @param entry l’entrée à enregistrer
     */
    void put(Entry entry);

    /**
     * Supprime une entrée du registre.
     *
     * @param relativePath le chemin relatif de l’entrée à supprimer
     */
    void remove(RelativePath relativePath);

    /**
     * Retourne toutes les entrées actuellement enregistrées.
     *
     * @return la liste des entrées du registre
     */
    List<Entry> getAllEntries();
}
