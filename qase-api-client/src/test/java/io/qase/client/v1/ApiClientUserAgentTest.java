package io.qase.client.v1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApiClientUserAgentTest {

    @Test
    void userAgentContainsQaseApiClient() {
        ApiClient client = new ApiClient();

        String userAgent = client.defaultHeaderMap.get("User-Agent");
        assertNotNull(userAgent, "User-Agent header should be set");
        assertTrue(userAgent.contains("qase-api-client"),
                "User-Agent should contain 'qase-api-client', got: " + userAgent);
    }

    @Test
    void userAgentMatchesConvention() {
        ApiClient client = new ApiClient();

        String userAgent = client.defaultHeaderMap.get("User-Agent");
        assertTrue(userAgent.startsWith("qase-api-client-java/"),
                "User-Agent should start with 'qase-api-client-java/', got: " + userAgent);
    }

    @Test
    void userAgentHasVersionAfterSlash() {
        ApiClient client = new ApiClient();

        String userAgent = client.defaultHeaderMap.get("User-Agent");
        String[] parts = userAgent.split("/", 2);
        assertEquals(2, parts.length, "User-Agent should have format 'name/version'");
        assertFalse(parts[1].isEmpty(), "Version part should not be empty");
    }
}
