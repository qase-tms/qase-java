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
import java.util.Optional;

import static io.qase.api.utils.IntegrationUtils.*;

public class QaseListener extends RunListener {
    private static final Logger logger = LoggerFactory.getLogger(QaseListener.class);
    private boolean isEnabled;
    private String projectCode;
    private String runId;
    private QaseApi qaseApi;
    private long startTime;

    public QaseListener() {
        isEnabled = Boolean.parseBoolean(System.getProperty(ENABLE_KEY, "false"));
        if (!isEnabled) {
            return;
        }

        String apiToken = System.getProperty(API_TOKEN_KEY, System.getenv(API_TOKEN_KEY));
        if (apiToken == null) {
            logger.info(REQUIRED_PARAMETER_WARNING_MESSAGE, API_TOKEN_KEY);
            isEnabled = false;
            return;
        }

        String qaseUrl = System.getProperty(QASE_URL_KEY);
        if (qaseUrl != null) {
            qaseApi = new QaseApi(apiToken, qaseUrl);
        } else {
            qaseApi = new QaseApi(apiToken);
        }

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
    }

    @Override
    public void testFinished(Description description) {
        send(description, RunResultStatus.passed, null);
    }

    @Override
    public void testFailure(Failure failure) {
        send(failure.getDescription(), RunResultStatus.failed, failure.getException());
    }

    private void send(Description description, RunResultStatus runResultStatus, Throwable error) {
        if (!isEnabled) {
            return;
        }
        long end = System.currentTimeMillis();
        Duration duration = Duration.ofMillis(end - startTime);
        CaseId caseId = description.getAnnotation(CaseId.class);
        TmsLink tmsLink = description.getAnnotation(TmsLink.class);
        if (caseId != null || tmsLink != null) {

            Optional<Throwable> optionalThrowable = Optional.ofNullable(error);
            String comment = optionalThrowable
                    .flatMap(throwable -> Optional.of(throwable.toString())).orElse(null);
            Boolean isDefect = optionalThrowable
                    .flatMap(throwable -> Optional.of(throwable instanceof AssertionError))
                    .orElse(false);
            String stacktrace = optionalThrowable
                    .flatMap(throwable -> Optional.of(getStacktrace(throwable))).orElse(null);
            try {
                qaseApi.testRunResults().create(projectCode, Long.parseLong(runId),
                        caseId != null ? caseId.value() : Long.parseLong(tmsLink.value()),
                        runResultStatus, duration, null, comment, stacktrace, isDefect);
            } catch (QaseException e) {
                logger.error(e.getMessage());
            } catch (NumberFormatException e) {
                logger.error("String could not be parsed as Long", e);
            }
        }
    }
}
