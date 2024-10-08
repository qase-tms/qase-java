package io.qase.api.config;

import io.qase.api.utils.StringUtils;

public class RunConfig {
    public String title = "Automated run " + StringUtils.getDateTime();
    public String description;
    public boolean complete = true;
    public int id;
}
