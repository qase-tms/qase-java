package io.qase.plugin.gradle.testplan.strategy;

import io.qase.api.services.TestPlanService;
import io.qase.plugin.gradle.QaseTest;
import io.qase.plugin.testplan.strategy.defaults.AbstractCucumberStrategy;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Properties;

import static io.qase.plugin.gradle.util.Utils.getFirstNonNullOrDefault;

@Slf4j
public class GradleCucumberStrategy extends AbstractCucumberStrategy<QaseTest> {

    private static final String EMPTY_STRING = "";

    private static final String CASE_ID_EXPRESSION_FORMAT = "@caseId=%d";

    private static final String OR = " or ";

    public GradleCucumberStrategy(QaseTest plugin, TestPlanService testPlanService) {
        super(plugin, testPlanService);
    }

    @Override
    protected String getTagsFilteringExpression(QaseTest plugin) {
        return getFirstNonNullOrDefault(
            Arrays.asList(
                () -> CucumberTagFilters.getFromBuildConfiguration(plugin),
                CucumberTagFilters::getFromEnvironment,
                CucumberTagFilters::tryGetFromCucumberPropertiesOrNull
            ),
            EMPTY_STRING
        );
    }

    @Override
    protected void setTagsFilteringExpression(QaseTest plugin, String tagsFilteringExpression) {
        CucumberTagFilters.setWithHighestPriority(plugin, tagsFilteringExpression);
    }

    @Override
    protected FilteringExpressionSyntax createFilteringExpressionSyntax() {
        return FilteringExpressionSyntax.builder()
            .or(OR)
            .caseIdExpressionFormat(CASE_ID_EXPRESSION_FORMAT)
            .build();
    }

    /**
     * This is just an internal utility class holding static getters/setter for filtering-by-tags expressions.
     * */
    private static class CucumberTagFilters {

        private static final String CUCUMBER_FILTER_TAGS_PROPERTY_NAME = "cucumber.filter.tags";

        private static final String CUCUMBER_FILTER_TAGS_ENV_VAR_NAME = "CUCUMBER_FILTER_TAGS";

        private static final String CUCUMBER_PROPERTIES_RESOURCE_NAME = "cucumber.properties";

        /**
         * Sets the overriding filtering-by-tags value.
         * The system property comes first before environmental variables and those from files.
         * */
        private static void setWithHighestPriority(QaseTest plugin, String tagsFilteringExpression) {
            plugin.getTestTask().systemProperty(CUCUMBER_FILTER_TAGS_PROPERTY_NAME, tagsFilteringExpression);
        }

        /**
         * This method just increases the code readability.
         *
         * @return {@link CucumberTagFilters#CUCUMBER_FILTER_TAGS_PROPERTY_NAME}
         *         {@link System#getProperty(String) system property value} or {@code null} if the value is not found.
         * */
        private static String getFromBuildConfiguration(QaseTest qaseTest) {
            return (String) qaseTest.getTestTask().getSystemProperties().get(CUCUMBER_FILTER_TAGS_PROPERTY_NAME);
        }

        /**
         * This method just increases the code readability.
         *
         * @return {@link CucumberTagFilters#CUCUMBER_FILTER_TAGS_ENV_VAR_NAME}
         *         {@link System#getenv(String) environment variable value} or {@code null} if the value is not found.
         * */
        public static String getFromEnvironment() {
            return System.getenv(CUCUMBER_FILTER_TAGS_ENV_VAR_NAME);
        }

        /**
         * @return {@link CucumberTagFilters#CUCUMBER_FILTER_TAGS_PROPERTY_NAME} property value from
         *         {@link CucumberTagFilters#CUCUMBER_PROPERTIES_RESOURCE_NAME the cucumber properties resource file}
         *         or {@code null} if the value is not found.
         *
         * @throws IOException if the resource file is not found, there is lack of access privileges to it,
         *                     or the property file is of invalid format.
         * */
        private static String getFromCucumberProperties() throws IOException {
            Properties cucumberProperties = new Properties();
            URL cucumberPropertiesUrl = CucumberTagFilters.class.getClassLoader()
                .getResource(CUCUMBER_PROPERTIES_RESOURCE_NAME);
            if (cucumberPropertiesUrl == null) {
                throw new IOException(
                    String.format("The resource '%s' can not be load", CUCUMBER_PROPERTIES_RESOURCE_NAME
                    ));
            }
            try (InputStream cucumberPropertiesInput = cucumberPropertiesUrl.openStream()) {
                cucumberProperties.load(cucumberPropertiesInput);
            }
            return cucumberProperties.getProperty(CUCUMBER_FILTER_TAGS_PROPERTY_NAME);
        }

        /**
         * This is just a safe wrapper over {@link #getFromCucumberProperties}.
         *
         * @return the same as the wrapped method or {@code null} if an exception occurs.
         * */
        private static String tryGetFromCucumberPropertiesOrNull() {
            try {
                return getFromCucumberProperties();
            } catch (Exception e) {
                // Trace is used intentionally in order to avoid log pollution.
                log.trace("An exception occurred during loading Cucumber properties", e);
                return null;
            }
        }
    }
}
