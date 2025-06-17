package io.qase.junit4;

import io.qase.commons.CasesStorage;
import io.qase.commons.StepStorage;
import io.qase.commons.annotation.*;
import io.qase.commons.models.annotation.Field;
import io.qase.commons.models.domain.*;
import io.qase.commons.reporters.CoreReporterFactory;
import io.qase.commons.reporters.Reporter;
import io.qase.commons.utils.StringUtils;

import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static io.qase.commons.utils.IntegrationUtils.*;

@RunListener.ThreadSafe
public class QaseListener extends RunListener {
    private final Set<String> methods = new HashSet<>();
    private final Reporter qaseTestCaseListener;

    public QaseListener() {
        this.qaseTestCaseListener = CoreReporterFactory.getInstance();
    }

    @Override
    public void testSuiteStarted(Description description) {
        this.qaseTestCaseListener.startTestRun();
    }

    @Override
    public void testSuiteFinished(Description result) {
        this.qaseTestCaseListener.uploadResults();
        this.qaseTestCaseListener.completeTestRun();
    }

    @Override
    public void testStarted(Description description) {
        TestResult resultCreate = startTestCase(description);
        CasesStorage.startCase(resultCreate);
    }

    @Override
    public void testFinished(Description description) {
        if (addIfNotPresent(description)) {
            this.stopTestCase(TestResultStatus.PASSED, null);
        }
    }

    @Override
    public void testFailure(Failure failure) {
        if (addIfNotPresent(failure.getDescription())) {
            this.stopTestCase(TestResultStatus.FAILED, failure.getException());
        }
    }

    @Override
    public void testAssumptionFailure(Failure failure) {
        if (addIfNotPresent(failure.getDescription())) {
            this.stopTestCase(TestResultStatus.FAILED, failure.getException());
        }
    }

    @Override
    public void testIgnored(Description description) {
        if (addIfNotPresent(description)) {
            this.stopTestCase(TestResultStatus.SKIPPED, null);
        }
    }

    private boolean addIfNotPresent(Description description) {
        String methodFullName = description.getClassName() + description.getMethodName();
        if (methods.contains(methodFullName)) {
            return false;
        }
        methods.add(methodFullName);
        return true;
    }

    private TestResult startTestCase(Description description) {
        TestResult resultCreate = new TestResult();
        boolean ignore = getQaseIgnore(description);

        if (ignore) {
            resultCreate.ignore = true;
            return resultCreate;
        }

        List<Long> caseIds = getCaseIds(description);
        String caseTitle = getCaseTitle(description);
        Map<String, String> fields = getQaseFields(description);
        String suite = getQaseSuite(description);
        Relations relations = new Relations();

        if (suite != null) {
            String[] parts = suite.split("\t");
            for (String part : parts) {
                SuiteData data = new SuiteData();
                data.title = part;
                relations.suite.data.add(data);
            }
        } else {
            SuiteData className = new SuiteData();
            className.title = description.getClassName();
            relations.suite.data.add(className);
        }

        resultCreate.execution.startTime = Instant.now().toEpochMilli();
        resultCreate.execution.thread = Thread.currentThread().getName();
        resultCreate.testopsIds = caseIds;
        resultCreate.title = caseTitle;
        resultCreate.fields = fields;
        resultCreate.relations = relations;
        resultCreate.signature = generateSignature(description, caseIds, null);

        return resultCreate;
    }

    private void stopTestCase(TestResultStatus status, Throwable error) {
        TestResult resultCreate = CasesStorage.getCurrentCase();

        if (resultCreate.ignore) {
            CasesStorage.stopCase();
            return;
        }

        Optional<Throwable> resultThrowable = Optional.ofNullable(error);
        String comment = resultThrowable.flatMap(throwable -> Optional.of(throwable.toString())).orElse(null);
        String stacktrace = resultThrowable.flatMap(throwable -> Optional.of(getStacktrace(throwable))).orElse(null);
        LinkedList<StepResult> steps = StepStorage.stopSteps();

        resultCreate.execution.status = status;
        resultCreate.execution.endTime = Instant.now().toEpochMilli();
        resultCreate.execution.duration = (int) (resultCreate.execution.endTime - resultCreate.execution.startTime);
        resultCreate.execution.stacktrace = stacktrace;
        resultCreate.steps = steps;

        if (comment != null) {
            if (resultCreate.message != null) {
                resultCreate.message += "\n\n" + comment;
            } else {
                resultCreate.message = comment;
            }
        }

        this.qaseTestCaseListener.addResult(resultCreate);
        CasesStorage.stopCase();
    }

    private List<Long> getCaseIds(Description description) {
        Long qaseId = getQaseId(description);
        if (qaseId != null) {
            return Collections.singletonList(qaseId);
        }
        List<Long> qaseIds = getQaseIds(description);
        if (qaseIds != null) {
            return qaseIds;
        }

        CaseId caseIdAnnotation = description.getAnnotation(CaseId.class);
        return caseIdAnnotation != null ? Collections.singletonList(caseIdAnnotation.value()) : null;
    }

    private String getCaseTitle(Description description) {
        String qaseTitle = getQaseTitle(description);
        if (qaseTitle != null) {
            return qaseTitle;
        }
        CaseTitle caseTitleAnnotation = description.getAnnotation(CaseTitle.class);
        return caseTitleAnnotation != null ? caseTitleAnnotation.value() : description.getMethodName();
    }

    private Long getQaseId(Description description) {
        QaseId caseIdAnnotation = description.getAnnotation(QaseId.class);
        return caseIdAnnotation != null ? caseIdAnnotation.value() : null;
    }

    private List<Long> getQaseIds(Description description) {
        QaseIds caseIdAnnotation = description.getAnnotation(QaseIds.class);
        return caseIdAnnotation != null ? Arrays.stream(caseIdAnnotation.value())
                .boxed()
                .collect(Collectors.toList()) : null;
    }

    private String getQaseTitle(Description description) {
        QaseTitle caseTitleAnnotation = description.getAnnotation(QaseTitle.class);
        return caseTitleAnnotation != null ? caseTitleAnnotation.value() : null;
    }

    private boolean getQaseIgnore(Description description) {
        return description.getAnnotation(QaseIgnore.class) != null;
    }

    private String getQaseSuite(Description description) {
        if (description.getAnnotation(QaseSuite.class) != null) {
            return description.getAnnotation(QaseSuite.class).value();
        }
        return null;
    }

    private Map<String, String> getQaseFields(Description description) {
        Map<String, String> fields = new HashMap<>();

        QaseFields annotation = description.getAnnotation(QaseFields.class);
        if (annotation != null) {
            for (Field field : annotation.value()) {
                fields.put(field.name(), field.value());
            }
        }

        return fields;
    }

    private String generateSignature(Description description, List<Long> qaseIds, Map<String, String> parameters) {
        String className = description.getClassName().toLowerCase().replace(".", ":");
        String[] parts = description.getMethodName().split("\\.");
        String methodName = parts[parts.length - 1].toLowerCase();

        return StringUtils.generateSignature(
                qaseIds != null ? new ArrayList<>(qaseIds) : new ArrayList<>(),
                new ArrayList<>(Arrays.asList(className, methodName)),
                parameters);
    }
}
