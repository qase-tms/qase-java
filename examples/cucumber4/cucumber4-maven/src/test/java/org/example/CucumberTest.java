package org.example;

import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import org.testng.annotations.Test;

@Test
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "org.example",
        plugin = {
                "io.qase.cucumber4.QaseEventListener",
        }
)
public class CucumberTest extends AbstractTestNGCucumberTests {
}
