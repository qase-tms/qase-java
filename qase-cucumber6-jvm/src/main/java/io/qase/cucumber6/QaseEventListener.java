package io.qase.cucumber6;

import io.cucumber.gherkin.Gherkin;
import io.cucumber.messages.IdGenerator;
import io.cucumber.messages.Messages;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.*;
import io.qase.api.QaseClient;
import io.qase.api.StepStorage;
import io.qase.api.config.QaseConfig;
import io.qase.api.services.QaseTestCaseListener;
import io.qase.api.utils.CucumberUtils;
import io.qase.api.utils.IntegrationUtils;
import io.qase.client.model.ResultCreate;
import io.qase.client.model.ResultCreate.StatusEnum;
import io.qase.client.model.ResultCreateCase;
import io.qase.client.model.ResultCreateStepsInner;
import io.qase.cucumber6.guice.module.Cucumber6Module;
import lombok.AccessLevel;
import lombok.Getter;

import java.net.URI;
import java.util.*;
import java.util.stream.Stream;

import static io.cucumber.gherkin.Gherkin.makeSourceEnvelope;
import static io.qase.api.utils.CucumberUtils.getHash;
import static io.qase.api.utils.IntegrationUtils.getStacktrace;

public class QaseEventListener implements ConcurrentEventListener {

    private static final Map<Integer, Map<String, String>> EXAMPLES = new HashMap<>();
    private static final String REPORTER_NAME = "Cucumber 6-JVM";

    @Getter(lazy = true, value = AccessLevel.PRIVATE)
    private final QaseTestCaseListener qaseTestCaseListener = createQaseListener();
    private final Map<URI, String> sources = new HashMap<>();

    static {
        System.setProperty(QaseConfig.QASE_CLIENT_REPORTER_NAME_KEY, REPORTER_NAME);
    }

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        if (QaseClient.isEnabled()) {
            publisher.registerHandlerFor(TestSourceRead.class, this::testSourceRead);
            publisher.registerHandlerFor(TestCaseStarted.class, this::testCaseStarted);
            publisher.registerHandlerFor(TestCaseFinished.class, this::testCaseFinished);
            publisher.registerHandlerFor(TestRunFinished.class, this::testRunFinished);
            publisher.registerHandlerFor(TestStepFinished.class, this::testStepFinished);
            publisher.registerHandlerFor(TestStepStarted.class, this::testCaseStarted);
        }
    }

    private void testSourceRead(TestSourceRead testSourceRead) {
        sources.put(testSourceRead.getUri(), testSourceRead.getSource());
    }

    private void testCaseStarted(TestStepStarted testStepStarted) {
        if (testStepStarted.getTestStep() instanceof PickleStepTestStep) {
            StepStorage.startStep();
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
                            .status(ResultCreateStepsInner.StatusEnum.PASSED);
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
                            .status(ResultCreateStepsInner.StatusEnum.FAILED)
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
        URI uri = event.getTestCase().getUri();
        if (EXAMPLES.get(getHash(uri, (long) event.getTestCase().getLine())) == null && sources.containsKey(uri)) {

            Messages.Envelope envelope = makeSourceEnvelope(this.sources.get(uri), uri.toString());

            Stream<Messages.Envelope> envelopes = Gherkin.fromSources(
                    Collections.singletonList(envelope),
                    true,
                    true,
                    true,
                    new IdGenerator.UUID());

            envelopes
                    .filter(Messages.Envelope::hasGherkinDocument)
                    .map(Messages.Envelope::getGherkinDocument)
                    .findFirst()
                    .ifPresent(gherkinDocument -> parseExamples(uri, gherkinDocument));
        }
        getQaseTestCaseListener().onTestCaseStarted();
    }

    private void parseExamples(URI uri, Messages.GherkinDocument gherkinDocument) {
        Messages.GherkinDocument.Feature feature = gherkinDocument.getFeature();
        List<Messages.GherkinDocument.Feature.FeatureChild> childrenList = feature.getChildrenList();
        for (int i = 0; i < childrenList.size(); i++) {
            List<Messages.GherkinDocument.Feature.Scenario.Examples> examplesList = childrenList.get(i).getScenario().getExamplesList();
            for (int j = 0; j < examplesList.size(); j++) {
                List<String> headers = new ArrayList<>();
                Messages.GherkinDocument.Feature.TableRow tableHeader = examplesList.get(j).getTableHeader();
                tableHeader.getCellsList().forEach(h -> headers.add(h.getValue()));
                List<Messages.GherkinDocument.Feature.TableRow> tableBodyList = examplesList.get(j).getTableBodyList();
                for (int k = 0; k < tableBodyList.size(); k++) {
                    Messages.GherkinDocument.Feature.TableRow tableRow = tableBodyList.get(k);
                    List<Messages.GherkinDocument.Feature.TableRow.TableCell> cellsList = tableRow.getCellsList();
                    HashMap<String, String> example = new HashMap<>();
                    for (int l = 0; l < cellsList.size(); l++) {
                        String value = cellsList.get(l).getValue();
                        example.put(headers.get(l), value);
                    }
                    EXAMPLES.put(getHash(uri, (long) tableRow.getLocation().getLine()), example);
                }
            }
        }
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

        StatusEnum status = convertStatus(event.getResult().getStatus());
        Optional<Throwable> optionalThrowable = Optional.ofNullable(event.getResult().getError());
        String comment = optionalThrowable
                .flatMap(throwable -> Optional.of(throwable.toString())).orElse(null);
        Boolean isDefect = optionalThrowable
                .flatMap(throwable -> Optional.of(throwable instanceof AssertionError))
                .orElse(false);
        String stacktrace = optionalThrowable
                .flatMap(throwable -> Optional.of(getStacktrace(throwable))).orElse(null);
        LinkedList<ResultCreateStepsInner> steps = StepStorage.stopSteps();
        resultCreate
                ._case(caseTitle == null ? null : new ResultCreateCase().title(caseTitle))
                .caseId(caseId)
                .status(status)
                .comment(comment)
                .stacktrace(stacktrace)
                .steps(steps.isEmpty() ? null : steps)
                .defect(isDefect);
        Map<String, String> params = EXAMPLES.get(getHash(testCase.getUri(), (long) testCase.getLocation().getLine()));
        resultCreate.param(params);
    }

    private StatusEnum convertStatus(Status status) {
        switch (status) {
            case FAILED:
                return StatusEnum.FAILED;
            case PASSED:
                return StatusEnum.PASSED;
            case PENDING:
            case SKIPPED:
            case AMBIGUOUS:
            case UNDEFINED:
            case UNUSED:
            default:
                return StatusEnum.SKIPPED;
        }
    }

    private static QaseTestCaseListener createQaseListener() {
        return Cucumber6Module.getInjector().getInstance(QaseTestCaseListener.class);
    }
}
