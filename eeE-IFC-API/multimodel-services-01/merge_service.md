# eeE IFC-API Merge Services #

* [Level Up](../README.md)
* [Overview](./README.md)

Version/Date: 2016.02.24 AET/EPM  API v0.2+ (in progress)

## List Merge History

**Resource URL**: *GET /ifc-api/{version}/multimodels/{model_id}/mergehistory

element | explanation
--------|-----------|
*ifc-api*	|Shorthand for eeEmbedded Repository Services |
*version*	|States version of the API to use, allowing multiple versions of API for upgrading |
*model_id*	|Identifies which model to look into |

Returns list of [merge_history_data](./a_schemata/merge_history_data.md) for the found objects. 


**Example:** see [merge_history_data](./a_schemata/merge_history_data.md)




## Merge into new version of model

**Purpose**: Merge one or more secondary model(s) with a primary model yielding result as a new version of the primary model

**Resource URL**: *POST /ifc-api/{version}/multimodels/{model_id}/merge {JSON body in request}


URL element | explanation
--------|-----------|
*ifc-api*	|Shorthand for eeEmbedded Repository Services |
*version*	|States version of the API to use, allowing multiple versions of API for upgrading |
*model_id*	|Identifies primary model. Alternatively given as part of JSON body argument |

<br/>

For actual layout of JSON argument body see [merge_argument_data](./a_schemata/merge_argument_data.md)

<br/>

JSON body element| type  | explanation
-----------------|-------|----
*source_models*   |object|List of source models. **model_id** mandatory 
*target_model*    |object|Target model, if **model_id** is supplied then it will override primary model id given in URL.
*todo_argument*	  |string|TODO: necessary arguments to be identified


Returns a list containing single element [merge_history_data](./a_schemata/merge_history_data.md)

**Example:** see [merge_history_data](./a_schemata/merge_history_data.md)

EDM mapped query:

```
QUERY_FUNCTION MergeModelsForWarehouse(	FromModels : Aggregate Of Generic;
		       	     				ToModel	: Generic;
			     				Options : Integer;
							Arguments : Aggregate Of Generic) : Aggregate Of Generic;

```

## Delete Merge History

**Purpose**: Delete merge history record(s) for a model

**Resource URL**: *DELETE /ifc-api/{version}/multimodels/model_id/merge {JSON body in request}


URL element | explanation
--------|-----------|
*ifc-api*	|Shorthand for eeEmbedded Repository Services |
*version*	|States version of the API to use, allowing multiple versions of API for upgrading |
*model_id*	|Identifies primary model |


## Update Merge History

**Purpose**: Update merge history record(s) for a model  

**Resource URL**: *PUT /ifc-api/{version}/multimodels/model_id/merge {JSON body in request}


URL element | explanation
--------|-----------|
*ifc-api*	|Shorthand for eeEmbedded Repository Services |
*version*	|States version of the API to use, allowing multiple versions of API for upgrading |
*model_id*	|Identifies primary model |

ONly last merge history can be adapted. In this version only merge history **Description** attribute can be written, meaning following JSON request body is relevant:

```
{"merge_result":{"description":"The New Value For Attribute"}}
```
