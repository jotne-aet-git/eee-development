## eeE IFCAPI Services Schemata : IFC Object Data ##

* [Level Up](../README.md)
* [Overview](./README.md)

Version/Date: 2016.02.16 AET/EPM  API v0.2+ (in progress)


## IFC Object Data


 
 Attribute   | Type | Comment |
-------------|------|---------|
globalid |String|Id for the object within model, ref IFC Schema
name |String|Name as givenm in IFC object
description  |String|Human readable description of the object 
url |String| How to identify the object as a cloud resource  
ifcproperty |Array | Optional list af properties for this object, see [ifcproperty_data](./ifcproperty_data.md) 


* The attributes are mandatory or optional depending on the service used.
* Additional attributes may occur depending on the actual IFC type, for example, "longname" may occur for IfcSpatialStructureElement and its subtypes like IfcSpace.

###JSON schema:

```
{
"$schema": "http://json-schema.org/draft-04/schema#" 
	"title": "eee_ifcapi_object_data",
	"description": "Schema for object data, eeE REST API.",
	"type": "object",
	"properties": {
		"model_id":    {"type": ["string","null"]},
		"globalid":    {"type": ["string","null"]},
		"name":        {"type": ["string","null"]},
		"description": {"type": ["string","null"]},
		"url":         {"type": ["string","null"]},
		"ifcproperty": {
		    "title": "ifcproperty"
		    "type": ["array","null"],
			"properties": {
				"ifcpropertyset.globalid":    {"type": ["string","null"]},
				"ifcpropertyset.name":        {"type": ["string","null"]},
				"ifcpropertyset.description": {"type": ["string","null"]},
				"ifcproperty.name":           {"type": ["string","null"]},
				"ifcproperty.description":    {"type": ["string","null"]},
				"ifcproperty.type":           {"type": ["string","null"]},
				"ifcproperty.unit":           {"type": ["string","null"]},
				"ifcproperty.value":          {"type": ["string","null"]},
				"url":          {"type": ["string","null"]},
			}
	    }
	}
}
```

The *ifcproperty* part is same as in [ifcproperty_data](./ifcproperty_data.md) schema.