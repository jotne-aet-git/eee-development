# eeE Delete Attachment Service #

* [Level Up](../README.md)
* [Overview](./README.md)

**Delete a single attachment .**

## Delete Attachment Parameters

Dependent on access method, GUID or URL is used. If URL is used, GUID is part of URL

I/O/opt	| Parameter | Type | Comment |
--------|-----------|------|---------|
In  	|attachment_guid	|String	| attachment unique identifier 
In  	|attachment_url	|String	| URL to attachment data
-|-|-|-|-				
Out  	|attachment_meta_data	|[attachment_meta_data](./a_schemata/attachment_meta_data.md)	| attachment meta data for the deleted attachment


##REST/JSON interface

For consistency, repository_name is given in the resource URL. 


Element | Content|
--------|--------|
**Resource URL** 	|*DELETE /eee-repos/{version}/{repository_name}/attachments/{attachment_guid}*
*eee-repos*			|Shorthand for eeEmbedded Repository Services
*version*			|States version of the API to use, allowing multiple versions of API for upgrading.
*repository_name*	|States which server repository to use. If not given, the default repository will be used. If the repository does not exist, an error will be raised.
*attachment_guid*		|identifies the attachment


JSON Schema not available (trivial)

##REST/JSON Example

This example uses default repository by not supplying one
```

DELETE https://example.com/eee-repos/0.2/attachments/ABCD2233

Request:
	n.a

Response:
{
    "attachment_meta_data ":
    {
		TODO
    }
}


```
