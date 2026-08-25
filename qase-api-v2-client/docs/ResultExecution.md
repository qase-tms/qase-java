

# ResultExecution


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**startTime** | **Double** | Unix epoch time in seconds (whole part) and milliseconds (fractional part). |  [optional] |
|**endTime** | **Double** | Unix epoch time in seconds (whole part) and milliseconds (fractional part). |  [optional] |
|**status** | **String** | Can have the following values passed, failed, blocked, skipped, invalid + custom statuses |  |
|**duration** | **Long** | Duration of the test execution in milliseconds. |  [optional] |
|**stacktrace** | **String** |  |  [optional] |
|**errorContext** | **String** | Free-form failure context captured by the reporter. For Playwright this is the content of error-context.md (test info, error details, page snapshot), so it may include rendered page content. Stored verbatim so it can be copied as raw text. Values longer than 262144 characters are silently truncated by Qase and the request still succeeds. Write-only — not returned by the result read endpoints. |  [optional] |
|**thread** | **String** |  |  [optional] |



