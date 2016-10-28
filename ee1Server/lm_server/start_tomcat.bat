goto %1

:normal
set PATH=%PATH%;%EDM_HOME_BIN%
call %CATALINA_HOME%\bin\catalina.bat start 	
timeout /T 5 										
goto end


:debug
set PATH=%PATH%;%EDM_HOME_BIN%
set JAVA_OPTS=-Xmx250M -Xms250M				
set JPDA_TRANSPORT=dt_socket					
set JPDA_ADDRESS=8888							
set JPDA_OPTS=-Xdebug -Xrunjdwp:transport=dt_socket,address=8000,server=y,suspend=n  
call %CATALINA_HOME%\bin\catalina.bat jpda start 	
timeout /T 5 										
goto end

:end
