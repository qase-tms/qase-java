package io.qase.plugin.testplan.strategy.defaults;

import io.qase.plugin.testplan.strategy.TestPlanExecutionSetupStrategy;
import io.qase.plugin.testplan.strategy.TestPlanExecutionSetupStrategyFactory;

/**
 * @param <T> build tool specific plugin type
 * */
public abstract class DependenciesAwareStrategyFactory<T> implements TestPlanExecutionSetupStrategyFactory<T> {

    private static final String CUCUMBER_GROUP_ID = "io.cucumber";

    private static final String CUCUMBER_ARTIFACT_ID = "cucumber-java";

    @Override
    public TestPlanExecutionSetupStrategy createStrategy(T plugin) {
        if (isDependencyInTestClasspath(plugin, CUCUMBER_GROUP_ID, CUCUMBER_ARTIFACT_ID)) {
            return createCucumberStrategy(plugin);
        } else { // for Junit4, Junit5, and TestNG
            return createDefaultStrategy(plugin);
        }
    }

    protected abstract boolean isDependencyInTestClasspath(T plugin, String groupId, String artifactId);

    protected abstract AbstractCucumberStrategy<T> createCucumberStrategy(T plugin);

    protected abstract AbstractDefaultStrategy<T> createDefaultStrategy(T plugin);
}
