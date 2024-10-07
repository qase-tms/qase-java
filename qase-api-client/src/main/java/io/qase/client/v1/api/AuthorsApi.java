/*
 * Qase.io TestOps API v1
 * Qase TestOps API v1 Specification.
 *
 * The version of the OpenAPI document: 1.0.0
 * Contact: support@qase.io
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package io.qase.client.v1.api;

import io.qase.client.v1.ApiCallback;
import io.qase.client.v1.ApiClient;
import io.qase.client.v1.ApiException;
import io.qase.client.v1.ApiResponse;
import io.qase.client.v1.Configuration;
import io.qase.client.v1.Pair;
import io.qase.client.v1.ProgressRequestBody;
import io.qase.client.v1.ProgressResponseBody;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;


import io.qase.client.v1.models.AuthorListResponse;
import io.qase.client.v1.models.AuthorResponse;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthorsApi {
    private ApiClient localVarApiClient;
    private int localHostIndex;
    private String localCustomBaseUrl;

    public AuthorsApi() {
        this(Configuration.getDefaultApiClient());
    }

    public AuthorsApi(ApiClient apiClient) {
        this.localVarApiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return localVarApiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.localVarApiClient = apiClient;
    }

    public int getHostIndex() {
        return localHostIndex;
    }

    public void setHostIndex(int hostIndex) {
        this.localHostIndex = hostIndex;
    }

    public String getCustomBaseUrl() {
        return localCustomBaseUrl;
    }

    public void setCustomBaseUrl(String customBaseUrl) {
        this.localCustomBaseUrl = customBaseUrl;
    }

    /**
     * Build call for getAuthor
     * @param id Identifier. (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> An author. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request. </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized. </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden. </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found. </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Too Many Requests. </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAuthorCall(Integer id, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/author/{id}"
            .replace("{" + "id" + "}", localVarApiClient.escapeString(id.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "TokenAuth" };
        return localVarApiClient.buildCall(basePath, localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getAuthorValidateBeforeCall(Integer id, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling getAuthor(Async)");
        }

        return getAuthorCall(id, _callback);

    }

    /**
     * Get a specific author
     * This method allows to retrieve a specific author. 
     * @param id Identifier. (required)
     * @return AuthorResponse
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> An author. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request. </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized. </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden. </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found. </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Too Many Requests. </td><td>  -  </td></tr>
     </table>
     */
    public AuthorResponse getAuthor(Integer id) throws ApiException {
        ApiResponse<AuthorResponse> localVarResp = getAuthorWithHttpInfo(id);
        return localVarResp.getData();
    }

    /**
     * Get a specific author
     * This method allows to retrieve a specific author. 
     * @param id Identifier. (required)
     * @return ApiResponse&lt;AuthorResponse&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> An author. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request. </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized. </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden. </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found. </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Too Many Requests. </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<AuthorResponse> getAuthorWithHttpInfo(Integer id) throws ApiException {
        okhttp3.Call localVarCall = getAuthorValidateBeforeCall(id, null);
        Type localVarReturnType = new TypeToken<AuthorResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get a specific author (asynchronously)
     * This method allows to retrieve a specific author. 
     * @param id Identifier. (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> An author. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request. </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized. </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden. </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found. </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Too Many Requests. </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAuthorAsync(Integer id, final ApiCallback<AuthorResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = getAuthorValidateBeforeCall(id, _callback);
        Type localVarReturnType = new TypeToken<AuthorResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getAuthors
     * @param search Provide a string that will be used to search by name. (optional)
     * @param type  (optional)
     * @param limit A number of entities in result set. (optional, default to 10)
     * @param offset How many entities should be skipped. (optional, default to 0)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Author list. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request. </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized. </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden. </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found. </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Too Many Requests. </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAuthorsCall(String search, String type, Integer limit, Integer offset, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/author";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (search != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("search", search));
        }

        if (type != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("type", type));
        }

        if (limit != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("limit", limit));
        }

        if (offset != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("offset", offset));
        }

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "TokenAuth" };
        return localVarApiClient.buildCall(basePath, localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getAuthorsValidateBeforeCall(String search, String type, Integer limit, Integer offset, final ApiCallback _callback) throws ApiException {
        return getAuthorsCall(search, type, limit, offset, _callback);

    }

    /**
     * Get all authors
     * This method allows to retrieve all authors in selected project. 
     * @param search Provide a string that will be used to search by name. (optional)
     * @param type  (optional)
     * @param limit A number of entities in result set. (optional, default to 10)
     * @param offset How many entities should be skipped. (optional, default to 0)
     * @return AuthorListResponse
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Author list. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request. </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized. </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden. </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found. </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Too Many Requests. </td><td>  -  </td></tr>
     </table>
     */
    public AuthorListResponse getAuthors(String search, String type, Integer limit, Integer offset) throws ApiException {
        ApiResponse<AuthorListResponse> localVarResp = getAuthorsWithHttpInfo(search, type, limit, offset);
        return localVarResp.getData();
    }

    /**
     * Get all authors
     * This method allows to retrieve all authors in selected project. 
     * @param search Provide a string that will be used to search by name. (optional)
     * @param type  (optional)
     * @param limit A number of entities in result set. (optional, default to 10)
     * @param offset How many entities should be skipped. (optional, default to 0)
     * @return ApiResponse&lt;AuthorListResponse&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Author list. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request. </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized. </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden. </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found. </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Too Many Requests. </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<AuthorListResponse> getAuthorsWithHttpInfo(String search, String type, Integer limit, Integer offset) throws ApiException {
        okhttp3.Call localVarCall = getAuthorsValidateBeforeCall(search, type, limit, offset, null);
        Type localVarReturnType = new TypeToken<AuthorListResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get all authors (asynchronously)
     * This method allows to retrieve all authors in selected project. 
     * @param search Provide a string that will be used to search by name. (optional)
     * @param type  (optional)
     * @param limit A number of entities in result set. (optional, default to 10)
     * @param offset How many entities should be skipped. (optional, default to 0)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Author list. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request. </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized. </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden. </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Not Found. </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Too Many Requests. </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAuthorsAsync(String search, String type, Integer limit, Integer offset, final ApiCallback<AuthorListResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = getAuthorsValidateBeforeCall(search, type, limit, offset, _callback);
        Type localVarReturnType = new TypeToken<AuthorListResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
}
