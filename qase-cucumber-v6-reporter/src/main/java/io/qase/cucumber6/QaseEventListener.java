package io.qase.cucumber6;

import io.cucumber.messages.Messages.GherkinDocument.Feature.Scenario;
import io.cucumber.messages.Messages.GherkinDocument.Feature.Step;
import io.cucumber.messages.Messages.GherkinDocument.Feature.Scenario.Examples;
import io.cucumber.messages.Messages.GherkinDocument.Feature.TableRow;
import io.cucumber.messages.Messages.GherkinDocument.Feature.TableRow.TableCell;
import io.cucumber.plugin.event.*;
import io.qase.commons.CasesStorage;
import io.qase.commons.StepStorage;
import io.qase.commons.utils.CucumberUtils;
import io.qase.commons.utils.IntegrationUtils;
import io.qase.commons.utils.TestResultBuilder;
import io.qase.commons.utils.TestResultCompletion;
import io.qase.commons.models.domain.*;
import io.qase.commons.reporters.CoreReporterFactory;
import io.qase.commons.utils.ExceptionUtils;
import io.cucumber.plugin.ConcurrentEventListener;
import io.qase.commons.reporters.Reporter;

import java.net.URI;
import java.time.Instant;
import java.util.*;
import java.util.stream.IntStream;

public class QaseEventListener implements ConcurrentEventListener {

    private final Reporter qaseTestCaseListener;
    private final ScenarioStorage scenarioStorage;

    private final ThreadLocal<URI> currentFeatureFile = new InheritableThreadLocal<>();

    public QaseEventListener() {
        String reporterVersion = QaseEventListener.class.getPackage().getImplementationVersion();
        String frameworkVersion = getFrameworkVersion();
        this.qaseTestCaseListener = CoreReporterFactory.getInstance(
            "qase-cucumber-v6", reporterVersion, "cucumber", frameworkVersion);
        this.scenarioStorage = new ScenarioStorage();
    }

    private String getFrameworkVersion() {
        return IntegrationUtils.detectFrameworkVersion(io.cucumber.plugin.event.TestCase.class);
    }

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestSourceRead.class, this::scenarioRead);
        publisher.registerHandlerFor(TestCaseStarted.class, this::testCaseStarted);
        publisher.registerHandlerFor(TestCaseFinished.class, this::testCaseFinished);
        publisher.registerHandlerFor(TestRunStarted.class, this::testRunStarted);
        publisher.registerHandlerFor(TestRunFinished.class, this::testRunFinished);
        publisher.registerHandlerFor(TestStepStarted.class, this::testStepStarted);
        publisher.registerHandlerFor(TestStepFinished.class, this::testStepFinished);
    }

    private void scenarioRead(TestSourceRead event) {
        this.scenarioStorage.addScenarioEvent(event.getUri(), event);
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

            stepResult.data.action = step.getStep().getKeyword() + " " + step.getStep().getText();
            stepResult.execution.status = this.convertStepStatus(testStepFinished.getResult().getStatus());

            Step cucumberStep = getCucumberStep(currentFeatureFile.get(), step.getStep().getLine());

            if (cucumberStep != null && !cucumberStep.getDataTable().getRowsList().isEmpty()) {
                stepResult.data.inputData = CucumberUtils
                        .formatTable(convertTableRowsToLists(cucumberStep.getDataTable().getRowsList()));
            }

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
        List<String> tags = event.getTestCase().getTags();

        // Guard: check ignore BEFORE ScenarioStorage to avoid NPE on ignored tests
        if (CucumberUtils.getCaseIgnore(tags)) {
            TestResult ignored = new TestResult();
            ignored.ignore = true;
            return ignored;
        }

        currentFeatureFile.set(event.getTestCase().getUri());

        final Scenario scenarioDefinition = ScenarioStorage.getScenarioDefinition(
                scenarioStorage.getCucumberNode(
                        currentFeatureFile.get(),
                        event.getTestCase().getLocation().getLine()));

        Map<String, String> parameters = new HashMap<>();
        if (scenarioDefinition.getExamplesList() != null) {
            parameters = getExamplesAsParameters(scenarioDefinition, event.getTestCase());
        }

        V6TestCaseAdapter adapter = new V6TestCaseAdapter(event.getTestCase());
        return TestResultBuilder.fromCucumber(adapter, parameters, Instant.now().toEpochMilli());
    }

    private TestResult stopTestCase(TestCaseFinished event) {
        TestResult resultCreate = CasesStorage.getCurrentCase();
        CasesStorage.stopCase();
        if (resultCreate.ignore) {
            return null;
        }

        Optional<Throwable> optionalThrowable = Optional.ofNullable(event.getResult().getError());
        Throwable cause = optionalThrowable.orElse(null);

        TestResultStatus status = convertStatus(event.getResult().getStatus());
        if (status == TestResultStatus.FAILED && cause != null) {
            status = ExceptionUtils.isAssertionFailure(cause) ?
                    TestResultStatus.FAILED : TestResultStatus.INVALID;
        }

        return TestResultCompletion.complete(status, cause);
    }

    private TestResultStatus convertStatus(Status status) {
        switch (status) {
            case FAILED:
                // We need to check if the failure is due to assertion or other reason
                // This will be handled in stopTestCase method where we have access to the throwable
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
            final Scenario scenario, final TestCase localCurrentTestCase) {
        final Optional<Examples> maybeExample = scenario.getExamplesList().stream()
                .filter(example -> example.getTableBodyList().stream()
                        .anyMatch(row -> row.getLocation().getLine() == localCurrentTestCase.getLocation().getLine()))
                .findFirst();

        if (!maybeExample.isPresent()) {
            return Collections.emptyMap();
        }

        final Examples examples = maybeExample.get();

        final Optional<TableRow> maybeRow = examples.getTableBodyList().stream()
                .filter(example -> example.getLocation().getLine() == localCurrentTestCase.getLocation().getLine())
                .findFirst();

        if (!maybeRow.isPresent()) {
            return Collections.emptyMap();
        }

        final TableRow row = maybeRow.get();
        final Map<String, String> parameters = new HashMap<>();

        IntStream.range(0, examples.getTableHeader().getCellsList().size()).forEach(index -> {
            final String name = examples.getTableHeader().getCellsList().get(index).getValue();
            final String value = row.getCellsList().get(index).getValue();
            parameters.put(name, value);
        });

        return parameters;
    }

    private Step getCucumberStep(final URI uri, final int stepLine) {
        Scenario scenario = ScenarioStorage.getScenarioDefinition(scenarioStorage.getCucumberNode(uri, stepLine));

        if (scenario == null) {
            return null;
        }

        return scenario.getStepsList().stream().filter(s -> s.getLocation().getLine() == stepLine).findFirst()
                .orElse(null);
    }

    private List<List<String>> convertTableRowsToLists(List<TableRow> rows) {
        List<List<String>> result = new ArrayList<>();

        for (TableRow row : rows) {
            List<String> rowValues = new ArrayList<>();
            for (TableCell cell : row.getCellsList()) {
                rowValues.add(cell.getValue());
            }
            result.add(rowValues);
        }

        return result;
    }
}
