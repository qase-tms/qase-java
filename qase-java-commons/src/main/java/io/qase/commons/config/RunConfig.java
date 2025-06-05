package io.qase.commons.config;

import io.qase.commons.utils.StringUtils;

public class RunConfig {
    public String title = "Automated run " + StringUtils.getDateTime();
    public String description = "";
    public boolean complete = true;
    public Integer id = 0;
    public String[] tags = {};
}
