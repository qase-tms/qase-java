package io.qase.commons.client;

import io.qase.client.v1.ApiClient;
import io.qase.client.v1.ApiException;
import io.qase.client.v1.api.AttachmentsApi;
import io.qase.client.v1.api.ConfigurationsApi;
import io.qase.client.v1.api.PlansApi;
import io.qase.client.v1.api.RunsApi;
import io.qase.client.v1.models.*;
import io.qase.commons.config.ExternalLinkType;
import io.qase.commons.QaseException;
import io.qase.commons.config.ConfigurationValue;
import io.qase.commons.config.QaseConfig;
import io.qase.commons.logger.Logger;
import io.qase.commons.models.domain.Attachment;
import io.qase.commons.models.domain.TestResult;
import org.apache.commons.lang3.NotImplementedException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Qase API V1 client
 */
public class ApiClientV1 implements io.qase.commons.client.ApiClient {
    private static final Logger logger = Logger.getInstance();

    private final QaseConfig config;
    private final ApiClient client;
    private final String url;

    public ApiClientV1(QaseConfig config) {
        this.config = config;
        this.client = new ApiClient();

        if (config.testops.api.host.equals("qase.io")) {
            this.client.setBasePath("https://api.qase.io/v1");
            this.url = "https://app.qase.io/";
        } else {
            String url = "https://api-" + config.testops.api.host + "/v1";
            this.client.setBasePath(url);
            this.url = "https://app-" + config.testops.api.host + "/";
        }

        this.client.setApiKey(config.testops.api.token);
    }

    @Override
    public Long createTestRun() throws QaseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String utcTime = ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("UTC")).format(formatter);

        RunCreate model = new RunCreate()
                .title(this.config.testops.run.title)
                .isAutotest(true)
                .startTime(utcTime);

        if (this.config.testops.run.description != null) {
            model.setDescription(this.config.testops.run.description);
        }

        if (this.config.environment != null) {
            model.setEnvironmentSlug(this.config.environment);
        }

        if (this.config.testops.plan.id != 0) {
            model.setPlanId((long) this.config.testops.plan.id);
        }

        if (this.config.testops.run.tags != null && this.config.testops.run.tags.length > 0) {
            model.setTags(Arrays.asList(this.config.testops.run.tags));
        }

        if (this.config.testops.configurations != null && !this.config.testops.configurations.getValues().isEmpty()) {
            try {
                List<Long> configurationIds = getConfigurationIds();
                if (!configurationIds.isEmpty()) {
                    model.setConfigurations(configurationIds);
                }
            } catch (ApiException e) {
                logger.error("Failed to get configuration IDs: %s", e.getMessage());
            }
        }

        try {
            return Objects.requireNonNull(
                    new RunsApi(client)
                            .createRun(this.config.testops.project, model)
                            .getResult())
                    .getId();
        } catch (ApiException e) {
            throw new QaseException("Failed to create test run: " + e.getResponseBody(), e.getCause());
        }
    }

    @Override
    public void completeTestRun(Long runId) throws QaseException {
        try {
            new RunsApi(client)
                    .completeRun(this.config.testops.project, runId.intValue());
        } catch (ApiException e) {
            throw new QaseException("Failed to complete test run: " + e.getResponseBody(), e.getCause());
        }

        logger.info("Test run link: %srun/%s/dashboard/%d", this.url, this.config.testops.project, runId);
    }

    @Override
    public void updateExternalIssue(Long runId) throws QaseException {
        if (this.config.testops.run.externalLink == null) {
            return;
        }

        try {
            // Map our enum values to API enum values
            RunexternalIssues.TypeEnum apiType = this.config.testops.run.externalLink.getType() == ExternalLinkType.JIRA_CLOUD
                    ? RunexternalIssues.TypeEnum.CLOUD
                    : RunexternalIssues.TypeEnum.SERVER;

            RunexternalIssuesLinksInner link = new RunexternalIssuesLinksInner()
                    .runId(runId)
                    .externalIssue(this.config.testops.run.externalLink.getLink());

            RunexternalIssues externalIssues = new RunexternalIssues()
                    .type(apiType)
                    .links(Collections.singletonList(link));

            new RunsApi(client)
                    .runUpdateExternalIssue(this.config.testops.project, externalIssues);

            logger.info("External issue link updated for run %d: %s", runId, this.config.testops.run.externalLink.getLink());
        } catch (ApiException e) {
            throw new QaseException("Failed to update external issue: " + e.getResponseBody(), e.getCause());
        }
    }

    @Override
    public void uploadResults(Long runId, List<TestResult> results) throws QaseException {
        throw new NotImplementedException("Use ApiClientV2 for uploading results");
    }

    @Override
    public List<Long> getTestCaseIdsForExecution() throws QaseException {
        if (this.config.testops.plan.id == 0) {
            return Collections.emptyList();
        }

        try {
            PlanResponse response = new PlansApi(client)
                    .getPlan(this.config.testops.project, this.config.testops.plan.id);

            return Objects.requireNonNull(
                    Objects.requireNonNull(response.getResult())
                            .getCases())
                    .stream()
                    .map(PlanDetailedAllOfCases::getCaseId)
                    .collect(Collectors.toList());
        } catch (ApiException e) {
            throw new QaseException("Failed to get test case ids for execution: " + e.getResponseBody(), e.getCause());
        }
    }

    private List<Long> getConfigurationIds() throws ApiException {
        if (this.config.testops.configurations == null || this.config.testops.configurations.getValues().isEmpty()) {
            return Collections.emptyList();
        }
        
        ConfigurationsApi configurationsApi = new ConfigurationsApi(client);
        List<Long> configurationIds = new ArrayList<>();
        Map<String, Long> groupIds = new HashMap<>();
        
        // Get existing configuration groups
        ConfigurationListResponse existingConfigs = configurationsApi.getConfigurations(this.config.testops.project);
        ConfigurationListResponseAllOfResult result = existingConfigs.getResult();
        if (result != null && result.getEntities() != null) {
            List<ConfigurationGroup> entities = result.getEntities();
            if (entities != null) {
                for (ConfigurationGroup group : entities) {
                    List<ModelConfiguration> configs = group.getConfigurations();
                    if (configs != null) {
                        for (ModelConfiguration config : configs) {
                            groupIds.put(group.getTitle() + ":" + config.getTitle(), config.getId());
                        }
                    }
                }
            }
        }
        
        // Process each configuration value
        for (ConfigurationValue configValue : this.config.testops.configurations.getValues()) {
            String key = configValue.getName() + ":" + configValue.getValue();
            Long configId = groupIds.get(key);
            
            if (configId != null) {
                // Configuration already exists
                configurationIds.add(configId);
            } else if (this.config.testops.configurations.isCreateIfNotExists()) {
                // Create configuration if it doesn't exist
                try {
                    Long newConfigId = createConfiguration(configValue, configurationsApi);
                    if (newConfigId != null) {
                        configurationIds.add(newConfigId);
                    }
                } catch (Exception e) {
                    logger.error("Failed to create configuration %s=%s: %s", 
                               configValue.getName(), configValue.getValue(), e.getMessage());
                }
            }
        }
        
        return configurationIds;
    }
    
    private Long createConfiguration(ConfigurationValue configValue, ConfigurationsApi configurationsApi) throws ApiException {
        // First, try to find or create the configuration group
        Long groupId = findOrCreateConfigurationGroup(configValue.getName(), configurationsApi);
        
        if (groupId == null) {
            return null;
        }
        
        // Create the configuration value
        ConfigurationCreate configCreate = new ConfigurationCreate()
                .title(configValue.getValue())
                .groupId(groupId.intValue());
        
        return Objects.requireNonNull(configurationsApi.createConfiguration(this.config.testops.project, configCreate).getResult()).getId();
    }
    
    private Long findOrCreateConfigurationGroup(String groupName, ConfigurationsApi configurationsApi) throws ApiException {
        // Get existing groups to find the one with matching title
        ConfigurationListResponse existingConfigs = configurationsApi.getConfigurations(this.config.testops.project);
        ConfigurationListResponseAllOfResult result = existingConfigs.getResult();
        if (result != null && result.getEntities() != null) {
            List<ConfigurationGroup> entities = result.getEntities();
            if (entities != null) {
                for (ConfigurationGroup group : entities) {
                    if (groupName.equals(group.getTitle())) {
                        return group.getId();
                    }
                }
            }
        }
        
        // Create new group if it doesn't exist
        ConfigurationGroupCreate groupCreate = new ConfigurationGroupCreate()
                .title(groupName);
        
        return Objects.requireNonNull(configurationsApi.createConfigurationGroup(this.config.testops.project, groupCreate).getResult()).getId();
    }

    public String uploadAttachment(Attachment attachment) {
        AttachmentsApi api = new AttachmentsApi(client);
        File file;
        boolean removeFile = false;

        if (attachment.filePath != null) {
            file = new File(attachment.filePath);
        } else if (attachment.content != null) {
            String tempPath = Paths.get(System.getProperty("user.dir"), attachment.fileName).toString();
            file = new File(tempPath);

            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write(attachment.content);
                removeFile = true;
            } catch (IOException e) {
                logger.error("Failed to write attachment content to file: %s", e.getMessage());
                return "";
            }
        } else {
            String tempPath = Paths.get(System.getProperty("user.dir"), attachment.fileName).toString();
            file = new File(tempPath);

            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(attachment.contentBytes);
                removeFile = true;
            } catch (IOException e) {
                logger.error("Failed to write attachment content to file: %s", e.getMessage());
                return "";
            }
        }

        try {
            List<Attachmentupload> response = api
                    .uploadAttachment(this.config.testops.project, Collections.singletonList(file)).getResult();
            return processUploadResponse(response, file, removeFile);
        } catch (ApiException e) {
            logger.error("Failed to upload attachment: %s", e.getMessage());
            return "";
        }
    }

    private String processUploadResponse(List<Attachmentupload> response, File file, boolean removeFile) {
        if (file != null && file.exists() && removeFile) {
            file.delete();
        }

        if (response == null || response.isEmpty()) {
            return "";
        }

        return response.get(0).getHash();
    }
}
