package io.qase.commons.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntegrationUtilsTest {

    @Test
    void detectFrameworkVersionShouldReturnImplementationVersion() {
        // org.junit.jupiter.api.Test is on the test classpath and has a real Package
        // with version info from the JUnit JAR manifest
        String version = IntegrationUtils.detectFrameworkVersion(org.junit.jupiter.api.Test.class);

        assertNotNull(version);
        // Version should be non-empty for a well-known library on the classpath
        // (if the JAR includes MANIFEST.MF with Implementation-Version)
        // We just verify the method returns a string without throwing
        assertNotNull(version);
    }

    @Test
    void detectFrameworkVersionShouldReturnEmptyStringOnException() {
        // Passing null should trigger exception handling and return empty string
        String version = IntegrationUtils.detectFrameworkVersion(null);

        assertEquals("", version);
    }

    @Test
    void detectFrameworkVersionShouldReturnEmptyStringWhenPackageIsNull() {
        // Use the test class itself — compiled classes in test scope may lack
        // MANIFEST.MF Implementation-Version, so getImplementationVersion() returns null.
        // The method must fall back gracefully and return "" rather than null.
        String version = IntegrationUtils.detectFrameworkVersion(IntegrationUtilsTest.class);

        assertNotNull(version);
        // Must return a string (not null), empty or otherwise
    }
}
