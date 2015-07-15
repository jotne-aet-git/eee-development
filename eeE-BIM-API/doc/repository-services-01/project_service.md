# eeE BIM-API Project Services #

* [Level Up](../README.md)
* [Overview](./README.md)

Version/Date: 2015.07.10 AET/EPM  API v0.4+ (in progress)

## List Projects

**Resource URL**: *GET /eee-repos/{version}/projects*

element | explanation
--------|-----------|
*eee-repos*	|Shorthand for eeEmbedded Repository Services|
*version*	|States version of the API to use, allowing multiple versions of API for upgrading.|

Returns list of {project_url, {project_meta_data}}. JSON Schema not shown (trivial)

**Example:**

```
GET https://example.com/eee-repos/0.4/projects

Request: none

Response:
[{
    "project_url ": "http://example.com/eee-repos/0.4/projects/ABCD",
    "project_meta_data ":
    {
	"project_id": "ABCD",
	"project_name": "munchen-parkhaus",
    	"description": "eeEmbedded BIM-API project example",
    }
},
{
    "project_url ": "http://example.com/eee-repos/0.4/projects/ABCD",
    "project_meta_data ":
    {
	"project_id": "DEFG",
	"project_name": "stuttgart-Z3",
    	"description": "eeEmbedded BIM-API project example",
    }
}]
```


## Retrieve Project
**Resource URL**: *GET /eee-repos/{version}/projects/**project_id***

element | explanation
--------|-----------|
*eee-repos*	|Shorthand for eeEmbedded Repository Services|
*version*	|States version of the API to use, allowing multiple versions of API for upgrading.
*project_id*	| identifies the project to retrieve

Returns a list containing single element {project_url, {project_meta_data}}. JSON Schema not shown (trivial).

If project not found, return is empty list *[ ]*.

**Example:**

```
GET https://example.com/eee-repos/0.4/projects/ABCD

Response:
[{
    "project_url ": "http://example.com/eee-repos/0.4/projects/ABCD",
    "project_meta_data ":
    {
	"project_id": "ABCD",
	"project_name": "munchen-parkhaus",
	"description": "eeEmbedded BIM-API project example",
    }
}]
```

## Create Project
**Resource URL**: *POST /eee-repos/{version}/projects*

element | explanation
--------|-----------|
*eee-repos*	|Shorthand for eeEmbedded Repository Services|
*version*	|States version of the API to use, allowing multiple versions of API for upgrading.
JSON body	|[project_meta_data](./schemata/project_meta_data.md) for the project to create. 

Response: List containing single element {project_url, {project_meta_data}}. JSON Schema not shown (trivial).

**Example:**

```
POST https://example.com/eee-repos/0.4/projects
Request:
{
	"project_name": "oslo-office",
	"description": "eeEmbedded BIM-API project example - Oslo",
}

Response:
[{
    "project_url ": "http://example.com/eee-repos/0.4/projects/DABB",
    "project_meta_data ":
    {
	"project_id": "DABB",
	"project_name": "oslo-office",
	"description": "eeEmbedded BIM-API project example - Oslo",
    }
}]
```

## Update Project
**Resource URL**: *PUT /eee-repos/{version}/projects/**project_id***

element | explanation
--------|-----------|
*eee-repos*	|Shorthand for eeEmbedded Repository Services|
*version*	|States version of the API to use, allowing multiple versions of API for upgrading.
*project_id*	|Identifies which project to update 
JSON body	|[project_meta_data](./schemata/project_meta_data.md) for the project to update

Fields ***project_name*** and ***description*** can be updated.

Response: List containing single element {project_url, {project_meta_data}} for updated project. JSON Schema not shown (trivial).

**Example:**

```
PUT https://example.com/eee-repos/0.4/projects/DABB
Request:
{
	"project_name": "stuttgart-office",
	"description": "eeEmbedded BIM-API project example moved to Stuttgart",
}

Response:
[{
    "project_url ": "http://example.com/eee-repos/0.4/projects/DABB",
    "project_meta_data ":
    {
	"project_id": "DABB",
	"project_name": "stuttgart-office",
	"description": "eeEmbedded BIM-API project example moved to Stuttgart",
    }
}]
```

## Delete Project
**Resource URL**: *DELETE /eee-repos/{version}/projects/**project_id***

element | explanation
--------|-----------|
*eee-repos*	|Shorthand for eeEmbedded Repository Services|
*version*	|States version of the API to use, allowing multiple versions of API for upgrading.
*project_id*	|Identifies which project to delete 

Response: List containing single element {project_url, {project_meta_data}} for deleted project. Empty list if not found. JSON Schema not shown (trivial).

**NOTE : **

* If project is not empty, it is not deleted, and an error return will be sent.

**Example:**

```
DELETE https://example.com/eee-repos/0.4/projects/DABB
Request: none

Response:
[{
    "project_url ": "http://example.com/eee-repos/0.4/projects/DABB",
    "project_meta_data ":
    {
	"project_id": "DABB",
	"project_name": "stuttgart-office",
	"description": "eeEmbedded BIM-API project example moved to Stuttgart",
    }
}]
```

