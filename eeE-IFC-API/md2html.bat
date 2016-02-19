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
call %MD2_CMD% ifcobject-services-01\README
call %MD2_CMD% ifcobject-services-01\ifcobject_service
call %MD2_CMD% ifcobject-services-01\ifcproperty_service
call %MD2_CMD% ifcobject-services-01\ifcrelation_service

call %MD2_CMD% ifcobject-services-01\a_schemata\README
call %MD2_CMD% ifcobject-services-01\a_schemata\filter_data
call %MD2_CMD% ifcobject-services-01\a_schemata\ifcobject_data
call %MD2_CMD% ifcobject-services-01\a_schemata\ifcproperty_data
call %MD2_CMD% ifcobject-services-01\a_schemata\ifcrelation_data

call %MD2_CMD% multimodel-services-01\README
call %MD2_CMD% multimodel-services-01\merge_service

call %MD2_CMD% multimodel-services-01\a_schemata\README
call %MD2_CMD% multimodel-services-01\a_schemata\merge_meta_data

rem pause
goto end

:end
