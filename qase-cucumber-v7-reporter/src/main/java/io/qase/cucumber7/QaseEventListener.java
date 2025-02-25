package io.qase.cucumber7;

import io.cucumber.plugin.event.*;
import io.qase.commons.CasesStorage;
import io.qase.commons.StepStorage;
import io.qase.commons.utils.CucumberUtils;
import io.qase.commons.models.domain.*;
import io.qase.commons.reporters.CoreReporterFactory;
import io.cucumber.plugin.ConcurrentEventListener;
import io.qase.commons.reporters.Reporter;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static io.qase.commons.utils.IntegrationUtils.getStacktrace;

public class QaseEventListener implements ConcurrentEventListener {

    private final Reporter qaseTestCaseListener;

    public QaseEventListener() {
        this.qaseTestCaseListener = CoreReporterFactory.getInstance();
    }

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestCaseStarted.class, this::testCaseStarted);
        publisher.registerHandlerFor(TestCaseFinished.class, this::testCaseFinished);
        publisher.registerHandlerFor(TestRunStarted.class, this::testRunStarted);
        publisher.registerHandlerFor(TestRunFinished.class, this::testRunFinished);
        publisher.registerHandlerFor(TestStepStarted.class, this::testStepStarted);
        publisher.registerHandlerFor(TestStepFinished.class, this::testStepFinished);
    }

    private void testRunStarted(TestRunStarted testRunStarted) {
        this.qaseTestCaseListener.startTestRun();
    }

    private void testRunFinished(TestRunFinished testRunFinished) {
        this.qaseTestCaseListener.uploadResults();
        this.qaseTestCaseListener.completeTestRun();
    }


    private void testStepStarted(TestStepStarted testStepStarted) {
        if (testStepStarted.getTestStep() instanceof PickleStepTestStep) {
            StepStorage.startStep();
        }
    }

    private void testStepFinished(TestStepFinished testStepFinished) {
        if (testStepFinished.getTestStep() instanceof PickleStepTestStep) {
            PickleStepTestStep step = (PickleStepTestStep) testStepFinished.getTestStep();
            StepResult stepResult = StepStorage.getCurrentStep();
            stepResult.data.action = step.getStepText();
            stepResult.execution.status = this.convertStepStatus(testStepFinished.getResult().getStatus());
            StepStorage.stopStep();
        }
    }

    private void testCaseStarted(TestCaseStarted event) {
        TestResult resultCreate = startTestCase(event);
        CasesStorage.startCase(resultCreate);
    }

    private void testCaseFinished(TestCaseFinished event) {
        TestResult result = this.stopTestCase(event);

        if (result == null) {
            return;
        }

        this.qaseTestCaseListener.addResult(result);
    }

    private TestResult startTestCase(TestCaseStarted event) {
        TestResult resultCreate = new TestResult();
        List<String> tags = event.getTestCase().getTags();

        boolean ignore = CucumberUtils.getCaseIgnore(tags);
        if (ignore) {
            resultCreate.ignore = true;
            return resultCreate;
        }

        Long caseId = CucumberUtils.getCaseId(tags);
        Map<String, String> fields = CucumberUtils.getCaseFields(tags);

        String caseTitle = Optional.ofNullable(CucumberUtils.getCaseTitle(tags))
                .orElse(event.getTestCase().getName());

        String suite = CucumberUtils.getCaseSuite(tags);
        Relations relations = new Relations();
        if (suite != null) {
            String[] parts = suite.split("\t");
            for (String part : parts) {
                SuiteData data = new SuiteData();
                data.title = part;
                relations.suite.data.add(data);
            }
        } else {
            SuiteData className = new SuiteData();
            String[] parts = event.getTestCase().getUri().toString().split("/");
            className.title = parts[parts.length - 1];
            relations.suite.data.add(className);
        }

        resultCreate.title = caseTitle;
        resultCreate.testopsId = caseId;
        resultCreate.execution.startTime = System.currentTimeMillis();
        resultCreate.fields = fields;
        resultCreate.relations = relations;

        return resultCreate;
    }

    private TestResult stopTestCase(TestCaseFinished event) {
        TestResult resultCreate = CasesStorage.getCurrentCase();
        CasesStorage.stopCase();
        if (resultCreate.ignore) {
            return null;
        }

        Optional<Throwable> optionalThrowable = Optional.ofNullable(event.getResult().getError());
        String stacktrace = optionalThrowable
                .flatMap(throwable -> Optional.of(getStacktrace(throwable))).orElse(null);

        resultCreate.execution.status = convertStatus(event.getResult().getStatus());
        resultCreate.execution.endTime = System.currentTimeMillis();
        resultCreate.execution.duration = (int) (resultCreate.execution.endTime - resultCreate.execution.startTime);
        resultCreate.execution.stacktrace = stacktrace;
        resultCreate.steps = StepStorage.stopSteps();

        optionalThrowable.ifPresent(throwable ->
                resultCreate.message = Optional.ofNullable(resultCreate.message)
                        .map(msg -> msg + "\n\n" + throwable.toString())
                        .orElse(throwable.toString()));

        return resultCreate;
    }

    private TestResultStatus convertStatus(Status status) {
        switch (status) {
            case FAILED:
                return TestResultStatus.FAILED;
            case PASSED:
                return TestResultStatus.PASSED;
            case PENDING:
            case SKIPPED:
            case AMBIGUOUS:
            case UNDEFINED:
            default:
                return TestResultStatus.SKIPPED;
        }
    }

    private StepResultStatus convertStepStatus(Status status) {
        switch (status) {
            case PASSED:
                return StepResultStatus.PASSED;
            case FAILED:
                return StepResultStatus.FAILED;
            case PENDING:
            case UNDEFINED:
            case AMBIGUOUS:
            case SKIPPED:
            default:
                return StepResultStatus.BLOCKED;
        }
    }
}
