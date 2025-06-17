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
import io.qase.commons.utils.CucumberUtils;
import io.qase.commons.utils.StringUtils;
import io.qase.commons.models.domain.*;
import io.qase.commons.reporters.CoreReporterFactory;
import io.cucumber.plugin.ConcurrentEventListener;
import io.qase.commons.reporters.Reporter;

import java.net.URI;
import java.time.Instant;
import java.util.*;
import java.util.stream.IntStream;

import static io.qase.commons.utils.IntegrationUtils.getStacktrace;

public class QaseEventListener implements ConcurrentEventListener {

    private final Reporter qaseTestCaseListener;
    private final ScenarioStorage scenarioStorage;

    private final ThreadLocal<URI> currentFeatureFile = new InheritableThreadLocal<>();

    public QaseEventListener() {
        this.qaseTestCaseListener = CoreReporterFactory.getInstance();
        this.scenarioStorage = new ScenarioStorage();
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

            if (cucumberStep != null && cucumberStep.getDataTable().isPresent() && !cucumberStep.getDataTable().get().getRows().isEmpty()) {
                stepResult.data.inputData = CucumberUtils.formatTable(convertTableRowsToLists(cucumberStep.getDataTable().get().getRows()));
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
        TestResult resultCreate = new TestResult();
        List<String> tags = event.getTestCase().getTags();

        boolean ignore = CucumberUtils.getCaseIgnore(tags);
        if (ignore) {
            resultCreate.ignore = true;
            return resultCreate;
        }

        currentFeatureFile.set(event.getTestCase().getUri());

        final Scenario scenarioDefinition = ScenarioStorage.getScenarioDefinition(scenarioStorage.getCucumberNode(currentFeatureFile.get(), event.getTestCase().getLocation().getLine()));

        Map<String, String> parameters = new HashMap<>();

        if (scenarioDefinition.getExamples() != null) {
            parameters =
                    getExamplesAsParameters(scenarioDefinition, event.getTestCase());
        }

        List<Long> caseIds = CucumberUtils.getCaseIds(tags);
        Map<String, String> fields = CucumberUtils.getCaseFields(tags);

        String caseTitle = Optional.ofNullable(CucumberUtils.getCaseTitle(tags))
                .orElse(event.getTestCase().getName());

        String suite = CucumberUtils.getCaseSuite(tags);
        Relations relations = new Relations();
        if (suite != null) {
            String[] parts = suite.split("\\\\t");
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
        resultCreate.testopsIds = caseIds;
        resultCreate.execution.startTime = Instant.now().toEpochMilli();
        resultCreate.execution.thread = Thread.currentThread().getName();
        resultCreate.fields = fields;
        resultCreate.relations = relations;
        resultCreate.params = parameters;

        ArrayList<String> suites = new ArrayList<>();
        suites.addAll(Arrays.asList(event.getTestCase().getUri().toString().split("/")));
        suites.add(caseTitle);
        
        resultCreate.signature = StringUtils.generateSignature(
            caseIds != null ? new ArrayList<>(caseIds) : new ArrayList<>(),
            suites, 
            parameters);

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
        resultCreate.execution.endTime = Instant.now().toEpochMilli();
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

        return scenario.getSteps().stream().filter(s -> s.getLocation().getLine().equals((long) stepLine)).findFirst().orElse(null);
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
