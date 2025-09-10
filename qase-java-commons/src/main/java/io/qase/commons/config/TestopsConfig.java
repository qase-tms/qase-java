package io.qase.commons.config;

import java.util.ArrayList;
import java.util.List;

public class TestopsConfig {
    public String project = "";
    public boolean defect = false;
    public ApiConfig api;
    public RunConfig run;
    public PlanConfig plan;
    public BatchConfig batch;
    public ConfigurationsConfig configurations;
    public List<String> statusFilter = new ArrayList<>();

    public TestopsConfig() {
        this.api = new ApiConfig();
        this.run = new RunConfig();
        this.plan = new PlanConfig();
        this.batch = new BatchConfig();
        this.configurations = new ConfigurationsConfig();
    }
}
