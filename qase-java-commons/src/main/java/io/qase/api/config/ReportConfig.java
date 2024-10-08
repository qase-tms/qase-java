package io.qase.api.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReportConfig {
    private static final Logger logger = LoggerFactory.getLogger(ReportConfig.class);

    public Driver driver = Driver.LOCAL;
    public ConnectionConfig connection;

    public ReportConfig() {
        this.connection = new ConnectionConfig();
    }

    public void setDriver(String driver) {
        try {
            this.driver = Driver.valueOf(driver.toUpperCase());
        } catch (IllegalArgumentException e) {
            this.driver = Driver.LOCAL;
            logger.error("Unknown driver: {}. Supported drivers: {}", driver, Driver.values());
        }
    }

    public String getDriver() {
        return driver.toString();
    }
}

