package io.qase.plugin.maven.testplan.impl;

import io.qase.api.annotation.CaseId;
import io.qase.api.exceptions.QaseException;
import io.qase.api.exceptions.UncheckedQaseException;
import io.qase.api.services.TestPlanService;
import io.qase.plugin.maven.QaseSurefirePlugin;
import io.qase.plugin.maven.codeparsing.ClassParser;
import io.qase.plugin.maven.codeparsing.MethodFilter;
import io.qase.plugin.maven.codeparsing.criteria.CriteriaUtils;
import io.qase.plugin.maven.codeparsing.criteria.MethodInfoCriteria;
import io.qase.plugin.maven.codeparsing.model.ClassInfo;
import io.qase.plugin.maven.testplan.TestPlanExecutionSetupStrategy;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class DefaultStrategy implements TestPlanExecutionSetupStrategy {

    private static final String CASE_ID_CANONICAL_NAME = CaseId.class.getCanonicalName();

    private static final String SET_TEST_METHOD_FORMAT = "%s#%s";

    private static final String CASE_ID_ATTRIBUTE_NAME = "value";

    private static final String TEST_PROPERTY_SEPARATOR = ",";

    private static final String CLASS_FILE_SUFFIX = ".class";

    private final QaseSurefirePlugin qaseSurefirePlugin;

    private final MethodFilter methodFilter;

    private final ClassParser classParser;

    private final TestPlanService testPlanService;

    @Override
    public void setupPlanExecution()  {
        Collection<Long> casesIds;
        try {
            casesIds = testPlanService.getPlanCasesIds();
        } catch (QaseException exception) {
            throw new UncheckedQaseException(exception);
        }
        File testOutputDirectory = new File(qaseSurefirePlugin.getProject().getBuild().getTestOutputDirectory());
        try (Stream<Path> testOutputDirectoryContentStream = Files.walk(testOutputDirectory.toPath())) {
            String qaseTestVariable = testOutputDirectoryContentStream
                .filter(this::isCompiledClass)
                .map(this::tryParseClassFileOrNull)
                .filter(Objects::nonNull)
                .map(classInfo -> createSetTestVariableEntryForCaseId(classInfo, casesIds))
                .collect(Collectors.joining(TEST_PROPERTY_SEPARATOR));
            String previousTestVariable = qaseSurefirePlugin.getTest();
            qaseSurefirePlugin.setTest(
                Stream.of(previousTestVariable, qaseTestVariable)
                    .filter(Objects::nonNull)
                    .collect(Collectors.joining(TEST_PROPERTY_SEPARATOR))
            );
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }
    }

    private boolean isCompiledClass(Path path) {
        return path.toFile().isFile() && path.toString().endsWith(CLASS_FILE_SUFFIX);
    }

    private ClassInfo tryParseClassFileOrNull(Path compiledClass) {
        try (InputStream inputStream = compiledClass.toUri().toURL().openStream()) {
            return classParser.parseCompiledClass(inputStream);
        } catch (IOException exception) {
            qaseSurefirePlugin.getLog().error(exception);
            return null;
        }
    }

    private String createSetTestVariableEntryFor(ClassInfo classInfo, MethodInfoCriteria methodInfoCriteria) {
        return methodFilter.filterMethodsByCriteria(classInfo, methodInfoCriteria)
            .parallelStream()
            .map(methodInfo ->
                String.format(SET_TEST_METHOD_FORMAT, classInfo.getCanonicalName(), methodInfo.getMethodName())
            )
            .collect(Collectors.joining(TEST_PROPERTY_SEPARATOR));
    }

    private String createSetTestVariableEntryForCaseId(ClassInfo classInfo, Collection<Long> caseIds) {
        return createSetTestVariableEntryFor(
            classInfo,
            CriteriaUtils.withAnnotationAttributeValueIn(CASE_ID_CANONICAL_NAME, CASE_ID_ATTRIBUTE_NAME, caseIds)
        );
    }
}
