package io.qase.cucumber5;


import io.cucumber.java.en.Given;

import java.util.concurrent.TimeUnit;

public class Steps {
    @Given("success step")
    public void success() {
    }

    @Given("success step with parameter {string}")
    public void success_step_with_parameter(String string) {
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
