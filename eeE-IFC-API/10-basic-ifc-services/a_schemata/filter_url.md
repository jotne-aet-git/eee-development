## eeE IFCAPI Services Schemata : Filter in URL ##

* [Level Up](../README.md)
* [Overview](./README.md)

Version/Date: 2016.02.23 AET/EPM  API v0.2+ (in progress)


## Filter Arguments in URL

Filtering, sorting and similar can oftenb be controlled in RESTful API's by URL arguments. Such is **not** implemented in this version of IFC-API, but anyhow outlined already in case implementation becomes due.

###Selecting fields

Limits the fields retruned from a request. Will only work on "top level" in return.

Syntax: **fields=***name1*,*name2*,...


```
Example: 
    GET http://example.com/ifcapi/0.5/ifcmodel/12332/ifcbuildingstorey?fields=globalid,name

[
	{
		"globalid": "123433" ,
		"name": "U1" 
	},
	{
		"globalid": "1234aa" ,
		"name": "01" 
	},
	...
] 
```


###Sorting fields

Uses value of a named field as sort key. Multiples allowed. Will only work on "top level" in return.


Syntax: **sort=***[-]name1*,*[-]name2*,...

"-" indicates descending sort

```
Example: 
    GET http://example.com/ifcapi/0.5/ifcmodel/12332/ifcbuildingstorey?fields=globalid,name&sort=name

[
	{
		"globalid": "1234aa" ,
		"name": "01" 
	},
	{
		"globalid": "123433" ,
		"name": "U1" 
	},
	...
] 
```

###Filtering with query

Uses a stored query with arguments for filtering. Behaviour depends (of course) of the query.


Syntax: **query=***queryname***&argname1=***argval1*,**&argname2=***argval2*,...

"-" indicates descending sort

```
Example: find all object IDs for object that has a name matching a search pattern
    GET http://example.com/ifcapi/0.5/ifcmodel/12332/ifcbuildingstorey?query=findObjBN&match=*U

[
	{
		"globalid": "123433" 
	},
	...
] 
```
