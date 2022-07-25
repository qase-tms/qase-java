package io.qase.junit4;


import io.qase.api.QaseClient;
import io.qase.api.StepStorage;
import io.qase.api.annotation.CaseId;
import io.qase.api.annotation.CaseTitle;
import io.qase.api.exceptions.QaseException;
import io.qase.client.ApiClient;
import io.qase.client.api.AttachmentsApi;
import io.qase.client.api.ResultsApi;
import io.qase.client.api.RunsApi;
import io.qase.client.model.ResultCreate;
import io.qase.client.model.ResultCreate.StatusEnum;
import io.qase.client.model.ResultCreateBulk;
import io.qase.client.model.ResultCreateCase;
import io.qase.client.model.ResultCreateSteps;
import io.qase.client.services.ScreenshotsSender;
import io.qase.client.services.impl.AttachmentsApiScreenshotsUploader;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Set;

import static io.qase.api.Constants.X_CLIENT_REPORTER;
import static io.qase.api.QaseClient.getConfig;
import static io.qase.api.utils.IntegrationUtils.getStacktrace;
import static io.qase.client.model.ResultCreate.StatusEnum.*;

public class QaseListener extends RunListener {
    private static final Logger logger = LoggerFactory.getLogger(QaseListener.class);
    private static final ThreadLocal<Set<Integer>> cases = ThreadLocal.withInitial(HashSet::new);
    private final ApiClient apiClient = QaseClient.getApiClient();
    private final ResultsApi resultsApi = new ResultsApi(apiClient);
    private final RunsApi runsApi = new RunsApi(apiClient);
    private final ResultCreateBulk resultCreateBulk = new ResultCreateBulk();

    private final ScreenshotsSender screenshotsSender =
        new AttachmentsApiScreenshotsUploader(new AttachmentsApi(apiClient));

    private long startTime;

    public QaseListener() {
        apiClient.addDefaultHeader(X_CLIENT_REPORTER, "JUnit 4");
    }

    @Override
    public void testStarted(Description description) {
        startTime = System.currentTimeMillis();
        cases.get().add(description.hashCode());
    }

    @Override
    public void testFinished(Description description) {
        if (getConfig().useBulk()) {
            addBulkResult(description, PASSED, null);
        } else {
            send(description, PASSED, null);
        }
    }

    @Override
    public void testFailure(Failure failure) {
        if (getConfig().useBulk()) {
            addBulkResult(failure.getDescription(), FAILED, failure.getException());
        } else {
            send(failure.getDescription(), FAILED, failure.getException());
        }
    }

    @Override
    public void testAssumptionFailure(Failure failure) {
        if (getConfig().useBulk()) {
            addBulkResult(failure.getDescription(), SKIPPED, null);
        } else {
            send(failure.getDescription(), SKIPPED, null);
        }
    }

    @Override
    public void testIgnored(Description description) {
        if (getConfig().useBulk()) {
            addBulkResult(description, SKIPPED, null);
        } else {
            send(description, SKIPPED, null);
        }
    }

    @Override
    public void testRunFinished(Result result) throws Exception {
        if (getConfig().useBulk()) {
            sendBulkResult();
        }
        if (getConfig().runAutocomplete()) {
            try {
                runsApi.completeRun(getConfig().projectCode(), getConfig().runId());
            } catch (QaseException e) {
                logger.error(e.getMessage());
            }
        }
        super.testRunFinished(result);
    }

    private void addBulkResult(Description description, StatusEnum status, Throwable error) {
        if (QaseClient.isEnabled() && cases.get().contains(description.hashCode())) {
            resultCreateBulk.addResultsItem(getResultItem(description, status, error));
            cases.get().remove(description.hashCode());
        }
    }

    private void send(Description description, StatusEnum status, Throwable error) {
        if (!QaseClient.isEnabled() || !cases.get().contains(description.hashCode())) {
            return;
        }
        ResultCreate result = getResultItem(description, status, error);
        try {
            resultsApi.createResult(getConfig().projectCode(),
                    getConfig().runId(),
                    result);
        } catch (QaseException e) {
            logger.error(e.getMessage());
        }
        cases.get().remove(description.hashCode());
    }

    private void sendBulkResult() {
        if (!QaseClient.isEnabled()) {
            return;
        }
        try {
            resultsApi.createResultBulk(
                    getConfig().projectCode(),
                    getConfig().runId(),
                    resultCreateBulk
            );
            screenshotsSender.sendScreenshotsIfPermitted();
            resultCreateBulk.getResults().clear();
        } catch (QaseException e) {
            logger.error(e.getMessage());
        }
    }

    private ResultCreate getResultItem(Description description, StatusEnum status, Throwable error) {
        long end = System.currentTimeMillis();
        Duration duration = Duration.ofMillis(end - startTime);

        Long caseId = getCaseId(description);
        String caseTitle = null;
        if (caseId == null) {
            caseTitle = getCaseTitle(description);
        }
        Optional<Throwable> optionalThrowable = Optional.ofNullable(error);
        String comment = optionalThrowable
                .flatMap(throwable -> Optional.of(throwable.toString())).orElse(null);
        Boolean isDefect = optionalThrowable
                .flatMap(throwable -> Optional.of(throwable instanceof AssertionError))
                .orElse(false);
        String stacktrace = optionalThrowable
                .flatMap(throwable -> Optional.of(getStacktrace(throwable))).orElse(null);
        LinkedList<ResultCreateSteps> steps = StepStorage.getSteps();
        return new ResultCreate()
                ._case(caseTitle == null ? null : new ResultCreateCase().title(caseTitle))
                .caseId(caseId)
                .status(status)
                .timeMs(duration.toMillis())
                .comment(comment)
                .stacktrace(stacktrace)
                .steps(steps.isEmpty() ? null : steps)
                .defect(isDefect);
    }

    private Long getCaseId(Description description) {
        CaseId caseIdAnnotation = description.getAnnotation(CaseId.class);
        return caseIdAnnotation != null ? caseIdAnnotation.value() : null;
    }

    private String getCaseTitle(Description description) {
        CaseTitle caseTitleAnnotation = description.getAnnotation(CaseTitle.class);
        return caseTitleAnnotation != null ? caseTitleAnnotation.value() : null;
    }
}
