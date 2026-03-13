package io.qase.commons.cucumber;

import java.util.List;
import java.util.Map;

/**
 * Adapter interface for normalizing Cucumber TestCase across versions (v3-v7).
 *
 * <p>Implementations live in each versioned reporter module
 * ({@code qase-cucumber-v3-reporter} through {@code qase-cucumber-v7-reporter})
 * and wrap the framework-specific {@code TestCase} (or {@code Pickle}) type.
 * This interface itself carries zero Cucumber framework imports, keeping
 * {@code qase-java-commons} free of any Cucumber compile dependency.
 *
 * <p>The {@link #getParameters()} method returns only the direct TestCase
 * parameters from the adapter implementation (typically an empty map). Example-row
 * parameters sourced from {@code ScenarioStorage} are merged by the caller
 * ({@code startTestCase}) on top of the value returned here.
 */
public interface CucumberTestCaseAdapter {

    /**
     * Returns the list of tags attached to this scenario
     * (e.g., {@code @QaseId(42)}, {@code @QaseTitle("My title")}).
     *
     * @return non-null list of tag strings, may be empty
     */
    List<String> getTags();

    /**
     * Returns the human-readable name of the scenario or scenario outline.
     *
     * @return scenario name as defined in the feature file
     */
    String getName();

    /**
     * Returns the URI path segments of the feature file containing this scenario.
     * Used by the reporter to derive the suite hierarchy (path → suite path).
     *
     * @return non-null list of path segments split on {@code "/"}, may be empty
     */
    List<String> getUriPathParts();

    /**
     * Returns the direct test parameters from this TestCase.
     * Implementations return {@link java.util.Collections#emptyMap()} because
     * example-row parameters are merged by the caller from {@code ScenarioStorage}.
     *
     * @return non-null map of parameter name to value, may be empty
     */
    Map<String, String> getParameters();
}
