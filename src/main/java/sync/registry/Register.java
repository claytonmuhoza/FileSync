package sync.registry;

import sync.fs.Entry;

import java.util.List;
import java.util.Optional;

public interface Register {
    Optional<Entry> get(String relativePath);         // lire une entrée
    void put(Entry entry);                            // ajouter ou mettre à jour une entrée
    void remove(String relativePath);                 // supprimer une entrée
    List<Entry> getAllEntries();
}
