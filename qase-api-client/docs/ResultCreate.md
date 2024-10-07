

# ResultCreate


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**caseId** | **Long** |  |  [optional] |
|**_case** | [**ResultCreateCase**](ResultCreateCase.md) |  |  [optional] |
|**status** | **String** | Can have the following values &#x60;passed&#x60;, &#x60;failed&#x60;, &#x60;blocked&#x60;, &#x60;skipped&#x60;, &#x60;invalid&#x60; + custom statuses |  |
|**startTime** | **Integer** |  |  [optional] |
|**time** | **Long** |  |  [optional] |
|**timeMs** | **Long** |  |  [optional] |
|**defect** | **Boolean** |  |  [optional] |
|**attachments** | **List&lt;String&gt;** |  |  [optional] |
|**stacktrace** | **String** |  |  [optional] |
|**comment** | **String** |  |  [optional] |
|**param** | **Map&lt;String, String&gt;** | A map of parameters (name &#x3D;&gt; value) |  [optional] |
|**paramGroups** | **List&lt;List&lt;String&gt;&gt;** | List parameter groups by name only. Add their values in the &#39;param&#39; field |  [optional] |
|**steps** | [**List&lt;TestStepResultCreate&gt;**](TestStepResultCreate.md) |  |  [optional] |
|**authorId** | **Long** |  |  [optional] |



