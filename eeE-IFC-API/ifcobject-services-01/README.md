## eeE IFCAPI Ifc Object Services ##

[Level Up](../README.md)

Version/Date: 2016.02.03 AET/EPM  API v0.1+ (in progress)

### Classes 

The following main classes are defined:

* Object - generlaized object corresponding to IfcObject in IFC Schema

Schemata defining data structures can be found here: [IFCAPI Object Services Schemata](a_schemata/README.md)

### Services for the classes 

* [IfcType Simple Services](./ifctype_service.md)

Provides instace retrieval based on type 

**IMPORTANT :**

### Navigating to an item in the server

By convention a RESTful interface often implement a list as:

```
 GET  http(s)://<server>/rest_api/<path_to_items>
```

The general eeE IFC-API URL pattern for simple object targeted services is

```
GET <path-to-service>/models/<model_id>/<ifctypename>/[globalid]/[subfunction]/[subfunction_id] 
```

Where:

* Each element contained in <> is mandatory
* Each element contained in [] can be present or not
* If **_id** part is present in the last element return data for indicated item
* If **_id** part is not present in the last element return data for all matching items

Examples:

URL | Result |
--|--|
*GET ../models/ABCD/ifcbuilding/* | retrieve list of all buildings in model *ABCD*
*GET ../models/ABCD/ifcbuilding/1234/ifcpropertysets* | retrieve list of all property sets for building *1234* in model *ABCD*


### Arguments/data in JSON Body versus in URL

In many cases you can find the same elements in the URL as in returned or sent JSON body. In case the same elements are found both places, arguments given as part of URL will take precedence.

**In general, it is recommended to use the URL for navigating in the data when browsing.** Implementation of "LIST" filtering from JSON body is actually optional (!).


### Services related to other object types


TBD:

* [IFC Relation oriented services](ifcrelation_service.md)





