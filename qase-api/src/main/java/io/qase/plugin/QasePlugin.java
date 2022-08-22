package io.qase.plugin;

public interface QasePlugin {

    String getTest();

    void setTest(String test);

    String getGroups();

    void setGroups(String groups);

    String getTestOutputDirectory();

    boolean isDependencyInTestClasspath(String groupId, String artifactId);
}
