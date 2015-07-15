## eeE Repository Services Schemata : Model Meta Data ##

* [Level Up](../README.md)
* [Overview](./README.md)

Version: 0.4 2015.07.10 AET

Here is the components so far:

## Model Meta Data

For each model the following attributes are used for model meta data, as members of a model meta data element:
 
 Attribute   | Type | Comment |
-------------|------|---------|
project_id   |String|Id for the project this model belongs to
project_name |String|Names the project this model belongs to
multimodel_id|String|Id for the multimodel this model belongs to
multimodel_name |String|Name for the multimodel this model belongs to
domain_id    |String|Id for the domain this model belongs to
domain_name |String|Name for the domain this model belongs to
model_guid   |String|Unique identifier of the model. The guid is generated on the server, and differs between versions
model_name   |String|Name of the model. Also used as "model id". Then name is same across versions
model_type   |String|Type of the model (e.g. IFC2x3, IFC4, XML, CSV, ifcXML, ….) 
model_version|String|Version of the model, usually server generated in the form of V21,V2,V3,...
schema_url   |String|URL to the model schema
description  |String|Human readable description of the model, informative only, no functional impact

The attributes are mandatory or optional depending on the service used.


###XSD rep

###JSON rep:

```
{
"$schema": "http://json-schema.org/draft-04/schema#" 
	"title": "eee_model_meta_data",
	"description": "Schema for model meta data, eeE REST API.",
	"type": "object",
			"properties": {
				"project_id": {"type": ["string","null"]},
				"project_name": {"type": ["string","null"]},
				"multimodel_id ": {"type": ["string","null"]
				"multimodel_name ": {"type": ["string","null"]
				"domain_id ": {"type": ["string","null"]
				"domain_name ": {"type": ["string","null"]
				"model_guid ": {"type": ["string","null"]},
				"model_name": {"type": ["string","null"]},
				"model_type ": {"type": ["string","null"]},
				"model_version ": {"type": ["string","null"]},
				"schema_url": {"type": ["string","null"]},
				"description": {"type": ["string","null"]}
				},
			}
	}
}
```

