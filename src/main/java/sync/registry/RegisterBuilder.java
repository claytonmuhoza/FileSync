package sync.registry;

import sync.fs.Entry;

public interface RegisterBuilder {
    RegisterBuilder addEntry(Entry entry);
    Register build();
}
