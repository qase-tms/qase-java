# Qase TMS TestNG Integration #

[![License](https://lxgaming.github.io/badges/License-Apache%202.0-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)

## Description ##

This integration uploads test run results to Qase TMS via API.

## Usage

### Maven

Add the following dependency to your pom.xml:

```xml

<properties>
    <aspectj.version>1.9.8</aspectj.version>
</properties>

<dependencies>
    <dependency>
        <groupId>io.qase</groupId>
        <artifactId>qase-testng</artifactId>
        <version>3.2.0</version>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.testng</groupId>
        <artifactId>testng</artifactId>
        <version>7.1.0</version>
        <scope>test</scope>
    </dependency>
</dependencies>

<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-surefire-plugin</artifactId>
            <version>3.0.0-M5</version>
            <configuration>
                <argLine>
                    -javaagent:"${settings.localRepository}/org/aspectj/aspectjweaver/${aspectj.version}/aspectjweaver-${aspectj.version}.jar"
                </argLine>
            </configuration>
            <dependencies>
                <dependency>
                    <groupId>org.aspectj</groupId>
                    <artifactId>aspectjweaver</artifactId>
                    <version>${aspectj.version}</version>
                </dependency>
            </dependencies>
        </plugin>
    </plugins>
</build>
```

### Gradle

Add the following dependency to build.gradle:

```groovy
configurations {
    aspectjweaver
}

dependencies {
    aspectjweaver "org.aspectj:aspectjweaver:1.9.8"
    testImplementation 'io.qase:qase-testng:3.2.0'
    testImplementation 'org.testng:testng:7.1.0'
}

test {
    systemProperties = System.properties
}

test.doFirst {
    jvmArgs "-javaagent:${configurations.aspectjweaver.singleFile}"
}
```
