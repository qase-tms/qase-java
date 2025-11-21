# CustomFieldsApi

All URIs are relative to *https://api.qase.io/v2*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**getCustomFieldV2**](CustomFieldsApi.md#getCustomFieldV2) | **GET** /custom_field/{id} | Get Custom Field |
| [**getCustomFieldsV2**](CustomFieldsApi.md#getCustomFieldsV2) | **GET** /custom_field | Get all Custom Fields |


<a id="getCustomFieldV2"></a>
# **getCustomFieldV2**
> CustomFieldResponse getCustomFieldV2(id)

Get Custom Field

This method allows to retrieve custom field. 

### Example
```java
// Import classes:
import io.qase.client.v2.ApiClient;
import io.qase.client.v2.ApiException;
import io.qase.client.v2.Configuration;
import io.qase.client.v2.auth.*;
import io.qase.client.v2.models.*;
import io.qase.client.v2.api.CustomFieldsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.qase.io/v2");
    
    // Configure API key authorization: TokenAuth
    ApiKeyAuth TokenAuth = (ApiKeyAuth) defaultClient.getAuthentication("TokenAuth");
    TokenAuth.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //TokenAuth.setApiKeyPrefix("Token");

    CustomFieldsApi apiInstance = new CustomFieldsApi(defaultClient);
    Integer id = 56; // Integer | Identifier.
    try {
      CustomFieldResponse result = apiInstance.getCustomFieldV2(id);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling CustomFieldsApi#getCustomFieldV2");
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
| **id** | **Integer**| Identifier. | |

### Return type

[**CustomFieldResponse**](CustomFieldResponse.md)

### Authorization

[TokenAuth](../README.md#TokenAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | A Custom Field. |  -  |
| **400** | Bad Request. |  -  |
| **401** | Unauthorized. |  -  |
| **403** | Forbidden. |  -  |
| **404** | Not Found. |  -  |
| **429** | Too Many Requests. |  -  |

<a id="getCustomFieldsV2"></a>
# **getCustomFieldsV2**
> CustomFieldListResponse getCustomFieldsV2(entity, type, limit, offset)

Get all Custom Fields

This method allows to retrieve and filter custom fields. 

### Example
```java
// Import classes:
import io.qase.client.v2.ApiClient;
import io.qase.client.v2.ApiException;
import io.qase.client.v2.Configuration;
import io.qase.client.v2.auth.*;
import io.qase.client.v2.models.*;
import io.qase.client.v2.api.CustomFieldsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.qase.io/v2");
    
    // Configure API key authorization: TokenAuth
    ApiKeyAuth TokenAuth = (ApiKeyAuth) defaultClient.getAuthentication("TokenAuth");
    TokenAuth.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //TokenAuth.setApiKeyPrefix("Token");

    CustomFieldsApi apiInstance = new CustomFieldsApi(defaultClient);
    String entity = "case"; // String | 
    String type = "string"; // String | 
    Integer limit = 10; // Integer | A number of entities in result set.
    Integer offset = 0; // Integer | How many entities should be skipped.
    try {
      CustomFieldListResponse result = apiInstance.getCustomFieldsV2(entity, type, limit, offset);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling CustomFieldsApi#getCustomFieldsV2");
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
| **entity** | **String**|  | [optional] [enum: case, run, defect] |
| **type** | **String**|  | [optional] [enum: string, text, number, checkbox, selectbox, radio, multiselect, url, user, datetime] |
| **limit** | **Integer**| A number of entities in result set. | [optional] [default to 10] |
| **offset** | **Integer**| How many entities should be skipped. | [optional] [default to 0] |

### Return type

[**CustomFieldListResponse**](CustomFieldListResponse.md)

### Authorization

[TokenAuth](../README.md#TokenAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Custom Field list. |  -  |
| **400** | Bad Request. |  -  |
| **401** | Unauthorized. |  -  |
| **403** | Forbidden. |  -  |
| **429** | Too Many Requests. |  -  |

