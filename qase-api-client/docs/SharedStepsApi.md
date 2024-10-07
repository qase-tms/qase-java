# SharedStepsApi

All URIs are relative to *https://api.qase.io/v1*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createSharedStep**](SharedStepsApi.md#createSharedStep) | **POST** /shared_step/{code} | Create a new shared step |
| [**deleteSharedStep**](SharedStepsApi.md#deleteSharedStep) | **DELETE** /shared_step/{code}/{hash} | Delete shared step |
| [**getSharedStep**](SharedStepsApi.md#getSharedStep) | **GET** /shared_step/{code}/{hash} | Get a specific shared step |
| [**getSharedSteps**](SharedStepsApi.md#getSharedSteps) | **GET** /shared_step/{code} | Get all shared steps |
| [**updateSharedStep**](SharedStepsApi.md#updateSharedStep) | **PATCH** /shared_step/{code}/{hash} | Update shared step |


<a id="createSharedStep"></a>
# **createSharedStep**
> HashResponse createSharedStep(code, sharedStepCreate)

Create a new shared step

This method allows to create a shared step in selected project. 

### Example
```java
// Import classes:
import io.qase.client.v1.ApiClient;
import io.qase.client.v1.ApiException;
import io.qase.client.v1.Configuration;
import io.qase.client.v1.auth.*;
import io.qase.client.v1.models.*;
import io.qase.client.v1.api.SharedStepsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.qase.io/v1");
    
    // Configure API key authorization: TokenAuth
    ApiKeyAuth TokenAuth = (ApiKeyAuth) defaultClient.getAuthentication("TokenAuth");
    TokenAuth.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //TokenAuth.setApiKeyPrefix("Token");

    SharedStepsApi apiInstance = new SharedStepsApi(defaultClient);
    String code = "code_example"; // String | Code of project, where to search entities.
    SharedStepCreate sharedStepCreate = new SharedStepCreate(); // SharedStepCreate | 
    try {
      HashResponse result = apiInstance.createSharedStep(code, sharedStepCreate);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SharedStepsApi#createSharedStep");
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
| **sharedStepCreate** | [**SharedStepCreate**](SharedStepCreate.md)|  | |

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

<a id="deleteSharedStep"></a>
# **deleteSharedStep**
> HashResponse deleteSharedStep(code, hash)

Delete shared step

This method completely deletes a shared step from repository. 

### Example
```java
// Import classes:
import io.qase.client.v1.ApiClient;
import io.qase.client.v1.ApiException;
import io.qase.client.v1.Configuration;
import io.qase.client.v1.auth.*;
import io.qase.client.v1.models.*;
import io.qase.client.v1.api.SharedStepsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.qase.io/v1");
    
    // Configure API key authorization: TokenAuth
    ApiKeyAuth TokenAuth = (ApiKeyAuth) defaultClient.getAuthentication("TokenAuth");
    TokenAuth.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //TokenAuth.setApiKeyPrefix("Token");

    SharedStepsApi apiInstance = new SharedStepsApi(defaultClient);
    String code = "code_example"; // String | Code of project, where to search entities.
    String hash = "hash_example"; // String | Hash.
    try {
      HashResponse result = apiInstance.deleteSharedStep(code, hash);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SharedStepsApi#deleteSharedStep");
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

[**HashResponse**](HashResponse.md)

### Authorization

[TokenAuth](../README.md#TokenAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | A Result. |  -  |
| **400** | Bad Request. |  -  |
| **401** | Unauthorized. |  -  |
| **403** | Forbidden. |  -  |
| **404** | Not Found. |  -  |
| **429** | Too Many Requests. |  -  |

<a id="getSharedStep"></a>
# **getSharedStep**
> SharedStepResponse getSharedStep(code, hash)

Get a specific shared step

This method allows to retrieve a specific shared step. 

### Example
```java
// Import classes:
import io.qase.client.v1.ApiClient;
import io.qase.client.v1.ApiException;
import io.qase.client.v1.Configuration;
import io.qase.client.v1.auth.*;
import io.qase.client.v1.models.*;
import io.qase.client.v1.api.SharedStepsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.qase.io/v1");
    
    // Configure API key authorization: TokenAuth
    ApiKeyAuth TokenAuth = (ApiKeyAuth) defaultClient.getAuthentication("TokenAuth");
    TokenAuth.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //TokenAuth.setApiKeyPrefix("Token");

    SharedStepsApi apiInstance = new SharedStepsApi(defaultClient);
    String code = "code_example"; // String | Code of project, where to search entities.
    String hash = "hash_example"; // String | Hash.
    try {
      SharedStepResponse result = apiInstance.getSharedStep(code, hash);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SharedStepsApi#getSharedStep");
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

[**SharedStepResponse**](SharedStepResponse.md)

### Authorization

[TokenAuth](../README.md#TokenAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | A shared step. |  -  |
| **400** | Bad Request. |  -  |
| **401** | Unauthorized. |  -  |
| **403** | Forbidden. |  -  |
| **404** | Not Found. |  -  |
| **429** | Too Many Requests. |  -  |

<a id="getSharedSteps"></a>
# **getSharedSteps**
> SharedStepListResponse getSharedSteps(code, search, limit, offset)

Get all shared steps

This method allows to retrieve all shared steps stored in selected project. 

### Example
```java
// Import classes:
import io.qase.client.v1.ApiClient;
import io.qase.client.v1.ApiException;
import io.qase.client.v1.Configuration;
import io.qase.client.v1.auth.*;
import io.qase.client.v1.models.*;
import io.qase.client.v1.api.SharedStepsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.qase.io/v1");
    
    // Configure API key authorization: TokenAuth
    ApiKeyAuth TokenAuth = (ApiKeyAuth) defaultClient.getAuthentication("TokenAuth");
    TokenAuth.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //TokenAuth.setApiKeyPrefix("Token");

    SharedStepsApi apiInstance = new SharedStepsApi(defaultClient);
    String code = "code_example"; // String | Code of project, where to search entities.
    String search = "search_example"; // String | Provide a string that will be used to search by name.
    Integer limit = 10; // Integer | A number of entities in result set.
    Integer offset = 0; // Integer | How many entities should be skipped.
    try {
      SharedStepListResponse result = apiInstance.getSharedSteps(code, search, limit, offset);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SharedStepsApi#getSharedSteps");
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
| **search** | **String**| Provide a string that will be used to search by name. | [optional] |
| **limit** | **Integer**| A number of entities in result set. | [optional] [default to 10] |
| **offset** | **Integer**| How many entities should be skipped. | [optional] [default to 0] |

### Return type

[**SharedStepListResponse**](SharedStepListResponse.md)

### Authorization

[TokenAuth](../README.md#TokenAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | A list of all shared steps. |  -  |
| **400** | Bad Request. |  -  |
| **401** | Unauthorized. |  -  |
| **403** | Forbidden. |  -  |
| **404** | Not Found. |  -  |
| **429** | Too Many Requests. |  -  |

<a id="updateSharedStep"></a>
# **updateSharedStep**
> HashResponse updateSharedStep(code, hash, sharedStepUpdate)

Update shared step

This method updates a shared step. 

### Example
```java
// Import classes:
import io.qase.client.v1.ApiClient;
import io.qase.client.v1.ApiException;
import io.qase.client.v1.Configuration;
import io.qase.client.v1.auth.*;
import io.qase.client.v1.models.*;
import io.qase.client.v1.api.SharedStepsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.qase.io/v1");
    
    // Configure API key authorization: TokenAuth
    ApiKeyAuth TokenAuth = (ApiKeyAuth) defaultClient.getAuthentication("TokenAuth");
    TokenAuth.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //TokenAuth.setApiKeyPrefix("Token");

    SharedStepsApi apiInstance = new SharedStepsApi(defaultClient);
    String code = "code_example"; // String | Code of project, where to search entities.
    String hash = "hash_example"; // String | Hash.
    SharedStepUpdate sharedStepUpdate = new SharedStepUpdate(); // SharedStepUpdate | 
    try {
      HashResponse result = apiInstance.updateSharedStep(code, hash, sharedStepUpdate);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SharedStepsApi#updateSharedStep");
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
| **sharedStepUpdate** | [**SharedStepUpdate**](SharedStepUpdate.md)|  | |

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
| **429** | Too Many Requests. |  -  |

