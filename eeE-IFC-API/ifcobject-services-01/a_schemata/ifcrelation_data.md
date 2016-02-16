## eeE IFCAPI Services Schemata : IFC Relation Data ##

* [Level Up](../README.md)
* [Overview](./README.md)

Version/Date: 2016.02.03 AET/EPM  API v0.1+ (in progress)


## IFC Relation Data

Represents an IFC relation. The object is labelled with an ifcRelationType indicating its class, for example XXXX or YYYY. Only a small subset of relation types is supported.


 
 Attribute   | Type | Comment |
-------------|------|---------|
globalid |String|Id for the object within model, ref IFC Schema
name |String|Name as givenm in IFC object
description  |String|Human readable description of the object 
url |String| How to identify the object as a cloud resource 

The attributes are mandatory or optional depending on the service used.

###JSON rep:

```
{
"$schema": "http://json-schema.org/draft-04/schema#" 
	"title": "eee_ifcapi_ifcrelation_data",
	"description": "Schema for ifc relation data, eeE REST API.",
	"type": "object",
			"properties": {
				"globalid":    {"type": ["string","null"]},
				"name":        {"type": ["string","null"]},
				"description": {"type": ["string","null"]}
				},
			}
	}
}
```

###JSON example:

```
JSON example: 
{   “IfcType”:”IfcConnectsInSpatialStructure”
    “GlobalId”:”abcd”,
    “Name”:”ConnectBuildingToStoreys”,
    “<attrname>”:”<value>,
    ...,
    “RelatingObject”:<guid>,
    “RelatedObjects”:[<guid>,<guid>,<guid>,<guid>]
}
```
