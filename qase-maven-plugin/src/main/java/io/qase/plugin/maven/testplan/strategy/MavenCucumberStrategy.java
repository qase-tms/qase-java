package io.qase.plugin.maven.testplan.strategy;

import io.qase.api.services.TestPlanService;
import io.qase.plugin.maven.MavenQasePlugin;
import io.qase.plugin.testplan.strategy.defaults.AbstractCucumberStrategy;

public class MavenCucumberStrategy extends AbstractCucumberStrategy<MavenQasePlugin> {

    private static final String CASE_ID_EXPRESSION_FORMAT = "caseId=%d";

    private static final String OR = " | ";

    public MavenCucumberStrategy(MavenQasePlugin plugin, TestPlanService testPlanService) {
        super(plugin, testPlanService);
    }

    @Override
    protected String getTagsFilteringExpression(MavenQasePlugin plugin) {
        return plugin.getGroups();
    }

    @Override
    protected void setTagsFilteringExpression(MavenQasePlugin plugin, String tagsFilteringExpression) {
        plugin.setGroups(tagsFilteringExpression);
    }

    @Override
    protected FilteringExpressionSyntax createFilteringExpressionSyntax() {
        return FilteringExpressionSyntax.builder()
            .or(OR)
            .caseIdExpressionFormat(CASE_ID_EXPRESSION_FORMAT)
            .build();
    }
}
