package io.qase.api.enums;

public enum Automation {
    is_not_automated("is-not-automated"), automated("automated"), to_be_automated("to-be-automated");

    private final String name;

    Automation(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
