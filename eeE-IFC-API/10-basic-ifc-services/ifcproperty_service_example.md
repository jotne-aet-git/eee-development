# eeE IFC-API Ifc Property Service Examples #

* [Level Up](../README.md)
* [Overview](./README.md)
* [Property Service](./ifcproperty_service.md)

Version/Date: 2016.03.04 AET/EPM  API v0.30+ (in progress)


##List Properties

###List Properties for single object

**Example:** *List properties for a building storey*

```
GET https://example.com/ifc-api/0.4/ifcmodel/12324/ifcbuildingstorey/ABCD1/ifcproperty
Request: none
Response:
[{
	"ifcmodel_id":    	      "12324",
	"ifcpropertyset_globalid":    "3564",
	"ifcpropertyset_name":        "MyPropertySet",
	"ifcpropertyset_description": "Example Property Set",
	"ifcproperty_type":           "ifcsimpleproperty",
	"ifcproperty_name":           "MyProperty",
	"ifcproperty_description":    "Example Property,
	"ifcproperty_unit":           "text",
	"ifcproperty_value":          "example content",
	"url":  "http://example.com/ifcapi/v0.4/ifcmodel/12324/ifcpropertyset/3564/ifcproperty/MyProperty"
}]
```

##List Property Sets

###List Property sets across models

**Example:** *List property sets for same building storey across models*

```
GET https://example.com/ifc-api/0.4/ifcmodel/ifcbuildingstorey/ABCD1/ifcpropertyset
Request: [{"ifcmodel_id":"id1"},
	  {"ifcmodel_id":"id2"},
	  {"ifcmodel_id":"id2"}]
Response:
[{
	"ifcmodel_id":    	      "id1",
	"ifcpropertyset_globalid":    "3564",
	"ifcpropertyset_name":        "MyPropertySet",
	"ifcpropertyset_description": "Example Property Set",
	"url":  "http://example.com/ifcapi/v0.4/ifcmodel/id1/ifcpropertyset/3564"
},
{
	"ifcmodel_id":    	      "id2",
	"ifcpropertyset_globalid":    "3564",
	"ifcpropertyset_name":        "MyPropertySet",
	"ifcpropertyset_description": "Example Property Set",
	"url":  "http://example.com/ifcapi/v0.4/ifcmodel/id2/ifcpropertyset/3564"
}]
```

The models ***id1*** and ***id2*** contains the property set, while ***id3*** does not.



