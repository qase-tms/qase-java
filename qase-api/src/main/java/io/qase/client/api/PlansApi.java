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

import io.qase.api.exceptions.QaseException;
import io.qase.client.ApiCallback;
import io.qase.client.ApiClient;
import io.qase.client.ApiResponse;
import io.qase.client.model.*;

public class PlansApi extends AbstractEntityApi<PlanCreate, PlanResponse, PlanListResponse, PlanUpdate, Object> {

    public PlansApi() {
        super();
    }

    public PlansApi(ApiClient apiClient) {
        super(apiClient);
    }

    /**
     * Build call for createPlan
     *
     * @param code       Code of project, where to search entities. (required)
     * @param planCreate (required)
     * @param _callback  Callback for upload/download progress
     * @return Call to execute
     * @throws QaseException If fail to serialize the request body object
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A result. </td><td>  -  </td></tr>
     * </table>
     */
    public okhttp3.Call createPlanCall(String code, PlanCreate planCreate, final ApiCallback _callback)
    throws QaseException {
        return createEntityCall(code, planCreate, _callback);
    }

    /**
     * Create a new plan.
     * This method allows to create a plan in selected project.
     *
     * @param code       Code of project, where to search entities. (required)
     * @param planCreate (required)
     * @return IdResponse
     * @throws QaseException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A result. </td><td>  -  </td></tr>
     * </table>
     */
    public IdResponse createPlan(String code, PlanCreate planCreate) throws QaseException {
        return createEntity(code, planCreate);
    }

    /**
     * Create a new plan.
     * This method allows to create a plan in selected project.
     *
     * @param code       Code of project, where to search entities. (required)
     * @param planCreate (required)
     * @return ApiResponse&lt;IdResponse&gt;
     * @throws QaseException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A result. </td><td>  -  </td></tr>
     * </table>
     */
    public ApiResponse<IdResponse> createPlanWithHttpInfo(String code, PlanCreate planCreate) throws QaseException {
        return createEntityWithHttpInfo(code, planCreate);
    }

    /**
     * Create a new plan. (asynchronously)
     * This method allows to create a plan in selected project.
     *
     * @param code       Code of project, where to search entities. (required)
     * @param planCreate (required)
     * @param _callback  The callback to be executed when the API call finishes
     * @return The request call
     * @throws QaseException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A result. </td><td>  -  </td></tr>
     * </table>
     */
    public okhttp3.Call createPlanAsync(String code, PlanCreate planCreate, final ApiCallback<IdResponse> _callback)
    throws QaseException {
        return createEntityAsync(code, planCreate, _callback);
    }

    /**
     * Build call for deletePlan
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
    public okhttp3.Call deletePlanCall(String code, Integer id, final ApiCallback _callback) throws QaseException {
        return deleteEntityCall(code, id, _callback);
    }

    /**
     * Delete plan.
     * This method completely deletes a plan from repository.
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
    public IdResponse deletePlan(String code, Integer id) throws QaseException {
        return deleteEntity(code, id);
    }

    /**
     * Delete plan.
     * This method completely deletes a plan from repository.
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
    public ApiResponse<IdResponse> deletePlanWithHttpInfo(String code, Integer id) throws QaseException {
        return deleteEntityWithHttpInfo(code, id);
    }

    /**
     * Delete plan. (asynchronously)
     * This method completely deletes a plan from repository.
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
    public okhttp3.Call deletePlanAsync(String code, Integer id, final ApiCallback<IdResponse> _callback)
    throws QaseException {
        return deleteEntityAsync(code, id, _callback);
    }

    /**
     * Build call for getPlan
     *
     * @param code      Code of project, where to search entities. (required)
     * @param id        Identifier. (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws QaseException If fail to serialize the request body object
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A plan. </td><td>  -  </td></tr>
     * </table>
     */
    public okhttp3.Call getPlanCall(String code, Integer id, final ApiCallback _callback) throws QaseException {
        return getEntityCall(code, id, _callback);
    }

    /**
     * Get a specific plan.
     * This method allows to retrieve a specific plan.
     *
     * @param code Code of project, where to search entities. (required)
     * @param id   Identifier. (required)
     * @return PlanResponse
     * @throws QaseException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A plan. </td><td>  -  </td></tr>
     * </table>
     */
    public PlanResponse getPlan(String code, Integer id) throws QaseException {
        return getEntity(code, id);
    }

    /**
     * Get a specific plan.
     * This method allows to retrieve a specific plan.
     *
     * @param code Code of project, where to search entities. (required)
     * @param id   Identifier. (required)
     * @return ApiResponse&lt;PlanResponse&gt;
     * @throws QaseException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A plan. </td><td>  -  </td></tr>
     * </table>
     */
    public ApiResponse<PlanResponse> getPlanWithHttpInfo(String code, Integer id) throws QaseException {
        return getEntityWithHttpInfo(code, id);
    }

    /**
     * Get a specific plan. (asynchronously)
     * This method allows to retrieve a specific plan.
     *
     * @param code      Code of project, where to search entities. (required)
     * @param id        Identifier. (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws QaseException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A plan. </td><td>  -  </td></tr>
     * </table>
     */
    public okhttp3.Call getPlanAsync(String code, Integer id, final ApiCallback<PlanResponse> _callback)
    throws QaseException {
        return getEntityAsync(code, id, _callback);
    }

    /**
     * Build call for getPlans
     *
     * @param code      Code of project, where to search entities. (required)
     * @param limit     A number of entities in result set. (optional, default to 10)
     * @param offset    How many entities should be skipped. (optional, default to 0)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws QaseException If fail to serialize the request body object
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A list of all plans. </td><td>  -  </td></tr>
     * </table>
     */
    public okhttp3.Call getPlansCall(String code, Integer limit, Integer offset, final ApiCallback _callback)
    throws QaseException {
        return getEntitiesCall(code, limit, offset, _callback);
    }

    /**
     * Get all plans.
     * This method allows to retrieve all plans stored in selected project.
     *
     * @param code   Code of project, where to search entities. (required)
     * @param limit  A number of entities in result set. (optional, default to 10)
     * @param offset How many entities should be skipped. (optional, default to 0)
     * @return PlanListResponse
     * @throws QaseException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A list of all plans. </td><td>  -  </td></tr>
     * </table>
     */
    public PlanListResponse getPlans(String code, Integer limit, Integer offset) throws QaseException {
        return getEntities(code, limit, offset);
    }

    /**
     * Get all plans.
     * This method allows to retrieve all plans stored in selected project.
     *
     * @param code   Code of project, where to search entities. (required)
     * @param limit  A number of entities in result set. (optional, default to 10)
     * @param offset How many entities should be skipped. (optional, default to 0)
     * @return ApiResponse&lt;PlanListResponse&gt;
     * @throws QaseException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A list of all plans. </td><td>  -  </td></tr>
     * </table>
     */
    public ApiResponse<PlanListResponse> getPlansWithHttpInfo(String code, Integer limit, Integer offset)
    throws QaseException {
        return getEntitiesWithHttpInfo(code, limit, offset);
    }

    /**
     * Get all plans. (asynchronously)
     * This method allows to retrieve all plans stored in selected project.
     *
     * @param code      Code of project, where to search entities. (required)
     * @param limit     A number of entities in result set. (optional, default to 10)
     * @param offset    How many entities should be skipped. (optional, default to 0)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws QaseException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A list of all plans. </td><td>  -  </td></tr>
     * </table>
     */
    public okhttp3.Call getPlansAsync(
        String code, Integer limit, Integer offset, final ApiCallback<PlanListResponse> _callback
    ) throws QaseException {
        return getEntitiesAsync(code, limit, offset, _callback);
    }

    /**
     * Build call for updatePlan
     *
     * @param code       Code of project, where to search entities. (required)
     * @param id         Identifier. (required)
     * @param planUpdate (required)
     * @param _callback  Callback for upload/download progress
     * @return Call to execute
     * @throws QaseException If fail to serialize the request body object
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A result. </td><td>  -  </td></tr>
     * </table>
     */
    public okhttp3.Call updatePlanCall(String code, Integer id, PlanUpdate planUpdate, final ApiCallback _callback)
    throws QaseException {
        return updateEntityCall(code, id, planUpdate, _callback);
    }

    /**
     * Update plan.
     * This method updates a plan.
     *
     * @param code       Code of project, where to search entities. (required)
     * @param id         Identifier. (required)
     * @param planUpdate (required)
     * @return IdResponse
     * @throws QaseException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A result. </td><td>  -  </td></tr>
     * </table>
     */
    public IdResponse updatePlan(String code, Integer id, PlanUpdate planUpdate) throws QaseException {
        return updateEntity(code, id, planUpdate);
    }

    /**
     * Update plan.
     * This method updates a plan.
     *
     * @param code       Code of project, where to search entities. (required)
     * @param id         Identifier. (required)
     * @param planUpdate (required)
     * @return ApiResponse&lt;IdResponse&gt;
     * @throws QaseException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A result. </td><td>  -  </td></tr>
     * </table>
     */
    public ApiResponse<IdResponse> updatePlanWithHttpInfo(String code, Integer id, PlanUpdate planUpdate)
    throws QaseException {
        return updateEntityWithHttpInfo(code, id, planUpdate);
    }

    /**
     * Update plan. (asynchronously)
     * This method updates a plan.
     *
     * @param code       Code of project, where to search entities. (required)
     * @param id         Identifier. (required)
     * @param planUpdate (required)
     * @param _callback  The callback to be executed when the API call finishes
     * @return The request call
     * @throws QaseException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details <table summary="Response Details" border="1">
     * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
     * <tr><td> 200 </td><td> A result. </td><td>  -  </td></tr>
     * </table>
     */
    public okhttp3.Call updatePlanAsync(
        String code, Integer id, PlanUpdate planUpdate, final ApiCallback<IdResponse> _callback
    ) throws QaseException {
        return updateEntityAsync(code, id, planUpdate, _callback);
    }

    @Override
    protected String getEntityRootPathSegment() {
        return "plan";
    }
}
