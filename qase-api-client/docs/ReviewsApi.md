# ReviewsApi

All URIs are relative to *https://api.qase.io/v1*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**bulkCreateReviews**](ReviewsApi.md#bulkCreateReviews) | **POST** /review/{code}/bulk | Create reviews in bulk |
| [**createReview**](ReviewsApi.md#createReview) | **POST** /review/{code} | Create a new review |
| [**deleteReview**](ReviewsApi.md#deleteReview) | **DELETE** /review/{code}/{id} | Delete review |
| [**getReview**](ReviewsApi.md#getReview) | **GET** /review/{code}/{id} | Get a specific review |
| [**getReviews**](ReviewsApi.md#getReviews) | **GET** /review/{code} | Get all reviews |
| [**updateReview**](ReviewsApi.md#updateReview) | **PATCH** /review/{code}/{id} | Update review |


<a id="bulkCreateReviews"></a>
# **bulkCreateReviews**
> ReviewBulkResponse bulkCreateReviews(code, reviewBulk)

Create reviews in bulk

This method allows to submit multiple test cases for review in one request.  Returns an error if test case review is disabled in the project settings. 

### Example
```java
// Import classes:
import io.qase.client.v1.ApiClient;
import io.qase.client.v1.ApiException;
import io.qase.client.v1.Configuration;
import io.qase.client.v1.auth.*;
import io.qase.client.v1.models.*;
import io.qase.client.v1.api.ReviewsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.qase.io/v1");
    
    // Configure API key authorization: TokenAuth
    ApiKeyAuth TokenAuth = (ApiKeyAuth) defaultClient.getAuthentication("TokenAuth");
    TokenAuth.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //TokenAuth.setApiKeyPrefix("Token");

    ReviewsApi apiInstance = new ReviewsApi(defaultClient);
    String code = "code_example"; // String | Code of project, where to search entities.
    ReviewBulk reviewBulk = new ReviewBulk(); // ReviewBulk | 
    try {
      ReviewBulkResponse result = apiInstance.bulkCreateReviews(code, reviewBulk);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ReviewsApi#bulkCreateReviews");
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
| **reviewBulk** | [**ReviewBulk**](ReviewBulk.md)|  | |

### Return type

[**ReviewBulkResponse**](ReviewBulkResponse.md)

### Authorization

[TokenAuth](../README.md#TokenAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Per-item outcomes for the submitted reviews. |  -  |
| **400** | Bad Request. |  -  |
| **401** | Unauthorized. |  -  |
| **402** | Payment Required. |  -  |
| **403** | Forbidden. |  -  |
| **404** | Not Found. |  -  |
| **422** | Unprocessable Entity. |  -  |
| **429** | Too Many Requests. |  -  |

<a id="createReview"></a>
# **createReview**
> IdResponse createReview(code, reviewCreate)

Create a new review

This method allows to submit a test case for review in selected project.  Returns an error if test case review is disabled in the project settings. 

### Example
```java
// Import classes:
import io.qase.client.v1.ApiClient;
import io.qase.client.v1.ApiException;
import io.qase.client.v1.Configuration;
import io.qase.client.v1.auth.*;
import io.qase.client.v1.models.*;
import io.qase.client.v1.api.ReviewsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.qase.io/v1");
    
    // Configure API key authorization: TokenAuth
    ApiKeyAuth TokenAuth = (ApiKeyAuth) defaultClient.getAuthentication("TokenAuth");
    TokenAuth.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //TokenAuth.setApiKeyPrefix("Token");

    ReviewsApi apiInstance = new ReviewsApi(defaultClient);
    String code = "code_example"; // String | Code of project, where to search entities.
    ReviewCreate reviewCreate = new ReviewCreate(); // ReviewCreate | 
    try {
      IdResponse result = apiInstance.createReview(code, reviewCreate);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ReviewsApi#createReview");
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
| **reviewCreate** | [**ReviewCreate**](ReviewCreate.md)|  | |

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
| **402** | Payment Required. |  -  |
| **403** | Forbidden. |  -  |
| **404** | Not Found. |  -  |
| **422** | Unprocessable Entity. |  -  |
| **429** | Too Many Requests. |  -  |

<a id="deleteReview"></a>
# **deleteReview**
> IdResponse deleteReview(code, id)

Delete review

This method allows to delete a review. Merged reviews cannot be deleted.  Returns an error if test case review is disabled in the project settings. 

### Example
```java
// Import classes:
import io.qase.client.v1.ApiClient;
import io.qase.client.v1.ApiException;
import io.qase.client.v1.Configuration;
import io.qase.client.v1.auth.*;
import io.qase.client.v1.models.*;
import io.qase.client.v1.api.ReviewsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.qase.io/v1");
    
    // Configure API key authorization: TokenAuth
    ApiKeyAuth TokenAuth = (ApiKeyAuth) defaultClient.getAuthentication("TokenAuth");
    TokenAuth.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //TokenAuth.setApiKeyPrefix("Token");

    ReviewsApi apiInstance = new ReviewsApi(defaultClient);
    String code = "code_example"; // String | Code of project, where to search entities.
    Integer id = 56; // Integer | Identifier.
    try {
      IdResponse result = apiInstance.deleteReview(code, id);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ReviewsApi#deleteReview");
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
| **402** | Payment Required. |  -  |
| **403** | Forbidden. |  -  |
| **404** | Not Found. |  -  |
| **422** | Unprocessable Entity. |  -  |
| **429** | Too Many Requests. |  -  |

<a id="getReview"></a>
# **getReview**
> ReviewResponse getReview(code, id)

Get a specific review

This method allows to retrieve a specific review, including its current approval status per reviewer. 

### Example
```java
// Import classes:
import io.qase.client.v1.ApiClient;
import io.qase.client.v1.ApiException;
import io.qase.client.v1.Configuration;
import io.qase.client.v1.auth.*;
import io.qase.client.v1.models.*;
import io.qase.client.v1.api.ReviewsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.qase.io/v1");
    
    // Configure API key authorization: TokenAuth
    ApiKeyAuth TokenAuth = (ApiKeyAuth) defaultClient.getAuthentication("TokenAuth");
    TokenAuth.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //TokenAuth.setApiKeyPrefix("Token");

    ReviewsApi apiInstance = new ReviewsApi(defaultClient);
    String code = "code_example"; // String | Code of project, where to search entities.
    Integer id = 56; // Integer | Identifier.
    try {
      ReviewResponse result = apiInstance.getReview(code, id);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ReviewsApi#getReview");
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

[**ReviewResponse**](ReviewResponse.md)

### Authorization

[TokenAuth](../README.md#TokenAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | A Review. |  -  |
| **400** | Bad Request. |  -  |
| **401** | Unauthorized. |  -  |
| **402** | Payment Required. |  -  |
| **403** | Forbidden. |  -  |
| **404** | Not Found. |  -  |
| **429** | Too Many Requests. |  -  |

<a id="getReviews"></a>
# **getReviews**
> ReviewListResponse getReviews(code, status, type, caseId, authorUuid, reviewerUuid, search, limit, offset)

Get all reviews

This method allows to retrieve all test case reviews stored in selected project. 

### Example
```java
// Import classes:
import io.qase.client.v1.ApiClient;
import io.qase.client.v1.ApiException;
import io.qase.client.v1.Configuration;
import io.qase.client.v1.auth.*;
import io.qase.client.v1.models.*;
import io.qase.client.v1.api.ReviewsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.qase.io/v1");
    
    // Configure API key authorization: TokenAuth
    ApiKeyAuth TokenAuth = (ApiKeyAuth) defaultClient.getAuthentication("TokenAuth");
    TokenAuth.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //TokenAuth.setApiKeyPrefix("Token");

    ReviewsApi apiInstance = new ReviewsApi(defaultClient);
    String code = "code_example"; // String | Code of project, where to search entities.
    String status = "open"; // String | 
    String type = "create"; // String | 
    Long caseId = 56L; // Long | Filter reviews by the reviewed test case ID.
    UUID authorUuid = UUID.randomUUID(); // UUID | Filter reviews by the author who created them (author UUID).
    UUID reviewerUuid = UUID.randomUUID(); // UUID | Filter reviews by an assigned reviewer (author UUID).
    String search = "search_example"; // String | Provide a string that will be used to search by review title.
    Integer limit = 10; // Integer | A number of entities in result set.
    Integer offset = 0; // Integer | How many entities should be skipped.
    try {
      ReviewListResponse result = apiInstance.getReviews(code, status, type, caseId, authorUuid, reviewerUuid, search, limit, offset);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ReviewsApi#getReviews");
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
| **status** | **String**|  | [optional] [enum: open, merged, declined] |
| **type** | **String**|  | [optional] [enum: create, edit] |
| **caseId** | **Long**| Filter reviews by the reviewed test case ID. | [optional] |
| **authorUuid** | **UUID**| Filter reviews by the author who created them (author UUID). | [optional] |
| **reviewerUuid** | **UUID**| Filter reviews by an assigned reviewer (author UUID). | [optional] |
| **search** | **String**| Provide a string that will be used to search by review title. | [optional] |
| **limit** | **Integer**| A number of entities in result set. | [optional] [default to 10] |
| **offset** | **Integer**| How many entities should be skipped. | [optional] [default to 0] |

### Return type

[**ReviewListResponse**](ReviewListResponse.md)

### Authorization

[TokenAuth](../README.md#TokenAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | A list of all reviews. |  -  |
| **400** | Bad Request. |  -  |
| **401** | Unauthorized. |  -  |
| **402** | Payment Required. |  -  |
| **403** | Forbidden. |  -  |
| **404** | Not Found. |  -  |
| **429** | Too Many Requests. |  -  |

<a id="updateReview"></a>
# **updateReview**
> IdResponse updateReview(code, id, reviewUpdate)

Update review

This method allows to update the assigned reviewers and/or the proposed test case payload of an open review. The reviewed test case cannot be changed.  Returns an error if test case review is disabled in the project settings, or if the review is not open. 

### Example
```java
// Import classes:
import io.qase.client.v1.ApiClient;
import io.qase.client.v1.ApiException;
import io.qase.client.v1.Configuration;
import io.qase.client.v1.auth.*;
import io.qase.client.v1.models.*;
import io.qase.client.v1.api.ReviewsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.qase.io/v1");
    
    // Configure API key authorization: TokenAuth
    ApiKeyAuth TokenAuth = (ApiKeyAuth) defaultClient.getAuthentication("TokenAuth");
    TokenAuth.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //TokenAuth.setApiKeyPrefix("Token");

    ReviewsApi apiInstance = new ReviewsApi(defaultClient);
    String code = "code_example"; // String | Code of project, where to search entities.
    Integer id = 56; // Integer | Identifier.
    ReviewUpdate reviewUpdate = new ReviewUpdate(); // ReviewUpdate | 
    try {
      IdResponse result = apiInstance.updateReview(code, id, reviewUpdate);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ReviewsApi#updateReview");
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
| **reviewUpdate** | [**ReviewUpdate**](ReviewUpdate.md)|  | |

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
| **402** | Payment Required. |  -  |
| **403** | Forbidden. |  -  |
| **404** | Not Found. |  -  |
| **422** | Unprocessable Entity. |  -  |
| **429** | Too Many Requests. |  -  |

