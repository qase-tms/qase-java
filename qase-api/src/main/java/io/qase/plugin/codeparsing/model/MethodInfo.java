package io.qase.plugin.codeparsing.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@ToString
public class MethodInfo {

    @Setter
    @Getter
    private String methodName;

    private final Collection<AnnotationInfo> annotatedWith = new ArrayList<>();

    public void addAnnotatedWith(AnnotationInfo annotationInfo) {
        annotatedWith.add(annotationInfo);
    }

    public Collection<AnnotationInfo> getAnnotatedWith() {
        return Collections.unmodifiableCollection(annotatedWith);
    }
}
