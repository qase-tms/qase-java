package io.qase.commons.hooks;

import io.qase.commons.models.domain.TestResult;

public interface HooksListener extends DefaultListener {
    default void beforeTestStop(final TestResult result) {
        //do nothing
    }
}
