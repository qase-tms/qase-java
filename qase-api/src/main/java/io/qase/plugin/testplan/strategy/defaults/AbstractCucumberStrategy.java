package io.qase.plugin.testplan.strategy.defaults;

import io.qase.api.services.TestPlanService;
import io.qase.plugin.testplan.strategy.TestPlanExecutionSetupStrategy;
import io.qase.utils.StringUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
public abstract class AbstractCucumberStrategy<T> implements TestPlanExecutionSetupStrategy {

    private final T plugin;

    private final TestPlanService testPlanService;

    @Override
    public void setupPlanExecution() {
        Collection<Long> casesIds = testPlanService.tryGetPlanCasesIds();
        FilteringExpressionSyntax syntax = createFilteringExpressionSyntax();
        tryAddFilteringExpressionForCasesUsingSyntax(casesIds, syntax);
    }

    protected abstract String getTagsFilteringExpression(T plugin);

    protected abstract void setTagsFilteringExpression(T plugin, String tagsFilteringExpression);

    protected abstract FilteringExpressionSyntax createFilteringExpressionSyntax();

    private void tryAddFilteringExpressionForCasesUsingSyntax(
        Collection<Long> casesIds, FilteringExpressionSyntax syntax
    ) {
        String qaseFilteringExpression = casesIds.stream()
            .map(caseId -> String.format(syntax.getCaseIdExpressionFormat(), caseId))
            .collect(Collectors.joining(syntax.getOr(), syntax.getBegin(), syntax.getEnd()));
        String oldFilteringExpression = getTagsFilteringExpression(plugin);
        String effectiveFilteringExpression = Stream.of(oldFilteringExpression, qaseFilteringExpression)
            .filter(StringUtils::isNotBlank)
            .map(filteringExpression -> wrapExpressionUsingSyntax(filteringExpression, syntax))
            .collect(Collectors.joining(syntax.getOr()));
        setTagsFilteringExpression(plugin, effectiveFilteringExpression);
    }

    private String wrapExpressionUsingSyntax(String filteringExpression, FilteringExpressionSyntax syntax) {
        return syntax.getBegin() + filteringExpression + syntax.getEnd();
    }

    @Builder
    @Getter
    protected static class FilteringExpressionSyntax {

        private static final String DEFAULT_BEGIN = "(";

        private static final String DEFAULT_END = ")";

        private String or;

        @Builder.Default
        private String begin = DEFAULT_BEGIN;

        @Builder.Default
        private String end = DEFAULT_END;

        private String caseIdExpressionFormat;
    }
}
