package io.qase.commons.config;


import io.qase.commons.logger.Logger;

public class ReportConfig {
    private static final Logger logger = Logger.getInstance();

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
            logger.error("Unknown driver: %s. Supported drivers: %s", driver, Driver.values());
        }
    }

    public String getDriver() {
        return driver.toString();
    }
}

