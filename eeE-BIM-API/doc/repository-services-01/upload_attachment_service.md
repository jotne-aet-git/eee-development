# eeE Upload Attachment Service #

* [Level Up](../README.md)
* [Overview](./README.md)

## Upload Model Parameters

I/O/opt	| Parameter | Type | Comment |
--------|-----------|------|---------|
In  	|attachment_meta_data	|[attachment_meta_data](./a_schemata/attachment_meta_data.md)	| Attachment meta data for creating, see beolw for mandatory fields
In		|attachment_is_external	|booolean	|Indicates if the attachment data is supplied as multii-part attachment (A2:false) or as an URL (A1:true), 
In (A1)	|attachment_content		|URL		| 	A1) URL to attachment data 
In (A2)	|attachment_content		|Attachment	|	A2) Input attachment  data as file
-|-|-|-|-				
Out 	|Attachment URL 		|String			|URL to the attachment data on the target server 
Out 	|Atatchment Meta Data 	|[attachmentl_meta_data](./a_schemata/attachment_meta_data.md)	|Full attachment meta data as stored on the server.

The following attachment meta data fields are mandatory:

* repository_name :States which server repository to use. If not given, the default repository will be used. If the repository does not exist, an error will be raised.
* attachment_name : Name of the attachment', _"filename". If an atachment model with this name already exists, an error will be raised (?)
* attachment_type : Type of the attachment (e.g. IFC2x3, XLS, docx,... ). If the type does not exist, it will  be created.

##REST interface

Element | Content|
--------|--------|
**Resource URL** 	|*POST /eee-repos/{version}/{repository_name}/attachments*
*eee-repos*			|Shorthand for eeEmbedded Repository Services
*version*			|States version of the API to use, allowing multiple versions of API for upgrading.
*repository_name*	|States which server repository to use. If not given, the default repository will be used. If the repository does not exist, an error will be raised.

###Upload Schema Example

**TODO** (possible to construct out of model upload service and attachment_meta_data schema I think)

###Upload Attachment Example

**TODO**


###EDMmodelServer implementation note:

In the first version only alternative A1 is supplied, and only for files residing on the server upload folder.
To upload a file from client the following sequence must be used:

* Upload the file to the server using the [EDM Upload File](edm_file_transfer.md) service
* Apply the Upload Attachment service using the returned filename as "Attachment URL"


