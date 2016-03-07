# eeE IFC-API Ifc Quantity Services #

* [Level Up](../README.md)
* [Overview](./README.md)

Version/Date: 2016.03.07 AET/EPM  API v0.30+ (in progress)

### Classes 

The following main classes are defined:

* [Object - generalized object corresponding to IfcObject in IFC Schema](a_schemata/ifcobject_data.md)
* [Quantity - generalized object corresponding to IfcQuantity / IfcElementQuantity in IFC Schema](a_schemata/ifcquantity_data.md)


##Services

###List Quantities 

**Resource URL**: *GET /ifc-api/{version}/ifcmodel/{model_id}/{ifctype}/{globalid}/ifcelementquantity/{quantity_id}

Request: Optional JSONArray in body according to [filter data](a_schemata/filter_data.md), see example further down

element | explanation
--------|-----------|
*ifc-api*	|Shorthand for eeEmbedded Repository Services |
*version*	|States version of the API to use, allowing multiple versions of API for upgrading |
*model_id*	|Identifies which model top look into |
*ifctype*	|Identifies which ifc type to look for |
*globalid*	|Identifies which ifc object to look for |
*quantity_id*	|Identifies which ifc element quantity (set) to look for |


Returns list of [ifcquantity_data](./a_schemata/ifcquantity_data.md) for the found objects. 


**Example:**

*Get quantities for a window in a building*

```
GET https://example.com/ifc-api/0.4/ifcmodel/12324/ifcwindow/3TpVCxQ6f56B333YrVJfIH
     /ifcelementquantity/0mgYEDYxqlwTNgEnxKzW4y/ifcphysicalquantity
Request: none
Response:
[{
"ifcmodel_id":                    "12324",
"ifcelementquantity_globalid":    "2uoYlMTgT9hD",
"ifcelementquantity_name":        "BaseQuantities",
"ifcelementquantity_description": "Example area",
"ifcelementquantity_methodofmeasurement": "ArchiCAD BIM Base Quantities",
"ifcphysicalquantity_type":           "ifcquantityarea",
"ifcphysicalquantity_name":           "GrossArea",
"ifcphysicalquantity_description":    "",
"ifcphysicalquantity_unit":           "m2",
"ifcphysicalquantity_value":          "1.73240",
"url":"http://example.com/ifcapi/v0.4/models/ABCD/ifcelementquantity/2uoYlMTgT9hD/ifcquantityarea/GrossArea"
},
"ifcmodel_id":                    "12324",
"ifcelementquantity_globalid":    "2uoYlMTgT9hD",
"ifcelementquantity_name":        "BaseQuantities",
"ifcelementquantity_description": "Example weight",
"ifcelementquantity_methodofmeasurement": "ArchiCAD BIM Base Quantities",
"ifcphysicalquantity_type":           "ifcquantityweight",
"ifcphysicalquantity_name":           "GrossWeight",
"ifcphysicalquantity_description":    "",
"ifcphysicalquantity_unit":           "kg,
"ifcphysicalquantity_value":          "24.2",
"url":"http://example.com/ifcapi/v0.4/models/ABCD/ifcelementquantity/2uoYlMTgT9hD/ifcquantityweight/GrossWeight"
}]
```

### Optional filter spec in JSON body 


***1) List across multiple models, same object GUID ***

```
GET https://example.com/ifc-api/0.4/ifcmodel/ifcbuildingstorey/ABCD1/ifcelementquantity
JSON Body:
[
{"ifcmodel_id":"id1"},
{"ifcmodel_id":"id2"},
{"ifcmodel_id":"id3"},
...
]
```

***2) List across multiple models, different object GUID ***

```
GET https://example.com/ifc-api/0.4/ifcmodel/ifcbuildingstorey/ifcelementquantity
JSON Body:
[
{"ifcmodel_id":"id1","ifcbuildingstorey":"ABDC1"},
{"ifcmodel_id":"id2","ifcbuildingstorey":"EFAA3"},
{"ifcmodel_id":"id3","ifcbuildingstorey":"17E7F"},
...
]
```


