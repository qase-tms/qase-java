

# ReviewStepData

A step of the proposed test case. When `steps_type` is `gherkin` the step carries the scenario in `value` and nothing else: a non-empty `action`, `expected_result`, `data`, `attachments`, `shared` or nested `steps` is rejected.

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**action** | **String** | Step action text. Classic steps only. |  [optional] |
|**shared** | **String** | Hash of an existing shared step to insert at this position. |  [optional] |
|**expectedResult** | **String** |  |  [optional] |
|**data** | **String** |  |  [optional] |
|**value** | **String** | Gherkin scenario text. Used when steps_type is \&quot;gherkin\&quot;. Example: \&quot;Given a user exists\\nWhen they log in\\nThen they see the dashboard\&quot; |  [optional] |
|**attachments** | **List&lt;String&gt;** | A list of Attachment hashes. |  [optional] |
|**steps** | **List&lt;Object&gt;** | Nested steps may be passed here. Use same structure for them. |  [optional] |



