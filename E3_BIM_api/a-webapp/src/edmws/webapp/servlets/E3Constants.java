package edmws.webapp.servlets;

import javax.servlet.http.HttpServletResponse;

public class E3Constants {

	public static final String SERVICE_UNKNOWN 			= "UNKNOWN";
	public static final String SERVICE_NOT_IMPLEMENTED 	= "NOT_IMPLEMENTED";

	
	public final static int ERROR_CODE_FAIL = HttpServletResponse.SC_INTERNAL_SERVER_ERROR; // 500
	public final static int ERROR_CODE_PARAMS = HttpServletResponse.SC_BAD_REQUEST; // 400
	public final static int ERROR_CODE_AUTH = HttpServletResponse.SC_FORBIDDEN; // 403
	public final static int ERROR_CODE_SESSION_EXPIRED = HttpServletResponse.SC_UNAUTHORIZED; // 401

	// additional query argument names in on model_meta_data

	public static final String QF_ARG_INPUT_FILE	= "input_file";
	public static final String QF_ARG_OUTPUT_FILE	= "output_file";

	// Schema names that require special handling

	//public static final String SCHEMA_BINARY_MODEL = "E3BinaryModel";

	
	// special patch - filter out "multimodel"	
	// TODO: remove public static final boolean BLOCK_MULTIMODEL_FIELDS = true;

	// query path components
	
	public static final String QP_REPOSITORY_NAME = "repository_name";
	public static final String QP_MODEL_NAME = "model_name";
	public static final String QP_QUERY_SCHEMA_NAME = "query_schema";
	public static final String QP_QUERY_FUNCTION_NAME = "query_function";

	// Query postprocessor,  - temporary solution for creating retruns that are not "table"
	public static final String QPP_NONE = "";
	public static final String QPP_LIST_MODELS = "E3BimApiActions.getModelListFromListQuery";
	public static final String QPP_LIST_DOMAINS = "E3BimApiActions.getDomainListFromListQuery";
	public static final String QPP_LIST_PROJECTS = "E3BimApiActions.getProjectListFromListQuery";

	public static final String MR_EEEOP = "eeeop";
	public static final String MR_RESTOP = "restop";

	
	
}
