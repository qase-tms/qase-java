package io.qase.api.services;

import io.qase.api.enums.Filters;
import io.qase.api.enums.RunResultStatus;
import io.qase.api.inner.RouteFilter;
import io.qase.api.models.v1.testrunresults.Step;
import io.qase.api.models.v1.testrunresults.TestRunResult;
import io.qase.api.models.v1.testrunresults.TestRunResults;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public interface TestRunResultService {

    TestRunResults getAll(String projectCode, int limit, int offset, RouteFilter filter);

    TestRunResults getAll(String projectCode);

    TestRunResult get(String projectCode, String hash);

    String create(String projectCode, long runId, long caseId, RunResultStatus status, Duration timeSpent,
                  Long memberId, String comment, Boolean isDefect, Step... steps);

    String create(String projectCode, long runId, long caseId, RunResultStatus status);

    String update(String projectCode, long runId, String hash, RunResultStatus status, Duration timeSpent,
                  Long memberId, String comment, Boolean isDefect, Step... steps);

    String update(String projectCode, long runId, String hash, RunResultStatus status, Duration timeSpent);

    String update(String projectCode, long runId, String hash, RunResultStatus status);

    boolean delete(String projectCode, long runId, String hash);

    default Filter filter() {
        return new Filter();
    }

    class Filter implements RouteFilter {
        private final Map<Filters, String> filters = new EnumMap<>(Filters.class);
        private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        private Filter() {
        }

        @Override
        public Map<Filters, String> getFilters() {
            return Collections.unmodifiableMap(filters);
        }

        /**
         * @param status A single test run result status. Possible values: passed, failed, blocked
         * @return
         */
        public Filter status(RunResultStatus status) {
            filters.put(Filters.status, status.name());
            return this;
        }

        /**
         * @param id User member ID. Search result by team member.
         * @return
         */
        public Filter member(String id) {
            filters.put(Filters.member, id);
            return this;
        }

        /**
         * @param id Run ID. Search for all results in a specific run.
         * @return
         */
        public Filter run(String id) {
            filters.put(Filters.run, id);
            return this;
        }

        /**
         * @param id Test case ID. Search for all results for a specific test case.
         * @return
         */
        public Filter caseId(String id) {
            filters.put(Filters.case_id, id);
            return this;
        }

        /**
         * @param fromEndTime Will return all results created after provided datetime.
         * @return
         */
        public Filter fromEndTime(LocalDateTime fromEndTime) {
            filters.put(Filters.from_end_time, dateTimeFormatter.format(fromEndTime));
            return this;
        }

        /**
         * @param toEndTime Will return all results created before provided datetime.
         * @return
         */
        public Filter toEndTime(LocalDateTime toEndTime) {
            filters.put(Filters.to_end_time, dateTimeFormatter.format(toEndTime));
            return this;
        }

    }
}
