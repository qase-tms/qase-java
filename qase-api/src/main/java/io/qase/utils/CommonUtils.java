package io.qase.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Optional;
import java.util.function.Supplier;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonUtils {

    @SafeVarargs
    public static <T> Optional<T> getFirstNonNullResult(Supplier<T>... suppliers) {
        for (Supplier<T> supplier : suppliers) {
            T item = supplier.get();
            if (item != null) {
                return Optional.of(item);
            }
        }

        return Optional.empty();
    }
}
