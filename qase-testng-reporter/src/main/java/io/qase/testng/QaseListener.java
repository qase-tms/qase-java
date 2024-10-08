package io.qase.testng;

import io.qase.api.QaseClient;
import io.qase.api.StepStorage;
import io.qase.api.config.QaseConfig;
import io.qase.api.services.QaseTestCaseListener;
import io.qase.api.services.ReportersResultOperations;
import io.qase.api.services.impl.QaseTestCaseListenerImpl;
import io.qase.api.services.impl.ReportersResultOperationsImpl;
import io.qase.client.v1.api.ResultsApi;
import io.qase.client.v1.api.RunsApi;
import io.qase.client.v1.models.ResultCreate;
import io.qase.client.v1.models.ResultCreateCase;
import io.qase.client.v1.models.TestStepResultCreate;
import lombok.AccessLevel;
import lombok.Getter;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.Optional;

import static io.qase.api.utils.IntegrationUtils.*;

public class QaseListener extends TestListenerAdapter implements ITestListener {

    private static final String REPORTER_NAME = "TestNG";

    @Getter(lazy = true, value = AccessLevel.PRIVATE)
    private final QaseTestCaseListener qaseTestCaseListener = createQaseListener();

    static {
        System.setProperty(QaseConfig.QASE_CLIENT_REPORTER_NAME_KEY, REPORTER_NAME);
    }

    @Override
    public void onTestStart(ITestResult result) {
        getQaseTestCaseListener().onTestCaseStarted();
        super.onTestStart(result);
    }

    @Override
    public void onTestSuccess(ITestResult tr) {
        getQaseTestCaseListener()
                .onTestCaseFinished(resultCreate -> setupResultItem(resultCreate, tr, "passed"));
        super.onTestSuccess(tr);
    }

    @Override
    public void onTestFailure(ITestResult tr) {
        getQaseTestCaseListener()
                .onTestCaseFinished(resultCreate -> setupResultItem(resultCreate, tr, "failed"));
        super.onTestFailure(tr);
    }

    @Override
    public void onFinish(ITestContext testContext) {
        getQaseTestCaseListener().onTestCasesSetFinished();
        super.onFinish(testContext);
    }

    private void setupResultItem(ResultCreate resultCreate, ITestResult result, String status) {
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
        LinkedList<TestStepResultCreate> steps = StepStorage.stopSteps();
        resultCreate
                ._case(caseTitle == null ? null : new ResultCreateCase().title(caseTitle))
                .caseId(caseId)
                .status(status)
                .comment(comment)
                .stacktrace(stacktrace)
                .steps(steps.isEmpty() ? null : steps)
                .defect(isDefect);
    }

    private static QaseTestCaseListener createQaseListener() {
        RunsApi runsApi = new RunsApi(QaseClient.getApiClient());
        ResultsApi resultsApi = new ResultsApi(QaseClient.getApiClient());
        ReportersResultOperations resultOperations = new ReportersResultOperationsImpl(resultsApi);
        return new QaseTestCaseListenerImpl(runsApi, resultOperations);
    }
}
