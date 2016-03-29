package edmws.bimapi.test;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import edmws.webapp.servlets.E3AccessUtil;
import edmws.webapp.servlets.E3ApiActions;
import edmws.webapp.servlets.E3BimApiResourcePath;
import edmws.webapp.servlets.E3Logger;

public class E3TestUtils {


	
	private E3TestArgs BimApiArgs() {
		return new E3TestArgs("GET","/eee-repos/0.5/models");
	}

	
	private static E3Logger logger = E3Logger.getLogger(E3TestUtils.class.getName());
	private static void log(int severity,String msg) {
		logger.log(severity, msg);
	}
	
	E3TestBase testlib;
	E3AccessUtil carrier;
	public E3TestUtils(E3TestBase testlib,E3AccessUtil carrier) {
		this.testlib = testlib;
		this.carrier = carrier;
	}


	protected String runE3Service(String servletName, String workerClassName,E3TestArgs args) throws Exception {
		String result;
		if(testlib.useWebService()) {
			args.setServerUrl(testlib.webServerRoot + "/" + servletName);
			result = new E3TestWebRequest().sendGet(args);			
		} else {
			carrier.setEDMTestContext(testlib.getRemoteContext(), args.getUrl(),args.urlArgs,args.bodyArgs);
			E3ApiActions worker = (E3ApiActions) Class.forName(workerClassName).newInstance();
			//E3BimApiActions worker = new E3BimApiActions();
			result = worker.doRest3(carrier, args.restOp);
		}
		return result;
	}

	private String runBimApiService(E3TestArgs args) throws Exception {
		return this.runE3Service("E3ReposServlet","edmws.webapp.servlets.E3BimApiActions",args); 
	}

	public String loadTestModel(String testModelName) throws Exception{
		E3TestArgs ta = BimApiArgs();
		String result = this.runBimApiService(ta);
		String model_guid = null;
		JSONArray jresult = new JSONArray(result);
		for(int i0 = 0; i0 < jresult.length(); i0++) {
			JSONObject md = jresult.getJSONObject(i0);
			String model_name = md.getJSONObject(E3BimApiResourcePath.MUF_MODEL_META_DATA).getString(E3BimApiResourcePath.MDF_MODEL_NAME);
			if (model_name.equals(testModelName)) {
				model_guid = md.getJSONObject(E3BimApiResourcePath.MUF_MODEL_META_DATA).getString(E3BimApiResourcePath.MDF_MODEL_ID);
				break;
			}
		}
		if(model_guid == null) {
			ta.restOp = "POST";
			ta.urlArgs = new JSONObject();
			ta.urlArgs.put(E3BimApiResourcePath.MDF_PROJECT_NAME, "FM");
			ta.urlArgs.put(E3BimApiResourcePath.MDF_DOMAIN_NAME, "ArK");
			ta.urlArgs.put(E3BimApiResourcePath.MDF_MODEL_NAME, testModelName);
			ta.urlArgs.put(E3BimApiResourcePath.MDF_MODEL_TYPE,"IFC2X3");

			if(testlib.useWebService()) {
				ta.urlArgs.put(E3BimApiResourcePath.MUF_MODEL_IS_EXTERNAL,"false");
				ta.set_file_input(testlib.inputPath + "/0000-Referansebygg.ifc");
			} else {
				ta.urlArgs.put(E3BimApiResourcePath.MUF_MODEL_IS_EXTERNAL,"true");
				ta.urlArgs.put(E3BimApiResourcePath.MUF_MODEL_CONTENT,testlib.inputPath + "/0000-Referansebygg.ifc");
			}
			
			result = this.runBimApiService(ta);
			jresult = new JSONArray(result);
			JSONObject jargs = jresult.getJSONObject(0);
			model_guid = jargs.getString("model_id");
			ta.setModelGuid(model_guid);
			log(E3Logger.DEBUG,"Loaded test model name: " + testModelName);
		} else {
			log(E3Logger.DEBUG,"Found test model name: " + testModelName);
		}
		return model_guid;
	}


	public int deleteAllTestModels1(String testModelName) throws Exception { 
		ArrayList<E3TestArgs> toDelete = new ArrayList<E3TestArgs>();
		E3TestArgs ta = BimApiArgs();
		String result = this.runBimApiService(ta);
		JSONArray jresult = new JSONArray(result);
		for(int i0 = 0; i0 < jresult.length(); i0++) {
			JSONObject md = jresult.getJSONObject(i0);
			String model_name = md.getJSONObject(E3BimApiResourcePath.MUF_MODEL_META_DATA).getString(E3BimApiResourcePath.MDF_MODEL_NAME);
			if (model_name.equals(testModelName)) {
				ta = BimApiArgs();
				ta.restOp = "DELETE";
				ta.urlArgs = null;
				ta.bodyArgs = null;				
				ta.setModelGuid(md.getJSONObject(E3BimApiResourcePath.MUF_MODEL_META_DATA).getString(E3BimApiResourcePath.MDF_MODEL_ID));
				//ta.url = "/eee-repos/0.5/models/" + ta.model_guid;
				toDelete.add(ta);
			}
		}
		for(E3TestArgs t2 : toDelete){
			ta.restOp = "DELETE";
			result = this.runBimApiService(t2);
		}
		log(E3Logger.DEBUG,"Deleted " + toDelete.size() + " test models name: " + testModelName);
		return toDelete.size();
	}
	
}
