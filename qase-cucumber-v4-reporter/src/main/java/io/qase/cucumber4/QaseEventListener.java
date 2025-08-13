package io.qase.cucumber4;

import cucumber.api.PickleStepTestStep;
import cucumber.api.Result;
import cucumber.api.TestCase;
import cucumber.api.event.*;
import gherkin.ast.Examples;
import gherkin.ast.ScenarioDefinition;
import gherkin.ast.ScenarioOutline;
import gherkin.ast.TableRow;
import gherkin.pickles.PickleTag;
import io.qase.commons.CasesStorage;
import io.qase.commons.StepStorage;
import io.qase.commons.utils.CucumberUtils;
import io.qase.commons.utils.StringUtils;
import io.qase.commons.models.domain.*;
import io.qase.commons.reporters.CoreReporterFactory;
import io.qase.commons.utils.ExceptionUtils;
import io.qase.commons.reporters.Reporter;
import okio.Path;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.qase.commons.utils.IntegrationUtils.getStacktrace;

public class QaseEventListener implements ConcurrentEventListener {

    private final Reporter qaseTestCaseListener;
    private final ScenarioStorage scenarioStorage;

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
        this.scenarioStorage.addScenarioEvent(event.uri, event);
    }

    private void testRunStarted(TestRunStarted testRunStarted) {
        this.qaseTestCaseListener.startTestRun();
    }

    private void testRunFinished(TestRunFinished testRunFinished) {
        this.qaseTestCaseListener.uploadResults();
        this.qaseTestCaseListener.completeTestRun();
    }

    private void testStepStarted(TestStepStarted testStepStarted) {
        if (testStepStarted.testStep instanceof PickleStepTestStep) {
            StepStorage.startStep();
        }
    }

    private void testStepFinished(TestStepFinished testStepFinished) {
        if (testStepFinished.testStep instanceof PickleStepTestStep) {
            PickleStepTestStep step = (PickleStepTestStep) testStepFinished.testStep;
            StepResult stepResult = StepStorage.getCurrentStep();
            stepResult.data.action = step.getStepText();
            stepResult.execution.status = this.convertStepStatus(testStepFinished.result.getStatus());
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
        List<String> tags = event.testCase
                .getTags()
                .stream()
                .map(PickleTag::getName)
                .collect(Collectors.toList());

        boolean ignore = CucumberUtils.getCaseIgnore(tags);
        if (ignore) {
            resultCreate.ignore = true;
            return resultCreate;
        }

        final ScenarioDefinition scenarioDefinition = ScenarioStorage.getScenarioDefinition(
                scenarioStorage.getCucumberNode(event.getTestCase().getUri(), event.getTestCase().getLine()));

        Map<String, String> parameters = new HashMap<>();

        if (scenarioDefinition instanceof ScenarioOutline) {
            parameters = getExamplesAsParameters((ScenarioOutline) scenarioDefinition, event.getTestCase());
        }

        List<Long> caseIds = CucumberUtils.getCaseIds(tags);
        Map<String, String> fields = CucumberUtils.getCaseFields(tags);

        String caseTitle = Optional.ofNullable(CucumberUtils.getCaseTitle(tags))
                .orElse(event.testCase.getName());

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
            String[] parts = event.testCase.getScenarioDesignation().split(":")[0].split(Path.DIRECTORY_SEPARATOR);
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

        Optional<Throwable> optionalThrowable = Optional.ofNullable(event.result.getError());
        String stacktrace = optionalThrowable
                .flatMap(throwable -> Optional.of(getStacktrace(throwable))).orElse(null);

        // Determine the correct status based on the exception type
        TestResultStatus status = convertStatus(event.result.getStatus());
        if (status == TestResultStatus.FAILED && optionalThrowable.isPresent()) {
            status = ExceptionUtils.isAssertionFailure(optionalThrowable.get()) ? 
                TestResultStatus.FAILED : TestResultStatus.INVALID;
        }

        resultCreate.execution.status = status;
        resultCreate.execution.endTime = Instant.now().toEpochMilli();
        resultCreate.execution.duration = (int) (resultCreate.execution.endTime - resultCreate.execution.startTime);
        resultCreate.execution.stacktrace = stacktrace;
        resultCreate.steps = StepStorage.stopSteps();

        optionalThrowable.ifPresent(throwable -> resultCreate.message = Optional.ofNullable(resultCreate.message)
                .map(msg -> msg + "\n\n" + throwable.toString())
                .orElse(throwable.toString()));

        return resultCreate;
    }

    private TestResultStatus convertStatus(Result.Type status) {
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

    private StepResultStatus convertStepStatus(Result.Type status) {
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
