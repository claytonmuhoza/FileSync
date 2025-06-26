package sync.registry;

import sync.fs.Entry;

/**
 * Interface de construction d’un registre de synchronisation ({@link Register}).
 *
 * Permet d’ajouter progressivement des entrées avant de construire un registre complet et prêt à l’emploi.
 */
public interface RegisterBuilder {

    /**
     * Ajoute une entrée au registre en cours de construction.
     *
     * @param entry l’entrée à ajouter
     * @return le builder courant pour chaînage
     */
    RegisterBuilder addEntry(Entry entry);

    /**
     * Construit l’instance finale de {@link Register}.
     *
     * @return le registre construit contenant toutes les entrées ajoutées
     */
    Register build();
}
