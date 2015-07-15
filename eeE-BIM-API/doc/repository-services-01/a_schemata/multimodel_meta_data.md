## eeE Repository Services Schemata : Domain Meta Data ##

* [Level Up](../README.md)
* [Overview](./README.md)

Version: 0.4 2015.07.10 AET


## Multimodel Meta Data

For each multimodel the following attributes are used for multimodel meta data, as members of a multimodel meta data element:
 
 Attribute   | Type | Comment |
-------------|------|---------|
project_id   |String|Id for the project this multimodel belongs to
project_name |String|Names the project this multimodel belongs to
multimodel_id|String|Id for the multimodel
multimodel_name |String|Name for the multimodel
description  |String|Human readable description of the multimodel, informative only, no functional impact

The attributes are mandatory or optional depending on the service used.


###XSD rep

###JSON rep:

```
{
"$schema": "http://json-schema.org/draft-04/schema#" 
	"title": "eee_multimodel_meta_data",
	"description": "Schema for multimodel meta data, eeE REST API.",
	"type": "object",
			"properties": {
				"project_id": {"type": ["string","null"]},
				"project_name": {"type": ["string","null"]},
				"multimodel_id ": {"type": ["string","null"]
				"multimodel_name ": {"type": ["string","null"]
				"description": {"type": ["string","null"]}
				},
			}
	}
}
```

