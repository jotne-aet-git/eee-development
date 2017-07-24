rem @echo off
SET JSON2_TEST_HOME=%CD%
SET EE1SERVER_HOME=O:\proj_dev\eeE\eee-development\ee1Server

:check_worker
set WORKER=%1
if "%1" == "all" goto check_config
if "%1" == "start_server" goto check_config
if "%1" == "stop_server" goto check_config
if "%1" == "json2_tcp" goto check_config
goto usage

:check_config
set BCONFIG=%2
if "%2"=="" set BCONFIG=debug
if "%BCONFIG%"=="all" goto known_config
if "%BCONFIG%"=="release" goto known_config
if "%BCONFIG%"=="debug" goto known_config
goto usage

:usage
echo Usage: test_json2 worker [config]
echo     worker: all  - start server, run tests, stop server
echo             start_server - start server
echo             stop_server - stop server
echo             all json2_tcp  - java tests
echo     config: debug release(default) all
goto end


:known_config
if %BCONFIG%==all goto all_configs
call :one_config
goto end

:all_configs
set BCONFIG=debug
call :one_config
set BCONFIG=release
call :one_config
goto end


:one_config

set EDM_HOME_BIN=O:\edm\v6.0.xxx\output\x64\%BCONFIG%
set EDMSRV_HOME_BIN=O:\edm\v6.0.xxx\output\x64\%BCONFIG%
set EDM_JAVA_HOME=O:\edm\v6.0.xxx\output\x64\jar
set EDOM3_HOME=O:\edm_dev\EDMeXtensions\EDOM3

set EDMSRV_LIC_ID=10007
set EDMSRV_TRACE_HOME=%EDOM3_HOME%/test/output

rem run worker(s)
set REPORT_FILE=%JSON2_TEST_HOME%\json2_test.%BCONFIG%.out
echo *** json2_test %BCONFIG% >%REPORT_FILE%
call :%WORKER%
goto end

rem obsolete I think
if "%WORKER%" == "all" call :all
if "%WORKER%" == "start_server" call :start_server
if "%WORKER%" == "stop_server" call :stop_server
if "%WORKER%" == "json2_all" call :json2_all
goto end


:all
rem call tests
call :start_server
timeout /T 5
call :json2_all
call :stop_server
goto end


:json2_all
call :json2_tcp
goto end



:json2_tcp
pushd %EDM_HOME_BIN%
set BCLASSPATH=%EDM_JAVA_HOME%/edom3_java3.jar;
set BCLASSPATH=%BCLASSPATH%;%JSON2_TEST_HOME%/lib/servlet-api.jar
set BCLASSPATH=%BCLASSPATH%;%JSON2_TEST_HOME%/build
@echo on
java -classpath %BCLASSPATH%  edmws.json.test.Test01Runner >>%REPORT_FILE%
@echo off
popd
goto end




:start_server
set EDMSRV_PORT=4590
set EDMSRV_DB_PATH=%EE1SERVER_HOME%\db
set EDMSRV_DB_NAME=db1
set EDMSRV_DB_PWD=Db1#123
set EDMSRV_APPSERVERS=1
SET BACKUP_ABORT_BACKUP_THREAD=true
@echo start_server
call start_server.bat
goto end


:stop_server
@echo stop_server
%EDM_HOME_BIN%\edms stop_server.EDMscript
goto end


:end

