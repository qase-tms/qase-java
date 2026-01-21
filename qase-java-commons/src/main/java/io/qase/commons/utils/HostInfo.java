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
     * Get information about the current host environment
     *
     * @param reporterVersion Reporter version to check
     * @return Map with host information
     */
    public Map<String, String> getHostInfo(String reporterVersion) {
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

            hostInfo.put("system", System.getProperty("os.name").toLowerCase());
            hostInfo.put("machineName", InetAddress.getLocalHost().getHostName());
            hostInfo.put("release", System.getProperty("os.version"));
            hostInfo.put("version", getDetailedOsInfo());
            hostInfo.put("arch", System.getProperty("os.arch"));
            hostInfo.put("java", javaVersion);
            hostInfo.put("buildTool", buildToolVersion);
            hostInfo.put("reporter", reporterVersion);
        } catch (Exception e) {
            System.err.println("Error getting host info: " + e.getMessage());

            try {
                hostInfo.put("system", System.getProperty("os.name").toLowerCase());
                hostInfo.put("machineName", InetAddress.getLocalHost().getHostName());
                hostInfo.put("release", System.getProperty("os.version"));
                hostInfo.put("version", "");
                hostInfo.put("arch", System.getProperty("os.arch"));
                hostInfo.put("java", "");
                hostInfo.put("buildTool", "");
                hostInfo.put("reporter", "");
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
