# eeE IFC-API Special Services #

* [Level Up](../README.md)
* [Overview](./README.md)

Version/Date: 2017.03.23 AET/EPM  API v0.30+ (in progress)

## Append file to model


---

### Append file to model - data in local file

**Resource URL  ** POST /ifc-api/{version}/multimodels/{model_id}/append_file?file_type={filetype}

element | explanation
--------|-----------|
*ifc-api*	|Shorthand for eeEmbedded Repository Services |
*version*	|States version of the API to use, allowing multiple versions of API for upgrading |
*model_id*	|Identifies which model to append to |
*file_type*      | Domain ftype ids like ESIM, BACS, ... see **TODO**
multipart body	|file data 

<br/>

**Example:** *Append ESIM data to model*

```
POST https://example.com/ifc-api/0.4/multimodels/12324/append_file?file_type=ESIM
Request: (file data in multipart body)
Response:
[{ blabla
},
{  blabla
}]
```


---

### Append file to model - data as URL reference


**Resource URL  ** POST /ifc-api/{version}/multimodels/{model_id}/append_url?filetype={filetype}

element | explanation
--------|-----------|
*ifc-api*	|Shorthand for eeEmbedded Repository Services |
*version*	|States version of the API to use, allowing multiple versions of API for upgrading |
*model_id*	|Identifies which model to append to |
*filetype*      | Domain ftype ids like ESIM, BACS, ... see **TODO**
*fileurl*       | URL to the file to fetch

<br/>

JSON body element| type  | explanation
-----------------|-------|----
*file_url*   |string| URL of the file to use as append input

<br/>

**Example:** *Append ESIM data to model*

```
POST https://example.com/ifc-api/0.4/multimodels/12324/append?fileurl=
Request: [{"file_type":"ESIM",
           "file_url":"https://eee.org/store/ESIM?f=esimfile.csv"
         }]
Response:
    [{ blabla
    },
    {  blabla
    }]
```


