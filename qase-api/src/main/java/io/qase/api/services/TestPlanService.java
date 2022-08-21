package io.qase.api.services;

import io.qase.api.config.QaseConfig;
import io.qase.api.exceptions.QaseException;

import java.util.Collection;

public interface TestPlanService {

    Collection<Long> getPlanCasesIds(String projectKey, int planId) throws QaseException;

    default Collection<Long> getPlanCasesIds() throws QaseException {
        return getPlanCasesIds(
            System.getProperty(QaseConfig.PROJECT_CODE_KEY),
            Integer.parseInt(System.getProperty(QaseConfig.QASE_TEST_PLAN_ID_KEY))
        );
    }
}
