package io.qase.api.services;

import io.qase.client.model.ResultCreate;

import java.util.function.Consumer;

public interface QaseTestCaseListener {

    void onTestCasesSetFinished();

    void onTestCaseStarted();

    /**
     * @param resultCreateConfigurer this consumer will be given with an instance of {@link ResultCreate}
     *                               to configure before saving or sending the result.
     * */
    void onTestCaseFinished(Consumer<ResultCreate> resultCreateConfigurer);
}
