package sync.logging;

public class ConsoleLogger implements SyncLogger {
    private static SyncLogger uniqueinstance;
    private ConsoleLogger() {}
    public static SyncLogger getInstance() {
        if (uniqueinstance == null)
        {
            uniqueinstance = new ConsoleLogger();
        }
        return uniqueinstance;
    }

    @Override
    public void info(String message) {
        System.out.println("[🟢 Info] " + message);
    }
    @Override
    public void success(String message) {
        System.out.println("[✅ Success] " + message);
    }
    @Override
    public void warning(String message) {
        System.out.println("[⚠️ Warning] " + message);
    }
    @Override
    public void message(String message) {
        System.out.println(message);
    }

    @Override
    public void error(String message) {
        System.err.println("[❌ Error] " + message);
    }
}
