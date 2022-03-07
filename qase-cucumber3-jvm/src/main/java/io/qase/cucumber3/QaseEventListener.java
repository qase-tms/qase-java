package io.qase.cucumber3;

import cucumber.api.Result;
import cucumber.api.event.EventPublisher;
import cucumber.api.event.TestCaseFinished;
import cucumber.api.event.TestCaseStarted;
import cucumber.api.formatter.Formatter;
import gherkin.pickles.PickleTag;
import io.qase.api.QaseClient;
import io.qase.api.StepStorage;
import io.qase.api.exceptions.QaseException;
import io.qase.api.utils.CucumberUtils;
import io.qase.client.ApiClient;
import io.qase.client.api.ResultsApi;
import io.qase.client.model.ResultCreate;
import io.qase.client.model.ResultCreate.StatusEnum;
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

public class QaseEventListener implements Formatter {
    private static final Logger logger = LoggerFactory.getLogger(QaseEventListener.class);
    private final ApiClient apiClient = QaseClient.getApiClient();
    private final ResultsApi resultsApi = new ResultsApi(apiClient);
    private long startTime;

    public QaseEventListener() {
        apiClient.addDefaultHeader(X_CLIENT_REPORTER, "Cucumber 3-JVM");
    }

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestCaseStarted.class, this::testCaseStarted);
        publisher.registerHandlerFor(TestCaseFinished.class, this::testCaseFinished);
    }

    private void testCaseStarted(TestCaseStarted event) {
        startTime = System.currentTimeMillis();
    }

    private void testCaseFinished(TestCaseFinished event) {
        Duration duration = Duration.ofMillis(System.currentTimeMillis() - startTime);
        List<PickleTag> pickleTags = event.testCase.getTags();
        List<String> tags = pickleTags.stream().map(PickleTag::getName).collect(Collectors.toList());
        Long caseId = CucumberUtils.getCaseId(tags);
        if (caseId != null) {
            send(caseId, duration, event.result);
        }
    }

    private void send(Long caseId, Duration duration, Result result) {
        if (!QaseClient.isEnabled()) {
            return;
        }
        try {
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
            resultsApi.createResult(getConfig().projectCode(),
                    getConfig().runId(),
                    new ResultCreate()
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
