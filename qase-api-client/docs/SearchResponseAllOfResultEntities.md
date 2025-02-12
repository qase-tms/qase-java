

# SearchResponseAllOfResultEntities


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **Long** |  |  [optional] |
|**runId** | **Long** |  |  |
|**title** | **String** |  |  [optional] |
|**description** | **String** |  |  [optional] |
|**status** | **String** |  |  [optional] |
|**statusText** | **String** |  |  [optional] |
|**startTime** | **OffsetDateTime** |  |  [optional] |
|**endTime** | **OffsetDateTime** |  |  [optional] |
|**_public** | **Boolean** |  |  [optional] |
|**stats** | [**RunStats**](RunStats.md) |  |  [optional] |
|**timeSpent** | **Long** | Time in ms. |  [optional] |
|**environment** | [**RunEnvironment**](RunEnvironment.md) |  |  [optional] |
|**milestone** | [**RunMilestone**](RunMilestone.md) |  |  [optional] |
|**customFields** | [**List&lt;CustomFieldValue&gt;**](CustomFieldValue.md) |  |  [optional] |
|**tags** | [**List&lt;TagValue&gt;**](TagValue.md) |  |  [optional] |
|**cases** | **List&lt;Long&gt;** |  |  [optional] |
|**planId** | **Long** |  |  |
|**hash** | **String** |  |  [optional] |
|**resultHash** | **String** |  |  |
|**comment** | **String** |  |  [optional] |
|**stacktrace** | **String** |  |  [optional] |
|**caseId** | **Long** |  |  [optional] |
|**steps** | [**List&lt;TestStep&gt;**](TestStep.md) |  |  [optional] |
|**isApiResult** | **Boolean** |  |  [optional] |
|**timeSpentMs** | **Long** |  |  [optional] |
|**attachments** | [**List&lt;Attachment&gt;**](Attachment.md) |  |  [optional] |
|**requirementId** | **Long** |  |  |
|**parentId** | **Long** |  |  [optional] |
|**memberId** | **Long** | Deprecated, use &#x60;author_id&#x60; instead. |  [optional] |
|**type** | **Integer** |  |  [optional] |
|**createdAt** | **OffsetDateTime** |  |  [optional] |
|**updatedAt** | **OffsetDateTime** |  |  [optional] |
|**testCaseId** | **Long** |  |  |
|**position** | **Integer** |  |  [optional] |
|**preconditions** | **String** |  |  [optional] |
|**postconditions** | **String** |  |  [optional] |
|**severity** | **String** |  |  [optional] |
|**priority** | **Integer** |  |  [optional] |
|**layer** | **Integer** |  |  [optional] |
|**isFlaky** | **Integer** |  |  [optional] |
|**behavior** | **Integer** |  |  [optional] |
|**automation** | **Integer** |  |  [optional] |
|**milestoneId** | **Long** |  |  [optional] |
|**suiteId** | **Long** |  |  [optional] |
|**stepsType** | **String** |  |  [optional] |
|**params** | [**TestCaseParams**](TestCaseParams.md) |  |  [optional] |
|**authorId** | **Long** |  |  [optional] |
|**defectId** | **Long** |  |  |
|**actualResult** | **String** |  |  [optional] |
|**resolved** | **OffsetDateTime** |  |  [optional] |
|**externalData** | **String** |  |  [optional] |
|**casesCount** | **Integer** |  |  [optional] |



