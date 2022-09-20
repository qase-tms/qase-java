package io.qase.plugin.gradle.testplan.strategy;

import io.qase.api.services.TestPlanService;
import io.qase.plugin.codeparsing.ClassParser;
import io.qase.plugin.codeparsing.MethodFilter;
import io.qase.plugin.gradle.QaseTest;
import io.qase.plugin.testplan.strategy.defaults.AbstractDefaultStrategy;
import lombok.extern.slf4j.Slf4j;
import org.gradle.api.tasks.testing.TestFilter;

import java.io.File;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class GradleDefaultStrategy extends AbstractDefaultStrategy<QaseTest> {

    public GradleDefaultStrategy(
        QaseTest plugin, MethodFilter methodFilter, ClassParser classParser, TestPlanService testPlanService
    ) {
        super(plugin, methodFilter, classParser, testPlanService);
    }

    @Override
    protected File getTestOutputDirectory(QaseTest qaseTest) {
        return qaseTest.getProject().getBuildDir();
    }

    @Override
    protected String getTestsFilteringExpression(QaseTest qaseTest) {
        return SplittingFilteringExpressionAdapter.getTestsFilteringExpression(qaseTest);
    }

    @Override
    protected void setTestsFilteringExpression(QaseTest qaseTest, String tagsFilteringExpression) {
        SplittingFilteringExpressionAdapter.setTestsFilteringExpression(qaseTest, tagsFilteringExpression);
    }

    private static class SplittingFilteringExpressionAdapter {

        private static final String CLASS_NAME_GROUP_NAME = "className";

        private static final String METHOD_NAME_GROUP_NAME = "methodName";

        private static final Pattern FULLY_QUALIFIED_METHOD_PATTERN =
            Pattern.compile("(?<" + CLASS_NAME_GROUP_NAME + ">.*)#(?<" + METHOD_NAME_GROUP_NAME + ">.*)");

        private static String getTestsFilteringExpression(QaseTest qaseTest) {
            return String.join(FILTERING_EXPRESSION_SEPARATOR, getTestFilterFor(qaseTest).getIncludePatterns());
        }

        private static void setTestsFilteringExpression(QaseTest qaseTest, String tagsFilteringExpression) {
            TestFilter testFilter = getTestFilterFor(qaseTest);
            Arrays.stream(tagsFilteringExpression.split(FILTERING_EXPRESSION_SEPARATOR)).forEach(
                fullyQualifiedMethodName -> testFilter.includeTest(
                    extractClassName(fullyQualifiedMethodName),
                    extractMethodName(fullyQualifiedMethodName)
                )
            );
        }

        private static String extractClassName(String fullMethodName) {
            return extractGroupFromFullMethodName(CLASS_NAME_GROUP_NAME, fullMethodName);
        }

        private static String extractMethodName(String fullMethodName) {
            return extractGroupFromFullMethodName(METHOD_NAME_GROUP_NAME, fullMethodName);
        }

        private static String extractGroupFromFullMethodName(String groupName, String fullMethodName) {
            Matcher matcher = FULLY_QUALIFIED_METHOD_PATTERN.matcher(fullMethodName);
            if (!matcher.matches()) {
                throw new IllegalArgumentException(
                    "The full method name format must match " + FULLY_QUALIFIED_METHOD_PATTERN.pattern()
                );
            }
            return matcher.group(groupName);
        }

        private static TestFilter getTestFilterFor(QaseTest qaseTest) {
            return qaseTest.getTestTask().getFilter();
        }
    }
}
