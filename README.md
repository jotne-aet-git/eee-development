# eee-development

Last update 2015.06.19

Repository contains files that mainly ends up on the eeEmbedded EDM BIM Model Server.  

The **ee1Server** is an image of the eeE EDM BIM Model Server installation, EDM subfolders:


- db (.gitignore): EDM database folder, 
- edm-bin (.gitignore): same as the "bin" folder of a normal EDM installation .gitignore
- trace (.gitignore): EDM trace output folder 

The rest of the folders are a normal TOMCAT "webroot" folder structure:

- bin : TOMCAT binaries for webroot
- conf : TOMCAT configuration
- lib : TOMCAT common libs (.jar's)
- logs (.gitignore): TOMCAT logs 
- temp (.gitignore) : TOMCAT temp files, also exchange directory between EDM and TOMCAT
- webapps : TOMCAT actual webapps
- work (.gitignore): TOMCAT work folder


  

