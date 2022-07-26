package io.qase.cucumber3;

import cucumber.api.event.EventPublisher;
import cucumber.api.formatter.Formatter;

import static io.qase.configuration.QaseModule.INJECTOR;

public class QaseEventListenerDelegator implements Formatter {

    private final QaseEventListener qaseEventListener;

    public QaseEventListenerDelegator() {
        qaseEventListener = INJECTOR.getInstance(QaseEventListener.class);
    }

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        qaseEventListener.setEventPublisher(publisher);
    }
}
