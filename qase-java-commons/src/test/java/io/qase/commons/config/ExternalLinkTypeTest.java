package io.qase.commons.config;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ExternalLinkTypeTest {

    @Test
    void testFromValue() {
        assertEquals(ExternalLinkType.JIRA_CLOUD, ExternalLinkType.fromValue("jiraCloud"));
        assertEquals(ExternalLinkType.JIRA_SERVER, ExternalLinkType.fromValue("jiraServer"));
    }

    @Test
    void testFromValueInvalid() {
        assertThrows(IllegalArgumentException.class, () -> ExternalLinkType.fromValue("invalid"));
    }

    @Test
    void testGetValue() {
        assertEquals("jiraCloud", ExternalLinkType.JIRA_CLOUD.getValue());
        assertEquals("jiraServer", ExternalLinkType.JIRA_SERVER.getValue());
    }
}
