# eeE IFC-API Special Services #

* [Level Up](../README.md)
* [Overview](./README.md)

Version/Date: 2017.03.24 AET/EPM  API v0.5+ (in progress)

## Append file to model


---

### Append file to model - data in local file

**Resource URL  ** POST /ifc-api/{version}/ifcmodel/{model_id}/append_file?file_type={filetype}

element | explanation
--------|-----------|
*ifc-api*	  |Shorthand for eeEmbedded Repository Services |
*version*	  |States version of the API to use, allowing multiple versions of API for upgrading |
*model_id*	  |Identifies which model to append to |
*file_type*	  |"csv" or "xml". If not given "xml" is assumed |
*merge_function*  |The merge function to use, ... see below 
*model_is_external* | Indicates if file is in multipart data (false) or given as url (true)
multipart body	|file data if model_:is_external=false

Parameters can be given in URL or as JSON in multipart body

Returns list of [merge_history_data](./a_schemata/merge_history_data.md) describing the result
<br/>

**Example:** *Append HVAC data to model*

```
POST https://example.com/ifc-api/0.5/ifcmodel/2QMXCU478Hvu00y1_lMzwL/append_file?merge_function=E3MergeBacs1&file_type=xml&EDMSESSIONID=AUTO_LOGIN
Request: [{"model_is_external":false}] (multipart 1)
Request: file data (multipart 2)
Response: see bottom of page
```

---

### Append file to model - data as URL reference

**Resource URL  ** POST /ifc-api/{version}/ifcmodel/{model_id}/append_file?file_type={filetype}

element | explanation
--------|-----------|
*ifc-api*	  |Shorthand for eeEmbedded Repository Services |
*version*	  |States version of the API to use, allowing multiple versions of API for upgrading |
*model_id*	  |Identifies which model to append to |
*file_type*	  |"csv" or "xml". If not given "xml" is assumed |
*merge_function*  |The merge function to use, ... see below 
*model_is_external* | Indicates if file is in multipart data (false=default) or given as url (true)
*model_content*    | URL for the file

Parameters can be given in URL or as JSON in multipart body

Returns list of [merge_history_data](./a_schemata/merge_history_data.md) describing the result objects. 


<br/>

**Example:** *Append HVAC data to model*




```
POST https://example.com/ifc-api/0.5/ifcmodel/2QMXCU478Hvu00y1_lMzwL/append_file?merge_function=E3MergeBacs1&file_type=xml&EDMSESSIONID=AUTO_LOGIN
Request: [{"model_is_external":true,
           "file_url":"https://example.com/externalfile.xml},]
Response: see bottom of page

```

### Merge Functions

These are for the moment eeEmbedded specific.

function | explanation
---------|-----------|
*E3MergeBacs1*	  |Merge in the XML additional data, ref Florian/Milos/Raphael |
*E3MergeSIM1*	  |Merge in the CSV additional data, ref Jens|


### Example Response

```
POST https://example.com/ifc-api/0.5/ifcmodel/2QMXCU478Hvu00y1_lMzwL/append_file?merge_function=E3MergeBacs1&file_type=xml&EDMSESSIONID=AUTO_LOGIN
Request: [{"model_is_external":true,
           "file_url":"https://example.com/externalfile.xml},]
Response:

[{
  "_type_": "vmerge_history_data",
  "start_time": "Fri Mar 24 10:42:33 2017",
  "diagnostics": [
    {
      "_type_": "idcresult",
      "idcmessage": "WARNING:object not found for guid 3CvoAKgsn09uL2tJI5vhj6-1\n",
      "_instance_": 107369887437300,
      "idcseveritylevel": 1
    },
    {
      "_type_": "idcresult",
      "idcmessage": "ERROR:guid and name mismatch for guid=3JZbSbbbzBvfwng5MEV4CW found object same name and different guid=2rL_7_bjH08QojlcDCt3oQ",
      "_instance_": 107369887437453,
      "idcseveritylevel": 11
    },
    {
      "_type_": "idcresult",
      "idcmessage": "E3Services v.0.3 from 2017-03-15 #1E3MergeBACS1 interior completed",
      "_instance_": 107369887437497,
      "idcseveritylevel": 0
    }
  ],
  "resulting_model": {
    "_type_": "vmodel_meta_data",
    "_instance_": 107369887437286,
    "model_id": "E3Tests03_HVAC_V1"
  },
  "target_model": {
    "_type_": "vmodel_meta_data",
    "_instance_": 107369887437286,
    "model_id": "E3Tests03_HVAC_V1"
  },
  "_instance_": 107369887437281,
  "end_time": "Fri Mar 24 10:42:33 2017",
  "source_models": [{
    "_type_": "vmodel_meta_data",
    "_instance_": 107369887437284,
    "model_id": "DummyModel_EDM_RAW_XML"
  }]
}]

```
