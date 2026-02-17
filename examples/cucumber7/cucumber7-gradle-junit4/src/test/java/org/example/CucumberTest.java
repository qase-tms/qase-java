package org.example;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "org.example",
        plugin = {
                "io.qase.cucumber7.QaseEventListener",
        }
)
public class CucumberTest {
}
