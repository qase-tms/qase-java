package io.qase.junit5;

import io.qase.api.exceptions.QaseException;
import io.qase.client.ApiClient;
import io.qase.client.api.ResultsApi;
import io.qase.client.model.ResultCreate;
import io.qase.client.model.ResultCreate.StatusEnum;
import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.engine.TestSource;
import org.junit.platform.engine.support.descriptor.MethodSource;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static io.qase.api.utils.IntegrationUtils.*;
import static org.junit.platform.engine.TestExecutionResult.Status.SUCCESSFUL;

public class QaseExtension implements TestExecutionListener {
    private static final Logger logger = LoggerFactory.getLogger(QaseExtension.class);
    private boolean isEnabled;
    private String projectCode;
    private String runId;
    private final ApiClient apiClient = new ApiClient();
    private final ResultsApi resultsApi = new ResultsApi(apiClient);
    private final Map<TestIdentifier, Long> startTime = new ConcurrentHashMap<>();

    public QaseExtension() {
        isEnabled = Boolean.parseBoolean(System.getProperty(ENABLE_KEY, "false"));
        if (!isEnabled) {
            return;
        }

        String apiToken = System.getProperty(API_TOKEN_KEY, System.getenv(API_TOKEN_KEY));
        if (apiToken == null) {
            logger.error(REQUIRED_PARAMETER_WARNING_MESSAGE, API_TOKEN_KEY);
            isEnabled = false;
            return;
        }

        String qaseUrl = System.getProperty(QASE_URL_KEY, System.getenv(API_TOKEN_KEY));
        if (qaseUrl != null) {
            apiClient.setBasePath(qaseUrl);
        }
        apiClient.setApiKey(apiToken);

        projectCode = System.getProperty(PROJECT_CODE_KEY, System.getenv(PROJECT_CODE_KEY));
        if (projectCode == null) {
            logger.error(REQUIRED_PARAMETER_WARNING_MESSAGE, PROJECT_CODE_KEY);
            isEnabled = false;
            return;
        }
        logger.info("Qase project code - {}", projectCode);

        runId = System.getProperty(RUN_ID_KEY, System.getenv(RUN_ID_KEY));
        if (runId == null) {
            logger.error(REQUIRED_PARAMETER_WARNING_MESSAGE, RUN_ID_KEY);
            isEnabled = false;
            return;
        }

        logger.info("Qase run id - {}", runId);
    }

    @Override
    public void executionStarted(TestIdentifier testIdentifier) {
        if (isEnabled && testIdentifier.isTest()) {
            startTime.put(testIdentifier, System.currentTimeMillis());
        }
    }

    @Override
    public void executionFinished(TestIdentifier testIdentifier, TestExecutionResult testExecutionResult) {
        if (isEnabled && testIdentifier.isTest()) {
            Duration duration = Duration.ofMillis(System.currentTimeMillis() - this.startTime.remove(testIdentifier));
            TestSource testSource = testIdentifier.getSource().orElse(null);
            if (testSource instanceof MethodSource) {
                try {
                    Method testMethod = getMethod((MethodSource) testSource);
                    sendResults(testExecutionResult, duration, testMethod);
                } catch (NumberFormatException e) {
                    logger.error("String could not be parsed as Long", e);
                }
            }
        }
    }

    private void sendResults(TestExecutionResult testExecutionResult, Duration timeSpent, Method testMethod) {
        if (testMethod != null) {
            Long caseId = getCaseId(testMethod);
            if (caseId != null) {
                StatusEnum status =
                        testExecutionResult.getStatus() == SUCCESSFUL ? StatusEnum.PASSED : StatusEnum.FAILED;
                String comment = testExecutionResult.getThrowable()
                        .flatMap(throwable -> Optional.of(throwable.toString())).orElse(null);
                Boolean isDefect = testExecutionResult.getThrowable()
                        .flatMap(throwable -> Optional.of(throwable instanceof AssertionError))
                        .orElse(false);
                String stacktrace = testExecutionResult.getThrowable()
                        .flatMap(throwable -> Optional.of(getStacktrace(throwable))).orElse(null);
                try {
                    resultsApi.createResult(projectCode,
                            runId,
                            new ResultCreate()
                                    .caseId(caseId)
                                    .status(status)
                                    .time(timeSpent.getSeconds())
                                    .comment(comment)
                                    .stacktrace(stacktrace)
                                    .defect(isDefect));
                } catch (QaseException e) {
                    logger.error(e.getMessage());
                } catch (NumberFormatException e) {
                    logger.error("String could not be parsed as Long", e);
                }
            }
        }
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
