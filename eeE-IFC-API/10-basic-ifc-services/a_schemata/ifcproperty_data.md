## eeE IFCAPI Services Schemata : IFC Property Data ##

* [Level Up](../README.md)
* [Overview](./README.md)

Version/Date: 2016.02.19 AET/EPM  API v0.2+ (in progress)


## IFC Property Data


Same schema is used for IFC properties and IFC property sets, only field usage / contents differ. Only a subset of IfcSimpleProperty property types is supported.


 
 Attribute   | Type | Comment |
-------------|------|---------|
ifcpropertyset_globalid |String|Id for the property set within model 
ifcpropertyset_name |String|Ifc property set name as given in model
ifcpropertyset_description  |String|Human readable description of the property set 
ifcproperty_name |String|Ifc property name as given in model
ifcproperty_description  |String|Human readable description of the property 
ifcproperty_type |String|Ifc property type - entity name for IFC property 
ifcproperty_unit |String|Ifc property unit as a string 
ifcproperty_value |String|Ifc property value as a srting, see table below 
url |String| How to identify the object as a cloud resource 

Applicable property types versus value string:


 IfcProperty type | value string example | comment
--|--|
IfcPropertySimpleValue | *"value"* | 
IfcPropertyEnumeratedValue | *"value"* | No difference from simple value
IfcPropertyBoundedValue | *"'min','max'"* | Values are surrounded by single quotes, *nil* symbolized by ''
IfcPropertyTableValue |  | *-- not supported --*
IfcPropertyReferenceValue |  | *-- not supported --*
IfcPropertyListValue | *"'value1','value2','value3',..."* | Values are surrounded by single quotes

The attributes are mandatory or optional depending on the service used.



###JSON schema:

```
{
"$schema": "http://json-schema.org/draft-04/schema#" 
	"title": "eee_ifcapi_ifcproperty_data",
	"description": "Schema for ifc property data, eeE REST API.",
	"type": "object",
			"properties": {
				"ifcpropertyset_globalid":    {"type": ["string","null"]},
				"ifcpropertyset_name":        {"type": ["string","null"]},
				"ifcpropertyset_description": {"type": ["string","null"]},
				"ifcproperty_name":           {"type": ["string","null"]},
				"ifcproperty_description":    {"type": ["string","null"]},
				"ifcproperty_type":           {"type": ["string","null"]},
				"ifcproperty_unit":           {"type": ["string","null"]},
				"ifcproperty_value":          {"type": ["string","null"]},
				"url":          {"type": ["string","null"]},
				},
			}
	}
}
```

###ifcpropertyset example

```
JSON example: 
{
	"ifcpropertyset_globalid":    "3564",
	"ifcpropertyset_name":        "MyPropertySet",
	"ifcpropertyset_description": "Example Property Set",
	"url":  "http://example.com/ifcapi/v0.4/models/ABCD/ifcpropertyset/3564"
}
```


###ifcproperty example

```
JSON example: 
{
	"ifcpropertyset_globalid":    "3564",
	"ifcpropertyset_name":        "MyPropertySet",
	"ifcpropertyset_description": "Example Property Set",
	"ifcproperty_type":           "ifcsimpleproperty",
	"ifcproperty_name":           "MyProperty",
	"ifcproperty_description":    "Example Property,
	"ifcproperty_unit":           "text",
	"ifcproperty_value":          "example content",
	"url":  "http://example.com/ifcapi/v0.4/models/ABCD/ifcpropertyset/3564/ifcproperty/MyProperty"
}
```
