## eeE IFCAPI Services Schemata : Merge Argument Data ##

* [Level Up](../README.md)
* [Overview](./README.md)

Version/Date: 2016.02.24 AET/EPM  API v0.2+ (in progress)


## Merge Argument Data

 
 Attribute   | Type | Comment |
-------------|------|---------|
source_models|Array|List of source models for merge, model id mandatory member 
target_model|Object|Target model for merge, model id mandatory member 
arguments|Array|List of name value pairs, actual names described in operation description 

###Example:

```

{   "source_models":
      [  {"model_id":"12343"}
      ]
    "target_model": 
        {"model_id":"44123"}
    "arguments":
        [   {"merge_into_new_version":true},
            {"another_merge_argument":"example"},
            ...
        ]
}

```

###JSON rep:

```
{
"$schema": "http://json-schema.org/draft-04/schema#" 
	"title": "eee_ifcapi_object_meta_data",
	"description": "Schema for merge argumentsd, eeE REST IFC-API.",
	"type": "object",
			"properties": {
				"not completed":    {"type": ["string","null"]},
				},
			}
	}
}
```

