package io.qase.cucumber5;

import gherkin.ast.*;
import io.cucumber.plugin.event.*;
import io.qase.commons.CasesStorage;
import io.qase.commons.StepStorage;
import io.qase.commons.utils.CucumberUtils;
import io.qase.commons.utils.StringUtils;
import io.qase.commons.models.domain.*;
import io.qase.commons.reporters.CoreReporterFactory;
import io.qase.commons.utils.ExceptionUtils;
import io.cucumber.plugin.ConcurrentEventListener;
import io.qase.commons.reporters.Reporter;

import java.time.Instant;
import java.util.*;
import java.util.stream.IntStream;

import static io.qase.commons.utils.IntegrationUtils.getStacktrace;

public class QaseEventListener implements ConcurrentEventListener {

    private final Reporter qaseTestCaseListener;
    private final ScenarioStorage scenarioStorage;

    public QaseEventListener() {
        String reporterVersion = QaseEventListener.class.getPackage().getImplementationVersion();
        String frameworkVersion = getFrameworkVersion();
        this.qaseTestCaseListener = CoreReporterFactory.getInstance(
            "qase-cucumber-v5", reporterVersion, "cucumber", frameworkVersion);
        this.scenarioStorage = new ScenarioStorage();
    }

    private String getFrameworkVersion() {
        try {
            Package pkg = io.cucumber.plugin.event.TestCase.class.getPackage();
            if (pkg != null) {
                String version = pkg.getImplementationVersion();
                if (version == null || version.isEmpty()) {
                    version = pkg.getSpecificationVersion();
                }
                return version != null ? version : "";
            }
        } catch (Exception e) {
            // Framework version not available
        }
        return "";
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
            stepResult.data.action = step.getStep().getKeyWord() + " " + step.getStep().getText();
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

        final ScenarioDefinition scenarioDefinition = ScenarioStorage.getScenarioDefinition(
                scenarioStorage.getCucumberNode(event.getTestCase().getUri(), event.getTestCase().getLine()));

        Map<String, String> parameters = new HashMap<>();

        if (scenarioDefinition instanceof ScenarioOutline) {
            parameters = getExamplesAsParameters((ScenarioOutline) scenarioDefinition, event.getTestCase());
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

        // Determine the correct status based on the exception type
        TestResultStatus status = convertStatus(event.getResult().getStatus());
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
