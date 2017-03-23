# eeE IFC-API Special Services #

* [Level Up](../README.md)
* [Overview](./README.md)

Version/Date: 2017.03.23 AET/EPM  API v0.30+ (in progress)

## Append file to model

**Resource URL**: *POST /ifc-api/{version}/multimodels/{model_id}/append_file

element | explanation
--------|-----------|
*ifc-api*	|Shorthand for eeEmbedded Repository Services |
*version*	|States version of the API to use, allowing multiple versions of API for upgrading |
*model_id*	|Identifies which model to append to |

Returns list of [merge_massage](./a_schemata/merge_message.md) giving the result of the operation. 

The intention of this service is to enruich an IFC model with data in a "known" format like ESIM results or BACKS control lists

**Example:** *Append ESIM data to model*

```
POST https://example.com/ifc-api/0.4/multimodels/12324/append
Request: [{"file_template":"ESIM"}]
Response:
[{ blabla
},
{  blabla
}]
```
