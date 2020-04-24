package io.qase.junit4;


import io.qameta.allure.TmsLink;
import io.qase.api.QaseApi;
import io.qase.api.annotation.CaseId;
import io.qase.api.enums.RunResultStatus;
import io.qase.api.exceptions.QaseException;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class QaseListener extends RunListener {
    private static final Logger logger = LoggerFactory.getLogger(QaseListener.class);
    private static final String REQUIRED_PARAMETER_WARNING_MESSAGE = "Required parameter '{}' not specified";
    private final boolean isEnabled;
    private String projectCode;
    private String runId;
    private QaseApi qaseApi;
    private final ThreadLocal<Long> startTime = new ThreadLocal<>();

    private static final String PROJECT_CODE_KEY = "qase.project.code";

    private static final String RUN_ID_KEY = "qase.run.id";

    private static final String API_TOKEN_KEY = "qase.api.token";

    public QaseListener() {
        isEnabled = Boolean.parseBoolean(System.getProperty("qase.enable", "false"));
        if (!isEnabled) {
            return;
        }

        String apiToken = System.getProperty(API_TOKEN_KEY, System.getenv(API_TOKEN_KEY));
        if (apiToken == null) {
            logger.info(REQUIRED_PARAMETER_WARNING_MESSAGE, API_TOKEN_KEY);
            return;
        }

        String qaseUrl = System.getProperty("qase.url");
        if (qaseUrl != null) {
            qaseApi = new QaseApi(apiToken, qaseUrl);
        } else {
            qaseApi = new QaseApi(apiToken);
        }

        projectCode = System.getProperty(PROJECT_CODE_KEY, System.getenv(PROJECT_CODE_KEY));
        if (projectCode == null) {
            logger.info(REQUIRED_PARAMETER_WARNING_MESSAGE, PROJECT_CODE_KEY);
            return;
        }
        logger.info("Qase project code - {}", projectCode);

        runId = System.getProperty(RUN_ID_KEY, System.getenv(RUN_ID_KEY));
        if (runId == null) {
            logger.info(REQUIRED_PARAMETER_WARNING_MESSAGE, RUN_ID_KEY);
            return;
        }

        logger.info("Qase run id - {}", runId);
    }

    @Override
    public void testStarted(Description description) {
        startTime.remove();
        startTime.set(System.currentTimeMillis());
    }

    @Override
    public void testFinished(Description description) {
        send(description, RunResultStatus.passed);
    }

    @Override
    public void testFailure(Failure failure) {
        send(failure.getDescription(), RunResultStatus.failed);
    }

    private void send(Description description, RunResultStatus runResultStatus) {
        if (!isEnabled) {
            return;
        }
        Long start = this.startTime.get();
        long end = System.currentTimeMillis();
        CaseId caseId = description.getAnnotation(CaseId.class);
        TmsLink tmsLink = description.getAnnotation(TmsLink.class);
        if (caseId != null) {
            try {
                qaseApi.testRunResults().create(projectCode, Long.parseLong(runId), caseId.value(), runResultStatus,
                        Duration.ofMillis(end - start), null, null, null);
            } catch (QaseException e) {
                logger.error(e.getMessage());
            }
        } else if (tmsLink != null) {
            try {
                qaseApi.testRunResults().create(projectCode, Long.parseLong(runId), Long.parseLong(tmsLink.value()),
                        runResultStatus, Duration.ofMillis(end - start), null, null, null);
            } catch (QaseException e) {
                logger.error(e.getMessage());
            } catch (NumberFormatException e) {
                logger.error("String could not be parsed as Long", e);
            }
        }
    }
}
