# DefectsApi

All URIs are relative to *https://api.qase.io/v1*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createDefect**](DefectsApi.md#createDefect) | **POST** /defect/{code} | Create a new defect |
| [**deleteDefect**](DefectsApi.md#deleteDefect) | **DELETE** /defect/{code}/{id} | Delete defect |
| [**getDefect**](DefectsApi.md#getDefect) | **GET** /defect/{code}/{id} | Get a specific defect |
| [**getDefects**](DefectsApi.md#getDefects) | **GET** /defect/{code} | Get all defects |
| [**resolveDefect**](DefectsApi.md#resolveDefect) | **PATCH** /defect/{code}/resolve/{id} | Resolve a specific defect |
| [**updateDefect**](DefectsApi.md#updateDefect) | **PATCH** /defect/{code}/{id} | Update defect |
| [**updateDefectStatus**](DefectsApi.md#updateDefectStatus) | **PATCH** /defect/{code}/status/{id} | Update a specific defect status |


<a id="createDefect"></a>
# **createDefect**
> IdResponse createDefect(code, defectCreate)

Create a new defect

This method allows to create a defect in selected project. 

### Example
```java
// Import classes:
import io.qase.client.v1.ApiClient;
import io.qase.client.v1.ApiException;
import io.qase.client.v1.Configuration;
import io.qase.client.v1.auth.*;
import io.qase.client.v1.models.*;
import io.qase.client.v1.api.DefectsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.qase.io/v1");
    
    // Configure API key authorization: TokenAuth
    ApiKeyAuth TokenAuth = (ApiKeyAuth) defaultClient.getAuthentication("TokenAuth");
    TokenAuth.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //TokenAuth.setApiKeyPrefix("Token");

    DefectsApi apiInstance = new DefectsApi(defaultClient);
    String code = "code_example"; // String | Code of project, where to search entities.
    DefectCreate defectCreate = new DefectCreate(); // DefectCreate | 
    try {
      IdResponse result = apiInstance.createDefect(code, defectCreate);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefectsApi#createDefect");
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
| **defectCreate** | [**DefectCreate**](DefectCreate.md)|  | |

### Return type

[**IdResponse**](IdResponse.md)

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

<a id="deleteDefect"></a>
# **deleteDefect**
> IdResponse deleteDefect(code, id)

Delete defect

This method completely deletes a defect from repository. 

### Example
```java
// Import classes:
import io.qase.client.v1.ApiClient;
import io.qase.client.v1.ApiException;
import io.qase.client.v1.Configuration;
import io.qase.client.v1.auth.*;
import io.qase.client.v1.models.*;
import io.qase.client.v1.api.DefectsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.qase.io/v1");
    
    // Configure API key authorization: TokenAuth
    ApiKeyAuth TokenAuth = (ApiKeyAuth) defaultClient.getAuthentication("TokenAuth");
    TokenAuth.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //TokenAuth.setApiKeyPrefix("Token");

    DefectsApi apiInstance = new DefectsApi(defaultClient);
    String code = "code_example"; // String | Code of project, where to search entities.
    Integer id = 56; // Integer | Identifier.
    try {
      IdResponse result = apiInstance.deleteDefect(code, id);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefectsApi#deleteDefect");
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

### Return type

[**IdResponse**](IdResponse.md)

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

<a id="getDefect"></a>
# **getDefect**
> DefectResponse getDefect(code, id)

Get a specific defect

This method allows to retrieve a specific defect. 

### Example
```java
// Import classes:
import io.qase.client.v1.ApiClient;
import io.qase.client.v1.ApiException;
import io.qase.client.v1.Configuration;
import io.qase.client.v1.auth.*;
import io.qase.client.v1.models.*;
import io.qase.client.v1.api.DefectsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.qase.io/v1");
    
    // Configure API key authorization: TokenAuth
    ApiKeyAuth TokenAuth = (ApiKeyAuth) defaultClient.getAuthentication("TokenAuth");
    TokenAuth.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //TokenAuth.setApiKeyPrefix("Token");

    DefectsApi apiInstance = new DefectsApi(defaultClient);
    String code = "code_example"; // String | Code of project, where to search entities.
    Integer id = 56; // Integer | Identifier.
    try {
      DefectResponse result = apiInstance.getDefect(code, id);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefectsApi#getDefect");
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

### Return type

[**DefectResponse**](DefectResponse.md)

### Authorization

[TokenAuth](../README.md#TokenAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | A defect. |  -  |
| **400** | Bad Request. |  -  |
| **401** | Unauthorized. |  -  |
| **403** | Forbidden. |  -  |
| **404** | Not Found. |  -  |
| **429** | Too Many Requests. |  -  |

<a id="getDefects"></a>
# **getDefects**
> DefectListResponse getDefects(code, status, limit, offset)

Get all defects

This method allows to retrieve all defects stored in selected project. 

### Example
```java
// Import classes:
import io.qase.client.v1.ApiClient;
import io.qase.client.v1.ApiException;
import io.qase.client.v1.Configuration;
import io.qase.client.v1.auth.*;
import io.qase.client.v1.models.*;
import io.qase.client.v1.api.DefectsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.qase.io/v1");
    
    // Configure API key authorization: TokenAuth
    ApiKeyAuth TokenAuth = (ApiKeyAuth) defaultClient.getAuthentication("TokenAuth");
    TokenAuth.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //TokenAuth.setApiKeyPrefix("Token");

    DefectsApi apiInstance = new DefectsApi(defaultClient);
    String code = "code_example"; // String | Code of project, where to search entities.
    String status = "open"; // String | 
    Integer limit = 10; // Integer | A number of entities in result set.
    Integer offset = 0; // Integer | How many entities should be skipped.
    try {
      DefectListResponse result = apiInstance.getDefects(code, status, limit, offset);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefectsApi#getDefects");
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
| **status** | **String**|  | [optional] [enum: open, resolved, in_progress, invalid] |
| **limit** | **Integer**| A number of entities in result set. | [optional] [default to 10] |
| **offset** | **Integer**| How many entities should be skipped. | [optional] [default to 0] |

### Return type

[**DefectListResponse**](DefectListResponse.md)

### Authorization

[TokenAuth](../README.md#TokenAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | A list of all defects. |  -  |
| **400** | Bad Request. |  -  |
| **401** | Unauthorized. |  -  |
| **403** | Forbidden. |  -  |
| **404** | Not Found. |  -  |
| **429** | Too Many Requests. |  -  |

<a id="resolveDefect"></a>
# **resolveDefect**
> IdResponse resolveDefect(code, id)

Resolve a specific defect

This method allows to resolve a specific defect. 

### Example
```java
// Import classes:
import io.qase.client.v1.ApiClient;
import io.qase.client.v1.ApiException;
import io.qase.client.v1.Configuration;
import io.qase.client.v1.auth.*;
import io.qase.client.v1.models.*;
import io.qase.client.v1.api.DefectsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.qase.io/v1");
    
    // Configure API key authorization: TokenAuth
    ApiKeyAuth TokenAuth = (ApiKeyAuth) defaultClient.getAuthentication("TokenAuth");
    TokenAuth.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //TokenAuth.setApiKeyPrefix("Token");

    DefectsApi apiInstance = new DefectsApi(defaultClient);
    String code = "code_example"; // String | Code of project, where to search entities.
    Integer id = 56; // Integer | Identifier.
    try {
      IdResponse result = apiInstance.resolveDefect(code, id);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefectsApi#resolveDefect");
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

### Return type

[**IdResponse**](IdResponse.md)

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
| **422** | Unprocessable Entity. |  -  |
| **429** | Too Many Requests. |  -  |

<a id="updateDefect"></a>
# **updateDefect**
> IdResponse updateDefect(code, id, defectUpdate)

Update defect

This method updates a defect. 

### Example
```java
// Import classes:
import io.qase.client.v1.ApiClient;
import io.qase.client.v1.ApiException;
import io.qase.client.v1.Configuration;
import io.qase.client.v1.auth.*;
import io.qase.client.v1.models.*;
import io.qase.client.v1.api.DefectsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.qase.io/v1");
    
    // Configure API key authorization: TokenAuth
    ApiKeyAuth TokenAuth = (ApiKeyAuth) defaultClient.getAuthentication("TokenAuth");
    TokenAuth.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //TokenAuth.setApiKeyPrefix("Token");

    DefectsApi apiInstance = new DefectsApi(defaultClient);
    String code = "code_example"; // String | Code of project, where to search entities.
    Integer id = 56; // Integer | Identifier.
    DefectUpdate defectUpdate = new DefectUpdate(); // DefectUpdate | 
    try {
      IdResponse result = apiInstance.updateDefect(code, id, defectUpdate);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefectsApi#updateDefect");
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
| **defectUpdate** | [**DefectUpdate**](DefectUpdate.md)|  | |

### Return type

[**IdResponse**](IdResponse.md)

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

<a id="updateDefectStatus"></a>
# **updateDefectStatus**
> BaseResponse updateDefectStatus(code, id, defectStatus)

Update a specific defect status

This method allows to update a specific defect status. 

### Example
```java
// Import classes:
import io.qase.client.v1.ApiClient;
import io.qase.client.v1.ApiException;
import io.qase.client.v1.Configuration;
import io.qase.client.v1.auth.*;
import io.qase.client.v1.models.*;
import io.qase.client.v1.api.DefectsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.qase.io/v1");
    
    // Configure API key authorization: TokenAuth
    ApiKeyAuth TokenAuth = (ApiKeyAuth) defaultClient.getAuthentication("TokenAuth");
    TokenAuth.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //TokenAuth.setApiKeyPrefix("Token");

    DefectsApi apiInstance = new DefectsApi(defaultClient);
    String code = "code_example"; // String | Code of project, where to search entities.
    Integer id = 56; // Integer | Identifier.
    DefectStatus defectStatus = new DefectStatus(); // DefectStatus | 
    try {
      BaseResponse result = apiInstance.updateDefectStatus(code, id, defectStatus);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefectsApi#updateDefectStatus");
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
| **defectStatus** | [**DefectStatus**](DefectStatus.md)|  | |

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
| **422** | Unprocessable Entity. |  -  |
| **429** | Too Many Requests. |  -  |

