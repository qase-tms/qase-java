package io.qase.commons.cucumber;

import io.qase.commons.CasesStorage;
import io.qase.commons.StepStorage;
import io.qase.commons.models.domain.StepResult;
import io.qase.commons.models.domain.StepResultStatus;
import io.qase.commons.models.domain.TestResult;
import io.qase.commons.models.domain.TestResultStatus;
import io.qase.commons.reporters.CoreReporterFactory;
import io.qase.commons.reporters.Reporter;
import io.qase.commons.utils.ExceptionUtils;
import io.qase.commons.utils.TestResultBuilder;
import io.qase.commons.utils.TestResultCompletion;

import java.time.Instant;
import java.util.Map;

/**
 * Abstract base class for Cucumber v4-v7 QaseEventListener implementations.
 * Contains shared event-handler boilerplate with zero Cucumber imports.
 * Concrete subclasses provide setEventPublisher (version-specific EventPublisher type)
 * and perform version-specific extraction before delegating to on* methods.
 */
public abstract class AbstractCucumberEventListener {

    protected final Reporter qaseTestCaseListener;

    protected AbstractCucumberEventListener(String frameworkSlug, String reporterVersion,
                                             String frameworkVersion) {
        this.qaseTestCaseListener = CoreReporterFactory.getInstance(
                frameworkSlug, reporterVersion, "cucumber", frameworkVersion);
    }

    // --- Shared event handler bodies (version-agnostic) ---

    protected void onTestRunStarted() {
        qaseTestCaseListener.startTestRun();
    }

    protected void onTestRunFinished() {
        qaseTestCaseListener.uploadResults();
        qaseTestCaseListener.completeTestRun();
    }

    protected void onTestStepStarted(boolean isPickleStep) {
        if (isPickleStep) {
            StepStorage.startStep();
        }
    }

    /**
     * Called by concrete subclasses after performing version-specific step extraction.
     * The concrete class extracts stepText and converts step status before calling here.
     */
    protected void onTestStepFinished(boolean isPickleStep, String stepText, StepResultStatus status) {
        if (isPickleStep) {
            StepResult stepResult = StepStorage.getCurrentStep();
            stepResult.data.action = stepText;
            stepResult.execution.status = status;
            StepStorage.stopStep();
        }
    }

    /**
     * Called by concrete subclasses after creating a version-specific adapter
     * and extracting parameters from ScenarioStorage.
     */
    protected void onTestCaseStarted(CucumberTestCaseAdapter adapter, Map<String, String> parameters) {
        TestResult result = TestResultBuilder.fromCucumber(adapter, parameters, Instant.now().toEpochMilli());
        CasesStorage.startCase(result);
    }

    /**
     * Called by concrete subclasses after extracting status and throwable from
     * the version-specific event. Concrete class converts version status to
     * TestResultStatus before calling here.
     */
    protected void onTestCaseFinished(TestResultStatus status, Throwable cause) {
        TestResult resultCreate = CasesStorage.getCurrentCase();
        CasesStorage.stopCase();
        if (resultCreate.ignore) {
            return;
        }
        TestResultStatus resolvedStatus = status;
        if (resolvedStatus == TestResultStatus.FAILED && cause != null) {
            resolvedStatus = ExceptionUtils.isAssertionFailure(cause)
                    ? TestResultStatus.FAILED
                    : TestResultStatus.INVALID;
        }
        TestResult result = TestResultCompletion.complete(resolvedStatus, cause);
        qaseTestCaseListener.addResult(result);
    }
}
