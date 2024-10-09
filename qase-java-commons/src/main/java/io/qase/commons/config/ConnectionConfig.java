package io.qase.commons.config;

public class ConnectionConfig {
    public Format format = Format.JSON;
    public String path = "./build/qase-report";

    public void setFormat(String format) {
        try {
            this.format = Format.valueOf(format.toUpperCase());
        } catch (IllegalArgumentException e) {
            this.format = Format.JSON;
        }
    }

    public String getFormat() {
        return format.toString();
    }
}

