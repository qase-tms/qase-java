package io.qase.junit4;

public class ListenerFactory {
    private static QaseListener instance;

    private ListenerFactory() {
    }

    public static synchronized QaseListener getInstance() {
        if (instance == null) {
            instance = new QaseListener();
        }
        return instance;
    }
}
