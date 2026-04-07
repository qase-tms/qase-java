

# CustomFieldCreate


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**title** | **String** |  |  |
|**value** | [**List&lt;CustomFieldCreateValueInner&gt;**](CustomFieldCreateValueInner.md) | Required if type one of: 3 - selectbox; 5 - radio; 6 - multiselect;  |  [optional] |
|**entity** | **Integer** | Possible values: 0 - case; 1 - run; 2 - defect;  |  |
|**type** | **Integer** | Possible values: 0 - number; 1 - string; 2 - text; 3 - selectbox; 4 - checkbox; 5 - radio; 6 - multiselect; 7 - url; 8 - user; 9 - datetime;  |  |
|**placeholder** | **String** |  |  [optional] |
|**defaultValue** | **String** |  |  [optional] |
|**isFilterable** | **Boolean** |  |  [optional] |
|**isVisible** | **Boolean** |  |  [optional] |
|**isRequired** | **Boolean** |  |  [optional] |
|**isEnabledForAllProjects** | **Boolean** |  |  [optional] |
|**projectsCodes** | **List&lt;String&gt;** |  |  [optional] |



