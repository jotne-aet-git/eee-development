## Delete Model - eeE BIM-API Model Services

* [Model Services Overview](./model_service.md)

Version/Date: 2015.07.10 AET/EPM  API v0.4+ (in progress)


**Resource URLs** 

(1): *DELETE /eee-repos/{version}/projects/**project_id**/multimodels/**multimodel_id**/domains/**domain_id**/models/**model_id***

(2): *DELETE /eee-repos/{version}/models/**model_id***

element | explanation
--------|-----------|
*eee-repos*	|Shorthand for eeEmbedded Repository Services|
*version*	|States version of the API to use, allowing multiple versions of API for upgrading.
*project_id*	|Identifies which project to look for model in 
*multimodel_id*	|Identifies which multimodel to look for model in 
*domain_id*	|Identifies which assiged domain to check for model 
*model_id**	| Identifies which model to delete

** NOTE: **

As indicated, one or more of the URL elements can be omitted. If they are present, the server will check that the model really is placed where indicated. Whether tis is a useful security measure or not is left to the API user to decide.

Response: List containing single element {model_url,{[model_meta_data](./a_schemata/model_meta_data.md)}} for the deleted model

**Example :**



```
DELETE https://example.com/eee-repos/0.4/models/ABCD2233

Response:
[{
    "model_url" : "https://example.com/eee-repos/0.4/models/ABCD2233",
    "model_meta_data ":
    {
	"project_id": "DABB",
	"project_name": "oslo-office",
	"multimodel_id":"3456",
	"multimodel_name":"Main Building",
	"domain_id": "fcfc",
	"domain_name": "HVAC",
	"model_guid": "ABCD2233",
	"model_name": "HVAC_alt2_attributes",
	"model_type": "XLS",
	"model_version": "V33",
	"description": "Alternative 2 data HVAC solution of Use Case 1",
 }]
```
