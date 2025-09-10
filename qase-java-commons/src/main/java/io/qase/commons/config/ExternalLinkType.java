package io.qase.commons.config;

/**
 * External link types for test runs
 */
public enum ExternalLinkType {
    JIRA_CLOUD("jiraCloud"),
    JIRA_SERVER("jiraServer");

    private final String value;

    ExternalLinkType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ExternalLinkType fromValue(String value) {
        for (ExternalLinkType type : values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown external link type: " + value);
    }
}
