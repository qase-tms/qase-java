package io.qase.plugin.maven.util;

public interface TriPredicate<T, U, R> {

    boolean test(T t, U u, R r);
}
