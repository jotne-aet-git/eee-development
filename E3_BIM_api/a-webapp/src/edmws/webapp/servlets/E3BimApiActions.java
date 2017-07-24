package edmws.webapp.servlets;

import org.json.JSONArray;
import org.json.JSONObject;

import edm.edom3.exception.EdmiException;
import edmws.webapp.exception.E3GeneralException;

 


public class E3BimApiActions extends E3ApiActions {

	public E3BimApiActions() {
		super(E3BimApiResourcePath.BIMAPI_VERSION);
		// TODO Auto-generated constructor stub
	}

	protected JSONArray postProcessor(String ppName,E3AccessUtil carrier,JSONArray resultList) throws EdmiException, E3GeneralException {
		if (E3Constants.QPP_LIST_MODELS.equals(ppName)) return this.getModelListFromListQuery(carrier, resultList);
		if (E3Constants.QPP_LIST_DOMAINS.equals(ppName)) return this.getDomainListFromListQuery(carrier, resultList);
		if (E3Constants.QPP_LIST_PROJECTS.equals(ppName)) return this.getProjectListFromListQuery(carrier, resultList);
		throw new E3GeneralException(E3Constants.ERROR_CODE_PARAMS,null,"Illegal postprocessor name:" + ppName);
	}
	
	private JSONArray getModelListFromListQuery(E3AccessUtil carrier,JSONArray modelList) throws EdmiException, E3GeneralException {
		String baseUrl = carrier.getBaseUrl()
						+ "/" + E3BimApiResourcePath.BIMAPI_SERVICE_TAG
						+ "/" + E3BimApiResourcePath.BIMAPI_VERSION
						+ "/" + E3BimApiResourcePath.MR_MODEL_RESOURCE
						+ "/";
		
		JSONArray resultList = new JSONArray();
		for (int i0 = 0; i0 < modelList.length();i0++) {
			JSONObject mdOut = (JSONObject)modelList.get(i0);
			// for now remove MULTIMODEL element
			try {
				mdOut.remove(E3BimApiResourcePath.MDF_MULTIMODEL_ID);
				mdOut.remove(E3BimApiResourcePath.MDF_MULTIMODEL_NAME);
			} catch(Exception ex){}
			JSONObject element = new JSONObject();
			element.put(E3BimApiResourcePath.MUF_MODEL_META_DATA,mdOut);
			element.put(E3BimApiResourcePath.MUF_MODEL_URL,baseUrl + mdOut.getString(E3BimApiResourcePath.MDF_MODEL_ID) + "?EDMSESSIONID=AUTO_LOGIN");
			resultList.put(element);			
		}
		return resultList;
	}

	
	private JSONArray getDomainListFromListQuery(E3AccessUtil carrier,JSONArray domainList) throws EdmiException, E3GeneralException {
		String baseUrl = carrier.getBaseUrl()
						+ "/" + E3BimApiResourcePath.BIMAPI_SERVICE_TAG
						+ "/" + E3BimApiResourcePath.BIMAPI_VERSION
						+ "/" + E3BimApiResourcePath.MR_DOMAIN_RESOURCE
						+ "/";
		
		JSONArray resultList = new JSONArray();
		for (int i0 = 0; i0 < domainList.length();i0++) {
			JSONObject mdOut = (JSONObject)domainList.get(i0);
			JSONObject element = new JSONObject();
			element.put(E3BimApiResourcePath.MUF_DOMAIN_META_DATA,mdOut);
			element.put(E3BimApiResourcePath.MUF_DOMAIN_URL,baseUrl + mdOut.getString(E3BimApiResourcePath.MDF_DOMAIN_ID) + "?EDMSESSIONID=AUTO_LOGIN");
			resultList.put(element);			
		}
		return resultList;
	}

	private JSONArray getProjectListFromListQuery(E3AccessUtil carrier,JSONArray projectList) throws EdmiException, E3GeneralException {
		String baseUrl = carrier.getBaseUrl()
						+ "/" + E3BimApiResourcePath.BIMAPI_SERVICE_TAG
						+ "/" + E3BimApiResourcePath.BIMAPI_VERSION
						+ "/" + E3BimApiResourcePath.MR_PROJECT_RESOURCE
						+ "/";
		
		JSONArray resultList = new JSONArray();
		for (int i0 = 0; i0 < projectList.length();i0++) {
			JSONObject mdOut = (JSONObject)projectList.get(i0);
			JSONObject element = new JSONObject();
			element.put(E3BimApiResourcePath.MUF_PROJECT_META_DATA,mdOut);
			element.put(E3BimApiResourcePath.MUF_PROJECT_URL,baseUrl + mdOut.getString(E3BimApiResourcePath.MDF_PROJECT_ID) + "?EDMSESSIONID=AUTO_LOGIN");
			resultList.put(element);			
		}
		return resultList;
	}


	@Override
	public IE3ResourcePath getAnalyzer() {
		return new E3BimApiResourcePath();
	}

}