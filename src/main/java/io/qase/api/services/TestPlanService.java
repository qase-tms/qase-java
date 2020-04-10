package io.qase.api.services;

import io.qase.api.enums.Filters;
import io.qase.api.inner.RouteFilter;
import io.qase.api.models.v1.testplans.TestPlan;
import io.qase.api.models.v1.testplans.TestPlans;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public interface TestPlanService {
    TestPlans getAll(String projectCode, int limit, int offset, RouteFilter filter);

    TestPlans getAll(String projectCode);

    TestPlan get(String projectCode, long id);

    long create(String projectCode, String title, String description, Integer... cases);

    long create(String projectCode, String title, Integer... cases);

    long update(String projectCode, long testPlanId, String title, String description, Integer... cases);

    boolean delete(String projectCode, long testPlanId);

    default Filter filter() {
        return new Filter();
    }


    class Filter implements RouteFilter {
        private final Map<Filters, String> filters = new EnumMap<>(Filters.class);

        private Filter() {
        }

        @Override
        public Map<Filters, String> getFilters() {
            return Collections.unmodifiableMap(filters);
        }

        /**
         * String that will be used to search by name
         *
         * @param search
         * @return
         */
        public Filter search(String search) {
            filters.put(Filters.search, search);
            return this;
        }
    }
}
