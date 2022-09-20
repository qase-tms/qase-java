package io.qase.plugin.gradle.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Optional;
import java.util.function.Supplier;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Utils {

    public static <T> T getFirstNonNullOrNull(Iterable<Supplier<T>> itemSuppliers) {
        for (Supplier<T> itemSupplier : itemSuppliers) {
            T item = itemSupplier.get();
            if (item != null) {
                return item;
            }
        }

        return null;
    }

    public static <T> T getFirstNonNullOrDefault(Iterable<Supplier<T>> itemSuppliers, T defaultItem) {
        return Optional.ofNullable(getFirstNonNullOrNull(itemSuppliers)).orElse(defaultItem);
    }
}
