package io.qase.commons.logger;

public class Logger {
    public static void info(String message, Object... argArray) {
        System.out.println(message);
    }

    public static void error(String message, Throwable e) {
        System.err.println(message);
    }

    public static void debug(String message) {
        System.out.println(message);
    }
}
