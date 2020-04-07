package io.qase.api.services;

import io.qase.api.models.v1.team.User;
import io.qase.api.models.v1.team.Users;

public interface TeamService {

    Users getAll(int limit, int offset);

    Users getAll();

    User get(long id);
}
