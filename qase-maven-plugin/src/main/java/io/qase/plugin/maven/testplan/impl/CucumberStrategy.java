package io.qase.plugin.maven.testplan.impl;

import io.qase.api.exceptions.QaseException;
import io.qase.api.exceptions.UncheckedQaseException;
import io.qase.api.services.TestPlanService;
import io.qase.plugin.maven.QaseSurefirePlugin;
import io.qase.plugin.maven.testplan.TestPlanExecutionSetupStrategy;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class CucumberStrategy implements TestPlanExecutionSetupStrategy {

    private final QaseSurefirePlugin qaseSurefirePlugin;

    private final TestPlanService testPlanService;

    private static final String CASE_ID_TEST_GROUP_FORMAT = "caseId=%d";

    private static final String OR = " | ";

    private static final String BEGIN = "(";

    private static final String END = ")";

    @Override
    public void setupPlanExecution() {
        Collection<Long> casesIds;
        try {
            casesIds = testPlanService.getPlanCasesIds();
        } catch (QaseException e) {
            throw new UncheckedQaseException(e);
        }
        String qaseGroups = casesIds.stream()
            .map(caseId -> String.format(CASE_ID_TEST_GROUP_FORMAT, caseId))
            .collect(Collectors.joining(OR, BEGIN, END));
        String oldGroups = Optional.ofNullable(qaseSurefirePlugin.getGroups())
            .map(groups -> BEGIN + groups + END).orElse(null);
        String effectiveGroups = Stream.of(oldGroups, qaseGroups)
            .filter(Objects::nonNull)
            .collect(Collectors.joining(OR, BEGIN, END));
        qaseSurefirePlugin.setGroups(effectiveGroups);
    }
}
