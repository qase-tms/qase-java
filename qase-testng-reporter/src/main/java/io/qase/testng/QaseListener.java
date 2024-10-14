package io.qase.testng;


import io.qase.commons.StepStorage;
import io.qase.commons.config.ConfigFactory;
import io.qase.commons.config.QaseConfig;
import io.qase.commons.models.domain.StepResult;
import io.qase.commons.models.domain.TestResult;
import io.qase.commons.models.domain.TestResultStatus;
import io.qase.commons.reporters.CoreReporter;
import io.qase.commons.reporters.CoreReporterFactory;
import lombok.AccessLevel;
import lombok.Getter;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.annotations.Parameters;
import org.testng.xml.XmlTest;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Stream;

import static io.qase.api.utils.IntegrationUtils.*;

public class QaseListener extends TestListenerAdapter implements ITestListener {

    @Getter(lazy = true, value = AccessLevel.PRIVATE)
    private final CoreReporter qaseTestCaseListener = createQaseListener();

    @Override
    public void onStart(ITestContext testContext) {
        getQaseTestCaseListener().startTestRun();
        super.onStart(testContext);
    }

    @Override
    public void onFinish(ITestContext context) {
        getQaseTestCaseListener().uploadResults();
        getQaseTestCaseListener().completeTestRun();
        super.onFinish(context);
    }

//    @Override
//    public void onTestStart(ITestResult result) {
//        getQaseTestCaseListener().startTestRun();
//        super.onTestStart(result);
//    }

    @Override
    public void onTestSuccess(ITestResult tr) {
        getQaseTestCaseListener().addResult(setupResultItem(tr, TestResultStatus.PASSED));
        super.onTestSuccess(tr);
    }

    @Override
    public void onTestFailure(ITestResult tr) {
        getQaseTestCaseListener().addResult(setupResultItem(tr, TestResultStatus.FAILED));
        super.onTestFailure(tr);
    }

    private TestResult setupResultItem(ITestResult result, TestResultStatus status) {
        Optional<Throwable> resultThrowable = Optional.ofNullable(result.getThrowable());
        String comment = resultThrowable.flatMap(throwable -> Optional.of(throwable.toString())).orElse(null);
        String stacktrace = resultThrowable.flatMap(throwable -> Optional.of(getStacktrace(throwable))).orElse(null);
        Method method = result.getMethod().getConstructorOrMethod().getMethod();
        Long caseId = getCaseId(method);
        String caseTitle = getCaseTitle(method);
        LinkedList<StepResult> steps = StepStorage.stopSteps();
        Map<String, String> parameters = this.getParameters(result);

        TestResult resultCreate = new TestResult();
        resultCreate.execution.status = status;
        resultCreate.execution.startTime = result.getStartMillis();
        resultCreate.execution.endTime = result.getEndMillis();
        resultCreate.execution.duration = (int) (result.getEndMillis() - result.getStartMillis());
        resultCreate.execution.stacktrace = stacktrace;
        resultCreate.message = comment;
        resultCreate.testopsId = caseId;
        resultCreate.steps = steps;
        resultCreate.title = caseTitle;
        resultCreate.params = parameters;

        return resultCreate;
    }

    private static CoreReporter createQaseListener() {
        QaseConfig config = ConfigFactory.loadConfig();
        return CoreReporterFactory.getInstance(config);
    }

    private Map<String, String> getParameters(final ITestResult testResult) {
        Method method = testResult.getMethod().getConstructorOrMethod().getMethod();
        final Map<String, String> testParameters = new HashMap<>(testResult.getTestContext().getCurrentXmlTest().getAllParameters());

        Object[] parameters = testResult.getParameters();
        List<Class<?>> injectedTypes = Arrays.asList(ITestContext.class, ITestResult.class, XmlTest.class, Method.class, Object[].class);

        final Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length != parameters.length) {
            return testParameters;
        }

        final String[] providedNames = Optional.ofNullable(method.getAnnotation(Parameters.class)).map(Parameters::value).orElse(new String[]{});

        final String[] reflectionNames = Stream.of(method.getParameters()).map(java.lang.reflect.Parameter::getName).toArray(String[]::new);

        int skippedCount = 0;
        for (int i = 0; i < parameterTypes.length; i++) {
            final Class<?> parameterType = parameterTypes[i];
            if (injectedTypes.contains(parameterType)) {
                skippedCount++;
                continue;
            }

            final int indexFromAnnotation = i - skippedCount;

            String value = String.format("%s", parameters[i]);

            if (indexFromAnnotation < providedNames.length) {
                if (parameters[i] != null) {
                    testParameters.put(providedNames[indexFromAnnotation], value);
                }
                continue;
            }

            if (i < reflectionNames.length) {
                testParameters.put(reflectionNames[i], value);
            }
        }

        return testParameters;
    }
}
