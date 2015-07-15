# eeE Upload Model Service #

* [Level Up](../README.md)
* [Overview](./README.md)

## Upload Model Parameters

I/O/opt	| Parameter | Type | Comment |
--------|-----------|------|---------|
In  	|model_meta_data	|[model_meta_data](./a_schemata/model_meta_data.md)	| Model meta data for cretaing, see beolw for madatory fields
In		|model_is_external	|booolean	|Indicates if the model data is supplied as attachment (A2:false) or as a model URL (A1:true), 
In (A1)	|model_content		|URL		| 	A1) URL to model data 
In (A2)	|model_content		|Attachment	|	A2) Input model data as IFC file, XML file, ... 
-|-|-|-|-				
Out 	|Model URL 			|String			|URL to the model data on the target server 
Out 	|Model Meta Data 	|[model_meta_data](./a_schemata/model_meta_data.md)	|Full model meta data as stored on the server.

The following model meta data fields are mandatory:

* project_id :States which project the model belongs to.
* model_name : Name of the model. If a model with this name and same domain already exists, an error will be raised
* model_type : Type of the model (e.g. IFC2x3, IFC4, XML, CSV, ifcXML, ….) 
* domain_name: Tags the model with a named displine/domain. If the discipline does not exist, it will  be created.

##REST interface

Element | Content|
--------|--------|
**Resource URL** 	|*POST /eee-repos/{version}/{repository_name}/models*
*eee-repos*			|Shorthand for eeEmbedded Repository Services
*version*			|States version of the API to use, allowing multiple versions of API for upgrading.

* [Upload Model Schema](upload_model_schema.md)
* [Upload Model Example](upload_model_example.md)

###EDMmodelServer implementation note:
In the first version only alternative A1 is supplied

To upload a file from client the following sequence must be used:

* Upload the file to the server using the [EDM Upload File](edm-file-transfer.md) service
* Apply the Upload Model service using the returned filename as "Model URL"


