package io.qase.commons.utils;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CucumberUtilsTest {

    @Test
    void getCaseTags_basicParsing() {
        List<String> tags = Arrays.asList("@QaseTags=smoke,regression");
        List<String> result = CucumberUtils.getCaseTags(tags);

        assertEquals(2, result.size());
        assertEquals("smoke", result.get(0));
        assertEquals("regression", result.get(1));
    }

    @Test
    void getCaseTags_caseInsensitive() {
        List<String> tags = Arrays.asList("@qasetags=Smoke");
        List<String> result = CucumberUtils.getCaseTags(tags);

        assertEquals(1, result.size());
        assertEquals("Smoke", result.get(0));
    }

    @Test
    void getCaseTags_trimming() {
        List<String> tags = Arrays.asList("@QaseTags=smoke , regression");
        List<String> result = CucumberUtils.getCaseTags(tags);

        assertEquals(2, result.size());
        assertEquals("smoke", result.get(0));
        assertEquals("regression", result.get(1));
    }

    @Test
    void getCaseTags_multipleTagsAccumulate() {
        List<String> tags = Arrays.asList("@QaseTags=smoke", "@QaseTags=regression");
        List<String> result = CucumberUtils.getCaseTags(tags);

        assertEquals(2, result.size());
        assertTrue(result.contains("smoke"));
        assertTrue(result.contains("regression"));
    }

    @Test
    void getCaseTags_noTags_returnsEmptyList() {
        List<String> tags = Arrays.asList("@QaseId=42", "@QaseTitle=MyTest");
        List<String> result = CucumberUtils.getCaseTags(tags);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getCaseTags_emptyInput_returnsEmptyList() {
        List<String> result = CucumberUtils.getCaseTags(Collections.<String>emptyList());

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
