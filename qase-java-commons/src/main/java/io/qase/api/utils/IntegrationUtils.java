package io.qase.api.utils;

import io.qase.commons.annotation.CaseId;
import io.qase.commons.annotation.CaseTitle;
import io.qase.commons.annotation.QaseId;
import io.qase.commons.annotation.QaseTitle;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class IntegrationUtils {
    public static final List<String> CASE_TAGS = Collections.unmodifiableList(Arrays.asList("@caseId", "@tmsLink"));

    private IntegrationUtils() throws IllegalAccessException {
        throw new IllegalAccessException("Utils class");
    }

    public static String getStacktrace(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        throwable.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    public static Long getCaseId(Method method) {
        Long qaseId = getQaseId(method);
        if (qaseId != null) {
            return qaseId;
        }
        if (method.isAnnotationPresent(CaseId.class)) {
            return method
                    .getDeclaredAnnotation(CaseId.class).value();
        }
        return null;
    }

    public static String getCaseTitle(Method method) {
        String qaseTitle = getQaseTitle(method);
        if (qaseTitle != null) {
            return qaseTitle;
        }
        if (method.isAnnotationPresent(CaseTitle.class)) {
            return method.getDeclaredAnnotation(CaseTitle.class).value();
        }
        return method.getName();
    }

    private static Long getQaseId(Method method) {
        if (method.isAnnotationPresent(QaseId.class)) {
            return method
                    .getDeclaredAnnotation(QaseId.class).value();
        }
        return null;
    }

    private static String getQaseTitle(Method method) {
        if (method.isAnnotationPresent(QaseTitle.class)) {
            return method.getDeclaredAnnotation(QaseTitle.class).value();
        }
        return null;
    }
}
