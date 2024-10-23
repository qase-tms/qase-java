package io.qase.junit4;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.junit.runner.notification.RunNotifier;

@Aspect
public final class QaseJunit4Aspects {
    private final QaseListener qaseListener = ListenerFactory.getInstance();

    @After("execution(org.junit.runner.notification.RunNotifier.new())")
    public void addListener(JoinPoint point) {
        System.out.println("Add listener");
        RunNotifier notifier = (RunNotifier) point.getThis();
        notifier.removeListener(qaseListener);
        notifier.addListener(qaseListener);
    }
}
