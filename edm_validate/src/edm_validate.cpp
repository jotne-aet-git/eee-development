//---------------------------------------------------------------------
//---------------------------------------------------------------------
// conductor: EDM compiler tests - AET 20150713
//---------------------------------------------------------------------
//---------------------------------------------------------------------

#include <sdai.h>

#include <malloc.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#pragma warning(disable:4996) 
//warning C4996 : 'sprintf' : This function or variable may be unsafe.Consider using sprintf_s instead.
//To disable deprecation, use _CRT_SECURE_NO_WARNINGS.See online help for details.

#include "edm_validate.h"

char* strdup(char* s) {
	char* r = (char*)malloc(strlen(s) + 1);
	strcpy(r, s);
	return r;
}

//typedef void(*EdmiOutput)(char *); /* For edmiDefineOutputFunction() */

void dummyOutput(char*) {
	;
}




void usage(void) {

	printf("USAGE:\n");
	printf("edm_validate [–database <dblocation> <dbname> <dbpassword> ] [–schemafile <schemafile>] [-stepfile <stepfile>]\n");
	printf("\n");
	printf("if database is not specified following will be used:\n");
	printf("dblocation $EDM_HOME$\\db - $EDM_HOME$ is your EDM installation location\n");
	printf("                dbname 'db1_edm_validate'\n");
	printf("                dbpassword 'Db1#123'\n");
	printf("if database does not exists it will be created\n");
	printf("\n");
	printf("if schemafile not specified it is assumed schema is already in database\n");
	printf("if schemafile is specified the file identfied will be run throug EDM compiler \n");
	printf("if stepfile not specified the compiler is run if schemafile specified\n");
	printf("if neither schemafile nor stepfile is specified it is not very meaningful\n");
	printf("examples\n");
	printf("    edm_validate –schemafile C:\\edm\\ifc4.exp \n");
	printf("	-	Compiles IFC4 schema\n");
	printf("	edm_validate –stepfile  C:\\data\\z3.ifc\n");
	printf("	-	Validate the z3.ifc model\n");
	printf("\n");
	printf("Import diagnostic and validation output are  written to files, and not to stdout due to # of lines can grow VERY big!\n");
	printf("Unless overridden, filenames are (same directory is stepfile):\n");
	printf("    Import     diagnostics: <stepfile>.import.log\n");
	printf("    Validation diagnostics: <stepfile>.validation.log\n");
	printf("Latest loaded model is kept in EDM database for reference and possibility for further investigation. Fixed model name “DataRepository.edm_validate”\n");
	exit(2);

}



Commandline::Commandline(void) {
	strcpy(dbpath, "");
	strcpy(dbname, "");
	strcpy(dbpass, "");

	strcpy(schemafile, "");
	strcpy(stepfile, "");
	strcpy(log_importfile, "");
	strcpy(log_validationfile, "");
}

int Commandline::parse(int __argc, char** __argv) {
	if (__argc < 2) { usage(); }
	for (int i1 = 0; i1 < __argc; i1++) {
		char* arg = __argv[i1];
		if (!strcmp("-database", arg)) {
			if (__argc - i1 < 3) { usage(); }
			strcpy(dbpath, __argv[i1 + 1]);
			strcpy(dbname, __argv[i1 + 2]);
			strcpy(dbpass, __argv[i1 + 3]);
		}
		if (!strcmp("-schemafile", arg)) {
			if (__argc - i1 < 1) { usage(); }
			strcpy(schemafile, __argv[i1 + 1]);
		}
		if (!strcmp("-stepfile", arg)) {
			if (__argc - i1 < 1) { usage(); }
			strcpy(stepfile, __argv[i1 + 1]);
		}
	}
	return 0;
}

EdmiError Commandline::setNotGivenArgs(void){

	EdmiError rstat = 0;
	char* edm_home = getenv("EDM_HOME");
	if ((edm_home == NULL) || (*edm_home == 0)) {
		printf("*** Environment EDM_HOME not found, use explicit database location or set EDM_HOME in environment\n");
		usage();
		rstat = -1;
	}


	if (dbpath[0] == 0) {
		strcpy(dbpath, edm_home);
		strcat(dbpath, "\\db");
		strcpy(dbname, "db1_edm_validate");
		strcpy(dbpass, "Db1#123");
	}

	if (log_importfile[0] == 0) {
		strcpy(log_importfile, stepfile);
		strcat(log_importfile, ".import.log");
	}
	if (log_validationfile[0] == 0) {
		strcpy(log_validationfile, stepfile);
		strcat(log_validationfile, ".validation.log");
	}
	return rstat;
}



int main(int __argc, char** __argv) {

	const int NORMAL = 0;
	const int SINGLE_TEST = 1;
	const int FAILING_TESTS = 2;

	Commandline cline;
	EdmiError rstat = cline.parse(__argc, __argv);


	if (!rstat) rstat = cline.setNotGivenArgs();
	Worker worker;
	
	if (!rstat) rstat = worker.checkDatabase(cline);
	if (!rstat) rstat = worker.checkSchema(cline);
	if (!rstat) rstat = worker.checkStepfile(cline);
	if (!rstat) rstat = worker.checkValidate(cline);
	worker.markComplete(cline);
	if (rstat) {
		char* errmsg = edmiGetErrorText(rstat);
		printf("ERROR %d :%s\n", rstat,errmsg);
	}
	return (int) rstat;
}