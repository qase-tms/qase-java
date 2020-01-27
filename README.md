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

## Usage ##

```java
QaseApi qaseApi = new QaseApi("8ad37269a306d94f2387067e42b1a01565ebc0a8")
```

### Get Project Info ###
```java
qaseApi.projects().get("PRJ").getResult().getTitle()
```

		
### Get All Projects ###
```java
List<Entity> projects = qaseApi.projects().getAll().getResult().getEntities();
for (Entity project : projects) {
    System.out.println(project.getTitle());
}
```

### Create Project ###
```java
qaseApi.projects().create("PRJ", "Project description").getStatus()
```

### Get Suite Info ###
```java
SuiteResponse suite = qaseApi.suites().get("PRJ", "1");
System.out.println(suite.getResult().getTitle());
```

### Get All Suites ###
```java
Suites.Filter filter = qaseApi.suites().filter().search("suite-title-for-search");
SuitesResponse suites = qaseApi.suites().getAll("PRJ", 100, 0, filter);
List<Entity> suitesInfo = suites.getResult().getEntities();
for (Entity suiteInfo : suitesInfo) {
    System.out.println(suiteInfo.getTitle());
}
```