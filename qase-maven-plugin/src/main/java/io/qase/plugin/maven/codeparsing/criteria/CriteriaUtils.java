package io.qase.plugin.maven.codeparsing.criteria;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CriteriaUtils {

    public static MethodInfoCriteria withAnnotationAttributeValueIn(
        String annotationClassCanonicalName, String attributeName, Collection<?> attributeValues
    ) {
        return MethodInfoCriteria.builder()
            .annotationAttributePredicate((annotation, attribute, value) ->
                Objects.equals(annotation, annotationClassCanonicalName)
                    && Objects.equals(attribute, attributeName)
                    && attributeValues.contains(value)
            )
            .build();
    }
}
