package io.qase.junit5;

import io.qase.commons.CasesStorage;
import io.qase.commons.StepStorage;
import io.qase.commons.models.domain.*;
import io.qase.commons.reporters.CoreReporterFactory;
import io.qase.commons.reporters.Reporter;
import io.qase.commons.utils.ExceptionUtils;
import org.junit.jupiter.api.extension.*;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestPlan;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.time.Instant;
import java.util.*;

import static io.qase.commons.utils.IntegrationUtils.*;


public class QaseListener implements TestExecutionListener, Extension, BeforeAllCallback, AfterAllCallback, InvocationInterceptor, TestWatcher {
    private final Reporter qaseTestCaseListener;


    public QaseListener() {
        String reporterVersion = QaseListener.class.getPackage().getImplementationVersion();
        String frameworkVersion = getFrameworkVersion();
        this.qaseTestCaseListener = CoreReporterFactory.getInstance(
            "qase-junit5", reporterVersion, "junit5", frameworkVersion);
    }

    private String getFrameworkVersion() {
        try {
            Package pkg = org.junit.jupiter.api.Test.class.getPackage();
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
    public void testPlanExecutionStarted(TestPlan testPlan) {
        this.qaseTestCaseListener.startTestRun();
    }

    @Override
    public void testPlanExecutionFinished(TestPlan testPlan) {
        this.qaseTestCaseListener.uploadResults();
        this.qaseTestCaseListener.completeTestRun();
    }

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) throws Exception {
    }

    @Override
    public void interceptTestTemplateMethod(
            InvocationInterceptor.Invocation<Void> invocation,
            ReflectiveInvocationContext<Method> invocationContext,
            ExtensionContext extensionContext
    ) throws Throwable {
        TestResult result = startTestCase(extensionContext.getRequiredTestMethod(), invocationContext);
        CasesStorage.startCase(result);

        invocation.proceed();
    }

    @Override
    public void interceptTestMethod(
            InvocationInterceptor.Invocation<Void> invocation,
            ReflectiveInvocationContext<Method> invocationContext,
            ExtensionContext extensionContext
    ) throws Throwable {
        TestResult result = startTestCase(extensionContext.getRequiredTestMethod(), invocationContext);
        CasesStorage.startCase(result);

        invocation.proceed();
    }

    private TestResult startTestCase(Method method, ReflectiveInvocationContext<Method> invocationContext) {
        TestResult resultCreate = new TestResult();
        boolean ignore = getQaseIgnore(method);

        if (ignore) {
            resultCreate.ignore = true;
            return resultCreate;
        }

        List<Long> caseIds = getCaseIds(method);
        String caseTitle = getCaseTitle(method);
        Map<String, String> parameters = this.getParameters(invocationContext);
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

        resultCreate.execution.startTime = Instant.now().toEpochMilli();
        resultCreate.execution.thread = Thread.currentThread().getName();
        resultCreate.testopsIds = caseIds;
        resultCreate.title = caseTitle;
        resultCreate.params = parameters;
        resultCreate.fields = fields;
        resultCreate.relations = relations;
        resultCreate.signature = generateSignature(method, caseIds, parameters);

        return resultCreate;
    }

    private Map<String, String> getParameters(final ReflectiveInvocationContext<Method> invocationContext) {
        final Parameter[] parameters = invocationContext.getExecutable().getParameters();
        Map<String, String> testParameters = new HashMap<>();

        for (int i = 0; i < parameters.length; i++) {
            final Parameter parameter = parameters[i];
            final Class<?> parameterType = parameter.getType();

            if (parameterType.getCanonicalName().startsWith("org.junit.jupiter.api")) {
                continue;
            }

            String name = parameter.getName();
            String value = invocationContext.getArguments().get(i).toString();

            testParameters.put(name, value);
        }

        return testParameters;
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
       this.stopTestCase(TestResultStatus.PASSED, null);
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        this.stopTestCase(TestResultStatus.SKIPPED, cause);
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        TestResultStatus status = ExceptionUtils.isAssertionFailure(cause) ? 
            TestResultStatus.FAILED : TestResultStatus.INVALID;
        this.stopTestCase(status, cause);
    }

    private void stopTestCase(TestResultStatus status, Throwable cause) {
        TestResult resultCreate = CasesStorage.getCurrentCase();

        if (resultCreate.ignore) {
            CasesStorage.stopCase();
            return;
        }

        Optional<Throwable> resultThrowable = Optional.ofNullable(cause);
        String comment = resultThrowable.flatMap(throwable -> Optional.of(throwable.toString())).orElse(null);
        String stacktrace = resultThrowable.flatMap(throwable -> Optional.of(getStacktrace(throwable))).orElse(null);
        LinkedList<StepResult> steps = StepStorage.stopSteps();

        resultCreate.execution.status = status;
        resultCreate.execution.endTime = Instant.now().toEpochMilli();
        resultCreate.execution.duration = (int) (resultCreate.execution.endTime - resultCreate.execution.startTime);
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
}
