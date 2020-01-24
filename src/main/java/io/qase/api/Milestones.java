package io.qase.api;

import io.qase.api.enums.Filters;
import io.qase.api.inner.RouteFilter;
import io.qase.api.models.v1.milestones.add.CreateUpdateMilestoneResponse;
import io.qase.api.models.v1.milestones.add.CreateUpdateMilestonesRequest;
import io.qase.api.models.v1.milestones.get.MilestoneResponse;
import io.qase.api.models.v1.milestones.get_all.MilestonesResponse;

import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.singletonMap;

public final class Milestones {
    private final QaseApiClient qaseApiClient;

    public Milestones(QaseApiClient qaseApiClient) {
        this.qaseApiClient = qaseApiClient;
    }

    /**
     * This method allows to retrieve all milestones stored in selected project.
     *
     * @param projectCode Project code
     * @param limit       A number of milestones in result set
     * @param offset      How many milestones should be skipped
     * @return
     */
    public MilestonesResponse getAll(String projectCode, int limit, int offset, Filter filter) {
        return qaseApiClient.get(MilestonesResponse.class, "/milestone/{code}", singletonMap("code", projectCode), filter, limit, offset);
    }

    public MilestonesResponse getAll(String projectCode, Filter filter) {
        return this.getAll(projectCode, 100, 0, filter);
    }

    public MilestonesResponse getAll(String projectCode) {
        return this.getAll(projectCode, 100, 0, new Filter());
    }

    public MilestoneResponse get(String projectCode, String milestoneId) {
        Map<String, Object> routeParams = new HashMap<>();
        routeParams.put("code", projectCode);
        routeParams.put("id", milestoneId);
        return qaseApiClient.get(MilestoneResponse.class, "/milestone/{code}/{id}", routeParams);

    }

    public Filter filter() {
        return new Filter();
    }

    /**
     * This method is used to create a new milestone through API.
     *
     * @param projectCode Project code
     * @param title       Milestone title
     * @param description Milestone description
     * @return
     */
    public CreateUpdateMilestoneResponse create(String projectCode, String title, String description) {
        CreateUpdateMilestonesRequest createUpdateMilestonesRequest = new CreateUpdateMilestonesRequest(title);
        createUpdateMilestonesRequest.setDescription(description);
        return qaseApiClient.post(
                CreateUpdateMilestoneResponse.class,
                "/milestone/{code}",
                singletonMap("code", projectCode),
                createUpdateMilestonesRequest);

    }

    /**
     * This method is used to create a new milestone through API.
     *
     * @param projectCode Project code
     * @param title       Milestone title
     * @return
     */
    public CreateUpdateMilestoneResponse create(String projectCode, String title) {
        return create(projectCode, title, null);
    }

    /**
     * This method completely deletes a milestone from repository
     *
     * @param projectCode Project code
     * @param id          Milestone id
     * @return
     */
    public boolean delete(String projectCode, String id) {
        Map<String, Object> routeParams = new HashMap<>();
        routeParams.put("code", projectCode);
        routeParams.put("id", id);
        return (boolean) qaseApiClient.delete("/milestone/{code}/{id}", routeParams).get("status");
    }

    /**
     * This method is used to update a milestone through API. You should provide an object with a list of fields you want to update in a payload.
     *
     * @param projectCode Project code
     * @param id          Milestone id
     * @param title       Milestone title
     * @param description Milestone description
     * @return
     */
    public CreateUpdateMilestoneResponse update(String projectCode, String id, String title, String description) {
        CreateUpdateMilestonesRequest createUpdateMilestonesRequest = new CreateUpdateMilestonesRequest(title);
        createUpdateMilestonesRequest.setDescription(description);
        Map<String, Object> routeParams = new HashMap<>();
        routeParams.put("code", projectCode);
        routeParams.put("id", id);
        return qaseApiClient.patch(CreateUpdateMilestoneResponse.class,
                "/milestone/{code}/{id}",
                routeParams,
                createUpdateMilestonesRequest
        );
    }

    /**
     * This method is used to update a milestone through API. You should provide an object with a list of fields you want to update in a payload.
     *
     * @param projectCode Project code
     * @param id          Milestone id
     * @param title       Milestone title
     * @return
     */
    public CreateUpdateMilestoneResponse update(String projectCode, String id, String title) {
        return this.update(projectCode, id, title, null);
    }

    public static class Filter implements RouteFilter {
        private final Map<Filters, String> filters = new EnumMap<>(Filters.class);

        private Filter() {
        }

        public Map<Filters, String> getFilters() {
            return Collections.unmodifiableMap(filters);
        }

        /**
         * String that will be used to search by name
         *
         * @param search
         * @return
         */
        public Filter search(String search) {
            filters.put(Filters.search, search);
            return this;
        }
    }
}
