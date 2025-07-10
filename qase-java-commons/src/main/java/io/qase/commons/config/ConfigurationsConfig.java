package io.qase.commons.config;

import java.util.ArrayList;
import java.util.List;

/**
 * Configurations configuration model
 */
public class ConfigurationsConfig {
    private List<ConfigurationValue> values = new ArrayList<>();
    private boolean createIfNotExists = false;

    public ConfigurationsConfig() {
    }

    public List<ConfigurationValue> getValues() {
        return values;
    }

    public void setValues(List<ConfigurationValue> values) {
        this.values = values;
    }

    public boolean isCreateIfNotExists() {
        return createIfNotExists;
    }

    public void setCreateIfNotExists(boolean createIfNotExists) {
        this.createIfNotExists = createIfNotExists;
    }

    @Override
    public String toString() {
        return "ConfigurationsConfig{" +
                "values=" + values +
                ", createIfNotExists=" + createIfNotExists +
                '}';
    }
} 
