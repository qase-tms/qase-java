package io.qase.commons.utils;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilsTest {

    @Test
    void isBlankShouldReturnTrueForNull() {
        assertTrue(StringUtils.isBlank(null));
    }

    @Test
    void isBlankShouldReturnTrueForEmptyString() {
        assertTrue(StringUtils.isBlank(""));
    }

    @Test
    void isBlankShouldReturnTrueForWhitespaceString() {
        assertTrue(StringUtils.isBlank("   "));
    }

    @Test
    void isBlankShouldReturnFalseForNonEmptyString() {
        assertFalse(StringUtils.isBlank("test"));
    }

    @Test
    void getDateTimeShouldReturnValidFormat() {
        String dateTime = StringUtils.getDateTime();
        assertNotNull(dateTime);
        assertTrue(dateTime.matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}(\\.\\d+)?"));
    }

    @Test
    void generateSignatureWithIdsOnly() {
        ArrayList<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(2L);
        ids.add(3L);

        String signature = StringUtils.generateSignature(ids, null, null);
        assertEquals("1-2-3", signature);
    }

    @Test
    void generateSignatureWithSuitesOnly() {
        ArrayList<String> suites = new ArrayList<>();
        suites.add("Test Suite");
        suites.add("Another Suite");

        String signature = StringUtils.generateSignature(null, suites, null);
        assertEquals("test_suite::another_suite", signature);
    }

    @Test
    void generateSignatureWithParametersOnly() {
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("param1", "value1");
        parameters.put("param2", "value2");

        String signature = StringUtils.generateSignature(null, null, parameters);
        assertEquals("{\"param1\":\"value1\"}::{\"param2\":\"value2\"}", signature);
    }

    @Test
    void generateSignatureWithAllParameters() {
        ArrayList<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(2L);

        ArrayList<String> suites = new ArrayList<>();
        suites.add("Test Suite");

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("param1", "value1");

        String signature = StringUtils.generateSignature(ids, suites, parameters);
        assertEquals("1-2::test_suite::{\"param1\":\"value1\"}", signature);
    }

    @Test
    void generateSignatureWithEmptyInputs() {
        String signature = StringUtils.generateSignature(null, null, null);
        assertEquals("", signature);
    }

    @Test
    void generateSignatureWithEmptyLists() {
        String signature = StringUtils.generateSignature(new ArrayList<>(), new ArrayList<>(), new HashMap<>());
        assertEquals("", signature);
    }
}
