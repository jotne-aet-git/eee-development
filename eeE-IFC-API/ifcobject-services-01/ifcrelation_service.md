# eeE IFC-API Ifc Relation Services #

* [Level Up](../README.md)
* [Overview](./README.md)

Version/Date: 2016.02.03 AET/EPM  API v0.1+ (in progress)

*** THIS PAGE RATHER RATHER BUGRIDDEN RIGHT NOW!!!***


### Classes 

The following main classes are defined:

* [Object - generalized object corresponding to IfcObject in IFC Schema](a_schemata/ifcobject_data.md)
* [Relation - generalized object corresponding to IfcRelation in IFC Schema](a_schemata/ifcrelation_data.md)


##Services


Ref to original spec:

* getRelations (IfcObject) : List of IfcRelation
* getRelations (IfcObject, List of RelationName) : List of IfcRelation

```
Synopsis 	Returns an object array containing one instance for each relation where the indicated IFC object is the relating object. If at least one of indicated relation types is unsupported,  an error condition is raised. If input list is empty, all relations are returned. 
Input 	List of IfcObjectIds (strings) 
Output 	Array of matching relation objects 
REST/JSON query example	GET <url-to-model>/<prefix>/ifcentity/guid/ifcrelation
[{“IfcRelationType”:”IfcRelType1”}, {“IfcRelationType”:”IfcRelType2”}]
JSON Return example 	[
{“IfcType”:“ IfcRelType1”,”GlobalID”:”ABCD”,“Name”:”01”,...,
  “RelatedObjects”:[<guid>,<guid>,<guid>]     
 },
{“IfcType”:“ IfcRelType2”,”GlobalID”:”EF01”,“Name”:”02”,...,
  “RelatedObjects”:[<guid>,<guid>,<guid>]
 }
]
```





###List Relations

**Resource URL**: *GET /ifc-api/{version}/models/{model_id}/{ifctype}/{globalid}/relations

element | explanation
--------|-----------|
*ifc-api*	|Shorthand for eeEmbedded Repository Services |
*version*	|States version of the API to use, allowing multiple versions of API for upgrading |
*model_id*	|Identifies which model top look into |
*ifctype*	|Identifies which ifc type to look for |
{filter}	| JSON expression for filtering relation types in request body

Returns list of [ifcrelation_data](./schemata/ifcrelation_data.md) for the found objects. 

**IMPORTANT:**

For single objects, the *type* of the object is in principle superfluous, since 'globalid* will uniquely identify it. Depending on the implementation, the *type* may be used for filtering / double check, but it is not required.

**Example:**

*List all relations for storeys in the model*

```
GET https://example.com/ifc-api/0.4/models/12324/ifcbuildingstorey//relations

Request: {filter}

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


