package io.qase.junit5;

import io.qase.commons.reporters.CoreReporterFactory;
import org.junit.platform.engine.FilterResult;
import org.junit.platform.engine.TestDescriptor;
import org.junit.platform.engine.TestSource;
import org.junit.platform.engine.support.descriptor.MethodSource;
import org.junit.platform.launcher.PostDiscoveryFilter;

import java.util.List;
import java.util.Optional;

import static io.qase.commons.utils.IntegrationUtils.*;

public class Junit5PostDiscoveryFilter implements PostDiscoveryFilter {
    private final List<Long> caseIds;

    public Junit5PostDiscoveryFilter() {
        this.caseIds = CoreReporterFactory.getInstance().getTestCaseIdsForExecution();
    }

    @Override
    public FilterResult apply(TestDescriptor testDescriptor) {
        if (this.caseIds.isEmpty()) {
            return FilterResult.includedIf(true);
        }

        if (!testDescriptor.getChildren().isEmpty()) {
            return FilterResult.included("filter only applied for tests");
        }

        final Optional<TestSource> testSource = testDescriptor.getSource();
        if (!testSource.isPresent()) {
            return FilterResult.includedIf(false);
        }

        final MethodSource methodSource = (MethodSource) testSource.get();
        Long id = getCaseId(methodSource.getJavaMethod());

        if (id == null) {
            return FilterResult.includedIf(false);
        }

        if (this.caseIds.contains(id)) {
            return FilterResult.includedIf(true);
        }

        return FilterResult.includedIf(false);
    }
}
