package io.qase.cucumber4;


import io.cucumber.java.en.Given;

import java.util.concurrent.TimeUnit;

public class Steps {
    @Given("success step")
    public void success() {
    }

    @Given("timeout {int} seconds")
    public void success(int integer) throws InterruptedException {
        TimeUnit.SECONDS.sleep(integer);
    }

    @Given("failed step")
    public void failed() {
        throw new AssertionError();
    }
}
