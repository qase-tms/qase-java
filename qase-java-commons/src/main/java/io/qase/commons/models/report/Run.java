package io.qase.commons.models.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Run {
    public String title;
    public RunExecution execution;
    public RunStats stats;
    public List<ShortReportResult> results;
    public List<String> threads;
    public List<String> suites;
    public String environment;
    public Map<String, String> hostData;

    public Run(String title, long startTime, long endTime, String environment) {
        this.title = title;
        this.execution = new RunExecution(startTime, endTime);
        this.stats = new RunStats();
        this.results = new ArrayList<>();
        this.threads = new ArrayList<>();
        this.suites = new ArrayList<>();
        this.environment = environment;
    }

    public void addResults(List<ReportResult> results) {
        for (ReportResult result : results) {
            ShortReportResult shortResult = new ShortReportResult();
            shortResult.id = result.id;
            shortResult.title = result.title;
            shortResult.status = result.execution.status.toString().toLowerCase();
            shortResult.duration = result.execution.duration;
            shortResult.thread = result.execution.thread;

            this.execution.track(shortResult);
            this.stats.track(shortResult, result.muted);
            this.results.add(shortResult);

        }

        this.threads = this.results.stream()
                .map(result -> result.thread)
                .distinct()
                .collect(Collectors.toList());
    }

    public void addHostData() {
        this.hostData = hostData;
    }
}
