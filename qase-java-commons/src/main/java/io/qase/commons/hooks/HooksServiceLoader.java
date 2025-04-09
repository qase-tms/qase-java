package io.qase.commons.hooks;

import io.qase.commons.logger.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

public class HooksServiceLoader {
    private static final Logger logger = Logger.getInstance();

    private HooksServiceLoader() {
        throw new IllegalStateException("Do not have instance");
    }

    public static <T> List<T> load(final Class<T> type, final ClassLoader classLoader) {
        final List<T> loaded = new ArrayList<>();
        final Iterator<T> iterator = ServiceLoader.load(type, classLoader).iterator();
        while (nextSafely(iterator)) {
            try {
                final T next = iterator.next();
                loaded.add(next);
                logger.debug("Found type %s", type);
            } catch (Exception e) {
                logger.error("Could not load listener %s: %s", type, e.getMessage());
            }
        }
        return loaded;
    }

    private static boolean nextSafely(final Iterator iterator) {
        try {
            return iterator.hasNext();
        } catch (Exception e) {
            logger.error("nextSafely failed", e);
            return false;
        }
    }
}
