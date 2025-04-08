package io.qase.api.utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static io.qase.commons.utils.CucumberUtils.getCaseIds;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CucumberUtilsTest {

    @ParameterizedTest
    @MethodSource("data")
    public void getCaseIdTest(List<String> tags, List<Long> actualResult) {
        assertEquals(getCaseIds(tags), actualResult);
    }

    static Stream<Arguments> data() {
        List<Long> actualResult = Collections.singletonList(1234L);
        return Stream.of(
                Arguments.of(Collections.singletonList("@tmsLink=1234"), actualResult),
                Arguments.of(Collections.singletonList("@tmsLink=PRJ-1234"), actualResult),
                Arguments.of(Arrays.asList("@tmsLink=1234", "@PRJ-5678", "@tmsLink=PRJ-9123"), Arrays.asList(1234L, 9123L)),
                Arguments.of(Arrays.asList("@tmsLink=PRJ-1234", "@tmsLink=2345", "@PRJ-5678"), Arrays.asList(1234L, 2345L)),
                Arguments.of(Collections.singletonList("@tmsLink=any"), null),
                Arguments.of(Collections.singletonList("@any"), null),
                Arguments.of(Collections.singletonList("@tmsLink=any123"), null),
                Arguments.of(Collections.singletonList("@Qaseids=1234,5678,9123"), Arrays.asList(1234L, 5678L, 9123L)),
                Arguments.of(Collections.singletonList("@Qaseids=PRJ-1234,5678,PRJ-9123"), Arrays.asList(1234L, 5678L, 9123L)),
                Arguments.of(Collections.singletonList("@Qaseids=1234, any, 9123"), Arrays.asList(1234L, 9123L)),
                Arguments.of(Collections.singletonList("@Qaseids=any"), null)
        );
    }

}
