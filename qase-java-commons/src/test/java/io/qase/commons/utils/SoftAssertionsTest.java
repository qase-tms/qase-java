package io.qase.commons.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class to demonstrate SoftAssertions behavior
 */
class SoftAssertionsTest {

    @Test
    void testSoftAssertionsFailure() {
        // Simulate a test that uses SoftAssertions and fails
        // This would normally be done with AssertJ SoftAssertions
        
        // Create an exception that simulates SoftAssertions failure
        Exception softAssertionException = new Exception("Expecting value to be true but was false") {
            @Override
            public String toString() {
                return "org.assertj.core.api.SoftAssertionError: Expecting value to be true but was false";
            }
        };
        
        // Verify that our ExceptionUtils correctly identifies this as an assertion failure
        assertTrue(ExceptionUtils.isAssertionFailure(softAssertionException), 
                  "SoftAssertions failure should be identified as assertion failure");
    }

    @Test
    void testMultipleAssertionFailures() {
        // Simulate multiple assertion failures (common with SoftAssertions)
        Exception multipleFailuresException = new Exception("Multiple failures") {
            @Override
            public String toString() {
                return "org.assertj.core.api.MultipleFailuresError: Multiple failures";
            }
        };
        
        assertTrue(ExceptionUtils.isAssertionFailure(multipleFailuresException),
                  "MultipleFailuresError should be identified as assertion failure");
    }

    @Test
    void testAssertJMessagePatterns() {
        // Test various AssertJ message patterns that indicate assertion failures
        String[] assertJMessages = {
            "Expecting value to be true but was false",
            "Expecting collection to contain 'value' but was empty",
            "Expecting object to be null but was 'some value'",
            "Expecting value to be equal to 5 but was 3",
            "Expecting string to be empty but was 'not empty'"
        };
        
        for (String message : assertJMessages) {
            Exception exception = new Exception(message);
            assertTrue(ExceptionUtils.isAssertionFailure(exception),
                      "AssertJ message pattern should be identified as assertion failure: " + message);
        }
    }

    @Test
    void testNonAssertionExceptions() {
        // Test that non-assertion exceptions are not identified as assertion failures
        String[] nonAssertionMessages = {
            "NullPointerException: Cannot invoke method on null object",
            "RuntimeException: Network connection failed",
            "IOException: File not found",
            "IllegalArgumentException: Invalid parameter value"
        };
        
        for (String message : nonAssertionMessages) {
            Exception exception = new Exception(message);
            assertFalse(ExceptionUtils.isAssertionFailure(exception),
                       "Non-assertion exception should not be identified as assertion failure: " + message);
        }
    }
}
