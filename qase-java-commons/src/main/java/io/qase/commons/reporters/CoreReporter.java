package io.qase.commons.reporters;

import io.qase.commons.QaseException;
import io.qase.commons.client.ApiClientV1;
import io.qase.commons.config.Mode;
import io.qase.commons.config.QaseConfig;
import io.qase.commons.models.domain.TestResult;
import io.qase.commons.writers.FileWriter;
import io.qase.commons.writers.Writer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoreReporter implements Reporter {
    private static final Logger logger = LoggerFactory.getLogger(CoreReporter.class);

    private InternalReporter reporter;
    private InternalReporter fallback;

    public CoreReporter(QaseConfig config) {
        this.reporter = this.createReporter(config, config.mode);
        this.fallback = this.createReporter(config, config.fallback);
    }

    @Override
    public void startTestRun() {
        executeWithFallback(() -> reporter.startTestRun(), "start test run");
    }

    @Override
    public void completeTestRun() {
        executeWithFallback(() -> reporter.completeTestRun(), "complete test run");
    }

    @Override
    public void addResult(TestResult result) {
        executeWithFallback(() -> reporter.addResult(result), "add result");
    }

    @Override
    public void uploadResults() {
        executeWithFallback(() -> reporter.uploadResults(), "upload results");
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
                ApiClientV1 client = new ApiClientV1(config);
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
