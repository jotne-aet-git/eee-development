## eeE IFCAPI Services Schemata : Merge Meta Data ##

* [Level Up](../README.md)
* [Overview](./README.md)

Version/Date: 2016.02.03 AET/EPM  API v0.1+ (in progress)


## Merge Meta Data

 
 Attribute   | Type | Comment |
-------------|------|---------|
model_id |String|Model Id for resulting model after merge 

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
				"model_id":    {"type": ["string","null"]},
				},
			}
	}
}
```

