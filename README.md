# [Qase TMS](https://qase.io) Java Api Client
[![Download](https://api.bintray.com/packages/qase/maven/io.qase.qase-api/images/download.svg?version=0.0.1) ](https://bintray.com/qase/maven/io.qase.qase-api/0.0.1/link)
[![License](https://lxgaming.github.io/badges/License-Apache%202.0-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)

## Maven ##

Add the following dependency and repository to your pom.xml:
```xml
<dependency>
    <groupId>io.qase</groupId>
    <artifactId>qase-api</artifactId>
    <version>0.0.1</version>
</dependency>
```


```xml
<repository>
    <id>io.qase</id>
    <url>https://dl.bintray.com/qase/maven/</url>
</repository>
```



## Example ##
```java
QaseApi qaseApi = new QaseApi("api_token")
TestCases.Filter filter = qaseApi.testCases()
    .filter()
    .type(Type.other)
    .priority(Priority.high);
TestCasesResponse testCases = qaseApi.testCases().getAll("projectCode", 50, 0, filter);
```