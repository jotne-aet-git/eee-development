set E3_BIMAPI_BUILD_HOME=%CD%
set E3_BIMAPI_BUILD_REPORT=e3_bimapi_build.out
pushd ..\..\edom3\java
call ant deploy >%E3_BIMAPI_BUILD_HOME%\%E3_BIMAPI_BUILD_REPORT%
popd
pushd a-webapp
call ant clean >>%E3_BIMAPI_BUILD_HOME%\%E3_BIMAPI_BUILD_REPORT%
call ant deploy >>%E3_BIMAPI_BUILD_HOME%\%E3_BIMAPI_BUILD_REPORT%
popd

