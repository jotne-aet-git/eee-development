SET EDM_LM_HOME=%CD%

set B3CONFIG=%1
if "%B3CONFIG%"=="" set B3CONFIG=debug

rem set EDM_HOME_BIN=%EDM_LM_HOME%\install-image\bin
rem set EDM_HOME_BIN=C:\edm\EDMSix-2.100.04\bin
set EDM_HOME_BIN=%EDM_LM_HOME%\edm-bin
set EDMSRV_HOME_BIN=%EDM_HOME_BIN%
set EDMSRV_TRACE_HOME=%EDM_LM_HOME%\trace
set EDMSRV_DB_PATH=%EDM_LM_HOME%\db
set EDMSRV_DB_NAME=db1
set EDMSRV_DB_PWD=Db1$123
set EDMSRV_APPSERVERS=1
set EDMSRV_PORT=4590
set EDMSRV_LIC_ID=10007
set EDM_EXTENDED_NAME_CHARACTERS="0123456789!#$%%&'*+-/=?^`{|}~@._Ê¯Â∆ÿ≈"
set EDM_SERVER_CLIENTS_FILES_PATH=%EDM_LM_HOME%/temp

SET JAVA_HOME=C:\apps\Java\jdk1.8.0_45
SET CATALINA_HOME=C:\apps\apache-tomcat-8.0.22
SET CATALINA_BASE=%CD%
SET CATALINA_TMPDIR=%EDM_SERVER_CLIENTS_FILES_PATH%
rem set PATH=%PATH%;%EDM_HOME_BIN%

SET MSM_HOME_BIN=O:\edm_dev\EDMmodelServerManager\Install\EDM64

pushd ..\Install
SET MSM_DATA_ROOT=%CD%
popd
SET MSM_DATA_TEMP=%EDM_LM_HOME%\temp

set B3TEST_DATA_DIR=
if not exist ..\test\data goto skip5
pushd ..\test\data
set B3TEST_DATA_DIR=%CD%
popd
:skip5

rem make sure necessary directories exist

if not exist logs md logs
if not exist %MSM_DATA_TEMP% md %MSM_DATA_TEMP%
if not exist trace md trace
if not exist %EDMSRV_DB_PATH% md %EDMSRV_DB_PATH%
if not exist %EDM_SERVER_CLIENTS_FILES_PATH% md %EDM_SERVER_CLIENTS_FILES_PATH%