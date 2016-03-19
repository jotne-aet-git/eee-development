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
POST  https://example.com/ifc-api/0.4/multimodels/12324/convert/ifc4/44123
Request: none
Response:
[{"conversion_result":
  {"start_time":"yyymmdd-hhmmss.ffff",
   "end_time":"yyymmdd-hhmmss.ffff",
   "success":true,
   "description":"Conversion finished successfully." 
   "source_model":
      [  {"model_id":"12343",
          "model_schema":"IFC2X3",
          "model_name:"Bierhaus_Arch_V5 (project name 'Project', schema IFC2X3)" }
      ]
    "target_model": 
        {"model_id":"44123",
         "model_schema":"IFC4",
         "model_name:"Bierhaus_Arch_IFC4' (project 'Project', schema IFC4)" }
    "diagnostics:
      [ {"warning": 
          {"description":"Semantics not completed",
           "elements":
             [ {"target_object":
                  {"ifctype":"IFCBUILDINGSTOREY",
                   "instanceid":"193273571733",
                   "name":"U1",
                   "globalid":"02_9V76BX0EPhFK1UkAsoo"}
                }]
        },
        {"message":
          {"description":"1127 ifcObjects cobnverted to target model "}
        }]
},
{"conversion_result":...}
]
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
