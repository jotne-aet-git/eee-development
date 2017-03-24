@echo off
call lm_environment.bat %2
set THIS_NAME=lm_worker
set REPORT_FILE_04=%cd%\%THIS_NAME%.out
set WORK_DIR=%cd%\work
set EDMSCRIPT_DIR=%cd%\EDMscript
if not exist %WORK_DIR% md %WORK_DIR%

echo --- %THIS_NAME% %1 %2 %3 >%REPORT_FILE_04%

set B3WORKER=
if "%1"=="delete_database" set B3WORKER=%1
if "%1"=="dump_database" set B3WORKER=%1
if "%1"=="restore_database" set B3WORKER=%1
if "%1"=="compile_express_schemata" set B3WORKER=%1
if "%1"=="compile_query_schemata" set B3WORKER=%1
if "%1"=="compile_test_query_schemata" set B3WORKER=%1
if "%1"=="create_models" set B3WORKER=%1
if "%1"=="create_test_population" set B3WORKER=%1
if "%1"=="import_enterprise_objects" set B3WORKER=%1
if "%1"=="init_work_order" set B3WORKER=%1
if "%1"=="start_msm" set B3WORKER=%1
if "%1"=="start_edms" set B3WORKER=%1

if "%1"=="start_server" set B3WORKER=%1
if "%1"=="stop_server" set B3WORKER=%1

if "%1"=="start_tomcat" set B3WORKER=%1
if "%1"=="start_tomcat_normal" set B3WORKER=%1
if "%1"=="start_tomcat_debug" set B3WORKER=%1
if "%1"=="stop_tomcat" set B3WORKER=%1

rem 'macros'

if "%1"=="init_work_db" set B3WORKER=%1


if "%B3WORKER%"=="" goto usage
echo --- %THIS_NAME% worker: %B3WORKER% 
echo --- %THIS_NAME% worker: %B3WORKER% >>%REPORT_FILE_04%
goto %B3WORKER%
goto usage

:usage
echo *USAGE FAIL* >>%REPORT_FILE_04%

@echo USAGE: %THIS_NAME% worker [config]
@echo     config: debug (default) release
@echo     worker: dump_database  - as name says, server must be running
@echo     worker: restore_database - as name says, server must be stopped
@echo     worker: (1) delete_database  - as name says, server must be stopped
@echo     worker: (2) compile_express_schemata
@echo     worker: (3) compile_query_schemata
@echo     worker: (3.1) compile_test_query_schemata
@echo     worker: (4) create_models
@echo     worker: (5) import_enterprise_objects
@echo     worker: (5.1) create_test_population - init a project and maybe more preparing for test
@echo     worker: (6) init_work_order
@echo     worker: init_work_db - run 1-3 above - restart server (to get correct SSP loading) and run 4-6, stop server
@echo     worker: start_server stop_server - start/stop EDM server
@echo     worker: start_tomcat start_tomcat_debug start_tomcat_normal stop_toicat - start/stop TOMCAT
@echo     worker: start_edms - start an EDM supervisor
@echo     worker: start_msm - start an EDMmodelServerManager
goto end

:init_work_db
call :stop_server
call :delete_database
call :start_server
call :compile_express_schemata
call :compile_query_schemata
call :compile_test_query_schemata
call :stop_server
call :start_server
call :create_models
call :import_enterprise_objects
call :init_work_order
call :create_test_population
call :stop_server
goto end

:delete_database
del /Q %EDMSRV_DB_PATH%\*.*
goto end


:dump_database
SET EDM_SCRIPT_02=%WORK_DIR%\dump_database.EDMscript
type %EDMSCRIPT_DIR%\define_context.EDMscript > %EDM_SCRIPT_02%
type %EDMSCRIPT_DIR%\dump_database.EDMscript >> %EDM_SCRIPT_02%
type %EDMSCRIPT_DIR%\exit.EDMscript >> %EDM_SCRIPT_02%
SET EDM_SCRIPT_01=%EDM_SCRIPT_02%
call :do_edm_script
goto end


:restore_database
SET EDM_SCRIPT_02=%WORK_DIR%\restore_database.EDMscript
type %EDMSCRIPT_DIR%\restore_database.EDMscript > %EDM_SCRIPT_02%
type %EDMSCRIPT_DIR%\exit.EDMscript >> %EDM_SCRIPT_02%
rem %EDM_HOME_BIN%\edms %EDM_SCRIPT_02%
SET EDM_SCRIPT_01=%EDM_SCRIPT_02%
call :do_edm_script
goto end



:start_server
call start_server.bat
timeout /T 5
goto end

:start_server_special
call start_server.bat
timeout /T 5
pause --- adjust server instances ????
goto end

:stop_server
SET EDM_SCRIPT_02=%WORK_DIR%\stop_server.EDMscript
type %EDMSCRIPT_DIR%\define_context.EDMscript > %EDM_SCRIPT_02%
type %EDMSCRIPT_DIR%\server_terminate.EDMscript >> %EDM_SCRIPT_02%
type %EDMSCRIPT_DIR%\exit.EDMscript >> %EDM_SCRIPT_02%
SET EDM_SCRIPT_01=%EDM_SCRIPT_02%
call :do_edm_script
timeout /T 5
goto end

:compile_express_schemata
SET EDM_SCRIPT_02=%WORK_DIR%\compile_express_schemata.EDMscript
echo Aux$gt$History$gt$Start($MSM_DATA_TEMP$\compile_express_schemata.EDMscript.log, START_HISTORY_TO_FILE) > %EDM_SCRIPT_02%
type %EDMSCRIPT_DIR%\define_context.EDMscript >> %EDM_SCRIPT_02%
type %EDMSCRIPT_DIR%\prepare_compile.EDMscript >> %EDM_SCRIPT_02%
for %%n in (%MSM_DATA_ROOT%\Schemata\EDM_RAW_XML\*.exp) DO echo RemoteSystems$gt$Expressschema$gt$Define(super, %%n, %%n.dia, $, "STORING_SOURCE,DELETING_EXISTING_SCHEMAS,TC2,EDM_EXPRESS_EXTENSION") >>%EDM_SCRIPT_02%
for %%n in (%MSM_DATA_ROOT%\Schemata\EnterpriseSchema\*.exp) DO echo RemoteSystems$gt$Expressschema$gt$Define(super, %%n, %%n.dia, $, "STORING_SOURCE,DELETING_EXISTING_SCHEMAS,TC2,EDM_EXPRESS_EXTENSION") >>%EDM_SCRIPT_02%
for %%n in (%MSM_DATA_ROOT%\Schemata\Ifc2x3\*.exp) DO echo RemoteSystems$gt$Expressschema$gt$Define(super, %%n, %%n.dia, $, "STORING_SOURCE,DELETING_EXISTING_SCHEMAS,TC2,EDM_EXPRESS_EXTENSION") >>%EDM_SCRIPT_02%
for %%n in (%MSM_DATA_ROOT%\Schemata\Ifc4\*.exp) DO echo RemoteSystems$gt$Expressschema$gt$Define(super, %%n, %%n.dia, $, "STORING_SOURCE,DELETING_EXISTING_SCHEMAS,TC2,EDM_EXPRESS_EXTENSION") >>%EDM_SCRIPT_02%
for %%n in (%MSM_DATA_ROOT%\Schemata\Work_Orders\*.exp) DO echo RemoteSystems$gt$Expressschema$gt$Define(super, %%n, %%n.dia, $, "STORING_SOURCE,DELETING_EXISTING_SCHEMAS,TC2,EDM_EXPRESS_EXTENSION") >>%EDM_SCRIPT_02%
rem type %EDM_SCRIPT_02% |jrepl.bat \$gt\$ ">" >%EDM_SCRIPT_02%
type %EDMSCRIPT_DIR%\exit.EDMscript >> %EDM_SCRIPT_02%
SET EDM_SCRIPT_01=%EDM_SCRIPT_02%
call :do_edm_script
goto end


:compile_query_schemata
SET EDM_SCRIPT_02=%WORK_DIR%\compile_query_schemata.EDMscript
echo Aux$gt$History$gt$Start($MSM_DATA_TEMP$\compile_query_schemata.EDMscript.log, START_HISTORY_TO_FILE) > %EDM_SCRIPT_02%
type %EDMSCRIPT_DIR%\define_context.EDMscript >> %EDM_SCRIPT_02%
type %EDMSCRIPT_DIR%\prepare_compile.EDMscript >> %EDM_SCRIPT_02%
for %%n in (..\Install\Schemata\EDM_RAW_XML\*.qex) DO echo RemoteSystems$gt$Queryschema$gt$Define(super,%%n , %%n.dia, DELETING_EXISTING_SCHEMAS) >>%EDM_SCRIPT_02%
for %%n in (..\Install\Schemata\EnterpriseSchema\*.qex) DO echo RemoteSystems$gt$Queryschema$gt$Define(super,%%n , %%n.dia, DELETING_EXISTING_SCHEMAS) >>%EDM_SCRIPT_02%
for %%n in (..\Install\Schemata\Ifc2x3\*.qex) DO echo RemoteSystems$gt$Queryschema$gt$Define(super,%%n , %%n.dia, DELETING_EXISTING_SCHEMAS) >>%EDM_SCRIPT_02%
for %%n in (..\Install\Schemata\Ifc4\*.qex) DO echo RemoteSystems$gt$Queryschema$gt$Define(super,%%n , %%n.dia, DELETING_EXISTING_SCHEMAS) >>%EDM_SCRIPT_02%
for %%n in (..\Install\Schemata\PLUGINQUERYSCHEMA_DUMMYSCHEMA\*.qex) DO echo RemoteSystems$gt$Queryschema$gt$Define(super,%%n , %%n.dia, DELETING_EXISTING_SCHEMAS) >>%EDM_SCRIPT_02%
for %%n in (..\Install\Schemata\Work_Orders\*.qex) DO echo RemoteSystems$gt$Queryschema$gt$Define(super,%%n , %%n.dia, DELETING_EXISTING_SCHEMAS) >>%EDM_SCRIPT_02%
type %EDMSCRIPT_DIR%\exit.EDMscript >> %EDM_SCRIPT_02%
SET EDM_SCRIPT_01=%EDM_SCRIPT_02%
call :do_edm_script
goto end

:compile_test_query_schemata
if not exist %B3TEST_DATA_DIR%\Schemata goto end
SET EDM_SCRIPT_02=%WORK_DIR%\compile_test_query_schemata.EDMscript
echo Aux$gt$History$gt$Start($MSM_DATA_TEMP$\compile_test_query_schemata.EDMscript.log, START_HISTORY_TO_FILE) > %EDM_SCRIPT_02%
type %EDMSCRIPT_DIR%\define_context.EDMscript >> %EDM_SCRIPT_02%
type %EDMSCRIPT_DIR%\prepare_compile.EDMscript >> %EDM_SCRIPT_02%
for %%n in (%B3TEST_DATA_DIR%\Schemata\EnterpriseSchema\*.qex) DO echo RemoteSystems$gt$Queryschema$gt$Define(super,%%n , %%n.dia, DELETING_EXISTING_SCHEMAS) >>%EDM_SCRIPT_02%
for %%n in (%B3TEST_DATA_DIR%\Schemata\Ifc2x3\*.qex) DO echo RemoteSystems$gt$Queryschema$gt$Define(super,%%n , %%n.dia, DELETING_EXISTING_SCHEMAS) >>%EDM_SCRIPT_02%
for %%n in (%B3TEST_DATA_DIR%\Schemata\Ifc4\*.qex) DO echo RemoteSystems$gt$Queryschema$gt$Define(super,%%n , %%n.dia, DELETING_EXISTING_SCHEMAS) >>%EDM_SCRIPT_02%
for %%n in (%B3TEST_DATA_DIR%\Schemata\PLUGINQUERYSCHEMA_DUMMYSCHEMA\*.qex) DO echo RemoteSystems$gt$Queryschema$gt$Define(super,%%n , %%n.dia, DELETING_EXISTING_SCHEMAS) >>%EDM_SCRIPT_02%
for %%n in (%B3TEST_DATA_DIR%\Schemata\Work_Orders\*.qex) DO echo RemoteSystems$gt$Queryschema$gt$Define(super,%%n , %%n.dia, DELETING_EXISTING_SCHEMAS) >>%EDM_SCRIPT_02%
type %EDMSCRIPT_DIR%\exit.EDMscript >> %EDM_SCRIPT_02%
SET EDM_SCRIPT_01=%EDM_SCRIPT_02%
call :do_edm_script
goto end


:create_models
SET EDM_SCRIPT_02=%WORK_DIR%\create_models.EDMscript
type %EDMSCRIPT_DIR%\define_context.EDMscript > %EDM_SCRIPT_02%
type %EDMSCRIPT_DIR%\create_models.EDMscript >> %EDM_SCRIPT_02%
type %EDMSCRIPT_DIR%\exit.EDMscript >> %EDM_SCRIPT_02%
SET EDM_SCRIPT_01=%EDM_SCRIPT_02%
call :do_edm_script
goto end

:create_test_population
SET EDM_SCRIPT_02=%WORK_DIR%\create_test_population.EDMscript
type %EDMSCRIPT_DIR%\define_context.EDMscript > %EDM_SCRIPT_02%
type %EDMSCRIPT_DIR%\create_test_population.EDMscript >> %EDM_SCRIPT_02%
type %EDMSCRIPT_DIR%\exit.EDMscript >> %EDM_SCRIPT_02%
SET EDM_SCRIPT_01=%EDM_SCRIPT_02%
call :do_edm_script
goto end


:import_enterprise_objects
SET EDM_SCRIPT_02=%WORK_DIR%\import_enterprise_objects.EDMscript
echo Aux$gt$History$gt$Start($MSM_DATA_TEMP$\import_enterprise_objects.EDMscript.log, START_HISTORY_TO_FILE) > %EDM_SCRIPT_02%
type %EDMSCRIPT_DIR%\define_context.EDMscript >> %EDM_SCRIPT_02%
for %%n in (..\Install\Schemata\BIMManual\*.xml) DO echo RemoteSystems$gt$Models$gt$ImportXMLfile(super, ModelServer, EnterpriseModel, $, $, %%n, %%n.dia, $, "ADD_TO_EXISTING_MODEL,CONTINUE_STORING_ON_ERROR") >>%EDM_SCRIPT_02%
for %%n in (..\Install\Schemata\Models\*.xml) DO echo RemoteSystems$gt$Models$gt$ImportXMLfile(super, ModelServer, EnterpriseModel, $, $, %%n, %%n.dia, $, ADD_TO_EXISTING_MODEL) >>%EDM_SCRIPT_02%
type %EDMSCRIPT_DIR%\exit.EDMscript >> %EDM_SCRIPT_02%
SET EDM_SCRIPT_01=%EDM_SCRIPT_02%
call :do_edm_script
goto end


:init_work_order
SET EDM_SCRIPT_02=%WORK_DIR%\init_work_order.EDMscript
type %EDMSCRIPT_DIR%\define_context.EDMscript > %EDM_SCRIPT_02%
type %EDMSCRIPT_DIR%\init_work_order.EDMscript >> %EDM_SCRIPT_02%
type %EDMSCRIPT_DIR%\exit.EDMscript >> %EDM_SCRIPT_02%
SET EDM_SCRIPT_01=%EDM_SCRIPT_02%
call :do_edm_script
goto end


:start_edms
SET EDM_SCRIPT_02=%WORK_DIR%\start_edms.EDMscript
type %EDMSCRIPT_DIR%\define_context.EDMscript > %EDM_SCRIPT_02%
type %EDMSCRIPT_DIR%\list_models.EDMscript >> %EDM_SCRIPT_02%
SET EDM_SCRIPT_01=%EDM_SCRIPT_02%
call :make_edm_script
start %EDM_HOME_BIN%\edms %EDM_SCRIPT_01%
goto end

:start_msm
start %MSM_HOME_BIN%\ModelServerManagerForms3.0.exe
goto end

:start_tomcat
call :start_tomcat_debug
goto end

:start_tomcat_normal
set THE_COMMANDLINE_04=start_tomcat normal
call :do_commandline_04
goto end

:start_tomcat_debug
set THE_COMMANDLINE_04=start_tomcat debug
call :do_commandline_04
goto end


:start_tomcat_debug_X
set B3TEMP2=%WORK_DIR%\start_tomcat_debug.bat
echo set PATH=%PATH%;%EDM_HOME_BIN% 				>%B3TEMP2%
echo set JAVA_OPTS=-Xmx250M -Xms250M				>>%B3TEMP2%
echo set JPDA_TRANSPORT=dt_socket					>>%B3TEMP2%
echo set JPDA_ADDRESS=8888							>>%B3TEMP2%
echo set JPDA_OPTS=-Xdebug -Xrunjdwp:transport=dt_socket,address=8000,server=y,suspend=n  >>%B3TEMP2%
echo call %CATALINA_HOME%\bin\catalina.bat jpda start 	>>%B3TEMP2%
echo timeout /T 5 										>>%B3TEMP2%
echo exit 												>>%B3TEMP2%
type %B3TEMP2% >>%REPORT_FILE_04%
cmd /k  %B3TEMP2%
goto end

:stop_tomcat
rem call %CATALINA_HOME%\bin\catalina.bat stop
set THE_COMMANDLINE_04=%CATALINA_HOME%\bin\catalina.bat stop
call :do_commandline_04
timeout /T 5
goto end

rem === internal tasks ===

:make_edm_script
echo --- %THIS_NAME% make_edm_script %EDM_SCRIPT_01%
type %EDM_SCRIPT_01% |jrepl.bat \$EDMSRV_DB_PWD\$ %EDMSRV_DB_PWD%  >%WORK_DIR%\workx.EDMscript
type %WORK_DIR%\workx.EDMscript |jrepl.bat \$EDMSRV_PORT\$ %EDMSRV_PORT%  >%WORK_DIR%\worky.EDMscript
type %WORK_DIR%\worky.EDMscript |jrepl.bat \$MSM_DATA_ROOT\$ %MSM_DATA_ROOT%  >%WORK_DIR%\workx.EDMscript
type %WORK_DIR%\workx.EDMscript |jrepl.bat \$MSM_DATA_TEMP\$ %MSM_DATA_TEMP%  >%WORK_DIR%\worky.EDMscript
type %WORK_DIR%\worky.EDMscript |jrepl.bat \$gt\$ ">" >%WORK_DIR%\_last.EDMscript
set EDM_SCRIPT_01=%WORK_DIR%\_last.EDMscript
goto end


:do_edm_script
call :make_edm_script
echo %EDM_HOME_BIN%\edms %EDM_SCRIPT_01% >>%REPORT_FILE_04%
type %EDM_SCRIPT_01% >> %REPORT_FILE_04%
echo --- %THIS_NAME% %EDM_HOME_BIN%\edms %EDM_SCRIPT_01%
%EDM_HOME_BIN%\edms %EDM_SCRIPT_01%
goto end

:do_commandline_04
echo *** %THIS_NAME% %THE_COMMANDLINE_04% >>%REPORT_FILE_04%
echo *** %THIS_NAME% %THE_COMMANDLINE_04%
cmd/c %THE_COMMANDLINE_04%
goto end


:end