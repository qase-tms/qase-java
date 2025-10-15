package io.qase.commons.models.report;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Run model for file report according to root.yaml specification
 */
public class Run {
    public String title;
    public RunExecution execution;
    public RunStats stats;
    public List<ShortReportResult> results;
    public List<String> threads;
    public List<String> suites;
    public String environment;
    
    @SerializedName("host_data")
    public List<ReportHostData> hostData;

    public Run(String title, long startTime, long endTime, String environment) {
        this.title = title;
        this.execution = new RunExecution(startTime, endTime);
        this.stats = new RunStats();
        this.results = new ArrayList<>();
        this.threads = new ArrayList<>();
        this.suites = new ArrayList<>();
        this.environment = environment;
        this.hostData = new ArrayList<>();
    }

    public void addResults(List<ReportResult> results) {
        for (ReportResult result : results) {
            ShortReportResult shortResult = new ShortReportResult();
            shortResult.id = result.id;
            shortResult.title = result.title;
            shortResult.status = result.execution.status;
            shortResult.duration = result.execution.duration != null ? result.execution.duration.intValue() : 0;
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

    /**
     * Adds host data to the report
     * @param hostData Map of host data properties
     */
    public void addHostData(Map<String, String> hostData) {
        if (hostData == null) {
            return;
        }
        
        for (Map.Entry<String, String> entry : hostData.entrySet()) {
            this.hostData.add(new ReportHostData(entry.getKey(), entry.getValue()));
        }
    }
}
