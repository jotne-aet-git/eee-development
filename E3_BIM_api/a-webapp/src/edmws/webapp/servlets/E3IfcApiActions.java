package edmws.webapp.servlets;

import org.json.JSONArray;
import org.json.JSONObject;

import edm.edom3.exception.EdmiException;
import edmws.webapp.exception.E3GeneralException;

 


public class E3IfcApiActions extends E3ApiActions {


	public E3IfcApiActions() {
		super(E3IfcApiResourcePath.IFCAPI_VERSION);
	}

	protected JSONArray postProcessor(String ppName,E3AccessUtil carrier,JSONArray modelList) throws EdmiException, E3GeneralException {
		if (E3Constants.QPP_LIST_MODELS.equals(ppName)) return this.getModelListFromListQuery(carrier, modelList);
		throw new E3GeneralException(E3Constants.ERROR_CODE_PARAMS,null,"Illegal postprocessor name:" + ppName);
	}

	private JSONArray getModelListFromListQuery(E3AccessUtil carrier,JSONArray modelList) throws EdmiException, E3GeneralException {
		String baseUrl = carrier.getBaseUrl()
						+ "/" + E3IfcApiResourcePath.IFCAPI_SERVICE_TAG
						+ "/" + E3IfcApiResourcePath.IFCAPI_VERSION
						+ "/" + E3IfcApiResourcePath.MR_IFCMODEL
						+ "/";
		
		JSONArray resultList = new JSONArray();
		for (int i0 = 0; i0 < modelList.length();i0++) {
			//E3ModelMetaData mdOut = new E3ModelMetaData((JSONObject)modelList.get(i0));
			JSONObject mdOut = (JSONObject)modelList.get(i0);
			//E3ModelListData element = new E3ModelListData();
			JSONObject element = new JSONObject();
			element.put(E3BimApiResourcePath.MUF_MODEL_META_DATA,mdOut);
			element.put(E3BimApiResourcePath.MUF_MODEL_URL,baseUrl + mdOut.getString(E3BimApiResourcePath.MUF_MODEL_ID) + "?EDMSESSIONID=AUTO_LOGIN");
			resultList.put(element);			
		}
		return resultList;
	}
	
	@Override
	public IE3ResourcePath getAnalyzer() {
		return new E3IfcApiResourcePath();
	}

}