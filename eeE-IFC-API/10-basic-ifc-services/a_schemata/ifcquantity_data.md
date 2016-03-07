## eeE IFCAPI Services Schemata : IFC Quantity Data ##

* [Level Up](../README.md)
* [Overview](./README.md)

Version/Date: 2016.03.07 AET/EPM  API v0.30+ (in progress)


## IFC Quantity Data


Same schema is used for IFC properties and IFC property sets, only field usage / contents differ. Only a subset of IfcSimpleProperty property types is supported.


 
 Attribute   | Type   | Comment |
-------------|--------|---------|
ifcmodel_id  | String | Id for the model containing the property 
ifcelementquantity_globalid |String| Id for the element quantity set within model 
ifcelementquantity_name |String| Name for the element quantity set within model 
ifcelementquantity_description  |String|Human readable description for the element quantity set 
ifcelementquantity_methodofmeasurement|String|Method of measurement for the element quantity set 
ifcphysicalquantity_name |String |Ifc quantity name as given in model
ifcphysicalquantity_type |String|Ifc quantity type - entity name for IFC property 
ifcphysicalquantity_unit |String|Ifc quantity unit as a string 
ifcphysicalquantity_value |String|Ifc property value as a srting, see table below 
url |String| How to identify the object as a cloud resource 



**NOTE:**

* Complex quantities not suppoprted in this version
* The attributes are mandatory or optional depending on the service used.


###JSON schema:

```
{
"$schema": "http://json-schema.org/draft-04/schema#" 
	"title": "eee_ifcapi_ifcproperty_data",
	"description": "Schema for ifc property data, eeE REST API.",
	"type": "object",
			"properties": {
				"ifcmodel_id":                        {"type": ["string","null"]},
				"ifcelementquantity_globalid":        {"type": ["string","null"]},
				"ifcelementquantity_name":            {"type": ["string","null"]},
				"ifcelementquantity_description":     {"type": ["string","null"]},
				"ifcelementquantity_methodofmeasurement": {"type": ["string","null"]},
				"ifcphysicalquantity_name":           {"type": ["string","null"]},
				"ifcphysicalquantity_description":    {"type": ["string","null"]},
				"ifcphysicalquantity_type":           {"type": ["string","null"]},
				"ifcphysicalquantity_unit":           {"type": ["string","null"]},
				"ifcphysicalquantity_value":          {"type": ["string","null"]},
				"url":                                {"type": ["string","null"]},
				},
			}
	}
}
```

###Example

```
JSON example: 
{
"ifcmodel_id":                    "ABCD",
"ifcelementquantity_globalid":    "2uoYlMTgT9hD",
"ifcelementquantity_name":        "BaseQuantities",
"ifcelementquantity_description": "Example from real model",
"ifcelementquantity_methodofmeasurement": "ArchiCAD BIM Base Quantities",
"ifcphysicalquantity_type":           "ifcquantitylength",
"ifcphysicalquantity_name":           "GrossPerimeter",
"ifcphysicalquantity_description":    "Unit is actually often missing??",
"ifcphysicalquantity_unit":           "m",
"ifcphysicalquantity_value":          "53",
"url":  "http://example.com/ifcapi/v0.4/models/ABCD/ifcelementquantity/2uoYlMTgT9hD/ifcquantitylength/GrossPerimeter"
}
```
