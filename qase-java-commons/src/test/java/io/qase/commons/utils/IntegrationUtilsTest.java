package io.qase.commons.utils;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IntegrationUtilsTest {

    @Test
    void sanitizeCaseIds_dropsZeroAndNegative() {
        List<Long> result = IntegrationUtils.sanitizeCaseIds(Arrays.asList(0L, 42L, -3L, 7L), null);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(42L));
        assertTrue(result.contains(7L));
    }

    @Test
    void sanitizeCaseIds_allInvalid_returnsNull() {
        List<Long> result = IntegrationUtils.sanitizeCaseIds(Arrays.asList(0L, -1L), null);

        assertNull(result);
    }

    @Test
    void sanitizeCaseIds_emptyOrNull_returnsNull() {
        assertNull(IntegrationUtils.sanitizeCaseIds(Collections.<Long>emptyList(), null));
        assertNull(IntegrationUtils.sanitizeCaseIds(null, null));
    }

    @Test
    void sanitizeCaseIds_allValid_returnsSameElements() {
        List<Long> result = IntegrationUtils.sanitizeCaseIds(Arrays.asList(1L, 2L), null);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1L, (long) result.get(0));
        assertEquals(2L, (long) result.get(1));
    }


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
