package io.qase.testng;

import io.qase.api.exceptions.QaseException;
import io.qase.client.ApiClient;
import io.qase.client.api.ResultsApi;
import io.qase.client.model.ResultCreate;
import io.qase.client.model.ResultCreate.StatusEnum;
import io.qase.client.model.ResultCreateBulk;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.*;
import org.testng.xml.XmlSuite;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

import static io.qase.api.utils.IntegrationUtils.*;

public class QaseListener extends TestListenerAdapter implements IReporter, ITestListener {
    private static final Logger logger = LoggerFactory.getLogger(QaseListener.class);
    private final ResultCreateBulk resultCreateBulk = new ResultCreateBulk();
    private final ApiClient apiClient = new ApiClient();
    private final ResultsApi resultsApi = new ResultsApi(apiClient);
    private boolean isEnabled;
    private boolean useBulk;
    private String projectCode;
    private Integer runId;

    @Override
    public void onTestSuccess(ITestResult tr) {
        if (useBulk) {
            super.onTestSuccess(tr);
        } else {
            sendResult(tr, StatusEnum.PASSED);
        }
    }

    @Override
    public void onTestFailure(ITestResult tr) {
        if (useBulk) {
            super.onTestFailure(tr);
        } else {
            sendResult(tr, StatusEnum.FAILED);
        }
    }

    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        if (useBulk) {
            List<ITestResult> passedTests = getPassedTests();
            List<ITestResult> failedTests = getFailedTests();
            passedTests.forEach(passedTest -> addBulkResult(passedTest, StatusEnum.PASSED));
            failedTests.forEach(passedTest -> addBulkResult(passedTest, StatusEnum.FAILED));
            sendBulkResult();
        }
    }

    public QaseListener() {
        isEnabled = Boolean.parseBoolean(System.getProperty(ENABLE_KEY, "false"));
        if (!isEnabled) {
            return;
        }
        useBulk = Boolean.parseBoolean(System.getProperty(BULK_KEY, "true"));

        String apiToken = System.getProperty(API_TOKEN_KEY, System.getenv(API_TOKEN_KEY));
        if (apiToken == null) {
            isEnabled = false;
            logger.info(REQUIRED_PARAMETER_WARNING_MESSAGE, API_TOKEN_KEY);
            return;
        }

        String qaseUrl = System.getProperty(QASE_URL_KEY, System.getenv(API_TOKEN_KEY));
        if (qaseUrl != null) {
            apiClient.setBasePath(qaseUrl);
        }
        apiClient.setApiKey(apiToken);

        projectCode = System.getProperty(PROJECT_CODE_KEY, System.getenv(PROJECT_CODE_KEY));
        if (projectCode == null) {
            isEnabled = false;
            logger.info(REQUIRED_PARAMETER_WARNING_MESSAGE, PROJECT_CODE_KEY);
            return;
        }
        logger.info("Qase project code - {}", projectCode);

        try {
            runId = Integer.valueOf(System.getProperty(RUN_ID_KEY, System.getenv(RUN_ID_KEY)));
        } catch (NumberFormatException e) {
            isEnabled = false;
            logger.info(REQUIRED_PARAMETER_WARNING_MESSAGE, RUN_ID_KEY);
            return;
        }
        logger.info("Qase run id - {}", runId);
    }

    private void sendResult(ITestResult result, StatusEnum status) {
        if (!isEnabled) {
            return;
        }
        try {
            resultsApi.createResult(
                    projectCode,
                    String.valueOf(runId),
                    getResultItem(result, status)
            );
        } catch (QaseException e) {
            logger.error(e.getMessage());
        }
    }

    private void addBulkResult(ITestResult result, StatusEnum status) {
        if (!isEnabled) {
            return;
        }
        resultCreateBulk.addResultsItem(
                getResultItem(result, status));
    }

    private void sendBulkResult() {
        if (!isEnabled) {
            return;
        }
        try {
            resultsApi.createResultBulk(
                    projectCode,
                    runId,
                    resultCreateBulk
            );
        } catch (QaseException e) {
            logger.error(e.getMessage());
        }
    }

    private ResultCreate getResultItem(ITestResult result, StatusEnum status) {
        Duration timeSpent = Duration.ofMillis(result.getEndMillis() - result.getStartMillis());
        Optional<Throwable> resultThrowable = Optional.ofNullable(result.getThrowable());
        String comment = resultThrowable
                .flatMap(throwable -> Optional.of(throwable.toString())).orElse(null);
        Boolean isDefect = resultThrowable.flatMap(throwable -> Optional.of(throwable instanceof AssertionError))
                .orElse(false);
        String stacktrace = resultThrowable.flatMap(throwable -> Optional.of(getStacktrace(throwable))).orElse(null);
        Method method = result.getMethod()
                .getConstructorOrMethod()
                .getMethod();
        Long caseId = getCaseId(method);
        return new ResultCreate()
                .caseId(caseId)
                .status(status)
                .timeMs(timeSpent.toMillis())
                .comment(comment)
                .stacktrace(stacktrace)
                .defect(isDefect);
    }
}
