#include <sdai.h>

#include <malloc.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "edm_validate.h"

/*
EdmiError Worker::xtDefineSchema(char* expressFile, char* diagnosticFile, char* schemaName, SdaiInteger options, SdaiInteger* nWarnings, SdaiInteger *nErrors) {
	return edmiDefineSchema(expressFile, diagnosticFile, schemaName, options, nWarnings, nErrors);
}
*/

EdmiError Worker::checkDatabase(Commandline& cline){
	if (cline.dbpath[0] == 0) return 0;
	EdmiError rstat = edmiCreateDatabase(cline.dbpath, cline.dbname, cline.dbpass);
	if (rstat == edmiEDBEXIST)rstat = edmiOpenDatabase(cline.dbpath, cline.dbname, cline.dbpass);
	sdaiOpenSession();
	sdaiOpenRepositoryBN("DataRepository");
	return rstat;
}

EdmiError Worker::checkSchema(Commandline& cline){
	if (cline.schemafile[0] == 0) return 0;
	this->deleteModel(cline);
	SdaiInteger options = DELETING_EXISTING_SCHEMA | TC2 | STORING_SOURCE;
	SdaiInteger nWarnings, nErrors;
	EdmiError rstat = edmiDefineSchema(cline.schemafile , NULL/*diagnosticFile*/,
					NULL/*schemaName*/, options, &nWarnings, &nErrors);
	return rstat;
}
EdmiError Worker::checkStepfile(Commandline& cline){
	if (cline.stepfile[0] == 0) return 0;
	printf("==============================================\n");
	printf("--- Importing step (IFC) model from   file: %s ---\n", cline.stepfile);
	printf("--- Import errors will be written to  file: %s\n", cline.log_importfile);
	printf("==============================================\n");
	printf("...working...\n");
	this->deleteModel(cline);
	SdaiInteger options = DELETING_EXISTING_MODEL | LOG_TO_FILE | LOG_TO_STDOUT | LOG_ERRORS_AND_WARNINGS_ONLY;
	SdaiInteger stepError;
	EdmiError rstat = edmiReadStepFile(cline.stepfile, cline.log_importfile , NULL, "DataRepository", NULL, "edm_validate",
		NULL, NULL, 0, options, &stepError);
	printf("--- Import finished, import errors in file: %s\n", cline.log_importfile );
	SdaiRepository rep;
	edmiGetRepository("DataRepository", &rep);
	sdaiOpenModelBN(rep, "edm_validate",sdaiRO);
	if (rstat == 11108) rstat = 0;
	return rstat;
}

EdmiError Worker::checkValidate(Commandline& cline) {
	printf("==============================================\n");
	printf("--- Validating model read from step file: %s ---\n", cline.stepfile);
	printf("--- Validation errors written to    file: %s\n", cline.log_validationfile);
	printf("==============================================\n");
	printf("...working...\n");
	if (cline.stepfile[0] == 0) return 0;
	SdaiInteger options = FULL_VALIDATION | FULL_OUTPUT;

	SdaiInstance valError;
	SdaiRepository rep;
	EdmiError rstat = edmiGetRepository("DataRepository", &rep);
	if(!rstat) rstat = edmiValidateModelBN(rep, "edm_validate", cline.log_validationfile, options, NULL, NULL, &valError);
	if (!rstat) printf("--- Validation completed, output in file: %s\n", cline.log_validationfile);
	return rstat;
}

EdmiError Worker::deleteModel(Commandline& cline) {
	SdaiRepository rep;
	EdmiError rstat = edmiGetRepository("DataRepository",&rep);
	if (!rstat)rstat = edmiDeleteModelBN(rep, "edm_validate");
	return rstat;
}

EdmiError Worker::markComplete(Commandline& cline) {
	printf("==============================================\n");
	printf("--- edm_validate completed ---\n");
	printf("==============================================\n");
	EdmiError rstat = 0;
	return rstat;
}
