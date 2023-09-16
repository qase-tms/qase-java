# Qase TMS JUnit 5 Integration #
[![License](https://lxgaming.github.io/badges/License-Apache%202.0-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)

## Description ##
This integration uploads test run results to Qase TMS via API.

## Maven ##

Add the following dependency and repository to your pom.xml:
```xml
<dependencies>
    <dependency>
        <groupId>io.qase</groupId>
        <artifactId>qase-junit5</artifactId>
        <version>3.1.0</version>
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
                <properties>
                    <configurationParameters>
                        junit.jupiter.extensions.autodetection.enabled = true
                    </configurationParameters>
                </properties>
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

## Run example ##

```
mvn clean test -DQASE_ENABLE=true -DQASE_PROJECT_CODE=PRJ -DQASE_RUN_ID=123 -DQASE_API_TOKEN=secret-token
```


## Gradle ##
add the below code to build.gradle:
```
dependencies {
    ...
    testImplementation 'io.qase:qase-junit5:3.1.0'
}

test {
    systemProperty("junit.jupiter.extensions.autodetection.enabled", true)
    useJUnitPlatform()
}
```
