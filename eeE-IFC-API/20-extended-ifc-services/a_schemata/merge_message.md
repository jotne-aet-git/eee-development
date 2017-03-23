## eeE IFCAPI Services Schemata : Merge Message ##

* [Level Up](../README.md)
* [Overview](./README.md)

Version/Date: 2017.03.23 AET/EPM  API v0.30+ (in progress)


## Merge Message

Records the rsults of a MERGE or APPEND operation 
 
 Attribute   | Type | Comment |
-------------|------|---------|
something |String|something for resulting model after merge 

The attributes are mandatory or optional depending on the service used.


###Example

**Example:**

*List results from an ESIM merge*

```
POST https://example.com/ifc-api/0.4/multimodels/12324/append_file 

Request (multipart>: <args> <file>

Response:
[
{"merge_message":
  {"blabla":"blabla"},
  {"blabla":"blabla"}
},
{"merge_message":
  {"blabla":"blabla"},
  {"blabla":"blabla"}
}
]
```


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

