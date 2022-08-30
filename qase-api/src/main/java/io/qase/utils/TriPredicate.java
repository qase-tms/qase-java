package io.qase.utils;

public interface TriPredicate<T, U, R> {

    boolean test(T t, U u, R r);
}
