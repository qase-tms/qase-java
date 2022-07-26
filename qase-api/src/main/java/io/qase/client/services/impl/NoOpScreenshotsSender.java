package io.qase.client.services.impl;

import io.qase.client.services.ScreenshotsSender;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NoOpScreenshotsSender implements ScreenshotsSender { // TODO: replace the implementation

    @Override
    public void sendScreenshotsIfPermitted() {
        log.warn("This implementation supposes doing nothing when asked to send the screenshots.");
    }
}
