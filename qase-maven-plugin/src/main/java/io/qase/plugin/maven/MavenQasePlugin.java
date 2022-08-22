package io.qase.plugin.maven;

import io.qase.api.config.QaseConfig;
import io.qase.plugin.QasePlugin;
import io.qase.plugin.testplan.TestPlanExecutionSetupStrategyFactory;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.surefire.SurefirePlugin;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.ResolutionScope;

@Mojo(
    name = MavenQasePlugin.QASE_SUREFIRE_PLUGIN_GOAL_NAME,
    defaultPhase = LifecyclePhase.TEST,
    requiresDependencyResolution = ResolutionScope.TEST
)
public class MavenQasePlugin extends SurefirePlugin implements QasePlugin {
    // TODO: implement support for multiple test frameworks being used
    public static final String QASE_SUREFIRE_PLUGIN_GOAL_NAME = "test";

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (mustRunPlan()) {
            setupPlanExecution();
        }
        super.execute();
    }

    private void setupPlanExecution() throws MojoExecutionException {
        try {
            TestPlanExecutionSetupStrategyFactory.createStrategy(this).setupPlanExecution();
        } catch (Exception e) {
            getLog().error(e);
            throw new MojoExecutionException(e.getMessage(), e);
        }
    }

    private boolean mustRunPlan() {
        return System.getProperty(QaseConfig.QASE_TEST_PLAN_ID_KEY) != null
            && System.getProperty(QaseConfig.RUN_ID_KEY) == null;
    }

    @Override
    public String getTestOutputDirectory() {
        return getProject().getBuild().getTestOutputDirectory();
    }

    @Override
    public boolean isDependencyInTestClasspath(String groupId, String artifactId) {
        return getProject().getModel().getDependencies().stream().anyMatch(dependency ->
            groupId.equals(dependency.getGroupId()) && artifactId.equals(dependency.getArtifactId())
        );
    }
}
