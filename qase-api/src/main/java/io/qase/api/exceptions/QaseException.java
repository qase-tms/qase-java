package io.qase.api.exceptions;

public class QaseException extends RuntimeException {
    public QaseException(String message) {
        super(message);
    }

    public QaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public QaseException(String message, Object... params) {
        super(String.format(message, params));
    }
}
