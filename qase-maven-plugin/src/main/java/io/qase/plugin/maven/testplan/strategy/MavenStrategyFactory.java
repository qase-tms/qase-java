package io.qase.plugin.maven.testplan.strategy;

import com.google.inject.Injector;
import io.qase.api.services.TestPlanService;
import io.qase.guice.Injectors;
import io.qase.plugin.codeparsing.ClassParser;
import io.qase.plugin.codeparsing.MethodFilter;
import io.qase.plugin.maven.MavenQasePlugin;
import io.qase.plugin.testplan.strategy.defaults.AbstractCucumberStrategy;
import io.qase.plugin.testplan.strategy.defaults.AbstractDefaultStrategy;
import io.qase.plugin.testplan.strategy.defaults.DependenciesAwareStrategyFactory;

public class MavenStrategyFactory extends DependenciesAwareStrategyFactory<MavenQasePlugin> {

    private static final Injector injector = Injectors.createDefaultInjector();

    @Override
    protected boolean isDependencyInTestClasspath(MavenQasePlugin plugin, String groupId, String artifactId) {
        return plugin.getProject().getDependencies().stream().anyMatch(dependency ->
            groupId.equals(dependency.getGroupId()) && artifactId.equals(dependency.getArtifactId())
        );
    }

    @Override
    protected AbstractCucumberStrategy<MavenQasePlugin> createCucumberStrategy(MavenQasePlugin plugin) {
        return new MavenCucumberStrategy(plugin, injector.getInstance(TestPlanService.class));
    }

    @Override
    protected AbstractDefaultStrategy<MavenQasePlugin> createDefaultStrategy(MavenQasePlugin plugin) {
        return new MavenDefaultStrategy(
            plugin,
            injector.getInstance(MethodFilter.class),
            injector.getInstance(ClassParser.class),
            injector.getInstance(TestPlanService.class)
        );
    }
}
