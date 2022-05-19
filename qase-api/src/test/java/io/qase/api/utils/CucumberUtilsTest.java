package io.qase.api.utils;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static io.qase.api.utils.CucumberUtils.getCaseId;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CucumberUtilsTest {

    @Test
    public void getCaseIdTest() {
        assertEquals(getCaseId(Collections.singletonList("@tmsLink=1234")), 1234);
        assertEquals(getCaseId(Collections.singletonList("@tmsLink=PRJ-1234")), 1234);
        assertEquals(getCaseId(Arrays.asList("@tmsLink=1234", "@PRJ-5678", "@tmsLink=PRJ-9123")), 1234);
        assertEquals(getCaseId(Arrays.asList("@tmsLink=PRJ-9123", "@tmsLink=1234", "@PRJ-5678")), 9123);

        assertNull(getCaseId(Collections.singletonList("@tmsLink=any")));
        assertNull(getCaseId(Collections.singletonList("@any")));
        assertNull(getCaseId(Collections.singletonList("@tmsLink=any123")));
    }

}
