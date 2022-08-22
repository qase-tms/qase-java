package io.qase.plugin.util;

public interface TriPredicate<T, U, R> {

    boolean test(T t, U u, R r);
}
