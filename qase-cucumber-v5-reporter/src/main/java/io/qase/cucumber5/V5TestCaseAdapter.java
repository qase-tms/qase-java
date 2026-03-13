package io.qase.cucumber5;

import io.qase.commons.cucumber.CucumberTestCaseAdapter;
import io.cucumber.plugin.event.TestCase;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

class V5TestCaseAdapter implements CucumberTestCaseAdapter {
    private final TestCase testCase;

    V5TestCaseAdapter(TestCase testCase) {
        this.testCase = testCase;
    }

    @Override
    public List<String> getTags() {
        return testCase.getTags();
    }

    @Override
    public String getName() {
        return testCase.getName();
    }

    @Override
    public List<String> getUriPathParts() {
        return Arrays.asList(testCase.getUri().toString().split("/"));
    }

    @Override
    public Map<String, String> getParameters() {
        return Collections.emptyMap();
    }
}
