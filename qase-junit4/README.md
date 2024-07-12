# Qase TMS JUnit 4 Integration #
[![License](https://lxgaming.github.io/badges/License-Apache%202.0-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)

## Description ##
This integration uploads test run results to Qase TMS via API.

## Maven ##

Add the following dependency and repository to your pom.xml:
```xml
<dependencies>
    <dependency>
        <groupId>io.qase</groupId>
        <artifactId>qase-junit4</artifactId>
        <version>3.2.1</version>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.13.2</version>
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
                <properties>
                    <property>
                        <name>listener</name>
                        <value>io.qase.junit4.QaseListener</value>
                    </property>
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

### Gradle

Add the following dependency to build.gradle:

```groovy
configurations {
    aspectjweaver
}

dependencies {
    aspectjweaver "org.aspectj:aspectjweaver:1.9.8"
    testImplementation 'io.qase:qase-api:3.2.1'
    testImplementation 'io.qase:qase-junit4:3.2.1'
    testImplementation 'io.qase:qase-junit4-aspect:3.2.1'
    testImplementation 'junit:junit:4.13.2'
}

test {
    systemProperties = System.properties
}

test.doFirst {
    jvmArgs "-javaagent:${configurations.aspectjweaver.singleFile}"
}
```

## Run example ##

```
mvn clean test -DQASE_ENABLE=true -DQASE_PROJECT_CODE=PRJ -DQASE_RUN_ID=123 -DQASE_API_TOKEN=secret-token
```

or

```
gradle clean test -DQASE_ENABLE=true -DQASE_PROJECT_CODE=PRJ -DQASE_RUN_ID=123 -DQASE_API_TOKEN=secret-token
```
