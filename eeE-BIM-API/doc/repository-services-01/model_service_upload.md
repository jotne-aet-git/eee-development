## Upload Model Service - eeE BIMAPI

* [Model Services Overview](./model_service.md)

Version/Date: 2015.07.11 AET/EPM  API v0.4+ (in progress)

There are four distinct functions for upload model:

* Upload new model - data in local file
* Upload new model - data in URL reference
* Upload new version of model - data in local file
* Upload new version of model - data in URL reference

The first one is a composite of two operations:

* Create model - requires meta-data
* Upload model data - requires model data "file"

This is a bit challenging to do with a single HTTP request using JSON with standard software tools, but "workaroubnds" are perfectly possible. The option will be described last of the four. 

Alternatively, a client may use [Create Model](./model_service_create) service to create the model, and then use upload new model version service to supply the data.

When model data is a link (URL reference), both operations are simly implemented, in this case it is no need to use a two-step creat-upload sequence.

---
### Upload new model version - data in local file


**Resource URL: ** POST /eee-repos/{version}/models/**model_id**


element | explanation
--------|-----------|
*eee-repos*	|Shorthand for eeEmbedded Repository Services|
*version*	|States version of the API to use, allowing multiple versions of API for upgrading.
*model_id* | Model to upload new version for, must exist
multipart body	|file data 

Returns list containing single element {model_url, {[model meta data](./a_schemata/model_meta_data.md)}}. JSON Schema not shown (trivial).

It is possible, and sometimes necessary, to supply meta-data as URL parameters like **model_id**..?*description*="new version of model". 

-|element | explanation
-|--------|-----------|
-| *file_type* | ".ifc", ".xml", Necessary if model_type has more than one possible file type (NEW) 
-| *schema_url* | Informative only at time of writing
-| *desciption* | Informative only


** NOTE : **

* The access URL is the same as returned in **model_url** from LIST MODELS service.
* Since new model version gets new DI, URL to this model will change

Using a full URL like:

* POST /eee-repos/{version}/projects/**project_id**/multimodels/**multimodel_id**/domains/**domain_id**/models/*model_id*

might also work, but the exact behaviour is not defined.

**Example: **

```
POST https://example.com/eee-repos/0.4/models/8A4B23AA4BFA6610007EBB?description="Alternative 1 for the HVAC solution of Use Case 1"

** Model data in body of multi-part request **

Response:
[{
    "model_url ": "http://example.com/eee-repos/0.3/models/CFCA23AA59BEEE444FFFFF",
    "model_meta_data ":
    {
	"model_id": "CFCA23AA59BEEE444FFFFF",
	"project_id": "ABCD",
	"project_name": "munchen-parkhaus",
	"domain_id": "fdfd",
	"domain_name": "HVAC",
	"multimodel_id": "1112",
	"multimodel_name": "HVAC_alt_1",
	"model_type": "IFC4",
	"model_name": "HVAC_alt_1",
	"model_type": "IFC4",
	"model_version": "V2",
	"description": "Alternative 1 for the HVAC solution of Use Case 1",
    }
}]
```

---

### Upload new model version - data in URL reference


**Resource URL: ** POST /eee-repos/{version}/models

Request: JSON body according to [Upload model schema](./a_schemata/model_upload_schema.md)

-|element | explanation
-|--------|-----------|
-|*eee-repos*	|Shorthand for eeEmbedded Repository Services|
-|*version*	|States version of the API to use, allowing multiple versions of API for upgrading.
-| *model_is_external*	|must be supplied and set to "*true*". If you do not, weird things may happen!
-|*model_id* | Model to upload new version for, must exist
-| *file_type* | ".ifc", ".xml", Necessary if model_type has more than one possible file type (NEW) 
(opt) | *schema_url* | Informative only at time of writing
(opt) | *desciption* | Informative only

Returns list containing single element {model_url, {[model meta data](./a_schemata/model_meta_data.md)}}. JSON Schema not shown (trivial).

** NOTE : **

* DO NOT supply model_id in URL. If you do, server may think you are doing a local file upload and that JSON body is a file.
* It is possible to supply updates for meta-data as URL parameters like **model_id**..?*description*="new version of model" 
* The access URL is the same as returned in **model_url** from LIST MODELS service.
* Since new model version gets new DI, URL to this model will change

Using a full URL like:

* POST /eee-repos/{version}/projects/**project_id**/multimodels/**multimodel_id**/domains/**domain_id**/models/*model_id*

might also work, but the exact behaviour is not defined.

**Example: **

```
POST https://example.com/eee-repos/0.4/models

Request:
{
    "model_meta_data ":
  	{
	"model_id": "/CFCA23AA59BEEE444FFFFF"
	"description": "Alternative 1 for the HVAC solution update",
	}
    "model_is_external": true,
    "model_content ": "https://example.com/yet-another-HVAC-model.ifc",
}

Response:
[{
    "model_url ": "http://example.com/eee-repos/0.3/models/8A4B23AA4BFA6610007EBB",
    "model_meta_data ":
    {
	"model_id": "8A4B23AA4BFA6610007EBB",
	"project_id": "ABCD",
	"project_name": "munchen-parkhaus",
	"domain_id": "fdfd",
	"domain_name": "HVAC",
	"multimodel_id": "1112",
	"multimodel_name": "HVAC_alt_1",
	"model_type": "IFC4",
	"model_name": "HVAC_alt_1",
	"model_type": "IFC4",
	"model_version": "V2",
	"description": "Alternative 1 for the HVAC solution update",
    }
}]
```
---

### Upload new model  - data in URL Reference

**Resource URL: ** POST /eee-repos/{version}/models

Assuming **model name locking** is applied, here is the URL parameters:

Request: JSON body according to [Upload model schema](./a_schemata/model_upload_schema.md)

e/o |element | explanation
--|--------|-----------|
-| *eee-repos*	|Shorthand for eeEmbedded Repository Services|
-| *version*	|States version of the API to use, allowing multiple versions of API for upgrading.
-| *model_is_external*	|must be supplied and set to "*true*". If you do not, weird things may happen!
either | *project_id*	|Project to create model in, must exist
or | *project_name*	|Project to create model in, must exist
either | *domain_name*	|Domain to assign model to. If ***domain auto create*** is enabled, the domain is created on the fly if it does not exist 
or |*domain_id*	|Domain to assign model to, must exist. 
either | *multimodel_id*	| Multimodel to create model in, must exist
or | *multimodel_name* | Name of model / multimodel
or | *model_name* | Name of model / multimodel (recommended in stead of multimodel_name)
 - | *model_type* | "IFC2x3", "IFC4", "...." 
(opt)| *schema_url* | Informative only at time of writing
(opt)| *desciption* | Informative only
- |*model_content*		|URL to model data (model_is_external=true)


Returns list containing single element {model_url, {[model meta data](./a_schemata/model_meta_data.md)}}. JSON Schema not shown (trivial).

**NOTE :**

* If a model already exist inside a multimodel with same name and domain, a new version is created,if not a new model is created.
*  The model name and multimodel name is always same with name locking. Using model_name field, if ***multimodel auto create*** is enabled, the multimodel is created on the fly if it does not exist. If autocreate is disabled, an error will be sent.  
* It is also possible to "identify" an existing model with the "path" using project_id, multimodel_id and domain_id as indicated above. Same rule applies: if model name and domain name indicates an existing model, a new version is created. 
* Put anoher way -if you send exactly the same request multiple times, the first one will create a new model, the subsequent ones will create new versions.

**Example: **

```
POST https://example.com/eee-repos/0.4/models

Request:
{
    "model_meta_data ":
  	{
	"project_id": "munchen-parkhaus",
	"model_name": "HVAC_alt_2",
	"model_type": "IFC4",
	"description": "Alternative 2 for the HVAC solution of Use Case 1",
	"domain_name": "HVAC",
	}
    "model_is_external": true,
    "model_content ": "https://example.com/yet-another-HVAC-model.ifc",
}

Response:
[{
    "model_url ": "http://example.com/eee-repos/0.3/models/CFCA23AA59BEEE444FFFFF",
    "model_meta_data ":
    {
	"model_id": "CFCA23AA59BEEE444FFFFF",
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

---

### Upload new model - data in local file

Due to both client side and server side challenges using HTTP for file upload, the operation is not as straight forward as you might expect. The main challenge is that neither client nor server side software libraries are too happy with multi-part requests where the request body itself consists of multiple parts.

However, for thin stateless clients like web pages the possibility to create and upload in one go is very valuable.The workaround is to supply metadata as URL parameters:

* POST /eee-repos/{version}/models?*fieldname*=value&*fieldname*=value&,*fieldname*=value&...

It is possible to (partly) use URL itself, the two URLs brelow are equivalent:

* POST /eee-repos/{version}/projects/*project_id*/multimodels/*multimodel_id*/domains/*domain_id*/models
* POST /eee-repos/{version}/models?project_id="*project_id*"/multimodel_id="*multimodel_id*"/domain_id="*domain_id*"


**Resource URL**: POST /eee-repos/{version}/models?*fieldname*=value&*fieldname*=value&,*fieldname*=value&...

Assuming **model name locking** is applied, here is the URL parameters:

e/o |element | explanation
--|--------|-----------|
-| *eee-repos*	|Shorthand for eeEmbedded Repository Services|
-| *version*	|States version of the API to use, allowing multiple versions of API for upgrading.
either | *project_id*	|Project to create model in, must exist
or | *project_name*	|Project to create model in, must exist
either | *domain_name*	|Domain to assign model to. If ***domain auto create*** is enabled, the domain is created on the fly if it does not exist 
or |*domain_id*	|Domain to assign model to, must exist. 
either | *multimodel_id*	| Multimodel to create model in, must exist
or | *multimodel_name* | Name of model / multimodel
or | *model_name* | Name of model / multimodel (recommended in stead of multimodel_name)
 - | *model_type* | "IFC2x3", "IFC4", "...." 
(opt)| *file_type* | ".ifc", ".xml", Necessary if model_type has more than one possible file type (NEW) 
(opt)| *schema_url* | Informative only at time of writing
(opt)| *desciption* | Informative only
-| - |
- | *model_is_external*	|boolean, indicating if the model data is supplied as URL or attachment
either|*model_content*		|URL to model data (model_is_external=true)
or  |*model_content*		|Attachment in multipart request: Input model data as "file"(model_is_external=false)



**NOTE :**

* If a model already exist inside a multimodel with same name and domain, a new version is created,if not a new model is created.
*  The model name and multimodel name is always same with name locking. Using model_name field, if ***multimodel auto create*** is enabled, the multimodel is created on the fly if it does not exist. If autocreate is disabled, an error will be sent.  
* It is also possible to "identify" an existing model with the "path" using project_id, multimodel_id and domain_id as indicatee above. Same rule applies: if model name and domain name indicates an existing model, a new version is created. 
* Put anoher way -if you send exactly the same request multiple times, the first one will create a new model, the subsequent ones will create new versions.

**Example: **

```
POST https://example.com/eee-repos/0.4/models?project_id="munchen-parkhaus"
	&domain_name="HVAC"
	&model_name="HVAC_alt_1"
	&model_type="IFC4"
	&description="Alternative 1 for the HVAC solution of Use Case 1"

** Model data in body of multi-part request **

Response:
[{
    "model_url ": "http://example.com/eee-repos/0.3/models/CFCA23AA59BEEE444FFFFF",
    "model_meta_data ":
    {
	"model_id": "CFCA23AA59BEEE444FFFFF",
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
