package io.qase.api.services;

import io.qase.api.enums.Filters;
import io.qase.api.enums.RunStatus;
import io.qase.api.inner.RouteFilter;
import io.qase.api.models.v1.testruns.TestRun;
import io.qase.api.models.v1.testruns.TestRuns;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Collectors;

public interface TestRunService {
    TestRuns getAll(String projectCode, int limit, int offset, RouteFilter filter, boolean includeCases);

    TestRuns getAll(String projectCode, boolean includeCases);

    TestRun get(String projectCode, long id, boolean includeCases);

    long create(String projectCode, String title, Integer environmentId, String description, Integer... cases);

    long create(String projectCode, String title, Integer... cases);

    boolean delete(String projectCode, long testRunId);

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
         * @param statuses A list of status values. Possible values: active, complete, abort
         * @return
         */
        public Filter status(RunStatus... statuses) {
            filters.put(Filters.status, Arrays.stream(statuses).map(RunStatus::name)
                    .collect(Collectors.joining(",")));
            return this;
        }
    }
}
