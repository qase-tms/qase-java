package io.qase.api.exceptions;

/**
 * Wraps a {@link QaseException} with an unchecked exception.
 * The exception is available via {@link #getCause()}.
 * */
public class UncheckedQaseException extends RuntimeException {

    public UncheckedQaseException(QaseException qaseException) {
        super(qaseException);
    }
}
