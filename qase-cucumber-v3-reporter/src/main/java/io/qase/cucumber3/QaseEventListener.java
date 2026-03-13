package io.qase.cucumber3;

import cucumber.api.PickleStepTestStep;
import cucumber.api.Result;
import cucumber.api.TestCase;
import cucumber.api.event.*;
import cucumber.api.formatter.Formatter;
import gherkin.ast.Examples;
import gherkin.ast.ScenarioDefinition;
import gherkin.ast.ScenarioOutline;
import gherkin.ast.TableRow;
import gherkin.pickles.PickleTag;
import io.qase.commons.CasesStorage;
import io.qase.commons.StepStorage;
import io.qase.commons.models.domain.*;
import io.qase.commons.reporters.CoreReporterFactory;
import io.qase.commons.reporters.Reporter;
import io.qase.commons.utils.ExceptionUtils;
import io.qase.commons.utils.IntegrationUtils;
import io.qase.commons.utils.TestResultBuilder;
import io.qase.commons.utils.TestResultCompletion;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class QaseEventListener implements Formatter {

    private final Reporter qaseTestCaseListener;
    private final ScenarioStorage scenarioStorage;

    public QaseEventListener() {
        String reporterVersion = QaseEventListener.class.getPackage().getImplementationVersion();
        String frameworkVersion = IntegrationUtils.detectFrameworkVersion(cucumber.api.TestCase.class);
        this.qaseTestCaseListener = CoreReporterFactory.getInstance(
            "qase-cucumber-v3", reporterVersion, "cucumber", frameworkVersion);
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
        final ScenarioDefinition scenarioDefinition = ScenarioStorage.getScenarioDefinition(
                scenarioStorage.getCucumberNode(event.testCase.getUri(), event.testCase.getLine()));

        Map<String, String> parameters = new HashMap<>();
        if (scenarioDefinition instanceof ScenarioOutline) {
            parameters = getExamplesAsParameters((ScenarioOutline) scenarioDefinition, event.testCase);
        }

        V3TestCaseAdapter adapter = new V3TestCaseAdapter(event.testCase);
        return TestResultBuilder.fromCucumber(adapter, parameters, Instant.now().toEpochMilli());
    }

    private TestResult stopTestCase(TestCaseFinished event) {
        TestResult resultCreate = CasesStorage.getCurrentCase();
        if (resultCreate == null || resultCreate.ignore) {
            CasesStorage.stopCase();
            return null;
        }

        Optional<Throwable> optionalThrowable = Optional.ofNullable(event.result.getError());
        Throwable cause = optionalThrowable.orElse(null);

        TestResultStatus status = convertStatus(event.result.getStatus());
        if (status == TestResultStatus.FAILED && cause != null) {
            status = ExceptionUtils.isAssertionFailure(cause) ?
                    TestResultStatus.FAILED : TestResultStatus.INVALID;
        }

        TestResult result = TestResultCompletion.complete(status, cause);
        CasesStorage.stopCase();
        return result;
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
