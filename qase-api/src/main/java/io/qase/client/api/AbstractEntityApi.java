package io.qase.client.api;

import com.google.gson.reflect.TypeToken;
import io.qase.api.exceptions.QaseException;
import io.qase.client.*;
import io.qase.client.model.*;
import io.qase.enums.HttpMethod;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.qase.configuration.QaseModule.INJECTOR;

/**
 *
 * @param <C> a DTO type for a create-entity-call
 * @param <R> a DTO type for a read-entity-response
 * @param <RL> a DTO type for a read-list-entity-response
 * @param <U> a DTO type for an update-call
 * @param <S> a type for entities` status
 * */
public abstract class AbstractEntityApi<C, R, RL, U, S> { // TODO: decompose the abstract class, refactor to a service

    private static final Object NO_FILTERS = null;

    private static final String URL_PATH_SEPARATOR = "/";

    private ApiClient localVarApiClient;

    public AbstractEntityApi() {
        this(INJECTOR.getInstance(ApiClient.class));
    }

    public AbstractEntityApi(ApiClient apiClient) {
        this.localVarApiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return localVarApiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.localVarApiClient = apiClient;
    }

    public okhttp3.Call createEntityCall(String code, C entityCreate, final ApiCallback _callback)
    throws QaseException {
        // create path and map variables
        String localVarPath = getEntityPath() + URL_PATH_SEPARATOR + localVarApiClient.escapeString(code);

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();
        Map<String, String> localVarHeaderParams = new HashMap<>();
        Map<String, String> localVarCookieParams = new HashMap<>();
        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"application/json"};
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {"application/json"};
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[]{"TokenAuth"};
        return localVarApiClient.buildCall(
            localVarPath,
            HttpMethod.POST.toString(),
            localVarQueryParams,
            localVarCollectionQueryParams,
            entityCreate,
            localVarHeaderParams,
            localVarCookieParams,
            localVarFormParams,
            localVarAuthNames,
            _callback
        );
    }

    @SuppressWarnings("rawtypes")
    protected okhttp3.Call createEntityValidateBeforeCall(
        String code, C entityCreate, final ApiCallback _callback
    ) throws QaseException {
        // verify the required parameter 'code' is set
        if (code == null) {
            throw new QaseException("Missing the required parameter 'code' when calling createEntity(Async)");
        }
        // verify the required parameter 'entityCreate' is set
        if (entityCreate == null) {
            throw new QaseException("Missing the required parameter 'entityCreate' when calling createEntity(Async)");
        }

        return createEntityCall(code, entityCreate, _callback);
    }

    /**
     * Create a new entity.
     * This method allows to create a entity in selected project.
     *
     * @param code         Code of project, where to search entities. (required)
     * @param entityCreate (required)
     * @return IdResponse
     * @throws QaseException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A result. </td><td>  -  </td></tr>
     * </table>
     */
    public IdResponse createEntity(String code, C entityCreate) throws QaseException {
        ApiResponse<IdResponse> localVarResp = createEntityWithHttpInfo(code, entityCreate);
        return localVarResp.getData();
    }

    public ApiResponse<IdResponse> createEntityWithHttpInfo(String code, C entityCreate) throws QaseException {
        okhttp3.Call localVarCall = createEntityValidateBeforeCall(code, entityCreate, null);
        Type localVarReturnType = new TypeToken<IdResponse>() { }.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Create a new entity. (asynchronously)
     * This method allows to create an entity in selected project.
     *
     * @param code         Code of project, where to search entities. (required)
     * @param entityCreate (required)
     * @param _callback    The callback to be executed when the API call finishes
     * @return The request call
     * @throws QaseException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A result. </td><td>  -  </td></tr>
     * </table>
     */
    public okhttp3.Call createEntityAsync(
        String code, C entityCreate, final ApiCallback<IdResponse> _callback
    ) throws QaseException {
        okhttp3.Call localVarCall = createEntityValidateBeforeCall(code, entityCreate, _callback);
        Type localVarReturnType = new TypeToken<IdResponse>() { }.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    /**
     * Build call for deleteEntity
     *
     * @param code      Code of project, where to search entities. (required)
     * @param id        Identifier. (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws QaseException If fail to serialize the request body object
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A Result. </td><td>  -  </td></tr>
     * </table>
     */
    public okhttp3.Call deleteEntityCall(String code, Integer id, final ApiCallback _callback) throws QaseException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = getEntityPath()
            + URL_PATH_SEPARATOR + localVarApiClient.escapeString(code)
            + URL_PATH_SEPARATOR + localVarApiClient.escapeString(id.toString());

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();
        Map<String, String> localVarHeaderParams = new HashMap<>();
        Map<String, String> localVarCookieParams = new HashMap<>();
        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"application/json"};
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = { };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[] {"TokenAuth"};
        return localVarApiClient.buildCall(
            localVarPath,
            HttpMethod.DELETE.toString(),
            localVarQueryParams,
            localVarCollectionQueryParams,
            localVarPostBody,
            localVarHeaderParams,
            localVarCookieParams,
            localVarFormParams,
            localVarAuthNames,
            _callback
        );
    }

    /**
     * Delete entity.
     * This method completely deletes an entity from repository.
     *
     * @param code Code of project, where to search entities. (required)
     * @param id   Identifier. (required)
     * @return IdResponse
     * @throws QaseException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A Result. </td><td>  -  </td></tr>
     * </table>
     */
    public IdResponse deleteEntity(String code, Integer id) throws QaseException {
        ApiResponse<IdResponse> localVarResp = deleteEntityWithHttpInfo(code, id);
        return localVarResp.getData();
    }

    /**
     * Delete entity.
     * This method completely deletes an entity from repository.
     *
     * @param code Code of project, where to search entities. (required)
     * @param id   Identifier. (required)
     * @return ApiResponse&lt;IdResponse&gt;
     * @throws QaseException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A Result. </td><td>  -  </td></tr>
     * </table>
     */
    public ApiResponse<IdResponse> deleteEntityWithHttpInfo(String code, Integer id) throws QaseException {
        okhttp3.Call localVarCall = deleteEntityValidateBeforeCall(code, id, null);
        Type localVarReturnType = new TypeToken<IdResponse>() { }.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Delete entity. (asynchronously)
     * This method completely deletes a entity from repository.
     *
     * @param code      Code of project, where to search entities. (required)
     * @param id        Identifier. (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws QaseException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A Result. </td><td>  -  </td></tr>
     * </table>
     */
    public okhttp3.Call deleteEntityAsync(String code, Integer id, final ApiCallback<IdResponse> _callback) throws QaseException {
        okhttp3.Call localVarCall = deleteEntityValidateBeforeCall(code, id, _callback);
        Type localVarReturnType = new TypeToken<IdResponse>() { }.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    /**
     * Build call for getEntity
     *
     * @param code      Code of project, where to search entities. (required)
     * @param id        Identifier. (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws QaseException If fail to serialize the request body object
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> An entity. </td><td>  -  </td></tr>
     * </table>
     */
    public okhttp3.Call getEntityCall(String code, Integer id, final ApiCallback _callback) throws QaseException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = getEntityPath()
            + URL_PATH_SEPARATOR + localVarApiClient.escapeString(code)
            + URL_PATH_SEPARATOR + localVarApiClient.escapeString(id.toString());

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();
        Map<String, String> localVarHeaderParams = new HashMap<>();
        Map<String, String> localVarCookieParams = new HashMap<>();
        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"application/json"};
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = { };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[] {"TokenAuth"};
        return localVarApiClient.buildCall(
            localVarPath,
            HttpMethod.GET.toString(),
            localVarQueryParams,
            localVarCollectionQueryParams,
            localVarPostBody,
            localVarHeaderParams,
            localVarCookieParams,
            localVarFormParams,
            localVarAuthNames,
            _callback
        );
    }

    /**
     * Get a specific entity.
     * This method allows to retrieve a specific entity.
     *
     * @param code Code of project, where to search entities. (required)
     * @param id   Identifier. (required)
     * @return an entity response
     * @throws QaseException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> An entity. </td><td>  -  </td></tr>
     * </table>
     */
    public R getEntity(String code, Integer id) throws QaseException {
        ApiResponse<R> localVarResp = getEntityWithHttpInfo(code, id);
        return localVarResp.getData();
    }

    /**
     * Get a specific entity.
     * This method allows to retrieve a specific entity.
     *
     * @param code Code of project, where to search entities. (required)
     * @param id   Identifier. (required)
     * @return ApiResponse&lt;R&gt;
     * @throws QaseException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> An entity. </td><td>  -  </td></tr>
     * </table>
     */
    public ApiResponse<R> getEntityWithHttpInfo(String code, Integer id) throws QaseException {
        okhttp3.Call localVarCall = getEntityValidateBeforeCall(code, id, null);
        Type localVarReturnType = new TypeToken<R>() { }.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get a specific entity. (asynchronously)
     * This method allows to retrieve a specific entity.
     *
     * @param code      Code of project, where to search entities. (required)
     * @param id        Identifier. (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws QaseException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> An entity. </td><td>  -  </td></tr>
     * </table>
     */
    public okhttp3.Call getEntityAsync(String code, Integer id, final ApiCallback<R> _callback) throws QaseException {
        okhttp3.Call localVarCall = getEntityValidateBeforeCall(code, id, _callback);
        Type localVarReturnType = new TypeToken<R>() { }.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    /**
     * Build call for getEntity
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
     * <tr><td> 200 </td><td> A list of all entity. </td><td>  -  </td></tr>
     * </table>
     */
    public okhttp3.Call getEntitiesCall(
        String code, Object filters, Integer limit, Integer offset, final ApiCallback _callback
    ) throws QaseException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = getEntityPath() + URL_PATH_SEPARATOR + localVarApiClient.escapeString(code);

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();
        Map<String, String> localVarHeaderParams = new HashMap<>();
        Map<String, String> localVarCookieParams = new HashMap<>();
        Map<String, Object> localVarFormParams = new HashMap<>();

        if (filters != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("filters", filters));
        }

        if (limit != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("limit", limit));
        }

        if (offset != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("offset", offset));
        }

        final String[] localVarAccepts = {"application/json"};
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = { };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[] {"TokenAuth"};
        return localVarApiClient.buildCall(
            localVarPath,
            HttpMethod.GET.toString(),
            localVarQueryParams,
            localVarCollectionQueryParams,
            localVarPostBody,
            localVarHeaderParams,
            localVarCookieParams,
            localVarFormParams,
            localVarAuthNames,
            _callback
        );
    }

    public okhttp3.Call getEntitiesCall(
        String code, Integer limit, Integer offset, final ApiCallback _callback
    ) throws QaseException {
        return getEntitiesCall(code, NO_FILTERS, limit, offset, _callback);
    }

    /**
     * Get all entities.
     * This method allows to retrieve all entities stored in selected project.
     *
     * @param code    Code of project, where to search entities. (required)
     * @param filters (optional)
     * @param limit   A number of entities in result set. (optional, default to 10)
     * @param offset  How many entities should be skipped. (optional, default to 0)
     * @return RL
     * @throws QaseException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A list of all entities. </td><td>  -  </td></tr>
     * </table>
     */
    public RL getEntities(String code, Object filters, Integer limit, Integer offset) throws QaseException {
        ApiResponse<RL> localVarResp = getEntitiesWithHttpInfo(code, filters, limit, offset);
        return localVarResp.getData();
    }

    public RL getEntities(String code, Integer limit, Integer offset) throws QaseException {
        return getEntities(code, NO_FILTERS, limit, offset);
    }

    /**
     * Get all entities.
     * This method allows to retrieve all entities stored in selected project.
     *
     * @param code    Code of project, where to search entities. (required)
     * @param filters (optional)
     * @param limit   A number of entities in result set. (optional, default to 10)
     * @param offset  How many entities should be skipped. (optional, default to 0)
     * @return ApiResponse&lt;RL&gt;
     * @throws QaseException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A list of all entities. </td><td>  -  </td></tr>
     * </table>
     */
    public ApiResponse<RL> getEntitiesWithHttpInfo(String code, Object filters, Integer limit, Integer offset)
    throws QaseException {
        okhttp3.Call localVarCall = getEntitiesValidateBeforeCall(code, filters, limit, offset, null);
        Type localVarReturnType = new TypeToken<RL>() { }.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    public ApiResponse<RL> getEntitiesWithHttpInfo(String code, Integer limit, Integer offset)
        throws QaseException {
        return getEntitiesWithHttpInfo(code, NO_FILTERS, limit, offset);
    }

    /**
     * Get all entities. (asynchronously)
     * This method allows to retrieve all entities stored in selected project.
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
     * <tr><td> 200 </td><td> A list of all entities. </td><td>  -  </td></tr>
     * </table>
     */
    public okhttp3.Call getEntitiesAsync(
        String code, Object filters, Integer limit, Integer offset, final ApiCallback<RL> _callback
    ) throws QaseException {
        okhttp3.Call localVarCall = getEntitiesValidateBeforeCall(code, filters, limit, offset, _callback);
        Type localVarReturnType = new TypeToken<RL>() { }.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    public okhttp3.Call getEntitiesAsync(String code, Integer limit, Integer offset, final ApiCallback<RL> _callback)
    throws QaseException {
        return getEntitiesAsync(code, NO_FILTERS, limit, offset, _callback);
    }

    /**
     * Build call for resolveEntity
     *
     * @param code      Code of project, where to search entities. (required)
     * @param id        Identifier. (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws QaseException If fail to serialize the request body object
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A result. </td><td>  -  </td></tr>
     * </table>
     */
    public okhttp3.Call resolveEntityCall(String code, Integer id, final ApiCallback _callback) throws QaseException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = getEntityPath()
            + URL_PATH_SEPARATOR + localVarApiClient.escapeString(code)
            + URL_PATH_SEPARATOR + "resolve"
            + URL_PATH_SEPARATOR + localVarApiClient.escapeString(id.toString());

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();
        Map<String, String> localVarHeaderParams = new HashMap<>();
        Map<String, String> localVarCookieParams = new HashMap<>();
        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"application/json"};
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = { };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[]{"TokenAuth"};
        return localVarApiClient.buildCall(
            localVarPath,
            HttpMethod.PATCH.toString(),
            localVarQueryParams,
            localVarCollectionQueryParams,
            localVarPostBody,
            localVarHeaderParams,
            localVarCookieParams,
            localVarFormParams,
            localVarAuthNames,
            _callback
        );
    }

    /**
     * Resolve a specific entity.
     * This method allows to resolve a specific entity.
     *
     * @param code Code of project, where to search entities. (required)
     * @param id   Identifier. (required)
     * @return IdResponse
     * @throws QaseException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A result. </td><td>  -  </td></tr>
     * </table>
     */
    public IdResponse resolveEntity(String code, Integer id) throws QaseException {
        ApiResponse<IdResponse> localVarResp = resolveEntityWithHttpInfo(code, id);
        return localVarResp.getData();
    }

    /**
     * Resolve a specific entity.
     * This method allows to resolve a specific entity.
     *
     * @param code Code of project, where to search entities. (required)
     * @param id   Identifier. (required)
     * @return ApiResponse&lt;IdResponse&gt;
     * @throws QaseException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A result. </td><td>  -  </td></tr>
     * </table>
     */
    public ApiResponse<IdResponse> resolveEntityWithHttpInfo(String code, Integer id) throws QaseException {
        okhttp3.Call localVarCall = resolveEntityValidateBeforeCall(code, id, null);
        Type localVarReturnType = new TypeToken<IdResponse>() { }.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Resolve a specific entity. (asynchronously)
     * This method allows to resolve a specific entity.
     *
     * @param code      Code of project, where to search entities. (required)
     * @param id        Identifier. (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws QaseException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A result. </td><td>  -  </td></tr>
     * </table>
     */
    public okhttp3.Call resolveEntityAsync(String code, Integer id, final ApiCallback<IdResponse> _callback)
    throws QaseException {
        okhttp3.Call localVarCall = resolveEntityValidateBeforeCall(code, id, _callback);
        Type localVarReturnType = new TypeToken<IdResponse>() { }.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    /**
     * Build call for updateEntity
     *
     * @param code         Code of project, where to search entities. (required)
     * @param id           Identifier. (required)
     * @param entityUpdate (required)
     * @param _callback    Callback for upload/download progress
     * @return Call to execute
     * @throws QaseException If fail to serialize the request body object
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A result. </td><td>  -  </td></tr>
     * </table>
     */
    public okhttp3.Call updateEntityCall( // TODO: unhardcode string values
        String code, Integer id, U entityUpdate, final ApiCallback _callback
    ) throws QaseException {
        Object localVarPostBody = entityUpdate;

        // create path and map variables
        String localVarPath = getEntityPath()
            + URL_PATH_SEPARATOR + localVarApiClient.escapeString(code)
            + URL_PATH_SEPARATOR + localVarApiClient.escapeString(id.toString());

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();
        Map<String, String> localVarHeaderParams = new HashMap<>();
        Map<String, String> localVarCookieParams = new HashMap<>();
        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"application/json"};
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {"application/json"};
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[] {"TokenAuth"};
        return localVarApiClient.buildCall(
            localVarPath,
            HttpMethod.PATCH.toString(),
            localVarQueryParams,
            localVarCollectionQueryParams,
            localVarPostBody,
            localVarHeaderParams,
            localVarCookieParams,
            localVarFormParams,
            localVarAuthNames,
            _callback
        );
    }

    /**
     * Update entity.
     * This method updates a entity.
     *
     * @param code         Code of project, where to search entities. (required)
     * @param id           Identifier. (required)
     * @param entityUpdate (required)
     * @return IdResponse
     * @throws QaseException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A result. </td><td>  -  </td></tr>
     * </table>
     */
    public IdResponse updateEntity(String code, Integer id, U entityUpdate) throws QaseException {
        ApiResponse<IdResponse> localVarResp = updateEntityWithHttpInfo(code, id, entityUpdate);
        return localVarResp.getData();
    }

    /**
     * Update entity.
     * This method updates a entity.
     *
     * @param code         Code of project, where to search entities. (required)
     * @param id           Identifier. (required)
     * @param entityUpdate (required)
     * @return ApiResponse&lt;IdResponse&gt;
     * @throws QaseException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A result. </td><td>  -  </td></tr>
     * </table>
     */
    public ApiResponse<IdResponse> updateEntityWithHttpInfo(String code, Integer id, U entityUpdate)
    throws QaseException {
        okhttp3.Call localVarCall = updateEntityValidateBeforeCall(code, id, entityUpdate, null);
        Type localVarReturnType = new TypeToken<IdResponse>() {
        }.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Update entity. (asynchronously)
     * This method updates a entity.
     *
     * @param code         Code of project, where to search entities. (required)
     * @param id           Identifier. (required)
     * @param entityUpdate (required)
     * @param _callback    The callback to be executed when the API call finishes
     * @return The request call
     * @throws QaseException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A result. </td><td>  -  </td></tr>
     * </table>
     */
    public okhttp3.Call updateEntityAsync(
        String code, Integer id, U entityUpdate, final ApiCallback<IdResponse> _callback
    ) throws QaseException {
        okhttp3.Call localVarCall = updateEntityValidateBeforeCall(code, id, entityUpdate, _callback);
        Type localVarReturnType = new TypeToken<IdResponse>() { }.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    /**
     * Build call for updateEntityStatus
     *
     * @param code         Code of project, where to search entities. (required)
     * @param id           Identifier. (required)
     * @param entityStatus (required)
     * @param _callback    Callback for upload/download progress
     * @return Call to execute
     * @throws QaseException If fail to serialize the request body object
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A result. </td><td>  -  </td></tr>
     * </table>
     */
    public okhttp3.Call updateEntityStatusCall(String code, Integer id, S entityStatus, final ApiCallback _callback)
    throws QaseException {
        // create path and map variables
        String localVarPath = getEntityPath()
            + URL_PATH_SEPARATOR + localVarApiClient.escapeString(code)
            + URL_PATH_SEPARATOR + "status"
            + URL_PATH_SEPARATOR + localVarApiClient.escapeString(id.toString());

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();
        Map<String, String> localVarHeaderParams = new HashMap<>();
        Map<String, String> localVarCookieParams = new HashMap<>();
        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {"application/json"};
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {"application/json"};
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[]{"TokenAuth"};
        return localVarApiClient.buildCall(
            localVarPath,
            HttpMethod.PATCH.toString(),
            localVarQueryParams,
            localVarCollectionQueryParams,
            entityStatus,
            localVarHeaderParams,
            localVarCookieParams,
            localVarFormParams,
            localVarAuthNames,
            _callback
        );
    }

    /**
     * Update a specific entity status.
     * This method allows to update a specific entity status.
     *
     * @param code         Code of project, where to search entities. (required)
     * @param id           Identifier. (required)
     * @param entityStatus (required)
     * @return Response
     * @throws QaseException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A result. </td><td>  -  </td></tr>
     * </table>
     */
    public Response updateEntityStatus(String code, Integer id, S entityStatus) throws QaseException {
        ApiResponse<Response> localVarResp = updateEntityStatusWithHttpInfo(code, id, entityStatus);
        return localVarResp.getData();
    }

    /**
     * Update a specific entity status.
     * This method allows to update a specific entity status.
     *
     * @param code         Code of project, where to search entities. (required)
     * @param id           Identifier. (required)
     * @param entityStatus (required)
     * @return ApiResponse&lt;Response&gt;
     * @throws QaseException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A result. </td><td>  -  </td></tr>
     * </table>
     */
    public ApiResponse<Response> updateEntityStatusWithHttpInfo(String code, Integer id, S entityStatus)
    throws QaseException {
        okhttp3.Call localVarCall = updateEntityStatusValidateBeforeCall(code, id, entityStatus, null);
        Type localVarReturnType = new TypeToken<Response>() { }.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Update a specific entity status. (asynchronously)
     * This method allows to update a specific entity status.
     *
     * @param code         Code of project, where to search entities. (required)
     * @param id           Identifier. (required)
     * @param entityStatus (required)
     * @param _callback    The callback to be executed when the API call finishes
     * @return The request call
     * @throws QaseException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A result. </td><td>  -  </td></tr>
     * </table>
     */
    public okhttp3.Call updateEntityStatusAsync(
        String code, Integer id, S entityStatus, final ApiCallback<Response> _callback
    ) throws QaseException {
        okhttp3.Call localVarCall = updateEntityStatusValidateBeforeCall(code, id, entityStatus, _callback);
        Type localVarReturnType = new TypeToken<Response>() { }.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    /**
     *
     * */
    protected abstract String getEntityPath();

    @SuppressWarnings("rawtypes")
    private okhttp3.Call updateEntityValidateBeforeCall(
        String code, Integer id, U entityUpdate, final ApiCallback _callback
    ) throws QaseException {
        // verify the required parameter 'code' is set
        if (code == null) {
            throw new QaseException("Missing the required parameter 'code' when calling updateEntity(Async)");
        }

        // verify the required parameter 'id' is set
        if (id == null) {
            throw new QaseException("Missing the required parameter 'id' when calling updateEntity(Async)");
        }

        // verify the required parameter 'entityUpdate' is set
        if (entityUpdate == null) {
            throw new QaseException("Missing the required parameter 'entityUpdate' when calling updateEntity(Async)");
        }

        return updateEntityCall(code, id, entityUpdate, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call deleteEntityValidateBeforeCall(String code, Integer id, final ApiCallback _callback)
        throws QaseException {
        // verify the required parameter 'code' is set
        if (code == null) {
            throw new QaseException("Missing the required parameter 'code' when calling deleteEntity(Async)");
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new QaseException("Missing the required parameter 'id' when calling deleteEntity(Async)");
        }

        return deleteEntityCall(code, id, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getEntityValidateBeforeCall(String code, Integer id, final ApiCallback _callback)
        throws QaseException {
        // verify the required parameter 'code' is set
        if (code == null) {
            throw new QaseException("Missing the required parameter 'code' when calling getEntity(Async)");
        }

        // verify the required parameter 'id' is set
        if (id == null) {
            throw new QaseException("Missing the required parameter 'id' when calling getEntity(Async)");
        }

        return getEntityCall(code, id, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getEntitiesValidateBeforeCall(
        String code, Object filters, Integer limit, Integer offset, final ApiCallback _callback
    ) throws QaseException {
        // verify the required parameter 'code' is set
        if (code == null) {
            throw new QaseException("Missing the required parameter 'code' when calling getEntities(Async)");
        }

        return getEntitiesCall(code, filters, limit, offset, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call resolveEntityValidateBeforeCall(String code, Integer id, final ApiCallback _callback)
    throws QaseException {
        // verify the required parameter 'code' is set
        if (code == null) {
            throw new QaseException("Missing the required parameter 'code' when calling resolveEntity(Async)");
        }

        // verify the required parameter 'id' is set
        if (id == null) {
            throw new QaseException("Missing the required parameter 'id' when calling resolveEntity(Async)");
        }

        return resolveEntityCall(code, id, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call updateEntityStatusValidateBeforeCall(
        String code, Integer id, S entityStatus, final ApiCallback _callback
    ) throws QaseException {
        // verify the required parameter 'code' is set
        if (code == null) {
            throw new QaseException("Missing the required parameter 'code' when calling updateEntityStatus(Async)");
        }

        // verify the required parameter 'id' is set
        if (id == null) {
            throw new QaseException("Missing the required parameter 'id' when calling updateEntityStatus(Async)");
        }

        // verify the required parameter 'entityStatus' is set
        if (entityStatus == null) {
            throw new QaseException(
                "Missing the required parameter 'entityStatus' when calling updateEntityStatus(Async)"
            );
        }

        return updateEntityStatusCall(code, id, entityStatus, _callback);
    }
}
