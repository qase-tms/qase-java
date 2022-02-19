package io.qase.junit4;


import io.qase.api.QaseClient;
import io.qase.api.StepStorage;
import io.qase.api.annotation.CaseId;
import io.qase.api.annotation.CaseTitle;
import io.qase.api.exceptions.QaseException;
import io.qase.client.ApiClient;
import io.qase.client.api.ResultsApi;
import io.qase.client.model.ResultCreate;
import io.qase.client.model.ResultCreate.StatusEnum;
import io.qase.client.model.ResultCreateCase;
import io.qase.client.model.ResultCreateSteps;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Set;

import static io.qase.api.QaseClient.getConfig;
import static io.qase.api.utils.IntegrationUtils.getStacktrace;

public class QaseListener extends RunListener {
    private static final Logger logger = LoggerFactory.getLogger(QaseListener.class);
    private static final ThreadLocal<Set<Integer>> cases = ThreadLocal.withInitial(HashSet::new);
    private final ApiClient apiClient = QaseClient.getApiClient();
    private final ResultsApi resultsApi = new ResultsApi(apiClient);
    private long startTime;

    @Override
    public void testStarted(Description description) {
        startTime = System.currentTimeMillis();
        cases.get().add(description.hashCode());
    }

    @Override
    public void testFinished(Description description) {
        if (cases.get().contains(description.hashCode())) {
            send(description, StatusEnum.PASSED, null);
            cases.get().remove(description.hashCode());
        }
    }

    @Override
    public void testFailure(Failure failure) {
        cases.get().remove(failure.getDescription().hashCode());
        send(failure.getDescription(), StatusEnum.FAILED, failure.getException());
    }

    @Override
    public void testAssumptionFailure(Failure failure) {
        cases.get().remove(failure.getDescription().hashCode());
        send(failure.getDescription(), StatusEnum.SKIPPED, null);
    }

    @Override
    public void testIgnored(Description description) {
        cases.get().remove(description.hashCode());
        send(description, StatusEnum.SKIPPED, null);
    }

    private void send(Description description, StatusEnum status, Throwable error) {
        if (!QaseClient.isEnabled()) {
            return;
        }
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
        try {
            resultsApi.createResult(getConfig().projectCode(),
                    getConfig().runId(),
                    new ResultCreate()
                            ._case(caseTitle == null ? null : new ResultCreateCase().title(caseTitle))
                            .caseId(caseId)
                            .status(status)
                            .timeMs(duration.toMillis())
                            .comment(comment)
                            .stacktrace(stacktrace)
                            .steps(steps.isEmpty() ? null : steps)
                            .defect(isDefect));
        } catch (QaseException e) {
            logger.error(e.getMessage());
        }
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
