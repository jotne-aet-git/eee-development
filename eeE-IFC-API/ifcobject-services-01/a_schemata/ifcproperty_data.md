## eeE IFCAPI Services Schemata : IFC Property Data ##

* [Level Up](../README.md)
* [Overview](./README.md)

Version/Date: 2016.02.16 AET/EPM  API v0.2+ (in progress)


## IFC Property Data


Same schema is used for IFC properties and IFC property sets, only field usage / contents differ. Only a subset of IfcSimpleProperty property types is supported.


 
 Attribute   | Type | Comment |
-------------|------|---------|
ifcpropertyset.globalid |String|Id for the property set within model 
ifcpropertyset.name |String|Ifc property set name as given in model
ifcpropertyset.description  |String|Human readable description of the property set 
ifcproperty.name |String|Ifc property name as given in model
ifcproperty.description  |String|Human readable description of the property 
ifcproperty.type |String|Ifc property type - entity name for IFC property 
ifcproperty.unit |String|Ifc property unit as a string 
ifcproperty.value |String|Ifc property value as a srting, see table below 
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
				"ifcpropertyset.globalid":    {"type": ["string","null"]},
				"ifcpropertyset.name":        {"type": ["string","null"]},
				"ifcpropertyset.description": {"type": ["string","null"]},
				"ifcproperty.name":           {"type": ["string","null"]},
				"ifcproperty.description":    {"type": ["string","null"]},
				"ifcproperty.type":           {"type": ["string","null"]},
				"ifcproperty.unit":           {"type": ["string","null"]},
				"ifcproperty.value":          {"type": ["string","null"]},
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
	"ifcpropertyset.globalid":    "3564",
	"ifcpropertyset.name":        "MyPropertySet",
	"ifcpropertyset.description": "Example Property Set",
	"url":  "http://example.com/ifcapi/v0.4/models/ABCD/ifcpropertyset/3564"
}
```


###ifcproperty example

```
JSON example: 
{
	"ifcpropertyset.globalid":    "3564",
	"ifcpropertyset.name":        "MyPropertySet",
	"ifcpropertyset.description": "Example Property Set",
	"ifcproperty.type":           "ifcsimpleproperty",
	"ifcproperty.name":           "MyProperty",
	"ifcproperty.description":    "Example Property,
	"ifcproperty.unit":           "text",
	"ifcproperty.value":          "example content",
	"url":  "http://example.com/ifcapi/v0.4/models/ABCD/ifcpropertyset/3564/ifcproperty/MyProperty"
}
```
