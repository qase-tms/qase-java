package io.qase.cucumber7;

import io.cucumber.messages.types.*;
import io.cucumber.messages.types.Step;
import io.cucumber.plugin.event.*;
import io.cucumber.plugin.event.TestCase;
import io.cucumber.plugin.event.TestCaseFinished;
import io.cucumber.plugin.event.TestCaseStarted;
import io.cucumber.plugin.event.TestRunFinished;
import io.cucumber.plugin.event.TestRunStarted;
import io.cucumber.plugin.event.TestStepFinished;
import io.cucumber.plugin.event.TestStepStarted;
import io.qase.commons.CasesStorage;
import io.qase.commons.StepStorage;
import io.qase.commons.cucumber.AbstractCucumberEventListener;
import io.qase.commons.models.domain.StepResult;
import io.qase.commons.models.domain.StepResultStatus;
import io.qase.commons.models.domain.TestResult;
import io.qase.commons.models.domain.TestResultStatus;
import io.qase.commons.utils.CucumberUtils;
import io.qase.commons.utils.IntegrationUtils;
import io.cucumber.plugin.ConcurrentEventListener;

import java.net.URI;
import java.util.*;
import java.util.stream.IntStream;

public class QaseEventListener extends AbstractCucumberEventListener
        implements ConcurrentEventListener {

    private final ScenarioStorage scenarioStorage;

    private final ThreadLocal<URI> currentFeatureFile = new InheritableThreadLocal<>();

    public QaseEventListener() {
        super("qase-cucumber-v7",
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
            String stepText = step.getStep().getKeyword() + " " + step.getStep().getText();
            StepResultStatus stepStatus = convertStepStatus(testStepFinished.getResult().getStatus());

            // v7-specific: process data table before calling base handler (base calls stopStep at end)
            StepResult stepResult = StepStorage.getCurrentStep();
            Step cucumberStep = getCucumberStep(currentFeatureFile.get(), step.getStep().getLine());
            if (cucumberStep != null && cucumberStep.getDataTable().isPresent()
                    && !cucumberStep.getDataTable().get().getRows().isEmpty()) {
                stepResult.data.inputData = CucumberUtils
                        .formatTable(convertTableRowsToLists(cucumberStep.getDataTable().get().getRows()));
            }

            onTestStepFinished(true, stepText, stepStatus);
        }
    }

    private void testCaseStarted(TestCaseStarted event) {
        List<String> tags = event.getTestCase().getTags();

        // Guard: check ignore BEFORE ScenarioStorage to avoid NPE on ignored tests
        if (CucumberUtils.getCaseIgnore(tags)) {
            TestResult ignored = new TestResult();
            ignored.ignore = true;
            CasesStorage.startCase(ignored);
            return;
        }

        currentFeatureFile.set(event.getTestCase().getUri());

        final Scenario scenarioDefinition = ScenarioStorage.getScenarioDefinition(
                scenarioStorage.getCucumberNode(
                        currentFeatureFile.get(),
                        event.getTestCase().getLocation().getLine()));

        Map<String, String> parameters = new HashMap<>();
        if (scenarioDefinition.getExamples() != null) {
            parameters = getExamplesAsParameters(scenarioDefinition, event.getTestCase());
        }

        V7TestCaseAdapter adapter = new V7TestCaseAdapter(event.getTestCase());
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
            final Scenario scenario, final TestCase localCurrentTestCase
    ) {
        final Optional<Examples> maybeExample =
                scenario.getExamples().stream()
                        .filter(example -> example.getTableBody().stream()
                                .anyMatch(row -> row.getLocation().getLine()
                                        == localCurrentTestCase.getLocation().getLine())
                        )
                        .findFirst();

        if (!maybeExample.isPresent()) {
            return Collections.emptyMap();
        }

        final Examples examples = maybeExample.get();

        final Optional<TableRow> maybeRow = examples.getTableBody().stream()
                .filter(example -> example.getLocation().getLine() == localCurrentTestCase.getLocation().getLine())
                .findFirst();

        if (!maybeRow.isPresent()) {
            return Collections.emptyMap();
        }

        final TableRow row = maybeRow.get();
        final Map<String, String> parameters = new HashMap<>();

        IntStream.range(0, examples.getTableHeader().get().getCells().size()).forEach
                (index -> {
                    final String name = examples.getTableHeader().get().getCells().get(index).getValue();
                    final String value = row.getCells().get(index).getValue();
                    parameters.put(name, value);
                });

        return parameters;
    }

    private Step getCucumberStep(final URI uri, final int stepLine) {
        Scenario scenario = ScenarioStorage.getScenarioDefinition(scenarioStorage.getCucumberNode(uri, stepLine));

        if (scenario == null) {
            return null;
        }

        return scenario.getSteps().stream()
                .filter(s -> s.getLocation().getLine().equals((long) stepLine))
                .findFirst().orElse(null);
    }

    private List<List<String>> convertTableRowsToLists(List<TableRow> rows) {
        List<List<String>> result = new ArrayList<>();

        for (TableRow row : rows) {
            List<String> rowValues = new ArrayList<>();
            for (TableCell cell : row.getCells()) {
                rowValues.add(cell.getValue());
            }
            result.add(rowValues);
        }

        return result;
    }
}
