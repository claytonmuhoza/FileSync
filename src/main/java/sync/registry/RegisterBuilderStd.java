package sync.registry;

import sync.fs.Entry;

import java.util.HashMap;
import java.util.Map;

public class RegisterBuilderStd implements RegisterBuilder {
    private final Map<String, Entry> entries = new HashMap<>();

    @Override
    public RegisterBuilder addEntry(Entry entry) {
        entries.put(entry.getRelativePath().toString(), entry);
        return this;
    }

    @Override
    public Register build() {
        RegisterStd register = new RegisterStd();
        for (Entry entry : entries.values()) {
            register.put(entry);
        }
        return register;
    }
}
