# Qase TMS JUnit 4 Integration #
[![License](https://lxgaming.github.io/badges/License-Apache%202.0-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)

## Description ##
This integration uploads test run results to Qase TMS via API.

To link autotest to test case in Qase TMS use annotation `@CaseId`

### Required params ###
All required params are passed through system properties or environment variables:

|  Key              | Description |
| :---------------: | :----------: |
| QASE_ENABLE       | Enable Integration |
| QASE_PROJECT_CODE | Project Code |
| QASE_RUN_ID       | Run Id |
| QASE_API_TOKEN    | Qase API Token |

## Maven ##

Add the following dependency and repository to your pom.xml:
```xml
<dependencies>
    <dependency>
        <groupId>io.qase</groupId>
        <artifactId>qase-junit4</artifactId>
        <version>2.1.1</version>
        <scope>test</scope>
    </dependency>
</dependencies>
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-surefire-plugin</artifactId>
            <version>3.0.0-M4</version>
            <configuration>
                <properties>
                    <property>
                        <name>listener</name>
                        <value>io.qase.junit4.QaseListener</value>
                    </property>
                </properties>
            </configuration>
        </plugin>
    </plugins>
</build>

```

## Run example ##

```
mvn clean test -DQASE_ENABLE=true -DQASE_PROJECT_CODE=PRJ -DQASE_RUN_ID=123 -DQASE_API_TOKEN=secret-token
```
