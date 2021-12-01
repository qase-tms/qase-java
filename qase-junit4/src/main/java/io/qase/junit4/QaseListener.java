package io.qase.junit4;


import io.qameta.allure.TmsLink;
import io.qase.api.exceptions.QaseException;
import io.qase.client.ApiClient;
import io.qase.api.annotation.CaseId;
import io.qase.client.api.ResultsApi;
import io.qase.client.model.ResultCreate;
import io.qase.client.model.ResultCreate.StatusEnum;
import io.qase.client.model.ResultCreateBulk;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static io.qase.api.utils.IntegrationUtils.*;

public class QaseListener extends RunListener {
    private static final Logger logger = LoggerFactory.getLogger(QaseListener.class);
    private static final ThreadLocal<Set<Integer>> cases = ThreadLocal.withInitial(HashSet::new);
    private boolean isEnabled;
    private boolean useBulk;
    private String projectCode;
    private String runId;
    private final ResultCreateBulk resultCreateBulk = new ResultCreateBulk();
    private final ApiClient apiClient = new ApiClient();
    private final ResultsApi resultsApi = new ResultsApi(apiClient);
    private long startTime;

    public QaseListener() {
        isEnabled = Boolean.parseBoolean(System.getProperty(ENABLE_KEY, "false"));
        if (!isEnabled) {
            return;
        }
        useBulk = Boolean.parseBoolean(System.getProperty(BULK_KEY, "true"));

        String apiToken = System.getProperty(API_TOKEN_KEY, System.getenv(API_TOKEN_KEY));
        if (apiToken == null) {
            isEnabled = false;
            logger.info(REQUIRED_PARAMETER_WARNING_MESSAGE, API_TOKEN_KEY);
            return;
        }

        String qaseUrl = System.getProperty(QASE_URL_KEY, System.getenv(API_TOKEN_KEY));
        if (qaseUrl != null) {
            apiClient.setBasePath(qaseUrl);
        }
        apiClient.setApiKey(apiToken);

        projectCode = System.getProperty(PROJECT_CODE_KEY, System.getenv(PROJECT_CODE_KEY));
        if (projectCode == null) {
            isEnabled = false;
            logger.info(REQUIRED_PARAMETER_WARNING_MESSAGE, PROJECT_CODE_KEY);
            return;
        }
        logger.info("Qase project code - {}", projectCode);

        runId = System.getProperty(RUN_ID_KEY, System.getenv(RUN_ID_KEY));
        if (runId == null) {
            isEnabled = false;
            logger.info(REQUIRED_PARAMETER_WARNING_MESSAGE, RUN_ID_KEY);
            return;
        }
        logger.info("Qase run id - {}", runId);
    }

    @Override
    public void testStarted(Description description) {
        startTime = System.currentTimeMillis();
        cases.get().add(description.hashCode());
    }

    @Override
    public void testFinished(Description description) {
        if (cases.get().contains(description.hashCode())) {
            send(description, StatusEnum.PASSED, null);
            cases.get().remove(description.hashCode());
        }
    }

    @Override
    public void testFailure(Failure failure) {
        cases.get().remove(failure.getDescription().hashCode());
        send(failure.getDescription(), StatusEnum.FAILED, failure.getException());
    }

    @Override
    public void testAssumptionFailure(Failure failure) {
        cases.get().remove(failure.getDescription().hashCode());
        send(failure.getDescription(), StatusEnum.SKIPPED, null);
    }

    @Override
    public void testIgnored(Description description) throws Exception {
        cases.get().remove(description.hashCode());
        send(description, StatusEnum.SKIPPED, null);
    }

    private void send(Description description, StatusEnum status, Throwable error) {
        if (!isEnabled) {
            return;
        }
        long end = System.currentTimeMillis();
        Duration duration = Duration.ofMillis(end - startTime);

        CaseId caseIdAnnotation = description.getAnnotation(CaseId.class);
        TmsLink tmsLinkAnnotation = description.getAnnotation(TmsLink.class);
        Long caseId = caseIdAnnotation != null ? caseIdAnnotation.value() :
                tmsLinkAnnotation != null ? Long.parseLong(tmsLinkAnnotation.value()) : null;

        Optional<Throwable> optionalThrowable = Optional.ofNullable(error);
        String comment = optionalThrowable
                .flatMap(throwable -> Optional.of(throwable.toString())).orElse(null);
        Boolean isDefect = optionalThrowable
                .flatMap(throwable -> Optional.of(throwable instanceof AssertionError))
                .orElse(false);
        String stacktrace = optionalThrowable
                .flatMap(throwable -> Optional.of(getStacktrace(throwable))).orElse(null);
        try {
            resultsApi.createResult(projectCode,
                    runId,
                    new ResultCreate()
                            .caseId(caseId)
                            .status(status)
                            .timeMs(duration.toMillis())
                            .comment(comment)
                            .stacktrace(stacktrace)
                            .defect(isDefect));
        } catch (QaseException e) {
            logger.error(e.getMessage());
        }
    }
}
