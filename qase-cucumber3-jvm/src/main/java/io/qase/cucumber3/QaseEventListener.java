package io.qase.cucumber3;

import cucumber.api.Result;
import cucumber.api.event.EventPublisher;
import cucumber.api.event.TestCaseFinished;
import cucumber.api.event.TestCaseStarted;
import cucumber.api.formatter.Formatter;
import gherkin.pickles.PickleTag;
import io.qase.api.QaseApi;
import io.qase.api.enums.RunResultStatus;
import io.qase.api.exceptions.QaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class QaseEventListener implements Formatter {
    private static final Logger logger = LoggerFactory.getLogger(QaseEventListener.class);
    private static final List<String> caseTags = Arrays.asList("@caseId", "@tmsLink");
    private static final String REQUIRED_PARAMETER_WARNING_MESSAGE = "Required parameter '{}' not specified";
    private boolean isEnabled;
    private String projectCode;
    private String runId;
    private QaseApi qaseApi;
    private long startTime;

    private static final String PROJECT_CODE_KEY = "qase.project.code";

    private static final String RUN_ID_KEY = "qase.run.id";

    private static final String API_TOKEN_KEY = "qase.api.token";

    public QaseEventListener() {
        isEnabled = Boolean.parseBoolean(System.getProperty("qase.enable", "false"));
        if (!isEnabled) {
            return;
        }

        String apiToken = System.getProperty(API_TOKEN_KEY, System.getenv(API_TOKEN_KEY));
        if (apiToken == null) {
            logger.info(REQUIRED_PARAMETER_WARNING_MESSAGE, API_TOKEN_KEY);
            isEnabled = false;
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
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestCaseStarted.class, this::testCaseStarted);
        publisher.registerHandlerFor(TestCaseFinished.class, this::testCaseFinished);
    }

    private void testCaseStarted(TestCaseStarted event) {
        startTime = System.currentTimeMillis();
    }

    private void testCaseFinished(TestCaseFinished event) {
        Duration duration = Duration.ofMillis(System.currentTimeMillis() - startTime);
        List<PickleTag> tags = event.testCase.getTags();
        Integer caseId = getCaseId(tags);
        if (caseId != null) {
            send(caseId, duration, event.result);
        }
    }

    private Integer getCaseId(List<PickleTag> tags) {
        for (PickleTag pickleTag : tags) {
            String tag = pickleTag.getName();
            String[] split = tag.split("=");
            if (caseTags.contains(split[0]) && split.length == 2 && split[1].matches("\\d+")) {
                return Integer.valueOf(split[1]);
            }
        }
        return null;
    }

    private void send(Integer caseId, Duration duration, Result result) {
        if (!isEnabled) {
            return;
        }
        try {
            RunResultStatus status = convertStatus(result.getStatus());
            if (status == null) {
                return;
            }
            String comment = Optional.ofNullable(result.getError())
                    .flatMap(throwable -> Optional.of(throwable.toString())).orElse(null);
            qaseApi.testRunResults().create(projectCode, Long.parseLong(runId), caseId,
                    status, duration, null, comment, null);
        } catch (QaseException e) {
            logger.error(e.getMessage());
        }
    }

    private RunResultStatus convertStatus(Result.Type status) {
        switch (status) {
            case FAILED:
                return RunResultStatus.failed;
            case PASSED:
                return RunResultStatus.passed;
            case PENDING:
            case SKIPPED:
            case AMBIGUOUS:
            case UNDEFINED:
            default:
                return null;
        }
    }
}
