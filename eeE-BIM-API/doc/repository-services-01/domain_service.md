# eeE BIM-API Domain Services #

* [Level Up](../README.md)
* [Overview](./README.md)

Version/Date: 2015.07.10 AET/EPM  API v0.4+ (in progress)

**Important :**

* If server is set up for it domains are created on the fly when uploading. This has proven to be an effective concept for many usages. Example: two architects creates models for separate parts of building. Then domain can be set to **Arch-1** and **Arch-2** respectively at file upload. See [Model Services](./model_service.md) for more (later). 



## List Domains

**Resource URL (1) **: *GET /eee-repos/{version}/projects/project-id/domains*

**Resource URL (2) **: *GET /eee-repos/{version}/projects/project-id/multimodels/multimodel-id/domains*

element | explanation
--------|-----------|
*eee-repos*	|Shorthand for eeEmbedded Repository Services|
*version*	|States version of the API to use, allowing multiple versions of API for upgrading.|
*project_id*	|Project to list domains for. If skipped, multimodel_id will implicitly define project
*multimodel_id*	| Multimodel to list **used** domains for. If skipped, all domains in project will be listed. 
 |If both *project_id* and *multimodel_id* is skipped, all domains in server will be listed.

Returns list of {domain_url, {domain_meta_data}}. JSON Schema not shown (trivial). If server does not support direct concept of domain, *domain_url* is not returned.

** NOTE: **

* Domains belong on project level. To retrieve a list of recommended domains for new model uploads, the list should be retrieved from project level, and not from multimodel level.
* To retrive a list of domains acutally used in a multimodel level, add

**Example (project level):**

```
GET https://example.com/eee-repos/0.4/projects/ABCD/domains


Response:
[{
    "domain_url ": "http://example.com/eee-repos/0.4/projects/ABCD/multimodel/1234/domains/EFFE",
    "domain_meta_data ":
    {
	"project_id": "ABCD",
	"project_name": "munchen-parkhaus",
	"domain_id": "fafa",
	"domain_name": "Arch",
    	"description": "Architectural domain",
    }
},
{
    "domain_url ": "http://example.com/eee-repos/0.4/projects/ABCD/multimodel/1234/domains/EFFE",
    "domain_meta_data ":
    {
	"project_id": "ABCD",
	"project_name": "munchen-parkhaus",
	"domain_id": "fbfb",
	"domain_name": "HVAC",
    	"description": "HVAC domain",
    }
}]
```


## Retrieve Domain
**Resource URL**: *GET /eee-repos/{version}/projects/project-id/multimodels/multimodel_id/domains/EFFE*

element | explanation
--------|-----------|
*eee-repos*	|Shorthand for eeEmbedded Repository Services|
*version*	|States version of the API to use, allowing multiple versions of API for upgrading.
*project_id*	|Project to look for domain in. If skipped, entire server is searched for matching domain
*multimodel*	|Multimodel to look for domain **usage** in. See below.

Returns list containing single element {domain_url, {domain_meta_data}}. JSON Schema not shown (trivial). If domjain not found, return is empty list *[ ]*.

**NOTE :**

* Domain as usually attached to project level, so including the **multimodel** in the URL in practice checks whether this domain is *used* in the multimodel. 

**Example:**

```
GET https://example.com/eee-repos/0.4/projects/ABCD/multimodels/5678/domains/fbfb

Response:
[]
```

Here server indicates that domain with id ***fbfb*** (HVAC above) is not used in multi-model ***5678***.


## Create Domain
**Resource URL**: *POST /eee-repos/{version}/projects/project_id/domains*

element | explanation
--------|-----------|
*eee-repos*	|Shorthand for eeEmbedded Repository Services|
*version*	|States version of the API to use, allowing multiple versions of API for upgrading.
*project_id*	|Project to create domain in. 
JSON body	|[domain_meta_data](./schemata/domain_meta_data.md) for the domain to create. 

Returns list containing single element {domain_url, {domain_meta_data}}. JSON Schema not shown (trivial). 

**Example:**

```
POST https://example.com/eee-repos/0.4/projects/DABB/domains
Request:
{
	"domain_name": "BCS",
	"description": "Control system domain for eeEmbedded Oslo-office project",
}

Response:
[{
    "project_url ": "http://example.com/eee-repos/0.4/projects/DABB",
    "project_meta_data ":
    {
	"project_id": "DABB",
	"project_name": "oslo-office",
	"domain_id": "fcfc",
	"domain_name": "BCS",
	"description": "Control system domain for eeEmbedded Oslo-office project",
    }
}]
```

## Update Domain
**Resource URL**: *PUT /eee-repos/{version}/projects/**project_id**/domains/**domain_id***

element | explanation
--------|-----------|
*eee-repos*	|Shorthand for eeEmbedded Repository Services|
*version*	|States version of the API to use, allowing multiple versions of API for upgrading.
*project_id*	|Project to search domain in. 
*domain_id*	|Id for the domain to update 
JSON body	|[domain_meta_data](./schemata/domain_meta_data.md) for the domain to update. 

Fields ***domain_name*** and ***description*** can be updated.


Returns list containing single element {domain_url, {domain_meta_data}}. JSON Schema not shown (trivial). 

**Example:**

```
POST https://example.com/eee-repos/0.4/projects/DABB/domains
Request:
{
	"domain_name": "BACS",
	"description": "Oslo-office project domain BACS (earlier BCS)",
}

Response:
[{
    "project_url ": "http://example.com/eee-repos/0.4/projects/DABB",
    "project_meta_data ":
    {
	"project_id": "DABB",
	"project_name": "oslo-office",
	"domain_id": "fcfc",
	"domain_name": "BACS",
	"description": "Oslo-office project domain BACS (earlier BCS)",
    }
}]
```

## Delete Domain
**Resource URL**: *DELETE /eee-repos/{version}/projects/**project_id**/multimodels/**multimodel_id***

element | explanation
--------|-----------|
*eee-repos*	|Shorthand for eeEmbedded Repository Services|
*version*	|States version of the API to use, allowing multiple versions of API for upgrading.
*project_id*	|Project to search domain in. 
*domain_id*	|Id for the domain to delete 

Response: list containing single element {domain_url, {domain_meta_data}}. JSON Schema not shown (trivial). Empty list if not found. JSON Schema not shown (trivial).

**NOTE:**

* If the domain is used in the project it will not be deleted, and an error return will be sent.

**Example:**

```
DELETE https://example.com/eee-repos/0.4/projects/DABB/domains/fcfc

Response:
[{
    "project_url ": "http://example.com/eee-repos/0.4/projects/DABB",
    "project_meta_data ":
    {
	"project_id": "DABB",
	"project_name": "oslo-office",
	"domain_id": "fcfc",
	"domain_name": "BACS",
	"description": "Oslo-office project domain BACS (earlier BCS)",
    }
}]
```

