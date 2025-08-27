package io.qase.commons.reporters;

import io.qase.commons.QaseException;
import io.qase.commons.client.ApiClient;
import io.qase.commons.config.TestopsConfig;
import io.qase.commons.logger.Logger;
import io.qase.commons.models.domain.TestResult;
import io.qase.commons.models.domain.TestResultStatus;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestopsReporter implements InternalReporter {
    private static final Logger logger = Logger.getInstance();

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
        this.config.run.id = this.testRunId.intValue();
        logger.info("Test run %d started", this.testRunId);
    }

    @Override
    public void completeTestRun() throws QaseException {
        this.client.completeTestRun(this.testRunId);
        logger.info("Test run %d completed", this.testRunId);
    }

    @Override
    public synchronized void addResult(TestResult result) throws QaseException {
        this.results.add(result);

        if (result.execution.status == TestResultStatus.FAILED) {
            logger.info("See why this test failed: %s", this.prepareLink(result.testopsIds != null ? result.testopsIds.get(0) : null, result.title));
        }

        if (this.results.size() >= this.config.batch.size) {
            this.client.uploadResults(this.testRunId, this.results);
            this.results.clear();
        }
    }

    @Override
    public synchronized void uploadResults() throws QaseException {
        int batchSize = this.config.batch.size;
        int totalResults = this.results.size();

        if (totalResults == 0) {
            return;
        }

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
    public synchronized List<TestResult> getResults() {
        return this.results;
    }

    @Override
    public synchronized void setResults(List<TestResult> results) {
        this.results.clear();
        this.results.addAll(results);
    }

    @Override
    public List<Long> getTestCaseIdsForExecution() {
        try {
            return this.client.getTestCaseIdsForExecution();
        } catch (QaseException e) {
            return Collections.emptyList();
        }
    }

    private String prepareLink(Long id, String title) {
        String baseLink = this.getBaseUrl(this.config.api.host) + "/run/"
                + this.config.project + "/dashboard/" + this.testRunId
                + "?source=logs&status=%5B2%5D&search=";

        if (id != null) {
            return baseLink + this.config.project + "-" + id;
        }

        try {
            String encodedTitle = URLEncoder.encode(title, "UTF-8");
            return baseLink + encodedTitle;
        } catch (UnsupportedEncodingException e) {
            logger.error("Error while encoding title", e);
            return null;
        }
    }

    private String getBaseUrl(String host) {
        if (host.equals("qase.io")) {
            return "https://app.qase.io";
        }

        return "https://" + host.replace("api", "app");
    }
}
