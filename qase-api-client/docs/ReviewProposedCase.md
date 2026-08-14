

# ReviewProposedCase

The test case state proposed by the review. Only the fields the proposal carries are present.

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**title** | **String** |  |  [optional] |
|**description** | **String** |  |  [optional] |
|**preconditions** | **String** |  |  [optional] |
|**postconditions** | **String** |  |  [optional] |
|**severity** | **Integer** |  |  [optional] |
|**priority** | **Integer** |  |  [optional] |
|**behavior** | **Integer** |  |  [optional] |
|**type** | **Integer** |  |  [optional] |
|**layer** | **Integer** |  |  [optional] |
|**isFlaky** | **Integer** |  |  [optional] |
|**isMuted** | **Boolean** |  |  [optional] |
|**suiteId** | **Long** |  |  [optional] |
|**milestoneId** | **Long** |  |  [optional] |
|**isManual** | **Boolean** | &#x60;true&#x60; if the case is manual, &#x60;false&#x60; if it is automated. |  [optional] |
|**isToBeAutomated** | **Boolean** | &#x60;true&#x60; if a manual case is planned to be automated. |  [optional] |
|**status** | **Integer** |  |  [optional] |
|**stepsType** | [**StepsTypeEnum**](#StepsTypeEnum) |  |  [optional] |
|**attachments** | **List&lt;String&gt;** | Attachment hashes. |  [optional] |
|**steps** | [**List&lt;ReviewProposedStep&gt;**](ReviewProposedStep.md) |  |  [optional] |
|**tags** | **List&lt;String&gt;** |  |  [optional] |
|**parameters** | [**List&lt;TestCaseParameter&gt;**](TestCaseParameter.md) |  |  [optional] |
|**customFields** | [**List&lt;CustomFieldValue&gt;**](CustomFieldValue.md) |  |  [optional] |



## Enum: StepsTypeEnum

| Name | Value |
|---- | -----|
| CLASSIC | &quot;classic&quot; |
| GHERKIN | &quot;gherkin&quot; |



