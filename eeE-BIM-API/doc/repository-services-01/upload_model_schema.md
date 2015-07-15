## eeE Upload Model Schema ##

* [Level Up](../README.md)
* [Overview](./README.md)
* [Upload Model Service](./upload_model_service.md)
* [Upload Model Example](./upload_model_example.md)

The schema for “model-meta-data” is same for response and request, but the rules for which fields that are supplied are different.
The schemata in this page is derived from the "ORIGINAL" meta data schema

###JSON Schema
```
{
"$schema": "http://json-schema.org/draft-03/schema#" 
	"title": "eee_model_POST",
	"description": "Schema for upload template POST, eeE REST API.",
	"type": "object",
	"properties": {
		"model_meta_data ": {
			"title": " model_meta_data ",
			"type": "object"
			"properties": {
				"guid ": {
					"type": ["string","null"]
				},
				"project_id": {
					"type": ["string","null"]
				},
				"model_name": {
					"required": true,
					"type": "string"
				},
				"model_type ": {
					"required": true,
					"type": "string"
				},
				"schema_url": {
					"type": ["string","null"]
				},
				"domain_name ": {
					"type": ["string","null"]
				},
			}
		},
		"model_is_external": {
			"required": true,
			"type": "boolean"
		},
		"model_content ": {
		  "type": ["string","null"]
		}
	}
}
```