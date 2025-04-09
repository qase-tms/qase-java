package io.qase.commons.logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class Logger {
    // Singleton instance
    private static volatile Logger INSTANCE;

    // Lock for file access
    private final ReentrantLock fileLock = new ReentrantLock();

    // Default configuration
    private static final String LOG_DIRECTORY = "logs";
    private static final String DEFAULT_LOG_FILE = "log.txt";
    private static final DateTimeFormatter DEFAULT_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    // Configuration parameters
    private String logFile = LOG_DIRECTORY + File.separator + DEFAULT_LOG_FILE;
    private DateTimeFormatter dateFormat = DEFAULT_DATE_FORMAT;
    private long maxFileSize = 100 * 1024 * 1024; // 100MB default
    private int maxBackupFiles = 5;

    // Log level enum
    public enum LogLevel {
        OFF(0),
        ERROR(1),
        WARN(2),
        INFO(3),
        DEBUG(4),
        TRACE(5);

        private final int value;

        LogLevel(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    // ANSI color codes for console output
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_BLUE = "\u001B[34m";

    // MDC context for the current thread
    private static final ThreadLocal<Map<String, String>> MDC_CONTEXT = ThreadLocal.withInitial(HashMap::new);

    // Volatile for thread visibility
    private volatile LogLevel globalLogLevel = LogLevel.INFO;
    private final Map<String, LogLevel> packageLogLevels = new HashMap<>();

    // Asynchronous logging
    private final BlockingQueue<LogMessage> messageQueue = new LinkedBlockingQueue<>();
    private final Thread loggerThread;
    private final AtomicBoolean running = new AtomicBoolean(true);

    // Statistics
    private long errorCount = 0;
    private long warnCount = 0;
    private long infoCount = 0;
    private long debugCount = 0;
    private long traceCount = 0;

    // Log message class for async queue
    private static class LogMessage {
        final LogLevel level;
        final String message;
        final Throwable throwable;
        final Map<String, String> mdcContext;
        final String callerClass;
        final LocalDateTime timestamp;

        LogMessage(LogLevel level, String message, Throwable throwable,
                   Map<String, String> mdcContext, String callerClass) {
            this.level = level;
            this.message = message;
            this.throwable = throwable;
            this.mdcContext = new HashMap<>(mdcContext);
            this.callerClass = callerClass;
            this.timestamp = LocalDateTime.now();
        }
    }

    private Logger() {
        // Start async logger thread
        loggerThread = new Thread(() -> {
            while (running.get() || !messageQueue.isEmpty()) {
                try {
                    LogMessage message = messageQueue.take();
                    processLogMessage(message);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("Logger thread interrupted: " + e.getMessage());
                } catch (Exception e) {
                    System.err.println("Error in logger thread: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
        loggerThread.setName("EnhancedLogger-Thread");
        loggerThread.setDaemon(true);
        loggerThread.start();
    }

    // Double-checked locking singleton pattern
    public static Logger getInstance() {
        if (INSTANCE == null) {
            synchronized (Logger.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Logger();
                }
            }
        }
        return INSTANCE;
    }

    // Configuration methods
    public synchronized void setGlobalLogLevel(LogLevel logLevel) {
        this.globalLogLevel = logLevel;
    }

    public synchronized void setPackageLogLevel(String packageName, LogLevel logLevel) {
        packageLogLevels.put(packageName, logLevel);
    }

    public synchronized void setLogFile(String logFile) {
        this.logFile = logFile;
    }

    public synchronized void setDateFormat(DateTimeFormatter dateFormat) {
        this.dateFormat = dateFormat;
    }

    public synchronized void setMaxFileSize(long maxFileSize) {
        this.maxFileSize = maxFileSize;
    }

    public synchronized void setMaxBackupFiles(int maxBackupFiles) {
        this.maxBackupFiles = maxBackupFiles;
    }

    // MDC context methods
    public static void putMdc(String key, String value) {
        MDC_CONTEXT.get().put(key, value);
    }

    public static String getMdc(String key) {
        return MDC_CONTEXT.get().get(key);
    }

    public static void clearMdc() {
        MDC_CONTEXT.get().clear();
    }

    public static void removeMdc(String key) {
        MDC_CONTEXT.get().remove(key);
    }

    // Basic logging methods
    public void error(String message) {
        log(LogLevel.ERROR, message, null);
    }

    public void error(String message, Throwable throwable) {
        log(LogLevel.ERROR, message, throwable);
    }

    public void error(String format, Object... args) {
        if (isEnabled(LogLevel.ERROR)) {
            log(LogLevel.ERROR, String.format(format, args), null);
        }
    }

    public void warn(String message) {
        log(LogLevel.WARN, message, null);
    }

    public void warn(String message, Throwable throwable) {
        log(LogLevel.WARN, message, throwable);
    }

    public void warn(String format, Object... args) {
        if (isEnabled(LogLevel.WARN)) {
            log(LogLevel.WARN, String.format(format, args), null);
        }
    }

    public void info(String message) {
        log(LogLevel.INFO, message, null);
    }

    public void info(String format, Object... args) {
        if (isEnabled(LogLevel.INFO)) {
            log(LogLevel.INFO, String.format(format, args), null);
        }
    }

    public void debug(String message) {
        log(LogLevel.DEBUG, message, null);
    }

    public void debug(String message, Throwable throwable) {
        log(LogLevel.DEBUG, message, throwable);
    }

    public void debug(String format, Object... args) {
        if (isEnabled(LogLevel.DEBUG)) {
            log(LogLevel.DEBUG, String.format(format, args), null);
        }
    }

    public void trace(String message) {
        log(LogLevel.TRACE, message, null);
    }

    public void trace(String format, Object... args) {
        if (isEnabled(LogLevel.TRACE)) {
            log(LogLevel.TRACE, String.format(format, args), null);
        }
    }

    // Check if log level is enabled for current context
    public boolean isEnabled(LogLevel level) {
        String callerClass = getCallerClassName();
        LogLevel effectiveLevel = getEffectiveLogLevel(callerClass);
        return level.getValue() <= effectiveLevel.getValue();
    }

    // Get effective log level based on package settings
    private LogLevel getEffectiveLogLevel(String className) {
        // Find most specific package that matches
        String bestMatch = "";
        LogLevel bestLevel = globalLogLevel;

        for (Map.Entry<String, LogLevel> entry : packageLogLevels.entrySet()) {
            String pkg = entry.getKey();
            if (className.startsWith(pkg) && pkg.length() > bestMatch.length()) {
                bestMatch = pkg;
                bestLevel = entry.getValue();
            }
        }

        return bestLevel;
    }

    // Main logging method
    private void log(LogLevel level, String message, Throwable throwable) {
        String callerClass = getCallerClassName();

        // Don't process if log level is not enabled
        if (level.getValue() > getEffectiveLogLevel(callerClass).getValue()) {
            return;
        }

        // Create message and add to queue
        Map<String, String> mdcCopy = new HashMap<>(MDC_CONTEXT.get());
        LogMessage logMessage = new LogMessage(level, message, throwable, mdcCopy, callerClass);

        // Update statistics
        updateStatistics(level);

        // Add to async queue
        messageQueue.add(logMessage);
    }

    // Process log message (called from async thread)
    private void processLogMessage(LogMessage message) {
        // Format message for output
        String formattedMessage = formatLogMessage(message);

        // Output to console with colors
        printToConsole(message.level, formattedMessage);

        // Write to file
        writeToFile(formattedMessage, message.throwable);
    }

    // Format log message with timestamp, level, context, etc.
    private String formatLogMessage(LogMessage message) {
        StringBuilder sb = new StringBuilder();

        // Add timestamp
        sb.append('[').append(message.timestamp.format(dateFormat)).append("] ");

        // Add log level
        sb.append('[').append(message.level).append("] ");

        // Add thread info
        sb.append('[').append("Thread-").append(Thread.currentThread().getId()).append("] ");

        // Add caller class
        sb.append('[').append(message.callerClass).append("] ");

        // Add MDC context if not empty
        if (!message.mdcContext.isEmpty()) {
            sb.append('[');
            boolean first = true;
            for (Map.Entry<String, String> entry : message.mdcContext.entrySet()) {
                if (!first) {
                    sb.append(", ");
                }
                sb.append(entry.getKey()).append('=').append(entry.getValue());
                first = false;
            }
            sb.append("] ");
        }

        // Add message
        sb.append(message.message);

        return sb.toString();
    }

    // Print to console with color
    private void printToConsole(LogLevel level, String message) {
        String colorCode;
        switch (level) {
            case ERROR:
                colorCode = ANSI_RED;
                break;
            case WARN:
                colorCode = ANSI_YELLOW;
                break;
            case INFO:
                colorCode = ANSI_GREEN;
                break;
            case DEBUG:
                colorCode = ANSI_CYAN;
                break;
            case TRACE:
                colorCode = ANSI_BLUE;
                break;
            default:
                colorCode = ANSI_RESET;
        }

        System.out.println(colorCode + message + ANSI_RESET);
    }

    // Write message to file with rotation
    private void writeToFile(String message, Throwable throwable) {
        fileLock.lock();
        try {
            createLogDirectory();

            File logFile = new File(this.logFile);

            // Check if rotation needed
            if (logFile.exists() && logFile.length() > maxFileSize) {
                rotateLogFiles();
            }

            try (FileWriter fw = new FileWriter(logFile, true);
                 PrintWriter pw = new PrintWriter(fw)) {

                pw.println(message);

                // Print stack trace if throwable is provided
                if (throwable != null) {
                    throwable.printStackTrace(pw);
                }

                pw.flush();

            } catch (IOException e) {
                System.err.println("Error writing to log file: " + e.getMessage());
            }
        } finally {
            fileLock.unlock();
        }
    }

    // Rotate log files
    private void rotateLogFiles() {
        // Delete oldest backup if it exists
        File oldestBackup = new File(logFile + "." + maxBackupFiles);
        if (oldestBackup.exists()) {
            oldestBackup.delete();
        }

        // Shift existing backups
        for (int i = maxBackupFiles - 1; i > 0; i--) {
            File file = new File(logFile + "." + i);
            if (file.exists()) {
                file.renameTo(new File(logFile + "." + (i + 1)));
            }
        }

        // Rename current log to .1
        File currentLog = new File(logFile);
        currentLog.renameTo(new File(logFile + ".1"));
    }

    // Get caller class name
    private String getCallerClassName() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String thisClassName = this.getClass().getName();

        for (int i = 2; i < stackTrace.length; i++) {
            String className = stackTrace[i].getClassName();
            if (!className.equals(thisClassName) && !className.startsWith("java.lang.Thread")) {
                return className;
            }
        }

        return "Unknown";
    }

    // Update statistics
    private synchronized void updateStatistics(LogLevel level) {
        switch (level) {
            case ERROR:
                errorCount++;
                break;
            case WARN:
                warnCount++;
                break;
            case INFO:
                infoCount++;
                break;
            case DEBUG:
                debugCount++;
                break;
            case TRACE:
                traceCount++;
                break;
        }
    }

    private void createLogDirectory() {
        File directory = new File(LOG_DIRECTORY);
        if (!directory.exists()) {
            boolean created = directory.mkdir();
            if (!created) {
                System.err.println("Can not create logs directory: " + LOG_DIRECTORY);
            }
        }
    }

    // Get statistics
    public synchronized Map<String, Long> getStatistics() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("ERROR", errorCount);
        stats.put("WARN", warnCount);
        stats.put("INFO", infoCount);
        stats.put("DEBUG", debugCount);
        stats.put("TRACE", traceCount);
        return stats;
    }

    // Shutdown logger properly
    public void shutdown() {
        running.set(false);
        loggerThread.interrupt();
        try {
            loggerThread.join(5000); // Wait up to 5 seconds for thread to finish
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Interrupted while shutting down logger");
        }
    }

    // Flush pending messages
    public void flush() {
        // Create a marker message and wait until it's processed
        final Object marker = new Object();
        final AtomicBoolean processed = new AtomicBoolean(false);

        try {
            // Add a special message as a marker
            log(LogLevel.INFO, "FLUSH-MARKER-" + marker.hashCode(), null);

            // Wait for queue to be processed
            while (!processed.get() && !messageQueue.isEmpty()) {
                Thread.sleep(10);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
