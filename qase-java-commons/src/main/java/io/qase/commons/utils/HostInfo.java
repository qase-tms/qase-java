package io.qase.commons.utils;

import java.io.*;
import java.net.InetAddress;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.json.JSONObject;

/**
 * Class that collects and provides information about the host system and installed packages
 */
public class HostInfo {

    /**
     * Remove ANSI escape sequences from a string
     * ANSI escape sequences start with ESC (0x1b) and are used for terminal formatting
     * 
     * @param input String that may contain ANSI escape sequences
     * @return String with ANSI escape sequences removed
     */
    private String removeAnsiEscapeSequences(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        // Remove ANSI escape sequences: \x1b[...m or \x1b[...;...m or similar patterns
        // Pattern matches: ESC followed by [ and any characters until a letter (command)
        return input.replaceAll("\u001B\\[[\\d;]*[A-Za-z]", "");
    }

    /**
     * Execute a command and return its output as a string
     *
     * @param command      Command to execute
     * @param defaultValue Default value to return on error
     * @return Command output or default value
     */
    private String execCommand(String command, String defaultValue) {
        StringBuilder output = new StringBuilder();
        try {
            Process process;
            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                process = Runtime.getRuntime().exec(new String[]{"cmd.exe", "/c", command});
            } else {
                process = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", command});
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                System.err.println("Error executing command '" + command + "': Return code " + exitCode);
                return defaultValue;
            }

            String result = output.toString().trim();
            // Remove ANSI escape sequences from command output
            return removeAnsiEscapeSequences(result);
        } catch (IOException | InterruptedException e) {
            System.err.println("Exception executing command '" + command + "': " + e.getMessage());
            return defaultValue;
        }
    }

    /**
     * Get detailed OS information based on the platform
     *
     * @return Detailed OS information
     */
    private String getDetailedOsInfo() {
        String system = System.getProperty("os.name").toLowerCase();

        try {
            if (system.contains("windows")) {
                return execCommand("ver", "");
            } else if (system.contains("mac")) {
                return execCommand("sw_vers -productVersion", "");
            } else {
                // Linux and others
                try {
                    File osReleaseFile = new File("/etc/os-release");
                    if (osReleaseFile.exists()) {
                        String osRelease = new String(Files.readAllBytes(Paths.get("/etc/os-release")));
                        Pattern pattern = Pattern.compile("PRETTY_NAME=\"(.+)\"");
                        Matcher matcher = pattern.matcher(osRelease);
                        if (matcher.find()) {
                            return matcher.group(1);
                        }
                    }
                } catch (IOException e) {
                    // Fallback if /etc/os-release doesn't exist or can't be read
                }

                return System.getProperty("os.version");
            }
        } catch (Exception e) {
            System.err.println("Error getting detailed OS info: " + e.getMessage());
            return System.getProperty("os.version");
        }
    }

    /**
     * Normalize OS name to a standard format: darwin, windows, linux
     *
     * @param osName Raw OS name from system properties
     * @return Normalized OS name
     */
    private String normalizeSystem(String osName) {
        if (osName == null || osName.isEmpty()) {
            return "";
        }
        String lower = osName.toLowerCase();
        if (lower.contains("mac") || lower.contains("darwin")) {
            return "darwin";
        } else if (lower.contains("windows")) {
            return "windows";
        } else if (lower.contains("linux")) {
            return "linux";
        }
        return lower;
    }

    /**
     * Get package implementation version by class name
     *
     * @param className Fully qualified class name
     * @return Package version or empty string if not available
     */
    private String getPackageVersion(String className) {
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
     * Get information about the current host environment
     *
     * @param reporterVersion Reporter version to check
     * @return Map with host information
     */
    public Map<String, String> getHostInfo(String reporterVersion) {
        return getHostInfo(reporterVersion, null, null);
    }

    /**
     * Get information about the current host environment
     *
     * @param reporterVersion  Reporter version
     * @param frameworkName    Framework name (e.g., "junit5", "testng")
     * @param frameworkVersion Framework version
     * @return Map with host information
     */
    public Map<String, String> getHostInfo(String reporterVersion, String frameworkName, String frameworkVersion) {
        Map<String, String> hostInfo = new HashMap<>();

        try {
            // Get Java version
            String javaVersion = System.getProperty("java.version");

            // Get Maven/Gradle version
            String buildToolVersion = "";
            String mavenOutput = execCommand("mvn --version", "");
            if (!mavenOutput.isEmpty()) {
                Pattern pattern = Pattern.compile("Apache Maven (\\S+)");
                Matcher matcher = pattern.matcher(mavenOutput);
                if (matcher.find()) {
                    buildToolVersion = matcher.group(1);
                }
            } else {
                String gradleOutput = execCommand("gradle --version", "");
                if (!gradleOutput.isEmpty()) {
                    Pattern pattern = Pattern.compile("Gradle (\\S+)");
                    Matcher matcher = pattern.matcher(gradleOutput);
                    if (matcher.find()) {
                        buildToolVersion = matcher.group(1);
                    }
                }
            }

            hostInfo.put("system", normalizeSystem(System.getProperty("os.name")));
            hostInfo.put("machineName", InetAddress.getLocalHost().getHostName());
            hostInfo.put("release", System.getProperty("os.version"));
            hostInfo.put("version", getDetailedOsInfo());
            hostInfo.put("arch", System.getProperty("os.arch"));
            hostInfo.put("language", javaVersion);
            hostInfo.put("packageManager", buildToolVersion);
            hostInfo.put("reporter", reporterVersion != null ? reporterVersion : "");

            // Framework version
            String fwVersion = "";
            if (frameworkName != null && !frameworkName.isEmpty()) {
                fwVersion = (frameworkVersion != null && !frameworkVersion.isEmpty())
                        ? frameworkName + "=" + frameworkVersion
                        : frameworkName;
            }
            hostInfo.put("framework", fwVersion);

            // Library versions
            hostInfo.put("commons", getPackageVersion("io.qase.commons.utils.HostInfo"));
            hostInfo.put("apiClientV1", getPackageVersion("io.qase.client.v1.ApiClient"));
            hostInfo.put("apiClientV2", getPackageVersion("io.qase.client.v2.ApiClient"));
        } catch (Exception e) {
            System.err.println("Error getting host info: " + e.getMessage());

            try {
                hostInfo.put("system", normalizeSystem(System.getProperty("os.name")));
                hostInfo.put("machineName", InetAddress.getLocalHost().getHostName());
                hostInfo.put("release", System.getProperty("os.version"));
                hostInfo.put("version", "");
                hostInfo.put("arch", System.getProperty("os.arch"));
                hostInfo.put("language", "");
                hostInfo.put("packageManager", "");
                hostInfo.put("reporter", "");
                hostInfo.put("framework", "");
                hostInfo.put("commons", "");
                hostInfo.put("apiClientV1", "");
                hostInfo.put("apiClientV2", "");
            } catch (Exception ex) {
                System.err.println("Error creating fallback host info: " + ex.getMessage());
            }
        }

        return hostInfo;
    }

    /**
     * Convert the host info Map to a JSON string
     *
     * @param hostInfo The host info Map
     * @return A JSON string representation
     */
    public String toJson(Map<String, String> hostInfo) {
        JSONObject json = new JSONObject(hostInfo);
        return json.toString(2);
    }
}
