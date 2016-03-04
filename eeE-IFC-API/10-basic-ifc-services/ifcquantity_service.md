# eeE IFC-API Ifc Quantity Services #

* [Level Up](../README.md)
* [Overview](./README.md)

Version/Date: 2016.02.24 AET/EPM  API v0.2+ (in progress)

### Classes 

The following main classes are defined:

* [Object - generalized object corresponding to IfcObject in IFC Schema](a_schemata/ifcobject_data.md)
* [Quantity - generalized object corresponding to IfcQuantity / IfcElementQuantity in IFC Schema](a_schemata/ifcquantity_data.md)


##Services

###List Quantities 

**Resource URL**: *GET /ifc-api/{version}/ifcmodel/{model_id}/{ifctype}/{globalid}/quantities

Request: Optional JSONArray in body according to [filter data](a_schemata/filter_data.md), see example further down

element | explanation
--------|-----------|
*ifc-api*	|Shorthand for eeEmbedded Repository Services |
*version*	|States version of the API to use, allowing multiple versions of API for upgrading |
*ifcmodel_id*	|Identifies which model top look into |
*ifctype*	|Identifies which ifc type to look for |

Returns list of [ifcquantity_data](./a_schemata/ifcquantity_data.md) for the found objects. 

**IMPORTANT:**

For single objects, the *type* of the object is in principle superfluous, since 'globalid* will uniquely identify it. Depending on the implementation, the *type* may be used for filtering / double check, but it is not required.

**Example:**

*List quantities for all building storeys in the model*

```
GET https://example.com/ifc-api/0.4/ifcmodel/12324/ifcbuildingstorey/ABCD1/quantities

Request: none

Response:
[{
    "globalid": "ABCD1",
    "name": "U1",
    "description": "Cellar",
},
{
    "globalid": "ABCD2",
    "name": "01",
    "description": "Ground floor",
}]
```

### Optional filter spec in JSON body 


***1) List across multiple models, same object GUID ***

```
GET https://example.com/ifc-api/0.4/ifcmodel/ifcbuildingstorey/ABCD1/quantities
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
GET https://example.com/ifc-api/0.4/ifcmodel/ifcbuildingstorey/quantities
JSON Body:
[
{"ifcmodel_id":"id1","ifcbuildingstorey":"ABDC1"},
{"ifcmodel_id":"id2","ifcbuildingstorey":"EFAA3"},
{"ifcmodel_id":"id3","ifcbuildingstorey":"17E7F"},
...
]
```


