package io.qase.api;

import io.qase.api.enums.Access;
import io.qase.api.exceptions.QaseException;
import io.qase.api.models.v1.projects.NewProject;
import io.qase.api.models.v1.projects.Project;
import io.qase.api.models.v1.projects.Projects;

import java.util.Collections;


public final class ProjectService {
    private final QaseApiClient qaseApiClient;

    ProjectService(QaseApiClient qaseApiClient) {
        this.qaseApiClient = qaseApiClient;
    }

    /**
     * This method allows to retrieve all test cases stored in selected project.
     *
     * @return
     */
    public Projects getAll() {
        return this.getAll(100, 0);
    }

    /**
     * This method allows to retrieve all test cases stored in selected project.
     *
     * @param limit  A number of projects in result set
     * @param offset How many projects should be skipped
     * @return
     */
    public Projects getAll(int limit, int offset) {
        return qaseApiClient.get(Projects.class, "/project", limit, offset);
    }

    /**
     * This method allows to retrieve a specific project.
     *
     * @param code Project CODE is required to retrieve specific project
     * @return
     */
    public Project get(String code) {
        return qaseApiClient.get(Project.class, "/project/{code}", Collections.singletonMap("code", code));
    }

    /**
     * This method is used to create a new project through API.
     *
     * @param code        Project code
     * @param title       Test suite title.
     * @param description Project description.
     * @param access      Possible value: all, group, none. Default none
     * @param groupHash   User group hash. Required if access param is set to group.
     * @return
     */
    public String create(String code, String title, String description, Access access, String groupHash) {
        if (!code.matches("[A-z]{2,6}")) {
            throw new QaseException("The code must be from 2 to 6 latin characters");
        }
        if (access == Access.group && groupHash == null) {
            throw new QaseException("User group hash required if access param is set to group");
        }
        NewProject createUpdateProjectRequest = new NewProject(code, title);
        createUpdateProjectRequest.setAccess(access);
        createUpdateProjectRequest.setDescription(description);
        return qaseApiClient.post(Project.class, "/project", createUpdateProjectRequest).getCode();
    }

    /**
     * This method is used to create a new project through API.
     *
     * @param code  Project code
     * @param title Test suite title.
     * @return
     */
    public String create(String code, String title) {
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
    public String create(String code, String title, String description) {
        return this.create(code, title, description, Access.none, null);
    }
}
