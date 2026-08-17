

# ReviewCreate


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**caseId** | **Long** | ID of the reviewed test case. When present an &#x60;edit&#x60; review is created, otherwise a &#x60;create&#x60; review with a new-case draft. |  [optional] |
|**reviewers** | **List&lt;UUID&gt;** | Author UUIDs of team members to assign as reviewers (see &#x60;GET /author&#x60;). |  [optional] |
|**proposedCase** | [**ReviewCaseData**](ReviewCaseData.md) | For &#x60;create&#x60; reviews &#x60;title&#x60; and all required project fields are required. For &#x60;edit&#x60; reviews send only the fields the proposal changes. |  |



