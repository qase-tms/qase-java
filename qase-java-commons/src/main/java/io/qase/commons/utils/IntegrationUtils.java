package io.qase.commons.utils;

import io.qase.commons.annotation.*;
import io.qase.commons.models.annotation.Field;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public final class IntegrationUtils {
    private IntegrationUtils() throws IllegalAccessException {
        throw new IllegalAccessException("Utils class");
    }

    public static String getStacktrace(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        throwable.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    public static List<Long> getCaseIds(Method method) {
        Long qaseId = getQaseId(method);
        if (qaseId != null) {
            return Collections.singletonList(qaseId);
        }

        List<Long> qaseIds = getQaseIds(method);
        if (qaseIds != null) {
            return qaseIds;
        }

        if (method.isAnnotationPresent(CaseId.class)) {
            return Collections.singletonList(method
                    .getDeclaredAnnotation(CaseId.class).value());
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

    private static List<Long> getQaseIds(Method method) {
        if (method.isAnnotationPresent(QaseIds.class)) {
            return Arrays.stream(method
                    .getDeclaredAnnotation(QaseIds.class).value())
                    .boxed()
                    .collect(Collectors.toList());
        }

        return null;
    }

    private static String getQaseTitle(Method method) {
        if (method.isAnnotationPresent(QaseTitle.class)) {
            return method.getDeclaredAnnotation(QaseTitle.class).value();
        }
        return null;
    }

    public static Map<String, String> getQaseFields(Method method) {
        Map<String, String> fields = new HashMap<>();

        if (method.isAnnotationPresent(QaseFields.class)) {
            QaseFields annotation = method.getDeclaredAnnotation(QaseFields.class);
            if (annotation != null) {
                for (Field field : annotation.value()) {
                    fields.put(field.name(), field.value());
                }
            }
        }

        return fields;
    }

    public static boolean getQaseIgnore(Method method) {
        return method.isAnnotationPresent(QaseIgnore.class);
    }

    public static String getQaseSuite(Method method) {
        if (method.isAnnotationPresent(QaseSuite.class)) {
            return method.getDeclaredAnnotation(QaseSuite.class).value();
        }
        return null;
    }

    public static String generateSignature(Method testMethod, List<Long> qaseIds, Map<String, String> parameters) {
        String packageName = testMethod.getDeclaringClass().getPackage().getName().toLowerCase().replace('.', ':');
        String className = testMethod.getDeclaringClass().getSimpleName().toLowerCase();
        String methodName = testMethod.getName().toLowerCase();

        ArrayList<String> suites = new ArrayList<>();
        suites.add(packageName);
        suites.add(className);
        suites.add(methodName);

        return StringUtils.generateSignature(
                qaseIds != null ? new ArrayList<>(qaseIds) : new ArrayList<>(),
                suites,
                parameters);
    }
}
