package sync.fs;

import sync.fs.local.LocalSyncPath;
import sync.fs.local.LocalSyncPathFactory;

import java.util.ArrayList;
import java.util.List;
// import sync.fs.webdav.WebDavSyncPath;

public class AutoSyncPathFactory implements SyncPathFactory {
    private static SyncPathFactory uniquePathFactory;
    private final List<SyncPathFactory> delegates = new ArrayList<>();
    private AutoSyncPathFactory() {

    }
    public static SyncPathFactory getInstance() {
        if (uniquePathFactory == null) {
            uniquePathFactory = new AutoSyncPathFactory();
        }
        return uniquePathFactory;
    }
    @Override
    public SyncPath create(String path) {
        if (path.startsWith("http://") || path.startsWith("https://")) {
            // TODO : retourner une implémentation WebDAV
            throw new UnsupportedOperationException("WebDavSyncPath non encore implémenté.");
            // return webDavSyncPathFactory.create(path);
        } else {
            return new LocalSyncPathFactory().create(path);
        }
    }
    @Override
    public boolean supports(String path) {
        return delegates.stream().anyMatch(f -> f.supports(path));
    }
}
