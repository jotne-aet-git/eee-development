rem @echo off

if "%1"=="" goto all
@echo on
pandoc %1.md -f markdown -t html -s -o %MD2_OUT%\%1.md
@echo off
goto end

:all
rem span up a directory tree for output
set MD2_ROOT="%CD%"
set MD2_CMD=%MD2_ROOT%\md2html.bat
md ..\output\doc
pushd ..\output\doc
set MD2_OUT="%CD%"
xcopy /s /y %MD2_ROOT%\*.md .
del *.* /s /q
popd
rem convert the files
call %MD2_CMD% README
call %MD2_CMD% repository-services-01\README

call %MD2_CMD% repository-services-01\a_schemata\README
call %MD2_CMD% repository-services-01\a_schemata\attachment_meta_data
call %MD2_CMD% repository-services-01\a_schemata\domain_meta_data
call %MD2_CMD% repository-services-01\a_schemata\model_meta_data
call %MD2_CMD% repository-services-01\a_schemata\model_upload_schema
call %MD2_CMD% repository-services-01\a_schemata\multimodel_meta_data
call %MD2_CMD% repository-services-01\a_schemata\project_meta_data

call %MD2_CMD% repository-services-01\domain_service

call %MD2_CMD% repository-services-01\model_service
call %MD2_CMD% repository-services-01\model_service_create
call %MD2_CMD% repository-services-01\model_service_delete
call %MD2_CMD% repository-services-01\model_service_list
call %MD2_CMD% repository-services-01\model_service_update
call %MD2_CMD% repository-services-01\model_service_upload

call %MD2_CMD% repository-services-01\multimodel_service
call %MD2_CMD% repository-services-01\project_service


call %MD2_CMD% repository-services-01\delete_attachment_service
call %MD2_CMD% repository-services-01\download_attachment_service
call %MD2_CMD% repository-services-01\list_attachment_service
call %MD2_CMD% repository-services-01\upload_attachment_service

rem pause
goto end

:end
