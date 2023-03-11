package io.qase.api.annotation;

import java.lang.annotation.*;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Qase {
    String DEFAULT_VALUE = "!!!DEFAULT_VALUE!!!";

    long testId() default 0L;

    String title() default DEFAULT_VALUE;

    String layer() default DEFAULT_VALUE;

    String severity() default DEFAULT_VALUE;

    String description() default DEFAULT_VALUE;

}
