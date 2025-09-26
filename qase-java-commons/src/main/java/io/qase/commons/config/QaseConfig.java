package io.qase.commons.config;

import com.google.gson.Gson;
import io.qase.commons.logger.Logger;
import io.qase.commons.utils.StatusMappingUtils;

import java.util.HashMap;
import java.util.Map;

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
    public Map<String, String> statusMapping;

    public QaseConfig() {
        this.mode = Mode.OFF;
        this.fallback = Mode.OFF;
        this.environment = "";
        this.rootSuite = "";
        this.debug = false;
        this.testops = new TestopsConfig();
        this.report = new ReportConfig();
        this.statusMapping = new HashMap<>();
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

    /**
     * Set status mapping from environment variable format.
     * Expected format: "invalid=failed,skipped=passed"
     * 
     * @param mappingString the mapping string
     */
    public void setStatusMapping(String mappingString) {
        this.statusMapping = StatusMappingUtils.parseStatusMapping(mappingString);
        if (!this.statusMapping.isEmpty()) {
            logger.info("Status mapping loaded: %s", this.statusMapping);
        }
    }

    /**
     * Get status mapping as a map.
     * 
     * @return map of status mappings
     */
    public Map<String, String> getStatusMapping() {
        return statusMapping;
    }

    /**
     * Set status mapping from a map.
     * 
     * @param mapping the status mapping map
     */
    public void setStatusMapping(Map<String, String> mapping) {
        if (StatusMappingUtils.validateStatusMapping(mapping)) {
            this.statusMapping = mapping != null ? new HashMap<>(mapping) : new HashMap<>();
        } else {
            logger.warn("Invalid status mapping provided, ignoring");
            this.statusMapping = new HashMap<>();
        }
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}

