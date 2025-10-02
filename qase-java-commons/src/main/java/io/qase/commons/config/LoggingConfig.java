package io.qase.commons.config;

public class LoggingConfig {
    public boolean console = true;
    public boolean file = false;

    public LoggingConfig() {
        // Default values are set above
    }

    public LoggingConfig(boolean console, boolean file) {
        this.console = console;
        this.file = file;
    }

    public boolean isConsole() {
        return console;
    }

    public void setConsole(boolean console) {
        this.console = console;
    }

    public boolean isFile() {
        return file;
    }

    public void setFile(boolean file) {
        this.file = file;
    }
}
