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
del *.* /s /q
xcopy /s /y %MD2_ROOT%\*.md .
md .\index_files
xcopy /s /y %MD2_ROOT%\index_files .\index_files
xcopy /s /y %MD2_ROOT%\index.* .
popd
rem convert the files
call %MD2_CMD% README
call %MD2_CMD% 10-basic-ifc-services\README
call %MD2_CMD% 10-basic-ifc-services\ifcobject_service
call %MD2_CMD% 10-basic-ifc-services\ifcproperty_service
call %MD2_CMD% 10-basic-ifc-services\ifcproperty_service_example
call %MD2_CMD% 10-basic-ifc-services\ifcrelation_service
call %MD2_CMD% 10-basic-ifc-services\ifcquantity_service

call %MD2_CMD% 10-basic-ifc-services\a_schemata\README
call %MD2_CMD% 10-basic-ifc-services\a_schemata\filter_data
call %MD2_CMD% 10-basic-ifc-services\a_schemata\filter_url
call %MD2_CMD% 10-basic-ifc-services\a_schemata\ifcobject_data
call %MD2_CMD% 10-basic-ifc-services\a_schemata\ifcproperty_data
call %MD2_CMD% 10-basic-ifc-services\a_schemata\ifcquantity_data
call %MD2_CMD% 10-basic-ifc-services\a_schemata\ifcrelation_data

call %MD2_CMD% 20-extended-ifc-services\README
call %MD2_CMD% 20-extended-ifc-services\convert_service
call %MD2_CMD% 20-extended-ifc-services\extract_service
call %MD2_CMD% 20-extended-ifc-services\merge_service

call %MD2_CMD% 20-extended-ifc-services\a_schemata\README
call %MD2_CMD% 20-extended-ifc-services\a_schemata\convert_history_data
call %MD2_CMD% 20-extended-ifc-services\a_schemata\extract_endpoint_data
call %MD2_CMD% 20-extended-ifc-services\a_schemata\merge_argument_data
call %MD2_CMD% 20-extended-ifc-services\a_schemata\merge_history_data

rem pause
goto end

:end
