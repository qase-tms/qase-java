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

    private static boolean isCommonAssertionException(Throwable throwable) {
        String className = throwable.getClass().getName();
        return className.contains("AssertionError") ||
               className.contains("AssertionFailedError") ||
               className.contains("AssertionException");
    }
}
