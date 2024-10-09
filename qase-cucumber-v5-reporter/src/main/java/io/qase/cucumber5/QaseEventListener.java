package io.qase.cucumber5;

import gherkin.AstBuilder;
import gherkin.Parser;
import gherkin.ast.*;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.*;
import io.qase.api.QaseClient;
import io.qase.api.StepStorage;
import io.qase.commons.config.QaseConfig;
import io.qase.api.services.QaseTestCaseListener;
import io.qase.api.utils.CucumberUtils;
import io.qase.api.utils.IntegrationUtils;
import io.qase.client.v1.models.ResultCreate;
import io.qase.client.v1.models.ResultCreateCase;
import io.qase.client.v1.models.TestStepResultCreate;
import io.qase.cucumber5.guice.module.Cucumber5Module;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static io.qase.api.utils.CucumberUtils.getHash;
import static io.qase.api.utils.IntegrationUtils.getStacktrace;

@Slf4j
public class QaseEventListener implements ConcurrentEventListener {

    private static final Map<Integer, Map<String, String>> EXAMPLES = new HashMap<>();
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

    private void testCaseStarted(TestStepStarted event) {
        if (EXAMPLES.get(getHash(event.getTestCase().getUri(), (long) event.getTestCase().getLine())) == null) {
            TestCase testCase = event.getTestCase();
            URI uri = testCase.getUri();
            Parser<GherkinDocument> gherkinParser = new Parser<>(new AstBuilder());
            try {
                URL resource = this.getClass().getClassLoader()
                        .getResource(uri.toString().replace("classpath:", ""));
                if (resource == null) {
                    return;
                }
                GherkinDocument gherkinDocument = gherkinParser.parse(new String(Files.readAllBytes(Paths.get(resource.toURI()))));
                parseExamples(uri, gherkinDocument);
            } catch (URISyntaxException | IOException e) {
                log.error(e.getMessage());
            }
        }
        if (event.getTestStep() instanceof PickleStepTestStep) {
            StepStorage.startStep();
        }
    }

    private void parseExamples(URI uri, GherkinDocument gherkinDocument) {
        Feature feature = gherkinDocument.getFeature();
        List<ScenarioDefinition> children = feature.getChildren();
        for (ScenarioDefinition child : children) {
            if (child instanceof ScenarioOutline) {
                ScenarioOutline scenario = (ScenarioOutline) child;
                for (Examples exampleItem : scenario.getExamples()) {
                    List<String> headers = new ArrayList<>();
                    exampleItem.getTableHeader().getCells().forEach(h -> headers.add(h.getValue()));
                    List<TableRow> tableBody = exampleItem.getTableBody();
                    for (TableRow tableRow : tableBody) {
                        List<TableCell> cells = tableRow.getCells();
                        HashMap<String, String> example = new HashMap<>();
                        for (int k = 0; k < cells.size(); k++) {
                            String value = cells.get(k).getValue();
                            example.put(headers.get(k), value);
                        }
                        EXAMPLES.put(getHash(uri, (long) tableRow.getLocation().getLine()), example);
                    }
                }
            }
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
                            .status(TestStepResultCreate.StatusEnum.PASSED);
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
                            .status(TestStepResultCreate.StatusEnum.FAILED)
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
        TestCase testCase = event.getTestCase();
        List<String> tags = testCase.getTags();
        Long caseId = CucumberUtils.getCaseId(tags);

        String caseTitle = null;
        if (caseId == null) {
            caseTitle = testCase.getName();
        }

        String status = convertStatus(event.getResult().getStatus());
        Optional<Throwable> optionalThrowable = Optional.ofNullable(event.getResult().getError());
        String comment = optionalThrowable
                .flatMap(throwable -> Optional.of(throwable.toString())).orElse(null);
        Boolean isDefect = optionalThrowable
                .flatMap(throwable -> Optional.of(throwable instanceof AssertionError))
                .orElse(false);
        String stacktrace = optionalThrowable
                .flatMap(throwable -> Optional.of(getStacktrace(throwable))).orElse(null);
        LinkedList<TestStepResultCreate> steps = StepStorage.stopSteps();
        resultCreate
                ._case(caseTitle == null ? null : new ResultCreateCase().title(caseTitle))
                .caseId(caseId)
                .status(status)
                .comment(comment)
                .stacktrace(stacktrace)
                .steps(steps.isEmpty() ? null : steps)
                .defect(isDefect);
        Map<String, String> params = EXAMPLES.get(getHash(testCase.getUri(), (long) testCase.getLine()));
        resultCreate.param(params);
    }

    private String convertStatus(Status status) {
        switch (status) {
            case FAILED:
                return "failed";
            case PASSED:
                return "passed";
            case PENDING:
            case SKIPPED:
            case AMBIGUOUS:
            case UNDEFINED:
            case UNUSED:
            default:
                return "skipped";
        }
    }

    private static QaseTestCaseListener createQaseListener() {
        return Cucumber5Module.getInjector().getInstance(QaseTestCaseListener.class);
    }
}
