# eeE IFC-API Object by type Services #

* [Level Up](../README.md)
* [Overview](./README.md)

Version/Date: 2016.02.16 AET/EPM  API v0.1+ (in progress)

## List Objects by type

**Resource URL**: *GET /ifc-api/{version}/models/{model_id}/{ifctype}

element | explanation
--------|-----------|
*ifc-api*	|Shorthand for eeEmbedded Repository Services |
*version*	|States version of the API to use, allowing multiple versions of API for upgrading |
*model_id*	|Identifies which model top look into |
*ifctype*	|Identifies which ifc type to look for |

Returns list of [ifcobject_data](./schemata/ifcobject_data.md) for the found objects. 
To control whether exact types or types with subtypes is retturned use larguments in JSON body or URL {filter} 

**Example:**

*List all building storeys in the model*

```
GET https://example.com/ifc-api/0.4/models/12324/ifcbuildingstorey

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
