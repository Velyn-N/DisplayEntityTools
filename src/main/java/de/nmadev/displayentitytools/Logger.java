package de.nmadev.displayentitytools;

public class Logger {

    private final java.util.logging.Logger baseLogger;
    private final boolean isDebugMode;

    public Logger(java.util.logging.Logger baseLogger, boolean isDebugMode) {
        this.baseLogger = baseLogger;
        this.isDebugMode = isDebugMode;
    }

    public void info(String format, Object... args) {
        baseLogger.info(String.format(format, args));
    }

    public void warn(String format, Object... args) {
        baseLogger.warning(String.format(format, args));
    }

    public void error(String format, Object... args) {
        this.severe(format, args);
    }

    public void severe(String format, Object... args) {
        baseLogger.severe(String.format(format, args));
    }

    public void debug(String format, Object... args) {
        if (isDebugMode) {
            info(String.format("[DEBUG] " + format, args));
        }
    }
}
