## eeE IFCAPI Services Schemata : Filter Data ##

* [Level Up](../README.md)
* [Overview](./README.md)

Version/Date: 2016.02.19 AET/EPM  API v0.2+ (in progress)


## Filter Data

Filter data is supplied in service request body as "function arguments" to modify the default behaviour of any service. For some services, the filter data is mandatory.

The "schema" for filter data is more like a guideline than an accurate spec, since the actual content may differ from service to service. Details should be supplied in the service documentation page.

The general format is quite simple - arguments are array of JSON objects that contain either strings or arrays of strings:

```
[
	{
		"name": "value" ,
		"name": "value" ,
		"name": [{"value"},{"value"},...],
		...
	},
	{
		"name": "value" ,
		"name": "value" ,
		"name": [{"value"},{"value"},...],
		...
	},
	...
] 
```

The most common use of the **filter_data** is to restrict the range of objects returned. Example: 

```
Get storey "abcd": 
    GET http://example.com/ifcapi/0.5/ifcmodel/12332/ifcbuildingstorey/abcd

Get all storeys: 
    GET http://example.com/ifcapi/0.5/ifcmodel/12332/ifcbuildingstorey

Get specific storeys: 
    GET http://example.com/ifcapi/0.5/ifcmodel/12332/ifcbuildingstorey
    Request body: [ {"globalid":"abcd"},{"globalid":"deff"}]	
```
 
The "attribute names" like "globalid" above in general matches the attribute names in expected JSON return. These names can be found in the data specification  pages listed in the [Overview](./README.md).


**NOTE**: The "attribute name" for the IFC model itself is **ifcmodel_id**. 

### Argument precedence

As you might have seen, an argument can be given both in URL and in body, and also as URL argument in many cases. The following requests are equivalent:

```
GET http://example.com/ifcapi/0.5/ifcmodel/12332/ifcbuildingstorey/abcd
GET http://example.com/ifcapi/0.5/ifcmodel/12332/ifcbuildingstorey [{"globalid":"abcd"}]	
GET http://example.com/ifcapi/0.5/ifcmodel/12332/ifcbuildingstorey?globalid=abcd	
```

If an argument is supplied in  multiple places the precedence is as follows:

* Argument given as URL argument overrides argument in URL itself 
* Argument given as URL argument overrides argument in JSON body
* Argument given in JSON body overrides arguent in URL itself

However, it is considered rude and unwise to create such conflicts.
 

##Examples


###List all relations for given set of storeys in the model

```
Request URL : GET https://example.com/ifc-api/0.4/ifcmodel/12324/ifcbuildingstorey/relations
Request body: [{"globalid":"STOREY1"},{"globalid":"STOREY2"}]
```


### List all building storeys in multiple models

```
Request URL : GET https://example.com/ifc-api/0.4/ifcmodel/ifcbuildingstorey
Request body: [{"ifcmodel_id":"12324"},{"ifcmodel_id":"66543"}]
```


### List all different building storeys in spearate models

This is how to select distinct objects in separate models.

```
Request URL : GET https://example.com/ifc-api/0.4/ifcmodel/ifcbuildingstorey/properties
Request body:
   [{"ifcmodel_id":"id1","ifcbuildingstorey":"ABDC1"},
    {"ifcmodel_id":"id2","ifcbuildingstorey":"EFAA3"},
    {"ifcmodel_id":"id3","ifcbuildingstorey":"17E7F"}  ]
```
















