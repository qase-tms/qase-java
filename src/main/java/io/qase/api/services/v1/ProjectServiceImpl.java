package io.qase.api.services.v1;

import io.qase.api.QaseApiClient;
import io.qase.api.enums.Access;
import io.qase.api.exceptions.QaseException;
import io.qase.api.models.v1.projects.NewProject;
import io.qase.api.models.v1.projects.Project;
import io.qase.api.models.v1.projects.Projects;
import io.qase.api.services.ProjectService;

import java.util.Collections;


public final class ProjectServiceImpl implements ProjectService {
    private final QaseApiClient qaseApiClient;

    public ProjectServiceImpl(QaseApiClient qaseApiClient) {
        this.qaseApiClient = qaseApiClient;
    }

    @Override
    public Projects getAll() {
        return this.getAll(100, 0);
    }

    @Override
    public Projects getAll(int limit, int offset) {
        return qaseApiClient.get(Projects.class, "/project", limit, offset);
    }

    @Override
    public Project get(String code) {
        return qaseApiClient.get(Project.class, "/project/{code}", Collections.singletonMap("code", code));
    }

    @Override
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
        createUpdateProjectRequest.setGroup(groupHash);
        return qaseApiClient.post(Project.class, "/project", createUpdateProjectRequest).getCode();
    }

    @Override
    public String create(String code, String title) {
        return this.create(code, title, null, Access.none, null);
    }

    @Override
    public String create(String code, String title, String description) {
        return this.create(code, title, description, Access.none, null);
    }
}
