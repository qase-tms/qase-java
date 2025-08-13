

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
|**automation** | **Integer** |  |  [optional] |
|**status** | **Integer** |  |  [optional] |
|**milestoneId** | **Long** |  |  [optional] |
|**suiteId** | **Long** |  |  [optional] |
|**customFields** | [**List&lt;CustomFieldValue&gt;**](CustomFieldValue.md) |  |  [optional] |
|**attachments** | [**List&lt;Attachment&gt;**](Attachment.md) |  |  [optional] |
|**stepsType** | **String** |  |  [optional] |
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



