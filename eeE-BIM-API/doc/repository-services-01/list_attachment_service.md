# eeE List Attachments Service #

* [Level Up](../README.md)
* [Overview](./README.md)

**Retrieve a collection of attachments descriptions where the currently logged on user has read access.**

## List Attachment Parameters

I/O/opt	| Parameter | Type | Comment |
--------|-----------|------|---------|
In  	|attachment_meta_data	|[attachment_meta_data](./a_schemata/attachment_meta_data.md)	| Meta data for filtering, see below for mandatory fields
-|-|-|-|-				
Out 	|Attachment URL			|String			|URL to the attachment data on the target server 
Out 	|Attachment Meta Data 	|[attachment_meta_data](./a_schemata/attachment_meta_data.md)	|Full attachment meta data as stored on the server.

The following attachment meta data fields are mandatory:

* repository_name :States which server repository to use. If not given, the default repository will be used. If the repository does not exist, an error will be raised.

The following attachment meta data fields are used for filtering:

* model_guid : Only return attachments for this model (version dependent)
* model_name : Only return attachments for this model (version independent)


##REST/JSON interface

repository_name is given in the resource URL

Element | Content|
--------|--------|
**Resource URL** 	|*GET /eee-repos/{version}/{repository_name}/attachments*
*eee-repos*			|Shorthand for eeEmbedded Repository Services
*version*			|States version of the API to use, allowing multiple versions of API for upgrading.
*repository_name*	|States which server repository to use. If not given, the default repository will be used. If the repository does not exist, an error will be raised.

JSON Schema not available (trivial)

##REST/JSON Example

This example shows two versions of one attachment and one version of another
```
GET https://example.com/eee-repos/0.2/munchen-parkhaus/attachments

Request:
	n.a

Response:
[{
	TODO
},
{
	TODO
},
[{
	TODO
}]
```
