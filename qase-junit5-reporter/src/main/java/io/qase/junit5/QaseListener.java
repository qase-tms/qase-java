package io.qase.junit5;

import io.qase.commons.CasesStorage;
import io.qase.commons.models.domain.*;
import io.qase.commons.reporters.CoreReporterFactory;
import io.qase.commons.reporters.Reporter;
import io.qase.commons.utils.ExceptionUtils;
import io.qase.commons.utils.IntegrationUtils;
import io.qase.commons.utils.TestResultBuilder;
import io.qase.commons.utils.TestResultCompletion;
import org.junit.jupiter.api.extension.*;
import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.engine.TestSource;
import org.junit.platform.engine.support.descriptor.ClassSource;
import org.junit.platform.engine.support.descriptor.MethodSource;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.TestPlan;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static io.qase.commons.utils.IntegrationUtils.*;


public class QaseListener implements TestExecutionListener, Extension, BeforeAllCallback, AfterAllCallback, InvocationInterceptor, TestWatcher {
    private final Reporter qaseTestCaseListener;
    private TestPlan testPlan;
    private final Set<String> startedIdentifiers = ConcurrentHashMap.newKeySet();


    public QaseListener() {
        String reporterVersion = QaseListener.class.getPackage().getImplementationVersion();
        String frameworkVersion = getFrameworkVersion();
        this.qaseTestCaseListener = CoreReporterFactory.getInstance(
            "qase-junit5", reporterVersion, "junit5", frameworkVersion);
    }

    private String getFrameworkVersion() {
        return IntegrationUtils.detectFrameworkVersion(org.junit.jupiter.api.Test.class);
    }

    @Override
    public void testPlanExecutionStarted(TestPlan testPlan) {
        this.testPlan = testPlan;
        this.qaseTestCaseListener.startTestRun();
    }

    @Override
    public void executionStarted(TestIdentifier testIdentifier) {
        startedIdentifiers.add(testIdentifier.getUniqueId());
    }

    @Override
    public void executionFinished(TestIdentifier testIdentifier, TestExecutionResult testExecutionResult) {
        if (testIdentifier.isTest()) {
            return;
        }

        if (testExecutionResult.getStatus() != TestExecutionResult.Status.FAILED) {
            return;
        }

        Optional<TestSource> sourceOpt = testIdentifier.getSource();
        if (!sourceOpt.isPresent() || !(sourceOpt.get() instanceof ClassSource)) {
            return;
        }

        if (testPlan == null) {
            return;
        }

        String reason = testExecutionResult.getThrowable()
                .map(Throwable::toString)
                .orElse("Container failed");
        String stacktrace = testExecutionResult.getThrowable()
                .map(t -> getStacktrace(t))
                .orElse(null);

        reportUnstartedDescendants(testIdentifier, reason, stacktrace);
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
        Map<String, String> parameters = getParameters(invocationContext);
        return TestResultBuilder.fromMethod(method, parameters, Instant.now().toEpochMilli());
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
        if (!CasesStorage.isCaseInProgress()) {
            return;
        }
        TestResult resultCreate = CasesStorage.getCurrentCase();
        try {
            if (resultCreate.ignore) {
                return;
            }
            TestResult result = TestResultCompletion.complete(status, cause);
            if (!result.ignore) {
                this.qaseTestCaseListener.addResult(result);
            }
        } finally {
            if (CasesStorage.isCaseInProgress()) {
                CasesStorage.stopCase();
            }
        }
    }

    private void reportUnstartedDescendants(TestIdentifier container, String reason, String stacktrace) {
        Set<TestIdentifier> children = testPlan.getChildren(container);
        for (TestIdentifier child : children) {
            if (startedIdentifiers.contains(child.getUniqueId())) {
                continue;
            }

            if (child.isTest()) {
                reportSkippedTest(child, reason, stacktrace);
            } else if (child.isContainer()) {
                // Container child: could be @Nested class (ClassSource) or
                // parameterized/repeated test (MethodSource)
                Optional<TestSource> childSource = child.getSource();
                if (childSource.isPresent()) {
                    if (childSource.get() instanceof MethodSource) {
                        // Parameterized/repeated test container — report the method once
                        reportSkippedTest(child, reason, stacktrace);
                    } else if (childSource.get() instanceof ClassSource) {
                        // @Nested class — recurse
                        reportUnstartedDescendants(child, reason, stacktrace);
                    }
                }
            }
        }
    }

    private void reportSkippedTest(TestIdentifier testIdentifier, String reason, String failureStacktrace) {
        Optional<TestSource> sourceOpt = testIdentifier.getSource();
        if (!sourceOpt.isPresent() || !(sourceOpt.get() instanceof MethodSource)) {
            return;
        }

        MethodSource methodSource = (MethodSource) sourceOpt.get();
        Method method = methodSource.getJavaMethod();

        if (getQaseIgnore(method)) {
            return;
        }

        List<Long> caseIds = getCaseIds(method);
        String caseTitle = getCaseTitle(method);
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

        TestResult result = new TestResult();
        result.testopsIds = caseIds;
        result.title = caseTitle;
        result.fields = fields;
        result.relations = relations;
        result.signature = generateSignature(method, caseIds, Collections.emptyMap());
        result.execution.status = TestResultStatus.SKIPPED;
        result.execution.startTime = Instant.now().toEpochMilli();
        result.execution.endTime = result.execution.startTime;
        result.execution.duration = 0;
        result.execution.stacktrace = failureStacktrace;
        result.execution.thread = Thread.currentThread().getName();
        result.message = reason;

        this.qaseTestCaseListener.addResult(result);
    }
}
