package sync.fs;

import java.util.List;

public interface SyncPath {
    SyncPath getSyncPath();
    SyncPathOperator getOperator();
    SyncPathExplorer getExplorer();
}
