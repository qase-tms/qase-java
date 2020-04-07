package io.qase.api.services;

import io.qase.api.enums.Access;
import io.qase.api.models.v1.projects.Project;
import io.qase.api.models.v1.projects.Projects;


public interface ProjectService {
    Projects getAll();

    /**
     * This method allows to retrieve all test cases stored in selected project.
     *
     * @param limit  A number of projects in result set
     * @param offset How many projects should be skipped
     * @return
     */
    Projects getAll(int limit, int offset);

    /**
     * This method allows to retrieve a specific project.
     *
     * @param code Project CODE is required to retrieve specific project
     * @return
     */
    Project get(String code);

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
    String create(String code, String title, String description, Access access, String groupHash);

    /**
     * This method is used to create a new project through API.
     *
     * @param code  Project code
     * @param title Test suite title.
     * @return
     */
    String create(String code, String title);

    /**
     * This method is used to create a new project through API.
     *
     * @param code        Project code
     * @param title       Test suite title.
     * @param description Project description.
     * @return
     */
    String create(String code, String title, String description);
}
