package sync.registry;

import sync.fs.Entry;
import sync.fs.RelativePath;

import java.util.*;

public class RegisterStd implements Register {
    private final Map<String, Entry> entries = new HashMap<>();

    @Override
    public Optional<Entry> get(RelativePath relativePath) {
        return Optional.ofNullable(entries.get(relativePath.getPath()));
    }

    @Override
    public void put(Entry entry) {
        entries.put(entry.getRelativePath().toString(), entry);
    }

    @Override
    public void remove(RelativePath relativePath) {
        entries.remove(relativePath.getPath());
    }


    @Override
    public List<Entry> getAllEntries() {
        return new ArrayList<>(entries.values());
    }
}
