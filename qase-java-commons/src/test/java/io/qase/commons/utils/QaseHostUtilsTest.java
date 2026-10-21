package io.qase.commons.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QaseHostUtilsTest {

    @Test
    void defaultHostShouldLinkToMainApp() {
        assertEquals("https://app.qase.io/", QaseHostUtils.getAppUrl("qase.io"));
    }

    @Test
    void euClusterShouldLinkToEuApp() {
        assertEquals("https://app-eu.qase.io/", QaseHostUtils.getAppUrl("eu.qase.io"));
    }

    @Test
    void euClusterLookupShouldIgnoreCase() {
        assertEquals("https://app-eu.qase.io/", QaseHostUtils.getAppUrl("EU.qase.io"));
    }

    @Test
    void enterpriseHostShouldLinkToItsOwnHost() {
        assertEquals("https://example.qase.io/", QaseHostUtils.getAppUrl("example.qase.io"));
    }
}
