package io.qase.plugin.maven.testplan;

import io.qase.api.services.TestPlanService;
import io.qase.plugin.maven.QaseSurefirePlugin;
import io.qase.plugin.maven.codeparsing.ClassParser;
import io.qase.plugin.maven.codeparsing.MethodFilter;
import io.qase.plugin.maven.testplan.impl.CucumberStrategy;
import io.qase.plugin.maven.testplan.impl.DefaultStrategy;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static io.qase.plugin.maven.configuration.QaseMavenModule.INJECTOR;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestPlanExecutionSetupStrategyFactory {

    private static final String CUCUMBER_GROUP_ID = "io.cucumber";

    private static final String CUCUMBER_ARTIFACT_ID = "cucumber-java";

    public static TestPlanExecutionSetupStrategy createStrategy(QaseSurefirePlugin qaseSurefirePlugin) {
        if (isCucumberInClassPath(qaseSurefirePlugin)) {
            return new CucumberStrategy(
                qaseSurefirePlugin,
                INJECTOR.getInstance(TestPlanService.class)
            );
        } else { // for Junit4, Junit5, and TestNG
            return new DefaultStrategy(
                qaseSurefirePlugin,
                INJECTOR.getInstance(MethodFilter.class),
                INJECTOR.getInstance(ClassParser.class),
                INJECTOR.getInstance(TestPlanService.class)
            );
        }
    }

    private static boolean isCucumberInClassPath(QaseSurefirePlugin qaseSurefirePlugin) {
        return qaseSurefirePlugin.getProject().getModel().getDependencies().stream().anyMatch(dependency ->
            CUCUMBER_GROUP_ID.equals(dependency.getGroupId()) && CUCUMBER_ARTIFACT_ID.equals(dependency.getArtifactId())
        );
    }
}
