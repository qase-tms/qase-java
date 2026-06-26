

# TestStepResult


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**status** | **Integer** | 1 - passed, 2 - failed, 3 - blocked, 5 - skipped, 7 - in_progress |  [optional] |
|**position** | **Integer** |  |  [optional] |
|**comment** | **String** | Comment left for the step. |  [optional] |
|**startTime** | **Long** | Unix timestamp of the step start time. |  [optional] |
|**endTime** | **Long** | Unix timestamp of the step end time. |  [optional] |
|**durationMs** | **Long** | Step duration in milliseconds. |  [optional] |
|**attachments** | [**List&lt;Attachment&gt;**](Attachment.md) |  |  [optional] |
|**steps** | [**List&lt;TestStepResult&gt;**](TestStepResult.md) | Nested steps results will be here. The same structure is used for them. |  [optional] |



