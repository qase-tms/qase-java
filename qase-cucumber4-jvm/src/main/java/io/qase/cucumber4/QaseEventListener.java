package io.qase.cucumber4;

import cucumber.api.Result;
import cucumber.api.event.*;
import gherkin.pickles.PickleTag;
import io.qase.api.QaseClient;
import io.qase.api.StepStorage;
import io.qase.api.utils.CucumberUtils;
import io.qase.client.model.ResultCreate;
import io.qase.client.model.ResultCreate.StatusEnum;
import io.qase.client.model.ResultCreateSteps;
import io.qase.client.services.QaseTestCaseListener;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.qase.api.utils.IntegrationUtils.getStacktrace;
import static io.qase.configuration.QaseModule.INJECTOR;

public class QaseEventListener implements ConcurrentEventListener {

    private static final String REPORTER_NAME = "Cucumber 4-JVM";

    private final QaseTestCaseListener qaseTestCaseListener;

    public QaseEventListener() {
        this.qaseTestCaseListener = INJECTOR.getInstance(QaseTestCaseListener.class);
        qaseTestCaseListener.setupReporterName(REPORTER_NAME);
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
        qaseTestCaseListener.reportResults();
    }

    private void testCaseStarted(TestCaseStarted event) {
        qaseTestCaseListener.onTestCaseStarted();
    }

    private void testCaseFinished(TestCaseFinished event) {
        qaseTestCaseListener.onTestCaseFinished(getResultItem(event));
    }

    private ResultCreate getResultItem(TestCaseFinished event) {
        List<PickleTag> pickleTags = event.testCase.getTags();
        List<String> tags = pickleTags.stream().map(PickleTag::getName).collect(Collectors.toList());
        Long caseId = CucumberUtils.getCaseId(tags);
        StatusEnum status = convertStatus(event.result.getStatus());
        Optional<Throwable> optionalThrowable = Optional.ofNullable(event.result.getError());
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
