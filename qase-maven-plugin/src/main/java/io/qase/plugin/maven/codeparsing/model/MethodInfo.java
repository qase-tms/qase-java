package io.qase.plugin.maven.codeparsing.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class MethodInfo {

    @Setter
    @Getter
    private String methodName;

    private final Collection<AnnotationInfo> annotatedWithCollection = new ArrayList<>();

    public void addAnnotatedWith(AnnotationInfo annotationInfo) {
        annotatedWithCollection.add(annotationInfo);
    }

    public Collection<AnnotationInfo> getAnnotatedWithCollection() {
        return Collections.unmodifiableCollection(annotatedWithCollection);
    }
}
