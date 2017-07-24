set E3_TARGET=O:\edm\trunk\EDMmodelServer-ifc\lm_server
set E3_WEBAPP_1=%E3_TARGET%\webapps\e3-bimapi-a
pushd ..\..\..\edom3\java
call ant jar
popd
pushd ..\..\edmws_EDOM3\java
call ant jar
popd
call ant deploy
pushd %E3_TARGET%
call lm_restart_debug_tomcat.bat
popd

