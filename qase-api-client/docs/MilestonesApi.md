# MilestonesApi

All URIs are relative to *https://api.qase.io/v1*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createMilestone**](MilestonesApi.md#createMilestone) | **POST** /milestone/{code} | Create a new milestone |
| [**deleteMilestone**](MilestonesApi.md#deleteMilestone) | **DELETE** /milestone/{code}/{id} | Delete milestone |
| [**getMilestone**](MilestonesApi.md#getMilestone) | **GET** /milestone/{code}/{id} | Get a specific milestone |
| [**getMilestones**](MilestonesApi.md#getMilestones) | **GET** /milestone/{code} | Get all milestones |
| [**updateMilestone**](MilestonesApi.md#updateMilestone) | **PATCH** /milestone/{code}/{id} | Update milestone |


<a id="createMilestone"></a>
# **createMilestone**
> IdResponse createMilestone(code, milestoneCreate)

Create a new milestone

This method allows to create a milestone in selected project. 

### Example
```java
// Import classes:
import io.qase.client.v1.ApiClient;
import io.qase.client.v1.ApiException;
import io.qase.client.v1.Configuration;
import io.qase.client.v1.auth.*;
import io.qase.client.v1.models.*;
import io.qase.client.v1.api.MilestonesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.qase.io/v1");
    
    // Configure API key authorization: TokenAuth
    ApiKeyAuth TokenAuth = (ApiKeyAuth) defaultClient.getAuthentication("TokenAuth");
    TokenAuth.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //TokenAuth.setApiKeyPrefix("Token");

    MilestonesApi apiInstance = new MilestonesApi(defaultClient);
    String code = "code_example"; // String | Code of project, where to search entities.
    MilestoneCreate milestoneCreate = new MilestoneCreate(); // MilestoneCreate | 
    try {
      IdResponse result = apiInstance.createMilestone(code, milestoneCreate);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling MilestonesApi#createMilestone");
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
| **milestoneCreate** | [**MilestoneCreate**](MilestoneCreate.md)|  | |

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

<a id="deleteMilestone"></a>
# **deleteMilestone**
> IdResponse deleteMilestone(code, id)

Delete milestone

This method completely deletes a milestone from repository. 

### Example
```java
// Import classes:
import io.qase.client.v1.ApiClient;
import io.qase.client.v1.ApiException;
import io.qase.client.v1.Configuration;
import io.qase.client.v1.auth.*;
import io.qase.client.v1.models.*;
import io.qase.client.v1.api.MilestonesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.qase.io/v1");
    
    // Configure API key authorization: TokenAuth
    ApiKeyAuth TokenAuth = (ApiKeyAuth) defaultClient.getAuthentication("TokenAuth");
    TokenAuth.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //TokenAuth.setApiKeyPrefix("Token");

    MilestonesApi apiInstance = new MilestonesApi(defaultClient);
    String code = "code_example"; // String | Code of project, where to search entities.
    Integer id = 56; // Integer | Identifier.
    try {
      IdResponse result = apiInstance.deleteMilestone(code, id);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling MilestonesApi#deleteMilestone");
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

<a id="getMilestone"></a>
# **getMilestone**
> MilestoneResponse getMilestone(code, id)

Get a specific milestone

This method allows to retrieve a specific milestone. 

### Example
```java
// Import classes:
import io.qase.client.v1.ApiClient;
import io.qase.client.v1.ApiException;
import io.qase.client.v1.Configuration;
import io.qase.client.v1.auth.*;
import io.qase.client.v1.models.*;
import io.qase.client.v1.api.MilestonesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.qase.io/v1");
    
    // Configure API key authorization: TokenAuth
    ApiKeyAuth TokenAuth = (ApiKeyAuth) defaultClient.getAuthentication("TokenAuth");
    TokenAuth.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //TokenAuth.setApiKeyPrefix("Token");

    MilestonesApi apiInstance = new MilestonesApi(defaultClient);
    String code = "code_example"; // String | Code of project, where to search entities.
    Integer id = 56; // Integer | Identifier.
    try {
      MilestoneResponse result = apiInstance.getMilestone(code, id);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling MilestonesApi#getMilestone");
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

[**MilestoneResponse**](MilestoneResponse.md)

### Authorization

[TokenAuth](../README.md#TokenAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | A Milestone. |  -  |
| **400** | Bad Request. |  -  |
| **401** | Unauthorized. |  -  |
| **403** | Forbidden. |  -  |
| **404** | Not Found. |  -  |
| **429** | Too Many Requests. |  -  |

<a id="getMilestones"></a>
# **getMilestones**
> MilestoneListResponse getMilestones(code, search, limit, offset)

Get all milestones

This method allows to retrieve all milestones stored in selected project. 

### Example
```java
// Import classes:
import io.qase.client.v1.ApiClient;
import io.qase.client.v1.ApiException;
import io.qase.client.v1.Configuration;
import io.qase.client.v1.auth.*;
import io.qase.client.v1.models.*;
import io.qase.client.v1.api.MilestonesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.qase.io/v1");
    
    // Configure API key authorization: TokenAuth
    ApiKeyAuth TokenAuth = (ApiKeyAuth) defaultClient.getAuthentication("TokenAuth");
    TokenAuth.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //TokenAuth.setApiKeyPrefix("Token");

    MilestonesApi apiInstance = new MilestonesApi(defaultClient);
    String code = "code_example"; // String | Code of project, where to search entities.
    String search = "search_example"; // String | Provide a string that will be used to search by name.
    Integer limit = 10; // Integer | A number of entities in result set.
    Integer offset = 0; // Integer | How many entities should be skipped.
    try {
      MilestoneListResponse result = apiInstance.getMilestones(code, search, limit, offset);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling MilestonesApi#getMilestones");
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

[**MilestoneListResponse**](MilestoneListResponse.md)

### Authorization

[TokenAuth](../README.md#TokenAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | A list of all milestones. |  -  |
| **400** | Bad Request. |  -  |
| **401** | Unauthorized. |  -  |
| **403** | Forbidden. |  -  |
| **404** | Not Found. |  -  |
| **429** | Too Many Requests. |  -  |

<a id="updateMilestone"></a>
# **updateMilestone**
> IdResponse updateMilestone(code, id, milestoneUpdate)

Update milestone

This method updates a milestone. 

### Example
```java
// Import classes:
import io.qase.client.v1.ApiClient;
import io.qase.client.v1.ApiException;
import io.qase.client.v1.Configuration;
import io.qase.client.v1.auth.*;
import io.qase.client.v1.models.*;
import io.qase.client.v1.api.MilestonesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.qase.io/v1");
    
    // Configure API key authorization: TokenAuth
    ApiKeyAuth TokenAuth = (ApiKeyAuth) defaultClient.getAuthentication("TokenAuth");
    TokenAuth.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //TokenAuth.setApiKeyPrefix("Token");

    MilestonesApi apiInstance = new MilestonesApi(defaultClient);
    String code = "code_example"; // String | Code of project, where to search entities.
    Integer id = 56; // Integer | Identifier.
    MilestoneUpdate milestoneUpdate = new MilestoneUpdate(); // MilestoneUpdate | 
    try {
      IdResponse result = apiInstance.updateMilestone(code, id, milestoneUpdate);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling MilestonesApi#updateMilestone");
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
| **milestoneUpdate** | [**MilestoneUpdate**](MilestoneUpdate.md)|  | |

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

