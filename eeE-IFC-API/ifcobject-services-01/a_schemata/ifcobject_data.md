## eeE IFCAPI Services Schemata : IFC Object Data ##

* [Level Up](../README.md)
* [Overview](./README.md)

Version/Date: 2016.02.03 AET/EPM  API v0.1+ (in progress)


## IFC Object Data

For now only preliminary sketch. Real implementation must follow IFC schema, with additional API sspecific field(s)

 
 Attribute   | Type | Comment |
-------------|------|---------|
globalid |String|Id for the object within model, ref IFC Schema
name |String|Name as givenm in IFC object
description  |String|Human readable description of the object 
url |String|Human readable description of the object 

The attributes are mandatory or optional depending on the service used.


###XSD rep

###JSON rep:

```
{
"$schema": "http://json-schema.org/draft-04/schema#" 
	"title": "eee_ifcapi_object_meta_data",
	"description": "Schema for domain meta data, eeE REST API.",
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

