package io.qase.commons.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Utility class for building X-Client and X-Platform headers for API requests
 */
public class ClientHeadersBuilder {
    
    /**
     * Build X-Client header value
     * Format: reporter={reporter_name};reporter_version=v{reporter_version};framework={framework};framework_version={framework_version};client_version_v1=v{api_client_v1_version};client_version_v2=v{api_client_v2_version};core_version=v{commons_version}
     * 
     * @return X-Client header value
     */
    public static String buildXClientHeader() {
        return buildXClientHeader(null, null, null, null);
    }
    
    /**
     * Build X-Client header value
     * Format: reporter={reporter_name};reporter_version=v{reporter_version};framework={framework};framework_version={framework_version};client_version_v1=v{api_client_v1_version};client_version_v2=v{api_client_v2_version};core_version=v{commons_version}
     * 
     * @param reporterName Reporter name (e.g., "qase-junit5")
     * @param reporterVersion Reporter version
     * @param frameworkName Framework name (e.g., "junit5", "testng", "cucumber")
     * @param frameworkVersion Framework version
     * @return X-Client header value
     */
    public static String buildXClientHeader(String reporterName, String reporterVersion,
                                            String frameworkName, String frameworkVersion) {
        List<String> parts = new ArrayList<>();
        
        // Get package versions
        String apiClientV1Version = getPackageVersion("io.qase.client.v1.ApiClient");
        String apiClientV2Version = getPackageVersion("io.qase.client.v2.ApiClient");
        String commonsVersion = getPackageVersion("io.qase.commons.utils.ClientHeadersBuilder");
        
        // Try to detect reporter and framework from stack trace if not provided
        if (reporterName == null || reporterName.isEmpty()) {
            ReporterInfo detected = detectReporterAndFramework();
            if (detected != null) {
                reporterName = detected.reporterName;
                reporterVersion = detected.reporterVersion;
                frameworkName = detected.frameworkName;
                frameworkVersion = detected.frameworkVersion;
            }
        }
        
        // Add reporter info
        if (reporterName != null && !reporterName.isEmpty()) {
            parts.add("reporter=" + reporterName);
        }
        if (reporterVersion != null && !reporterVersion.isEmpty()) {
            parts.add("reporter_version=" + ensureNoVPrefix(reporterVersion));
        }
        
        // Add framework info
        if (frameworkName != null && !frameworkName.isEmpty()) {
            parts.add("framework=" + frameworkName);
        }
        if (frameworkVersion != null && !frameworkVersion.isEmpty()) {
            parts.add("framework_version=" + frameworkVersion);
        }
        
        // Add client versions
        if (apiClientV1Version != null && !apiClientV1Version.isEmpty()) {
            parts.add("client_version_v1=" + ensureNoVPrefix(apiClientV1Version));
        }
        if (apiClientV2Version != null && !apiClientV2Version.isEmpty()) {
            parts.add("client_version_v2=" + ensureNoVPrefix(apiClientV2Version));
        }
        
        // Add core version
        if (commonsVersion != null && !commonsVersion.isEmpty()) {
            parts.add("core_version=" + ensureNoVPrefix(commonsVersion));
        }
        
        return String.join(";", parts);
    }
    
    /**
     * Remove ANSI escape sequences and other control characters from a string
     * ANSI escape sequences start with ESC (0x1b) and are used for terminal formatting
     * Control characters (0x00-0x1F, except tab, newline, carriage return) are not allowed in HTTP headers
     * 
     * @param input String that may contain ANSI escape sequences or control characters
     * @return String with ANSI escape sequences and invalid control characters removed
     */
    private static String sanitizeHeaderValue(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        // Remove ANSI escape sequences: \x1b[...m or \x1b[...;...m or similar patterns
        String result = input.replaceAll("\u001B\\[[\\d;]*[A-Za-z]", "");
        // Remove other control characters except tab (0x09), newline (0x0A), and carriage return (0x0D)
        result = result.replaceAll("[\\x00-\\x08\\x0B\\x0C\\x0E-\\x1F\\x7F]", "");
        return result;
    }
    
    /**
     * Build X-Platform header value
     * Format: os={os_name};arch={arch};{language}={language_version};{package_manager}={package_manager_version}
     * 
     * @param hostInfo Host information map from HostInfo.getHostInfo()
     * @return X-Platform header value
     */
    public static String buildXPlatformHeader(Map<String, String> hostInfo) {
        List<String> parts = new ArrayList<>();
        
        if (hostInfo == null) {
            return "";
        }
        
        // OS name
        String osName = hostInfo.get("system");
        if (osName != null && !osName.isEmpty()) {
            parts.add("os=" + sanitizeHeaderValue(osName));
        }
        
        // Architecture
        String arch = hostInfo.get("arch");
        if (arch != null && !arch.isEmpty()) {
            parts.add("arch=" + sanitizeHeaderValue(arch));
        }
        
        // Java version (language)
        String javaVersion = hostInfo.get("java");
        if (javaVersion != null && !javaVersion.isEmpty()) {
            parts.add("java=" + sanitizeHeaderValue(javaVersion));
        }
        
        // Build tool version (package manager: maven or gradle)
        String buildTool = hostInfo.get("buildTool");
        if (buildTool != null && !buildTool.isEmpty()) {
            // Determine package manager type
            String packageManager = detectPackageManager();
            // If package manager not detected but buildTool is present, default to maven
            if (packageManager == null || packageManager.isEmpty()) {
                packageManager = "maven"; // Default to maven if buildTool is present
            }
            parts.add(packageManager + "=" + sanitizeHeaderValue(buildTool));
        }
        
        return String.join(";", parts);
    }
    
    /**
     * Get package implementation version
     * 
     * @param className Fully qualified class name
     * @return Package version or null if not available
     */
    private static String getPackageVersion(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            Package pkg = clazz.getPackage();
            if (pkg != null) {
                String version = pkg.getImplementationVersion();
                return version != null ? version : "";
            }
        } catch (ClassNotFoundException e) {
            // Class not found, return empty string
        }
        return "";
    }
    
    /**
     * Ensure version string doesn't have duplicate "v" prefix
     * 
     * @param version Version string
     * @return Version string without leading "v"
     */
    private static String ensureNoVPrefix(String version) {
        if (version == null || version.isEmpty()) {
            return version;
        }
        return version.startsWith("v") ? version.substring(1) : version;
    }
    
    /**
     * Detect package manager type (maven or gradle)
     * 
     * @return Package manager name or null if not detected
     */
    private static String detectPackageManager() {
        // Check for Maven
        try {
            Class.forName("org.apache.maven.project.MavenProject");
            return "maven";
        } catch (ClassNotFoundException e) {
            // Maven not found
        }
        
        // Check for Gradle
        try {
            Class.forName("org.gradle.api.Project");
            return "gradle";
        } catch (ClassNotFoundException e) {
            // Gradle not found
        }
        
        // Try to detect from system properties or environment
        String mavenHome = System.getProperty("maven.home");
        if (mavenHome != null && !mavenHome.isEmpty()) {
            return "maven";
        }
        
        String gradleHome = System.getProperty("gradle.home");
        if (gradleHome != null && !gradleHome.isEmpty()) {
            return "gradle";
        }
        
        // Default to maven if buildTool is present (most common)
        return null;
    }
    
    /**
     * Detect reporter and framework from stack trace
     * 
     * @return ReporterInfo with detected information or null if not detected
     */
    private static ReporterInfo detectReporterAndFramework() {
        try {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            for (StackTraceElement element : stackTrace) {
                String className = element.getClassName();
                
                // Check for Qase reporter packages
                if (className.startsWith("io.qase.")) {
                    ReporterInfo info = new ReporterInfo();
                    
                    // Detect reporter name and version
                    if (className.contains("junit4")) {
                        info.reporterName = "qase-junit4";
                        info.frameworkName = "junit4";
                        info.reporterVersion = getPackageVersion("io.qase.junit4.QaseListener");
                        info.frameworkVersion = getFrameworkVersion("junit.framework.TestCase");
                    } else if (className.contains("junit5")) {
                        info.reporterName = "qase-junit5";
                        info.frameworkName = "junit5";
                        info.reporterVersion = getPackageVersion("io.qase.junit5.QaseListener");
                        info.frameworkVersion = getFrameworkVersion("org.junit.jupiter.api.Test");
                    } else if (className.contains("testng")) {
                        info.reporterName = "qase-testng";
                        info.frameworkName = "testng";
                        info.reporterVersion = getPackageVersion("io.qase.testng.QaseListener");
                        info.frameworkVersion = getFrameworkVersion("org.testng.TestNG");
                    } else if (className.contains("cucumber3")) {
                        info.reporterName = "qase-cucumber-v3";
                        info.frameworkName = "cucumber";
                        info.reporterVersion = getPackageVersion("io.qase.cucumber3.QaseEventListener");
                        info.frameworkVersion = getFrameworkVersion("io.cucumber.core.api.plugin.Plugin");
                    } else if (className.contains("cucumber4")) {
                        info.reporterName = "qase-cucumber-v4";
                        info.frameworkName = "cucumber";
                        info.reporterVersion = getPackageVersion("io.qase.cucumber4.QaseEventListener");
                        info.frameworkVersion = getFrameworkVersion("io.cucumber.core.api.plugin.Plugin");
                    } else if (className.contains("cucumber5")) {
                        info.reporterName = "qase-cucumber-v5";
                        info.frameworkName = "cucumber";
                        info.reporterVersion = getPackageVersion("io.qase.cucumber5.QaseEventListener");
                        info.frameworkVersion = getFrameworkVersion("io.cucumber.core.api.plugin.Plugin");
                    } else if (className.contains("cucumber6")) {
                        info.reporterName = "qase-cucumber-v6";
                        info.frameworkName = "cucumber";
                        info.reporterVersion = getPackageVersion("io.qase.cucumber6.QaseEventListener");
                        info.frameworkVersion = getFrameworkVersion("io.cucumber.core.api.plugin.Plugin");
                    } else if (className.contains("cucumber7")) {
                        info.reporterName = "qase-cucumber-v7";
                        info.frameworkName = "cucumber";
                        info.reporterVersion = getPackageVersion("io.qase.cucumber7.QaseEventListener");
                        info.frameworkVersion = getFrameworkVersion("io.cucumber.core.api.plugin.Plugin");
                    }
                    
                    if (info.reporterName != null) {
                        return info;
                    }
                }
            }
        } catch (Exception e) {
            // If detection fails, return null (will skip reporter/framework info)
        }
        
        return null;
    }
    
    /**
     * Get framework version from package
     * 
     * @param className Framework class name
     * @return Framework version or empty string if not available
     */
    private static String getFrameworkVersion(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            Package pkg = clazz.getPackage();
            if (pkg != null) {
                String version = pkg.getImplementationVersion();
                if (version == null || version.isEmpty()) {
                    version = pkg.getSpecificationVersion();
                }
                return version != null ? version : "";
            }
        } catch (ClassNotFoundException e) {
            // Framework class not found
        }
        return "";
    }
    
    /**
     * Internal class to hold reporter and framework information
     */
    private static class ReporterInfo {
        String reporterName;
        String reporterVersion;
        String frameworkName;
        String frameworkVersion;
    }
}
