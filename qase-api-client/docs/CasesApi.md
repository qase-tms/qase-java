# CasesApi

All URIs are relative to *https://api.qase.io/v1*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**bulk**](CasesApi.md#bulk) | **POST** /case/{code}/bulk | Create test cases in bulk |
| [**caseAttachExternalIssue**](CasesApi.md#caseAttachExternalIssue) | **POST** /case/{code}/external-issue/attach | Attach the external issues to the test cases |
| [**caseDetachExternalIssue**](CasesApi.md#caseDetachExternalIssue) | **POST** /case/{code}/external-issue/detach | Detach the external issues from the test cases |
| [**createCase**](CasesApi.md#createCase) | **POST** /case/{code} | Create a new test case |
| [**deleteCase**](CasesApi.md#deleteCase) | **DELETE** /case/{code}/{id} | Delete test case |
| [**getCase**](CasesApi.md#getCase) | **GET** /case/{code}/{id} | Get a specific test case |
| [**getCases**](CasesApi.md#getCases) | **GET** /case/{code} | Get all test cases |
| [**updateCase**](CasesApi.md#updateCase) | **PATCH** /case/{code}/{id} | Update test case |


<a id="bulk"></a>
# **bulk**
> Bulk200Response bulk(code, testCasebulk)

Create test cases in bulk

This method allows to bulk create new test cases in a project. 

### Example
```java
// Import classes:
import io.qase.client.v1.ApiClient;
import io.qase.client.v1.ApiException;
import io.qase.client.v1.Configuration;
import io.qase.client.v1.auth.*;
import io.qase.client.v1.models.*;
import io.qase.client.v1.api.CasesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.qase.io/v1");
    
    // Configure API key authorization: TokenAuth
    ApiKeyAuth TokenAuth = (ApiKeyAuth) defaultClient.getAuthentication("TokenAuth");
    TokenAuth.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //TokenAuth.setApiKeyPrefix("Token");

    CasesApi apiInstance = new CasesApi(defaultClient);
    String code = "code_example"; // String | Code of project, where to search entities.
    TestCasebulk testCasebulk = new TestCasebulk(); // TestCasebulk | 
    try {
      Bulk200Response result = apiInstance.bulk(code, testCasebulk);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling CasesApi#bulk");
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
| **testCasebulk** | [**TestCasebulk**](TestCasebulk.md)|  | |

### Return type

[**Bulk200Response**](Bulk200Response.md)

### Authorization

[TokenAuth](../README.md#TokenAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | List of IDs of the created cases. |  -  |
| **400** | Bad Request. |  -  |
| **401** | Unauthorized. |  -  |
| **403** | Forbidden. |  -  |
| **404** | Not Found. |  -  |
| **422** | Unprocessable Entity. |  -  |
| **429** | Too Many Requests. |  -  |

<a id="caseAttachExternalIssue"></a>
# **caseAttachExternalIssue**
> BaseResponse caseAttachExternalIssue(code, testCaseExternalIssues)

Attach the external issues to the test cases

### Example
```java
// Import classes:
import io.qase.client.v1.ApiClient;
import io.qase.client.v1.ApiException;
import io.qase.client.v1.Configuration;
import io.qase.client.v1.auth.*;
import io.qase.client.v1.models.*;
import io.qase.client.v1.api.CasesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.qase.io/v1");
    
    // Configure API key authorization: TokenAuth
    ApiKeyAuth TokenAuth = (ApiKeyAuth) defaultClient.getAuthentication("TokenAuth");
    TokenAuth.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //TokenAuth.setApiKeyPrefix("Token");

    CasesApi apiInstance = new CasesApi(defaultClient);
    String code = "code_example"; // String | Code of project, where to search entities.
    TestCaseExternalIssues testCaseExternalIssues = new TestCaseExternalIssues(); // TestCaseExternalIssues | 
    try {
      BaseResponse result = apiInstance.caseAttachExternalIssue(code, testCaseExternalIssues);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling CasesApi#caseAttachExternalIssue");
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
| **testCaseExternalIssues** | [**TestCaseExternalIssues**](TestCaseExternalIssues.md)|  | |

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
| **200** | OK. |  -  |
| **400** | Bad Request. |  -  |
| **401** | Unauthorized. |  -  |
| **402** | Payment Required. |  -  |
| **403** | Forbidden. |  -  |
| **404** | Not Found. |  -  |
| **422** | Unprocessable Entity. |  -  |
| **429** | Too Many Requests. |  -  |

<a id="caseDetachExternalIssue"></a>
# **caseDetachExternalIssue**
> BaseResponse caseDetachExternalIssue(code, testCaseExternalIssues)

Detach the external issues from the test cases

### Example
```java
// Import classes:
import io.qase.client.v1.ApiClient;
import io.qase.client.v1.ApiException;
import io.qase.client.v1.Configuration;
import io.qase.client.v1.auth.*;
import io.qase.client.v1.models.*;
import io.qase.client.v1.api.CasesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.qase.io/v1");
    
    // Configure API key authorization: TokenAuth
    ApiKeyAuth TokenAuth = (ApiKeyAuth) defaultClient.getAuthentication("TokenAuth");
    TokenAuth.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //TokenAuth.setApiKeyPrefix("Token");

    CasesApi apiInstance = new CasesApi(defaultClient);
    String code = "code_example"; // String | Code of project, where to search entities.
    TestCaseExternalIssues testCaseExternalIssues = new TestCaseExternalIssues(); // TestCaseExternalIssues | 
    try {
      BaseResponse result = apiInstance.caseDetachExternalIssue(code, testCaseExternalIssues);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling CasesApi#caseDetachExternalIssue");
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
| **testCaseExternalIssues** | [**TestCaseExternalIssues**](TestCaseExternalIssues.md)|  | |

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
| **200** | OK. |  -  |
| **400** | Bad Request. |  -  |
| **401** | Unauthorized. |  -  |
| **402** | Payment Required. |  -  |
| **403** | Forbidden. |  -  |
| **404** | Not Found. |  -  |
| **422** | Unprocessable Entity. |  -  |
| **429** | Too Many Requests. |  -  |

<a id="createCase"></a>
# **createCase**
> IdResponse createCase(code, testCaseCreate)

Create a new test case

This method allows to create a new test case in selected project. 

### Example
```java
// Import classes:
import io.qase.client.v1.ApiClient;
import io.qase.client.v1.ApiException;
import io.qase.client.v1.Configuration;
import io.qase.client.v1.auth.*;
import io.qase.client.v1.models.*;
import io.qase.client.v1.api.CasesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.qase.io/v1");
    
    // Configure API key authorization: TokenAuth
    ApiKeyAuth TokenAuth = (ApiKeyAuth) defaultClient.getAuthentication("TokenAuth");
    TokenAuth.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //TokenAuth.setApiKeyPrefix("Token");

    CasesApi apiInstance = new CasesApi(defaultClient);
    String code = "code_example"; // String | Code of project, where to search entities.
    TestCaseCreate testCaseCreate = new TestCaseCreate(); // TestCaseCreate | 
    try {
      IdResponse result = apiInstance.createCase(code, testCaseCreate);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling CasesApi#createCase");
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
| **testCaseCreate** | [**TestCaseCreate**](TestCaseCreate.md)|  | |

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

<a id="deleteCase"></a>
# **deleteCase**
> IdResponse deleteCase(code, id)

Delete test case

This method completely deletes a test case from repository. 

### Example
```java
// Import classes:
import io.qase.client.v1.ApiClient;
import io.qase.client.v1.ApiException;
import io.qase.client.v1.Configuration;
import io.qase.client.v1.auth.*;
import io.qase.client.v1.models.*;
import io.qase.client.v1.api.CasesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.qase.io/v1");
    
    // Configure API key authorization: TokenAuth
    ApiKeyAuth TokenAuth = (ApiKeyAuth) defaultClient.getAuthentication("TokenAuth");
    TokenAuth.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //TokenAuth.setApiKeyPrefix("Token");

    CasesApi apiInstance = new CasesApi(defaultClient);
    String code = "code_example"; // String | Code of project, where to search entities.
    Integer id = 56; // Integer | Identifier.
    try {
      IdResponse result = apiInstance.deleteCase(code, id);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling CasesApi#deleteCase");
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
| **200** | A Test Case. |  -  |
| **400** | Bad Request. |  -  |
| **401** | Unauthorized. |  -  |
| **403** | Forbidden. |  -  |
| **404** | Not Found. |  -  |
| **422** | Unprocessable Entity. |  -  |
| **429** | Too Many Requests. |  -  |

<a id="getCase"></a>
# **getCase**
> TestCaseResponse getCase(code, id, include)

Get a specific test case

This method allows to retrieve a specific test case. 

### Example
```java
// Import classes:
import io.qase.client.v1.ApiClient;
import io.qase.client.v1.ApiException;
import io.qase.client.v1.Configuration;
import io.qase.client.v1.auth.*;
import io.qase.client.v1.models.*;
import io.qase.client.v1.api.CasesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.qase.io/v1");
    
    // Configure API key authorization: TokenAuth
    ApiKeyAuth TokenAuth = (ApiKeyAuth) defaultClient.getAuthentication("TokenAuth");
    TokenAuth.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //TokenAuth.setApiKeyPrefix("Token");

    CasesApi apiInstance = new CasesApi(defaultClient);
    String code = "code_example"; // String | Code of project, where to search entities.
    Integer id = 56; // Integer | Identifier.
    String include = "include_example"; // String | A list of entities to include in response separated by comma. Possible values: external_issues. 
    try {
      TestCaseResponse result = apiInstance.getCase(code, id, include);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling CasesApi#getCase");
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
| **include** | **String**| A list of entities to include in response separated by comma. Possible values: external_issues.  | [optional] |

### Return type

[**TestCaseResponse**](TestCaseResponse.md)

### Authorization

[TokenAuth](../README.md#TokenAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | A Test Case. |  -  |
| **400** | Bad Request. |  -  |
| **401** | Unauthorized. |  -  |
| **403** | Forbidden. |  -  |
| **404** | Not Found. |  -  |
| **422** | Unprocessable Entity. |  -  |
| **429** | Too Many Requests. |  -  |

<a id="getCases"></a>
# **getCases**
> TestCaseListResponse getCases(code, search, milestoneId, suiteId, severity, priority, type, behavior, automation, status, externalIssuesType, externalIssuesIds, include, limit, offset)

Get all test cases

This method allows to retrieve all test cases stored in selected project. 

### Example
```java
// Import classes:
import io.qase.client.v1.ApiClient;
import io.qase.client.v1.ApiException;
import io.qase.client.v1.Configuration;
import io.qase.client.v1.auth.*;
import io.qase.client.v1.models.*;
import io.qase.client.v1.api.CasesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.qase.io/v1");
    
    // Configure API key authorization: TokenAuth
    ApiKeyAuth TokenAuth = (ApiKeyAuth) defaultClient.getAuthentication("TokenAuth");
    TokenAuth.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //TokenAuth.setApiKeyPrefix("Token");

    CasesApi apiInstance = new CasesApi(defaultClient);
    String code = "code_example"; // String | Code of project, where to search entities.
    String search = "search_example"; // String | Provide a string that will be used to search by name.
    Integer milestoneId = 56; // Integer | ID of milestone.
    Integer suiteId = 56; // Integer | ID of test suite.
    String severity = "severity_example"; // String | A list of severity values separated by comma. Possible values: undefined, blocker, critical, major, normal, minor, trivial 
    String priority = "priority_example"; // String | A list of priority values separated by comma. Possible values: undefined, high, medium, low 
    String type = "type_example"; // String | A list of type values separated by comma. Possible values: other, functional smoke, regression, security, usability, performance, acceptance 
    String behavior = "behavior_example"; // String | A list of behavior values separated by comma. Possible values: undefined, positive negative, destructive 
    String automation = "automation_example"; // String | A list of values separated by comma. Possible values: is-not-automated, automated to-be-automated 
    String status = "status_example"; // String | A list of values separated by comma. Possible values: actual, draft deprecated 
    String externalIssuesType = "asana"; // String | An integration type. 
    List<String> externalIssuesIds = Arrays.asList(); // List<String> | A list of issue IDs.
    String include = "include_example"; // String | A list of entities to include in response separated by comma. Possible values: external_issues. 
    Integer limit = 10; // Integer | A number of entities in result set.
    Integer offset = 0; // Integer | How many entities should be skipped.
    try {
      TestCaseListResponse result = apiInstance.getCases(code, search, milestoneId, suiteId, severity, priority, type, behavior, automation, status, externalIssuesType, externalIssuesIds, include, limit, offset);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling CasesApi#getCases");
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
| **milestoneId** | **Integer**| ID of milestone. | [optional] |
| **suiteId** | **Integer**| ID of test suite. | [optional] |
| **severity** | **String**| A list of severity values separated by comma. Possible values: undefined, blocker, critical, major, normal, minor, trivial  | [optional] |
| **priority** | **String**| A list of priority values separated by comma. Possible values: undefined, high, medium, low  | [optional] |
| **type** | **String**| A list of type values separated by comma. Possible values: other, functional smoke, regression, security, usability, performance, acceptance  | [optional] |
| **behavior** | **String**| A list of behavior values separated by comma. Possible values: undefined, positive negative, destructive  | [optional] |
| **automation** | **String**| A list of values separated by comma. Possible values: is-not-automated, automated to-be-automated  | [optional] |
| **status** | **String**| A list of values separated by comma. Possible values: actual, draft deprecated  | [optional] |
| **externalIssuesType** | **String**| An integration type.  | [optional] [enum: asana, azure-devops, clickup-app, github-app, gitlab-app, jira-cloud, jira-server, linear, monday, redmine-app, trello-app, youtrack-app] |
| **externalIssuesIds** | [**List&lt;String&gt;**](String.md)| A list of issue IDs. | [optional] |
| **include** | **String**| A list of entities to include in response separated by comma. Possible values: external_issues.  | [optional] |
| **limit** | **Integer**| A number of entities in result set. | [optional] [default to 10] |
| **offset** | **Integer**| How many entities should be skipped. | [optional] [default to 0] |

### Return type

[**TestCaseListResponse**](TestCaseListResponse.md)

### Authorization

[TokenAuth](../README.md#TokenAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | A list of all cases. |  -  |
| **400** | Bad Request. |  -  |
| **401** | Unauthorized. |  -  |
| **402** | Payment Required. |  -  |
| **403** | Forbidden. |  -  |
| **404** | Not Found. |  -  |
| **429** | Too Many Requests. |  -  |

<a id="updateCase"></a>
# **updateCase**
> IdResponse updateCase(code, id, testCaseUpdate)

Update test case

This method updates a test case. 

### Example
```java
// Import classes:
import io.qase.client.v1.ApiClient;
import io.qase.client.v1.ApiException;
import io.qase.client.v1.Configuration;
import io.qase.client.v1.auth.*;
import io.qase.client.v1.models.*;
import io.qase.client.v1.api.CasesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.qase.io/v1");
    
    // Configure API key authorization: TokenAuth
    ApiKeyAuth TokenAuth = (ApiKeyAuth) defaultClient.getAuthentication("TokenAuth");
    TokenAuth.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //TokenAuth.setApiKeyPrefix("Token");

    CasesApi apiInstance = new CasesApi(defaultClient);
    String code = "code_example"; // String | Code of project, where to search entities.
    Integer id = 56; // Integer | Identifier.
    TestCaseUpdate testCaseUpdate = new TestCaseUpdate(); // TestCaseUpdate | 
    try {
      IdResponse result = apiInstance.updateCase(code, id, testCaseUpdate);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling CasesApi#updateCase");
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
| **testCaseUpdate** | [**TestCaseUpdate**](TestCaseUpdate.md)|  | |

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
| **200** | A Test Case. |  -  |
| **400** | Bad Request. |  -  |
| **401** | Unauthorized. |  -  |
| **403** | Forbidden. |  -  |
| **404** | Not Found. |  -  |
| **422** | Unprocessable Entity. |  -  |
| **429** | Too Many Requests. |  -  |

