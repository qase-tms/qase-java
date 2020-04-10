package io.qase.api.services.v1;

import io.qase.api.QaseApiClient;
import io.qase.api.inner.RouteFilter;
import io.qase.api.models.v1.milestones.Milestone;
import io.qase.api.models.v1.milestones.Milestones;
import io.qase.api.models.v1.milestones.NewMilestone;
import io.qase.api.services.MilestoneService;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.singletonMap;

public final class MilestoneServiceImpl implements MilestoneService {
    private final QaseApiClient qaseApiClient;

    public MilestoneServiceImpl(QaseApiClient qaseApiClient) {
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
    @Override
    public Milestones getAll(String projectCode, int limit, int offset, RouteFilter filter) {
        return qaseApiClient.get(Milestones.class, "/milestone/{code}", singletonMap("code", projectCode), filter, limit, offset);
    }

    @Override
    public Milestones getAll(String projectCode, RouteFilter filter) {
        return this.getAll(projectCode, 100, 0, filter);
    }

    @Override
    public Milestones getAll(String projectCode) {
        return this.getAll(projectCode, 100, 0, filter());
    }

    @Override
    public Milestone get(String projectCode, long milestoneId) {
        Map<String, Object> routeParams = new HashMap<>();
        routeParams.put("code", projectCode);
        routeParams.put("id", milestoneId);
        return qaseApiClient.get(Milestone.class, "/milestone/{code}/{id}", routeParams);

    }

    /**
     * This method is used to create a new milestone through API.
     *
     * @param projectCode Project code
     * @param title       Milestone title
     * @param description Milestone description
     * @return
     */
    @Override
    public long create(String projectCode, String title, String description) {
        NewMilestone createUpdateMilestonesRequest = new NewMilestone(title);
        createUpdateMilestonesRequest.setDescription(description);
        return qaseApiClient.post(
                Milestone.class,
                "/milestone/{code}",
                singletonMap("code", projectCode),
                createUpdateMilestonesRequest).getId();
    }

    /**
     * This method is used to create a new milestone through API.
     *
     * @param projectCode Project code
     * @param title       Milestone title
     * @return
     */
    @Override
    public long create(String projectCode, String title) {
        return create(projectCode, title, null);
    }

    /**
     * This method completely deletes a milestone from repository
     *
     * @param projectCode Project code
     * @param id          Milestone id
     * @return
     */
    @Override
    public boolean delete(String projectCode, long id) {
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
    @Override
    public long update(String projectCode, long id, String title, String description) {
        NewMilestone createUpdateMilestonesRequest = new NewMilestone(title);
        createUpdateMilestonesRequest.setDescription(description);
        Map<String, Object> routeParams = new HashMap<>();
        routeParams.put("code", projectCode);
        routeParams.put("id", id);
        return qaseApiClient.patch(Milestone.class,
                "/milestone/{code}/{id}",
                routeParams,
                createUpdateMilestonesRequest
        ).getId();
    }

    /**
     * This method is used to update a milestone through API. You should provide an object with a list of fields you want to update in a payload.
     *
     * @param projectCode Project code
     * @param id          Milestone id
     * @param title       Milestone title
     * @return
     */
    @Override
    public long update(String projectCode, long id, String title) {
        return this.update(projectCode, id, title, null);
    }
}
