package io.qase.testng;


import io.qase.commons.CasesStorage;
import io.qase.commons.models.domain.*;
import io.qase.commons.reporters.CoreReporterFactory;
import io.qase.commons.utils.ExceptionUtils;
import io.qase.commons.utils.IntegrationUtils;
import io.qase.commons.utils.TestResultBuilder;
import io.qase.commons.utils.TestResultCompletion;
import org.testng.*;
import org.testng.annotations.Parameters;
import org.testng.xml.XmlTest;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.qase.commons.utils.IntegrationUtils.*;

public class QaseListener implements ISuiteListener,
        ITestListener,
        IClassListener,
        IInvokedMethodListener,
        IConfigurationListener,
        IMethodInterceptor {

    private final io.qase.commons.reporters.Reporter qaseTestCaseListener;

    public QaseListener() {
        String reporterVersion = QaseListener.class.getPackage().getImplementationVersion();
        String frameworkVersion = getFrameworkVersion();
        this.qaseTestCaseListener = CoreReporterFactory.getInstance(
            "qase-testng", reporterVersion, "testng", frameworkVersion);
    }

    private String getFrameworkVersion() {
        return IntegrationUtils.detectFrameworkVersion(org.testng.TestNG.class);
    }

    @Override
    public void onStart(final ISuite suite) {
        this.qaseTestCaseListener.startTestRun();
    }

    @Override
    public void onFinish(final ISuite suite) {
        this.qaseTestCaseListener.uploadResults();
        this.qaseTestCaseListener.completeTestRun();
    }

    @Override
    public void onTestStart(ITestResult tr) {
        TestResult result = startTestCase(tr);
        CasesStorage.startCase(result);
    }

    @Override
    public void onTestSuccess(ITestResult tr) {
        this.stopTestCase(tr, TestResultStatus.PASSED);
    }

    @Override
    public void onTestFailure(ITestResult tr) {
        TestResultStatus status = ExceptionUtils.isAssertionFailure(tr.getThrowable()) ?
            TestResultStatus.FAILED : TestResultStatus.INVALID;
        this.stopTestCase(tr, status);
    }

    @Override
    public void onTestSkipped(ITestResult tr) {
        if (!CasesStorage.isCaseInProgress()) {
            TestResult result = startTestCase(tr);
            CasesStorage.startCase(result);
        }
        this.stopTestCase(tr, TestResultStatus.SKIPPED);
    }

    private TestResult startTestCase(ITestResult result) {
        Method method = result.getMethod().getConstructorOrMethod().getMethod();
        Map<String, String> parameters = getParameters(result);
        return TestResultBuilder.fromMethod(method, parameters, result.getStartMillis());
    }

    private void stopTestCase(ITestResult result, TestResultStatus status) {
        if (!CasesStorage.isCaseInProgress()) {
            return;
        }
        TestResult resultCreate = CasesStorage.getCurrentCase();
        if (resultCreate.ignore) {
            CasesStorage.stopCase();
            return;
        }
        TestResult completed = TestResultCompletion.completeWithTiming(
            status,
            result.getThrowable(),
            result.getStartMillis(),
            result.getEndMillis()
        );
        if (!completed.ignore) {
            this.qaseTestCaseListener.addResult(completed);
        }
        CasesStorage.stopCase();
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

    @Override
    public List<IMethodInstance> intercept(List<IMethodInstance> methods, ITestContext context) {
        List<Long> caseIdsToExecute = qaseTestCaseListener.getTestCaseIdsForExecution();

        if (caseIdsToExecute.isEmpty()) {
            return methods;
        }

        return methods.stream()
                .filter(this::hasMatchingCaseId)
                .collect(Collectors.toList());
    }

    private boolean hasMatchingCaseId(IMethodInstance methodInstance) {
        Method method = methodInstance.getMethod().getConstructorOrMethod().getMethod();
        List<Long> caseIds = getCaseIds(method);

        if (caseIds == null || caseIds.isEmpty()) {
            return false;
        }

        for (Long caseId : caseIds) {
            if (qaseTestCaseListener.getTestCaseIdsForExecution().contains(caseId)) {
                return true;
            }
        }

        return false;
    }
}
