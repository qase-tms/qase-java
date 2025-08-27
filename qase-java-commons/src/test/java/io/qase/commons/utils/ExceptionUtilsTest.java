package io.qase.commons.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.opentest4j.AssertionFailedError;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionUtilsTest {

    @Test
    void testIsAssertionFailure_WithAssertionFailedError() {
        AssertionFailedError assertionError = new AssertionFailedError("Test failed");
        assertTrue(ExceptionUtils.isAssertionFailure(assertionError));
    }

    @Test
    void testIsAssertionFailure_WithAssertionError() {
        AssertionError assertionError = new AssertionError("Test failed");
        assertTrue(ExceptionUtils.isAssertionFailure(assertionError));
    }

    @Test
    void testIsAssertionFailure_WithRuntimeException() {
        RuntimeException runtimeException = new RuntimeException("Something went wrong");
        assertFalse(ExceptionUtils.isAssertionFailure(runtimeException));
    }

    @Test
    void testIsAssertionFailure_WithNull() {
        assertFalse(ExceptionUtils.isAssertionFailure(null));
    }

    @Test
    void testIsAssertionFailure_WithNestedAssertionError() {
        RuntimeException wrapper = new RuntimeException("Wrapper", new AssertionError("Inner assertion"));
        assertTrue(ExceptionUtils.isAssertionFailure(wrapper));
    }

    @Test
    void testIsAssertionFailure_WithJUnitException() {
        // Create a custom exception that simulates JUnit behavior
        Exception junitException = new Exception("Test failed") {
            @Override
            public String toString() {
                return "junit.framework.AssertionFailedError: Test failed";
            }
        };
        junitException.setStackTrace(new StackTraceElement[]{
            new StackTraceElement("junit.framework.Assert", "assertTrue", "Assert.java", 123)
        });
        assertTrue(ExceptionUtils.isAssertionFailure(junitException));
    }

    @Test
    void testIsAssertionFailure_WithTestNGException() {
        // Create a custom exception that simulates TestNG behavior
        Exception testngException = new Exception("Test failed") {
            @Override
            public String toString() {
                return "org.testng.AssertionError: Test failed";
            }
        };
        testngException.setStackTrace(new StackTraceElement[]{
            new StackTraceElement("org.testng.Assert", "assertTrue", "Assert.java", 123)
        });
        assertTrue(ExceptionUtils.isAssertionFailure(testngException));
    }

    @Test
    void testIsAssertionFailure_WithDeepNestedException() {
        // Test deep nesting of exceptions
        Throwable level3 = new AssertionError("Deep assertion");
        Throwable level2 = new RuntimeException("Level 2", level3);
        Throwable level1 = new Exception("Level 1", level2);
        assertTrue(ExceptionUtils.isAssertionFailure(level1));
    }

    @Test
    void testIsAssertionFailure_WithNonAssertionException() {
        // Test that non-assertion exceptions are not detected
        Exception nonAssertionException = new Exception("Something else went wrong") {
            @Override
            public String toString() {
                return "java.lang.Exception: Something else went wrong";
            }
        };
        nonAssertionException.setStackTrace(new StackTraceElement[]{
            new StackTraceElement("com.example.Test", "testMethod", "Test.java", 123)
        });
        assertFalse(ExceptionUtils.isAssertionFailure(nonAssertionException));
    }

    @Test
    void testIsAssertionFailure_WithAssertJSoftAssertionError() {
        // Simulate AssertJ SoftAssertionError
        Exception assertJException = new Exception("Expecting value to be true but was false") {
            @Override
            public String toString() {
                return "org.assertj.core.api.SoftAssertionError: Expecting value to be true but was false";
            }
        };
        assertTrue(ExceptionUtils.isAssertionFailure(assertJException));
    }

    @Test
    void testIsAssertionFailure_WithAssertJMultipleFailuresError() {
        // Simulate AssertJ MultipleFailuresError
        Exception assertJException = new Exception("Multiple failures") {
            @Override
            public String toString() {
                return "org.assertj.core.api.MultipleFailuresError: Multiple failures";
            }
        };
        assertTrue(ExceptionUtils.isAssertionFailure(assertJException));
    }

    @Test
    void testIsAssertionFailure_WithAssertJMessagePatterns() {
        // Test various AssertJ message patterns
        Exception assertJException1 = new Exception("Expecting value to be true but was false");
        Exception assertJException2 = new Exception("Expecting collection to contain 'value' but was empty");
        Exception assertJException3 = new Exception("Expecting object to be null but was 'some value'");
        
        assertTrue(ExceptionUtils.isAssertionFailure(assertJException1));
        assertTrue(ExceptionUtils.isAssertionFailure(assertJException2));
        assertTrue(ExceptionUtils.isAssertionFailure(assertJException3));
    }

    @Test
    void testIsAssertionFailure_WithCommonAssertionMessagePatterns() {
        // Test common assertion message patterns
        Exception assertionException1 = new Exception("expected: <true> but was: <false>");
        Exception assertionException2 = new Exception("Expected value to be 5 but actual was 3");
        Exception assertionException3 = new Exception("Assertion failed: value should not be null");
        
        assertTrue(ExceptionUtils.isAssertionFailure(assertionException1));
        assertTrue(ExceptionUtils.isAssertionFailure(assertionException2));
        assertTrue(ExceptionUtils.isAssertionFailure(assertionException3));
    }

    @Test
    void testIsAssertionFailure_WithAssertJClassNamePatterns() {
        // Test AssertJ class name patterns
        Exception assertJException = new Exception("Test failed") {
            @Override
            public String toString() {
                return "org.assertj.core.api.SoftAssertionError: Test failed";
            }
        };
        assertTrue(ExceptionUtils.isAssertionFailure(assertJException));
    }
}
