# eeE IFC-API Ifc Relation Services #

* [Level Up](../README.md)
* [Overview](./README.md)

Version/Date: 2016.02.20 AET/EPM  API v0.1+ (in progress)


### Classes 

The following main classes are defined:

* [Object - corresponding to IfcRoot in IFC Schema](a_schemata/ifcobject_data.md)
* [Relation - corresponding to IfcRelation in IFC Schema](a_schemata/ifcrelation_data.md)


##Services


###List Relations

**Resource URL**: *GET /ifc-api/{version}/ifcmodel/{model_id}/{ifctype}/{globalid}/relations* ***{filter}***


element | explanation
--------|-----------|
*ifc-api*	|Shorthand for eeEmbedded Repository Services |
*version*	|States version of the API to use, allowing multiple versions of API for upgrading |
*ifcmodel_id*	|Identifies which model top look into |
*ifctype*	|Identifies which ifc type to look for |
{filter}	| JSON expression for filtering relation types in request body, see [filter_data](./schemata/filter_data.md)

Returns list of [ifcrelation_data](./schemata/ifcrelation_data.md) for the found objects. 

**IMPORTANT:**

For single objects, the *type* of the object is in principle superfluous, since 'globalid* will uniquely identify it. Depending on the implementation, the *type* may be used for filtering / double check, but it is not required.

**Example:**

*List all relations for storeys in the model*

```
GET https://example.com/ifc-api/0.4/ifcmodel/12324/ifcbuildingstorey//relations

Request: {filter}

Response:
[{
    “ifcmodel_id”:”12345”
    “ifctype”:”IfcConnectsInSpatialStructure”
    “globalid”:”abcd”,
    “name”:”ConnectBuildingToStoreys”,
    "description":"Note that related is storey in this case"
    “url”:”http://example.com/ifcapi/0.4/models/12345/ifcconnectsinspatialstructure/abcd",
    “relating”:[{"globalid":"BUILDING"}],
    “related”:[{"globalid":"STOREY1"},{"globalid":"STOREY2"}]
},
{
    “ifcmodel_id”:”12345”
    “ifctype”:”IfcConnectsInSpatialStructure”
    “globalid”:”def1",
    “name”:”ConnectStoreyToSpace”,
    "description":"Note relating is storey in this case"
    “url”:”http://example.com/ifcapi/0.4/models/12345/ifcconnectsinspatialstructure/def1",
    “relating”:[{"globalid":"STOREY1"}],
    “related”:[{"globalid":"SPACE11"},{"globalid":"SPACE12"}]
},
{
    “ifcmodel_id”:”12345”
    “ifctype”:”IfcConnectsInSpatialStructure”
    “globalid”:”def2",
    “name”:”ConnectStoreyToSpace”,
    "description":"Note relating is stoprey in this case"
    “url”:”http://example.com/ifcapi/0.4/models/12345/ifcconnectsinspatialstructure/def1",
    “relating”:[{"globalid":"STOREY2"}],
    “related”:[{"globalid":"SPACE21"},{"globalid":"SPACE22"}]
}]
```


###Ref to revious spec


* getRelations (IfcObject) : List of IfcRelation
* getRelations (IfcObject, List of RelationName) : List of IfcRelation

