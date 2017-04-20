package edmws.bimapi.test;

import java.io.Writer;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import edmws.webapp.servlets.E3AccessUtil;
import edmws.webapp.servlets.E3BimApiResourcePath;
import edmws.webapp.servlets.E3Logger;
import edmws.webapp.servlets.E3RestServlet;

public class E3TestUtils {


	
	private E3TestArgs BimApiArgs() {
		return new E3TestArgs("GET","/eee-repos/0.5/models");
	}

	
	private static E3Logger logger = E3Logger.getLogger(E3TestUtils.class.getName());
	private static void log(int severity,String msg) {
		logger.log(severity, msg);
	}
	
	IE3TestBase testlib;
	E3AccessUtil carrier;
	private E3TestUtils(IE3TestBase testlib,E3AccessUtil carrier) {
		this.testlib = testlib;
		this.carrier = carrier;
	}


	public static E3TestUtils createE3TestUtils(IE3TestBase testlib) throws Exception {
		E3RestServlet servlet = new E3RestServlet();  
		E3AccessUtil carrier = new E3AccessUtil(servlet,null,null);
		return new E3TestUtils(testlib,carrier);
	}
	
	
	protected String runE3Service(String servletName, String workerClassName,E3TestArgs args,Writer wr) throws Exception {
		return testlib.runE3Service(carrier, servletName, workerClassName, args,wr);
	}

	private String runBimApiService(E3TestArgs args,Writer wr) throws Exception {
		return this.runE3Service("E3ReposServlet","edmws.webapp.servlets.E3BimApiActions",args,wr); 
	}



	public int deleteAllTestModels1(String testModelName) throws Exception { 
		ArrayList<E3TestArgs> toDelete = new ArrayList<E3TestArgs>();
		E3TestArgs ta = BimApiArgs();
		String result = this.runBimApiService(ta,null);
		JSONArray jresult = new JSONArray(result);
		for(int i0 = 0; i0 < jresult.length(); i0++) {
			JSONObject md = jresult.getJSONObject(i0);
			String model_name = md.getJSONObject(E3BimApiResourcePath.MUF_MODEL_META_DATA).getString(E3BimApiResourcePath.MDF_MODEL_NAME);
			if (model_name.startsWith(testModelName)) {
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
			result = this.runBimApiService(t2,null);
		}
		log(E3Logger.DEBUG,"Deleted " + toDelete.size() + " test models name: " + testModelName);
		return toDelete.size();
	}
	
}
