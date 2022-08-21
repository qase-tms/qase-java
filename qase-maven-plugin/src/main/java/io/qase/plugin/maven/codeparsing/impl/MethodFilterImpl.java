package io.qase.plugin.maven.codeparsing.impl;

import io.qase.plugin.maven.codeparsing.model.ClassInfo;
import io.qase.plugin.maven.codeparsing.MethodFilter;
import io.qase.plugin.maven.codeparsing.criteria.MethodInfoCriteria;
import io.qase.plugin.maven.codeparsing.model.MethodInfo;
import io.qase.plugin.maven.util.TriPredicate;

import java.util.Collection;
import java.util.stream.Collectors;

public class MethodFilterImpl implements MethodFilter {

    @Override
    public Collection<MethodInfo> filterMethodsByCriteria(ClassInfo classInfo, MethodInfoCriteria methodInfoCriteria) {
        return classInfo.getMethods()
            .parallelStream()
            .filter(methodInfo -> satisfiesCriteria(methodInfo, methodInfoCriteria))
            .collect(Collectors.toList());
    }

    private boolean satisfiesCriteria(MethodInfo methodInfo, MethodInfoCriteria methodInfoCriteria) {
        TriPredicate<String, String, Object> annotationAttributePredicate =
            methodInfoCriteria.getAnnotationAttributePredicate();
        return methodInfo.getAnnotatedWithCollection().parallelStream().anyMatch(
            annotationInfo -> annotationInfo.getAttributes().entrySet().parallelStream()
                .anyMatch(entry -> annotationAttributePredicate.test(
                    annotationInfo.getAnnotationClassCanonicalName(), entry.getKey(), entry.getValue()
                ))
        );
    }
}
