package io.qase.plugin.gradle;

import io.qase.plugin.QasePluginExecutableTemplate;
import io.qase.plugin.gradle.testplan.strategy.GradleStrategyFactory;
import io.qase.plugin.testplan.strategy.TestPlanExecutionSetupStrategyFactory;
import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.testing.Test;

import static org.gradle.api.plugins.JavaPlugin.TEST_TASK_NAME;

public class QaseTest extends DefaultTask {

    @TaskAction
    public void executeQaseTests() throws Exception {
        new GradleTemplate().executeTemplate();
    }

    @Internal
    public Test getTestTask() {
        return getProject().getTasks().withType(Test.class).getByName(TEST_TASK_NAME);
    }

    private class GradleTemplate extends QasePluginExecutableTemplate<QaseTest> {

        /**
         * There is no need to delegate the execution
         * because the task is configured to be finalized with the "test" task by Gradle engine.
         *
         * @see GradleQasePlugin#apply(Project)
         * */
        @Override
        protected void delegateExecution() {
            // Do nothing.
        }

        @Override
        protected TestPlanExecutionSetupStrategyFactory<QaseTest> createPlanExecutionSetupStrategyFactory() {
            return new GradleStrategyFactory();
        }

        @Override
        protected QaseTest getPlugin() {
            return QaseTest.this;
        }
    }
}
