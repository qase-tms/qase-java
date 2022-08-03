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
import io.qase.client.ApiCallback;
import io.qase.client.ApiClient;
import io.qase.client.ApiResponse;
import io.qase.client.model.*;
import io.qase.enums.HttpMethod;

import java.lang.reflect.Type;
import java.util.HashMap;

public class ResultsApi
extends AbstractEntityApi<ResultCreate, ResultResponse, ResultListResponse, ResultUpdate, Object> {

    public ResultsApi() {
        super();
    }

    public ResultsApi(ApiClient apiClient) {
        super(apiClient);
    }

    /**
     * Build call for createResult
     *
     * @param code         Code of project, where to search entities. (required)
     * @param id           Identifier. (required)
     * @param resultCreate (required)
     * @param _callback    Callback for upload/download progress
     * @return Call to execute
     * @throws QaseException If fail to serialize the request body object
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A result </td><td>  -  </td></tr>
     * </table>
     */
    public okhttp3.Call createResultCall(
        String code, Integer id, ResultCreate resultCreate, final ApiCallback _callback
    ) throws QaseException {
        return createCallInternal(
            HttpMethod.POST,
            joinEntitySubpath(code, id.toString()),
            resultCreate,
            _callback
        );
    }

    /**
     * Create test run result.
     * This method allows to create test run result by Run Id.
     *
     * @param code         Code of project, where to search entities. (required)
     * @param id           Identifier. (required)
     * @param resultCreate (required)
     * @return Response
     * @throws QaseException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A result </td><td>  -  </td></tr>
     * </table>
     */
    public Response createResult(String code, Integer id, ResultCreate resultCreate) throws QaseException {
        ApiResponse<Response> localVarResp = createResultWithHttpInfo(code, id, resultCreate);
        return localVarResp.getData();
    }

    /**
     * Create test run result.
     * This method allows to create test run result by Run Id.
     *
     * @param code         Code of project, where to search entities. (required)
     * @param id           Identifier. (required)
     * @param resultCreate (required)
     * @return ApiResponse&lt;Response&gt;
     * @throws QaseException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A result </td><td>  -  </td></tr>
     * </table>
     */
    public ApiResponse<Response> createResultWithHttpInfo(String code, Integer id, ResultCreate resultCreate)
    throws QaseException {
        okhttp3.Call localVarCall = createResultValidateBeforeCall(code, id, resultCreate, null);
        Type localVarReturnType = new TypeToken<Response>() { }.getType();
        return getApiClient().execute(localVarCall, localVarReturnType);
    }

    /**
     * Create test run result. (asynchronously)
     * This method allows to create test run result by Run Id.
     *
     * @param code         Code of project, where to search entities. (required)
     * @param id           Identifier. (required)
     * @param resultCreate (required)
     * @param _callback    The callback to be executed when the API call finishes
     * @return The request call
     * @throws QaseException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A result </td><td>  -  </td></tr>
     * </table>
     */
    public okhttp3.Call createResultAsync(
        String code, Integer id, ResultCreate resultCreate, final ApiCallback<Response> _callback
    ) throws QaseException {
        okhttp3.Call localVarCall = createResultValidateBeforeCall(code, id, resultCreate, _callback);
        Type localVarReturnType = new TypeToken<Response>() { }.getType();
        getApiClient().executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    /**
     * Build call for createResultBulk
     *
     * @param code             Code of project, where to search entities. (required)
     * @param id               Identifier. (required)
     * @param resultCreateBulk (required)
     * @param _callback        Callback for upload/download progress
     * @return Call to execute
     * @throws QaseException If fail to serialize the request body object
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A result </td><td>  -  </td></tr>
     * </table>
     */
    public okhttp3.Call createResultBulkCall(
        String code, Integer id, ResultCreateBulk resultCreateBulk, final ApiCallback _callback
    ) throws QaseException {
        return createCallInternal(
            HttpMethod.POST,
            joinEntitySubpath(code, id.toString()),
            resultCreateBulk,
            _callback
        );
    }

    /**
     * Bulk create test run result.
     * This method allows to create a lot of test run result at once.
     *
     * @param code             Code of project, where to search entities. (required)
     * @param id               Identifier. (required)
     * @param resultCreateBulk (required)
     * @return Response
     * @throws QaseException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A result </td><td>  -  </td></tr>
     * </table>
     */
    public Response createResultBulk(String code, Integer id, ResultCreateBulk resultCreateBulk) throws QaseException {
        ApiResponse<Response> localVarResp = createResultBulkWithHttpInfo(code, id, resultCreateBulk);
        return localVarResp.getData();
    }

    /**
     * Bulk create test run result.
     * This method allows to create a lot of test run result at once.
     *
     * @param code             Code of project, where to search entities. (required)
     * @param id               Identifier. (required)
     * @param resultCreateBulk (required)
     * @return ApiResponse&lt;Response&gt;
     * @throws QaseException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A result </td><td>  -  </td></tr>
     * </table>
     */
    public ApiResponse<Response> createResultBulkWithHttpInfo(
        String code, Integer id, ResultCreateBulk resultCreateBulk
    ) throws QaseException {
        okhttp3.Call localVarCall = createResultBulkValidateBeforeCall(code, id, resultCreateBulk, null);
        Type localVarReturnType = new TypeToken<Response>() { }.getType();
        return getApiClient().execute(localVarCall, localVarReturnType);
    }

    /**
     * Bulk create test run result. (asynchronously)
     * This method allows to create a lot of test run result at once.
     *
     * @param code             Code of project, where to search entities. (required)
     * @param id               Identifier. (required)
     * @param resultCreateBulk (required)
     * @param _callback        The callback to be executed when the API call finishes
     * @return The request call
     * @throws QaseException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A result </td><td>  -  </td></tr>
     * </table>
     */
    public okhttp3.Call createResultBulkAsync(
        String code, Integer id, ResultCreateBulk resultCreateBulk, final ApiCallback<Response> _callback
    ) throws QaseException {
        okhttp3.Call localVarCall = createResultBulkValidateBeforeCall(code, id, resultCreateBulk, _callback);
        Type localVarReturnType = new TypeToken<Response>() { }.getType();
        getApiClient().executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    /**
     * Build call for deleteResult
     *
     * @param code      Code of project, where to search entities. (required)
     * @param id        Identifier. (required)
     * @param hash      Hash. (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws QaseException If fail to serialize the request body object
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A result </td><td>  -  </td></tr>
     * </table>
     */
    public okhttp3.Call deleteResultCall(String code, Integer id, String hash, final ApiCallback _callback)
    throws QaseException {
        return createCallInternal(
            HttpMethod.DELETE,
            joinEntitySubpath(code, id.toString(), hash),
            null,
            _callback
        );
    }

    /**
     * Delete test run result.
     * This method allows to delete test run result.
     *
     * @param code Code of project, where to search entities. (required)
     * @param id   Identifier. (required)
     * @param hash Hash. (required)
     * @return HashResponse
     * @throws QaseException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A result </td><td>  -  </td></tr>
     * </table>
     */
    public HashResponse deleteResult(String code, Integer id, String hash) throws QaseException {
        ApiResponse<HashResponse> localVarResp = deleteResultWithHttpInfo(code, id, hash);
        return localVarResp.getData();
    }

    /**
     * Delete test run result.
     * This method allows to delete test run result.
     *
     * @param code Code of project, where to search entities. (required)
     * @param id   Identifier. (required)
     * @param hash Hash. (required)
     * @return ApiResponse&lt;HashResponse&gt;
     * @throws QaseException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A result </td><td>  -  </td></tr>
     * </table>
     */
    public ApiResponse<HashResponse> deleteResultWithHttpInfo(String code, Integer id, String hash)
    throws QaseException {
        okhttp3.Call localVarCall = deleteResultValidateBeforeCall(code, id, hash, null);
        Type localVarReturnType = new TypeToken<HashResponse>() { }.getType();
        return getApiClient().execute(localVarCall, localVarReturnType);
    }

    /**
     * Delete test run result. (asynchronously)
     * This method allows to delete test run result.
     *
     * @param code      Code of project, where to search entities. (required)
     * @param id        Identifier. (required)
     * @param hash      Hash. (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws QaseException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A result </td><td>  -  </td></tr>
     * </table>
     */
    public okhttp3.Call deleteResultAsync(
        String code, Integer id, String hash, final ApiCallback<HashResponse> _callback
    ) throws QaseException {
        okhttp3.Call localVarCall = deleteResultValidateBeforeCall(code, id, hash, _callback);
        Type localVarReturnType = new TypeToken<HashResponse>() { }.getType();
        getApiClient().executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    /**
     * Build call for getResult
     *
     * @param code      Code of project, where to search entities. (required)
     * @param hash      Hash. (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws QaseException If fail to serialize the request body object
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A test run result. </td><td>  -  </td></tr>
     * </table>
     */
    public okhttp3.Call getResultCall(String code, String hash, final ApiCallback _callback) throws QaseException {
        return createCallInternal(
            HttpMethod.GET,
            joinEntitySubpath(code, hash),
            null,
            _callback
        );
    }

    /**
     * Get test run result by code.
     * This method allows to retrieve a specific test run result by Hash.
     *
     * @param code Code of project, where to search entities. (required)
     * @param hash Hash. (required)
     * @return ResultResponse
     * @throws QaseException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A test run result. </td><td>  -  </td></tr>
     * </table>
     */
    public ResultResponse getResult(String code, String hash) throws QaseException {
        ApiResponse<ResultResponse> localVarResp = getResultWithHttpInfo(code, hash);
        return localVarResp.getData();
    }

    /**
     * Get test run result by code.
     * This method allows to retrieve a specific test run result by Hash.
     *
     * @param code Code of project, where to search entities. (required)
     * @param hash Hash. (required)
     * @return ApiResponse&lt;ResultResponse&gt;
     * @throws QaseException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A test run result. </td><td>  -  </td></tr>
     * </table>
     */
    public ApiResponse<ResultResponse> getResultWithHttpInfo(String code, String hash) throws QaseException {
        okhttp3.Call localVarCall = getResultValidateBeforeCall(code, hash, null);
        Type localVarReturnType = new TypeToken<ResultResponse>() { }.getType();
        return getApiClient().execute(localVarCall, localVarReturnType);
    }

    /**
     * Get test run result by code. (asynchronously)
     * This method allows to retrieve a specific test run result by Hash.
     *
     * @param code      Code of project, where to search entities. (required)
     * @param hash      Hash. (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws QaseException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A test run result. </td><td>  -  </td></tr>
     * </table>
     */
    public okhttp3.Call getResultAsync(String code, String hash, final ApiCallback<ResultResponse> _callback)
    throws QaseException {
        okhttp3.Call localVarCall = getResultValidateBeforeCall(code, hash, _callback);
        Type localVarReturnType = new TypeToken<ResultResponse>() { }.getType();
        getApiClient().executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    /**
     * Build call for getResults
     *
     * @param code      Code of project, where to search entities. (required)
     * @param filters   (optional)
     * @param limit     A number of entities in result set. (optional, default to 10)
     * @param offset    How many entities should be skipped. (optional, default to 0)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws QaseException If fail to serialize the request body object
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A list of all test run results. </td><td>  -  </td></tr>
     * </table>
     */
    public okhttp3.Call getResultsCall(
        String code, Filters4 filters, Integer limit, Integer offset, final ApiCallback _callback
    ) throws QaseException {
        return createCallInternal(
            HttpMethod.GET,
            joinEntitySubpath(code),
            null,
            filterNullsAndConvertToPairs(new HashMap<String, Object>() {{
                put(FILTERS_QUERY_PARAMETER_NAME, filters);
                put(LIMIT_QUERY_PARAMETER_NAME, limit);
                put(OFFSET_QUERY_PARAMETER_NAME, offset);
            }}),
            _callback
        );
    }

    /**
     * Get all test run results.
     * This method allows to retrieve all test run results stored in selected project.
     *
     * @param code    Code of project, where to search entities. (required)
     * @param filters (optional)
     * @param limit   A number of entities in result set. (optional, default to 10)
     * @param offset  How many entities should be skipped. (optional, default to 0)
     * @return ResultListResponse
     * @throws QaseException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A list of all test run results. </td><td>  -  </td></tr>
     * </table>
     */
    public ResultListResponse getResults(String code, Filters4 filters, Integer limit, Integer offset)
    throws QaseException {
        ApiResponse<ResultListResponse> localVarResp = getResultsWithHttpInfo(code, filters, limit, offset);
        return localVarResp.getData();
    }

    /**
     * Get all test run results.
     * This method allows to retrieve all test run results stored in selected project.
     *
     * @param code    Code of project, where to search entities. (required)
     * @param filters (optional)
     * @param limit   A number of entities in result set. (optional, default to 10)
     * @param offset  How many entities should be skipped. (optional, default to 0)
     * @return ApiResponse&lt;ResultListResponse&gt;
     * @throws QaseException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A list of all test run results. </td><td>  -  </td></tr>
     * </table>
     */
    public ApiResponse<ResultListResponse> getResultsWithHttpInfo(
        String code, Filters4 filters, Integer limit, Integer offset
    ) throws QaseException {
        okhttp3.Call localVarCall = getResultsValidateBeforeCall(code, filters, limit, offset, null);
        Type localVarReturnType = new TypeToken<ResultListResponse>() { }.getType();
        return getApiClient().execute(localVarCall, localVarReturnType);
    }

    /**
     * Get all test run results. (asynchronously)
     * This method allows to retrieve all test run results stored in selected project.
     *
     * @param code      Code of project, where to search entities. (required)
     * @param filters   (optional)
     * @param limit     A number of entities in result set. (optional, default to 10)
     * @param offset    How many entities should be skipped. (optional, default to 0)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws QaseException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A list of all test run results. </td><td>  -  </td></tr>
     * </table>
     */
    public okhttp3.Call getResultsAsync(
        String code, Filters4 filters, Integer limit, Integer offset, final ApiCallback<ResultListResponse> _callback
    ) throws QaseException {
        okhttp3.Call localVarCall = getResultsValidateBeforeCall(code, filters, limit, offset, _callback);
        Type localVarReturnType = new TypeToken<ResultListResponse>() { }.getType();
        getApiClient().executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    /**
     * Build call for updateResult
     *
     * @param code         Code of project, where to search entities. (required)
     * @param id           Identifier. (required)
     * @param hash         Hash. (required)
     * @param resultUpdate (required)
     * @param _callback    Callback for upload/download progress
     * @return Call to execute
     * @throws QaseException If fail to serialize the request body object
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A result </td><td>  -  </td></tr>
     * </table>
     */
    public okhttp3.Call updateResultCall(
        String code, Integer id, String hash, ResultUpdate resultUpdate, final ApiCallback _callback
    ) throws QaseException {
        return createCallInternal(
            HttpMethod.PATCH,
            joinEntitySubpath(code, id.toString(), hash),
            resultUpdate,
            _callback
        );
    }

    /**
     * Update test run result.
     * This method allows to update test run result.
     *
     * @param code         Code of project, where to search entities. (required)
     * @param id           Identifier. (required)
     * @param hash         Hash. (required)
     * @param resultUpdate (required)
     * @return HashResponse
     * @throws QaseException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A result </td><td>  -  </td></tr>
     * </table>
     */
    public HashResponse updateResult(String code, Integer id, String hash, ResultUpdate resultUpdate)
    throws QaseException {
        ApiResponse<HashResponse> localVarResp = updateResultWithHttpInfo(code, id, hash, resultUpdate);
        return localVarResp.getData();
    }

    /**
     * Update test run result.
     * This method allows to update test run result.
     *
     * @param code         Code of project, where to search entities. (required)
     * @param id           Identifier. (required)
     * @param hash         Hash. (required)
     * @param resultUpdate (required)
     * @return ApiResponse&lt;HashResponse&gt;
     * @throws QaseException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A result </td><td>  -  </td></tr>
     * </table>
     */
    public ApiResponse<HashResponse> updateResultWithHttpInfo(
        String code, Integer id, String hash, ResultUpdate resultUpdate
    ) throws QaseException {
        okhttp3.Call localVarCall = updateResultValidateBeforeCall(code, id, hash, resultUpdate, null);
        Type localVarReturnType = new TypeToken<HashResponse>() { }.getType();
        return getApiClient().execute(localVarCall, localVarReturnType);
    }

    /**
     * Update test run result. (asynchronously)
     * This method allows to update test run result.
     *
     * @param code         Code of project, where to search entities. (required)
     * @param id           Identifier. (required)
     * @param hash         Hash. (required)
     * @param resultUpdate (required)
     * @param _callback    The callback to be executed when the API call finishes
     * @return The request call
     * @throws QaseException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A result </td><td>  -  </td></tr>
     * </table>
     */
    public okhttp3.Call updateResultAsync(
        String code, Integer id, String hash, ResultUpdate resultUpdate, final ApiCallback<HashResponse> _callback
    ) throws QaseException {
        okhttp3.Call localVarCall = updateResultValidateBeforeCall(code, id, hash, resultUpdate, _callback);
        Type localVarReturnType = new TypeToken<HashResponse>() { }.getType();
        getApiClient().executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    @Override
    protected String getEntityRootPathSegment() {
        return "result";
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call createResultValidateBeforeCall(
        String code, Integer id, ResultCreate resultCreate, final ApiCallback _callback
    ) throws QaseException {
        // verify the required parameter 'code' is set
        if (code == null) {
            throw new QaseException("Missing the required parameter 'code' when calling createResult(Async)");
        }

        // verify the required parameter 'id' is set
        if (id == null) {
            throw new QaseException("Missing the required parameter 'id' when calling createResult(Async)");
        }

        // verify the required parameter 'resultCreate' is set
        if (resultCreate == null) {
            throw new QaseException("Missing the required parameter 'resultCreate' when calling createResult(Async)");
        }

        okhttp3.Call localVarCall = createResultCall(code, id, resultCreate, _callback);
        return localVarCall;
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call createResultBulkValidateBeforeCall(
        String code, Integer id, ResultCreateBulk resultCreateBulk, final ApiCallback _callback
    ) throws QaseException {
        // verify the required parameter 'code' is set
        if (code == null) {
            throw new QaseException("Missing the required parameter 'code' when calling createResultBulk(Async)");
        }

        // verify the required parameter 'id' is set
        if (id == null) {
            throw new QaseException("Missing the required parameter 'id' when calling createResultBulk(Async)");
        }

        // verify the required parameter 'resultCreateBulk' is set
        if (resultCreateBulk == null) {
            throw new QaseException(
                "Missing the required parameter 'resultCreateBulk' when calling createResultBulk(Async)"
            );
        }

        okhttp3.Call localVarCall = createResultBulkCall(code, id, resultCreateBulk, _callback);
        return localVarCall;
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call deleteResultValidateBeforeCall(
        String code, Integer id, String hash, final ApiCallback _callback
    ) throws QaseException {
        // verify the required parameter 'code' is set
        if (code == null) {
            throw new QaseException("Missing the required parameter 'code' when calling deleteResult(Async)");
        }

        // verify the required parameter 'id' is set
        if (id == null) {
            throw new QaseException("Missing the required parameter 'id' when calling deleteResult(Async)");
        }

        // verify the required parameter 'hash' is set
        if (hash == null) {
            throw new QaseException("Missing the required parameter 'hash' when calling deleteResult(Async)");
        }

        okhttp3.Call localVarCall = deleteResultCall(code, id, hash, _callback);
        return localVarCall;
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getResultValidateBeforeCall(String code, String hash, final ApiCallback _callback)
        throws QaseException {
        // verify the required parameter 'code' is set
        if (code == null) {
            throw new QaseException("Missing the required parameter 'code' when calling getResult(Async)");
        }

        // verify the required parameter 'hash' is set
        if (hash == null) {
            throw new QaseException("Missing the required parameter 'hash' when calling getResult(Async)");
        }

        okhttp3.Call localVarCall = getResultCall(code, hash, _callback);
        return localVarCall;
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getResultsValidateBeforeCall(
        String code, Filters4 filters, Integer limit, Integer offset, final ApiCallback _callback
    ) throws QaseException {
        // verify the required parameter 'code' is set
        if (code == null) {
            throw new QaseException("Missing the required parameter 'code' when calling getResults(Async)");
        }

        okhttp3.Call localVarCall = getResultsCall(code, filters, limit, offset, _callback);
        return localVarCall;
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call updateResultValidateBeforeCall(
        String code, Integer id, String hash, ResultUpdate resultUpdate, final ApiCallback _callback
    ) throws QaseException {
        // verify the required parameter 'code' is set
        if (code == null) {
            throw new QaseException("Missing the required parameter 'code' when calling updateResult(Async)");
        }

        // verify the required parameter 'id' is set
        if (id == null) {
            throw new QaseException("Missing the required parameter 'id' when calling updateResult(Async)");
        }

        // verify the required parameter 'hash' is set
        if (hash == null) {
            throw new QaseException("Missing the required parameter 'hash' when calling updateResult(Async)");
        }

        // verify the required parameter 'resultUpdate' is set
        if (resultUpdate == null) {
            throw new QaseException("Missing the required parameter 'resultUpdate' when calling updateResult(Async)");
        }

        return updateResultCall(code, id, hash, resultUpdate, _callback);
    }
}
