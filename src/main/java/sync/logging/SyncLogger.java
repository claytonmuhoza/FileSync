package sync.logging;

public interface SyncLogger {
    void message(String message);
    void info(String message);
    void warning(String message);
    void error(String message);
    void success(String message);
}