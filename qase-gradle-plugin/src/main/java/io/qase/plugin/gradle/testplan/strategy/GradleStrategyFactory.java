package io.qase.plugin.gradle.testplan.strategy;

import io.qase.api.services.TestPlanService;
import io.qase.plugin.codeparsing.ClassParser;
import io.qase.plugin.codeparsing.MethodFilter;
import io.qase.plugin.gradle.QaseTest;
import io.qase.plugin.testplan.strategy.defaults.AbstractCucumberStrategy;
import io.qase.plugin.testplan.strategy.defaults.AbstractDefaultStrategy;
import io.qase.plugin.testplan.strategy.defaults.DependenciesAwareStrategyFactory;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

import static io.qase.configuration.QaseModule.inject;

public class GradleStrategyFactory extends DependenciesAwareStrategyFactory<QaseTest> {

    private static final String TEST_COMPILE_CLASSPATH_CONFIGURATION_NAME = "testCompileClasspath";

    @Override
    protected boolean isDependencyInTestClasspath(QaseTest qaseTest, String groupId, String artifactId) {
        return Optional.of(qaseTest.getProject())
            .map(Project::getConfigurations)
            .map(configurations -> configurations.getByName(TEST_COMPILE_CLASSPATH_CONFIGURATION_NAME))
            .map(Configuration::getAllDependencies)
            .map(Collection::stream)
            .orElseGet(Stream::empty)
            .anyMatch(dependency ->
                groupId.equals(dependency.getGroup()) && artifactId.equals(dependency.getName())
            );
    }

    @Override
    protected AbstractCucumberStrategy<QaseTest> createCucumberStrategy(QaseTest qaseTest) {
        return new GradleCucumberStrategy(qaseTest, inject(TestPlanService.class));
    }

    @Override
    protected AbstractDefaultStrategy<QaseTest> createDefaultStrategy(QaseTest qaseTest) {
        return new GradleDefaultStrategy(
            qaseTest, inject(MethodFilter.class), inject(ClassParser.class), inject(TestPlanService.class)
        );
    }
}
