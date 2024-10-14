package io.qase.commons.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QaseConfig {
    private static final Logger logger = LoggerFactory.getLogger(QaseConfig.class);

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
            logger.error("Unknown mode: {}. Supported modes: {}", mode, Mode.values());
        }
    }

    public void setFallback(String fallback) {
        try {
            this.fallback = Mode.valueOf(fallback.toUpperCase());
        } catch (IllegalArgumentException e) {
            this.fallback = Mode.OFF;
            logger.error("Unknown fallback mode: {}. Supported modes: {}", fallback, Mode.values());
        }
    }

    public String getMode() {
        return mode.toString();
    }

    public String getFallback() {
        return fallback.toString();
    }
}

