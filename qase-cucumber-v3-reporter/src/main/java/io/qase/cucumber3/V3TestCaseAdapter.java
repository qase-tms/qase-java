package io.qase.cucumber3;

import cucumber.api.TestCase;
import gherkin.pickles.PickleTag;
import io.qase.commons.cucumber.CucumberTestCaseAdapter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class V3TestCaseAdapter implements CucumberTestCaseAdapter {
    private final TestCase testCase;

    V3TestCaseAdapter(TestCase testCase) {
        this.testCase = testCase;
    }

    @Override
    public List<String> getTags() {
        // v3: getTags() returns List<PickleTag>, must map to List<String>
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
        // v3: getUri() returns String (not java.net.URI) — same as v4
        return Arrays.asList(testCase.getUri().split("/"));
    }

    @Override
    public Map<String, String> getParameters() {
        return Collections.emptyMap();
    }
}
