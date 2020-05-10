package io.qase.testng;

import io.qameta.allure.TmsLink;
import io.qase.api.QaseApi;
import io.qase.api.annotation.CaseId;
import io.qase.api.enums.RunResultStatus;
import io.qase.api.exceptions.QaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Optional;

import static io.qase.api.utils.IntegrationUtils.*;

public class QaseListener implements ITestListener {
    private static final Logger logger = LoggerFactory.getLogger(QaseListener.class);
    private boolean isEnabled;
    private String projectCode;
    private String runId;
    private QaseApi qaseApi;

    public QaseListener() {
        isEnabled = Boolean.parseBoolean(System.getProperty(ENABLE_KEY, "false"));
        if (!isEnabled) {
            return;
        }

        String apiToken = System.getProperty(API_TOKEN_KEY, System.getenv(API_TOKEN_KEY));
        if (apiToken == null) {
            isEnabled = false;
            logger.info(REQUIRED_PARAMETER_WARNING_MESSAGE, API_TOKEN_KEY);
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
            isEnabled = false;
            logger.info(REQUIRED_PARAMETER_WARNING_MESSAGE, PROJECT_CODE_KEY);
            return;
        }
        logger.info("Qase project code - {}", projectCode);

        runId = System.getProperty(RUN_ID_KEY, System.getenv(RUN_ID_KEY));
        if (runId == null) {
            isEnabled = false;
            logger.info(REQUIRED_PARAMETER_WARNING_MESSAGE, RUN_ID_KEY);
            return;
        }
        logger.info("Qase run id - {}", runId);
    }

    @Override
    public void onTestStart(ITestResult result) {
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        sendResult(result, RunResultStatus.passed);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        sendResult(result, RunResultStatus.failed);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    @Override
    public void onStart(ITestContext context) {
    }

    @Override
    public void onFinish(ITestContext context) {

    }

    private void sendResult(ITestResult result, RunResultStatus status) {
        if (!isEnabled) {
            return;
        }
        Duration timeSpent = Duration.ofMillis(result.getEndMillis() - result.getStartMillis());
        Optional<Throwable> resultThrowable = Optional.ofNullable(result.getThrowable());
        String comment = resultThrowable
                .flatMap(throwable -> Optional.of(throwable.toString())).orElse(null);
        Boolean isDefect = resultThrowable.flatMap(throwable -> Optional.of(throwable instanceof AssertionError))
                .orElse(false);
        String stacktrace = resultThrowable.flatMap(throwable -> Optional.of(getStacktrace(throwable))).orElse(null);
        Long caseId = getCaseId(result);
        if (caseId != null) {
            try {
                qaseApi.testRunResults()
                        .create(projectCode,
                                Long.parseLong(runId),
                                caseId, status,
                                timeSpent,
                                null,
                                comment,
                                stacktrace,
                                isDefect);
            } catch (QaseException e) {
                logger.error(e.getMessage());
            }
        }
    }

    private Long getCaseId(ITestResult result) {
        Method method = result.getMethod()
                .getConstructorOrMethod()
                .getMethod();
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
