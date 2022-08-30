package io.qase.plugin.maven.testplan.strategy;

import io.qase.api.services.TestPlanService;
import io.qase.plugin.codeparsing.ClassParser;
import io.qase.plugin.codeparsing.MethodFilter;
import io.qase.plugin.maven.MavenQasePlugin;
import io.qase.plugin.testplan.strategy.defaults.AbstractCucumberStrategy;
import io.qase.plugin.testplan.strategy.defaults.AbstractDefaultStrategy;
import io.qase.plugin.testplan.strategy.defaults.DependenciesAwareStrategyFactory;

import static io.qase.configuration.QaseModule.inject;

public class MavenStrategyFactory extends DependenciesAwareStrategyFactory<MavenQasePlugin> {

    @Override
    protected boolean isDependencyInTestClasspath(MavenQasePlugin plugin, String groupId, String artifactId) {
        return plugin.getProject().getDependencies().stream().anyMatch(dependency ->
            groupId.equals(dependency.getGroupId()) && artifactId.equals(dependency.getArtifactId())
        );
    }

    @Override
    protected AbstractCucumberStrategy<MavenQasePlugin> createCucumberStrategy(MavenQasePlugin plugin) {
        return new MavenCucumberStrategy(plugin, inject(TestPlanService.class));
    }

    @Override
    protected AbstractDefaultStrategy<MavenQasePlugin> createDefaultStrategy(MavenQasePlugin plugin) {
        return new MavenDefaultStrategy(
            plugin,
            inject(MethodFilter.class),
            inject(ClassParser.class),
            inject(TestPlanService.class)
        );
    }
}
