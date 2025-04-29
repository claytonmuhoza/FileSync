package sync.registry;

import sync.fs.Entry;

import java.util.*;

public class RegisterStd implements Register {
    private final Map<String, Entry> entries = new HashMap<>();

    @Override
    public Optional<Entry> get(String relativePath) {
        return Optional.ofNullable(entries.get(relativePath));
    }

    @Override
    public void put(Entry entry) {
        entries.put(entry.getRelativePath().toString(), entry);
    }

    @Override
    public void remove(String relativePath) {
        entries.remove(relativePath);
    }


    @Override
    public List<Entry> getAllEntries() {
        return new ArrayList<>(entries.values());
    }
}
