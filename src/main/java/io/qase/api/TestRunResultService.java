package io.qase.api;

import io.qase.api.enums.Filters;
import io.qase.api.enums.RunResultStatus;
import io.qase.api.inner.RouteFilter;
import io.qase.api.models.v1.testrunresults.NewTestRunResults;
import io.qase.api.models.v1.testrunresults.Step;
import io.qase.api.models.v1.testrunresults.TestRunResult;
import io.qase.api.models.v1.testrunresults.TestRunResults;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.util.Collections.singletonMap;

public final class TestRunResultService {
    private final QaseApiClient qaseApiClient;

    public TestRunResultService(QaseApiClient qaseApiClient) {
        this.qaseApiClient = qaseApiClient;
    }

    public TestRunResults getAll(String projectCode, int limit, int offset, Filter filter) {
        return qaseApiClient.get(TestRunResults.class, "/result/{code}", singletonMap("code", projectCode), filter, limit, offset);
    }

    public TestRunResults getAll(String projectCode) {
        return this.getAll(projectCode, 100, 0, new Filter());
    }

    public TestRunResult get(String projectCode, String hash) {
        Map<String, Object> routeParams = new HashMap<>();
        routeParams.put("code", projectCode);
        routeParams.put("hash", hash);
        return qaseApiClient.get(TestRunResult.class, "/result/{code}/{hash}", routeParams);
    }

    public Filter filter() {
        return new Filter();
    }

    public String create(String projectCode, long runId, long caseId, RunResultStatus status, Duration timeSpent,
                         Long memberId, String comment, Boolean isDefect, Step... steps) {
        NewTestRunResults newTestRunResults = NewTestRunResults.builder()
                .caseId(caseId)
                .status(status)
                .time(timeSpent == null ? null : timeSpent.getSeconds())
                .memberId(memberId)
                .comment(comment)
                .defect(isDefect)
                .steps(Arrays.asList(steps))
                .build();
        Map<String, Object> routeParams = new HashMap<>();
        routeParams.put("code", projectCode);
        routeParams.put("run_id", runId);
        return qaseApiClient.post(TestRunResult.class, "/result/{code}/{run_id}", routeParams, newTestRunResults).getHash();
    }

    public String create(String projectCode, long runId, long caseId, RunResultStatus status) {
        return this.create(projectCode, runId, caseId, status, null, null, null, null, new Step[0]);
    }

    public String update(String projectCode, long runId, String hash, RunResultStatus status, Duration timeSpent,
                         Long memberId, String comment, Boolean isDefect, Step... steps) {
        NewTestRunResults newTestRunResults = NewTestRunResults.builder()
                .status(status)
                .time(timeSpent == null ? null : timeSpent.getSeconds())
                .memberId(memberId)
                .comment(comment)
                .defect(isDefect)
                .steps(steps == null ? null : Arrays.asList(steps))
                .build();
        Map<String, Object> routeParams = new HashMap<>();
        routeParams.put("code", projectCode);
        routeParams.put("run_id", runId);
        routeParams.put("hash", hash);
        return qaseApiClient.patch(TestRunResult.class, "/result/{code}/{run_id}/{hash}", routeParams, newTestRunResults)
                .getHash();
    }

    public String update(String projectCode, long runId, String hash, RunResultStatus status, Duration timeSpent) {
        return this.update(projectCode, runId, hash, status, timeSpent, null, null, null, new Step[0]);
    }

    public String update(String projectCode, long runId, String hash, RunResultStatus status) {
        return this.update(projectCode, runId, hash, status, null, null, null, null, new Step[0]);
    }

    public boolean delete(String projectCode, long runId, String hash) {
        Map<String, Object> routeParams = new HashMap<>();
        routeParams.put("code", projectCode);
        routeParams.put("run_id", runId);
        routeParams.put("hash", hash);
        return (boolean) qaseApiClient.delete("/result/{code}/{run_id}/{hash}", routeParams).get("status");
    }

    public static class Filter implements RouteFilter {
        private final Map<Filters, String> filters = new EnumMap<>(Filters.class);
        private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        private Filter() {
        }

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