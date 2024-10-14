package io.qase.junit4;


import io.qase.commons.StepStorage;
import io.qase.commons.annotation.CaseId;
import io.qase.commons.annotation.CaseTitle;
import io.qase.commons.annotation.QaseId;
import io.qase.commons.annotation.QaseTitle;
import io.qase.commons.config.QaseConfig;
import io.qase.api.services.QaseTestCaseListener;
import io.qase.client.v1.models.ResultCreate;
import io.qase.client.v1.models.ResultCreateCase;
import io.qase.client.v1.models.TestStepResultCreate;
import io.qase.junit4.guice.module.Junit4Module;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Set;

import static io.qase.api.utils.IntegrationUtils.getStacktrace;

@Slf4j
public class QaseListener extends RunListener {

    public static final String REPORTER_NAME = "JUnit 4";

    @Getter(lazy = true, value = AccessLevel.PRIVATE)
    private final QaseTestCaseListener qaseTestCaseListener = createQaseListener();

    private final Set<String> methods = new HashSet<>();

    static {
        System.setProperty(QaseConfig.QASE_CLIENT_REPORTER_NAME_KEY, REPORTER_NAME);
    }

    @Override
    public void testStarted(Description description) {
        getQaseTestCaseListener().onTestCaseStarted();
    }

    @Override
    public void testFinished(Description description) {
        if (addIfNotPresent(description)) {
            getQaseTestCaseListener().onTestCaseFinished(
                    resultCreate -> setupResultItem(resultCreate, description, "passed", null)
            );
        }
    }

    @Override
    public void testFailure(Failure failure) {
        if (addIfNotPresent(failure.getDescription())) {
            getQaseTestCaseListener().onTestCaseFinished(
                    resultCreate -> setupResultItem(resultCreate, failure.getDescription(), "failed", failure.getException())
            );
        }
    }

    @Override
    public void testAssumptionFailure(Failure failure) {
        if (addIfNotPresent(failure.getDescription())) {
            getQaseTestCaseListener().onTestCaseFinished(
                    resultCreate -> setupResultItem(resultCreate, failure.getDescription(), "skipped", null)
            );
        }
    }

    @Override
    public void testIgnored(Description description) {
        if (addIfNotPresent(description)) {
            getQaseTestCaseListener().onTestCaseFinished(
                    resultCreate -> setupResultItem(resultCreate, description, "skipped", null)
            );
        }
    }

    @Override
    public void testRunFinished(Result result) {
        getQaseTestCaseListener().onTestCasesSetFinished();
    }

    private boolean addIfNotPresent(Description description) {
        String methodFullName = description.getClassName() + description.getMethodName();
        if (methods.contains(methodFullName)) {
            return false;
        }
        methods.add(methodFullName);
        return true;
    }

    private ResultCreate setupResultItem(ResultCreate resultCreate, Description description, String status, Throwable error) {
        methods.add(description.getClassName() + description.getMethodName());
        Long caseId = getCaseId(description);
        String caseTitle = null;
        if (caseId == null) {
            caseTitle = getCaseTitle(description);
        }
        Optional<Throwable> optionalThrowable = Optional.ofNullable(error);
        String comment = optionalThrowable
                .flatMap(throwable -> Optional.of(throwable.toString())).orElse(null);
        Boolean isDefect = optionalThrowable
                .flatMap(throwable -> Optional.of(throwable instanceof AssertionError))
                .orElse(false);
        String stacktrace = optionalThrowable
                .flatMap(throwable -> Optional.of(getStacktrace(throwable))).orElse(null);
        LinkedList<TestStepResultCreate> steps = StepStorage.stopSteps();
        return resultCreate
                ._case(caseTitle == null ? null : new ResultCreateCase().title(caseTitle))
                .caseId(caseId)
                .status(status)
                .comment(comment)
                .stacktrace(stacktrace)
                .steps(steps.isEmpty() ? null : steps)
                .defect(isDefect);
    }

    private Long getCaseId(Description description) {
        Long qaseId = getQaseId(description);
        if (qaseId != null) {
            return qaseId;
        }
        CaseId caseIdAnnotation = description.getAnnotation(CaseId.class);
        return caseIdAnnotation != null ? caseIdAnnotation.value() : null;
    }

    private String getCaseTitle(Description description) {
        String qaseTitle = getQaseTitle(description);
        if (qaseTitle != null) {
            return qaseTitle;
        }
        CaseTitle caseTitleAnnotation = description.getAnnotation(CaseTitle.class);
        return caseTitleAnnotation != null ? caseTitleAnnotation.value() : null;
    }

    private Long getQaseId(Description description) {
        QaseId caseIdAnnotation = description.getAnnotation(QaseId.class);
        return caseIdAnnotation != null ? caseIdAnnotation.value() : null;
    }

    private String getQaseTitle(Description description) {
        QaseTitle caseTitleAnnotation = description.getAnnotation(QaseTitle.class);
        return caseTitleAnnotation != null ? caseTitleAnnotation.value() : null;
    }

    private static QaseTestCaseListener createQaseListener() {
        return Junit4Module.getInjector().getInstance(QaseTestCaseListener.class);
    }
}
