package io.qase.api.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class IntegrationUtils {
    public static final String ENABLE_KEY = "qase.enable";
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
}
