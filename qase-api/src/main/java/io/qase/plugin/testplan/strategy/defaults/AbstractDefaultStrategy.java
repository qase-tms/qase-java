package io.qase.plugin.testplan.strategy.defaults;

import io.qase.api.annotation.CaseId;
import io.qase.api.services.TestPlanService;
import io.qase.plugin.codeparsing.ClassParser;
import io.qase.plugin.codeparsing.MethodFilter;
import io.qase.plugin.codeparsing.criteria.CriteriaUtils;
import io.qase.plugin.codeparsing.criteria.MethodInfoCriteria;
import io.qase.plugin.codeparsing.model.ClassInfo;
import io.qase.plugin.testplan.strategy.TestPlanExecutionSetupStrategy;
import io.qase.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.qase.utils.StringUtils.isBlank;

@RequiredArgsConstructor
@Slf4j
public abstract class AbstractDefaultStrategy<T> implements TestPlanExecutionSetupStrategy {

    private static final String CASE_ID_CANONICAL_NAME = CaseId.class.getCanonicalName();

    /**
     * @see <a href="https://maven.apache.org/surefire/maven-surefire-plugin/examples/single-test.html#multiple-formats-in-one"> Maven Surefire plugin expressions</a>
     * */
    private static final String NO_TEST_FILTERING_EXPRESSION = "!*";

    /**
     * @see <a href="https://maven.apache.org/surefire/maven-surefire-plugin/examples/single-test.html#running-a-set-of-methods-in-a-single-test-class"> Maven Surefire plugin expressions</a>
     * */
    private static final String SET_TEST_METHOD_FORMAT = "%s#%s";

    private static final String CASE_ID_ATTRIBUTE_NAME = "value";

    private static final String CLASS_FILE_SUFFIX = ".class";

    protected static final String FILTERING_EXPRESSION_SEPARATOR = ",";

    private final T plugin;

    private final MethodFilter methodFilter;

    private final ClassParser classParser;

    private final TestPlanService testPlanService;

    @Override
    public void setupPlanExecution() {
        Collection<Long> casesIds = testPlanService.tryGetPlanCasesIds();
        File testOutputDirectory = tryGetTestOutputDirectory();
        tryAddFilteringExpressionForCaseMethodsFromDirectory(testOutputDirectory, casesIds);
    }

    /**
     * @throws IllegalStateException when the directory can not be accessed
     *                               (e.g. because of the test classes are not compiled yet).
     * */
    protected abstract File getTestOutputDirectory(T plugin) throws FileNotFoundException;

    protected abstract String getTestsFilteringExpression(T plugin);

    protected abstract void setTestsFilteringExpression(T plugin, String testsFilteringExpression);

    private File tryGetTestOutputDirectory() {
        try {
            return getTestOutputDirectory(plugin);
        } catch (FileNotFoundException e) {
            log.error(e.getMessage(), e);
            throw new UncheckedIOException(e);
        }
    }

    private void tryAddFilteringExpressionForCaseMethodsFromDirectory(
        File testOutputDirectory, Collection<Long> casesIds
    ) {
        try (Stream<Path> testOutputDirectoryContentStream = Files.walk(testOutputDirectory.toPath())) {
            String qaseFilteringExpression = testOutputDirectoryContentStream
                .filter(this::isCompiledClass)
                .map(this::tryParseClassFileOrNull)
                .filter(Objects::nonNull)
                .map(classInfo -> createFilteringExpressionForCaseId(classInfo, casesIds))
                .collect(Collectors.joining(FILTERING_EXPRESSION_SEPARATOR));
            String oldFilteringExpression = getTestsFilteringExpression(plugin);
            setTestsFilteringExpression(
                plugin,
                Stream.of(oldFilteringExpression, qaseFilteringExpression)
                    .filter(StringUtils::isNotBlank)
                    .collect(Collectors.joining(FILTERING_EXPRESSION_SEPARATOR))
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
            log.error(exception.getMessage(), exception);
            return null;
        }
    }

    private String createSetTestVariableEntryFor(ClassInfo classInfo, MethodInfoCriteria methodInfoCriteria) {
        String testFilteringExpression = methodFilter.filterMethodsByCriteria(classInfo, methodInfoCriteria)
            .parallelStream()
            .map(methodInfo ->
                String.format(SET_TEST_METHOD_FORMAT, classInfo.getCanonicalName(), methodInfo.getMethodName())
            )
            .collect(Collectors.joining(FILTERING_EXPRESSION_SEPARATOR));
        return isBlank(testFilteringExpression) ? NO_TEST_FILTERING_EXPRESSION : testFilteringExpression;
    }

    private String createFilteringExpressionForCaseId(ClassInfo classInfo, Collection<Long> caseIds) {
        return createSetTestVariableEntryFor(
            classInfo,
            CriteriaUtils.withAnnotationAttributeValueIn(CASE_ID_CANONICAL_NAME, CASE_ID_ATTRIBUTE_NAME, caseIds)
        );
    }
}
