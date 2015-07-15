## eeE Repository Services Schemata : Domain Meta Data ##

* [Level Up](../README.md)
* [Overview](./README.md)

Version: 0.4 2015.07.10 AET


## Domain Meta Data

For each domain the following attributes are used for domain meta data, as members of a domain meta data element:
 
 Attribute   | Type | Comment |
-------------|------|---------|
project_id   |String|Id for the project this domain belongs to
project_name |String|Names the project this domain belongs to
domain_id    |String|Id for the domain
domain_name |String|Name for the domain
description  |String|Human readable description of the domain, informative only, no functional impact

The attributes are mandatory or optional depending on the service used.


###XSD rep

###JSON rep:

```
{
"$schema": "http://json-schema.org/draft-04/schema#" 
	"title": "eee_domain_meta_data",
	"description": "Schema for domain meta data, eeE REST API.",
	"type": "object",
			"properties": {
				"project_id": {"type": ["string","null"]},
				"project_name": {"type": ["string","null"]},
				"domain_id ": {"type": ["string","null"]
				"domain_name ": {"type": ["string","null"]
				"description": {"type": ["string","null"]}
				},
			}
	}
}
```

