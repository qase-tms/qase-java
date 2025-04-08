package io.qase.commons.reporters;

import io.qase.commons.QaseException;
import io.qase.commons.client.ApiClient;
import io.qase.commons.client.ApiClientV2;
import io.qase.commons.config.Mode;
import io.qase.commons.config.QaseConfig;
import io.qase.commons.hooks.HooksManager;
import io.qase.commons.models.domain.TestResult;
import io.qase.commons.writers.FileWriter;
import io.qase.commons.writers.Writer;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.List;


public class CoreReporter implements Reporter {
    private static final Logger logger = LoggerFactory.getLogger(CoreReporter.class);

    private InternalReporter reporter;
    private InternalReporter fallback;
    private final HooksManager hooksManager;

    public CoreReporter(QaseConfig config) {
        logger.info("Qase config: {}", config);

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
        logger.info("Adding result: {}", result);

        this.hooksManager.beforeTestStop(result);

        executeWithFallback(() -> reporter.addResult(result), "add result");
    }

    @Override
    public void uploadResults() {
        logger.info("Uploading results");

        executeWithFallback(() -> reporter.uploadResults(), "upload results");
    }

    public List<Long> getTestCaseIdsForExecution() {
        return reporter.getTestCaseIdsForExecution();
    }

    private void executeWithFallback(ReporterAction action, String actionName) {
        if (reporter != null) {
            try {
                action.execute();
            } catch (QaseException e) {
                logger.error("Failed to {} with reporter", actionName, e);
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
                logger.error("Failed to {} with reporter after fallback", actionName, ex);
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
                ApiClient client = new ApiClientV2(config);
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
