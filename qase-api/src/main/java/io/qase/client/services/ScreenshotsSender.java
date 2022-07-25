package io.qase.client.services;

import io.qase.api.exceptions.QaseException;

public interface ScreenshotsSender {

    void sendScreenshotsIfPermitted() throws QaseException;
}
