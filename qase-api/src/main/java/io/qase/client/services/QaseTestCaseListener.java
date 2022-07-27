package io.qase.client.services;

import io.qase.client.model.ResultCreate;

public interface QaseTestCaseListener {

    void reportResults(); // TODO: rename method to onTestCaseSetFinished

    void onTestCaseStarted();

    void onTestCaseFinished(ResultCreate resultCreate);

    void setupReporterName(String reporterName);
}
