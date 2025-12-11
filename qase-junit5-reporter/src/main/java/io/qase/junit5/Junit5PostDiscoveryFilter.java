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
        // Use getInstance() without parameters - instance should already be created by QaseListener
        // with proper reporter and framework info
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
        List<Long> ids = getCaseIds(methodSource.getJavaMethod());

        if (ids == null) {
            return FilterResult.includedIf(false);
        }

        for (Long id : ids) {
            if (this.caseIds.contains(id)) {
                return FilterResult.includedIf(true);
            }
        }

        return FilterResult.includedIf(false);
    }
}
