package io.qase.cucumber3;

import com.google.inject.Inject;
import cucumber.api.Result;
import cucumber.api.event.EventPublisher;
import cucumber.api.event.TestCaseFinished;
import cucumber.api.event.TestCaseStarted;
import cucumber.api.event.TestRunFinished;
import cucumber.api.formatter.Formatter;
import gherkin.pickles.PickleTag;
import io.qase.api.QaseClient;
import io.qase.api.StepStorage;
import io.qase.api.exceptions.QaseException;
import io.qase.api.utils.CucumberUtils;
import io.qase.client.ApiClient;
import io.qase.client.api.RunsApi;
import io.qase.client.model.ResultCreate;
import io.qase.client.model.ResultCreate.StatusEnum;
import io.qase.client.model.ResultCreateSteps;
import io.qase.client.services.ReportersResultOperations;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.qase.api.Constants.X_CLIENT_REPORTER;
import static io.qase.api.QaseClient.getConfig;
import static io.qase.api.utils.IntegrationUtils.getStacktrace;

@Slf4j
public class QaseEventListener implements Formatter {
    private static final String REPORTER_NAME = "Cucumber 3-JVM";
    private final ThreadLocal<Long> startTime = new ThreadLocal<>();

    private final RunsApi runsApi;

    private final ReportersResultOperations resultOperations;

    @Inject
    public QaseEventListener(ApiClient apiClient, RunsApi runsApi, ReportersResultOperations resultOperations) {
        this.runsApi = runsApi;
        this.resultOperations = resultOperations;
        apiClient.addDefaultHeader(X_CLIENT_REPORTER, REPORTER_NAME);
    }

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        if (QaseClient.isEnabled()) {
            publisher.registerHandlerFor(TestCaseStarted.class, this::testCaseStarted);
            publisher.registerHandlerFor(TestCaseFinished.class, this::testCaseFinished);
            publisher.registerHandlerFor(TestRunFinished.class, this::testRunFinished);
        }
    }

    private void testRunFinished(TestRunFinished testRunFinished) {
        if (getConfig().useBulk()) {
            resultOperations.sendBulkResult();
        }
        if (getConfig().runAutocomplete()) {
            try {
                runsApi.completeRun(getConfig().projectCode(), getConfig().runId());
            } catch (QaseException e) {
                log.error(e.getMessage());
            }
        }
    }

    private void testCaseStarted(TestCaseStarted event) {
        startTime.set(System.currentTimeMillis());
    }

    private void testCaseFinished(TestCaseFinished event) {
        Duration duration = Duration.ofMillis(System.currentTimeMillis() - startTime.get());
        List<PickleTag> pickleTags = event.testCase.getTags();
        List<String> tags = pickleTags.stream().map(PickleTag::getName).collect(Collectors.toList());
        Long caseId = CucumberUtils.getCaseId(tags);
        if (caseId == null) {
            return;
        }
        if (getConfig().useBulk()) {
            resultOperations.addBulkResult(getResultItem(caseId, duration, event.result));
        } else {
            resultOperations.send(getResultItem(caseId, duration, event.result));
        }
    }

    private ResultCreate getResultItem(Long caseId, Duration duration, Result result) {
        StatusEnum status = convertStatus(result.getStatus());
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

    private StatusEnum convertStatus(Result.Type status) {
        switch (status) {
            case FAILED:
                return StatusEnum.FAILED;
            case PASSED:
                return StatusEnum.PASSED;
            case PENDING:
            case SKIPPED:
            case AMBIGUOUS:
            case UNDEFINED:
            default:
                return StatusEnum.SKIPPED;
        }
    }
}
