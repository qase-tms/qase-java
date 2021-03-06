package io.qase.junit5;

import io.qameta.allure.TmsLink;
import io.qase.api.QaseApi;
import io.qase.api.annotation.CaseId;
import io.qase.api.enums.RunResultStatus;
import io.qase.api.exceptions.QaseException;
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

import static io.qase.api.enums.RunResultStatus.failed;
import static io.qase.api.enums.RunResultStatus.passed;
import static io.qase.api.utils.IntegrationUtils.*;
import static org.junit.platform.engine.TestExecutionResult.Status.SUCCESSFUL;

public class QaseExtension implements TestExecutionListener {
    private static final Logger logger = LoggerFactory.getLogger(QaseExtension.class);
    private boolean isEnabled;
    private String projectCode;
    private String runId;
    private QaseApi qaseApi;
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

        String qaseUrl = System.getProperty(QASE_URL_KEY);
        if (qaseUrl != null) {
            qaseApi = new QaseApi(apiToken, qaseUrl);
        } else {
            qaseApi = new QaseApi(apiToken);
        }

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
                } catch (QaseException e) {
                    logger.error(e.getMessage());
                } catch (NumberFormatException e) {
                    logger.error("String could not be parsed as Long", e);
                }
            }
        }
    }

    private void sendResults(TestExecutionResult testExecutionResult, Duration duration, Method testMethod) {
        if (testMethod != null) {
            CaseId caseId = testMethod.getAnnotation(CaseId.class);
            TmsLink tmsLink = testMethod.getAnnotation(TmsLink.class);
            if (caseId != null || tmsLink != null) {
                RunResultStatus runResultStatus =
                        testExecutionResult.getStatus() == SUCCESSFUL ? passed : failed;
                String comment = testExecutionResult.getThrowable()
                        .flatMap(throwable -> Optional.of(throwable.toString())).orElse(null);
                Boolean isDefect = testExecutionResult.getThrowable()
                        .flatMap(throwable -> Optional.of(throwable instanceof AssertionError))
                        .orElse(false);
                String stacktrace = testExecutionResult.getThrowable()
                        .flatMap(throwable -> Optional.of(getStacktrace(throwable))).orElse(null);
                try {
                    qaseApi.testRunResults().create(projectCode, Long.parseLong(runId),
                            caseId != null ? caseId.value() : Long.parseLong(tmsLink.value()),
                            runResultStatus, duration, null, comment, stacktrace, isDefect);
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
