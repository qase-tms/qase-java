package io.qase.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonUtils {

    @SafeVarargs
    public static <T> Optional<T> getFirstNonNullResult(Supplier<T>... suppliers) {
        Iterable<Function<Void, T>> wrappedSuppliers = Arrays.stream(suppliers)
            .map(CommonUtils::convertSupplierToFunction)
            .collect(Collectors.toList());
        return getFirstNonNullResult(null, wrappedSuppliers);
    }

    @SafeVarargs
    public static <I, O> Optional<O> getFirstNonNullResult(
        I inputFunctionsArgument, Function<I, O>... argumentFunctions
    ) {
        return getFirstNonNullResult(inputFunctionsArgument, Arrays.asList(argumentFunctions));
    }

    public static <I, O> Optional<O> getFirstNonNullResult(
        I inputFunctionsArgument, Iterable<Function<I, O>> argumentFunctions
    ) {
        for (Function<I, O> function : argumentFunctions) {
            O outputResult = function.apply(inputFunctionsArgument);
            if (outputResult != null) {
                return Optional.of(outputResult);
            }
        }

        return Optional.empty();
    }

    private static <O> Function<Void, O> convertSupplierToFunction(Supplier<O> supplier) {
        return aVoid -> supplier.get();
    }
}
