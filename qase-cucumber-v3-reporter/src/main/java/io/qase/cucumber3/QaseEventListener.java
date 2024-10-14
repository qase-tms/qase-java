package io.qase.cucumber3;

import cucumber.api.PickleStepTestStep;
import cucumber.api.Result;
import cucumber.api.event.*;
import cucumber.api.formatter.Formatter;
import gherkin.pickles.PickleTag;
import io.qase.api.QaseClient;
import io.qase.commons.StepStorage;
import io.qase.commons.config.QaseConfig;
import io.qase.api.services.QaseTestCaseListener;
import io.qase.api.utils.CucumberUtils;
import io.qase.api.utils.IntegrationUtils;
import io.qase.client.v1.models.ResultCreate;
import io.qase.client.v1.models.ResultCreateCase;
import io.qase.client.v1.models.TestStepResultCreate;
import io.qase.cucumber3.guice.module.Cucumber3Module;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.qase.api.utils.IntegrationUtils.getStacktrace;

public class QaseEventListener implements Formatter {

    private static final String REPORTER_NAME = "Cucumber 3-JVM";

    @Getter(lazy = true, value = AccessLevel.PRIVATE)
    private final QaseTestCaseListener qaseTestCaseListener = createQaseListener();

    static {
        System.setProperty(QaseConfig.QASE_CLIENT_REPORTER_NAME_KEY, REPORTER_NAME);
    }

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        if (QaseClient.isEnabled()) {
            publisher.registerHandlerFor(TestCaseStarted.class, this::testCaseStarted);
            publisher.registerHandlerFor(TestCaseFinished.class, this::testCaseFinished);
            publisher.registerHandlerFor(TestRunFinished.class, this::testRunFinished);
            publisher.registerHandlerFor(TestStepStarted.class, this::testStepStarted);
            publisher.registerHandlerFor(TestStepFinished.class, this::testStepFinished);
        }
    }

    private void testStepStarted(TestStepStarted testStepStarted) {
        if (testStepStarted.testStep instanceof PickleStepTestStep) {
            StepStorage.startStep();
        }
    }

    private void testStepFinished(TestStepFinished testStepFinished) {
        if (testStepFinished.testStep instanceof PickleStepTestStep) {
            PickleStepTestStep step = (PickleStepTestStep) testStepFinished.testStep;
            String stepText = step.getStepText();
            Result result = testStepFinished.result;
            switch (result.getStatus()) {
                case PASSED:
                    StepStorage.getCurrentStep()
                            .action(stepText)
                            .status(TestStepResultCreate.StatusEnum.PASSED);
                    StepStorage.stopStep();
                    break;
                case SKIPPED:
                    break;
                case PENDING:
                    break;
                case UNDEFINED:
                    break;
                case AMBIGUOUS:
                    break;
                case FAILED:
                    StepStorage.getCurrentStep()
                            .action(stepText)
                            .status(TestStepResultCreate.StatusEnum.FAILED)
                            .addAttachmentsItem(IntegrationUtils.getStacktrace(result.getError()));
                    StepStorage.stopStep();
                    break;
            }
        }
    }

    private void testRunFinished(TestRunFinished testRunFinished) {
        getQaseTestCaseListener().onTestCasesSetFinished();
    }

    private void testCaseStarted(TestCaseStarted event) {
        getQaseTestCaseListener().onTestCaseStarted();
    }

    private void testCaseFinished(TestCaseFinished event) {
        getQaseTestCaseListener().onTestCaseFinished(resultCreate -> setupResultItem(resultCreate, event));
    }

    private void setupResultItem(ResultCreate resultCreate, TestCaseFinished event) {
        List<PickleTag> pickleTags = event.testCase.getTags();
        List<String> tags = pickleTags.stream().map(PickleTag::getName).collect(Collectors.toList());
        Long caseId = CucumberUtils.getCaseId(tags);

        String caseTitle = null;
        if (caseId == null) {
            caseTitle = event.testCase.getName();
        }
        String status = convertStatus(event.result.getStatus());
        Optional<Throwable> optionalThrowable = Optional.ofNullable(event.result.getError());
        String comment = optionalThrowable
                .flatMap(throwable -> Optional.of(throwable.toString())).orElse(null);
        Boolean isDefect = optionalThrowable
                .flatMap(throwable -> Optional.of(throwable instanceof AssertionError))
                .orElse(false);
        String stacktrace = optionalThrowable
                .flatMap(throwable -> Optional.of(getStacktrace(throwable))).orElse(null);
        LinkedList<TestStepResultCreate> steps = StepStorage.stopSteps();
        resultCreate
                ._case(caseTitle == null ? null : new ResultCreateCase().title(caseTitle))
                .caseId(caseId)
                .status(status)
                .comment(comment)
                .stacktrace(stacktrace)
                .steps(steps.isEmpty() ? null : steps)
                .defect(isDefect);
    }

    private String convertStatus(Result.Type status) {
        switch (status) {
            case FAILED:
                return "failed";
            case PASSED:
                return "passed";
            case PENDING:
            case SKIPPED:
            case AMBIGUOUS:
            case UNDEFINED:
            default:
                return "skipped";
        }
    }

    private static QaseTestCaseListener createQaseListener() {
        return Cucumber3Module.getInjector().getInstance(QaseTestCaseListener.class);
    }
}
