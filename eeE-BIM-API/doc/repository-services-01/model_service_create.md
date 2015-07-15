## Create Model Service - eeE BIMAPI

** Create an empty model in server repository **

* [Model Services Overview](./model_service.md)

Version/Date: 2015.07.10 AET/EPM  API v0.4+ (in progress)

To forms of resource URLs can be utilized:

* (1)POST /eee-repos/{version}/models

All meta-data in HTTP multipart request.Enables auto-creation of multimodel and domain.

* (2)POST /eee-repos/{version}/projects/*project_id*/multimodels/*multimodel_id*/domains/*domain_id*/models

Project, multimodel and domain is now given in URL. Additional meta-data in HTTP multipart request. Can only be used if domain and multi-model already exist.

Combinations of the above are possible. Both are described on this page.



### Create new model (1)

Assuming **model name locking** is applied:

**Resource URL**: POST /eee-repos/{version}/models

Request: JSON body according to [model meta data](./a_schemata/model_meta_data.md):

e/o |element | explanation
--|--------|-----------|
-| *eee-repos*	|Shorthand for eeEmbedded Repository Services|
-| *version*	|States version of the API to use, allowing multiple versions of API for upgrading.
either | *project_id*	|Project to create model in, must exist
or | *project_name*	|Project to create model in, must exist and be unique on  server
either | *domain_name*	|Domain to assign model to. If ***domain auto create*** is enabled, the domain is created on the fly if it does not exist 
or |*domain_id*	|Domain to assign model to, must exist. 
either | *model_name* | Name of model / multimodel (recommended in stead of multimodel_name)
or | *multimodel_id*	| Multimodel to create model in, must exist, yields model name
or | *multimodel_name* | Name of model / multimodel
 - | *model_type* | "IFC2x3", "IFC4", "...." 
 - | *schema_url* | Informative only at time of writing
 - | *desciption* | Informative only

Returns list containing single element {model_url, {[model meta data](./a_schemata/model_meta_data.md)}}. JSON Schema not shown (trivial).


**NOTE :**

* If a model already exist inside a multimodel with same name and domain, no model is created, and an error return is sent.
*  The model name and multimodel name is always same with name locking. Using model_name field, if ***multimodel auto create*** is enabled, the multimodel is created on the fly if it does not exist. If autocreate is disabled, an error will be sent.  

**Example:**

Domain "LCC" and multimodel "Cost model" assumed non existing => auto-created

```
POST https://example.com/eee-repos/0.4/models
Request:
{
	"project_name": "munchen-parkhaus",
	"domain_name": "LCC",
	"model_name": "Cost model",
	"model_type": "XLSX",
	"description": "Cost model Alternative 1 for the HVAC",
}

Response:
[{
    "model_url ": "http://example.com/eee-repos/0.3/models/CFCA23AA59BEEE444FEEEE",
    "model_meta_data ":
    {
	"model_guid": "CFCA23AA59BEEE444FEEEE",
	"project_id": "ABCD",
	"project_name": "munchen-parkhaus",
	"domain_id": "cdab",
	"domain_name": "LCC",
	"multimodel_id": "1113",
	"multimodel_name": "Cost model",
	"model_name": "Cost model",
	"model_type": "XLSX",
	"model_version": "V1",
	"description": "Cost model Alternative 1 for the HVAC",
    }
}]
```

### Create new model (2)

Assuming **model name locking** is applied:

**Resource URL**: POST /eee-repos/{version}/projects/*project_id*/multimodels/*multimodel_id*/domains/*domain_id*/models

Request: JSON body according to [model meta data](./a_schemata/model_meta_data.md):

e/o |element | explanation
--|--------|-----------|
-| *eee-repos*	|Shorthand for eeEmbedded Repository Services|
-| *version*	|States version of the API to use, allowing multiple versions of API for upgrading.
-| *project_id*	|Project to create model in, must exist
-|*domain_id*	|Domain to assign model to, must exist. 
- | *multimodel_id*	| Multimodel to create model in, must exist
 - | *model_type* | "IFC2x3", "IFC4", "...." 
 - | *schema_url* | Informative only at time of writing
 - | *desciption* | Informative only

Returns list containing single element {model_url, {[model meta data](./a_schemata/model_meta_data.md)}}. JSON Schema not shown (trivial).


**NOTE :**

* If a model already exist inside a multimodel with same name and domain, no model is created, and an error return is sent.
*  The model name and multimodel name is always same with name locking. Hence, the model name is not relevant, making this in facte "create domain model"



**Example:**

```
POST https://example.com/eee-repos/0.4/projects/ABCD/domains/fdfd/multimodels/1112/models
Request:
{
	"model_type": "IFC4",
	"description": "Alternative 1 for the HVAC solution of Use Case 1",
}

Response:
[{
    "model_url ": "http://example.com/eee-repos/0.3/models/CFCA23AA59BEEE444FFFFF",
    "model_meta_data ":
    {
	"model_guid": "CFCA23AA59BEEE444FFFFF",
	"project_id": "ABCD",
	"project_name": "munchen-parkhaus",
	"domain_id": "fdfd",
	"domain_name": "HVAC",
	"multimodel_id": "1112",
	"multimodel_name": "HVAC_alt_1",
	"model_type": "IFC4",
	"model_name": "HVAC_alt_1",
	"model_type": "IFC4",
	"model_version": "V1",
	"description": "Alternative 1 for the HVAC solution of Use Case 1",
    }
}]
```
