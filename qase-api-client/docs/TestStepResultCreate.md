

# TestStepResultCreate


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**position** | **Integer** |  |  [optional] |
|**status** | [**StatusEnum**](#StatusEnum) |  |  |
|**comment** | **String** |  |  [optional] |
|**attachments** | **List&lt;String&gt;** |  |  [optional] |
|**action** | **String** |  |  [optional] |
|**expectedResult** | **String** |  |  [optional] |
|**data** | **String** |  |  [optional] |
|**steps** | **List&lt;Object&gt;** | Nested steps results may be passed here. Use same structure for them. |  [optional] |



## Enum: StatusEnum

| Name | Value |
|---- | -----|
| PASSED | &quot;passed&quot; |
| FAILED | &quot;failed&quot; |
| BLOCKED | &quot;blocked&quot; |
| IN_PROGRESS | &quot;in_progress&quot; |



