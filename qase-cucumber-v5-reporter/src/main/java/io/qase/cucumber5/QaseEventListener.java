package io.qase.cucumber5;

import gherkin.ast.*;
import io.cucumber.plugin.event.*;
import io.qase.commons.CasesStorage;
import io.qase.commons.cucumber.AbstractCucumberEventListener;
import io.qase.commons.models.domain.StepResultStatus;
import io.qase.commons.models.domain.TestResult;
import io.qase.commons.models.domain.TestResultStatus;
import io.qase.commons.utils.CucumberUtils;
import io.qase.commons.utils.IntegrationUtils;
import io.cucumber.plugin.ConcurrentEventListener;

import java.util.*;
import java.util.stream.IntStream;

public class QaseEventListener extends AbstractCucumberEventListener
        implements ConcurrentEventListener {

    private final ScenarioStorage scenarioStorage;

    public QaseEventListener() {
        super("qase-cucumber-v5",
                QaseEventListener.class.getPackage().getImplementationVersion(),
                IntegrationUtils.detectFrameworkVersion(io.cucumber.plugin.event.TestCase.class));
        this.scenarioStorage = new ScenarioStorage();
    }

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestSourceRead.class, this::scenarioRead);
        publisher.registerHandlerFor(TestCaseStarted.class, this::testCaseStarted);
        publisher.registerHandlerFor(TestCaseFinished.class, this::testCaseFinished);
        publisher.registerHandlerFor(TestRunStarted.class, e -> onTestRunStarted());
        publisher.registerHandlerFor(TestRunFinished.class, e -> onTestRunFinished());
        publisher.registerHandlerFor(TestStepStarted.class, e -> onTestStepStarted(e.getTestStep() instanceof PickleStepTestStep));
        publisher.registerHandlerFor(TestStepFinished.class, this::testStepFinished);
    }

    private void scenarioRead(TestSourceRead event) {
        this.scenarioStorage.addScenarioEvent(event.getUri(), event);
    }

    private void testStepFinished(TestStepFinished testStepFinished) {
        if (testStepFinished.getTestStep() instanceof PickleStepTestStep) {
            PickleStepTestStep step = (PickleStepTestStep) testStepFinished.getTestStep();
            // v5: uses getKeyWord() (capital W) - differs from v6/v7
            String stepText = step.getStep().getKeyWord() + " " + step.getStep().getText();
            StepResultStatus stepStatus = convertStepStatus(testStepFinished.getResult().getStatus());
            onTestStepFinished(true, stepText, stepStatus);
        }
    }

    private void testCaseStarted(TestCaseStarted event) {
        List<String> tags = event.getTestCase().getTags();

        if (CucumberUtils.getCaseIgnore(tags)) {
            TestResult ignored = new TestResult();
            ignored.ignore = true;
            CasesStorage.startCase(ignored);
            return;
        }

        final ScenarioDefinition scenarioDefinition = ScenarioStorage.getScenarioDefinition(
                scenarioStorage.getCucumberNode(event.getTestCase().getUri(), event.getTestCase().getLine()));

        Map<String, String> parameters = new HashMap<>();
        if (scenarioDefinition instanceof ScenarioOutline) {
            parameters = getExamplesAsParameters((ScenarioOutline) scenarioDefinition, event.getTestCase());
        }

        V5TestCaseAdapter adapter = new V5TestCaseAdapter(event.getTestCase());
        onTestCaseStarted(adapter, parameters);
    }

    private void testCaseFinished(TestCaseFinished event) {
        Throwable cause = event.getResult().getError();
        TestResultStatus status = convertStatus(event.getResult().getStatus());
        onTestCaseFinished(status, cause);
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

    private Map<String, String> getExamplesAsParameters(
            final ScenarioOutline scenarioOutline, final TestCase localCurrentTestCase) {
        final Optional<Examples> examplesBlock = scenarioOutline.getExamples().stream()
                .filter(example -> example.getTableBody().stream()
                        .anyMatch(row -> row.getLocation().getLine() == localCurrentTestCase.getLine()))
                .findFirst();

        if (examplesBlock.isPresent()) {
            final TableRow row = examplesBlock.get().getTableBody().stream()
                    .filter(example -> example.getLocation().getLine() == localCurrentTestCase.getLine())
                    .findFirst().get();
            final Map<String, String> parameters = new HashMap<>();

            IntStream.range(0, examplesBlock.get().getTableHeader().getCells().size()).forEach(index -> {
                final String name = examplesBlock.get().getTableHeader().getCells().get(index).getValue();
                final String value = row.getCells().get(index).getValue();
                parameters.put(name, value);
            });

            return parameters;
        } else {
            return Collections.emptyMap();
        }
    }
}
