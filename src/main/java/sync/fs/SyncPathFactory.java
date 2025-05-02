package sync.fs;

public interface SyncPathFactory {
    SyncPath create(String path);
    boolean supports(String path);
}
