package io.qase.commons.models.report;

/**
 * Host data model for run report according to root.yaml specification
 */
public class ReportHostData {
    /**
     * Name of the host data property
     */
    public String name;

    /**
     * Value of the host data property
     */
    public String value;

    public ReportHostData() {
    }

    public ReportHostData(String name, String value) {
        this.name = name;
        this.value = value;
    }
}

