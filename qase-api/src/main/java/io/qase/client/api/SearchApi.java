/*
 * Qase.io API
 * Qase API Specification.
 *
 * The version of the OpenAPI document: 1.0.0
 * Contact: support@qase.io
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package io.qase.client.api;

import com.google.gson.reflect.TypeToken;
import io.qase.api.exceptions.QaseException;
import io.qase.client.*;
import io.qase.client.model.SearchResponse;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchApi {
    private ApiClient localVarApiClient;
    private int localHostIndex;
    private String localCustomBaseUrl;

    public SearchApi() {
        this(Configuration.getDefaultApiClient());
    }

    public SearchApi(ApiClient apiClient) {
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
     * Build call for search
     * @param query Expression in Qase Query Language. (required)
     * @param limit A number of entities in result set. (optional, default to 10)
     * @param offset How many entities should be skipped. (optional, default to 0)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws QaseException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> A list of found entities. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request. </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized. </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden. </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Too Many Requests. </td><td>  -  </td></tr>
     </table>
     * Find more info about QQL here.
     * @see <a href="https://help.qase.io/hc/en-us/articles/4404615073041">Search entities by Qase Query Language (QQL). Documentation</a>
     */
    public okhttp3.Call searchCall(String query, Integer limit, Integer offset, final ApiCallback _callback) throws QaseException {
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
        String localVarPath = "/search";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (limit != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("limit", limit));
        }

        if (offset != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("offset", offset));
        }

        if (query != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("query", query));
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
    private okhttp3.Call searchValidateBeforeCall(String query, Integer limit, Integer offset, final ApiCallback _callback) throws QaseException {
        // verify the required parameter 'query' is set
        if (query == null) {
            throw new QaseException("Missing the required parameter 'query' when calling search(Async)");
        }

        return searchCall(query, limit, offset, _callback);

    }

    /**
     * Search entities by Qase Query Language (QQL).
     * This method allows to retrieve data sets for various entities using expressions with conditions. 
     * @param query Expression in Qase Query Language. (required)
     * @param limit A number of entities in result set. (optional, default to 10)
     * @param offset How many entities should be skipped. (optional, default to 0)
     * @return SearchResponse
     * @throws QaseException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> A list of found entities. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request. </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized. </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden. </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Too Many Requests. </td><td>  -  </td></tr>
     </table>
     * Find more info about QQL here.
     * @see <a href="https://help.qase.io/hc/en-us/articles/4404615073041">Search entities by Qase Query Language (QQL). Documentation</a>
     */
    public SearchResponse search(String query, Integer limit, Integer offset) throws QaseException {
        ApiResponse<SearchResponse> localVarResp = searchWithHttpInfo(query, limit, offset);
        return localVarResp.getData();
    }

    /**
     * Search entities by Qase Query Language (QQL).
     * This method allows to retrieve data sets for various entities using expressions with conditions. 
     * @param query Expression in Qase Query Language. (required)
     * @param limit A number of entities in result set. (optional, default to 10)
     * @param offset How many entities should be skipped. (optional, default to 0)
     * @return ApiResponse&lt;SearchResponse&gt;
     * @throws QaseException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> A list of found entities. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request. </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized. </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden. </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Too Many Requests. </td><td>  -  </td></tr>
     </table>
     * Find more info about QQL here.
     * @see <a href="https://help.qase.io/hc/en-us/articles/4404615073041">Search entities by Qase Query Language (QQL). Documentation</a>
     */
    public ApiResponse<SearchResponse> searchWithHttpInfo(String query, Integer limit, Integer offset) throws QaseException {
        okhttp3.Call localVarCall = searchValidateBeforeCall(query, limit, offset, null);
        Type localVarReturnType = new TypeToken<SearchResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Search entities by Qase Query Language (QQL). (asynchronously)
     * This method allows to retrieve data sets for various entities using expressions with conditions. 
     * @param query Expression in Qase Query Language. (required)
     * @param limit A number of entities in result set. (optional, default to 10)
     * @param offset How many entities should be skipped. (optional, default to 0)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws QaseException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> A list of found entities. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Bad Request. </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized. </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden. </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Too Many Requests. </td><td>  -  </td></tr>
     </table>
     * Find more info about QQL here.
     * @see <a href="https://help.qase.io/hc/en-us/articles/4404615073041">Search entities by Qase Query Language (QQL). Documentation</a>
     */
    public okhttp3.Call searchAsync(String query, Integer limit, Integer offset, final ApiCallback<SearchResponse> _callback) throws QaseException {

        okhttp3.Call localVarCall = searchValidateBeforeCall(query, limit, offset, _callback);
        Type localVarReturnType = new TypeToken<SearchResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
}
