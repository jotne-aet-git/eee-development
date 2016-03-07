## eeE IFCAPI Services Schemata : Conversion History Data ##

* [Level Up](../README.md)
* [Overview](./README.md)

Version/Date: 2016.03.07 AET/EPM  API v0.30+ (in progress)


## Conversion History Data

Records the results of a CONVERT operation 
 
 Attribute   | Type | Comment |
-------------|------|---------|
something |String|something for resulting model after merge 

The attributes are mandatory or optional depending on the service used.


###Example

**Example:**

*List all merge result for model*

```
GET https://example.com/ifc-api/0.4/multimodels/12324/mergehistory

Request: none

Response:
[
{"conversion_result":
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


###JSON rep:

```
{
"$schema": "http://json-schema.org/draft-04/schema#" 
	"title": "eee_ifcapi_object_meta_data",
	"description": "Schema for domain meta data, eeE REST API.",
	"type": "object",
			"properties": {
				"model_id":    {"type": ["string","null"]},
				},
			}
	}
}
```

