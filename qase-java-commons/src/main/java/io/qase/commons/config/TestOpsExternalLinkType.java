package io.qase.commons.config;

/**
 * External link configuration for test runs
 */
public class TestOpsExternalLinkType {
    private ExternalLinkType type;
    private String link;

    public TestOpsExternalLinkType() {
    }

    public TestOpsExternalLinkType(ExternalLinkType type, String link) {
        this.type = type;
        this.link = link;
    }

    public ExternalLinkType getType() {
        return type;
    }

    public void setType(ExternalLinkType type) {
        this.type = type;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
