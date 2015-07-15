## eeE Upload Model Schema ##

* [Level Up](../README.md)
* [Overview](./README.md)
* [Upload Model Service](../model_service_upload.md)
* [Upload Model Example](../model_service_upload_example.md)

The schema for [model-meta-data](./model_meta_data.md) is same for response and request, but the rules for which fields that are supplied are different.
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
				},		},
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