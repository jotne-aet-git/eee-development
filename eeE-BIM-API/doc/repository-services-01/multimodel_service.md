# eeE BIM-API Multimodel Services #

* [Level Up](../README.md)
* [Overview](./README.md)

Version/Date: 2015.07.10 AET/EPM  API v0.4+ (in progress)

**Important :**

If **name locking** is used multi-models may be handled "implicit" by uploading a model indicating a model name. If the supplied model name does not match any existing multi-models a new will be "created" in the sense that it will appear in the list outputs. See [Model Services](./model_service.md) for more (later). 


## List Multi-models

**Resource URL**: *GET /eee-repos/{version}/projects/project-id/multimodels*

element | explanation
--------|-----------|
*eee-repos*	|Shorthand for eeEmbedded Repository Services|
*version*	|States version of the API to use, allowing multiple versions of API for upgrading.|
*project_id*	|Project to list multimodels for. If skipped, all multimodels in server is listed

Returns list of {multimodel_url, {multimodel_meta_data}}. JSON Schema not shown (trivial). If server does not support direct concept of multimodel, multimodel-url is not returned.

**Example:**

```
GET https://example.com/eee-repos/0.4/projects/ABCD/multimodels

Request: none

Response:
[{
    "multimodel_url ": "http://example.com/eee-repos/0.4/projects/ABCD/multimodel/1234",
    "multimodel_meta_data ":
    {
	"project_id": "ABCD",
	"project_name": "munchen-parkhaus",
	"multimodel_id": "1234",
	"multimodel_name": "parkhaus",
    	"description": "The main building itself",
    }
},
{
    "multimodel_url ": "http://example.com/eee-repos/0.4/projects/ABCD/multimodel/1234",
    "multimodel_meta_data ":
    {
	"project_id": "ABCD",
	"project_name": "munchen-parkhaus",
	"multimodel_id": "5678",
	"multimodel_name": "wachthaus",
    	"description": "Small extra house at the entrance gate",
    }
}]
```


## Retrieve Multi-model
**Resource URL**: *GET /eee-repos/{version}/projects/project-id/multimodels/multimodel_id*

element | explanation
--------|-----------|
*eee-repos*	|Shorthand for eeEmbedded Repository Services|
*version*	|States version of the API to use, allowing multiple versions of API for upgrading.
*project_id*	|Project to look for list multimodel in. If skipped, entire server is searched for matching all multimodel

Returns list containing single element {multimodel_url, {multimodel_meta_data}}. JSON Schema not shown (trivial). If server does not support direct concept of multimodel, multimodel-url is not returned. If multimodel not found, return is empty list *[ ]*.


**Example:**

```
GET https://example.com/eee-repos/0.4/projects/ABCD/multimodels/5678

Response:
[{
    "multimodel_meta_data ":
    {
	"project_id": "ABCD",
	"project_name": "munchen-parkhaus",
	"multimodel_id": "5678",
	"multimodel_name": "wachthaus",
	"description": "Small extra house at the entrance gate",
    }
}]
```

Here server return no URL to the multimodel definition, indicating it is not supported.

## Create Multimodel
**Resource URL**: *POST /eee-repos/{version}/projects/project_id/multimodels*

element | explanation
--------|-----------|
*eee-repos*	|Shorthand for eeEmbedded Repository Services|
*version*	|States version of the API to use, allowing multiple versions of API for upgrading.
*project_id*	|Project to create multimodel in. 
JSON body	|[multimodel_meta_data](./schemata/multimodel_meta_data.md) for the multimodel to create 


Returns list containing single element {multimodel_url, {multimodel_meta_data}}. JSON Schema not shown (trivial). 

**Example:**

```
POST https://example.com/eee-repos/0.4/projects/DABB/multimodels
Request:
{
	"multimodel_name": "Office Building 1",
	"description": "eeEmbedded BIM-API multimodel example - Oslo",
}

Response:
[{
    "project_url ": "http://example.com/eee-repos/0.4/projects/DABB",
    "project_meta_data ":
    {
	"project_id": "DABB",
	"project_name": "oslo-office",
	"multimodel_id": "XYZ",
	"multimodel_name": "Office Building 1",
	"description": "eeEmbedded BIM-API multimodel example - Oslo",
    }
}]
```

## Update Multimodel
**Resource URL**: *PUT /eee-repos/{version}/projects/**project_id**/multimodels/**multimodel_id***

element | explanation
--------|-----------|
*eee-repos*	|Shorthand for eeEmbedded Repository Services|
*version*	|States version of the API to use, allowing multiple versions of API for upgrading.
*project_id*	|Project to search multimodel in. 
JSON body	|[multimodel_meta_data](./schemata/multimodel_meta_data.md) for the multimodel to update.

Fields ***multimodel_name*** and ***description*** can be updated.

Returns list containing single element {multimodel_url, {multimodel_meta_data}}. JSON Schema not shown (trivial). 

**NOTE:**

Due to the **name locking** between models and multi-model, renaming the multi-model will also rename all contained models.

**Example:**

```
PUT https://example.com/eee-repos/0.4/projects/DABB/multimodels/XYZ
Request:
{
	"multimodel_name": "Block 1 Office Building",
	"description": "eeEmbedded BIM-API multimodel name change example",
}

Response:
[{
    "multimodel_url ": "http://example.com/eee-repos/0.4/projects/DABB/multimodels/XYZ",
    "project_meta_data ":
    {
	"project_id": "DABB",
	"project_name": "oslo-office",
	"multimodel_id": "XYZ",
	"multimodel_name": "Block 1 Office Building",
	"description": "eeEmbedded BIM-API multimodel name change example",
    }
}]
```

## Delete Multimodel
**Resource URL**: *DELETE /eee-repos/{version}/projects/**project_id**/multimodels/**multimodel_id***

element | explanation
--------|-----------|
*eee-repos*	|Shorthand for eeEmbedded Repository Services|
*version*	|States version of the API to use, allowing multiple versions of API for upgrading.
*project_id*	|Project to search multimodel in. 

Response: List containing single element {multimodel_url, {multimodel_meta_data}} for deleted multimodel. Empty list if not found. JSON Schema not shown (trivial).

**NOTE:**

* If the multimodel is not empty it will not be deleted, ,and an error return will be sent.
* If the server uses implicit creation of multimodels, the "virtual" multi-model will be "deleted" when last contained model is deleted.

**Example:**

```
DELETE https://example.com/eee-repos/0.4/projects/DABB/multimodels/XYZ

Response:
[{
    "project_url ": "http://example.com/eee-repos/0.4/projects/DABB",
    "project_meta_data ":
    {
	"project_id": "DABB",
	"project_name": "oslo-office",
	"multimodel_id": "XYZ",
	"multimodel_name": "Block 1 Office Building",
	"description": "eeEmbedded BIM-API multimodel name change example",
    }
}]
```

