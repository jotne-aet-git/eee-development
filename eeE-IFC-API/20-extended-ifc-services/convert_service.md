# eeE IFC-API Conversion Services #

* [Level Up](../README.md)
* [Overview](./README.md)

Version/Date: 2016.03.07 AET/EPM  API v0.30+ (in progress)

## List conversion history


**Resource URL**: *GET /ifc-api/{version}/multimodels/{model_id}/convert/history

element | explanation
--------|-----------|
*ifc-api*	|Shorthand for eeEmbedded Repository Services |
*version*	|States version of the API to use, allowing multiple versions of API for upgrading |
*model_id*	|Identifies which model to look into |

Returns list of [convert_history_data](./a_schemata/convert_history_data.md) for the found objects. 

**Example:** see [convert_history_data](./a_schemata/convert_history_data.md)

## Convert into new version of model

**Purpose**: Convert a source model yielding result as a new version of an existing model

**Resource URL**: *POST /ifc-api/{version}/multimodels/{source_id}/convert/{to_type}/{target_id}



URL element | explanation
--------|-----------|
*ifc-api*	|Shorthand for eeEmbedded Repository Services |
*version*	|States version of the API to use, allowing multiple versions of API for upgrading |
*source_id*	|Identifies source model. Alternatively given as part of JSON body argument |
*to_type*	|Identifies schem to convert into for target model. Supported: **ifc2x3** and **ifc4**
*target_id*	|Identifies target model. Alternatively given as part of JSON body argument |


Returns a list containing single element [convert_history_data](./a_schemata/convert_history_data.md)

**Example:** 

```
POST  https://example.com/ifc-api/0.4/multimodels/12324/convert/ifc4/mergehistory
Request: none
Response: {something TODO}
```

## Delete Conversion History

**Purpose**: Delete merge conversion record(s) for a model

**Resource URL**: *DELETE /ifc-api/{version}/multimodels/model_id/convert {JSON body in request}


URL element | explanation
--------|-----------|
*ifc-api*	|Shorthand for eeEmbedded Repository Services |
*version*	|States version of the API to use, allowing multiple versions of API for upgrading |
*model_id*	|Identifies model |


## Update Merge History

**Purpose**: Update conversion history record(s) for a model  

**Resource URL**: *PUT /ifc-api/{version}/multimodels/model_id/convert {JSON body in request}


URL element | explanation
--------|-----------|
*ifc-api*	|Shorthand for eeEmbedded Repository Services |
*version*	|States version of the API to use, allowing multiple versions of API for upgrading |
*model_id*	|Identifies primary model |

ONly last merge history can be adapted. In this version only **Description** attribute can be written, meaning following JSON request body is relevant:

```
{"conversion_result":{"description":"The New Value For Attribute"}}
```
