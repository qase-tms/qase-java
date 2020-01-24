# [Qase TMS](https://qase.io) Java Api Client

## Example ##
```java
QaseApi qaseApi = new QaseApi("api_token")
TestCases.Filter filter = qaseApi.testCases()
    .filter()
    .type(Type.other)
    .priority(Priority.high);
TestCasesResponse testCases = qaseApi.testCases().getAll("projectCode", 50, 0, filter);
```