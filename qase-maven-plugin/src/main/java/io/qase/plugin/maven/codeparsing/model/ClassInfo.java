package io.qase.plugin.maven.codeparsing.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@Setter
public class ClassInfo {

    @Getter
    private String canonicalName;

    private final Collection<MethodInfo> methods = new ArrayList<>();

    public void addMethod(MethodInfo methodInfo) {
        methods.add(methodInfo);
    }

    public Collection<MethodInfo> getMethods() {
        return Collections.unmodifiableCollection(methods);
    }
}
