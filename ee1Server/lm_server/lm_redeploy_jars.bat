call lm_environment.bat

set B4_EDM_JAR_HOME=%EDM_DEV_ROOT%\output\x64\jar
set B4_JAVA_LIB_HOME=%EDM_DEV_ROOT%\EDMeXtensions\lib
set B4_LIB_TARGET_DIR=%cd%\webapps\e3-bimapi-a\WEB-INF\lib

pushd ..\EDOM3\java
call ant jar
popd
pushd ..\EDMwebServices\wsmws_EDOM3\java
call ant jar
popd
pushd ..\EDMwebServices\E3_BIM_api\a-webapp
call ant jar
popd


copy %B4_EDM_JAR_HOME%\*.jar %B4_LIB_TARGET_DIR%
copy %B4_JAVA_LIB_HOME%\hamcrest-core*.jar %B4_LIB_TARGET_DIR%
copy %B4_JAVA_LIB_HOME%\logback*.jar %B4_LIB_TARGET_DIR%
copy %B4_JAVA_LIB_HOME%\slf4j-api*.jar %B4_LIB_TARGET_DIR%

call lm_restart_debug_tomcat.bat


