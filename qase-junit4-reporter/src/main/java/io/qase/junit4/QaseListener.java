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
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

import java.time.Instant;

@RunListener.ThreadSafe
public class QaseListener extends RunListener {
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
    public void testRunStarted(Description description) {
        this.qaseTestCaseListener.startTestRun();
    }

    @Override
    public void testRunFinished(Result result) {
        this.qaseTestCaseListener.uploadResults();
        this.qaseTestCaseListener.completeTestRun();
    }

    // Gradle fires no testRun* events, so the run is started by the first suite and
    // completed by the shutdown hook; per-suite events only flush the results.
    @Override
    public void testSuiteStarted(Description description) {
        this.qaseTestCaseListener.startTestRun();
    }

    @Override
    public void testSuiteFinished(Description result) {
        this.qaseTestCaseListener.uploadResults();
    }

    @Override
    public void testStarted(Description description) {
        TestResult resultCreate = startTestCase(description);
        CasesStorage.startCase(resultCreate);
    }

    @Override
    public void testFinished(Description description) {
        this.stopTestCase(TestResultStatus.PASSED, null);
    }

    @Override
    public void testFailure(Failure failure) {
        TestResultStatus status = ExceptionUtils.isAssertionFailure(failure.getException()) ?
            TestResultStatus.FAILED : TestResultStatus.INVALID;
        this.stopTestCase(status, failure.getException());
    }

    @Override
    public void testAssumptionFailure(Failure failure) {
        this.stopTestCase(TestResultStatus.SKIPPED, failure.getException());
    }

    @Override
    public void testIgnored(Description description) {
        // A class-level @Ignore comes without method descriptions, so there is nothing to map to a case.
        if (description.getMethodName() == null) {
            return;
        }
        // JUnit does not fire testStarted for ignored tests.
        CasesStorage.startCase(startTestCase(description));
        this.stopTestCase(TestResultStatus.SKIPPED, null);
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
        if (!CasesStorage.isCaseInProgress()) {
            return;
        }
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
