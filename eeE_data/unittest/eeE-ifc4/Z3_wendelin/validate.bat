@echo off
SET my_root=%CD%
set EDM_HOME=%my_root%\edm_home
md %EDM_HOME%\db

SET OLDPATH=%PATH%
set PATH=%PATH%;O:\edm\v6.0.xxx\output\x64\release
@echo on
edm_validate -schemafile ..\Install\Schemata\IFC4\IFC4.exp -stepfile %my_root%\%1.ifc
@echo off
SET PATH=%OLDPATH%