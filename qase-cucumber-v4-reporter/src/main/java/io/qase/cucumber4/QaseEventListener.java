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
import io.qase.commons.cucumber.AbstractCucumberEventListener;
import io.qase.commons.models.domain.StepResultStatus;
import io.qase.commons.models.domain.TestResult;
import io.qase.commons.models.domain.TestResultStatus;
import io.qase.commons.utils.CucumberUtils;
import io.qase.commons.utils.IntegrationUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class QaseEventListener extends AbstractCucumberEventListener
        implements ConcurrentEventListener {

    private final ScenarioStorage scenarioStorage;

    public QaseEventListener() {
        super("qase-cucumber-v4",
                QaseEventListener.class.getPackage().getImplementationVersion(),
                IntegrationUtils.detectFrameworkVersion(cucumber.api.TestCase.class));
        this.scenarioStorage = new ScenarioStorage();
    }

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestSourceRead.class, this::scenarioRead);
        publisher.registerHandlerFor(TestCaseStarted.class, this::testCaseStarted);
        publisher.registerHandlerFor(TestCaseFinished.class, this::testCaseFinished);
        publisher.registerHandlerFor(TestRunStarted.class, e -> onTestRunStarted());
        publisher.registerHandlerFor(TestRunFinished.class, e -> onTestRunFinished());
        // v4: public field access for testStep
        publisher.registerHandlerFor(TestStepStarted.class, e -> onTestStepStarted(e.testStep instanceof PickleStepTestStep));
        publisher.registerHandlerFor(TestStepFinished.class, this::testStepFinished);
    }

    private void scenarioRead(TestSourceRead event) {
        // v4: public field uri
        this.scenarioStorage.addScenarioEvent(event.uri, event);
    }

    private void testStepFinished(TestStepFinished testStepFinished) {
        // v4: public field testStep
        if (testStepFinished.testStep instanceof PickleStepTestStep) {
            PickleStepTestStep step = (PickleStepTestStep) testStepFinished.testStep;
            // v4: getStepText() method (not getStep().getText())
            String stepText = step.getStepText();
            // v4: public field result
            StepResultStatus stepStatus = convertStepStatus(testStepFinished.result.getStatus());
            onTestStepFinished(true, stepText, stepStatus);
        }
    }

    private void testCaseStarted(TestCaseStarted event) {
        // v4: tags via public field, List<PickleTag> must be mapped to List<String>
        List<String> tags = event.testCase
                .getTags()
                .stream()
                .map(PickleTag::getName)
                .collect(Collectors.toList());

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

        V4TestCaseAdapter adapter = new V4TestCaseAdapter(event.getTestCase());
        onTestCaseStarted(adapter, parameters);
    }

    private void testCaseFinished(TestCaseFinished event) {
        // v4: public field result
        Throwable cause = event.result.getError();
        TestResultStatus status = convertStatus(event.result.getStatus());
        onTestCaseFinished(status, cause);
    }

    private TestResultStatus convertStatus(Result.Type status) {
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
