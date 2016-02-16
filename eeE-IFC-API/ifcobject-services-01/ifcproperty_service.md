# eeE IFC-API Ifc Property Services #

* [Level Up](../README.md)
* [Overview](./README.md)

Version/Date: 2016.02.16 AET/EPM  API v0.1+ (in progress)

### Classes 

The following main classes are defined:

* [Object - generalized object corresponding to IfcObject in IFC Schema](a_schemata/ifcobject_data.md)
* [Property- generalized object corresponding to IfcProperty / IfcPropertySet in IFC Schema](a_schemata/ifcproperty_data.md)


##Services

###List Properties

**Resource URL**: *GET /ifc-api/{version}/models/{model_id}/{ifctype}/{globalid}/properties

element | explanation
--------|-----------|
*ifc-api*	|Shorthand for eeEmbedded Repository Services |
*version*	|States version of the API to use, allowing multiple versions of API for upgrading |
*model_id*	|Identifies which model top look into |
*ifctype*	|Identifies which ifc type to look for |

Returns list of [ifcproperty_data](./schemata/ifcproperty_data.md) for the found objects. 

**IMPORTANT:**

For single objects, the *type* of the object is in principle superfluous, since 'globalid* will uniquely identify it. Depending on the implementation, the *type* may be used for filtering / double check, but it is not required.

**Example:**

*List all building storeys in the model*

```
GET https://example.com/ifc-api/0.4/models/12324/ifcbuildingstorey/ABCD1/properties

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





```
getPropertySets(IfcObject) : List of IfcPropertySet
getPropertySets (IfcObject, List of PropertySetName) : List of IfcPropertySet
```
Synopsis 	Returns an object array containing named property sets for an IFC instance. If not found, an error condition is raised. 
Input 	List of property set names. If not given, all property sets are returned.
Output 	Array of matching property sets for the found object. 
REST/JSON query example	GET <url-to-model>/<prefix>/ifcentity/<guid>/ifcpropertyset
[ {“Name”:”ABCD”}, {“Name”:”EF12”} ]
JSON Return example 	[
 {”GlobalID”:”ABCD”,“Name”:”01”,..},
 {”GlobalID”:”EF12”,“Name”:”02”,..}
]
Code example: 
Depends on programming language

```
getProperties (IfcPropertySet) : List of IfcProperty
```
Synopsis 	Returns an object array containing named properties in an IFC property set. If not found, an error condition is raised. 
Input 	List of property names. If not given, all properties are returned.
Output 	Array of matching property sets for the found object. 
REST/JSON query example	GET <url-to-model>/<prefix>/ifcpropertyset/<guid>/ifcproperty
[ {“Name”:”ABCD”}, {“Name”:”EF12”} ]
JSON Return example 	[
 {“IfcType”:”IfcRealProperty”,”GlobalID”:”ABCD”,“Name”:”01”,..},
 {“IfcType”:”IfcRealProperty”,”GlobalID”:”EF01”,“Name”:”02”,..},
]
Code example: 
Depends on programming language

```
getPropertyValues (List of IfcObjectId) : List Of IfcPropertyValue
```
Synopsis 	Returns an object array containing property values for identified IFC properties. If not found, an error condition is raised. Function is intended for a compact transfer of property values.
Input 	List of property ids as list of IfcObjectIds. If not given, empty list is returned.
Output 	Array of matching property values for the found objects. 
REST/JSON query example	GET <url-to-model>/<prefix>/ifcproperty
[ {“GlobalId”:”ABCD”}, {“GlobalId”:”EF12”} ]
JSON Return example 	[ 
 {“IfcType”:”IfcRealValue”,”Value”:”3.14”,“Unit”:”Radians”,..},
 {“IfcType”:”IfcRealValue”,”Value”:”3.14”,“Unit”:”Radians”,..},
]
AET Comment: should values be identified by property global id?
Code example: 
Depends on programming language



-- BELOW HERE IS NOT RELEVANT YET --
