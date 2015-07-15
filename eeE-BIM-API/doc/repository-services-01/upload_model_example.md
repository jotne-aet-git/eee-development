## eeE Upload Model Example ##

* [Level Up](../README.md)
* [Overview](./README.md)
* [Upload Model Service](./upload_model_service.md)
* [Upload Model Example](./upload_model_example.md)

The schema for “model-meta-data” is same for response and request, but the rules for which fields that are supplied are different.

###JSON Example
```
POST https://example.com/eee-repos/0.3/models

Request:
{
    "model_meta_data ":
  	{
	    "project_id ": "munchen-parkhaus",
	    "model_name ": "HVAC_alt_2",
   	    "model_type ": "IFC4",
        "description ": "Alternative 2 for the HVAC solution of Use Case 1",
        "domain_name": "HVAC",
	}
    "model_is_external": true,
    "model_content ": "https://bimplus/&HVAC",
}


Response:
{
    "model_url ": "http://example.com/eee-repos/0.3/models/ADFE23AA11BCFF444122BB",
    "model_meta_data ":
   	{
        "model_guid ": "ADFE23AA11BCFF444122BB",
	    "project_id ": "munchen-parkhaus",
	    "model_name ": "HVAC_alt_2",
	    "model_type ": "IFC4",
	    "description ": "Alternative 2 for the HVAC solution of Use Case 1",
	    "domain_name": "HVAC",
    }
}

```