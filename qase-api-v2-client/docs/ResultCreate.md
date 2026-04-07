

# ResultCreate


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **String** | If passed, used as an idempotency key |  [optional] |
|**title** | **String** |  |  |
|**signature** | **String** |  |  [optional] |
|**testopsId** | **Long** | ID of the test case. Cannot be specified together with testopd_ids. |  [optional] |
|**testopsIds** | **List&lt;Long&gt;** | IDs of the test cases. Cannot be specified together with testopd_id. |  [optional] |
|**execution** | [**ResultExecution**](ResultExecution.md) |  |  |
|**fields** | **ResultCreateFields** |  |  [optional] |
|**attachments** | **List&lt;String&gt;** |  |  [optional] |
|**steps** | [**List&lt;ResultStep&gt;**](ResultStep.md) |  |  [optional] |
|**stepsType** | **ResultStepsType** |  |  [optional] |
|**params** | **Map&lt;String, String&gt;** |  |  [optional] |
|**paramGroups** | **List&lt;List&lt;String&gt;&gt;** | List parameter groups by name only. Add their values in the &#39;params&#39; field |  [optional] |
|**relations** | [**ResultRelations**](ResultRelations.md) |  |  [optional] |
|**message** | **String** |  |  [optional] |
|**defect** | **Boolean** | If true and the result is failed, the defect associated with the result will be created |  [optional] |



