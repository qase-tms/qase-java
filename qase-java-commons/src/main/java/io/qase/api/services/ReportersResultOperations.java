package io.qase.api.services;

import io.qase.client.v1.models.ResultCreate;

public interface ReportersResultOperations {

    void addBulkResult(ResultCreate resultCreate);

    void send(ResultCreate resultCreate);

    void sendBulkResult();
}
