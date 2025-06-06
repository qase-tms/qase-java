

# TestCaseQuery


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **Long** |  |  [optional] |
|**testCaseId** | **Long** |  |  |
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
|**tags** | [**List&lt;TagValue&gt;**](TagValue.md) |  |  [optional] |
|**memberId** | **Long** | Deprecated, use &#x60;author_id&#x60; instead. |  [optional] |
|**authorId** | **Long** |  |  [optional] |
|**createdAt** | **OffsetDateTime** |  |  [optional] |
|**updatedAt** | **OffsetDateTime** |  |  [optional] |
|**updatedBy** | **Long** | Author ID of the last update. |  [optional] |



