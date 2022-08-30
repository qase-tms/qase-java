package io.qase.plugin.maven;

import io.qase.plugin.QasePluginExecutableTemplate;
import io.qase.plugin.maven.testplan.strategy.MavenStrategyFactory;
import io.qase.plugin.testplan.strategy.TestPlanExecutionSetupStrategyFactory;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.surefire.SurefirePlugin;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.ResolutionScope;

@Mojo(
    name = QasePluginExecutableTemplate.QASE_TEMPLATE_NAME,
    defaultPhase = LifecyclePhase.TEST,
    requiresDependencyResolution = ResolutionScope.TEST
)
public class MavenQasePlugin extends SurefirePlugin {
    // TODO: implement support for multiple test frameworks being used

    @Override
    public void execute() throws MojoExecutionException {
        try {
            new MavenTemplate().executeTemplate();
        } catch (Exception e) {
            getLog().error(e);
            throw new MojoExecutionException(e.getMessage(), e);
        }
    }

    private class MavenTemplate extends QasePluginExecutableTemplate<MavenQasePlugin> {

        @Override
        protected void delegateExecution() throws MojoExecutionException, MojoFailureException {
            MavenQasePlugin.super.execute();
        }

        @Override
        protected TestPlanExecutionSetupStrategyFactory<MavenQasePlugin> createPlanExecutionSetupStrategyFactory() {
            return new MavenStrategyFactory();
        }

        @Override
        protected MavenQasePlugin getPlugin() {
            return MavenQasePlugin.this;
        }
    }
}
