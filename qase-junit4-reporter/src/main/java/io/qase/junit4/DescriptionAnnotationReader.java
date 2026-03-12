package io.qase.junit4;

import io.qase.commons.utils.AnnotationReader;
import org.junit.runner.Description;

/**
 * {@link AnnotationReader} implementation that delegates to a JUnit4 {@link Description}.
 *
 * <p>Note: {@link Description#getAnnotation(Class)} returns {@code null} for generated
 * Descriptions used in parameterized tests — this is expected behaviour and callers
 * must handle {@code null} returns gracefully.
 */
public class DescriptionAnnotationReader implements AnnotationReader {

    private final Description description;

    public DescriptionAnnotationReader(Description description) {
        this.description = description;
    }

    @Override
    public <T extends java.lang.annotation.Annotation> T getAnnotation(Class<T> annotationType) {
        return description.getAnnotation(annotationType);
    }
}
