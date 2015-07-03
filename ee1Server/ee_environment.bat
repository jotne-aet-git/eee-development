SET EDM_E3_HOME=%CD%
rem set EDM_HOME_BIN=O:\edm\v6.0.xxx\output\x64\debug
set EDM_HOME_BIN=%EDM_E3_HOME%\edm-bin
set EDMSRV_HOME_BIN=%EDM_HOME_BIN%
set EDMSRV_TRACE_HOME=%EDM_E3_HOME%\trace
set EDMSRV_DB_PATH=%EDM_E3_HOME%\db
set EDMSRV_DB_NAME=db1
set EDMSRV_DB_PWD=db1
set EDMSRV_APPSERVERS=1
set EDMSRV_PORT=4590
set EDMSRV_LIC_ID=10007
set EDM_EXTENDED_NAME_CHARACTERS="0123456789!#$%%&'*+-/=?^`{|}~@._æøåÆØÅ"
set EDM_SERVER_CLIENTS_FILES_PATH=%EDM_E3_HOME%/temp

SET JAVA_HOME=C:\apps\Java\jdk1.8.0_45
SET CATALINA_HOME=C:\apps\apache-tomcat-8.0.22
SET CATALINA_BASE=%CD%
set PATH=%PATH%;%EDM_HOME_BIN%

rem make sure necessary directories exist

md logs
md temp
md trace


