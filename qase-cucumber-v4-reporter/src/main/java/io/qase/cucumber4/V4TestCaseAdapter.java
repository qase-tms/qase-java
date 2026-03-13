package io.qase.cucumber4;

import cucumber.api.TestCase;
import gherkin.pickles.PickleTag;
import io.qase.commons.cucumber.CucumberTestCaseAdapter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class V4TestCaseAdapter implements CucumberTestCaseAdapter {
    private final TestCase testCase;

    V4TestCaseAdapter(TestCase testCase) {
        this.testCase = testCase;
    }

    @Override
    public List<String> getTags() {
        // v4: getTags() returns List<PickleTag>, must map to List<String>
        return testCase.getTags().stream()
                .map(PickleTag::getName)
                .collect(Collectors.toList());
    }

    @Override
    public String getName() {
        return testCase.getName();
    }

    @Override
    public List<String> getUriPathParts() {
        // v4: getUri() returns String (not java.net.URI)
        return Arrays.asList(testCase.getUri().split("/"));
    }

    @Override
    public Map<String, String> getParameters() {
        return Collections.emptyMap();
    }
}
