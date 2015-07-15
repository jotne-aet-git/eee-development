# eeE Download Attachment Service #

* [Level Up](../README.md)
* [Overview](./README.md)

**Retrieve a single attachment as a file with its own encoding and format.**

## Download Attachment Parameters

Dependent on access method, GUID or URL is used. If URL is kused, GUID is part of URL

I/O/opt	| Parameter | Type | Comment |
--------|-----------|------|---------|
In  	|attachment_guid	String	| Attachment unique identifier 
In  	|attachment_url	   |String	| URL to attachment data
-|-|-|-|-				
Out 	|Attachment Data	|binary stream			| attachment data from target server 


##REST/JSON interface

For consistency, repository_name is given in the resource URL. 

Element | Content|
--------|--------|
**Resource URL** 	|*GET /eee-repos/{version}/{repository_name}/attachments/{attachment_guid}*
*eee-repos*			|Shorthand for eeEmbedded Repository Services
*version*			|States version of the API to use, allowing multiple versions of API for upgrading.
*repository_name*	|States which server repository to use. If not given, the default repository will be used. If the repository does not exist, an error will be raised.
*attachment_guid*	|identifies the attachment


JSON Schema not available (trivial)

##REST/JSON Example

This example uses default repository by not supplying one
```

GET https://example.com/eee-repos/0.2/attachments/DBCAA9977

Request:
	n.a

Response:
	Attachmet data as “file”


```
