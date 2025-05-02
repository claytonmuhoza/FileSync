package sync.fs.local;

import sync.fs.SyncPath;
import sync.fs.SyncPathFactory;

public class LocalSyncPathFactory implements SyncPathFactory {
    @Override
    public SyncPath create(String path) {
        return new LocalSyncPath(path);
    }
    @Override
    public boolean supports(String path) {
        return !path.startsWith("http://") && !path.startsWith("https://");
    }
}
