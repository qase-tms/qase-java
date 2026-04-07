

# TestCasebulkCasesInner


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**description** | **String** |  |  [optional] |
|**preconditions** | **String** |  |  [optional] |
|**postconditions** | **String** |  |  [optional] |
|**title** | **String** |  |  |
|**severity** | **Integer** |  |  [optional] |
|**priority** | **Integer** |  |  [optional] |
|**behavior** | **Integer** |  |  [optional] |
|**type** | **Integer** |  |  [optional] |
|**layer** | **Integer** |  |  [optional] |
|**isFlaky** | **Integer** |  |  [optional] |
|**suiteId** | **Long** |  |  [optional] |
|**milestoneId** | **Long** |  |  [optional] |
|**automation** | **Integer** |  |  [optional] |
|**status** | **Integer** |  |  [optional] |
|**stepsType** | [**StepsTypeEnum**](#StepsTypeEnum) | Determines the format of the steps field. When \&quot;classic\&quot;, steps use the standard action/expected_result/data format. When \&quot;gherkin\&quot;, steps use the {value: \&quot;Given...\\nWhen...\\nThen...\&quot;} format. |  [optional] |
|**attachments** | **List&lt;String&gt;** | A list of Attachment hashes. |  [optional] |
|**steps** | [**List&lt;TestStepCreate&gt;**](TestStepCreate.md) |  |  [optional] |
|**tags** | **List&lt;String&gt;** |  |  [optional] |
|**params** | **Map&lt;String, List&lt;String&gt;&gt;** | Deprecated, use &#x60;parameters&#x60; instead. |  [optional] |
|**parameters** | [**List&lt;TestCaseParameterCreate&gt;**](TestCaseParameterCreate.md) |  |  [optional] |
|**customField** | **Map&lt;String, String&gt;** | A map of custom fields values (id &#x3D;&gt; value) |  [optional] |
|**createdAt** | **String** |  |  [optional] |
|**updatedAt** | **String** |  |  [optional] |
|**id** | **Integer** |  |  [optional] |



## Enum: StepsTypeEnum

| Name | Value |
|---- | -----|
| CLASSIC | &quot;classic&quot; |
| GHERKIN | &quot;gherkin&quot; |



