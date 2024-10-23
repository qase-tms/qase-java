package org.example;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class ParametrisedTests {
    static Stream<Arguments> arguments() {
        return Stream.of(
                Arguments.of("string1"),
                Arguments.of("string2"),
                Arguments.of("string3")
        );
    }

    @ParameterizedTest
    @MethodSource("arguments")
    public void testMethod_success1(String data) {
        System.out.println("Data is: " + data);
    }

    @ParameterizedTest
    @MethodSource("arguments")
    public void testMethod_failed1(String data) {
        System.out.println("Data is: " + data);
        throw new RuntimeException("Test failed");
    }
}
