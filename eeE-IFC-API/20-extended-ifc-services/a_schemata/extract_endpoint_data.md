## eeE IFCAPI Services Schemata : Extract Endpoint Data ##

* [Level Up](../README.md)
* [Overview](./README.md)

Version/Date: 2016.03.03 AET/EPM  API v0.30+ (in progress)

## Extract Endpoint Data

Defines the mode of an extract operation 
 
 Attribute   | Type | Comment |
-------------|------|---------|
discipline_id |String| Discipline ID if a discipline axis is used for extract
discipline_name |String| Discipline name if a discipline axis is used for extract - informative only
spatial_object_id |String| Spatial object ID if a spatial object axis is used for extract
spatial_object_name |String| Spatial object name if a spatial object axis is used for extract - informative only
system_id |String| System ID if a system axis is used for extract
system_name |String| System name if a system axis is used for extract - informative only

The attributes are mandatory or optional depending on the service used.

**NOTE:** The **system** concept above is not corresponding to *IFC System*, bit to concept like *"Energy System*". To get a list of available "endpoints" in a model use ***List Extract Endpoints*** service.

###Example

**Example:**

*List system extract endpoints in model*

```
GET https://example.com/ifc-api/0.4/multimodels/12324/extract/?filter="system_id"

Request: none

Response:
[{ "system_id":"522110",
   "system_name":"Energy System"
},
{  "system_id":"522111",
   "system_name":"BACS System"
}]
```


###JSON rep:

```
{
"$schema": "http://json-schema.org/draft-04/schema#" 
	"title": "eee_ifcapi_extract_endpoint_data",
	"description": "Schema for extract endpoint data, eeE REST API.",
	"type": "object",
			"properties": {
				"discipline_id":       {"type": ["string","null"]},
				"discipline_name":     {"type": ["string","null"]},
				"spatial_object_id":   {"type": ["string","null"]},
				"spatial_object_name": {"type": ["string","null"]},
				"system_id":           {"type": ["string","null"]},
				"system_name":         {"type": ["string","null"]}
				},
			}
	}
}
```

