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

public class SharedStepsApi
extends AbstractEntityApi<SharedStepCreate, SharedStepResponse, SharedStepListResponse, SharedStepUpdate, Object> {

    public SharedStepsApi() {
        super();
    }

    public SharedStepsApi(ApiClient apiClient) {
        super(apiClient);
    }

    /**
     * Build call for createSharedStep
     *
     * @param code             Code of project, where to search entities. (required)
     * @param sharedStepCreate (required)
     * @param _callback        Callback for upload/download progress
     * @return Call to execute
     * @throws QaseException If fail to serialize the request body object
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A result. </td><td>  -  </td></tr>
     * </table>
     */
    public okhttp3.Call createSharedStepCall(
        String code, SharedStepCreate sharedStepCreate, final ApiCallback _callback
    ) throws QaseException {
        return createEntityCall(code, sharedStepCreate, _callback);
    }

    /**
     * Create a new shared step.
     * This method allows to create a shared step in selected project.
     *
     * @param code             Code of project, where to search entities. (required)
     * @param sharedStepCreate (required)
     * @return HashResponse
     * @throws QaseException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A result. </td><td>  -  </td></tr>
     * </table>
     */
    public HashResponse createSharedStep(String code, SharedStepCreate sharedStepCreate) throws QaseException {
        ApiResponse<HashResponse> localVarResp = createSharedStepWithHttpInfo(code, sharedStepCreate);
        return localVarResp.getData();
    }

    /**
     * Create a new shared step.
     * This method allows to create a shared step in selected project.
     *
     * @param code             Code of project, where to search entities. (required)
     * @param sharedStepCreate (required)
     * @return ApiResponse&lt;HashResponse&gt;
     * @throws QaseException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A result. </td><td>  -  </td></tr>
     * </table>
     */
    public ApiResponse<HashResponse> createSharedStepWithHttpInfo(String code, SharedStepCreate sharedStepCreate)
    throws QaseException {
        okhttp3.Call localVarCall = createSharedStepValidateBeforeCall(code, sharedStepCreate, null);
        Type localVarReturnType = new TypeToken<HashResponse>() { }.getType();
        return getApiClient().execute(localVarCall, localVarReturnType);
    }

    /**
     * Create a new shared step. (asynchronously)
     * This method allows to create a shared step in selected project.
     *
     * @param code             Code of project, where to search entities. (required)
     * @param sharedStepCreate (required)
     * @param _callback        The callback to be executed when the API call finishes
     * @return The request call
     * @throws QaseException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A result. </td><td>  -  </td></tr>
     * </table>
     */
    public okhttp3.Call createSharedStepAsync(
        String code, SharedStepCreate sharedStepCreate, final ApiCallback<HashResponse> _callback
    ) throws QaseException {
        okhttp3.Call localVarCall = createSharedStepValidateBeforeCall(code, sharedStepCreate, _callback);
        Type localVarReturnType = new TypeToken<HashResponse>() { }.getType();
        getApiClient().executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    /**
     * Build call for deleteSharedStep
     *
     * @param code      Code of project, where to search entities. (required)
     * @param hash      Hash. (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws QaseException If fail to serialize the request body object
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A Result. </td><td>  -  </td></tr>
     * </table>
     */
    public okhttp3.Call deleteSharedStepCall(String code, String hash, final ApiCallback _callback)
    throws QaseException {
        return createCallInternal(
            HttpMethod.DELETE,
            joinEntitySubpathEscaped(code, hash),
            null,
            _callback
        );
    }

    /**
     * Delete shared step.
     * This method completely deletes a shared step from repository.
     *
     * @param code Code of project, where to search entities. (required)
     * @param hash Hash. (required)
     * @return HashResponse
     * @throws QaseException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A Result. </td><td>  -  </td></tr>
     * </table>
     */
    public HashResponse deleteSharedStep(String code, String hash) throws QaseException {
        ApiResponse<HashResponse> localVarResp = deleteSharedStepWithHttpInfo(code, hash);
        return localVarResp.getData();
    }

    /**
     * Delete shared step.
     * This method completely deletes a shared step from repository.
     *
     * @param code Code of project, where to search entities. (required)
     * @param hash Hash. (required)
     * @return ApiResponse&lt;HashResponse&gt;
     * @throws QaseException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A Result. </td><td>  -  </td></tr>
     * </table>
     */
    public ApiResponse<HashResponse> deleteSharedStepWithHttpInfo(String code, String hash) throws QaseException {
        okhttp3.Call localVarCall = deleteSharedStepValidateBeforeCall(code, hash, null);
        Type localVarReturnType = new TypeToken<HashResponse>() { }.getType();
        return getApiClient().execute(localVarCall, localVarReturnType);
    }

    /**
     * Delete shared step. (asynchronously)
     * This method completely deletes a shared step from repository.
     *
     * @param code      Code of project, where to search entities. (required)
     * @param hash      Hash. (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws QaseException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A Result. </td><td>  -  </td></tr>
     * </table>
     */
    public okhttp3.Call deleteSharedStepAsync(String code, String hash, final ApiCallback<HashResponse> _callback)
    throws QaseException {
        okhttp3.Call localVarCall = deleteSharedStepValidateBeforeCall(code, hash, _callback);
        Type localVarReturnType = new TypeToken<HashResponse>() { }.getType();
        getApiClient().executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    /**
     * Build call for getSharedStep
     *
     * @param code      Code of project, where to search entities. (required)
     * @param hash      Hash. (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws QaseException If fail to serialize the request body object
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A shared step. </td><td>  -  </td></tr>
     * </table>
     */
    public okhttp3.Call getSharedStepCall(String code, String hash, final ApiCallback _callback) throws QaseException {
        return createCallInternal(
            HttpMethod.GET,
            joinEntitySubpathEscaped(code, hash),
            null,
            _callback
        );
    }

    /**
     * Get a specific shared step.
     * This method allows to retrieve a specific shared step.
     *
     * @param code Code of project, where to search entities. (required)
     * @param hash Hash. (required)
     * @return SharedStepResponse
     * @throws QaseException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A shared step. </td><td>  -  </td></tr>
     * </table>
     */
    public SharedStepResponse getSharedStep(String code, String hash) throws QaseException {
        ApiResponse<SharedStepResponse> localVarResp = getSharedStepWithHttpInfo(code, hash);
        return localVarResp.getData();
    }

    /**
     * Get a specific shared step.
     * This method allows to retrieve a specific shared step.
     *
     * @param code Code of project, where to search entities. (required)
     * @param hash Hash. (required)
     * @return ApiResponse&lt;SharedStepResponse&gt;
     * @throws QaseException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A shared step. </td><td>  -  </td></tr>
     * </table>
     */
    public ApiResponse<SharedStepResponse> getSharedStepWithHttpInfo(String code, String hash) throws QaseException {
        okhttp3.Call localVarCall = getSharedStepValidateBeforeCall(code, hash, null);
        Type localVarReturnType = new TypeToken<SharedStepResponse>() { }.getType();
        return getApiClient().execute(localVarCall, localVarReturnType);
    }

    /**
     * Get a specific shared step. (asynchronously)
     * This method allows to retrieve a specific shared step.
     *
     * @param code      Code of project, where to search entities. (required)
     * @param hash      Hash. (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws QaseException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A shared step. </td><td>  -  </td></tr>
     * </table>
     */
    public okhttp3.Call getSharedStepAsync(String code, String hash, final ApiCallback<SharedStepResponse> _callback)
    throws QaseException {
        okhttp3.Call localVarCall = getSharedStepValidateBeforeCall(code, hash, _callback);
        Type localVarReturnType = new TypeToken<SharedStepResponse>() { }.getType();
        getApiClient().executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    /**
     * Build call for getSharedSteps
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
     * <tr><td> 200 </td><td> A list of all shared steps. </td><td>  -  </td></tr>
     * </table>
     */
    public okhttp3.Call getSharedStepsCall(
        String code, Filters6 filters, Integer limit, Integer offset, final ApiCallback _callback
    ) throws QaseException {
        return createCallInternal(
            HttpMethod.GET,
            joinEntitySubpathEscaped(code),
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
     * Get all shared steps.
     * This method allows to retrieve all shared steps stored in selected project.
     *
     * @param code    Code of project, where to search entities. (required)
     * @param filters (optional)
     * @param limit   A number of entities in result set. (optional, default to 10)
     * @param offset  How many entities should be skipped. (optional, default to 0)
     * @return SharedStepListResponse
     * @throws QaseException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A list of all shared steps. </td><td>  -  </td></tr>
     * </table>
     */
    public SharedStepListResponse getSharedSteps(String code, Filters6 filters, Integer limit, Integer offset)
    throws QaseException {
        ApiResponse<SharedStepListResponse> localVarResp = getSharedStepsWithHttpInfo(code, filters, limit, offset);
        return localVarResp.getData();
    }

    /**
     * Get all shared steps.
     * This method allows to retrieve all shared steps stored in selected project.
     *
     * @param code    Code of project, where to search entities. (required)
     * @param filters (optional)
     * @param limit   A number of entities in result set. (optional, default to 10)
     * @param offset  How many entities should be skipped. (optional, default to 0)
     * @return ApiResponse&lt;SharedStepListResponse&gt;
     * @throws QaseException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A list of all shared steps. </td><td>  -  </td></tr>
     * </table>
     */
    public ApiResponse<SharedStepListResponse> getSharedStepsWithHttpInfo(
        String code, Filters6 filters, Integer limit, Integer offset
    ) throws QaseException {
        okhttp3.Call localVarCall = getSharedStepsValidateBeforeCall(code, filters, limit, offset, null);
        Type localVarReturnType = new TypeToken<SharedStepListResponse>() { }.getType();
        return getApiClient().execute(localVarCall, localVarReturnType);
    }

    /**
     * Get all shared steps. (asynchronously)
     * This method allows to retrieve all shared steps stored in selected project.
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
     * <tr><td> 200 </td><td> A list of all shared steps. </td><td>  -  </td></tr>
     * </table>
     */
    public okhttp3.Call getSharedStepsAsync(
        String code,
        Filters6 filters,
        Integer limit,
        Integer offset,
        final ApiCallback<SharedStepListResponse> _callback
    ) throws QaseException {
        okhttp3.Call localVarCall = getSharedStepsValidateBeforeCall(code, filters, limit, offset, _callback);
        Type localVarReturnType = new TypeToken<SharedStepListResponse>() { }.getType();
        getApiClient().executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    /**
     * Build call for updateSharedStep
     *
     * @param code             Code of project, where to search entities. (required)
     * @param hash             Hash. (required)
     * @param sharedStepUpdate (required)
     * @param _callback        Callback for upload/download progress
     * @return Call to execute
     * @throws QaseException If fail to serialize the request body object
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A result. </td><td>  -  </td></tr>
     * </table>
     */
    public okhttp3.Call updateSharedStepCall(
        String code, String hash, SharedStepUpdate sharedStepUpdate, final ApiCallback _callback
    ) throws QaseException {
        return createCallInternal(
            HttpMethod.PATCH,
            joinEntitySubpathEscaped(code, hash),
            sharedStepUpdate,
            _callback
        );
    }

    /**
     * Update shared step.
     * This method updates a shared step.
     *
     * @param code             Code of project, where to search entities. (required)
     * @param hash             Hash. (required)
     * @param sharedStepUpdate (required)
     * @return HashResponse
     * @throws QaseException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A result. </td><td>  -  </td></tr>
     * </table>
     */
    public HashResponse updateSharedStep(String code, String hash, SharedStepUpdate sharedStepUpdate)
    throws QaseException {
        ApiResponse<HashResponse> localVarResp = updateSharedStepWithHttpInfo(code, hash, sharedStepUpdate);
        return localVarResp.getData();
    }

    /**
     * Update shared step.
     * This method updates a shared step.
     *
     * @param code             Code of project, where to search entities. (required)
     * @param hash             Hash. (required)
     * @param sharedStepUpdate (required)
     * @return ApiResponse&lt;HashResponse&gt;
     * @throws QaseException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A result. </td><td>  -  </td></tr>
     * </table>
     */
    public ApiResponse<HashResponse> updateSharedStepWithHttpInfo(
        String code, String hash, SharedStepUpdate sharedStepUpdate
    ) throws QaseException {
        okhttp3.Call localVarCall = updateSharedStepValidateBeforeCall(code, hash, sharedStepUpdate, null);
        Type localVarReturnType = new TypeToken<HashResponse>() { }.getType();
        return getApiClient().execute(localVarCall, localVarReturnType);
    }

    /**
     * Update shared step. (asynchronously)
     * This method updates a shared step.
     *
     * @param code             Code of project, where to search entities. (required)
     * @param hash             Hash. (required)
     * @param sharedStepUpdate (required)
     * @param _callback        The callback to be executed when the API call finishes
     * @return The request call
     * @throws QaseException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A result. </td><td>  -  </td></tr>
     * </table>
     */
    public okhttp3.Call updateSharedStepAsync(
        String code, String hash, SharedStepUpdate sharedStepUpdate, final ApiCallback<HashResponse> _callback
    ) throws QaseException {
        okhttp3.Call localVarCall = updateSharedStepValidateBeforeCall(code, hash, sharedStepUpdate, _callback);
        Type localVarReturnType = new TypeToken<HashResponse>() { }.getType();
        getApiClient().executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    @Override
    protected String getEntityPath() {
        return "/shared_step";
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call createSharedStepValidateBeforeCall(
        String code, SharedStepCreate sharedStepCreate, final ApiCallback _callback
    ) throws QaseException {
        // verify the required parameter 'code' is set
        if (code == null) {
            throw new QaseException("Missing the required parameter 'code' when calling createSharedStep(Async)");
        }

        // verify the required parameter 'sharedStepCreate' is set
        if (sharedStepCreate == null) {
            throw new QaseException(
                "Missing the required parameter 'sharedStepCreate' when calling createSharedStep(Async)"
            );
        }

        return createSharedStepCall(code, sharedStepCreate, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call deleteSharedStepValidateBeforeCall(String code, String hash, final ApiCallback _callback)
        throws QaseException {
        // verify the required parameter 'code' is set
        if (code == null) {
            throw new QaseException("Missing the required parameter 'code' when calling deleteSharedStep(Async)");
        }

        // verify the required parameter 'hash' is set
        if (hash == null) {
            throw new QaseException("Missing the required parameter 'hash' when calling deleteSharedStep(Async)");
        }

        return deleteSharedStepCall(code, hash, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getSharedStepValidateBeforeCall(String code, String hash, final ApiCallback _callback)
        throws QaseException {
        // verify the required parameter 'code' is set
        if (code == null) {
            throw new QaseException("Missing the required parameter 'code' when calling getSharedStep(Async)");
        }

        // verify the required parameter 'hash' is set
        if (hash == null) {
            throw new QaseException("Missing the required parameter 'hash' when calling getSharedStep(Async)");
        }

        return getSharedStepCall(code, hash, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getSharedStepsValidateBeforeCall(
        String code, Filters6 filters, Integer limit, Integer offset, final ApiCallback _callback
    ) throws QaseException {
        // verify the required parameter 'code' is set
        if (code == null) {
            throw new QaseException("Missing the required parameter 'code' when calling getSharedSteps(Async)");
        }

        return getSharedStepsCall(code, filters, limit, offset, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call updateSharedStepValidateBeforeCall(
        String code, String hash, SharedStepUpdate sharedStepUpdate, final ApiCallback _callback
    ) throws QaseException {
        // verify the required parameter 'code' is set
        if (code == null) {
            throw new QaseException("Missing the required parameter 'code' when calling updateSharedStep(Async)");
        }

        // verify the required parameter 'hash' is set
        if (hash == null) {
            throw new QaseException("Missing the required parameter 'hash' when calling updateSharedStep(Async)");
        }

        // verify the required parameter 'sharedStepUpdate' is set
        if (sharedStepUpdate == null) {
            throw new QaseException(
                "Missing the required parameter 'sharedStepUpdate' when calling updateSharedStep(Async)"
            );
        }

        return updateSharedStepCall(code, hash, sharedStepUpdate, _callback);
    }
}
