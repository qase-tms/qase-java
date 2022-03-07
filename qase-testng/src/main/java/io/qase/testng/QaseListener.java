package io.qase.testng;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.LinkedList;
import java.util.Optional;

import static io.qase.api.Constants.*;
import static io.qase.api.QaseClient.getConfig;
import static io.qase.api.utils.IntegrationUtils.*;

public class QaseListener extends TestListenerAdapter implements ITestListener {
    private static final Logger logger = LoggerFactory.getLogger(QaseListener.class);
    private final ResultCreateBulk resultCreateBulk = new ResultCreateBulk();
    private final ApiClient apiClient = QaseClient.getApiClient();
    private final ResultsApi resultsApi = new ResultsApi(apiClient);

    public QaseListener() {
        apiClient.addDefaultHeader(X_CLIENT_REPORTER, "TestNG");
    }

    @Override
    public void onTestSuccess(ITestResult tr) {
        if (getConfig().useBulk()) {
            addBulkResult(tr, StatusEnum.PASSED);
            super.onTestSuccess(tr);
        } else {
            sendResult(tr, StatusEnum.PASSED);
        }
        super.onTestSuccess(tr);
    }

    @Override
    public void onTestFailure(ITestResult tr) {
        if (getConfig().useBulk()) {
            addBulkResult(tr, StatusEnum.FAILED);
        } else {
            sendResult(tr, StatusEnum.FAILED);
        }
        super.onTestFailure(tr);
    }

    @Override
    public void onFinish(ITestContext testContext) {
        if (getConfig().useBulk()) {
            sendBulkResult();
        }
        super.onFinish(testContext);
    }

    private void sendResult(ITestResult result, StatusEnum status) {
        if (!QaseClient.isEnabled()) {
            return;
        }
        try {
            resultsApi.createResult(
                    getConfig().projectCode(),
                    getConfig().runId(),
                    getResultItem(result, status)
            );
        } catch (QaseException e) {
            logger.error(e.getMessage());
        }
    }

    private void addBulkResult(ITestResult result, StatusEnum status) {
        if (!QaseClient.isEnabled()) {
            return;
        }
        resultCreateBulk.addResultsItem(
                getResultItem(result, status));
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
        String caseTitle = null;
        if (caseId == null) {
            caseTitle = getCaseTitle(method);
        }
        LinkedList<ResultCreateSteps> steps = StepStorage.getSteps();
        return new ResultCreate()
                ._case(caseTitle == null ? null : new ResultCreateCase().title(caseTitle))
                .caseId(caseId)
                .status(status)
                .timeMs(timeSpent.toMillis())
                .comment(comment)
                .stacktrace(stacktrace)
                .steps(steps.isEmpty() ? null : steps)
                .defect(isDefect);
    }
}
