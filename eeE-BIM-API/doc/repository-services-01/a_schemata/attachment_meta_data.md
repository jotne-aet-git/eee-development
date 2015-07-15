## eeE Repository Services Schemata : Attachment Meta Data ##

* [Level Up](../README.md)
* [Overview](./README.md)

Here is the components so far:

## Attachment Meta Data

**An *attachment* can be attached to:**

* A model with specific version - meaning the attachment will not be brought forward if a new model version is created
* A model without specific version - meaning the attachment will still be attached to the newest model when a new version is created
* A "repository" which means the attachment is a document / model / file in its own right.
 
For each attachment the following attributes are used for  meta data, as members of a meta data element:
 
 Attribute   | Type | Comment |
-------------|------|---------|
project_name |String|Names the project the owning model belongs to
model_guid	 |String|Unique identifier of the model if attachment is attached to a model with version.
model_name	 |String|Name of the model of attachment is attached to a model across versions
attachment_guid	 |String|Unique identifier of the attachment (file). The guid is generated on the server, and differs between versions
attachment_name	 |String|Name of the attachment. Also used as "attachment id". Then name is same across versions
attachment_type	 |String|Type of the attachment (e.g. IFC2x3, XLS, docx, ….) 
attachment_version|String|Version of the model, usually server generated in sthe form of V21,V2,V3,...
schema_url	 |String|URL to the model schema
description  |String|Human readable description of the model, informative only, no functionbal impact
domain_name  |String|Tags the attachment with a named discipline/domain. 

The attributes are mandatory or optional depending on the service used.


###XSD rep

###JSON rep:

EXPRESS rep:
```
{
"$schema": "http://json-schema.org/draft-03/schema#" 
	"title": "eee_attachment_meta_data",
	"description": "Schema for attachment meta data, eeE REST API.",
	"type": "object",
			"properties": {
				"project_name": {
					"type": ["string","null"]
				},
				"model_guid ": {
					"type": ["string","null"]
				},
				"model_name": {
					"type": ["string","null"]
				},
				"attachment_guid ": {
					"type": ["string","null"]
				},
				"attachment_name": {
					"type": ["string","null"]
				},
				"attachment_type ": {
					"type": ["string","null"]
				},
				"attachment_version ": {
					"type": ["string","null"]
				},
				"schema_url": {
					"type": ["string","null"]
				},
				"description": {
					"type": ["string","null"]
				},
				"domain_name ": {
					"type": ["string","null"]
				},
			}
	}
}
```

###EXPRESS rep:

```
	ENTITY BIM3API_attachment_meta_data:
		repository_name	: OPTIONAL STRING;
		model_guid 		: OPTIONAL STRING;
		model_name		: OPTIONAL STRING;
		attachment_guid 		: OPTIONAL STRING;
		attachment_name		: OPTIONAL STRING;
		attachment_type		: OPTIONAL STRING;
		attachment_version	: OPTIONAL STRING;
		schema_url		: OPTIONAL STRING;
		description		: OPTIONAL STRING;
		domain_name		: OPTIONAL STRING;
	END_ENTITY;
```

