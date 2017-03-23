## eeE IFCAPI Extended Services ##

[Level Up](../README.md)

Version/Date: 2017.03.23 AET/EPM  API v0.30+ (in progress)

Main classes are:

* Model - a self-contained data set that is a legal population of the IFC schema
* Object - generalized object corresponding to IfcObject in IFC Schema

Schemata defining data structures can be found here: [IFC Extended Services Schemata](a_schemata/README.md)

### Services for the classes 


Service| Comment |
-------|---------|
* [Conversion Services](./convert_service.md) | Convert models between IFC4 and IFC2x3
* [Merge Services](./merge_service.md) | Merge models into a new model / model version
* [Extract Services](./extract_service.md) | Extract partial models from a model
* [eeEmbedded Services](./eeembedded_service.md) | Special eeEmbedded services


### Navigating to an item in the server

By convention a RESTful interface often implement a list as:

```
 GET  http(s)://<server>/rest_api/<path_to_items>
```

The general eeE IFC-API URL pattern for multi-model targeted services is

```
GET <path-to-service>/multimodels/[model_id]/<subfunction>/[subfunction_id] <JSON body>
```

Where:

* Each element contained in <> is mandatory
* Each element contained in [] can be present or not
* If **_id** part is present in the last element return data for indicated item
* If **_id** part is not present in the last element return data for all matching items

The model identified in the URL is called the **primary model**. To identify more **secondary** models for use in the multi-model services, JSON body of request is utilized.

Examples:


URL | Result |
----|--------|
*GET ../models/ABCD/mergehistory/* | retrieve list of all merge histories in model *ABCD*
*GET ../models/ABCD/mergehistory/1234* | retrieve merge history for merge id #1234 in model *ABCD*



### Arguments/data in JSON Body versus in URL

In many cases you can find the same elements in the URL as in returned or sent JSON body. In case the same elements are found both places, arguments given as part of URL will take precedence.

**In general, it is recommended to use the URL for navigating in the data when browsing.** Implementation of "LIST" filtering from JSON body is actually optional (!).






