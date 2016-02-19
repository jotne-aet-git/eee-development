## eeE IFCAPI Ifc Object Services ##

[Level Up](../README.md)
[Overview](./README.md)

Version/Date: 2016.02.16 AET/EPM  API v0.1+ (in progress)

### Classes 

Schemata defining data structures can be found here: [IFCAPI Object Services Schemata](a_schemata/README.md)

### Services for the classes 


Service kit| Description|
--|--|
[IfcObject Services](./ifcobject_service.md)| Provides instance retrieval based on object type 
[IfcProperty Services](./ifcproperty_service.md)| Provides property retrieval and update for simple properties
[IfcRelation Oriented Services](./ifcrelation_service.md)| Provides retrieval of relations between objects




## Services from original spec that is not understood or superfluous


3.2	Metadata Functions

###Data types

Basic JSON types are IfcObject and IfcClass. 

IfcObject

Return a representation of an IFC object. The object is labelled with an ifcObjectType indication its class, 	for example IfcBuilding or IfcWindow.

```
Schema 	TODO 
JSON example: 
{   “IfcType”:”IfcBuilding”
    “GlobalId”:”abcd”,
    “Name”:”Bierhaus”,
    “<attrname>”:”<value>,
    ...
}
```

IfcClass

Representation of an IFC entity type. The entity is identifiable with a single string corresponding to its class, for example “IfcBuilding” or” IfcWindow”.

```
Schema 	TODO 
JSON example: 
{   “IfcClass”:”IfcBuilding”,”exact”:”true” }    “exact” is optional

Java early binding example: 
className := IfcBuilding.getClass().name;   // “exact” not possible to express here
```

###Services

AET Comment: it is not Entities we “get”, it is objects. So probably better naming is: “getObjectsOf”, “getObjectsOfExactType” etc. However, I have kept naming as original proposal for the moment.

* getEntitiesOf (IfcClass) : List of IfcObject
* getEntitiesOfExactType (IfcClass) : List of IfcObject

Returns an entity array containing all instances of the specified entity data type. For getEntitiesOfExactType the instances of subtype of the specified entity data type are not included, for getEntitiesOfType the subtypes are included. 

This function can be useful e.g. to select all building storeys or all “standard case” walls 
in a given building complex, regardless of the number of buildings and respective BIM files used to model it. 

```
Input 	A single IfcClass 
Output 	Array of matching objects ( IfcObject []) 

REST/JSON query example	
    GET <url-to-model>/<prefix>/ifcentity/
    {“IfcClass”:”IfcBuildingStorey”, “exact”:”true”}
 --or—
    GET <url-to-model>/<prefix>/ifcentity/IfcBuildingStorey/exact

JSON Return example 	
[
 {“IfcType”:“IfcBuildingStorey”,”GlobalID”:”ABCD”,“Name”:”01”,..},
 {“IfcType”:“IfcBuildingStorey”,”GlobalID”:”EF12”,“Name”:”02”,..}
]

Code example:Depends on programming language
```

* getInstancesOf (IfcClass) : List of IfcObject 

*AET Comment: is this different from getEntitiesOf?*

* existInstancesOf (IfcClass) : true | false

*AET Comment: can be built on top of getEntitiesOf, at least until we get a performance problem….*

* getAttributes (IfcObjectID) : Array of Attributes
* getAttribute (IfcObjectID) : AttributeValue

*AET Comment: with my interpretation of “IfcObject” simple attributes like name, comment, description etc are always returned, the object attributes like IfcOwnerHistory is neglected. Do we need them? *

* getReferences (IfcObjectID, refAttribute) : List of IfcObject

*AET Comment: for relations, which is most central, I created a service set in section further down. I hope we do not need those references that are not going through any relations.*

* union (List of IfcObject1, List of IfcObject2) : List of IfcObject 

*AET Comment: this one was too advanced for me right now…*

