package org.example;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import org.testng.annotations.Test;

@Test
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "org.example",
        plugin = {
                "io.qase.cucumber3.QaseEventListener",
        }
)
public class CucumberTest extends AbstractTestNGCucumberTests {
}
