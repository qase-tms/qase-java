package io.qase.junit4;

import io.qase.commons.logger.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.junit.runner.notification.RunNotifier;

@Aspect
public final class QaseJunit4Aspects {
    private static final Logger logger = Logger.getInstance();
    private final QaseListener qaseListener = ListenerFactory.getInstance();

    @After("execution(org.junit.runner.notification.RunNotifier.new())")
    public void addListener(JoinPoint point) {
        logger.debug("Add listener");
        RunNotifier notifier = (RunNotifier) point.getThis();
        notifier.removeListener(qaseListener);
        notifier.addListener(qaseListener);
    }
}
