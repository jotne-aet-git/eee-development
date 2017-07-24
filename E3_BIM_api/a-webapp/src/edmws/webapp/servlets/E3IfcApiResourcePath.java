package edmws.webapp.servlets;

import org.json.JSONArray;
import org.json.JSONObject;

import edmws.webapp.exception.E3GeneralException;
import edmws.ws.model.E3ResourceData;

public class E3IfcApiResourcePath implements IE3ResourcePath {

	public static final String IFCAPI_VERSION = "0.5";
	public static final String IFCAPI_VERSION_STRING = "IFC-API v" + IFCAPI_VERSION;

	// URL parts, i.e ../<element>/<element>/
	
	public static final String IFCAPI_SERVICE_TAG = "ifc-api";
	public static final String IFCAPI_VERSION_TAG = "apiversion";
	
	// resources - ie service endpoints
	public static final String MR_IFCMODEL = "ifcmodel";	
	public static final String MR_IFCTYPE = "ifctype";	
	public static final String MR_IFCGUID= "globalid";	
	public static final String MR_IFCSUBTYPE  = "ifcsubtype";	
	public static final String MR_IFCSUBTYPEGUID   = "subtype_globalid";	
	public static final String MR_IFCFUNCTION   = "ifcfunction";	

	
	// function verbs
	
	public static final String MR_IFCFUNCTION_MERGE = "merge";
	public static final String MR_IFCFUNCTION_EXTRACT = "extract";
	public static final String MR_IFCFUNCTION_CONVERT = "convert";
	public static final String MR_IFCFUNCTION_APPEND_FILE = "append_file";
	
	
	// function enumeration, really)
	
	public static final String IAF_LIST_MERGE_HISTORY = "LIST_MERGE_HISTORY";
	public static final String IAF_DELETE_MERGE_HISTORY = "DELETE_MERGE_HISTORY";
	public static final String IAF_CREATE_MERGE_HISTORY = "CREATE_MERGE_HISTORY"; // actually: do a merge :)			
	public static final String IAF_UPDATE_MERGE_HISTORY = "UPDATE_MERGE_HISTORY";

	public static final String IAF_LIST_EXTRACT_ENDPOINTS = "LIST_EXTRACT_ENDPOINTS";
	public static final String IAF_EXTRACT_ENDPOINT = "EXTRACT_ENDPOINT";
	
	public static final String IAF_RETRIEVE_IFC_ITEM= "RETRIEVE_IFC_ITEM";
	public static final String IAF_DELETE_IFC_ITEM = "DELETE_IFC_ITEM";
	public static final String IAF_CREATE_IFC_ITEM = "CREATE_IFC_ITEM";			
	public static final String IAF_UPDATE_IFC_ITEM = "UPDATE_IFC_ITEM";	

	public static final String IAF_APPEND_FILE = "APPEND_FILE";
	
	// field names for the JSON object model_upload
	
	public static final String IAF_EXTERNAL_LINK 		= "file_url";
	public static final String IAF_FILE_TYPE 			= "file_type";
	public static final String IAF_MERGE_FUNCTION 		= "merge_function";
	
	private static E3Logger logger = E3Logger.getLogger(E3IfcApiResourcePath.class.getName());

	private static void log(int severity, String msg) {
		logger.log(severity, msg);
	}


	// /////////////////////
	// temporary: Query Path Handling
	// /////////////////////


	protected boolean isReservedWord(String word) {
		if(word != null) {
			for(char c: word.toCharArray()) {
				if(c == '_') continue;
				if(Character.isDigit(c)) continue;
				if(Character.isLowerCase(c)) continue;
				return false;
			}
			if (word.startsWith("ifc")) return true;
			if (word.startsWith("edm")) return true;
			if (word.startsWith("e3_")) return true;
		}
		return false;
	}

	protected boolean isIfcApiFunction(String word) {
		if(word != null) {
			word = word.toLowerCase();
			if (word.equals(MR_IFCFUNCTION_MERGE)) return true;
			if (word.equals(MR_IFCFUNCTION_EXTRACT)) return true;
			if (word.equals(MR_IFCFUNCTION_CONVERT)) return true;
			if (word.equals(MR_IFCFUNCTION_APPEND_FILE)) return true;
		}
		return false;
	}
	
	protected boolean isPossibleId(String word) {
		if(word != null) {
			if(!word.isEmpty()) {
				return !isReservedWord(word);
			}
		}
		return false;
	}

	// TODO: temporary solution while waiting for deep JSON arg lists to query...
	private JSONObject parseMergeArgumentData(JSONObject mad){
		if(mad == null) return null;
		JSONObject obj;
		JSONObject result = new JSONObject();
		JSONArray arr = mad.optJSONArray("source_models");
		if (arr != null) {
			for(int i0 = 0; i0 < arr.length();i0++) {
				String key = "source_model_id_" + (i0+1);
				obj = arr.optJSONObject(i0);
				if(obj != null) result.put(key, obj.optString("model_id"));
			}
		}
		obj = mad.optJSONObject("target_model");
		if(obj != null) result.put("target_model_id", obj.optString("model_id"));
		
		arr = mad.optJSONArray("arguments");
		if (arr != null) {
			result.put("json_arguments",arr.toString());
/*			
			for(int i0 = 0; i0 < arr.length();i0++) {
				obj = arr.optJSONObject(i0);
				if (obj != null) {
					for(String key : obj.keySet()) {
						String val = obj.get(key).toString();
						if ("" != val) result.put(key, val);
					}
				}
			}
*/			
		}

		return result;
	}
	
	
	protected JSONObject getPathComponents(String requestPathInfo)
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
				String tag = IFCAPI_SERVICE_TAG;
				if(!tag.equals(par)) {
					throw new E3GeneralException(E3BasicUtils.ERROR_CODE_FAIL, null,"tag '" + tag + "' missing from:" + requestPathInfo);					
				}
				// pick out version - "fuzzy logic" but works in most cases
				String apiVersion = IFCAPI_VERSION;
				par = (ix < list1.length) ? list1[ix] : "";
				if (par.length() < 6) {
					char dig = par.charAt(0);
					if ((dig >= '0') && (dig <= '9')) {
						apiVersion = list1[ix++];
						par = (ix < list1.length) ? list1[ix++] : "";
					}
				}
				result.put(IFCAPI_VERSION_TAG, apiVersion);
				tag = MR_IFCMODEL;
				if(!tag.equals(par)) {
					throw new E3GeneralException(E3BasicUtils.ERROR_CODE_FAIL, null,"tag '" + tag + "' missing from:" + requestPathInfo);					
				}
				par = (ix < list1.length) ? list1[ix++] : "";				
				if(this.isPossibleId(par)) {
					result.put(tag,par);					
					par = (ix < list1.length) ? list1[ix++] : "";				
				}
				if(this.isReservedWord(par)) {
					result.put(MR_IFCTYPE,par);					
					par = (ix < list1.length) ? list1[ix++] : "";				
				} else if (this.isIfcApiFunction(par)){
					result.put(MR_IFCFUNCTION,par);					
					result.put(MR_IFCTYPE,par); // maybe a little bit dirty - short-circuit validity check later...					
					par = (ix < list1.length) ? list1[ix++] : "";								
				} else {
					throw new E3GeneralException(E3BasicUtils.ERROR_CODE_FAIL, null,"ifctype missing from:" + requestPathInfo);										
				}

				if(this.isPossibleId(par)) {
					result.put(MR_IFCGUID,par);					
					par = (ix < list1.length) ? list1[ix++] : "";				
				}
				
				if(this.isReservedWord(par)) {
					result.put(MR_IFCSUBTYPE,par);					
					par = (ix < list1.length) ? list1[ix++] : "";				
				}
				
				if(this.isPossibleId(par)) {
					result.put(MR_IFCSUBTYPEGUID,par);					
					par = (ix < list1.length) ? list1[ix++] : "";				
				}

				if(this.isReservedWord(par)) {
					result.put(MR_IFCFUNCTION,par);					
					par = (ix < list1.length) ? list1[ix++] : "";				
				}

				// to make life easier for app programmer : if no SUBTYPEGUID SUBTYPE may also mean FUNCTION
				if(!result.has(MR_IFCSUBTYPEGUID) && result.has(MR_IFCSUBTYPE)) result.put(MR_IFCFUNCTION, result.getString(MR_IFCSUBTYPE));
			}
		}
		// catch(E3GeneralException ex) {
		// throw ex;
		// }
		catch (Exception ex) {
			throw new E3GeneralException(E3BasicUtils.ERROR_CODE_FAIL, ex,"IFCAPI URL parse failed:" + requestPathInfo);
		}
		return result;
	}
	
	/**
	 * 
	 * @param restOp: "GET" "POST" "DELETE" "PUT"
	 * @param urlArguments: URL?<arg1=val1><&arg2=val2>....
	 * @return
	 * @throws E3GeneralException
	 */
	public JSONObject analyzeResource(E3ResourceData e3Data,IE3JSONSource jsonBodySource) throws E3GeneralException {
		JSONObject result = new JSONObject();
		String eeeOp = null;
		JSONObject jsonUrlComponents = this.getPathComponents(e3Data.getURL());
		log(E3Logger.DEBUG, "--analyseResource par: " + jsonUrlComponents.toString());

		String apiVersion = jsonUrlComponents.optString(IFCAPI_VERSION_TAG);
		if (!apiVersion.equals(IFCAPI_VERSION)) {
			throw new E3GeneralException(E3Constants.ERROR_CODE_FAIL, null,"Unhandled IFCAPI version:" + apiVersion);
		}

		if (!jsonUrlComponents.has(MR_IFCMODEL)) {
			throw new E3GeneralException(E3Constants.ERROR_CODE_FAIL, null,"Missing MODEL identifier");
		}
		String modelGuid = jsonUrlComponents.getString(MR_IFCMODEL);
		if (!jsonUrlComponents.has(MR_IFCTYPE)) {
			throw new E3GeneralException(E3Constants.ERROR_CODE_FAIL, null,"Missing IFC type identifier");
		}
//		String ifcType = jsonUrlComponents.getString(MR_IFCTYPE);
//		String globalId =  jsonUrlComponents.optString(MR_IFCGUID,"");
//		String ifcOp =  jsonUrlComponents.optString(MR_IFCFUNCTION,"");

		String restOp = e3Data.getRestOP();
		
		if(MR_IFCFUNCTION_MERGE.equals(jsonUrlComponents.opt(MR_IFCFUNCTION))) {
			if ("GET".equals(restOp)) {
				eeeOp = IAF_LIST_MERGE_HISTORY;
			} else if ("DELETE".equals(restOp)) {
				eeeOp = IAF_DELETE_MERGE_HISTORY;
			} else if ("POST".equals(restOp)) {
				eeeOp = IAF_CREATE_MERGE_HISTORY; // actually: do a merge :)
				JSONObject jsonBody = jsonBodySource.getJsonIn();				
				JSONObject parsed = parseMergeArgumentData(jsonBody);
				if(parsed != null) {
					for(String key : parsed.keySet()) {
						String val = parsed.getString(key);
						if ("" != val) result.put(key, val);
					}
				}
				
				
			} else if ("PUT".equals(restOp)) {
				eeeOp = IAF_UPDATE_MERGE_HISTORY;	
			} else {
				throw new E3GeneralException(1,null,"Illegal service URL");
			}			
		} else  if(MR_IFCFUNCTION_EXTRACT.equals(jsonUrlComponents.opt(MR_IFCFUNCTION))) {
				if ("GET".equals(restOp)) {
					JSONObject jsonBody = jsonBodySource.getJsonIn();
					if ((jsonBody != null) && (jsonBody.length() > 0)) {
						eeeOp = IAF_EXTRACT_ENDPOINT;
						for(String key : jsonBody.keySet()) {
							String val = jsonBody.getString(key);
							if ("" != val) result.put(key, val);
						}						
					} else {
						eeeOp = IAF_LIST_EXTRACT_ENDPOINTS;
					}
				} else {
					throw new E3GeneralException(1,null,"Illegal service operation:" + restOp);
				}			
		} else  if(MR_IFCFUNCTION_APPEND_FILE.equals(jsonUrlComponents.opt(MR_IFCFUNCTION))) {
			if ("POST".equals(restOp)) {
				eeeOp = IAF_APPEND_FILE;
			} else {
				throw new E3GeneralException(1,null,"Illegal service operation:" + restOp);
			}			
		} else {			
			if ("GET".equals(restOp)) {
				eeeOp = IAF_RETRIEVE_IFC_ITEM;
			} else if ("DELETE".equals(restOp)) {
				eeeOp = IAF_DELETE_IFC_ITEM;
			} else if ("POST".equals(restOp)) {
				eeeOp = IAF_CREATE_IFC_ITEM;			
			} else if ("PUT".equals(restOp)) {
				eeeOp = IAF_UPDATE_IFC_ITEM;	
			} else {
				throw new E3GeneralException(1,null,"Illegal service URL");
			}
		}
		
		if( eeeOp != null) result.put(E3Constants.MR_EEEOP,eeeOp);
		if( restOp != null) result.put(E3Constants.MR_RESTOP,restOp);
		if( modelGuid != null) result.put("model_id",modelGuid);
		String tag;
		tag = MR_IFCTYPE; if( jsonUrlComponents.has(tag)) result.put(tag,jsonUrlComponents.getString(tag));
		tag = MR_IFCGUID; if( jsonUrlComponents.has(tag)) result.put(tag,jsonUrlComponents.getString(tag));
		tag = MR_IFCSUBTYPE; if( jsonUrlComponents.has(tag)) result.put(tag,jsonUrlComponents.getString(tag));
		tag = MR_IFCSUBTYPEGUID; if( jsonUrlComponents.has(tag)) result.put(tag,jsonUrlComponents.getString(tag));
		tag = MR_IFCFUNCTION; if( jsonUrlComponents.has(tag)) result.put(tag,jsonUrlComponents.getString(tag));


		// params in request always overrides values in request body
		JSONObject urlArguments = e3Data.getUrlArgs();
		if(urlArguments != null) {
			for(String key : urlArguments.keySet()) {
				String val = urlArguments.getString(key);
				if ("" != val) result.put(key, val);
			}
		}

		this.analyzeQueryPath(e3Data.getQueryPath(),result);
		return result;
	}


	private void analyzeQueryPath(IQueryPath qp,JSONObject jargs) throws E3GeneralException {
		
		String eeeOp = jargs.getString(E3Constants.MR_EEEOP);
		
		qp.setPostProcessor(E3Constants.QPP_NONE);
		
		if (eeeOp.endsWith("_IFC_ITEM")) {
			qp.setRepositoryName("ModelServer");
			qp.setModelName("EnterpriseModel");
			qp.setQuerySchemaName("E3Queries");
			qp.setQueryFunctionName("E3IfcItemOperation");

		} else if (eeeOp.endsWith("_MERGE_HISTORY")) {
			if(eeeOp.equals(IAF_CREATE_MERGE_HISTORY)) {
				
				qp.setRepositoryName("DataRepository");
				//qp.setModelName("DummyModel_IFC2X3");
				qp.setModelName("DummyModel_IFC4");
				qp.setQuerySchemaName("IfcEditFunctions");
				qp.setQueryFunctionName("E3MergeModels1");
				
				/*
				qp.setRepositoryName("ModelServer");
				qp.setModelName("EnterpriseModel");
				qp.setQuerySchemaName("E3Queries");
				qp.setQueryFunctionName("E3MergeModels1");
				*/				
			} else {
				throw new E3GeneralException(E3Constants.ERROR_CODE_FAIL, null,"Function not implemented:" + eeeOp);
			}
		} else if (eeeOp.contains("EXTRACT_ENDPOINT")) {
			qp.setRepositoryName("ModelServer");
			qp.setModelName("EnterpriseModel");
			qp.setQuerySchemaName("E3Queries");
			if(eeeOp.equals(IAF_LIST_EXTRACT_ENDPOINTS)) {
				qp.setQueryFunctionName("E3ListExtractEndpoints");				
			} else if(eeeOp.equals(IAF_EXTRACT_ENDPOINT)) {
					qp.setQueryFunctionName("E3ExtractEndpoint");				
			} else {
				throw new E3GeneralException(E3Constants.ERROR_CODE_FAIL, null,"Function not implemented:" + eeeOp);
			}

		} else if (eeeOp.startsWith("APPEND_")) {
			qp.setRepositoryName("ModelServer");
			qp.setModelName("EnterpriseModel");
			qp.setQuerySchemaName("E3Queries");
			qp.setQueryFunctionName("E3AppendFile1");				
		} else {
			throw new E3GeneralException(E3Constants.ERROR_CODE_FAIL, null,"Illegal service operation: " + eeeOp);
		}
	}



}
