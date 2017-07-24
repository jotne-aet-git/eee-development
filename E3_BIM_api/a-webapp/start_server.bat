rem echo off
rem #######################################################################
rem                    DO NOT EDIT THIS SCRIPT.
rem
rem Title: RunEDMserver.cmd
rem
rem Requires the following parameters set in the inherited environment.
rem This script is called from the EDMserverStartup scripts. All the
rem parameters used herein are defined in the EDMserverStartup script.
rem
rem - EDMSRV_HOME_BIN: EXPRESS Data Manager binaries folder
rem - EDMSRV_TRACE_HOME  : Trace file target folder.
rem - EDMSRV_PORT    : EDMserver Service Port.
rem - EDMSRV_LIC_ID  : EDMserver License Id (Check license certificate).
rem - EDMSRV_DB_PATH     : EDMdatabase folder.
rem - EDMSRV_DB_NAME     : EDMdatabase name.
rem - EDMSRV_DB_PWD      : EDMdatabase password.
rem 
rem Supports the following additional parameters 
rem 
rem Backup:
rem
rem - BACKUP_ABORT_BACKUP_THREAD
rem - BACKUP_START
rem - BACKUP_INTERVAL
rem - BACKUP_LOCATION
rem - BACKUP_NAME
rem - BACKUP_VERSIONS
rem - CONTINUE_WITH_NEXT_VERSION
rem - DELETING_EXISTING_BACKUP
rem - COMPRESS_BACKUP
rem
rem EDMserver Tracing
rem 
rem - TRACE_SERVER_HEADERS
rem - TRACE_SERVER_BODY
rem - TRACE_SERVER_BRIEF
rem - TRACE_SERVER_FILE
rem - TRACE_SERVER_TO_STDOUT
rem - 
rem EDMapplicationServer Tracing
rem 
rem - TRACE_APPSERVER_HEADERS
rem - TRACE_APPSERVER_BODY
rem - TRACE_APPSERVER_BRIEF
rem - TRACE_APPSERVER_FILE
rem - TRACE_APPSERVER_TO_STDOUT
rem - 
rem EDMdatabase configuration
rem - 
rem - DB_BLOCK_SIZE
rem
rem EDMserver Tuning
rem - EDM_DB_MEMORY_BLOCKS
rem - EDMSRV_APPSERVERS
rem
rem #######################################################################

if not defined EDMSRV_HOME_BIN goto err_no_edm_home
if not exist %EDMSRV_HOME_BIN% goto err_no_edm_home
if not defined EDMSRV_TRACE_HOME goto err_no_trace_home
if not exist %EDMSRV_TRACE_HOME% goto err_no_trace_home
if not defined EDMSRV_PORT goto err_no_srv_port
if not defined EDMSRV_LIC_ID goto err_no_edm_lic_id
if not defined EDMSRV_DB_PATH goto err_no_db_path
if not exist %EDMSRV_DB_PATH% goto err_no_db_path
if not defined EDMSRV_DB_NAME goto err_no_db_name
if not defined EDMSRV_DB_PWD goto err_no_db_pwd

set server_command=%EDMSRV_HOME_BIN%/edmserver.exe
set server_command=%server_command% -s%EDMSRV_PORT%
set server_command=%server_command% -ul%EDMSRV_LIC_ID%
set server_command=%server_command% -l%EDMSRV_DB_PATH%
set server_command=%server_command% -n%EDMSRV_DB_NAME%
set server_command=%server_command% -p%EDMSRV_DB_PWD%
set server_command=%server_command% -o
if exist %EDMSRV_DB_PATH%/%EDMSRV_DB_NAME%.EDM goto db_exist
set server_command=%server_command% -c
:db_exist

rem ---------------------------------------
rem EDMdatabase Scheduled Backup Parameters
rem ---------------------------------------
if defined BACKUP_ABORT_BACKUP_THREAD set server_command=%server_command% -ab
if defined BACKUP_ABORT_BACKUP_THREAD goto end_backup
if defined BACKUP_START set server_command=%server_command% -bs%BACKUP_START%
if defined BACKUP_INTERVAL set server_command=%server_command% -bi%BACKUP_INTERVAL%
if defined BACKUP_LOCATION set server_command=%server_command% -bl%BACKUP_LOCATION%
if defined BACKUP_NAME set server_command=%server_command% -bn%BACKUP_NAME%
if defined BACKUP_VERSIONS set server_command=%server_command% -bv%BACKUP_VERSIONS%
if defined CONTINUE_WITH_NEXT_VERSION set server_command=%server_command% -boBACKUP_CONTINUE_WITH_NEXT_VERSION
if defined DELETING_EXISTING_BACKUP set server_command=%server_command% -boDELETING_EXISTING_BACKUP
if defined COMPRESS_BACKUP set server_command=%server_command% -boCOMPRESS_BACKUP
:end_backup

rem ----------------------------
rem EDMserver Tracing Parameters
rem ----------------------------
if defined TRACE_SERVER_HEADERS set server_command=%server_command% -t
if defined TRACE_SERVER_BODY set server_command=%server_command% -w
if defined TRACE_SERVER_BRIEF set server_command=%server_command% -tb
if defined TRACE_SERVER_FILE set server_command=%server_command% -tf%TRACE_SERVER_FILE%_%EDMSRV_PORT%.log
if defined TRACE_SERVER_TO_STDOUT set server_command=%server_command% -tv
:end_trace

rem ------------------------------------
rem EDMdatabase Configuration Parameters
rem ------------------------------------
if defined DB_BLOCK_SIZE set server_command=%server_command% -eb%DB_BLOCK_SIZE%
if defined EDM_DB_MEMORY_BLOCKS set server_command=%server_command% -ec%EDM_DB_MEMORY_BLOCKS%
:end_config

rem ------------------------------------------
echo Starting EDMserver : %server_command%
set WINTITLE= EDMserver (%EDMSRV_PORT%) - %1%

pushd %EDMSRV_HOME_BIN%
rem start "%WINTITLE%" /MIN %server_command%
start "%WINTITLE%" /MIN %server_command% -t -w
popd

rem -----------------------------------
rem Start the EDMapplicationServers
rem
if not defined EDMSRV_APPSERVERS set EDMSRV_APPSERVERS=1
set APPSERVER_NO=0

:start_appserver
if "%APPSERVER_NO%" GEQ "%EDMSRV_APPSERVERS%" goto end_appserver
set /A APPSERVER_NO=APPSERVER_NO+1
set /A APPSRV_PORT=EDMSRV_PORT+APPSERVER_NO
set appserver_command=%EDMSRV_HOME_BIN%\edmappserver.exe
set appserver_command=%appserver_command% -s%EDMSRV_PORT%
if defined TRACE_APPSERVER_HEADERS set appserver_command=%appserver_command% -t
if defined TRACE_APPSERVER_BODY set appserver_command=%appserver_command% -w
if defined TRACE_APPSERVER_BRIEF set appserver_command=%appserver_command% -tb
if defined TRACE_APPSERVER_FILE set appserver_command=%appserver_command% -tf%TRACE_APPSERVER_FILE%_%APPSRV_PORT%.log

echo Starting EDMapplicationServer : %appserver_command%
rem set WINTITLE=EDMappServer (%APPSRV_PORT%) - %0%
pushd %EDMSRV_HOME_BIN%
rem start "%WINTITLE%" /MIN %appserver_command%
rem start "%WINTITLE%" /MIN %appserver_command% -t -w
start "appserver" /MIN %appserver_command% -t -w
popd

goto start_appserver

:end_appserver

goto end

rem -----------------
rem Error Conditions!
rem -----------------
:err_no_edm_home
echo "Script error: EDM_HOME unset or specified folder does not exist"
goto end

:err_no_trace_home
echo "Script error: EDMSRV_TRACE_HOME unset or specified folder does not exist"
goto end

:err_no_srv_port
echo "Script error: EDMSRV_PORT unset"
goto end

:err_no_edm_lic_id
echo "Script error: EDMSRV_LIC_ID unset"
goto end

:err_no_db_path
echo "Script error: EDMSRV_DB_PATH unset or specified folder does not exist"
goto end

:err_no_db_name
echo "Script error: EDMSRV_DB_NAME unset"
goto end

:err_no_db_pwd
echo "Script error: EDMSRV_DB_PWD unset"
goto end

pause
:end
