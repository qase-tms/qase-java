package io.qase.commons.reporters;

import io.qase.commons.QaseException;
import io.qase.commons.client.ApiClient;
import io.qase.commons.config.TestopsConfig;
import io.qase.commons.models.domain.TestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class TestopsReporter implements InternalReporter {
    private static final Logger logger = LoggerFactory.getLogger(TestopsReporter.class);

    private final TestopsConfig config;
    private final ApiClient client;
    Long testRunId;
    private final List<TestResult> results;

    public TestopsReporter(TestopsConfig config, ApiClient client) {
        this.config = config;
        this.client = client;
        this.results = new ArrayList<>();
    }

    @Override
    public void startTestRun() throws QaseException {
        if (config.run.id != 0) {
            this.testRunId = config.run.id.longValue();
            return;
        }
        this.testRunId = this.client.createTestRun();
        logger.info("Test run {} started", this.testRunId);
    }

    @Override
    public void completeTestRun() throws QaseException {
        this.client.completeTestRun(this.testRunId);
        logger.info("Test run {} completed", this.testRunId);
    }

    @Override
    public void addResult(TestResult result) throws QaseException {
        this.results.add(result);

        if (this.results.size() >= this.config.batch.size) {
            this.client.uploadResults(this.testRunId, this.results);
            this.results.clear();
        }
    }

    @Override
    public void uploadResults() throws QaseException {
        int batchSize = this.config.batch.size;
        int totalResults = this.results.size();

        if (totalResults <= batchSize) {
            this.client.uploadResults(this.testRunId, this.results);
            this.results.clear();
            return;
        }

        for (int index = 0; index < totalResults; index += batchSize) {
            int end = Math.min(index + batchSize, totalResults);
            this.client.uploadResults(this.testRunId, this.results.subList(index, end));
        }

        this.results.clear();
    }

    @Override
    public List<TestResult> getResults() {
        return this.results;
    }

    @Override
    public void setResults(List<TestResult> results) {
        this.results.clear();
        this.results.addAll(results);
    }
}
