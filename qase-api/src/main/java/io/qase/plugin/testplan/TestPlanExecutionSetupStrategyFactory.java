package io.qase.plugin.testplan;

import io.qase.api.services.TestPlanService;
import io.qase.plugin.QasePlugin;
import io.qase.plugin.codeparsing.ClassParser;
import io.qase.plugin.codeparsing.MethodFilter;
import io.qase.plugin.testplan.impl.CucumberStrategy;
import io.qase.plugin.testplan.impl.DefaultStrategy;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static io.qase.configuration.QaseModule.getInjector;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestPlanExecutionSetupStrategyFactory {

    private static final String CUCUMBER_GROUP_ID = "io.cucumber";

    private static final String CUCUMBER_ARTIFACT_ID = "cucumber-java";

    public static TestPlanExecutionSetupStrategy createStrategy(QasePlugin qasePlugin) {
        if (isCucumberInTestClassPath(qasePlugin)) {
            return new CucumberStrategy(
                qasePlugin,
                getInjector().getInstance(TestPlanService.class)
            );
        } else { // for Junit4, Junit5, and TestNG
            return new DefaultStrategy(
                qasePlugin,
                getInjector().getInstance(MethodFilter.class),
                getInjector().getInstance(ClassParser.class),
                getInjector().getInstance(TestPlanService.class)
            );
        }
    }

    private static boolean isCucumberInTestClassPath(QasePlugin qasePlugin) {
        return qasePlugin.isDependencyInTestClasspath(CUCUMBER_GROUP_ID, CUCUMBER_ARTIFACT_ID);
    }
}
