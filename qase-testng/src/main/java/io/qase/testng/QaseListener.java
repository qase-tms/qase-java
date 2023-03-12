package io.qase.testng;

import io.qase.api.StepStorage;
import io.qase.api.annotation.Qase;
import io.qase.api.config.QaseConfig;
import io.qase.api.services.QaseTestCaseListener;
import io.qase.api.utils.IntegrationUtils;
import io.qase.client.model.ResultCreate;
import io.qase.client.model.ResultCreate.StatusEnum;
import io.qase.client.model.ResultCreateStepsInner;
import io.qase.testng.guice.module.TestNgModule;
import lombok.AccessLevel;
import lombok.Getter;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.Optional;

import static io.qase.api.utils.IntegrationUtils.getStacktrace;

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
                .onTestCaseFinished(resultCreate -> setupResultItem(resultCreate, tr, StatusEnum.PASSED));
        super.onTestSuccess(tr);
    }

    @Override
    public void onTestFailure(ITestResult tr) {
        getQaseTestCaseListener()
                .onTestCaseFinished(resultCreate -> setupResultItem(resultCreate, tr, StatusEnum.FAILED));
        super.onTestFailure(tr);
    }

    @Override
    public void onFinish(ITestContext testContext) {
        getQaseTestCaseListener().onTestCasesSetFinished();
        super.onFinish(testContext);
    }

    private void setupResultItem(ResultCreate resultCreate, ITestResult result, StatusEnum status) {
        Optional<Throwable> resultThrowable = Optional.ofNullable(result.getThrowable());
        String comment = resultThrowable
                .flatMap(throwable -> Optional.of(throwable.toString())).orElse(null);
        Boolean isDefect = resultThrowable.flatMap(throwable -> Optional.of(throwable instanceof AssertionError))
                .orElse(false);
        String stacktrace = resultThrowable.flatMap(throwable -> Optional.of(getStacktrace(throwable))).orElse(null);
        Method method = result.getMethod()
                .getConstructorOrMethod()
                .getMethod();
        LinkedList<ResultCreateStepsInner> steps = StepStorage.stopSteps();
        if (method.isAnnotationPresent(Qase.class)) {
            IntegrationUtils.enrichResult(resultCreate, method.getDeclaredAnnotation(Qase.class));
        }
        resultCreate
                .status(status)
                .comment(comment)
                .stacktrace(stacktrace)
                .steps(steps.isEmpty() ? null : steps)
                .defect(isDefect);
    }

    private static QaseTestCaseListener createQaseListener() {
        return TestNgModule.getInjector().getInstance(QaseTestCaseListener.class);
    }
}
