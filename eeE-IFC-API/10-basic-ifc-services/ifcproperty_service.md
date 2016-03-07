# eeE IFC-API Ifc Property Services #

* [Level Up](../README.md)
* [Overview](./README.md)

Version/Date: 2016.03.07 AET/EPM  API v0.30+ (in progress)

### Classes 

The following main classes are defined:

* [Object - generalized object corresponding to IfcObject in IFC Schema](a_schemata/ifcobject_data.md)
* [Property- generalized object corresponding to IfcProperty / IfcPropertySet in IFC Schema](a_schemata/ifcproperty_data.md)


##Services

**NOTE:**

For single objects, the *type* of the object in the URL is in principle superfluous, since '*globalid*' will uniquely identify it. Depending on the implementation, the *type* may be used for filtering / double check, but it is not required.


###List Property Sets

**Resource URL**: *GET /ifc-api/{version}/ifcmodel/{model_id}/{ifctype}/{globalid}/ifcpropertyset

Request: Optional JSONArray in body according to [filter data](a_schemata/filter_data.md)



element | explanation
--------|-----------|
*ifc-api*	|Shorthand for eeEmbedded Repository Services |
*version*	|States version of the API to use, allowing multiple versions of API for upgrading |
*model_id*	|Identifies which model to look into |
*ifctype*	|Identifies which ifc type to look for |
*globalid*	|Identifies the ifc object to look for |

###List Properties

**Resource URL**: *GET /ifc-api/{version}/ifcmodel/{model_id}/{ifctype}/{globalid}/ifcproperty

**Request:** Optional JSONArray in body according to [filter data](a_schemata/filter_data.md), see example further down

**Returns:** list of [ifcproperty_data](./a_schemata/ifcproperty_data.md) for the found objects. 


element | explanation
--------|-----------|
*ifc-api*	|Shorthand for eeEmbedded Repository Services |
*version*	|States version of the API to use, allowing multiple versions of API for upgrading |
*model_id*	|Identifies which model to look into |
*ifctype*	|Identifies which ifc type to look for |
*globalid*	|Identifies the ifc object to look for |



**Example:** *see [Examples](./ifcproperty_service_example.md)


### Optional filter spec in JSON body 


***1) List across multiple models, same object GUID ***

```
GET https://example.com/ifc-api/0.4/ifcmodel/ifcbuildingstorey/ABCD1/ifcproperty
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
GET https://example.com/ifc-api/0.4/ifcmodel/ifcbuildingstorey/ifcproperty
JSON Body:
[
{"ifcmodel_id":"id1","ifcbuildingstorey":"ABDC1"},
{"ifcmodel_id":"id2","ifcbuildingstorey":"EFAA3"},
{"ifcmodel_id":"id3","ifcbuildingstorey":"17E7F"},
...
]
```


