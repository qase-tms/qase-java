package io.qase.commons.annotation;

import io.qase.commons.models.annotation.Field;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface QaseFields {
    Field[] value();
}
