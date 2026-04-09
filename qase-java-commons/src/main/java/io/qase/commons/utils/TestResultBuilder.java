package io.qase.commons.utils;

import io.qase.commons.annotation.*;
import io.qase.commons.cucumber.CucumberTestCaseAdapter;
import io.qase.commons.models.domain.Relations;
import io.qase.commons.models.domain.SuiteData;
import io.qase.commons.models.domain.TestResult;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Utility that builds a {@link TestResult} for the three supported test sources:
 * <ol>
 *   <li>{@link #fromMethod(Method, Map, long)} — JUnit5 and TestNG (annotation access via reflection)</li>
 *   <li>{@link #fromAnnotationReader(AnnotationReader, String, String, Map, long)} — JUnit4 (annotation access via {@link AnnotationReader} abstraction)</li>
 *   <li>{@link #fromCucumber(CucumberTestCaseAdapter, Map, long)} — Cucumber (tag-based annotation extraction)</li>
 * </ol>
 *
 * <p>This class carries zero JUnit4/JUnit5/TestNG/Cucumber imports — framework-specific
 * wiring stays in the respective reporter modules.
 *
 * <p>Java 8 compatible; no {@code var}, no {@code stream().toList()}.
 */
public final class TestResultBuilder {

    private TestResultBuilder() throws IllegalAccessException {
        throw new IllegalAccessException("Utils class");
    }

    // -------------------------------------------------------------------------
    // Public entry points
    // -------------------------------------------------------------------------

    /**
     * Builds a {@link TestResult} for a Java method-based test (JUnit5, TestNG).
     *
     * <p>Callers supply the already-resolved {@code startTime} — JUnit5 passes
     * {@code Instant.now().toEpochMilli()}, TestNG passes {@code result.getStartMillis()}.
     *
     * @param method     the test method being executed
     * @param parameters resolved parameter map (empty if none)
     * @param startTime  epoch-milliseconds when the test started
     * @return populated {@link TestResult} ready for {@code CasesStorage.startCase()}
     */
    public static TestResult fromMethod(Method method, Map<String, String> parameters, long startTime) {
        TestResult result = new TestResult();

        if (IntegrationUtils.getQaseIgnore(method)) {
            result.ignore = true;
            return result;
        }

        List<Long> caseIds = IntegrationUtils.getCaseIds(method);
        String caseTitle = IntegrationUtils.getCaseTitle(method);
        Map<String, String> fields = IntegrationUtils.getQaseFields(method);
        List<String> tags = IntegrationUtils.getQaseTags(method);
        String suite = IntegrationUtils.getQaseSuite(method);

        result.execution.startTime = startTime;
        result.execution.thread = Thread.currentThread().getName();
        result.testopsIds = caseIds;
        result.title = caseTitle;
        result.params = parameters;
        result.fields = fields;
        result.tags = tags;
        result.relations = buildRelations(suite, method.getDeclaringClass().getName());
        result.signature = IntegrationUtils.generateSignature(method, caseIds, parameters);

        return result;
    }

    /**
     * Builds a {@link TestResult} for a JUnit4 test using an {@link AnnotationReader}.
     *
     * <p>The reader abstraction keeps commons free of JUnit4 imports; the concrete
     * {@code DescriptionAnnotationReader} lives in {@code qase-junit4-reporter}.
     *
     * <p>Annotation resolution order: {@code @QaseId} → {@code @QaseIds} → {@code @CaseId} (legacy).
     *
     * <p>Signature generation follows the JUnit4 convention: class name with {@code "."} replaced
     * by {@code ":"}, and the last segment of the method name (split on {@code "."}).
     *
     * @param reader      annotation reader backed by a JUnit4 {@code Description} (or any source)
     * @param className   fully-qualified class name for suite fallback and signature
     * @param methodName  test method name for signature generation
     * @param parameters  resolved parameter map ({@code null} is safe — JUnit4 has no params)
     * @param startTime   epoch-milliseconds when the test started
     * @return populated {@link TestResult} ready for {@code CasesStorage.startCase()}
     */
    public static TestResult fromAnnotationReader(AnnotationReader reader, String className,
                                                   String methodName, Map<String, String> parameters,
                                                   long startTime) {
        TestResult result = new TestResult();

        if (reader.getAnnotation(QaseIgnore.class) != null) {
            result.ignore = true;
            return result;
        }

        // Case IDs: @QaseId → @QaseIds → @CaseId (legacy)
        List<Long> caseIds;
        QaseId qaseId = reader.getAnnotation(QaseId.class);
        if (qaseId != null) {
            caseIds = Collections.singletonList(qaseId.value());
        } else {
            QaseIds qaseIds = reader.getAnnotation(QaseIds.class);
            if (qaseIds != null) {
                caseIds = Arrays.stream(qaseIds.value()).boxed().collect(Collectors.<Long>toList());
            } else {
                CaseId caseIdAnnotation = reader.getAnnotation(CaseId.class);
                caseIds = caseIdAnnotation != null ? Collections.singletonList(caseIdAnnotation.value()) : null;
            }
        }

        // Case title: @QaseTitle → @CaseTitle (legacy) → methodName
        String caseTitle;
        QaseTitle qaseTitleAnnotation = reader.getAnnotation(QaseTitle.class);
        if (qaseTitleAnnotation != null) {
            caseTitle = qaseTitleAnnotation.value();
        } else {
            CaseTitle caseTitleAnnotation = reader.getAnnotation(CaseTitle.class);
            caseTitle = caseTitleAnnotation != null ? caseTitleAnnotation.value() : methodName;
        }

        // Fields: @QaseFields
        Map<String, String> fields = new java.util.HashMap<>();
        QaseFields qaseFields = reader.getAnnotation(QaseFields.class);
        if (qaseFields != null) {
            for (io.qase.commons.models.annotation.Field field : qaseFields.value()) {
                fields.put(field.name(), field.value());
            }
        }

        // Tags: @QaseTags
        List<String> tags = new ArrayList<>();
        QaseTags qaseTags = reader.getAnnotation(QaseTags.class);
        if (qaseTags != null) {
            Collections.addAll(tags, qaseTags.value());
        }

        // Suite: @QaseSuite → className fallback
        QaseSuite qaseSuiteAnnotation = reader.getAnnotation(QaseSuite.class);
        String suite = qaseSuiteAnnotation != null ? qaseSuiteAnnotation.value() : null;

        // JUnit4 signature: className.toLowerCase().replace(".", ":"), last part of methodName
        String classNameForSig = className.toLowerCase().replace(".", ":");
        String[] methodParts = methodName.split("\\.");
        String methodNameForSig = methodParts[methodParts.length - 1].toLowerCase();

        ArrayList<String> suites = new ArrayList<>();
        suites.add(classNameForSig);
        suites.add(methodNameForSig);

        String signature = StringUtils.generateSignature(
                caseIds != null ? new ArrayList<>(caseIds) : new ArrayList<Long>(),
                suites,
                parameters);

        result.execution.startTime = startTime;
        result.execution.thread = Thread.currentThread().getName();
        result.testopsIds = caseIds;
        result.title = caseTitle;
        result.params = parameters;
        result.fields = fields;
        result.tags = tags;
        result.relations = buildRelations(suite, className);
        result.signature = signature;

        return result;
    }

    /**
     * Builds a {@link TestResult} for a Cucumber scenario via the adapter.
     *
     * <p>Tag-based annotation extraction is performed by {@link CucumberUtils}. If no
     * {@code @QaseTitle} tag is present, the title falls back to {@code adapter.getName()}.
     * Suite hierarchy is built from {@code @QaseSuite} tag (split on literal {@code "\\t"})
     * or from the adapter's URI path parts.
     *
     * <p>Signature is generated from URI path parts + title + parameters using
     * {@link StringUtils#generateSignature(List, List, Map)}.
     *
     * @param adapter     normalized Cucumber TestCase adapter
     * @param parameters  merged parameters (adapter.getParameters() + ScenarioStorage)
     * @param startTime   epoch-milliseconds when the scenario started
     * @return populated {@link TestResult} ready for {@code CasesStorage.startCase()}
     */
    public static TestResult fromCucumber(CucumberTestCaseAdapter adapter,
                                           Map<String, String> parameters,
                                           long startTime) {
        TestResult result = new TestResult();
        List<String> tags = adapter.getTags();

        if (CucumberUtils.getCaseIgnore(tags)) {
            result.ignore = true;
            return result;
        }

        List<Long> caseIds = CucumberUtils.getCaseIds(tags);
        String caseTitle = CucumberUtils.getCaseTitle(tags);
        if (caseTitle == null) {
            caseTitle = adapter.getName();
        }
        Map<String, String> fields = CucumberUtils.getCaseFields(tags);
        String suite = CucumberUtils.getCaseSuite(tags);
        List<String> caseTags = CucumberUtils.getCaseTags(tags);

        result.execution.startTime = startTime;
        result.execution.thread = Thread.currentThread().getName();
        result.testopsIds = caseIds;
        result.title = caseTitle;
        result.params = parameters != null ? parameters : new java.util.HashMap<String, String>();
        result.fields = fields;
        result.tags = caseTags;
        result.relations = buildCucumberRelations(suite, adapter.getUriPathParts());

        ArrayList<String> suites = new ArrayList<>(adapter.getUriPathParts());
        suites.add(result.title);
        result.signature = StringUtils.generateSignature(
                caseIds != null ? new ArrayList<Long>(caseIds) : new ArrayList<Long>(),
                suites,
                result.params);

        return result;
    }

    // -------------------------------------------------------------------------
    // Private helpers
    // -------------------------------------------------------------------------

    /**
     * Builds a {@link Relations} object from a suite string (tab-separated) or a fallback class name.
     *
     * <p>Used by {@code fromMethod()} and {@code fromAnnotationReader()}.
     * The suite separator is a real tab character ({@code \t}).
     *
     * @param suite             tab-separated suite path, or {@code null}
     * @param fallbackClassName fully-qualified class name used when suite is {@code null}
     * @return populated {@link Relations}
     */
    private static Relations buildRelations(String suite, String fallbackClassName) {
        Relations relations = new Relations();
        if (suite != null) {
            String[] parts = suite.split("\t");
            for (String part : parts) {
                SuiteData data = new SuiteData();
                data.title = part;
                relations.suite.data.add(data);
            }
        } else {
            SuiteData data = new SuiteData();
            data.title = fallbackClassName;
            relations.suite.data.add(data);
        }
        return relations;
    }

    /**
     * Builds a {@link Relations} object for Cucumber scenarios.
     *
     * <p>The suite separator for Cucumber is the literal two-character sequence {@code \\t}
     * (backslash + t), NOT a tab character — Cucumber reporters encode suite hierarchy as
     * the string {@code "\\t"} in tag values.
     *
     * @param suite        literal-backslash-t-separated suite path, or {@code null}
     * @param uriPathParts URI path segments from the feature file, used when suite is {@code null}
     * @return populated {@link Relations}
     */
    private static Relations buildCucumberRelations(String suite, List<String> uriPathParts) {
        Relations relations = new Relations();
        if (suite != null) {
            // Cucumber uses literal "\\t" (two chars) not tab
            String[] parts = suite.split("\\\\t");
            for (String part : parts) {
                if (part == null || part.trim().isEmpty()) {
                    continue;
                }
                SuiteData data = new SuiteData();
                data.title = part;
                relations.suite.data.add(data);
            }
        } else {
            for (String part : uriPathParts) {
                if (part == null || part.trim().isEmpty()) {
                    continue;
                }
                SuiteData data = new SuiteData();
                data.title = part;
                relations.suite.data.add(data);
            }
        }
        return relations;
    }
}
