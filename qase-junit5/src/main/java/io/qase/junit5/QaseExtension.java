package io.qase.junit5;

import io.qase.api.QaseClient;
import io.qase.api.StepStorage;
import io.qase.api.exceptions.QaseException;
import io.qase.client.ApiClient;
import io.qase.client.api.ResultsApi;
import io.qase.client.model.ResultCreate;
import io.qase.client.model.ResultCreate.StatusEnum;
import io.qase.client.model.ResultCreateBulk;
import io.qase.client.model.ResultCreateCase;
import io.qase.client.model.ResultCreateSteps;
import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.engine.TestSource;
import org.junit.platform.engine.support.descriptor.MethodSource;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.TestPlan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static io.qase.api.Constants.X_CLIENT_REPORTER;
import static io.qase.api.QaseClient.getConfig;
import static io.qase.api.utils.IntegrationUtils.*;
import static org.junit.platform.engine.TestExecutionResult.Status.SUCCESSFUL;

public class QaseExtension implements TestExecutionListener {
    private static final Logger logger = LoggerFactory.getLogger(QaseExtension.class);
    private final ApiClient apiClient = QaseClient.getApiClient();
    private final ResultsApi resultsApi = new ResultsApi(apiClient);
    private final Map<TestIdentifier, Long> startTime = new ConcurrentHashMap<>();
    private final ResultCreateBulk resultCreateBulk = new ResultCreateBulk();

    public QaseExtension() {
        apiClient.addDefaultHeader(X_CLIENT_REPORTER, "JUnit 5");
    }

    @Override
    public void executionStarted(TestIdentifier testIdentifier) {
        if (QaseClient.isEnabled() && testIdentifier.isTest()) {
            startTime.put(testIdentifier, System.currentTimeMillis());
        }
    }

    @Override
    public void executionFinished(TestIdentifier testIdentifier, TestExecutionResult testExecutionResult) {
        if (!QaseClient.isEnabled() || !testIdentifier.isTest()
                || !startTime.containsKey(testIdentifier)) {
            return;
        }
        Duration duration = Duration.ofMillis(System.currentTimeMillis() - this.startTime.remove(testIdentifier));
        TestSource testSource = testIdentifier.getSource().orElse(null);
        Method testMethod = null;
        if (testSource instanceof MethodSource) {
            testMethod = getMethod((MethodSource) testSource);
        }

        if (getConfig().useBulk()) {
            addBulkResult(testExecutionResult, duration, testMethod);
        } else {
            sendResults(testExecutionResult, duration, testMethod);
        }
    }

    @Override
    public void testPlanExecutionFinished(TestPlan testPlan) {
        if (getConfig().useBulk()) {
            sendBulkResult();
        }
    }

    private void addBulkResult(TestExecutionResult testExecutionResult, Duration timeSpent, Method testMethod) {
        if (QaseClient.isEnabled() && testMethod != null) {
            resultCreateBulk.addResultsItem(getResultItem(testExecutionResult, timeSpent, testMethod));
        }
    }

    private void sendBulkResult() {
        if (!QaseClient.isEnabled()) {
            return;
        }
        try {
            resultsApi.createResultBulk(
                    getConfig().projectCode(),
                    getConfig().runId(),
                    resultCreateBulk
            );
            resultCreateBulk.getResults().clear();
        } catch (QaseException e) {
            logger.error(e.getMessage());
        }
    }

    private void sendResults(TestExecutionResult testExecutionResult, Duration timeSpent, Method testMethod) {
        if (testMethod != null) {
            ResultCreate resultCreate = getResultItem(testExecutionResult, timeSpent, testMethod);
            try {
                resultsApi.createResult(getConfig().projectCode(),
                        getConfig().runId(),
                        resultCreate);
            } catch (QaseException e) {
                logger.error(e.getMessage());
            }
        }
    }

    private ResultCreate getResultItem(TestExecutionResult testExecutionResult, Duration timeSpent, Method testMethod) {
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
        LinkedList<ResultCreateSteps> steps = StepStorage.getSteps();
        return new ResultCreate()
                ._case(caseTitle == null ? null : new ResultCreateCase().title(caseTitle))
                .caseId(caseId)
                .status(status)
                .timeMs(timeSpent.getSeconds())
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
            logger.error(e.getMessage());
            return null;
        }
    }
}
