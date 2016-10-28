
#define TEST_DATA_ROOT		"O:\\edm\\v6.0.xxx\\edmtest\\data"
#define EDM_DB_LOCATION		"O:\\edm\\_edm_home\\db-conductor"

char* strdup(char* s);




/*** for reference

// COMMON

#define STORING_SOURCE                     0200000000
#define DELETING_EXISTING_SCHEMAS          0400000000
#define DELETING_EXISTING_SCHEMA           0400000000
#define DELETING_EXISTING_QUERIES          0400000000
#define PARSE_ONLY                        01000000000
#define NO_DICTIONARY_MODEL               02000000000
#define CONTINUE_STORING_ON_ERROR         04000000000

// EXPRESS

#define EDM_EXPRESS_EXTENSION                      04
#define NO_EXPRESSION_CHECKED                    0100
#define NO_EXPRESSION_STORED                     0200
#define TC2                                      0400
#define ERRORS_ONLY                             01000
#define EXPRESS_EDITION_2                       02000
#define ENABLE_COMPILER_DEBUG_MESSAGES          04000
#define EXTENSIVE_DIAGNOSTICS                  040000
#define SDAI_WARNINGS                         0200000
#define DIAGNOSTICS_FOR_INTERFACED_ONLY       0100000
#define IGNORE_EMPTY_SELECT                  04000000
#define DIAGNOSTICS_FOR_ROOT_ONLY           020000000
#define LOG_SCHEMA_INTERFACE                010000000




**/


class Commandline
{
public:
	char dbpath[1024];
	char dbname[1024];
	char dbpass[1024];

	char schemafile[1024];
	char stepfile[1024];
	char log_importfile[1024];
	char log_validationfile[1024];

	Commandline(void);
	EdmiError setNotGivenArgs(void);
	int parse(int __argc, char** __argv);

};



class Worker {
public:
	EdmiError checkDatabase(Commandline& cline);
	EdmiError checkSchema(Commandline& cline);
	EdmiError checkStepfile(Commandline& cline);
	EdmiError checkValidate(Commandline& cline);
	EdmiError deleteModel(Commandline& cline);
	EdmiError markComplete(Commandline& cline);
};
