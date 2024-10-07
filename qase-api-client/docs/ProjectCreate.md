

# ProjectCreate


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**title** | **String** | Project title. |  |
|**code** | **String** | Project code. Unique for team. Digits and special characters are not allowed. |  |
|**description** | **String** | Project description. |  [optional] |
|**access** | [**AccessEnum**](#AccessEnum) |  |  [optional] |
|**group** | **String** | Team group hash. Required if access param is set to group. |  [optional] |
|**settings** | **Map&lt;String, Object&gt;** | Additional project settings. |  [optional] |



## Enum: AccessEnum

| Name | Value |
|---- | -----|
| ALL | &quot;all&quot; |
| GROUP | &quot;group&quot; |
| NONE | &quot;none&quot; |



