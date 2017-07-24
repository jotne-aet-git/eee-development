set E3_BIMAPI_TEST_HOME=%CD%
pushd test
set e3test.devRoot=../../../../
call ant clean >%E3_BIMAPI_TEST_HOME%\e3_bimapi_test.out
call ant run >>%E3_BIMAPI_TEST_HOME%\e3_bimapi_test.out
set e3test.devRoot=
popd