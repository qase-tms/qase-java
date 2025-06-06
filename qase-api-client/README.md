# Qase TMS Java API Client

## Installation

To install the latest release version (4.0.x), follow the instructions for Maven and Gradle below.

### Maven

Add the following dependency to your `pom.xml`:

```xml

<dependency>
    <groupId>io.qase</groupId>
    <artifactId>qase-api-client</artifactId>
    <version>4.0.0</version>
</dependency>
```

### Gradle

Add the following dependency to your `build.gradle`:

```groovy
testImplementation 'io.qase:qase-api-client:4.0.0'
```

## Usage

The client uses API tokens to authenticate requests. You can view and manage your API keys on
the [API tokens page](https://app.qase.io/user/api/token).

### Authentication

Replace `api_token` with your personal API key:

```java
import io.qase.client.ApiClient;

public class Example {
    public static void main(String[] args) {
        ApiClient apiClient = new ApiClient();
        apiClient.setBasePath("https://api.qase.io/v1");
        apiClient.setApiKey("api_token");
    }
}
```

### Example: Creating a New Test Run

To create a new test run, use the following:

```java
import io.qase.client.api.RunsApi;
import io.qase.client.model.RunCreate;
import io.qase.client.model.RunCreateResponse;
import io.qase.client.ApiClient;

public class TestRunExample {
    public static void main(String[] args) {
        ApiClient apiClient = new ApiClient();
        apiClient.setBasePath("https://api.qase.io/v1");
        apiClient.setApiKey("api_token");

        RunsApi runsApi = new RunsApi(apiClient);
        String projectCode = "YOUR_PROJECT_CODE"; // Replace with your project code

        RunCreate runCreate = new RunCreate();
        runCreate.setTitle("Automated Test Run");

        try {
            RunCreateResponse response = runsApi.createRun(projectCode, runCreate);
            System.out.println("Created Test Run with ID: " + response.getResult().getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

## Additional Documentation

For further information on the API endpoints and parameters, please refer to
the [Qase API documentation](https://developers.qase.io/).

## Requirements

- **Java** 1.8+
- **Maven** 3.6+ или **Gradle** 6.0+
