# ResultsApi

All URIs are relative to *https://api.qase.io/v1*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createResult**](ResultsApi.md#createResult) | **POST** /result/{code}/{id} | Create test run result |
| [**createResultBulk**](ResultsApi.md#createResultBulk) | **POST** /result/{code}/{id}/bulk | Bulk create test run result |
| [**deleteResult**](ResultsApi.md#deleteResult) | **DELETE** /result/{code}/{id}/{hash} | Delete test run result |
| [**getResult**](ResultsApi.md#getResult) | **GET** /result/{code}/{hash} | Get test run result by code |
| [**getResults**](ResultsApi.md#getResults) | **GET** /result/{code} | Get all test run results |
| [**updateResult**](ResultsApi.md#updateResult) | **PATCH** /result/{code}/{id}/{hash} | Update test run result |


<a id="createResult"></a>
# **createResult**
> ResultCreateResponse createResult(code, id, resultCreate)

Create test run result

This method allows to create test run result by Run Id. 

### Example
```java
// Import classes:
import io.qase.client.v1.ApiClient;
import io.qase.client.v1.ApiException;
import io.qase.client.v1.Configuration;
import io.qase.client.v1.auth.*;
import io.qase.client.v1.models.*;
import io.qase.client.v1.api.ResultsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.qase.io/v1");
    
    // Configure API key authorization: TokenAuth
    ApiKeyAuth TokenAuth = (ApiKeyAuth) defaultClient.getAuthentication("TokenAuth");
    TokenAuth.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //TokenAuth.setApiKeyPrefix("Token");

    ResultsApi apiInstance = new ResultsApi(defaultClient);
    String code = "code_example"; // String | Code of project, where to search entities.
    Integer id = 56; // Integer | Identifier.
    ResultCreate resultCreate = new ResultCreate(); // ResultCreate | 
    try {
      ResultCreateResponse result = apiInstance.createResult(code, id, resultCreate);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ResultsApi#createResult");
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
| **code** | **String**| Code of project, where to search entities. | |
| **id** | **Integer**| Identifier. | |
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
| **200** | A result. |  -  |
| **400** | Bad Request. |  -  |
| **401** | Unauthorized. |  -  |
| **403** | Forbidden. |  -  |
| **404** | Not Found. |  -  |
| **422** | Unprocessable Entity. |  -  |
| **429** | Too Many Requests. |  -  |

<a id="createResultBulk"></a>
# **createResultBulk**
> BaseResponse createResultBulk(code, id, resultcreateBulk)

Bulk create test run result

This method allows to create a lot of test run result at once.  If you try to send more than 2,000 results in a single bulk request, you will receive an error with code 413 - Payload Too Large.  If there is no free space left in your team account, when attempting to upload an attachment, e.g., through reporters, you will receive an error with code 507 - Insufficient Storage. 

### Example

```java
// Import classes:

import io.qase.client.v1.ApiClient;
import io.qase.client.v1.ApiException;
import io.qase.client.v1.Configuration;
import io.qase.client.v1.auth.*;
import io.qase.client.v1.models.*;
import io.qase.client.v1.api.ResultsApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api.qase.io/v1");

        // Configure API key authorization: TokenAuth
        ApiKeyAuth TokenAuth = (ApiKeyAuth) defaultClient.getAuthentication("TokenAuth");
        TokenAuth.setApiKey("YOUR API KEY");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //TokenAuth.setApiKeyPrefix("Token");

        ResultsApi apiInstance = new ResultsApi(defaultClient);
        String code = "code_example"; // String | Code of project, where to search entities.
        Integer id = 56; // Integer | Identifier.
        ResultCreateBulk resultcreateBulk = new ResultCreateBulk(); // ResultCreateBulk | 
        try {
            BaseResponse result = apiInstance.createResultBulk(code, id, resultcreateBulk);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling ResultsApi#createResultBulk");
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
| **code** | **String**| Code of project, where to search entities. | |
| **id** | **Integer**| Identifier. | |
| **resultcreateBulk** | [**ResultcreateBulk**](ResultcreateBulk.md)|  | |

### Return type

[**BaseResponse**](BaseResponse.md)

### Authorization

[TokenAuth](../README.md#TokenAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | A result. |  -  |
| **400** | Bad Request. |  -  |
| **401** | Unauthorized. |  -  |
| **403** | Forbidden. |  -  |
| **404** | Not Found. |  -  |
| **413** | Payload Too Large. |  -  |
| **422** | Unprocessable Entity. |  -  |
| **429** | Too Many Requests. |  -  |

<a id="deleteResult"></a>
# **deleteResult**
> HashResponse deleteResult(code, id, hash)

Delete test run result

This method allows to delete test run result. 

### Example
```java
// Import classes:
import io.qase.client.v1.ApiClient;
import io.qase.client.v1.ApiException;
import io.qase.client.v1.Configuration;
import io.qase.client.v1.auth.*;
import io.qase.client.v1.models.*;
import io.qase.client.v1.api.ResultsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.qase.io/v1");
    
    // Configure API key authorization: TokenAuth
    ApiKeyAuth TokenAuth = (ApiKeyAuth) defaultClient.getAuthentication("TokenAuth");
    TokenAuth.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //TokenAuth.setApiKeyPrefix("Token");

    ResultsApi apiInstance = new ResultsApi(defaultClient);
    String code = "code_example"; // String | Code of project, where to search entities.
    Integer id = 56; // Integer | Identifier.
    String hash = "hash_example"; // String | Hash.
    try {
      HashResponse result = apiInstance.deleteResult(code, id, hash);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ResultsApi#deleteResult");
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
| **code** | **String**| Code of project, where to search entities. | |
| **id** | **Integer**| Identifier. | |
| **hash** | **String**| Hash. | |

### Return type

[**HashResponse**](HashResponse.md)

### Authorization

[TokenAuth](../README.md#TokenAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | A result. |  -  |
| **400** | Bad Request. |  -  |
| **401** | Unauthorized. |  -  |
| **403** | Forbidden. |  -  |
| **404** | Not Found. |  -  |
| **429** | Too Many Requests. |  -  |

<a id="getResult"></a>
# **getResult**
> ResultResponse getResult(code, hash)

Get test run result by code

This method allows to retrieve a specific test run result by Hash. 

### Example
```java
// Import classes:
import io.qase.client.v1.ApiClient;
import io.qase.client.v1.ApiException;
import io.qase.client.v1.Configuration;
import io.qase.client.v1.auth.*;
import io.qase.client.v1.models.*;
import io.qase.client.v1.api.ResultsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.qase.io/v1");
    
    // Configure API key authorization: TokenAuth
    ApiKeyAuth TokenAuth = (ApiKeyAuth) defaultClient.getAuthentication("TokenAuth");
    TokenAuth.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //TokenAuth.setApiKeyPrefix("Token");

    ResultsApi apiInstance = new ResultsApi(defaultClient);
    String code = "code_example"; // String | Code of project, where to search entities.
    String hash = "hash_example"; // String | Hash.
    try {
      ResultResponse result = apiInstance.getResult(code, hash);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ResultsApi#getResult");
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
| **code** | **String**| Code of project, where to search entities. | |
| **hash** | **String**| Hash. | |

### Return type

[**ResultResponse**](ResultResponse.md)

### Authorization

[TokenAuth](../README.md#TokenAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | A test run result. |  -  |
| **400** | Bad Request. |  -  |
| **401** | Unauthorized. |  -  |
| **403** | Forbidden. |  -  |
| **404** | Not Found. |  -  |
| **429** | Too Many Requests. |  -  |

<a id="getResults"></a>
# **getResults**
> ResultListResponse getResults(code, status, run, caseId, member, api, fromEndTime, toEndTime, limit, offset)

Get all test run results

This method allows to retrieve all test run results stored in selected project. 

### Example
```java
// Import classes:
import io.qase.client.v1.ApiClient;
import io.qase.client.v1.ApiException;
import io.qase.client.v1.Configuration;
import io.qase.client.v1.auth.*;
import io.qase.client.v1.models.*;
import io.qase.client.v1.api.ResultsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.qase.io/v1");
    
    // Configure API key authorization: TokenAuth
    ApiKeyAuth TokenAuth = (ApiKeyAuth) defaultClient.getAuthentication("TokenAuth");
    TokenAuth.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //TokenAuth.setApiKeyPrefix("Token");

    ResultsApi apiInstance = new ResultsApi(defaultClient);
    String code = "code_example"; // String | Code of project, where to search entities.
    String status = "status_example"; // String | A single test run result status. Possible values: in_progress, passed, failed, blocked, skipped, invalid. 
    String run = "run_example"; // String | A list of run IDs separated by comma.
    String caseId = "caseId_example"; // String | A list of case IDs separated by comma.
    String member = "member_example"; // String | A list of member IDs separated by comma.
    Boolean api = true; // Boolean | 
    String fromEndTime = "fromEndTime_example"; // String | Will return all results created after provided datetime. Allowed format: `Y-m-d H:i:s`. 
    String toEndTime = "toEndTime_example"; // String | Will return all results created before provided datetime. Allowed format: `Y-m-d H:i:s`. 
    Integer limit = 10; // Integer | A number of entities in result set.
    Integer offset = 0; // Integer | How many entities should be skipped.
    try {
      ResultListResponse result = apiInstance.getResults(code, status, run, caseId, member, api, fromEndTime, toEndTime, limit, offset);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ResultsApi#getResults");
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
| **code** | **String**| Code of project, where to search entities. | |
| **status** | **String**| A single test run result status. Possible values: in_progress, passed, failed, blocked, skipped, invalid.  | [optional] |
| **run** | **String**| A list of run IDs separated by comma. | [optional] |
| **caseId** | **String**| A list of case IDs separated by comma. | [optional] |
| **member** | **String**| A list of member IDs separated by comma. | [optional] |
| **api** | **Boolean**|  | [optional] |
| **fromEndTime** | **String**| Will return all results created after provided datetime. Allowed format: &#x60;Y-m-d H:i:s&#x60;.  | [optional] |
| **toEndTime** | **String**| Will return all results created before provided datetime. Allowed format: &#x60;Y-m-d H:i:s&#x60;.  | [optional] |
| **limit** | **Integer**| A number of entities in result set. | [optional] [default to 10] |
| **offset** | **Integer**| How many entities should be skipped. | [optional] [default to 0] |

### Return type

[**ResultListResponse**](ResultListResponse.md)

### Authorization

[TokenAuth](../README.md#TokenAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | A list of all test run results. |  -  |
| **400** | Bad Request. |  -  |
| **401** | Unauthorized. |  -  |
| **403** | Forbidden. |  -  |
| **404** | Not Found. |  -  |
| **429** | Too Many Requests. |  -  |

<a id="updateResult"></a>
# **updateResult**
> HashResponse updateResult(code, id, hash, resultUpdate)

Update test run result

This method allows to update test run result. 

### Example
```java
// Import classes:
import io.qase.client.v1.ApiClient;
import io.qase.client.v1.ApiException;
import io.qase.client.v1.Configuration;
import io.qase.client.v1.auth.*;
import io.qase.client.v1.models.*;
import io.qase.client.v1.api.ResultsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.qase.io/v1");
    
    // Configure API key authorization: TokenAuth
    ApiKeyAuth TokenAuth = (ApiKeyAuth) defaultClient.getAuthentication("TokenAuth");
    TokenAuth.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //TokenAuth.setApiKeyPrefix("Token");

    ResultsApi apiInstance = new ResultsApi(defaultClient);
    String code = "code_example"; // String | Code of project, where to search entities.
    Integer id = 56; // Integer | Identifier.
    String hash = "hash_example"; // String | Hash.
    ResultUpdate resultUpdate = new ResultUpdate(); // ResultUpdate | 
    try {
      HashResponse result = apiInstance.updateResult(code, id, hash, resultUpdate);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ResultsApi#updateResult");
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
| **code** | **String**| Code of project, where to search entities. | |
| **id** | **Integer**| Identifier. | |
| **hash** | **String**| Hash. | |
| **resultUpdate** | [**ResultUpdate**](ResultUpdate.md)|  | |

### Return type

[**HashResponse**](HashResponse.md)

### Authorization

[TokenAuth](../README.md#TokenAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | A result. |  -  |
| **400** | Bad Request. |  -  |
| **401** | Unauthorized. |  -  |
| **403** | Forbidden. |  -  |
| **404** | Not Found. |  -  |
| **422** | Unprocessable Entity. |  -  |
| **429** | Too Many Requests. |  -  |

