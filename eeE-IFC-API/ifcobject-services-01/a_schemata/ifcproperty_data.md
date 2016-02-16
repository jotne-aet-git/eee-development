## eeE IFCAPI Services Schemata : IFC Property Data ##

* [Level Up](../README.md)
* [Overview](./README.md)

Version/Date: 2016.02.03 AET/EPM  API v0.1+ (in progress)


## IFC Property Data


Same schema is used for IFC properties and IFC property sets, only field usage / contents dioffer. See examples below.Only a subset of simple property types is supported.


 
 Attribute   | Type | Comment |
-------------|------|---------|
globalid |String|Id for the object within model, ref IFC Schema
name |String|Name as given in IFC object
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


###ifcpropertyset example

```
JSON example: 
{   “IfcType”:”<IfcPropertySetType>”
    “GlobalId”:”abcd”,
    “Name”:”PSET_eee1”,
    “<attrname>”:”<value>,
    ...
}
```


###ifcproperty example

```
{   “IfcType”:”<IfcPropertyType>”
    “GlobalId”:”abcd”,
    “Name”:”PROPERTY_eee1”,
    “<attrname>”:”<value>,
    “valuetype” : <enumeration>
    “value” : <stringrep>
    “unit” : <stringrep>
    ...
}
```
