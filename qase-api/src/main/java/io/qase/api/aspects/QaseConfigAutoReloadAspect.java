package io.qase.api.aspects;

import io.qase.api.config.QaseConfig;
import org.aeonbits.owner.Reloadable;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Aspect
public class QaseConfigAutoReloadAspect { // TODO: cover the aspect with tests

    private static final Collection<QaseConfig> TRACKED_CONFIG_INSTANCES = new ArrayList<>();

    private static final Lock CONFIG_INSTANCES_LOCK = new ReentrantLock(true);

    @Pointcut("call(public static java.lang.String java.lang.System.setProperty(*,*)) || call(public static void java.lang.System.setProperties(*))")
    public void systemPropertiesUpdated() {

    }

    @Pointcut("call(public static * org.aeonbits.owner.ConfigFactory.create(..))")
    public void configurationCreated() {

    }

    @After("systemPropertiesUpdated()")
    public void updateQaseConfig() {
        CONFIG_INSTANCES_LOCK.lock();
        try {
            TRACKED_CONFIG_INSTANCES.parallelStream().forEach(Reloadable::reload);
        } finally {
            CONFIG_INSTANCES_LOCK.unlock();
        }
    }

    @AfterReturning(
        value = "configurationCreated()",
        returning = "config"
    )
    public void addConfigInstanceToTracked(Object config) {
        if (!(config instanceof QaseConfig)) {
            return;
        }

        CONFIG_INSTANCES_LOCK.lock();
        try {
            TRACKED_CONFIG_INSTANCES.add((QaseConfig) config);
        } finally {
            CONFIG_INSTANCES_LOCK.unlock();
        }
    }
}
