package io.qase.commons.config;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TestOpsExternalLinkTypeTest {

    @Test
    void testDefaultConstructor() {
        TestOpsExternalLinkType externalLink = new TestOpsExternalLinkType();
        assertNull(externalLink.getType());
        assertNull(externalLink.getLink());
    }

    @Test
    void testParameterizedConstructor() {
        TestOpsExternalLinkType externalLink = new TestOpsExternalLinkType(
                ExternalLinkType.JIRA_CLOUD, "https://example.com/issue-123");
        
        assertEquals(ExternalLinkType.JIRA_CLOUD, externalLink.getType());
        assertEquals("https://example.com/issue-123", externalLink.getLink());
    }

    @Test
    void testSettersAndGetters() {
        TestOpsExternalLinkType externalLink = new TestOpsExternalLinkType();
        
        externalLink.setType(ExternalLinkType.JIRA_SERVER);
        externalLink.setLink("https://jira.example.com/browse/PROJ-456");
        
        assertEquals(ExternalLinkType.JIRA_SERVER, externalLink.getType());
        assertEquals("https://jira.example.com/browse/PROJ-456", externalLink.getLink());
    }
}
