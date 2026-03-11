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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

public class TestopsReporter implements InternalReporter {
    private static final Logger logger = Logger.getInstance();

    private final TestopsConfig config;
    private final ApiClient client;
    Long testRunId;
    private final List<TestResult> results;
    private final ExecutorService uploadExecutor;
    private volatile boolean shuttingDown = false;

    // Run-level aggregate upload statistics
    private final AtomicLong statTotalResults = new AtomicLong(0);
    private final AtomicLong statTotalAttachments = new AtomicLong(0);
    private final AtomicLong statTotalBytes = new AtomicLong(0);
    private final AtomicLong statTotalUploadTimeMs = new AtomicLong(0);
    private final AtomicLong statFailedBatches = new AtomicLong(0);

    // Submitted (pending + in-flight) counters for dynamic timeout computation
    private final AtomicLong statSubmittedBytes = new AtomicLong(0);
    private final AtomicLong statSubmittedAttachments = new AtomicLong(0);

    // Dynamic timeout constants
    static final long MINIMUM_UPLOAD_SPEED_BPS = 500L * 1024; // 500 KB/s conservative estimate
    static final int BASE_TIMEOUT_SECONDS = 60;                // baseline overhead
    static final int PER_ATTACHMENT_OVERHEAD_SECONDS = 2;      // API overhead per attachment
    static final int MAX_TIMEOUT_SECONDS = 3600;               // cap at 1 hour

    public TestopsReporter(TestopsConfig config, ApiClient client) {
        this.config = config;
        this.client = client;
        this.results = new ArrayList<>();
        this.uploadExecutor = Executors.newSingleThreadExecutor(r -> {
            Thread t = new Thread(r, "qase-upload");
            t.setDaemon(true);
            return t;
        });
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

        // Update external issue link if configured
        this.client.updateExternalIssue(this.testRunId);
    }

    @Override
    public void completeTestRun() throws QaseException {
        synchronized (this) {
            shuttingDown = true;
        }
        uploadResults();

        uploadExecutor.shutdown();
        int timeoutSeconds = computeUploadTimeout();
        try {
            if (!uploadExecutor.awaitTermination(timeoutSeconds, TimeUnit.SECONDS)) {
                logger.warn("Upload executor timed out after %d seconds", timeoutSeconds);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // LOGS-04: Emit aggregate upload statistics after all uploads complete
        logger.info("Upload summary: %d results, %d attachments, %d bytes, %d ms total, %d failed batches",
            statTotalResults.get(),
            statTotalAttachments.get(),
            statTotalBytes.get(),
            statTotalUploadTimeMs.get(),
            statFailedBatches.get());

        if (!this.config.run.complete) {
            logger.info("Test run %d: skipping completion (complete=false)", this.testRunId);
            return;
        }

        this.client.completeTestRun(this.testRunId);
        logger.info("Test run %d completed", this.testRunId);

        if (this.config.showPublicReportLink) {
            try {
                String publicReportUrl = this.client.enablePublicReport(this.testRunId);
                logger.info("Public report link: %s", publicReportUrl);
            } catch (QaseException e) {
                logger.warn("Failed to generate public report link: %s", e.getMessage());
            }
        }
    }

    @Override
    public synchronized void addResult(TestResult result) throws QaseException {
        if (shuttingDown) {
            logger.warn("Test run is completing, result for '%s' will not be uploaded", result.title);
            return;
        }
        // Check if result status should be filtered out
        if (shouldFilterResult(result)) {
            logger.debug("Filtering out result with status: %s", result.execution != null && result.execution.status != null ? result.execution.status : "null");
            return;
        }

        this.results.add(result);

        if (result.execution != null && result.execution.status == TestResultStatus.FAILED) {
            logger.info("See why this test failed: %s", this.prepareLink(result.testopsIds != null ? result.testopsIds.get(0) : null, result.title));
        }

        if (this.results.size() >= this.config.batch.size) {
            List<TestResult> batch = new ArrayList<>(this.results);
            this.results.clear();
            trackSubmittedBatch(batch);
            uploadExecutor.submit(() -> uploadBatch(batch));
        }
    }

    @Override
    public synchronized void uploadResults() throws QaseException {
        if (this.results.isEmpty()) {
            return;
        }

        int batchSize = this.config.batch.size;
        while (!this.results.isEmpty()) {
            int end = Math.min(batchSize, this.results.size());
            List<TestResult> batch = new ArrayList<>(this.results.subList(0, end));
            this.results.subList(0, end).clear();
            trackSubmittedBatch(batch);
            uploadExecutor.submit(() -> uploadBatch(batch));
        }
    }

    /**
     * Computes the upload timeout based on submitted data volume and configured minimum.
     * Formula: max(configured, BASE + submittedBytes / MIN_SPEED + attachments * OVERHEAD), capped at MAX.
     * Package-private for unit testing.
     */
    int computeUploadTimeout() {
        long bytes = statSubmittedBytes.get();
        long attachments = statSubmittedAttachments.get();
        long dynamicSeconds = BASE_TIMEOUT_SECONDS
                + bytes / MINIMUM_UPLOAD_SPEED_BPS
                + attachments * PER_ATTACHMENT_OVERHEAD_SECONDS;
        int computed = (int) Math.min(dynamicSeconds, MAX_TIMEOUT_SECONDS);
        int timeout = Math.max(config.batch.uploadTimeout, computed);
        logger.debug("Dynamic upload timeout: %ds (configured=%ds, computed=%ds, submitted=%d bytes, %d attachments)",
                timeout, config.batch.uploadTimeout, computed, bytes, attachments);
        return timeout;
    }

    private void trackSubmittedBatch(List<TestResult> batch) {
        long bytes = batch.stream()
            .flatMap(r -> r.attachments != null ? r.attachments.stream() : Stream.empty())
            .mapToLong(a -> a.sizeBytes)
            .sum();
        int attachments = batch.stream()
            .mapToInt(r -> r.attachments != null ? r.attachments.size() : 0)
            .sum();
        statSubmittedBytes.addAndGet(bytes);
        statSubmittedAttachments.addAndGet(attachments);
    }

    private void uploadBatch(List<TestResult> batch) {
        int attachmentCount = batch.stream()
            .mapToInt(r -> r.attachments != null ? r.attachments.size() : 0)
            .sum();
        long batchBytes = batch.stream()
            .flatMap(r -> r.attachments != null ? r.attachments.stream() : Stream.empty())
            .mapToLong(a -> a.sizeBytes)
            .sum();

        logger.info("Uploading batch: %d results, %d attachments, %.1f MB",
            batch.size(), attachmentCount, batchBytes / (1024.0 * 1024.0));

        long startNanos = System.nanoTime();
        try {
            this.client.uploadResults(this.testRunId, batch);
            long elapsedMs = (System.nanoTime() - startNanos) / 1_000_000;
            statTotalResults.addAndGet(batch.size());
            statTotalAttachments.addAndGet(attachmentCount);
            statTotalBytes.addAndGet(batchBytes);
            statTotalUploadTimeMs.addAndGet(elapsedMs);
        } catch (QaseException e) {
            statFailedBatches.incrementAndGet();
            logger.warn("Batch upload failed, %d results dropped: %s", batch.size(), e.getMessage());
        }
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
                + "?source=logs&search=";

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

    private boolean shouldFilterResult(TestResult result) {
        if (config.statusFilter == null || config.statusFilter.isEmpty()) {
            return false;
        }

        if (result.execution == null || result.execution.status == null) {
            return false;
        }

        String statusName = result.execution.status.name();
        return config.statusFilter.contains(statusName);
    }

    private String getBaseUrl(String host) {
        if (host.equals("qase.io")) {
            return "https://app.qase.io";
        }

        return "https://" + host.replace("api", "app");
    }
}
