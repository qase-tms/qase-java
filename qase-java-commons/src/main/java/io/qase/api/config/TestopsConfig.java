package io.qase.api.config;

public class TestopsConfig {
    public String project;
    public boolean defect = false;
    public ApiConfig api;
    public RunConfig run;
    public PlanConfig plan;
    public BatchConfig batch;

    public TestopsConfig() {
        this.api = new ApiConfig();
        this.run = new RunConfig();
        this.plan = new PlanConfig();
        this.batch = new BatchConfig();
    }
}