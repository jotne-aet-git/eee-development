# eeE IFC-API Merge Services #

* [Level Up](../README.md)
* [Overview](./README.md)

Version/Date: 2016.02.03 AET/EPM  API v0.1+ (in progress)

## List Merge history

**Resource URL**: *GET /ifc-api/{version}/multimodels/{model_id}/mergehistory

element | explanation
--------|-----------|
*ifc-api*	|Shorthand for eeEmbedded Repository Services |
*version*	|States version of the API to use, allowing multiple versions of API for upgrading |
*model_id*	|Identifies which model to look into |

Returns list of [merge_meta_data](./schemata/merge_meta_data.md) for the found objects. 


**Example:**

*List all building storeys in the model*

```
GET https://example.com/ifc-api/0.4/multimodels/12324/mergehistory

Request: none

Response:
[{
    "todo": "TODO",
},
{
    "todo": "TODO",
}]
```



## Merge into new version of model

**Purpose**: Merge a secondary model with a primary model  yielding result as a new versiopn of the primary model

**Resource URL**: *GET /ifc-api/{version}/multimodels/{model_id}/merge {JSON body in request}


URL element | explanation
--------|-----------|
*ifc-api*	|Shorthand for eeEmbedded Repository Services |
*version*	|States version of the API to use, allowing multiple versions of API for upgrading |
*model_id*	|Identifies which model to look into |

<br/>

JSON body element | explanation
------------------|-----------
*second_model_id* |Id of second model to use 
*<TODO>*	  |check merge service in EDM and finmd arguments


Returns a list containing single element [merge_meta_data](./schemata/merge_meta_data.md){project_url, {project_meta_data}}. JSON Schema not shown (trivial).


**Example:**

```
POST https://example.com/ifc-api/0.4/multimodels/12324/merge 
{
    "secondary_model_id": "3939"
}

Response:
[{
    "todo": "TODO",
}]
```

