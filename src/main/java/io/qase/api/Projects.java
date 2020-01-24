package io.qase.api;

import io.qase.api.enums.Access;
import io.qase.api.exceptions.QaseException;
import io.qase.api.models.v1.projects.add.CreateUpdateProjectRequest;
import io.qase.api.models.v1.projects.add.CreateUpdateProjectResponse;
import io.qase.api.models.v1.projects.get.ProjectResponse;
import io.qase.api.models.v1.projects.get_all.ProjectsResponse;

import java.util.Collections;


public final class Projects {
    private final QaseApiClient qaseApiClient;

    Projects(QaseApiClient qaseApiClient) {
        this.qaseApiClient = qaseApiClient;
    }

    /**
     * This method allows to retrieve all test cases stored in selected project.
     *
     * @return
     */
    public ProjectsResponse getAll() {
        return this.getAll(100, 0);
    }

    /**
     * This method allows to retrieve all test cases stored in selected project.
     *
     * @param limit  A number of projects in result set
     * @param offset How many projects should be skipped
     * @return
     */
    public ProjectsResponse getAll(int limit, int offset) {
        return qaseApiClient.get(ProjectsResponse.class, "/project", limit, offset);
    }

    /**
     * This method allows to retrieve a specific project.
     *
     * @param code Project CODE is required to retrieve specific project
     * @return
     */
    public ProjectResponse get(String code) {
        return qaseApiClient.get(ProjectResponse.class, "/project/{code}", Collections.singletonMap("code", code));
    }

    /**
     * This method is used to create a new project through API.
     *
     * @param code        Project code
     * @param title       Test suite title.
     * @param description Project description.
     * @param access      Possible value: all, group, none. Default none
     * @param groupHash   Team group hash. Required if access param is set to group.
     * @return
     */
    public CreateUpdateProjectResponse create(String code, String title, String description, Access access, String groupHash) {
        if (!code.matches("[A-z]{2,6}")) {
            throw new QaseException("The code must be from 2 to 6 latin characters");
        }
        if (access == Access.group && groupHash == null) {
            throw new QaseException("Team group hash required if access param is set to group");
        }
        CreateUpdateProjectRequest createUpdateProjectRequest = new CreateUpdateProjectRequest(code, title);
        createUpdateProjectRequest.setAccess(access);
        createUpdateProjectRequest.setDescription(description);
        return qaseApiClient.post(CreateUpdateProjectResponse.class, "/project", createUpdateProjectRequest);
    }

    /**
     * This method is used to create a new project through API.
     *
     * @param code  Project code
     * @param title Test suite title.
     * @return
     */
    public CreateUpdateProjectResponse create(String code, String title) {
        return this.create(code, title, null, Access.none, null);
    }

    /**
     * This method is used to create a new project through API.
     *
     * @param code        Project code
     * @param title       Test suite title.
     * @param description Project description.
     * @return
     */
    public CreateUpdateProjectResponse create(String code, String title, String description) {
        return this.create(code, title, description, Access.none, null);
    }
}
