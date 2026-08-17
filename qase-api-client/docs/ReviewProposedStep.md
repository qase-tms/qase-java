

# ReviewProposedStep


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**action** | **String** | Step action text. Used for classic steps. For gherkin steps, use the \&quot;value\&quot; property instead. |  [optional] |
|**expectedResult** | **String** |  |  [optional] |
|**data** | **String** |  |  [optional] |
|**value** | **String** | Gherkin scenario text. Used when steps_type is \&quot;gherkin\&quot;. |  [optional] |
|**shared** | **String** | Hash of the referenced shared step. |  [optional] |
|**attachments** | **List&lt;String&gt;** | A list of Attachment hashes. |  [optional] |
|**steps** | **List&lt;Object&gt;** | Nested steps use the same structure. |  [optional] |



