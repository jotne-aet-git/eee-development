package edmws.webapp.servlets;

import org.json.JSONObject;

import edmws.webapp.exception.E3GeneralException;
import edmws.ws.model.E3ResourceData;

public class E3BimApiResourcePath implements IE3ResourcePath {

	// URL parts, i.e ../<element>/<element>/
	
	public static final String BIMAPI_SERVICE_TAG = "eee-repos";
	public static final String BIMAPI_VERSION_TAG = "apiversion";
	public static final String BIMAPI_VERSION = "0.5";
	public static final String BIMAPI_VERSION_STRING = "BIM-API v0.5";
	
	// REST main resources - "service endpoints"
	
	public static final String MR_MODEL_RESOURCE = "models";	
	public static final String MR_PROJECT_RESOURCE = "projects";
	public static final String MR_MULTIMODEL_RESOURCE = "multimodels";
	public static final String MR_DOMAIN_RESOURCE = "domains";
	// REST sub resouces - can be added to m,ain resources to qualify
	
	public static final String METADATA_SERVICE_TAG = "metadata";
	
	// top level PROJECT tags
	public static final String MUF_PROJECT_META_DATA	= "project_meta_data";
	public static final String MUF_PROJECT_URL			= "project_url";

	// top level DOMAIN tags
	public static final String MUF_DOMAIN_META_DATA		= "domain_meta_data";
	public static final String MUF_DOMAIN_URL			= "domain_url";

	
	// field names for the JSON object model_upload
	
	public static final String MUF_MODEL_META_DATA		= "model_meta_data";
	public static final String MUF_MODEL_IS_EXTERNAL	= "model_is_external";
	public static final String MUF_MODEL_CONTENT        = "model_content";
	public static final String MUF_EXTERNAL_LINK 		= "model_content";
	public static final String MUF_MODEL_URL			= "model_url";
	public static final String MUF_MODEL_ID				= "model_id";
	
	// field names for the JSON object model_meta_data
	
	public static final String MDF_PROJECT_ID		= "project_id";
	public static final String MDF_PROJECT_NAME		= "project_name";
	public static final String MDF_DOMAIN_ID		= "domain_id";
	public static final String MDF_DOMAIN_NAME		= "domain_name";
	public static final String MDF_MULTIMODEL_ID	= "multimodel_id";
	public static final String MDF_MULTIMODEL_NAME	= "multimodel_name";
	public static final String MDF_MODEL_ID			= "model_id";
	public static final String MDF_MODEL_NAME		= "model_name";
	public static final String MDF_MODEL_TREAT_AS_BINARY	= "model_treat_as_binary";
	public static final String MDF_MODEL_TYPE		= "model_type";
	public static final String MDF_MODEL_VERSION	= "model_version";
	public static final String MDF_DESCRIPTION		= "description";
	public static final String MDF_SCHEMA_URL		= "schema_url";

	// provided services enum
	
	public static final String SERVICE_CREATE_MODEL		= "CREATE_MODEL";
	public static final String SERVICE_DELETE_MODEL		= "DELETE_MODEL";
	public static final String SERVICE_DOWNLOAD_MODEL	= "DOWNLOAD_MODEL";
	public static final String SERVICE_LIST_MODELS		= "LIST_MODELS";
	public static final String SERVICE_NEW_MODEL_ATTACHED	= "UPLOAD_MODEL";
	public static final String SERVICE_NEW_MODEL_LINKED		= "UPLOAD_MODEL";
	public static final String SERVICE_NEW_VERSION_ATTACHED	= "UPLOAD_MODEL";
	public static final String SERVICE_NEW_VERSION_LINKED		= "UPLOAD_MODEL";
	public static final String SERVICE_UPDATE_MODEL		= "UPDATE_MODEL";

	public static final String SERVICE_DELETE_PROJECT	= "DELETE_PROJECT";
	public static final String SERVICE_RETRIEVE_PROJECT	= "RETRIEVE_PROJECT";
	public static final String SERVICE_LIST_PROJECTS	= "LIST_PROJECTS";
	public static final String SERVICE_CREATE_PROJECT	= "CREATE_PROJECT";
	public static final String SERVICE_UPDATE_PROJECT	= "UPDATE_PROJECT";

	public static final String SERVICE_DELETE_MULTIMODEL 	= "DELETE_MULTIMODEL";
	public static final String SERVICE_RETRIEVE_MULTIMODEL	= "RETRIEVE_MULTIMODEL";
	public static final String SERVICE_LIST_MULTIMODEL		= "LIST_MULTIMODEL";
	public static final String SERVICE_CREATE_MULTIMODEL	= "CREATE_MULTIMODEL";
	public static final String SERVICE_UPDATE_MULTIMODEL	= "UPDATE_MULTIMODEL";

	public static final String SERVICE_DELETE_DOMAIN	 	= "DELETE_DOMAIN";
	public static final String SERVICE_RETRIEVE_DOMAIN  	= "RETRIEVE_DOMAIN";
	public static final String SERVICE_LIST_DOMAIN			= "LIST_DOMAINS";
	public static final String SERVICE_CREATE_DOMAIN        = "CREATE_DOMAIN";
	public static final String SERVICE_UPDATE_DOMAIN        = "UPDATE_DOMAIN";

	
	private static E3Logger logger = E3Logger.getLogger(E3BimApiResourcePath.class.getName());
	private static void log(int severity, String msg) {
		logger.log(severity, msg);
	}

	// /////////////////////
	// temporary: Query Path Handling
	// /////////////////////

	public JSONObject getBimApiPathComponents(String requestPathInfo)
			throws E3GeneralException {
		JSONObject result = new JSONObject();
		try {
			// String path1 = request.getPathInfo();/*"/AccessControl/login"*/
			String path1 = requestPathInfo;/* "/AccessControl/login" */
			String[] list1 = path1.split("/");
			int ix = 0;
			if (list1.length > 0) {
				// check if first component is ""
				if ("".equals(list1[ix])) ix++;
				// p should now be BIMAPI label
				String par = (ix < list1.length) ? list1[ix++] : "";
				String tag = BIMAPI_SERVICE_TAG;
				if(!tag.equals(par)) {
					throw new E3GeneralException(E3BasicUtils.ERROR_CODE_FAIL, null,"tag '" + tag + "' missing from:" + requestPathInfo);					
				}			
				// pick out version - "fuzzy logic" but works in most cases
				String apiVersion = BIMAPI_VERSION;
				par = (ix < list1.length) ? list1[ix] : "";
				if (par.length() < 6) {
					char dig = par.charAt(0);
					if ((dig >= '0') && (dig <= '9')) {
						apiVersion = list1[ix++];
						par = (ix < list1.length) ? list1[ix++] : "";
					}
				}
				result.put(BIMAPI_VERSION_TAG, apiVersion);
				tag = MR_PROJECT_RESOURCE;
				if(tag.equals(par)) {
					par = (ix < list1.length) ? list1[ix++] : "";
					result.put(tag,par);					
					if(!par.isEmpty()) par = (ix < list1.length) ? list1[ix++] : "";
				}
				tag = MR_MULTIMODEL_RESOURCE;
				if(tag.equals(par)) {
					par = (ix < list1.length) ? list1[ix++] : "";
					result.put(tag,par);					
					if(!par.isEmpty()) par = (ix < list1.length) ? list1[ix++] : "";
				}
				tag = MR_DOMAIN_RESOURCE;
				if(tag.equals(par)) {
					par = (ix < list1.length) ? list1[ix++] : "";
					result.put(tag,par);					
					if(!par.isEmpty()) par = (ix < list1.length) ? list1[ix++] : "";
				}
				tag = MR_MODEL_RESOURCE;
				if(tag.equals(par)) {
					par = (ix < list1.length) ? list1[ix++] : "";
					result.put(tag,par);					
					if(!par.isEmpty()) par = (ix < list1.length) ? list1[ix++] : "";
				}
				tag = METADATA_SERVICE_TAG;
				if(tag.equals(par)) {
					par = (ix < list1.length) ? list1[ix++] : "true";
					result.put(tag,par);					
					if(!par.isEmpty()) par = (ix < list1.length) ? list1[ix++] : "";
				}
			}
		}
		// catch(E3GeneralException ex) {
		// throw ex;
		// }
		catch (Exception ex) {
			throw new E3GeneralException(E3BasicUtils.ERROR_CODE_FAIL, ex,"BIMAPI URL parse failed:" + requestPathInfo);
		}
		return result;
	}


	
	/* (non-Javadoc)
	 * @see edmws.webapp.servlets.IE3ResourcePath#analyzeResource(java.lang.String, java.lang.String, org.json.JSONObject, org.json.JSONObject)
	 */
	@Override
	public JSONObject analyzeResource(E3ResourceData e3Data,IE3JSONSource jsonBodySource) throws E3GeneralException {
		JSONObject result = new JSONObject();
		String resourceName = null;
		String guid = null;
		String eeeOp = null;
		String restOp = e3Data.getRestOP();
		JSONObject urlArguments = e3Data.getUrlArgs();
		JSONObject jsonBody = null;
		JSONObject jsonUrlComponents = this.getBimApiPathComponents(e3Data.getURL());
		log(E3Logger.DEBUG, "--analyseResource par: " + jsonUrlComponents.toString());

		String apiVersion = jsonUrlComponents
				.optString(BIMAPI_VERSION_TAG);
		if (!apiVersion.equals(BIMAPI_VERSION)) {
			throw new E3GeneralException(E3Constants.ERROR_CODE_FAIL, null,"Unhandled BIMAPI version:" + apiVersion);
		}

		if (jsonUrlComponents.has(MR_MODEL_RESOURCE)) {
			resourceName = MR_MODEL_RESOURCE;
			guid = jsonUrlComponents.optString(resourceName);
			if (guid != "") result.put(MDF_MODEL_ID, guid);
			if ("GET".equals(restOp)) {
				boolean lMetaData = jsonUrlComponents.optString(METADATA_SERVICE_TAG).length() > 0;
				if (lMetaData && (guid.length() > 0)) {
					eeeOp = SERVICE_LIST_MODELS;
				} else if (guid.length() > 0) {
					eeeOp = SERVICE_DOWNLOAD_MODEL;
				} else {
					eeeOp = SERVICE_LIST_MODELS;
				}

			} else if ("DELETE".equals(restOp)) {
				eeeOp = SERVICE_DELETE_MODEL;

			} else if ("POST".equals(restOp)) {
				
				/*
				  Deciphering of POST :
				  SERVICE				model_id	JSON		p_id in		m_id		model_is_external
				  						in URL		body 		URL par		in body
				  NEW_MODEL_ATTACHED		yes		(multipart)	yes
				  NEW_VERSION_ATTACHED		yes		(multipart)	no				  								
				  NEW_MODEL_ATTACHED		yes		no			yes
				  NEW_VERSION_ATTACHED		yes		no			no
				  NEW_VERSION_LINKED		no		upload_d	no 			yes			true
				  NEW_MODEL_LINKED			no		upload_d	no			no			true
				  CREATE_MODEL				no		model_d		no			no			false (not present)
				 */

				
				boolean isNMUA = 	(urlArguments.optString(MDF_PROJECT_ID, "") != "")
							     || (urlArguments.optString(MDF_PROJECT_NAME, "") != "");
				if (isNMUA) {
					eeeOp = SERVICE_NEW_MODEL_ATTACHED;
					result = jsonUrlComponents; // there are no args in body!
				} else if (guid.length() > 0) {
					eeeOp = SERVICE_NEW_VERSION_ATTACHED;
					result = jsonUrlComponents; // there are no args in body!
				} else { // all others have params in JSON body
					JSONObject mud = jsonBodySource.getJsonIn();
					boolean isLinked = mud.optBoolean(E3BimApiResourcePath.MUF_MODEL_IS_EXTERNAL);
					// check if model_meta_data is member, if not its probably a CREATE
					result = mud.optJSONObject(E3BimApiResourcePath.MUF_MODEL_META_DATA);
					if (null == result) {
						eeeOp = SERVICE_CREATE_MODEL;
						result = jsonBodySource.getJsonIn();
					} else if (!isLinked) { // ??
						throw new E3GeneralException(E3Constants.ERROR_CODE_PARAMS, null,"model_id param unexpected here for UPLOAD_MODEL");
					} else if (result.optString(MDF_MODEL_ID).length() > 0) { // model_id given => version
						eeeOp = SERVICE_NEW_VERSION_LINKED;
						result.put(E3BimApiResourcePath.MUF_MODEL_IS_EXTERNAL, "true"); 
						result.put(E3BimApiResourcePath.MUF_MODEL_CONTENT,mud.optString(E3BimApiResourcePath.MUF_MODEL_CONTENT));						
					} else {
						eeeOp = SERVICE_NEW_MODEL_LINKED;
						result.put(E3BimApiResourcePath.MUF_MODEL_IS_EXTERNAL, "true"); 
						result.put(E3BimApiResourcePath.MUF_MODEL_CONTENT,mud.optString(E3BimApiResourcePath.MUF_MODEL_CONTENT));						
					}
				}
			} else if ("PUT".equals(restOp)) {
				eeeOp = SERVICE_UPDATE_MODEL;
			}

		}

		else if (jsonUrlComponents.has(MR_DOMAIN_RESOURCE)) {
			resourceName = MR_DOMAIN_RESOURCE;
			guid = jsonUrlComponents.optString(resourceName);
			if ("GET".equals(restOp)) {
				eeeOp = ((guid == null) || ("".equals(guid))) ? SERVICE_LIST_DOMAIN : SERVICE_RETRIEVE_DOMAIN;
			} else if ("DELETE".equals(restOp)) {
				eeeOp = SERVICE_DELETE_DOMAIN;
			} else if ("POST".equals(restOp)) {
				eeeOp = SERVICE_CREATE_DOMAIN;
			} else if ("PUT".equals(restOp)) {
				eeeOp = SERVICE_UPDATE_DOMAIN;
			}

		}

		else if (jsonUrlComponents.has(MR_MULTIMODEL_RESOURCE)) {
			resourceName = MR_MULTIMODEL_RESOURCE;
			guid = jsonUrlComponents.optString(resourceName);
			if ("GET".equals(restOp)) {
				eeeOp = ((guid == null) || ("".equals(guid))) ? SERVICE_LIST_MULTIMODEL : SERVICE_RETRIEVE_MULTIMODEL;
			} else if ("DELETE".equals(restOp)) {
				eeeOp = SERVICE_DELETE_MULTIMODEL;
			} else if ("POST".equals(restOp)) {
				eeeOp = SERVICE_CREATE_MULTIMODEL;
			} else if ("PUT".equals(restOp)) {
				eeeOp = SERVICE_UPDATE_MULTIMODEL;
			}

		}

		else if (jsonUrlComponents.has(MR_PROJECT_RESOURCE)) {
			resourceName = MR_PROJECT_RESOURCE;
			guid = jsonUrlComponents.optString(resourceName);
			if (guid != "") result.put(MDF_PROJECT_ID, guid);			
			if ("GET".equals(restOp)) {
				eeeOp = ((guid == null) || ("".equals(guid))) ? SERVICE_LIST_PROJECTS : SERVICE_RETRIEVE_PROJECT;
			} else if ("DELETE".equals(restOp)) {
				eeeOp = SERVICE_DELETE_PROJECT;
			} else if ("POST".equals(restOp)) {
				eeeOp = SERVICE_CREATE_PROJECT;
				jsonBody = jsonBodySource.getJsonIn();				
			} else if ("PUT".equals(restOp)) {
				eeeOp = SERVICE_UPDATE_PROJECT;
			}

		}
		
		if( resourceName != null) result.put("resourceName",resourceName);
		if( eeeOp != null) result.put(E3Constants.MR_EEEOP,eeeOp);
		if( restOp != null) result.put(E3Constants.MR_RESTOP,restOp);

		// if the "jsonBody" is in use assume it's a flat string list. Other layout must be handled locally
		if(jsonBody != null) {
			for(String key : jsonBody.keySet()) {
				String val = jsonBody.getString(key);
				if ("" != val) result.put(key, val);
			}
		}

		// params in request always overrides values in request body
		if(urlArguments != null) {
			for(String key : urlArguments.keySet()) {
				String val = urlArguments.getString(key);
				if ("" != val) result.put(key, val);
			}
		}

		this.analyzeQueryPath(e3Data.getQueryPath(), result);
		return result;
	}


	private void analyzeQueryPath(IQueryPath carrier,JSONObject jargs) throws E3GeneralException {
		
		String eeeOp = jargs.getString(E3Constants.MR_EEEOP);

		carrier.setPostProcessor(E3Constants.QPP_NONE);
		
		if (SERVICE_LIST_DOMAIN.equals(eeeOp)) {
			carrier.setRepositoryName("ModelServer");
			carrier.setModelName("EnterpriseModel");
			carrier.setQuerySchemaName("E3Queries");
			carrier.setQueryFunctionName("E3ListDomains0");
			carrier.setPostProcessor(E3Constants.QPP_LIST_DOMAINS);


		} else if (SERVICE_LIST_MODELS.equals(eeeOp)) {
			carrier.setRepositoryName("ModelServer");
			carrier.setModelName("EnterpriseModel");
			carrier.setQuerySchemaName("E3Queries");
			carrier.setQueryFunctionName("E3ListModels0");
			carrier.setPostProcessor(E3Constants.QPP_LIST_MODELS);
			
		} else if (SERVICE_UPDATE_MODEL.equals(eeeOp)) {
			carrier.setRepositoryName("ModelServer");
			carrier.setModelName("EnterpriseModel");
			carrier.setQuerySchemaName("E3Queries");
			carrier.setQueryFunctionName("E3UpdateModelVersion1");
			carrier.setPostProcessor(E3Constants.QPP_LIST_MODELS);

		} else if (SERVICE_CREATE_MODEL.equals(eeeOp)) {
			carrier.setRepositoryName("ModelServer");
			carrier.setModelName("EnterpriseModel");
			carrier.setQuerySchemaName("MasterServices");
			carrier.setQueryFunctionName("E3CreateModel1");
			carrier.setPostProcessor(E3Constants.QPP_LIST_MODELS);

		} else if (SERVICE_DELETE_MODEL.equals(eeeOp)) {
			carrier.setRepositoryName("ModelServer");
			carrier.setModelName("EnterpriseModel");
			carrier.setQuerySchemaName("MasterServices");
			carrier.setQueryFunctionName("E3DeleteModelVersion1");
			carrier.setPostProcessor(E3Constants.QPP_LIST_MODELS);

		} else if (SERVICE_DOWNLOAD_MODEL.equals(eeeOp)) {
			carrier.setRepositoryName("ModelServer");
			carrier.setModelName("EnterpriseModel");
			carrier.setQuerySchemaName("E3Queries");
			carrier.setQueryFunctionName("E3ExportModel1");			

		} else if (SERVICE_NEW_MODEL_ATTACHED.equals(eeeOp)) {
			carrier.setRepositoryName("ModelServer");
			carrier.setModelName("EnterpriseModel");
			carrier.setQuerySchemaName("MasterServices");
			carrier.setQueryFunctionName("E3ImportModel1");

		} else if (SERVICE_NEW_MODEL_LINKED.equals(eeeOp)) {
			carrier.setRepositoryName("ModelServer");
			carrier.setModelName("EnterpriseModel");
			carrier.setQuerySchemaName("MasterServices");
			carrier.setQueryFunctionName("E3ImportModel1");

		} else if (SERVICE_NEW_VERSION_LINKED.equals(eeeOp)) {
			carrier.setRepositoryName("ModelServer");
			carrier.setModelName("EnterpriseModel");
			carrier.setQuerySchemaName("MasterServices");
			carrier.setQueryFunctionName("E3ImportModel1");

		//} else if (SERVICE_DELETE_MULTIMODEL.equals(eeeOp)) {
		//	carrier.setQueryType(QT_NOT_IMPLEMENTED);

		} else if (SERVICE_LIST_MULTIMODEL.equals(eeeOp)) {
			carrier.setRepositoryName("ModelServer");
			carrier.setModelName("EnterpriseModel");
			carrier.setQuerySchemaName("E3Queries");
			carrier.setQueryFunctionName("E3ListMultiModels0");

/*
		} else if (SERVICE_RETRIEVE_MULTIMODEL.equals(eeeOp)) {
			carrier.setQueryType(QT_NOT_IMPLEMENTED);
		} else if (SERVICE_LIST_MULTIMODEL.equals(eeeOp)) {
			carrier.setQueryType(QT_NOT_IMPLEMENTED);
		} else if (SERVICE_UPDATE_MULTIMODEL.equals(eeeOp)) {
			carrier.setQueryType(QT_NOT_IMPLEMENTED);
		} else if (SERVICE_CREATE_MULTIMODEL.equals(eeeOp)) {
			carrier.setQueryType(QT_NOT_IMPLEMENTED);
*/
		} else if (SERVICE_LIST_PROJECTS.equals(eeeOp)) {
			carrier.setRepositoryName("ModelServer");
			carrier.setModelName("EnterpriseModel");
			carrier.setQuerySchemaName("E3Queries");
			carrier.setQueryFunctionName("E3ListProjects0");
			carrier.setPostProcessor(E3Constants.QPP_LIST_PROJECTS);
			
		} else if (SERVICE_CREATE_PROJECT.equals(eeeOp)) {
			carrier.setRepositoryName("ModelServer");
			carrier.setModelName("EnterpriseModel");
			carrier.setQuerySchemaName("MasterServices");
			carrier.setQueryFunctionName("E3CreateProject");
			carrier.setPostProcessor(E3Constants.QPP_LIST_PROJECTS);
		} else if (SERVICE_DELETE_PROJECT.equals(eeeOp)) {
			carrier.setRepositoryName("ModelServer");
			carrier.setModelName("EnterpriseModel");
			carrier.setQuerySchemaName("MasterServices");
			carrier.setQueryFunctionName("E3DeleteProject");
			carrier.setPostProcessor(E3Constants.QPP_LIST_PROJECTS);
/*
		} else if (SERVICE_RETRIEVE_PROJECT.equals(eeeOp)) {
			carrier.setQueryType(QT_NOT_IMPLEMENTED);
		} else if (SERVICE_UPDATE_PROJECT.equals(eeeOp)) {
			carrier.setQueryType(QT_NOT_IMPLEMENTED);
*/
		} else {
			if(null == eeeOp) eeeOp = "(null)";
			throw new E3GeneralException(E3Constants.ERROR_CODE_FAIL, null,
					"Illegal or unimplemented E3 domain service operation:" + eeeOp);
		}
	}

}
