package io.qase.commons;

public class QaseException extends Exception {
    public QaseException(String message) {
        super(message);
    }

    public QaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
