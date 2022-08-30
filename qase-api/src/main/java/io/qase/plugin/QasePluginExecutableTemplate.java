package io.qase.plugin;

import io.qase.api.config.QaseConfig;
import io.qase.plugin.testplan.strategy.TestPlanExecutionSetupStrategyFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * @param <T> build tool specific plugin type
 * */
@Slf4j
public abstract class QasePluginExecutableTemplate<T> {

    public static final String QASE_TEMPLATE_NAME = "qaseTest";

    public void executeTemplate() throws Exception {
        if (mustRunPlan()) {
            setupPlanExecution();
        }
        delegateExecution();
    }

    protected abstract void delegateExecution() throws Exception;

    protected abstract TestPlanExecutionSetupStrategyFactory<T> createPlanExecutionSetupStrategyFactory();

    protected abstract T getPlugin();

    private void setupPlanExecution() {
        try {
            createPlanExecutionSetupStrategyFactory().createStrategy(getPlugin()).setupPlanExecution();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private boolean mustRunPlan() {
        return System.getProperty(QaseConfig.QASE_TEST_PLAN_ID_KEY) != null
            && System.getProperty(QaseConfig.RUN_ID_KEY) == null;
    }
}
