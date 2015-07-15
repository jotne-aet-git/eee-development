## eeE Repository Services ##

[Level Up](../README.md)

Version/Date: 2015.07.10 AET/EPM  API v0.4+ (in progress)

### Classes 

The following main classes are defined:

* Project - contains multimodels and domains.
* Multimodel - collection of models and data for typically a virtual or physical building. Contains models, often grouped by domain
* Domain - generalized concept of discipline ur usage area like "Architect" "HVAC", "LCC Data" and similar. 
* Model - part of Multimodel that can be contained in a single file. A model is assignbed to a domain. Each model can exist in several versions, usually representing evolvement over time - that is, versions are *not* representing variants of the same model

Schemata defining data structures can be found here: [Repository Services Schemata](a_schemata/README.md)

### Services for the classes 

* [Project Services](./project_service.md)
* [Multimodel Services](./multimodel_service.md)
* [Domain Services](./domain_service.md)
* [Model Services](./model_service.md)


**IMPORTANT :**
Not all services are necessarily implemented on a server. As a baseline, assume the folling services are available per conformance level:

Level 1:

* Models: LIST UPLOAD DOWNLOAD DELETE

Level 2:

* Projects: LIST
* Multimodels: LIST
* Domains: LIST

Creation of  **Projects** may/will be done by server administration tools.

Creation of **Multimodels** and **Domains** may be handled implicitly by the MODEL UPLOAD service. See [Model Services](./model_service.md) for more.

*(To be formalized better later)*

### Navigating to an item in the server

By convention a RESTful interface often implement a list as:

```
 GET  http(s)://<server>/rest_api/<path_to_items>
```

The general eeE BIM-API URL pattern is
```
GET <path-to-service>/[projects/<p-id>/][multimodels/<mm-id>/][domains/d-id/][models/m-id]
```

Where:

* Each element contained in [] can be present or not
* If **id** part is present in the last element return data for indicated item
* If **id** part is not present in the last element return data for all matching items

Examples:

URL | Result |
--|--|
*GET ../projects* | retrieve data for all projects 
*GET ../projects/ABCD* | retrieve data for project with id *ABCD*
*GET ../projects/ABCD/multimodels* | retrieve list of all multimodels project *ABCD*
*GET ../projects/ABCD/multimodels/1234/domains* | retrieve list of all domains in multimodel *1234* in project *ABCD*
*GET ../projects/ABCD/domains* | retrieve list of all domains in project *ABCD*
*GET ../models*| retrieve list of all models on server
*GET ../projects/ABCD/models* | retrieve list of all models in project *ABCD*
*GET ../projects/ABCD/multimodels/1234/domains/XYZ/model/EFFE* | retrieve model *EFFE* for domain *XYZ* in  multimodel *1234* in project *ABCD*
*GET ../model/EFFE* | retrieve model *EFFE* 

Note that the last two URLS identify the same model, since any model has its own globally unique identifer, "*EFFE*" in this case. Since "parent" is (often) implicitly given by identifying the cild, it is in many cases not really necessary to include parents in the path. But it might be wise to  do anyway:

* To keep interface design “consistent”
* To compensate for possible "data duplication" errors, such problems are far from unknown


### Arguments/data in JSON Body versus in URL

In many cases you can find the same elements in the URL as in returned JSON objects. Example: listing multimodels for project abcd:

```
A1: GET https://example.com/eee-repos/0.4/projects/abcd/multimodels
A2: GET https://example.com/eee-repos/0.4/multimodels  {"project_id":"abcd"}
```

These two alternatives means the same. Now, what happens if you use both, especially with a conflict:

```
A3: GET https://example.com/eee-repos/0.4/projects/abcd/multimodels {"project_id":"1234"}
```
In this case, arguments given as part of URL will take precedence.

**In general, it is recommended to use the URL for navigating in the data when browsing.** Implementation of "LIST" filtering from JSON body is actually optional (!).


### Services related to other object types


Attachments:

* [Delete Attachment Service](delete_attachment_service.md)
* [Download Attachment Service](download_attachment_service.md)
* [List Attachments Service](list_attachment_service.md)
* [Upload Attachment Service](upload_attachment_service.md)





