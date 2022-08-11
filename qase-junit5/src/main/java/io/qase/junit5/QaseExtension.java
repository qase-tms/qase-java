package io.qase.junit5;

import io.qase.api.StepStorage;
import io.qase.api.config.QaseConfig;
import io.qase.client.model.ResultCreate;
import io.qase.client.model.ResultCreate.StatusEnum;
import io.qase.client.model.ResultCreateCase;
import io.qase.client.model.ResultCreateSteps;
import io.qase.api.services.QaseTestCaseListener;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.engine.TestSource;
import org.junit.platform.engine.support.descriptor.MethodSource;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.TestPlan;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;

import static io.qase.api.utils.IntegrationUtils.*;
import static io.qase.configuration.QaseModule.INJECTOR;
import static org.junit.platform.engine.TestExecutionResult.Status.SUCCESSFUL;

@Slf4j
public class QaseExtension implements TestExecutionListener {

    private static final String REPORTER_NAME = "JUnit 5";

    private final Set<TestIdentifier> startedTestIdentifiers =
        new ConcurrentSkipListSet<>(Comparator.comparing(TestIdentifier::hashCode));

    @Getter(lazy = true, value = AccessLevel.PRIVATE)
    private final QaseTestCaseListener qaseTestCaseListener = INJECTOR.getInstance(QaseTestCaseListener.class);

    static {
        System.setProperty(QaseConfig.QASE_CLIENT_REPORTER_NAME_KEY, REPORTER_NAME);
    }

    @Override
    public void executionStarted(TestIdentifier testIdentifier) {
        if (!testIdentifier.isTest()) {
            return;
        }
        getQaseTestCaseListener().onTestCaseStarted();
        startedTestIdentifiers.add(testIdentifier);
    }

    @Override
    public void executionFinished(TestIdentifier testIdentifier, TestExecutionResult testExecutionResult) {
        if (!testIdentifier.isTest() || !startedTestIdentifiers.contains(testIdentifier)) {
            return;
        }
        TestSource testSource = testIdentifier.getSource().orElse(null);
        Method testMethod = testSource instanceof MethodSource
            ? getMethod((MethodSource) testSource)
            : null;

        getQaseTestCaseListener()
            .onTestCaseFinished(resultCreate -> setupResultItem(resultCreate, testExecutionResult, testMethod));
    }

    @Override
    public void testPlanExecutionFinished(TestPlan testPlan) {
        getQaseTestCaseListener().onTestCasesSetFinished();
    }

    private void setupResultItem(
        ResultCreate resultCreate, TestExecutionResult testExecutionResult, Method testMethod
    ) {
        Long caseId = getCaseId(testMethod);
        String caseTitle = null;
        if (caseId == null) {
            caseTitle = getCaseTitle(testMethod);
        }
        StatusEnum status =
            testExecutionResult.getStatus() == SUCCESSFUL ? StatusEnum.PASSED : StatusEnum.FAILED;
        String comment = testExecutionResult.getThrowable()
            .flatMap(throwable -> Optional.of(throwable.toString())).orElse(null);
        Boolean isDefect = testExecutionResult.getThrowable()
            .flatMap(throwable -> Optional.of(throwable instanceof AssertionError))
            .orElse(false);
        String stacktrace = testExecutionResult.getThrowable()
            .flatMap(throwable -> Optional.of(getStacktrace(throwable))).orElse(null);
        LinkedList<ResultCreateSteps> steps = StepStorage.stopSteps();
        resultCreate
            ._case(caseTitle == null ? null : new ResultCreateCase().title(caseTitle))
            .caseId(caseId)
            .status(status)
            .comment(comment)
            .stacktrace(stacktrace)
            .steps(steps.isEmpty() ? null : steps)
            .defect(isDefect);
    }


    private Method getMethod(MethodSource testSource) {
        try {
            Class<?> testClass = Class.forName(testSource.getClassName());
            return Arrays.stream(testClass.getDeclaredMethods())
                    .filter(method -> MethodSource.from(method).equals(testSource))
                    .findFirst().orElse(null);
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
