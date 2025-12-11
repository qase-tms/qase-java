package io.qase.commons.reporters;

import io.qase.commons.QaseException;
import io.qase.commons.client.ApiClient;
import io.qase.commons.client.ApiClientV2;
import io.qase.commons.config.Mode;
import io.qase.commons.config.QaseConfig;
import io.qase.commons.hooks.HooksManager;
import io.qase.commons.logger.Logger;
import io.qase.commons.models.domain.TestResult;
import io.qase.commons.models.domain.TestResultStatus;
import io.qase.commons.utils.StatusMappingUtils;
import io.qase.commons.writers.FileWriter;
import io.qase.commons.writers.Writer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class CoreReporter implements Reporter {
    private static final Logger logger = Logger.getInstance();

    private InternalReporter reporter;
    private InternalReporter fallback;
    private final HooksManager hooksManager;
    private final QaseConfig config;
    private final String reporterName;
    private final String reporterVersion;
    private final String frameworkName;
    private final String frameworkVersion;
    private final java.util.Map<String, String> hostInfo;

    public CoreReporter(QaseConfig config) {
        this(config, null, null, null, null, null);
    }

    public CoreReporter(QaseConfig config, String reporterName, String reporterVersion,
                       String frameworkName, String frameworkVersion, java.util.Map<String, String> hostInfo) {
        this.config = config;
        this.reporterName = reporterName;
        this.reporterVersion = reporterVersion;
        this.frameworkName = frameworkName;
        this.frameworkVersion = frameworkVersion;
        this.hostInfo = hostInfo;
        this.reporter = this.createReporter(config, config.mode);
        this.fallback = this.createReporter(config, config.fallback);
        this.hooksManager = HooksManager.getDefaultHooksManager();
    }

    @Override
    public void startTestRun() {
        logger.info("Starting test run");

        executeWithFallback(() -> reporter.startTestRun(), "start test run");
    }

    @Override
    public void completeTestRun() {
        logger.info("Completing test run");

        executeWithFallback(() -> reporter.completeTestRun(), "complete test run");
    }

    @Override
    public void addResult(TestResult result) {
        logger.debug("Adding result: %s", result);

        // Apply status mapping before processing
        applyStatusMapping(result);

        this.hooksManager.beforeTestStop(result);

        executeWithFallback(() -> reporter.addResult(result), "add result");
    }

    @Override
    public void uploadResults() {
        logger.info("Uploading results");

        executeWithFallback(() -> reporter.uploadResults(), "upload results");
    }

    public List<Long> getTestCaseIdsForExecution() {
        if (reporter == null) {
            return new ArrayList<>();
        }

        return reporter.getTestCaseIdsForExecution();
    }

    /**
     * Apply status mapping to test result.
     * This method applies status mapping only to the main test result status.
     * Step statuses are not modified.
     * 
     * @param result the test result to apply mapping to
     */
    private void applyStatusMapping(TestResult result) {
        if (result == null || config.statusMapping == null || config.statusMapping.isEmpty()) {
            return;
        }

        // Apply mapping to main test result status only
        if (result.execution != null && result.execution.status != null) {
            TestResultStatus originalStatus = result.execution.status;
            TestResultStatus mappedStatus = StatusMappingUtils.applyStatusMapping(originalStatus, config.statusMapping);
            
            if (mappedStatus != originalStatus) {
                logger.info("Status mapping applied to test '%s': %s -> %s", 
                    result.title, originalStatus.name().toLowerCase(), mappedStatus.name().toLowerCase());
                result.execution.status = mappedStatus;
            }
        }
    }


    private void executeWithFallback(ReporterAction action, String actionName) {
        if (reporter != null) {
            try {
                action.execute();
            } catch (QaseException e) {
                logger.error("Failed to %s with reporter: %s", actionName, e.getMessage());
                useFallback();
                retryAction(action, actionName);
            }
        }
    }

    private void retryAction(ReporterAction action, String actionName) {
        if (reporter != null) {
            try {
                action.execute();
            } catch (QaseException ex) {
                logger.error("Failed to %s with reporter after fallback: %s", actionName, ex.getMessage());
                reporter = null;
            }
        }
    }

    private void useFallback() {
        if (fallback == null) {
            reporter = null;
            return;
        }

        try {
            fallback.startTestRun();
            fallback.setResults(reporter.getResults());
            reporter = fallback;
            fallback = null;
        } catch (QaseException e) {
            logger.error("Failed to start test run with fallback reporter", e);
            reporter = null;
        }
    }

    private InternalReporter createReporter(QaseConfig config, Mode mode) {
        switch (mode) {
            case TESTOPS:
                ApiClient client = new ApiClientV2(config, reporterName, reporterVersion, frameworkName, frameworkVersion, hostInfo);
                return new TestopsReporter(config.testops, client);
            case REPORT:
                Writer writer = new FileWriter(config.report.connection);
                return new FileReporter(config, writer);
            default:
                return null;
        }
    }

    @FunctionalInterface
    private interface ReporterAction {
        void execute() throws QaseException;
    }
}
