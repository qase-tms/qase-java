# Qase TMS Java API V2 Client

## Installation

To install the latest release version (4.0.x), follow the instructions for Maven and Gradle below.

### Maven

Add the following dependency to your `pom.xml`:

```xml

<dependency>
    <groupId>io.qase</groupId>
    <artifactId>qase-api-v2-client</artifactId>
    <version>4.0.0</version>
</dependency>
```

### Gradle

Add the following dependency to your `build.gradle`:

```groovy
testImplementation 'io.qase:qase-api-v2-client:4.0.0'
```

## Usage

The client uses API tokens to authenticate requests. You can view and manage your API keys on
the [API tokens page](https://app.qase.io/user/api/token).

### Authentication

Replace `api_token` with your personal API key:

```java
import io.qase.client.v2.ApiClient;

public class Example {
    public static void main(String[] args) {
        ApiClient apiClient = new ApiClient();
        apiClient.setBasePath("https://api.qase.io/v2");
        apiClient.setApiKey("api_token");
    }
}
```

### Example: Uploading Test Results

To upload test results, use the following:

```java
import io.qase.client.v2.api.ResultsApi;
import io.qase.client.v2.model.ResultCreate;
import io.qase.client.v2.model.CreateResultV2422Response;
import io.qase.client.v2.ApiClient;

public class UploadTestResultExample {
    public static void main(String[] args) {
        ApiClient apiClient = new ApiClient();
        apiClient.setBasePath("https://api.qase.io/v2");
        apiClient.setApiKey("api_token");

        ResultsApi resultsApi = new ResultsApi(apiClient);
        String projectCode = "YOUR_PROJECT_CODE"; // Replace with your project code

        ResultCreate resultCreate = new ResultCreate();
        Long testRunId = 1L; // Replace with your test run ID
        try {
            CreateResultV2422Response response = resultsApi.createResultV2(projectCode, testRunId, resultCreate);
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
