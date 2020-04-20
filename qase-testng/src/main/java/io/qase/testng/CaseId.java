package io.qase.testng;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CaseId {
    long value();
    boolean hasDataSet() default false;
}
