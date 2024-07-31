package io.qase.api.annotation;

import java.lang.annotation.*;


/**
 * @deprecated use {@link QaseTitle} instead.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CaseTitle {
    String value();
}
