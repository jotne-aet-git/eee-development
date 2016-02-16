# eeE IFC-API Object by type Services #

* [Level Up](../README.md)
* [Overview](./README.md)

Version/Date: 2016.02.16 AET/EPM  API v0.2+ (in progress)

### Classes 

The following main classes are defined:

* [Object - corresponding to Ifc object in IFC Schema](a_schemata/ifcobject_data.md)
* [Relation - corresponding to Ifc relation in IFC Schema](a_schemata/ifcrelation_data.md)

## List Objects by type

**Resource URL**: *GET /ifc-api/{version}/models/{model_id}/{ifctype}/{globalid}

Request: Optional JSONArray in body according to **[todo]**, see example further down.

element | explanation
--------|-----------|
*ifc-api*	|Shorthand for eeEmbedded Repository Services |
*version*	|States version of the API to use, allowing multiple versions of API for upgrading |
*model_id*	|Identifies which model to look into |
*ifctype*	|Identifies which ifc type to look for |
*globalid*	|Identifies which ifc object to look for, if data is wanted for a single object only |

Returns:

* If objects are subtypes of IfcRelationship list of [ifcrelation_data](./a_schemata/ifcrelation_data.md) for the found objects.
* If objects are subtypes of IfcTypeObject of list of [ifcobject_data](./a_schemata/ifcobject_data.md) for the found typeobjects.
* If objects are subtypes of IfcObjectDefinition list of [ifcobject_data](./a_schemata/ifcobject_data.md) for the found objects.
* For other types nothing is returned


**Example:**

*List all building storeys in the model*

```
GET https://example.com/ifc-api/0.4/models/12324/ifcbuildingstorey

Request: none

Response:
[{
    "model_id":"12324",
    "globalid": "ABCD1",
    "name": "U1",
    "description": "Cellar",
    "url":"https://example.com/ifc-api/0.4/models/12324/ifcbuildingstorey/ABCD1"
},
{
    "model_id":"12324",
    "globalid": "ABCD2",
    "name": "01",
    "description": "Ground floor",
    "url":"https://example.com/ifc-api/0.4/models/12324/ifcbuildingstorey/ABCD2"
}]
```

### List objects across models

This is achieved using filter spec in JSON body:


```
GET https://example.com/ifc-api/0.4/models/ifcbuildingstorey

Request: [
{"model_id":"12324"},
{"model_id":"66543"}
]

Response:
[{  "model_id":"12324",
    "globalid": "ABCD1",
    "name": "U1",
    "description": "Cellar",
    "url":"https://example.com/ifc-api/0.4/models/12324/ifcbuildingstorey/ABCD1"
},
{   "model_id":"12324",
    "globalid": "ABCD2",
    "name": "01",
    "description": "Ground floor",
    "url":"https://example.com/ifc-api/0.4/models/12324/ifcbuildingstorey/ABCD2"
},
{   "model_id":"66543",
    "globalid": "ABCD1",
    "name": "U1",
    "description": "Cellar, alternate version",
    "url":"https://example.com/ifc-api/0.4/models/66543/ifcbuildingstorey/ABCD1"
},
{   "model_id":"66543",
    "globalid": "ABCD2",
    "name": "01",
    "description": "Ground floor, alternate version",
    "url":"https://example.com/ifc-api/0.4/models/66543/ifcbuildingstorey/ABCD2"
}]

```

Note that when listing across models, GlobalID is no longer necessarily unique.

## From original spec


```
getGlobalID (IfcObject) : IfcObjectID
AET Comment: Obsolete, special case of getGlobalIds below

getGlobalIDs (List of IfcObject) : List of IfcObjectID
AET Comment: Not understood – when/how do we need this method?

getIfcEntity (IfcObjectID) : IfcObject
AET Comment: Obsolete, special case of getIfcEntities below
```

getIfcEntities (List of IfcObjectID) : List of IfcObject

```
Synopsis 	Returns an object array containing one instance for each ifcObjectId (GlobalID, guid) in input argument. If at least one of the input GUIDs does not have a matching instance in the model, an error condition is raised. If input list is empty, an empty list is returned. 
Input 	List of IfcObjectIds (strings) 
Output 	Array of matching objects all entities of the input type  ( IfcObject [] or subtype of this ) 
REST/JSON query example	GET <url-to-model>/<prefix>/ifcentity/
[{“ifcobjectid”:”ABCD”}, {“ifcobjectid”:”EF12”}]
JSON Return example 	[
 {“IfcType”:“IfcBuildingStorey”,”GlobalID”:”ABCD”,“Name”:”01”,..},
 {“IfcType”:“IfcBuildingStorey”,”GlobalID”:”EF12”,“Name”:”02”,..}
]
Code example:
Depends on programming language
```
