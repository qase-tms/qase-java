package io.qase.api.services.impl;

import io.qase.api.exceptions.QaseException;
import io.qase.api.services.TestPlanService;
import io.qase.client.api.PlansApi;
import io.qase.client.model.PlanDetailed;
import io.qase.client.model.PlanDetailedAllOfCases;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class TestPlanServiceImpl implements TestPlanService {

    private final PlansApi plansApi;

    @Override
    public Collection<Long> getPlanCasesIds(String projectKey, int planId) throws QaseException {
        return Optional.ofNullable(plansApi.getPlan(projectKey, planId).getResult())
            .map(PlanDetailed::getCases).orElse(Collections.emptyList()).stream()
            .map(PlanDetailedAllOfCases::getCaseId)
            .collect(Collectors.toList());
    }
}
