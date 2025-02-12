

# RequirementQuery


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **Long** |  |  [optional] |
|**requirementId** | **Long** |  |  |
|**parentId** | **Long** |  |  [optional] |
|**memberId** | **Long** |  |  [optional] |
|**title** | **String** |  |  [optional] |
|**description** | **String** |  |  [optional] |
|**status** | [**StatusEnum**](#StatusEnum) |  |  [optional] |
|**type** | [**TypeEnum**](#TypeEnum) |  |  [optional] |
|**createdAt** | **OffsetDateTime** |  |  [optional] |
|**updatedAt** | **OffsetDateTime** |  |  [optional] |



## Enum: StatusEnum

| Name | Value |
|---- | -----|
| VALID | &quot;valid&quot; |
| DRAFT | &quot;draft&quot; |
| REVIEW | &quot;review&quot; |
| REWORK | &quot;rework&quot; |
| FINISH | &quot;finish&quot; |
| IMPLEMENTED | &quot;implemented&quot; |
| NOT_TESTABLE | &quot;not-testable&quot; |
| OBSOLETE | &quot;obsolete&quot; |



## Enum: TypeEnum

| Name | Value |
|---- | -----|
| EPIC | &quot;epic&quot; |
| USER_STORY | &quot;user-story&quot; |
| FEATURE | &quot;feature&quot; |



