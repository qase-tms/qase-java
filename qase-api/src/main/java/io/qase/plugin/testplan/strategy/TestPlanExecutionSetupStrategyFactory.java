package io.qase.plugin.testplan.strategy;

/**
 * @param <T> build tool specific plugin type
 * */
public interface TestPlanExecutionSetupStrategyFactory<T> {

    TestPlanExecutionSetupStrategy createStrategy(T plugin);
}
