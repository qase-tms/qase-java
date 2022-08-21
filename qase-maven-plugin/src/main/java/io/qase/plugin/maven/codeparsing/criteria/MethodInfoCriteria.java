package io.qase.plugin.maven.codeparsing.criteria;

import io.qase.plugin.maven.util.TriPredicate;
import lombok.Builder;
import lombok.Getter;

@Builder
public class MethodInfoCriteria {

    /**
     * First parameter - the canonical name of an annotation class
     * Second parameter - the annotation attribute name
     * Third parameter - the attribute value
     * */
    @Builder.Default
    @Getter
    private TriPredicate<String, String, Object> annotationAttributePredicate = anything();

    private static <T, U, R> TriPredicate<T, U, R> anything() {
        return (first, second, third) -> true;
    }
}
