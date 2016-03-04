# eeE IFC-API Extract Services #

* [Level Up](../README.md)
* [Overview](./README.md)

Version/Date: 2016.03.03 AET/EPM  API v0.30+ (in progress)

## List Extract Endpoints

**Resource URL**: *GET /ifc-api/{version}/multimodels/{model_id}/extract

element | explanation
--------|-----------|
*ifc-api*	|Shorthand for eeEmbedded Repository Services |
*version*	|States version of the API to use, allowing multiple versions of API for upgrading |
*model_id*	|Identifies which model to look into |

Returns list of [extract_endpoint_data](./a_schemata/extract_endpoint_data.md) for the found objects. 

**NOTE:** Extract endpoints are not defined in this service, they are inherent properties of the model itself!

**Example:** *List system extract endpoints in model*

```
GET https://example.com/ifc-api/0.4/multimodels/12324/extract/?filter="system_id"
Request: none
Response:
[{ "system_id":"522110",
   "system_name":"Energy System"
},
{  "system_id":"522111",
   "system_name":"BACS System"
}]
```



## Extract endpoint

**Purpose**: Extract partial model according to an endpoint(axis) as IFC file

**Resource URL**: *GET /ifc-api/{version}/multimodels/{model_id}/extract/**endpoint**


URL element | explanation
--------|-----------|
*ifc-api*	|Shorthand for eeEmbedded Repository Services |
*version*	|States version of the API to use, allowing multiple versions of API for upgrading |
*model_id*	|Identifies primary model. Alternatively given as part of JSON body argument |
***endpoint***	|Identifies type of extract: discipline, system, spatial,... |

<br/>

Layout of ***endpoint*** depends on the type of endpoint, see [extract_endpoint_data](./a_schemata/extract_endpoint_data.md) for more. Using discipline as an example it looks like this:

* As part of URL: *GET /ifc-api/{version}/multimodels/{model_id}/extract/discipline_id=****guid***
* As URL argument: *GET /ifc-api/{version}/multimodels/{model_id}/extract?discipline_id=****guid***
* As JSON body: *GET /ifc-api/{version}/multimodels/{model_id}/extract* ***{"discipline_id"="guid"}***

Additional arguments can be supplied as follows:

JSON body element| type  | explanation
-----------------|-------|----
*todo_argument*	 |string | TODO: necessary arguments to be identified

<br/>


### Extract according to spatial endpoint

Returns data for a spatial level, for example *Building Storey*


**Example:** *Extract data for building storey 02*

```
GET https://example.com/ifc-api/0.4/multimodels/12324/extract 
Request: {"spatial_object_id"="98202"} 
Response: Building Storey 02 as IFC
```

### Extract according to discipline endpoint

Returns data for a discipline, for example *HVAC*


**Example:** *Extract HVAC data*

```
GET https://example.com/ifc-api/0.4/multimodels/12324/extract/discipline_id=ac1232
Request: none
Response: HVAC data as IFC
```


### Extract according to system endpoint

Returns data for a system, for example *Energy System*

**Example:** *Extract Energy System data*

```
GET https://example.com/ifc-api/0.4/multimodels/12324/extract?system_id=52201
Request: none
Response: Energy System data as IFC

```
