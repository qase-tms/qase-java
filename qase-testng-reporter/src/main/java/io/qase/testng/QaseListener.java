package io.qase.testng;


import io.qase.commons.CasesStorage;
import io.qase.commons.StepStorage;
import io.qase.commons.models.domain.*;
import io.qase.commons.reporters.CoreReporterFactory;
import io.qase.commons.utils.ExceptionUtils;
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
        try {
            Package pkg = org.testng.TestNG.class.getPackage();
            if (pkg != null) {
                String version = pkg.getImplementationVersion();
                if (version == null || version.isEmpty()) {
                    version = pkg.getSpecificationVersion();
                }
                return version != null ? version : "";
            }
        } catch (Exception e) {
            // Framework version not available
        }
        return "";
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
        this.stopTestCase(tr, TestResultStatus.SKIPPED);
    }

    private void stopTestCase(ITestResult result, TestResultStatus status) {
        TestResult resultCreate = CasesStorage.getCurrentCase();
        if (resultCreate.ignore) {
            CasesStorage.stopCase();
            return;
        }

        Optional<Throwable> resultThrowable = Optional.ofNullable(result.getThrowable());
        String comment = resultThrowable.flatMap(throwable -> Optional.of(throwable.toString())).orElse(null);
        String stacktrace = resultThrowable.flatMap(throwable -> Optional.of(getStacktrace(throwable))).orElse(null);
        LinkedList<StepResult> steps = StepStorage.stopSteps();

        resultCreate.execution.status = status;
        resultCreate.execution.endTime = result.getEndMillis();
        resultCreate.execution.duration = (int) (result.getEndMillis() - result.getStartMillis());
        resultCreate.execution.stacktrace = stacktrace;
        resultCreate.steps = steps;

        if (comment != null) {
            if (resultCreate.message != null) {
                resultCreate.message += "\n\n" + comment;
            } else {
                resultCreate.message = comment;
            }
        }

        this.qaseTestCaseListener.addResult(resultCreate);
        CasesStorage.stopCase();
    }

    private TestResult startTestCase(ITestResult result) {
        TestResult resultCreate = new TestResult();
        Method method = result.getMethod().getConstructorOrMethod().getMethod();
        boolean ignore = getQaseIgnore(method);

        if (ignore) {
            resultCreate.ignore = true;
            return resultCreate;
        }

        List<Long> caseIds = getCaseIds(method);
        String caseTitle = getCaseTitle(method);
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

        resultCreate.execution.startTime = result.getStartMillis();
        resultCreate.execution.thread = Thread.currentThread().getName();
        resultCreate.testopsIds = caseIds;
        resultCreate.title = caseTitle;
        resultCreate.params = parameters;
        resultCreate.fields = fields;
        resultCreate.relations = relations;
        resultCreate.signature = generateSignature(method, caseIds, parameters);

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
