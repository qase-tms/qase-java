package io.qase.api.utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static io.qase.api.utils.CucumberUtils.getCaseId;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CucumberUtilsTest {

    @ParameterizedTest
    @MethodSource("data")
    public void getCaseIdTest(List<String> tags, Long actualResult) {
        assertEquals(getCaseId(tags), actualResult);
    }

    static Stream<Arguments> data() {
        Long actualResult = 1234L;
        return Stream.of(
                Arguments.of(Collections.singletonList("@tmsLink=1234"), actualResult),
                Arguments.of(Collections.singletonList("@tmsLink=PRJ-1234"), actualResult),
                Arguments.of(Arrays.asList("@tmsLink=1234", "@PRJ-5678", "@tmsLink=PRJ-9123"), actualResult),
                Arguments.of(Arrays.asList("@tmsLink=PRJ-1234", "@tmsLink=2345", "@PRJ-5678"), actualResult),
                Arguments.of(Collections.singletonList("@tmsLink=any"), null),
                Arguments.of(Collections.singletonList("@any"), null),
                Arguments.of(Collections.singletonList("@tmsLink=any123"), null)
        );
    }

}
