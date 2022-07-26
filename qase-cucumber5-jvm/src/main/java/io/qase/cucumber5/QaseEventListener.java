package io.qase.cucumber5;

import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.*;
import io.qase.api.QaseClient;
import io.qase.api.StepStorage;
import io.qase.api.utils.CucumberUtils;
import io.qase.client.model.ResultCreate;
import io.qase.client.model.ResultCreate.StatusEnum;
import io.qase.client.model.ResultCreateSteps;
import io.qase.reporters.QaseReporter;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static io.qase.api.utils.IntegrationUtils.getStacktrace;
import static io.qase.configuration.QaseModule.INJECTOR;

public class QaseEventListener implements ConcurrentEventListener {

    private static final String REPORTER_NAME = "Cucumber 5-JVM";

    private final QaseReporter qaseReporter;

    public QaseEventListener() {
        this.qaseReporter = INJECTOR.getInstance(QaseReporter.class);
        qaseReporter.setupReporterName(REPORTER_NAME);
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
        qaseReporter.onTestRunFinished();
    }

    private void testCaseStarted(TestCaseStarted event) {
        qaseReporter.onTestCaseStarted();
    }

    private void testCaseFinished(TestCaseFinished event) {
        qaseReporter.onTestCaseFinished(getResultItem(event));
    }

    private ResultCreate getResultItem(TestCaseFinished event) {
        List<String> tags = event.getTestCase().getTags();
        Long caseId = CucumberUtils.getCaseId(tags);
        StatusEnum status = convertStatus(event.getResult().getStatus());
        Optional<Throwable> optionalThrowable = Optional.ofNullable(event.getResult().getError());
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
            .comment(comment)
            .stacktrace(stacktrace)
            .steps(steps.isEmpty() ? null : steps)
            .defect(isDefect);
    }

    private StatusEnum convertStatus(Status status) {
        switch (status) {
            case FAILED:
                return StatusEnum.FAILED;
            case PASSED:
                return StatusEnum.PASSED;
            case PENDING:
            case SKIPPED:
            case AMBIGUOUS:
            case UNDEFINED:
            case UNUSED:
            default:
                return StatusEnum.SKIPPED;
        }
    }
}
