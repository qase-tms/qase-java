package io.qase.commons.hooks;

import io.qase.commons.logger.Logger;
import io.qase.commons.models.domain.TestResult;


import java.util.List;
import java.util.function.BiConsumer;

public class HooksManager {
    private static final Logger logger = Logger.getInstance();
    private final List<HooksListener> listeners;

    public HooksManager(final List<HooksListener> listeners) {
        this.listeners = listeners;
    }

    public void beforeTestStop(final TestResult result) {
        runSafelyMethod(listeners, HooksListener::beforeTestStop, result);
    }

    protected <T extends DefaultListener, S> void runSafelyMethod(final List<T> listeners,
                                                                  final BiConsumer<T, S> method,
                                                                  final S object) {
        listeners.forEach(listener -> {
            try {
                method.accept(listener, object);
            } catch (Exception e) {
                logger.error("Could not invoke listener method", e);
            }
        });
    }

    public static HooksManager getDefaultHooksManager() {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        return new HooksManager(HooksServiceLoader.load(HooksListener.class, classLoader));
    }
}
