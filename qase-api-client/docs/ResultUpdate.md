

# ResultUpdate


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**status** | [**StatusEnum**](#StatusEnum) |  |  [optional] |
|**timeMs** | **Long** |  |  [optional] |
|**defect** | **Boolean** |  |  [optional] |
|**attachments** | **List&lt;String&gt;** |  |  [optional] |
|**stacktrace** | **String** |  |  [optional] |
|**comment** | **String** |  |  [optional] |
|**steps** | [**List&lt;TestStepResultCreate&gt;**](TestStepResultCreate.md) |  |  [optional] |



## Enum: StatusEnum

| Name | Value |
|---- | -----|
| IN_PROGRESS | &quot;in_progress&quot; |
| PASSED | &quot;passed&quot; |
| FAILED | &quot;failed&quot; |
| BLOCKED | &quot;blocked&quot; |
| SKIPPED | &quot;skipped&quot; |



