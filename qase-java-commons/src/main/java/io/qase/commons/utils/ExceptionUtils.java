package io.qase.commons.utils;

/**
 * Utility class for exception analysis and classification
 */
public class ExceptionUtils {

    /**
     * Determines if the exception is an assertion failure
     * @param throwable the exception to analyze
     * @return true if the exception is an assertion failure, false otherwise
     */
    public static boolean isAssertionFailure(Throwable throwable) {
        if (throwable == null) {
            return false;
        }

        // Check for JUnit assertion exceptions
        if (isJUnitAssertionException(throwable)) {
            return true;
        }

        // Check for TestNG assertion exceptions
        if (isTestNGAssertionException(throwable)) {
            return true;
        }

        // Check for AssertJ and other assertion libraries
        if (isAssertJAssertionException(throwable)) {
            return true;
        }

        // Check for common assertion libraries
        if (isCommonAssertionException(throwable)) {
            return true;
        }

        // Check the cause recursively
        return isAssertionFailure(throwable.getCause());
    }

    private static boolean isJUnitAssertionException(Throwable throwable) {
        String className = throwable.getClass().getName();
        String toString = throwable.toString();
        
        return className.contains("AssertionFailedError") ||
               className.contains("AssertionError") ||
               className.contains("junit.framework") ||
               className.contains("org.junit") ||
               className.contains("org.opentest4j") ||
               toString.contains("junit.framework.AssertionFailedError") ||
               toString.contains("junit.framework.AssertionError");
    }

    private static boolean isTestNGAssertionException(Throwable throwable) {
        String className = throwable.getClass().getName();
        String toString = throwable.toString();
        
        return className.contains("AssertionError") ||
               className.contains("org.testng") ||
               className.contains("AssertionFailedError") ||
               toString.contains("org.testng.AssertionError") ||
               toString.contains("org.testng.AssertionFailedError");
    }

    private static boolean isAssertJAssertionException(Throwable throwable) {
        String className = throwable.getClass().getName();
        String toString = throwable.toString();
        String message = throwable.getMessage();
        
        // Check for AssertJ specific exceptions
        return className.contains("AssertJ") ||
               className.contains("org.assertj") ||
               className.contains("SoftAssertionError") ||
               className.contains("MultipleFailuresError") ||
               // Check for common AssertJ exception patterns
               (message != null && (
                   message.contains("Expecting") ||
                   message.contains("to be") ||
                   message.contains("to have") ||
                   message.contains("to contain") ||
                   message.contains("to equal") ||
                   message.contains("to be equal to") ||
                   message.contains("to be true") ||
                   message.contains("to be false") ||
                   message.contains("to be null") ||
                   message.contains("to be empty") ||
                   message.contains("to be greater than") ||
                   message.contains("to be less than")
               )) ||
               // Check for SoftAssertions specific patterns
               (toString.contains("SoftAssertionError") ||
                toString.contains("MultipleFailuresError") ||
                toString.contains("org.assertj"));
    }

    private static boolean isCommonAssertionException(Throwable throwable) {
        String className = throwable.getClass().getName();
        String message = throwable.getMessage();
        
        // Only check for specific assertion exception classes
        if (className.contains("AssertionError") ||
            className.contains("AssertionFailedError") ||
            className.contains("AssertionException")) {
            return true;
        }
        
        // Check for common assertion message patterns only if they are very specific
        if (message != null) {
            // Look for very specific assertion patterns that are unlikely to appear in regular exceptions
            return message.contains("expected: <") && message.contains("> but was: <") ||
                   message.contains("Expected") && message.contains("but was") ||
                   message.contains("Assertion failed:") ||
                   (message.contains("expected") && message.contains("actual"));
        }
        
        return false;
    }
}
