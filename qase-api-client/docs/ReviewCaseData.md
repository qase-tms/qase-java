

# ReviewCaseData

The test case fields proposed by the review.

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
|**isMuted** | **Boolean** | Mute state of the proposed test case. |  [optional] |
|**suiteId** | **Long** |  |  [optional] |
|**milestoneId** | **Long** |  |  [optional] |
|**isManual** | **Boolean** | &#x60;true&#x60; if the case is manual, &#x60;false&#x60; if it is automated. |  [optional] |
|**isToBeAutomated** | **Boolean** | &#x60;true&#x60; if a manual case is planned to be automated. |  [optional] |
|**status** | **Integer** |  |  [optional] |
|**stepsType** | [**StepsTypeEnum**](#StepsTypeEnum) | Format of the steps field. Omit to keep the current one, &#x60;classic&#x60; for a new-case draft; changing it requires sending &#x60;steps&#x60; in the same request. |  [optional] |
|**attachments** | **List&lt;String&gt;** | A list of Attachment hashes. |  [optional] |
|**steps** | [**List&lt;ReviewStepData&gt;**](ReviewStepData.md) | For gherkin steps send the scenario in &#x60;value&#x60;. |  [optional] |
|**tags** | **List&lt;String&gt;** |  |  [optional] |
|**parameters** | [**List&lt;TestCaseParameterCreate&gt;**](TestCaseParameterCreate.md) |  |  [optional] |
|**customField** | **Map&lt;String, String&gt;** | Map of custom field ID to value. A &#x60;create&#x60; review must carry every required custom field. An &#x60;edit&#x60; review is validated against the current test case, so send only the fields the proposal changes. |  [optional] |



## Enum: StepsTypeEnum

| Name | Value |
|---- | -----|
| CLASSIC | &quot;classic&quot; |
| GHERKIN | &quot;gherkin&quot; |



