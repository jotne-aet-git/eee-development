rem @echo off
call lm_worker.bat stop_tomcat
call lm_worker.bat start_tomcat_debug

:end
rem pause

