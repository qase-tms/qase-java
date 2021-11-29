package io.qase.api.utils;

import io.qameta.allure.TmsLink;
import io.qase.api.annotation.CaseId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class IntegrationUtils {
    private static final Logger logger = LoggerFactory.getLogger(IntegrationUtils.class);
    public static final String ENABLE_KEY = "qase.enable";
    public static final String BULK_KEY = "qase.bulk";
    public static final String PROJECT_CODE_KEY = "qase.project.code";
    public static final String RUN_ID_KEY = "qase.run.id";
    public static final String API_TOKEN_KEY = "qase.api.token";
    public static final String QASE_URL_KEY = "qase.url";
    public static final String REQUIRED_PARAMETER_WARNING_MESSAGE = "Required parameter '{}' not specified";
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
        if (method.isAnnotationPresent(CaseId.class)) {
            return method
                    .getDeclaredAnnotation(CaseId.class).value();
        } else if (method.isAnnotationPresent(TmsLink.class)) {
            try {
                return Long.valueOf(method
                        .getDeclaredAnnotation(TmsLink.class).value());
            } catch (NumberFormatException e) {
                logger.error("String could not be parsed as Long", e);
            }
        }
        return null;
    }
}
