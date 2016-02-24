## eeE IFCAPI Services Schemata : Merge History Data ##

* [Level Up](../README.md)
* [Overview](./README.md)

Version/Date: 2016.02.24 AET/EPM  API v0.2+ (in progress)


## Merge History Data

Records the rsults of a MERGE operation 
 
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
{"merge_result":
  {"start_time":"yyymmdd-hhmmss.ffff",
   "end_time":"yyymmdd-hhmmss.ffff",
   "success":true,
   "description":"Merge finished successfully." 
   "source_models":
      [  {"model_id":"12343",
          "model_name:"Bierhaus_Arch_V5 (project name 'Project', schema IFC2X3)" }
      ]
    "target_model": 
        {"model_id":"44123",
         "model_name:"Sonnenhaus_Arch_V8' (project 'DDS Project', schema IFC2X3)" }
    "diagnostics:
      [ {"warning": 
          {"description":"Ambiguous alternative key",
           "elements":
             [ {"target_object":
                  {"ifctype":"IFCBUILDINGSTOREY",
                   "instanceid":"193273571733",
                   "name":"U1",
                   "globalid":"02_9V76BX0EPhFK1UkAsoo"}
                }]
        },
        {"warning":
          {"description":"Parents did not match with same alternative key. Assuming they are different objects."},
           "elements":
             [ {"source_object":
                 {"ifctype":"IFCBUILDINGSTOREY",
                  "instanceid":"184683595487" }
               },
               {"target_object":
                 {"ifctype":"IFCBUILDINGSTOREY",
                  "instanceid":"193273571733" }
               }]
        },
        {"message":
          {"description":"Initially 1127 ifcObjects copied to target model ready for merge (1127 new objects, 115172 new instances)"}
        }]
},
{"merge_result":...}
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

