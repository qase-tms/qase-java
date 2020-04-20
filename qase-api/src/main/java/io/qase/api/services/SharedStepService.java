package io.qase.api.services;

import io.qase.api.enums.Filters;
import io.qase.api.inner.RouteFilter;
import io.qase.api.models.v1.shared_steps.SharedStep;
import io.qase.api.models.v1.shared_steps.SharedSteps;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public interface SharedStepService {
    SharedSteps getAll(String projectCode, int limit, int offset, RouteFilter filter);

    SharedSteps getAll(String projectCode, RouteFilter filter);

    SharedSteps getAll(String projectCode);

    SharedStep get(String projectCode, String hash);

    String create(String projectCode, String title, String action, String expectedResult);

    String create(String projectCode, String title, String action);

    boolean delete(String projectCode, String hash);

    String update(String projectCode, String hash, String title, String action, String expectedResult);

    String update(String projectCode, String hash, String title, String action);

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
