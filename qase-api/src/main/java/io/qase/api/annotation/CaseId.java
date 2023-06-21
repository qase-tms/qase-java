package io.qase.api.annotation;

import java.lang.annotation.*;

/**
 * @deprecated use {@link QaseId} instead.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Deprecated
public @interface CaseId {
    long value();
}
