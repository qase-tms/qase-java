# SharedParametersApi

All URIs are relative to *https://api.qase.io/v1*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createSharedParameter**](SharedParametersApi.md#createSharedParameter) | **POST** /shared_parameter | Create a new shared parameter |
| [**deleteSharedParameter**](SharedParametersApi.md#deleteSharedParameter) | **DELETE** /shared_parameter/{id} | Delete shared parameter |
| [**getSharedParameter**](SharedParametersApi.md#getSharedParameter) | **GET** /shared_parameter/{id} | Get a specific shared parameter |
| [**getSharedParameters**](SharedParametersApi.md#getSharedParameters) | **GET** /shared_parameter | Get all shared parameters |
| [**updateSharedParameter**](SharedParametersApi.md#updateSharedParameter) | **PATCH** /shared_parameter/{id} | Update shared parameter |


<a id="createSharedParameter"></a>
# **createSharedParameter**
> UuidResponse createSharedParameter(sharedParameterCreate)

Create a new shared parameter

### Example
```java
// Import classes:
import io.qase.client.v1.ApiClient;
import io.qase.client.v1.ApiException;
import io.qase.client.v1.Configuration;
import io.qase.client.v1.auth.*;
import io.qase.client.v1.models.*;
import io.qase.client.v1.api.SharedParametersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.qase.io/v1");
    
    // Configure API key authorization: TokenAuth
    ApiKeyAuth TokenAuth = (ApiKeyAuth) defaultClient.getAuthentication("TokenAuth");
    TokenAuth.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //TokenAuth.setApiKeyPrefix("Token");

    SharedParametersApi apiInstance = new SharedParametersApi(defaultClient);
    SharedParameterCreate sharedParameterCreate = new SharedParameterCreate(); // SharedParameterCreate | 
    try {
      UuidResponse result = apiInstance.createSharedParameter(sharedParameterCreate);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SharedParametersApi#createSharedParameter");
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
| **sharedParameterCreate** | [**SharedParameterCreate**](SharedParameterCreate.md)|  | |

### Return type

[**UuidResponse**](UuidResponse.md)

### Authorization

[TokenAuth](../README.md#TokenAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | A shared parameter. |  -  |
| **400** | Bad Request. |  -  |
| **401** | Unauthorized. |  -  |
| **403** | Forbidden. |  -  |
| **404** | Not Found. |  -  |
| **422** | Unprocessable Entity. |  -  |
| **429** | Too Many Requests. |  -  |

<a id="deleteSharedParameter"></a>
# **deleteSharedParameter**
> UuidResponse1 deleteSharedParameter(id)

Delete shared parameter

Delete shared parameter along with all its usages in test cases and reviews.

### Example
```java
// Import classes:
import io.qase.client.v1.ApiClient;
import io.qase.client.v1.ApiException;
import io.qase.client.v1.Configuration;
import io.qase.client.v1.auth.*;
import io.qase.client.v1.models.*;
import io.qase.client.v1.api.SharedParametersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.qase.io/v1");
    
    // Configure API key authorization: TokenAuth
    ApiKeyAuth TokenAuth = (ApiKeyAuth) defaultClient.getAuthentication("TokenAuth");
    TokenAuth.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //TokenAuth.setApiKeyPrefix("Token");

    SharedParametersApi apiInstance = new SharedParametersApi(defaultClient);
    UUID id = UUID.randomUUID(); // UUID | Identifier.
    try {
      UuidResponse1 result = apiInstance.deleteSharedParameter(id);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SharedParametersApi#deleteSharedParameter");
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
| **id** | **UUID**| Identifier. | |

### Return type

[**UuidResponse1**](UuidResponse1.md)

### Authorization

[TokenAuth](../README.md#TokenAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Success. |  -  |
| **400** | Bad Request. |  -  |
| **401** | Unauthorized. |  -  |
| **403** | Forbidden. |  -  |
| **404** | Not Found. |  -  |
| **429** | Too Many Requests. |  -  |

<a id="getSharedParameter"></a>
# **getSharedParameter**
> SharedParameterResponse getSharedParameter(id)

Get a specific shared parameter

### Example
```java
// Import classes:
import io.qase.client.v1.ApiClient;
import io.qase.client.v1.ApiException;
import io.qase.client.v1.Configuration;
import io.qase.client.v1.auth.*;
import io.qase.client.v1.models.*;
import io.qase.client.v1.api.SharedParametersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.qase.io/v1");
    
    // Configure API key authorization: TokenAuth
    ApiKeyAuth TokenAuth = (ApiKeyAuth) defaultClient.getAuthentication("TokenAuth");
    TokenAuth.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //TokenAuth.setApiKeyPrefix("Token");

    SharedParametersApi apiInstance = new SharedParametersApi(defaultClient);
    UUID id = UUID.randomUUID(); // UUID | Identifier.
    try {
      SharedParameterResponse result = apiInstance.getSharedParameter(id);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SharedParametersApi#getSharedParameter");
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
| **id** | **UUID**| Identifier. | |

### Return type

[**SharedParameterResponse**](SharedParameterResponse.md)

### Authorization

[TokenAuth](../README.md#TokenAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | A shared parameter. |  -  |
| **400** | Bad Request. |  -  |
| **401** | Unauthorized. |  -  |
| **403** | Forbidden. |  -  |
| **404** | Not Found. |  -  |
| **429** | Too Many Requests. |  -  |

<a id="getSharedParameters"></a>
# **getSharedParameters**
> SharedParameterListResponse getSharedParameters(limit, offset, filtersSearch, filtersType, filtersProjectCodes)

Get all shared parameters

### Example
```java
// Import classes:
import io.qase.client.v1.ApiClient;
import io.qase.client.v1.ApiException;
import io.qase.client.v1.Configuration;
import io.qase.client.v1.auth.*;
import io.qase.client.v1.models.*;
import io.qase.client.v1.api.SharedParametersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.qase.io/v1");
    
    // Configure API key authorization: TokenAuth
    ApiKeyAuth TokenAuth = (ApiKeyAuth) defaultClient.getAuthentication("TokenAuth");
    TokenAuth.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //TokenAuth.setApiKeyPrefix("Token");

    SharedParametersApi apiInstance = new SharedParametersApi(defaultClient);
    Integer limit = 10; // Integer | A number of entities in result set.
    Integer offset = 0; // Integer | How many entities should be skipped.
    String filtersSearch = "filtersSearch_example"; // String | 
    String filtersType = "single"; // String | 
    List<String> filtersProjectCodes = Arrays.asList(); // List<String> | 
    try {
      SharedParameterListResponse result = apiInstance.getSharedParameters(limit, offset, filtersSearch, filtersType, filtersProjectCodes);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SharedParametersApi#getSharedParameters");
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
| **limit** | **Integer**| A number of entities in result set. | [optional] [default to 10] |
| **offset** | **Integer**| How many entities should be skipped. | [optional] [default to 0] |
| **filtersSearch** | **String**|  | [optional] |
| **filtersType** | **String**|  | [optional] [enum: single, group] |
| **filtersProjectCodes** | [**List&lt;String&gt;**](String.md)|  | [optional] |

### Return type

[**SharedParameterListResponse**](SharedParameterListResponse.md)

### Authorization

[TokenAuth](../README.md#TokenAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | A list of all shared parameters. |  -  |
| **400** | Bad Request. |  -  |
| **401** | Unauthorized. |  -  |
| **403** | Forbidden. |  -  |
| **404** | Not Found. |  -  |
| **429** | Too Many Requests. |  -  |

<a id="updateSharedParameter"></a>
# **updateSharedParameter**
> UuidResponse1 updateSharedParameter(id, sharedParameterUpdate)

Update shared parameter

### Example
```java
// Import classes:
import io.qase.client.v1.ApiClient;
import io.qase.client.v1.ApiException;
import io.qase.client.v1.Configuration;
import io.qase.client.v1.auth.*;
import io.qase.client.v1.models.*;
import io.qase.client.v1.api.SharedParametersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.qase.io/v1");
    
    // Configure API key authorization: TokenAuth
    ApiKeyAuth TokenAuth = (ApiKeyAuth) defaultClient.getAuthentication("TokenAuth");
    TokenAuth.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //TokenAuth.setApiKeyPrefix("Token");

    SharedParametersApi apiInstance = new SharedParametersApi(defaultClient);
    UUID id = UUID.randomUUID(); // UUID | Identifier.
    SharedParameterUpdate sharedParameterUpdate = new SharedParameterUpdate(); // SharedParameterUpdate | 
    try {
      UuidResponse1 result = apiInstance.updateSharedParameter(id, sharedParameterUpdate);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SharedParametersApi#updateSharedParameter");
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
| **id** | **UUID**| Identifier. | |
| **sharedParameterUpdate** | [**SharedParameterUpdate**](SharedParameterUpdate.md)|  | |

### Return type

[**UuidResponse1**](UuidResponse1.md)

### Authorization

[TokenAuth](../README.md#TokenAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK. |  -  |
| **400** | Bad Request. |  -  |
| **401** | Unauthorized. |  -  |
| **403** | Forbidden. |  -  |
| **404** | Not Found. |  -  |
| **429** | Too Many Requests. |  -  |

