package io.qase.junit4;

import io.qase.commons.CasesStorage;
import io.qase.commons.models.domain.*;
import io.qase.commons.reporters.CoreReporterFactory;
import io.qase.commons.reporters.Reporter;
import io.qase.commons.utils.ExceptionUtils;
import io.qase.commons.utils.IntegrationUtils;
import io.qase.commons.utils.TestResultBuilder;
import io.qase.commons.utils.TestResultCompletion;

import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@RunListener.ThreadSafe
public class QaseListener extends RunListener {
    private final Set<String> methods = new HashSet<>();
    private final Reporter qaseTestCaseListener;

    public QaseListener() {
        String reporterVersion = QaseListener.class.getPackage().getImplementationVersion();
        String frameworkVersion = getFrameworkVersion();
        this.qaseTestCaseListener = CoreReporterFactory.getInstance(
            "qase-junit4", reporterVersion, "junit4", frameworkVersion);
    }

    private String getFrameworkVersion() {
        return IntegrationUtils.detectFrameworkVersion(junit.framework.TestCase.class);
    }

    @Override
    public void testSuiteStarted(Description description) {
        this.qaseTestCaseListener.startTestRun();
    }

    @Override
    public void testSuiteFinished(Description result) {
        this.qaseTestCaseListener.uploadResults();
        this.qaseTestCaseListener.completeTestRun();
    }

    @Override
    public void testStarted(Description description) {
        TestResult resultCreate = startTestCase(description);
        CasesStorage.startCase(resultCreate);
    }

    @Override
    public void testFinished(Description description) {
        if (addIfNotPresent(description)) {
            this.stopTestCase(TestResultStatus.PASSED, null);
        }
    }

    @Override
    public void testFailure(Failure failure) {
        if (addIfNotPresent(failure.getDescription())) {
            TestResultStatus status = ExceptionUtils.isAssertionFailure(failure.getException()) ?
                TestResultStatus.FAILED : TestResultStatus.INVALID;
            this.stopTestCase(status, failure.getException());
        }
    }

    @Override
    public void testAssumptionFailure(Failure failure) {
        if (addIfNotPresent(failure.getDescription())) {
            TestResultStatus status = ExceptionUtils.isAssertionFailure(failure.getException()) ?
                TestResultStatus.FAILED : TestResultStatus.INVALID;
            this.stopTestCase(status, failure.getException());
        }
    }

    @Override
    public void testIgnored(Description description) {
        if (addIfNotPresent(description)) {
            this.stopTestCase(TestResultStatus.SKIPPED, null);
        }
    }

    private boolean addIfNotPresent(Description description) {
        String methodFullName = description.getClassName() + description.getMethodName();
        if (methods.contains(methodFullName)) {
            return false;
        }
        methods.add(methodFullName);
        return true;
    }

    private TestResult startTestCase(Description description) {
        DescriptionAnnotationReader reader = new DescriptionAnnotationReader(description);
        return TestResultBuilder.fromAnnotationReader(
            reader,
            description.getClassName(),
            description.getMethodName(),
            null,
            Instant.now().toEpochMilli()
        );
    }

    private void stopTestCase(TestResultStatus status, Throwable error) {
        TestResult resultCreate = CasesStorage.getCurrentCase();
        if (resultCreate.ignore) {
            CasesStorage.stopCase();
            return;
        }
        TestResult result = TestResultCompletion.complete(status, error);
        if (!result.ignore) {
            this.qaseTestCaseListener.addResult(result);
        }
        CasesStorage.stopCase();
    }
}
