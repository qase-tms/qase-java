package io.qase.api.utils;

import io.qase.api.annotation.Qase;
import io.qase.client.model.ResultCreate;
import io.qase.client.model.ResultCreateCase;
import lombok.experimental.UtilityClass;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static io.qase.api.annotation.Qase.DEFAULT_VALUE;

@UtilityClass
public final class IntegrationUtils {
    public static final List<String> CASE_TAGS = Collections.unmodifiableList(Arrays.asList("@caseId", "@tmsLink"));

    public static String getStacktrace(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        throwable.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    public static ResultCreate enrichResult(ResultCreate resultCreate, Qase qaseAnnotation) {
        ResultCreateCase aCase = new ResultCreateCase();
        if (!qaseAnnotation.title().equals(DEFAULT_VALUE)) {
            aCase.title(qaseAnnotation.title());
        }
        if (!qaseAnnotation.description().equals(DEFAULT_VALUE)) {
            aCase.description(qaseAnnotation.description());
        }
        if (!qaseAnnotation.layer().equals(DEFAULT_VALUE)) {
            aCase.layer(qaseAnnotation.layer());
        }
        if (!qaseAnnotation.severity().equals(DEFAULT_VALUE)) {
            aCase.severity(qaseAnnotation.severity());
        }
        if (qaseAnnotation.testId() != 0L) {
            resultCreate
                    .caseId(qaseAnnotation.testId());
        }
        return resultCreate._case(aCase);
    }
}
