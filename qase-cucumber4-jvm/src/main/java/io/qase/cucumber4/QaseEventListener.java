package io.qase.cucumber4;

import cucumber.api.Result;
import cucumber.api.event.*;
import gherkin.pickles.PickleTag;
import io.qase.api.QaseClient;
import io.qase.api.StepStorage;
import io.qase.api.exceptions.QaseException;
import io.qase.api.utils.CucumberUtils;
import io.qase.client.ApiClient;
import io.qase.client.api.ResultsApi;
import io.qase.client.model.ResultCreate;
import io.qase.client.model.ResultCreateBulk;
import io.qase.client.model.ResultCreateSteps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.qase.api.Constants.X_CLIENT_REPORTER;
import static io.qase.api.QaseClient.getConfig;
import static io.qase.api.utils.IntegrationUtils.*;

public class QaseEventListener implements ConcurrentEventListener {
    private static final Logger logger = LoggerFactory.getLogger(QaseEventListener.class);
    private final ApiClient apiClient = QaseClient.getApiClient();
    private final ResultsApi resultsApi = new ResultsApi(apiClient);
    private final ThreadLocal<Long> startTime = new ThreadLocal<>();
    private final ResultCreateBulk resultCreateBulk = new ResultCreateBulk();

    public QaseEventListener() {
        apiClient.addDefaultHeader(X_CLIENT_REPORTER, "Cucumber 4-JVM");
    }

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestCaseStarted.class, this::testCaseStarted);
        publisher.registerHandlerFor(TestCaseFinished.class, this::testCaseFinished);
        publisher.registerHandlerFor(TestRunFinished.class, this::testRunFinished);
    }

    private void testRunFinished(TestRunFinished testRunFinished) {
        sendBulkResult();
    }

    private void testCaseStarted(TestCaseStarted event) {
        startTime.set(System.currentTimeMillis());
    }

    private void testCaseFinished(TestCaseFinished event) {
        try {
            Duration duration = Duration.ofMillis(System.currentTimeMillis() - startTime.get());
            List<PickleTag> pickleTags = event.testCase.getTags();
            List<String> tags = pickleTags.stream().map(PickleTag::getName).collect(Collectors.toList());
            Long caseId = CucumberUtils.getCaseId(tags);
            if (!QaseClient.isEnabled() || caseId == null) {
                return;
            }
            if (getConfig().useBulk()) {
                addBulkResult(caseId, duration, event.result);
            } else {
                send(caseId, duration, event.result);
            }
        } finally {
            startTime.remove();
        }
    }

    private void addBulkResult(Long caseId, Duration duration, Result result) {
        resultCreateBulk.addResultsItem(getResultItem(caseId, duration, result));
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
        } catch (QaseException e) {
            logger.error(e.getMessage());
        }
    }

    private void send(Long caseId, Duration duration, Result result) {
        if (!QaseClient.isEnabled()) {
            return;
        }
        try {
            resultsApi.createResult(getConfig().projectCode(),
                    getConfig().runId(),
                    getResultItem(caseId, duration, result));
        } catch (QaseException e) {
            logger.error(e.getMessage());
        }
    }


    public ResultCreate getResultItem(Long caseId, Duration duration, Result result) {
        ResultCreate.StatusEnum status = convertStatus(result.getStatus());
        if (status == null) {
            return null;
        }
        Optional<Throwable> optionalThrowable = Optional.ofNullable(result.getError());
        String comment = optionalThrowable
                .flatMap(throwable -> Optional.of(throwable.toString())).orElse(null);
        Boolean isDefect = optionalThrowable
                .flatMap(throwable -> Optional.of(throwable instanceof AssertionError))
                .orElse(false);
        String stacktrace = optionalThrowable
                .flatMap(throwable -> Optional.of(getStacktrace(throwable))).orElse(null);
        LinkedList<ResultCreateSteps> steps = StepStorage.getSteps();
        return new ResultCreate()
                .caseId(caseId)
                .status(status)
                .timeMs(duration.toMillis())
                .comment(comment)
                .stacktrace(stacktrace)
                .steps(steps.isEmpty() ? null : steps)
                .defect(isDefect);
    }

    private ResultCreate.StatusEnum convertStatus(Result.Type status) {
        switch (status) {
            case FAILED:
                return ResultCreate.StatusEnum.FAILED;
            case PASSED:
                return ResultCreate.StatusEnum.PASSED;
            case PENDING:
            case SKIPPED:
            case AMBIGUOUS:
            case UNDEFINED:
            default:
                return ResultCreate.StatusEnum.SKIPPED;
        }
    }
}
