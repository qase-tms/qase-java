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
import org.opentest4j.TestAbortedException;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static io.qase.commons.utils.IntegrationUtils.*;


public class QaseListener implements TestExecutionListener, Extension, BeforeAllCallback, AfterAllCallback, InvocationInterceptor, TestWatcher {
    private static final String JUPITER_API_PACKAGE = "org.junit.jupiter.api";
    private static final String DYNAMIC_TEST_PARAMETER = "dynamic test";
    private final Reporter qaseTestCaseListener;
    private TestPlan testPlan;
    private final Set<String> startedIdentifiers = ConcurrentHashMap.newKeySet();


    public QaseListener() {
        this.qaseTestCaseListener = getReporter();
    }

    // Shared with Junit5PostDiscoveryFilter: whichever runs first creates the reporter with JUnit 5 metadata.
    static Reporter getReporter() {
        String reporterVersion = QaseListener.class.getPackage().getImplementationVersion();
        String frameworkVersion = IntegrationUtils.detectFrameworkVersion(org.junit.jupiter.api.Test.class);
        return CoreReporterFactory.getInstance("qase-junit5", reporterVersion, "junit5", frameworkVersion);
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

    @Override
    public <T> T interceptTestFactoryMethod(
            InvocationInterceptor.Invocation<T> invocation,
            ReflectiveInvocationContext<Method> invocationContext,
            ExtensionContext extensionContext
    ) throws Throwable {
        try {
            return invocation.proceed();
        } catch (Throwable failure) {
            // A factory that throws produces no dynamic tests, so the factory itself is reported.
            CasesStorage.startCase(startTestCase(extensionContext.getRequiredTestMethod(), invocationContext));
            this.stopTestCase(extensionContext, failureStatus(failure), failure);
            throw failure;
        }
    }

    // JUnit 5.8+ and 6 call this overload; 5.5-5.7 call the deprecated one below.
    @Override
    public void interceptDynamicTest(
            InvocationInterceptor.Invocation<Void> invocation,
            DynamicTestInvocationContext invocationContext,
            ExtensionContext extensionContext
    ) throws Throwable {
        reportDynamicTest(invocation, extensionContext);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void interceptDynamicTest(
            InvocationInterceptor.Invocation<Void> invocation,
            ExtensionContext extensionContext
    ) throws Throwable {
        reportDynamicTest(invocation, extensionContext);
    }

    // TestWatcher is not notified for dynamic tests, so each one is reported here as a
    // parameterized result of its factory method's case.
    private void reportDynamicTest(
            InvocationInterceptor.Invocation<Void> invocation,
            ExtensionContext extensionContext
    ) throws Throwable {
        Deque<String> path = new ArrayDeque<>();
        ExtensionContext context = extensionContext;
        while (context != null && !(context.getElement().orElse(null) instanceof Method)) {
            path.addFirst(context.getDisplayName());
            context = context.getParent().orElse(null);
        }
        if (context == null) {
            invocation.proceed();
            return;
        }

        Method factory = (Method) context.getElement().get();
        Map<String, String> parameters = Collections.singletonMap(DYNAMIC_TEST_PARAMETER, String.join(" / ", path));
        CasesStorage.startCase(TestResultBuilder.fromMethod(factory, parameters, Instant.now().toEpochMilli()));

        try {
            invocation.proceed();
        } catch (Throwable failure) {
            this.stopTestCase(extensionContext, failureStatus(failure), failure);
            throw failure;
        }
        this.stopTestCase(extensionContext, TestResultStatus.PASSED, null);
    }

    private static TestResultStatus failureStatus(Throwable failure) {
        if (failure instanceof TestAbortedException) {
            return TestResultStatus.SKIPPED;
        }
        return ExceptionUtils.isAssertionFailure(failure) ? TestResultStatus.FAILED : TestResultStatus.INVALID;
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

            if (isJupiterInjected(parameter)) {
                continue;
            }

            String name = parameter.getName();
            String value = formatParameterValue(invocationContext.getArguments().get(i));

            testParameters.put(name, value);
        }

        return testParameters;
    }

    // TestInfo, TestReporter and the like, or values supplied through Jupiter annotations such as @TempDir.
    private static boolean isJupiterInjected(Parameter parameter) {
        if (parameter.getType().getName().startsWith(JUPITER_API_PACKAGE)) {
            return true;
        }
        for (java.lang.annotation.Annotation annotation : parameter.getAnnotations()) {
            if (annotation.annotationType().getName().startsWith(JUPITER_API_PACKAGE)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        this.stopTestCase(context, TestResultStatus.PASSED, null);
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        this.stopTestCase(context, TestResultStatus.SKIPPED, cause);
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        this.stopTestCase(context, failureStatus(cause), cause);
    }

    private void stopTestCase(ExtensionContext context, TestResultStatus status, Throwable cause) {
        if (!CasesStorage.isCaseInProgress()) {
            // The test method was never invoked (e.g. @BeforeEach threw), so no case was started.
            Optional<Method> method = context.getTestMethod();
            if (!method.isPresent()) {
                return;
            }
            CasesStorage.startCase(TestResultBuilder.fromMethod(
                method.get(), Collections.emptyMap(), Instant.now().toEpochMilli()));
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
