package edmws.bimapi.test;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runners.MethodSorters;

import edm.edom3.EDMInterface;
import edmws.webapp.servlets.E3AccessUtil;
import edmws.webapp.servlets.E3BimApiActions;
import edmws.webapp.servlets.E3IfcApiActions;
import edmws.webapp.servlets.E3IfcApiResourcePath;
import edmws.webapp.servlets.E3Logger;
import edmws.webapp.servlets.E3RestServlet;
import edmws.webapp.servlets.IE3ResourcePath;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class E3Tests02 {
	
	private static boolean skipInit = true;

	@Rule public TestName mCurrentTestName = new TestName();

	private String getQualifiedTestName() {
		return (	this.getClass().getName()
				+ "_" 
				+ this.mCurrentTestName.getMethodName()
				);
	}

	private static final String TEST_MODEL_NAME = "E3Tests02";
	private static final String BASE_URL = "/ifc-api/0.5/" + E3IfcApiResourcePath.MR_IFCMODEL;
	

	
	private static E3Logger logger = E3Logger.getLogger(E3Tests02.class.getName());
	private static E3TestBase testlib = new E3TestBase();
	
	private static void log(int severity,String msg) {
		logger.log(severity, msg);
	}

	private E3AccessUtil carrier;

	
	@BeforeClass
	public static void beforeClass() throws Exception {
		testlib.initWebServiceTest(null);
		E3RestServlet servlet = new E3RestServlet();  
		E3AccessUtil carrier = new E3AccessUtil(servlet,null,null);
		if(!skipInit) {
			new E3TestUtils(testlib,carrier).deleteAllTestModels1(TEST_MODEL_NAME);
		}
	}

	@AfterClass
	public static void afterClass() throws Exception {		
		testlib.cleanup();
	}

	@Before
	public void initCarrier() throws Exception {
		E3BimApiActions.setLogFileRoot(testlib.getOutputPath());
		E3BimApiActions.setLogName(getQualifiedTestName());
		testlib.startTrace(getQualifiedTestName());
		E3RestServlet servlet = new E3RestServlet();  
		carrier = new E3AccessUtil(servlet,null,null);
	}

	@After
	public void afterEachTest() throws Exception {
		testlib.stopTrace();
	}

	
	private void assertMdFieldEqual(JSONObject m1, JSONObject m2,String field) {
		String msg = "Field " + field + ":" + m1.get(field).toString() + " <> " + m2.get(field).toString();
		assertTrue(msg, m1.get(field).equals(m2.get(field)));
	}


	private String runService(E3TestArgs args) throws Exception {
		String result = new E3TestUtils(testlib,carrier).runE3Service("E3IfcApiServlet", "edmws.webapp.servlets.E3IfcApiActions", args);
		return result;
	}

	
	@Test
	public void T01JSONListIfcTypes() throws Exception 
	{
		try {
			//this.initCarrier(testName);
			String model_guid = new E3TestUtils(testlib,carrier).loadTestModel(TEST_MODEL_NAME);
			E3TestArgs ta = new E3TestArgs("GET",BASE_URL);
			ta.setModelGuid(model_guid);
			ta.setIfcType("ifcbuildingstorey",null);
			
			String result = this.runService(ta);
			//log(E3Logger.DEBUG,result);
			JSONArray jresult = new JSONArray(result);
			assertTrue("Wrong number of stories",jresult.length() == 3);
			String guid = "none";
			for(int i0 = 0; i0 < jresult.length(); i0++) {
				JSONObject md = jresult.getJSONObject(i0);
				String url = md.getString("url");
				assertTrue("Illegal type",url.contains("/ifcbuildingstorey/"));
				guid = md.getString(E3IfcApiResourcePath.MR_IFCGUID);
			}
			ta.setIfcType("ifcbuildingstorey",guid);
			jresult = new JSONArray(this.runService(ta));
			assertTrue("Didn't find singlestorey",jresult.length() == 1);
			log(E3Logger.INFO,"..." + getQualifiedTestName() + " completed successfully");
		}
		catch(Exception ex)	{
			log(E3Logger.ERROR,"..." + getQualifiedTestName() + " completed with error(s):" + ex.toString());
			testlib.writeTrace(ex.toString());
			throw ex;
		}
	}
	
	@Test
	public void T02JSONListIfcProperties() throws Exception 
	{
		try {
			//this.initCarrier(testName);
			String model_guid = new E3TestUtils(testlib,carrier).loadTestModel(TEST_MODEL_NAME);
			E3TestArgs ta = new E3TestArgs("GET",BASE_URL);
			ta.setModelGuid(model_guid);
			ta.setIfcType("ifcspace","2MU61$8Yb0MvanC6CZFwZ0"); // TODO better id for test object
			ta.setIfcSubType("ifcproperty");
			JSONArray jresult = new JSONArray(this.runService(ta));
			assertTrue("Didn't find any properties",jresult.length() > 0);
			log(E3Logger.INFO,"..." + getQualifiedTestName() + " completed successfully");
		}
		catch(Exception ex)	{
			log(E3Logger.ERROR,"..." + getQualifiedTestName() + " completed with error(s):" + ex.toString());
			testlib.writeTrace(ex.toString());
			throw ex;
		}
	}
}
