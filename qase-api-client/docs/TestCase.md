

# TestCase


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **Long** |  |  [optional] |
|**position** | **Integer** |  |  [optional] |
|**title** | **String** |  |  [optional] |
|**description** | **String** |  |  [optional] |
|**preconditions** | **String** |  |  [optional] |
|**postconditions** | **String** |  |  [optional] |
|**severity** | **Integer** |  |  [optional] |
|**priority** | **Integer** |  |  [optional] |
|**type** | **Integer** |  |  [optional] |
|**layer** | **Integer** |  |  [optional] |
|**isFlaky** | **Integer** |  |  [optional] |
|**behavior** | **Integer** |  |  [optional] |
|**automation** | **Integer** | Deprecated, use &#x60;isManual&#x60; and &#x60;isToBeAutomated&#x60; instead. Encodes the test case automation state as a single integer: &#x60;0&#x60; &#x3D; manual, &#x60;1&#x60; &#x3D; manual planned to be automated, &#x60;2&#x60; &#x3D; automated. |  [optional] |
|**isManual** | **Integer** | &#x60;1&#x60; if the case is manual, &#x60;0&#x60; if it is automated. Combined with &#x60;isToBeAutomated&#x60;, replaces the deprecated &#x60;automation&#x60; field. |  [optional] |
|**isToBeAutomated** | **Integer** | &#x60;1&#x60; if a manual case is planned to be automated, &#x60;0&#x60; otherwise. Only meaningful when &#x60;isManual &#x3D; 1&#x60;; ignored when &#x60;isManual &#x3D; 0&#x60;. |  [optional] |
|**status** | **Integer** |  |  [optional] |
|**milestoneId** | **Long** |  |  [optional] |
|**suiteId** | **Long** |  |  [optional] |
|**customFields** | [**List&lt;CustomFieldValue&gt;**](CustomFieldValue.md) |  |  [optional] |
|**attachments** | [**List&lt;Attachment&gt;**](Attachment.md) |  |  [optional] |
|**stepsType** | [**StepsTypeEnum**](#StepsTypeEnum) |  |  [optional] |
|**steps** | [**List&lt;TestStep&gt;**](TestStep.md) |  |  [optional] |
|**params** | [**TestCaseParams**](TestCaseParams.md) |  |  [optional] |
|**parameters** | [**List&lt;TestCaseParameter&gt;**](TestCaseParameter.md) |  |  [optional] |
|**tags** | [**List&lt;TagValue&gt;**](TagValue.md) |  |  [optional] |
|**memberId** | **Long** | Deprecated, use &#x60;author_id&#x60; instead. |  [optional] |
|**authorId** | **Long** |  |  [optional] |
|**createdAt** | **OffsetDateTime** |  |  [optional] |
|**updatedAt** | **OffsetDateTime** |  |  [optional] |
|**deleted** | **String** |  |  [optional] |
|**created** | **String** | Deprecated, use the &#x60;created_at&#x60; property instead. |  [optional] |
|**updated** | **String** | Deprecated, use the &#x60;updated_at&#x60; property instead. |  [optional] |
|**externalIssues** | [**List&lt;ExternalIssue&gt;**](ExternalIssue.md) |  |  [optional] |



## Enum: StepsTypeEnum

| Name | Value |
|---- | -----|
| CLASSIC | &quot;classic&quot; |
| GHERKIN | &quot;gherkin&quot; |



