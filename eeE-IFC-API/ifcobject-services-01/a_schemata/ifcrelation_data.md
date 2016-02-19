## eeE IFCAPI Services Schemata : IFC Relation Data ##

* [Level Up](../README.md)
* [Overview](./README.md)

Version/Date: 2016.02.20 AET/EPM  API v0.2+ (in progress)


## IFC Relation Data

Represents an IFC relation; IfcRelationshiop and its subtypes . The object is labelled with an ifcRelationType indicating its class. Only a subset of relation types is supported.

The use of this type should be restricted to the cases where no other dedicated service is available, as it can impact performance and also interpretation and use of results might present challenges.


 
 Attribute   | Type | Comment |
-------------|------|---------|
model_id|String|identifies model within server, model + globalid is "unique key"
ifctype |String|entity name for the actual relation objecta
globalid |String|Id for the object within model, ref IFC Schema
name |String|Name as givenm in IFC object
description  |String|Human readable description of the object 
url |String| How to identify the object as a cloud resource 
relating|Array | Identifies "relating" end of relation, usually single instance
related|Array | Identifies "relating" end of relation, usually single or multiple instances


Relating and related contains array of following:

 Attribute   | Type | Comment |
-------------|------|---------|
globalid |String|Id for object within model, ref IFC Schema
name |String|Name as given in IFC object
url |String| Reference æto an object as a cloud resource 


* The use of *url* here may indicate possibility of cross-model and cross-server references, but currently thisa is neither implemented nor part of specification.
* The attributes are mandatory or optional depending on the service used.

###JSON rep:

```
{
"$schema": "http://json-schema.org/draft-04/schema#" 
	"title": "eee_ifcapi_ifcrelation_data",
	"description": "Schema for ifc relation data, eeE REST API.",
	"type": "object",
	"properties": {
		"ifcmodel_id":    {"type": ["string","null"]},
		"ifctype":    {"type": ["string","null"]},
		"globalid":    {"type": ["string","null"]},
		"name":        {"type": ["string","null"]},
		"description": {"type": ["string","null"]},
		"url": {"type": ["string","null"]},
		"relating": {
		    type:["array",null"]
		    properties: {
                        globalid: {"type": ["string","null"]},
                        name: {"type": ["string","null"]},
                        url: {"type": ["string","null"]}
                    }
		},
		"related": {
		    type:["array",null"]
		    properties: {
                        globalid: {"type": ["string","null"]},
                        name: {"type": ["string","null"]},
                        url: {"type": ["string","null"]}
                    }
		}
	},
}
```

###JSON example:

```
JSON example: 
{
    “ifcmodel_id”:”12345”
    “ifctype”:”IfcConnectsInSpatialStructure”
    “globalid”:”abcd”,
    “name”:”ConnectBuildingToStoreys”,
    "description":"Collect all building storeys"
    “url”:”http://example.com/ifcapi/0.4/models/12345/ifcconnectsinspatialstructure/abcd",
    “relating”:[{"globalid":"id10"}],
    “related”:[{"globalid":"id21"},{"globalid":"id22"}]
}
```
