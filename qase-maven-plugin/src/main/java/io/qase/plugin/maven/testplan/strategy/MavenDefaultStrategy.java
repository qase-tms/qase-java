package io.qase.plugin.maven.testplan.strategy;

import io.qase.api.services.TestPlanService;
import io.qase.plugin.codeparsing.ClassParser;
import io.qase.plugin.codeparsing.MethodFilter;
import io.qase.plugin.maven.MavenQasePlugin;
import io.qase.plugin.testplan.strategy.defaults.AbstractDefaultStrategy;

import java.io.File;
import java.io.FileNotFoundException;

public class MavenDefaultStrategy extends AbstractDefaultStrategy<MavenQasePlugin> {

    public MavenDefaultStrategy(
        MavenQasePlugin plugin, MethodFilter methodFilter, ClassParser classParser, TestPlanService testPlanService
    ) {
        super(plugin, methodFilter, classParser, testPlanService);
    }

    @Override
    protected File getTestOutputDirectory(MavenQasePlugin plugin) throws FileNotFoundException {
        File testOutputDirectory = new File(plugin.getProject().getBuild().getTestOutputDirectory());
        if (!testOutputDirectory.isDirectory()) {
            throw new FileNotFoundException(
                "The test directory can not be reached." +
                    " Please, make sure the tests are compiled and the process has enough privileges to the directory."
            );
        }
        return testOutputDirectory;
    }

    @Override
    protected String getTestsFilteringExpression(MavenQasePlugin plugin) {
        return plugin.getTest();
    }

    @Override
    protected void setTestsFilteringExpression(MavenQasePlugin plugin, String testsFilteringExpression) {
        plugin.setTest(testsFilteringExpression);
    }
}
