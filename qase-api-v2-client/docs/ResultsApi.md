# ResultsApi

All URIs are relative to *https://api.qase.io/v2*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createResultV2**](ResultsApi.md#createResultV2) | **POST** /{project_code}/run/{run_id}/result | Create test run result |
| [**createResultsV2**](ResultsApi.md#createResultsV2) | **POST** /{project_code}/run/{run_id}/results | Bulk create test run result |


<a id="createResultV2"></a>
# **createResultV2**
> ResultCreateResponse createResultV2(projectCode, runId, resultCreate)

Create test run result

This method allows to create single test run result.  If there is no free space left in your team account, when attempting to upload an attachment, e.g., through reporters, you will receive an error with code 507 - Insufficient Storage. 

### Example
```java
// Import classes:
import io.qase.client.v2.ApiClient;
import io.qase.client.v2.ApiException;
import io.qase.client.v2.Configuration;
import io.qase.client.v2.auth.*;
import io.qase.client.v2.models.*;
import io.qase.client.v2.api.ResultsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.qase.io/v2");
    
    // Configure API key authorization: TokenAuth
    ApiKeyAuth TokenAuth = (ApiKeyAuth) defaultClient.getAuthentication("TokenAuth");
    TokenAuth.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //TokenAuth.setApiKeyPrefix("Token");

    ResultsApi apiInstance = new ResultsApi(defaultClient);
    String projectCode = "projectCode_example"; // String | 
    Long runId = 56L; // Long | 
    ResultCreate resultCreate = new ResultCreate(); // ResultCreate | 
    try {
      ResultCreateResponse result = apiInstance.createResultV2(projectCode, runId, resultCreate);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ResultsApi#createResultV2");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **projectCode** | **String**|  | |
| **runId** | **Long**|  | |
| **resultCreate** | [**ResultCreate**](ResultCreate.md)|  | |

### Return type

[**ResultCreateResponse**](ResultCreateResponse.md)

### Authorization

[TokenAuth](../README.md#TokenAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **202** | OK |  -  |
| **400** | Bad Request |  -  |
| **401** | Unauthorized |  -  |
| **403** | Forbidden |  -  |
| **404** | Not Found |  -  |
| **422** | Unprocessable Entity |  -  |

<a id="createResultsV2"></a>
# **createResultsV2**
> ResultCreateBulkResponse createResultsV2(projectCode, runId, createResultsRequestV2)

Bulk create test run result

This method allows to create several test run results at once.  If there is no free space left in your team account, when attempting to upload an attachment, e.g., through reporters, you will receive an error with code 507 - Insufficient Storage. 

### Example
```java
// Import classes:
import io.qase.client.v2.ApiClient;
import io.qase.client.v2.ApiException;
import io.qase.client.v2.Configuration;
import io.qase.client.v2.auth.*;
import io.qase.client.v2.models.*;
import io.qase.client.v2.api.ResultsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.qase.io/v2");
    
    // Configure API key authorization: TokenAuth
    ApiKeyAuth TokenAuth = (ApiKeyAuth) defaultClient.getAuthentication("TokenAuth");
    TokenAuth.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //TokenAuth.setApiKeyPrefix("Token");

    ResultsApi apiInstance = new ResultsApi(defaultClient);
    String projectCode = "projectCode_example"; // String | 
    Long runId = 56L; // Long | 
    CreateResultsRequestV2 createResultsRequestV2 = new CreateResultsRequestV2(); // CreateResultsRequestV2 | 
    try {
      ResultCreateBulkResponse result = apiInstance.createResultsV2(projectCode, runId, createResultsRequestV2);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ResultsApi#createResultsV2");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **projectCode** | **String**|  | |
| **runId** | **Long**|  | |
| **createResultsRequestV2** | [**CreateResultsRequestV2**](CreateResultsRequestV2.md)|  | |

### Return type

[**ResultCreateBulkResponse**](ResultCreateBulkResponse.md)

### Authorization

[TokenAuth](../README.md#TokenAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **202** | OK |  -  |
| **400** | Bad Request |  -  |
| **401** | Unauthorized |  -  |
| **403** | Forbidden |  -  |
| **404** | Not Found |  -  |
| **422** | Unprocessable Entity |  -  |

