package io.qase.cucumber5;

import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.*;
import io.qase.api.QaseClient;
import io.qase.api.StepStorage;
import io.qase.api.config.QaseConfig;
import io.qase.api.services.QaseTestCaseListener;
import io.qase.api.utils.CucumberUtils;
import io.qase.api.utils.IntegrationUtils;
import io.qase.client.model.ResultCreate;
import io.qase.client.model.ResultCreate.StatusEnum;
import io.qase.client.model.ResultCreateCase;
import io.qase.client.model.ResultCreateStepsInner;
import io.qase.cucumber5.guice.module.Cucumber5Module;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static io.qase.api.utils.IntegrationUtils.getStacktrace;

public class QaseEventListener implements ConcurrentEventListener {

    private static final String REPORTER_NAME = "Cucumber 5-JVM";

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
            publisher.registerHandlerFor(TestStepFinished.class, this::testStepFinished);
            publisher.registerHandlerFor(TestStepStarted.class, this::testCaseStarted);
        }
    }

    private void testCaseStarted(TestStepStarted testStepStarted) {
        if (testStepStarted.getTestStep() instanceof PickleStepTestStep) {
            StepStorage.startStep();
        }
    }

    private void testStepFinished(TestStepFinished testStepFinished) {
        if (testStepFinished.getTestStep() instanceof PickleStepTestStep) {
            PickleStepTestStep step = (PickleStepTestStep) testStepFinished.getTestStep();
            String stepText = step.getStep().getKeyWord() + step.getStep().getText();
            Result result = testStepFinished.getResult();
            switch (result.getStatus()) {
                case PASSED:
                    StepStorage.getCurrentStep()
                            .action(stepText)
                            .status(ResultCreateStepsInner.StatusEnum.PASSED);
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
                            .status(ResultCreateStepsInner.StatusEnum.FAILED)
                            .addAttachmentsItem(IntegrationUtils.getStacktrace(result.getError()));
                    StepStorage.stopStep();
                    break;
                case UNUSED:
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
        List<String> tags = event.getTestCase().getTags();
        Long caseId = CucumberUtils.getCaseId(tags);

        String caseTitle = null;
        if (caseId == null) {
            caseTitle = event.getTestCase().getName();
        }

        StatusEnum status = convertStatus(event.getResult().getStatus());
        Optional<Throwable> optionalThrowable = Optional.ofNullable(event.getResult().getError());
        String comment = optionalThrowable
                .flatMap(throwable -> Optional.of(throwable.toString())).orElse(null);
        Boolean isDefect = optionalThrowable
                .flatMap(throwable -> Optional.of(throwable instanceof AssertionError))
                .orElse(false);
        String stacktrace = optionalThrowable
                .flatMap(throwable -> Optional.of(getStacktrace(throwable))).orElse(null);
        LinkedList<ResultCreateStepsInner> steps = StepStorage.stopSteps();
        resultCreate
                ._case(caseTitle == null ? null : new ResultCreateCase().title(caseTitle))
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

    private static QaseTestCaseListener createQaseListener() {
        return Cucumber5Module.getInjector().getInstance(QaseTestCaseListener.class);
    }
}
