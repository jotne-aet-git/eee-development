set B4_BUILD_WEBAPP_HOME=O:\edm_dev\EDMeXtensions\eeE\E3_BIM_api\a-webapp
set B4_LIB_TARGET_DIR=%cd%\webapps\e3-bimapi-a\WEB-INF\lib

pushd O:\edm_dev\EDMeXtensions\edom3\java
call ant jar
popd
copy O:\edm\v6.0.xxx\output\x64\jar\*.jar %B4_LIB_TARGET_DIR%

pushd %B4_BUILD_WEBAPP_HOME%
call ant jar
popd


copy %B4_BUILD_WEBAPP_HOME%\jar\*.jar %B4_LIB_TARGET_DIR%
copy %B4_BUILD_WEBAPP_HOME%\lib\*.jar %B4_LIB_TARGET_DIR%

call lm_restart_debug_tomcat.bat


