## eeE BIM-API Model Services ##

[Level Up](../README.md)
[Overview](./README.md)

Version/Date: 2015.07.10 AET/EPM  API v0.4+ (in progress)

### IMPORTANT

It is proposed to use **name locking** between multi-model and model. That is, any model is enforced to have the same name (*model_name*) as the multi-model it is a part of. To designate between the models in a multi-model, the *domain_name* has to be used as part of presentation to user.

This is a requirement for the simplified interface called "conformance level 1" to work; when a model is created or uploaded, the multi-model name given in URL will imply a creation of the specified multi-model. 

### Services

Models:

* [Create Model Service](model_service_create.md) - create an empty model
* [Delete Model Service](model_service_delete.md) 
* [Download Model Service](model_service_download.md)
* [List Models Service](model_service_list.md)
* [Update Model Service](model_service_update.md)
* [Upload Model Service](model_service_upload.md) - create a new (non-empty) model or new model version




