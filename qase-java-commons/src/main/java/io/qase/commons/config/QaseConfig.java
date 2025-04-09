package io.qase.commons.config;

import com.google.gson.Gson;
import io.qase.commons.logger.Logger;

public class QaseConfig {
    private static final Logger logger = Logger.getInstance();

    public Mode mode;
    public Mode fallback;
    public String environment;
    public String rootSuite;
    public boolean debug;
    // public ExecutionPlan executionPlan;
    public TestopsConfig testops;
    public ReportConfig report;

    public QaseConfig() {
        this.mode = Mode.OFF;
        this.fallback = Mode.OFF;
        this.environment = "";
        this.rootSuite = "";
        this.debug = false;
        this.testops = new TestopsConfig();
        this.report = new ReportConfig();
    }

    public void setMode(String mode) {
        try {
            this.mode = Mode.valueOf(mode.toUpperCase());
        } catch (IllegalArgumentException e) {
            this.mode = Mode.OFF;
            logger.error("Unknown mode: %s. Supported modes: %s", mode, Mode.values());
        }
    }

    public void setFallback(String fallback) {
        try {
            this.fallback = Mode.valueOf(fallback.toUpperCase());
        } catch (IllegalArgumentException e) {
            this.fallback = Mode.OFF;
            logger.error("Unknown fallback mode: %s. Supported modes: %s", fallback, Mode.values());
        }
    }

    public String getMode() {
        return mode.toString();
    }

    public String getFallback() {
        return fallback.toString();
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}

