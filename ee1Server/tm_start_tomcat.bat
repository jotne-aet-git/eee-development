rem @echo off
call ee_environment.bat
goto debug

:normal
call %CATALINA_HOME%\bin\catalina.bat start
goto end

:debug
set JAVA_OPTS=-Xmx250M -Xms250M
:: next two lines are for debugging tomcat
set JPDA_TRANSPORT=dt_socket
set JPDA_ADDRESS=8888
set JPDA_OPTS=-Xdebug -Xrunjdwp:transport=dt_socket,address=8000,server=y,suspend=n
call %CATALINA_HOME%\bin\catalina.bat jpda start

:end
rem pause

