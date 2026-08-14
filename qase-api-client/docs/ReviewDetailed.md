

# ReviewDetailed


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **Long** | Review ID, unique within the project. |  [optional] |
|**title** | **String** |  |  [optional] |
|**type** | [**TypeEnum**](#TypeEnum) | &#x60;create&#x60; — the review proposes a new test case; &#x60;edit&#x60; — the review proposes changes to an existing test case. |  [optional] |
|**status** | [**StatusEnum**](#StatusEnum) |  |  [optional] |
|**caseId** | **Long** | ID of the reviewed test case. Null for new-case draft reviews. |  [optional] |
|**authorUuid** | **UUID** | Author UUID of the review creator (see &#x60;GET /author&#x60;). |  [optional] |
|**reviewers** | [**List&lt;ReviewReviewersInner&gt;**](ReviewReviewersInner.md) |  |  [optional] |
|**createdAt** | **OffsetDateTime** |  |  [optional] |
|**updatedAt** | **OffsetDateTime** |  |  [optional] |
|**proposedCase** | **Object** | The proposed test case state. Merging the review applies it to the test case. |  [optional] |



## Enum: TypeEnum

| Name | Value |
|---- | -----|
| CREATE | &quot;create&quot; |
| EDIT | &quot;edit&quot; |



## Enum: StatusEnum

| Name | Value |
|---- | -----|
| OPEN | &quot;open&quot; |
| MERGED | &quot;merged&quot; |
| DECLINED | &quot;declined&quot; |



