package io.qase.testng;

import io.qase.api.QaseApi;
import io.qase.api.enums.RunResultStatus;
import io.qase.api.exceptions.QaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.lang.reflect.Method;
import java.time.Duration;

public class QaseListener implements ITestListener {
    private static final Logger logger = LoggerFactory.getLogger(QaseListener.class);
    private static final String REQUIRED_PARAMETER_WARNING_MESSAGE = "Required parameter '{}' not specified";
    private final boolean isEnabled;
    private String projectCode;
    private String runId;
    private QaseApi qaseApi;

    private static final String PROJECT_CODE_KEY = "qase.project.code";

    private static final String RUN_ID_KEY = "qase.run.id";

    private static final String API_TOKEN_KEY = "qase.api.token";

    public QaseListener() {
        isEnabled = Boolean.parseBoolean(System.getProperty("qase.enable", "false"));
        if (!isEnabled) {
            return;
        }

        String apiToken = System.getProperty(API_TOKEN_KEY, System.getenv(API_TOKEN_KEY));
        if (apiToken == null) {
            logger.info(REQUIRED_PARAMETER_WARNING_MESSAGE, API_TOKEN_KEY);
            return;
        }

        String qaseUrl = System.getProperty("qase.url");
        if (qaseUrl != null) {
            qaseApi = new QaseApi(apiToken, qaseUrl);
        } else {
            qaseApi = new QaseApi(apiToken);
        }

        projectCode = System.getProperty(PROJECT_CODE_KEY, System.getenv(PROJECT_CODE_KEY));
        if (projectCode == null) {
            logger.info(REQUIRED_PARAMETER_WARNING_MESSAGE, PROJECT_CODE_KEY);
            return;
        }
        logger.info("Qase project code - {}", projectCode);

        runId = System.getProperty(RUN_ID_KEY, System.getenv(RUN_ID_KEY));
        if (runId == null) {
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
        Long caseId = getCaseId(result);
        Duration timeSpent = Duration.ofMillis(result.getEndMillis() - result.getStartMillis());
        sendResult(caseId, RunResultStatus.passed, timeSpent);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        Long caseId = getCaseId(result);
        Duration timeSpent = Duration.ofMillis(result.getEndMillis() - result.getStartMillis());
        sendResult(caseId, RunResultStatus.failed, timeSpent);
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

    private void sendResult(Long caseId, RunResultStatus status, Duration timeSpent) {
        if (!isEnabled) {
            return;
        }
        if (caseId != null) {
            try {
                qaseApi.testRunResults().create(projectCode, Long.parseLong(runId), caseId, status, timeSpent, null, null, null);
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
        }
        return null;
    }
}
