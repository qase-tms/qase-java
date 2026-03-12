package io.qase.commons.utils;

/**
 * Abstraction for reading Qase annotations from a test method or test description.
 *
 * <p>Implementations live in reporter modules (e.g., {@code DescriptionAnnotationReader}
 * in {@code qase-junit4-reporter}); this interface itself carries zero framework imports,
 * keeping {@code qase-java-commons} free of any JUnit4/JUnit5/TestNG compile dependency.
 *
 * <p>Callers must handle {@code null} returns — implementations may return {@code null}
 * when an annotation is not present (e.g., for generated JUnit4 {@code Description} objects).
 */
public interface AnnotationReader {

    /**
     * Returns the annotation of the specified type, or {@code null} if not present.
     *
     * @param annotationType the annotation class to look up
     * @param <T>            the annotation type
     * @return the annotation instance, or {@code null} if absent
     */
    <T extends java.lang.annotation.Annotation> T getAnnotation(Class<T> annotationType);
}
