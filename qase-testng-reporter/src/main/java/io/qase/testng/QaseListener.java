package io.qase.testng;


import io.qase.commons.StepStorage;
import io.qase.commons.config.ConfigFactory;
import io.qase.commons.config.QaseConfig;
import io.qase.commons.models.domain.*;
import io.qase.commons.reporters.CoreReporterFactory;
import org.testng.*;
import org.testng.annotations.Parameters;
import org.testng.xml.XmlTest;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Stream;

import static io.qase.api.utils.IntegrationUtils.*;

public class QaseListener extends TestListenerAdapter implements ITestListener {

    private final io.qase.commons.reporters.Reporter qaseTestCaseListener;

    public QaseListener() {
        QaseConfig config = ConfigFactory.loadConfig();
        this.qaseTestCaseListener = CoreReporterFactory.getInstance(config);
    }

    @Override
    public void onStart(ITestContext testContext) {
        this.qaseTestCaseListener.startTestRun();
        super.onStart(testContext);
    }

    @Override
    public void onFinish(ITestContext context) {
        this.qaseTestCaseListener.uploadResults();
        this.qaseTestCaseListener.completeTestRun();
        super.onFinish(context);
    }

    @Override
    public void onTestSuccess(ITestResult tr) {
        TestResult result = setupResultItem(tr, TestResultStatus.PASSED);

        if (result == null) {
            return;
        }

        this.qaseTestCaseListener.addResult(result);
        super.onTestSuccess(tr);
    }

    @Override
    public void onTestFailure(ITestResult tr) {
        TestResult result = setupResultItem(tr, TestResultStatus.FAILED);

        if (result == null) {
            return;
        }

        this.qaseTestCaseListener.addResult(result);
        super.onTestFailure(tr);
    }

    private TestResult setupResultItem(ITestResult result, TestResultStatus status) {
        Optional<Throwable> resultThrowable = Optional.ofNullable(result.getThrowable());
        String comment = resultThrowable.flatMap(throwable -> Optional.of(throwable.toString())).orElse(null);
        String stacktrace = resultThrowable.flatMap(throwable -> Optional.of(getStacktrace(throwable))).orElse(null);
        Method method = result.getMethod().getConstructorOrMethod().getMethod();
        boolean ignore = getQaseIgnore(method);
        if (ignore) {
            return null;
        }
        Long caseId = getCaseId(method);
        String caseTitle = getCaseTitle(method);
        LinkedList<StepResult> steps = StepStorage.stopSteps();
        Map<String, String> parameters = this.getParameters(result);
        Map<String, String> fields = getQaseFields(method);
        String suite = getQaseSuite(method);
        Relations relations = new Relations();
        if (suite != null) {
            String[] parts = suite.split("\t");
            for (String part : parts) {
                SuiteData data = new SuiteData();
                data.title = part;
                relations.suite.data.add(data);
            }
        } else {
            SuiteData className = new SuiteData();
            className.title = method.getDeclaringClass().getName();
            relations.suite.data.add(className);
        }

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
        resultCreate.fields = fields;
        resultCreate.relations = relations;

        return resultCreate;
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
