package edmws.bimapi.test;

import static org.junit.Assert.assertTrue;

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
import edmws.webapp.servlets.E3ApiActions;
import edmws.webapp.servlets.E3BimApiActions;
import edmws.webapp.servlets.E3BimApiResourcePath;
import edmws.webapp.servlets.E3Constants;
import edmws.webapp.servlets.E3IfcApiActions;
import edmws.webapp.servlets.E3Logger;
import edmws.webapp.servlets.E3RestServlet;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class E3Tests01 {

	@Rule public TestName mCurrentTestName = new TestName();

	private String getQualifiedTestName() {
		return (	this.getClass().getName()
				+ "_" 
				+ this.mCurrentTestName.getMethodName()
				);
	}
	
	private static final String TEST_MODEL_NAME = "E3Tests01";
	private static final String TEST_MODEL_NAME_NEW = "E3Tests01_new";
	private static final String BASE_MODELS_URL = "/eee-repos/0.5/models";
	private static final String BASE_PROJECTS_URL = "/eee-repos/0.5/projects";
	private static final String BASE_DOMAINS_URL = "/eee-repos/0.5/domains";
	
	private static E3Logger logger = E3Logger.getLogger(E3Tests01.class.getName());
	private static E3TestBase testlib = new E3TestBase();
	
	private static void log(int severity,String msg) {
		logger.log(severity, msg);		
	}

	private E3AccessUtil carrier;

	
	@BeforeClass
	public static void beforeClass() {
		testlib.initWebServiceTest(null);
		// since this test unit test the basic functuion used for deleting models, just neglect errors
		try {
			E3RestServlet servlet = new E3RestServlet();  
			E3AccessUtil carrier = new E3AccessUtil(servlet,null,null);
			new E3TestUtils(testlib,carrier).deleteAllTestModels1(TEST_MODEL_NAME);
			new E3TestUtils(testlib,carrier).deleteAllTestModels1(TEST_MODEL_NAME_NEW);
		} catch (Exception e) {}
	}

	@AfterClass
	public static void afterClass() throws Exception {		
		testlib.cleanup();
	}

	@Before
	public void initCarrier() throws Exception {
		String testName = mCurrentTestName.getMethodName();
		E3BimApiActions.setLogFileRoot(testlib.getOutputPath());
		E3BimApiActions.setLogName(testName);
		testlib.startTrace(testName);
		E3RestServlet servlet = new E3RestServlet();  
		carrier = new E3AccessUtil(servlet,null,null);
		log(E3Logger.INFO,"...Starting test:" + testName);
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
		String result = new E3TestUtils(testlib,carrier).runE3Service("E3ReposServlet", "edmws.webapp.servlets.E3BimApiActions", args);
		return result;
	}


	private JSONArray makeModelList(String result) throws Exception {
		JSONArray jresult = new JSONArray();
		JSONArray list = new JSONArray(result);
		for(int i0 = 0; i0 < list.length(); i0++) {
			JSONObject m1 = list.getJSONObject(i0);
			JSONObject m2 = m1.optJSONObject(E3BimApiResourcePath.MUF_MODEL_META_DATA);
			m1 = (null != m2)? m2 : m1;
			jresult.put(m1);
		}
		return jresult;
	}

	
	
	
	private E3TestArgs getTestModel1() throws Exception { 
		E3TestArgs ta = new E3TestArgs("GET",BASE_MODELS_URL);
		String result = this.runService(ta);
		JSONArray jresult = new JSONArray(result);
		for(int i0 = 0; i0 < jresult.length(); i0++) {
			JSONObject md = jresult.getJSONObject(i0);
			String model_name = md.getJSONObject(E3BimApiResourcePath.MUF_MODEL_META_DATA).getString(E3BimApiResourcePath.MDF_MODEL_NAME);
			if (model_name.equals(TEST_MODEL_NAME)) {
				ta.setModelGuid(md.getJSONObject(E3BimApiResourcePath.MUF_MODEL_META_DATA).getString(E3BimApiResourcePath.MDF_MODEL_ID)); 
			}
		}
		return ta;
	}
	
	private E3TestArgs getTestModel2() throws Exception { 
		E3TestArgs ta = new E3TestArgs("GET",BASE_MODELS_URL);
		String result = this.runService(ta);
		JSONArray jresult = new JSONArray(result);
		for(int i0 = 0; i0 < jresult.length(); i0++) {
			JSONObject md = jresult.getJSONObject(i0);
			String model_name = md.getJSONObject(E3BimApiResourcePath.MUF_MODEL_META_DATA).getString(E3BimApiResourcePath.MDF_MODEL_NAME);
			if (model_name.equals(TEST_MODEL_NAME_NEW)) {
				ta.setModelGuid(md.getJSONObject(E3BimApiResourcePath.MUF_MODEL_META_DATA).getString(E3BimApiResourcePath.MDF_MODEL_ID)); 
			}
		}
		return ta;
	}

	@Test
	public void T01JSONListModels() throws Exception 
	{
		try {
			//this.initCarrier(testName);
			E3TestArgs ta = new E3TestArgs("GET",BASE_MODELS_URL);

			String result = this.runService(ta);
			log(E3Logger.DEBUG,result);
			
			JSONArray jresult = new JSONArray(result);
			int count = jresult.length();
			assertTrue("Did not find enough models :(",(count > 3));
			String guid = jresult.getJSONObject(3).getJSONObject(E3BimApiResourcePath.MUF_MODEL_META_DATA).getString(E3BimApiResourcePath.MDF_MODEL_ID);
			ta.setModelGuid(guid);
			
			assertTrue("Illegal GUID format",guid.length() > 4);
			
			log(E3Logger.INFO,"..." + getQualifiedTestName() + " completed successfully");
		}
		catch(Exception ex)	{
			log(E3Logger.ERROR,"..." + getQualifiedTestName() + " completed with error(s)");
			testlib.writeTrace(ex.toString());
			throw ex;
		}
	}

	@Test
	public void T02JSONDownloadModel() throws Exception 
	{
		try {
			E3TestArgs ta = this.getTestModel1();
			ta.urlArgs = null;
			ta.bodyArgs = null;
			ta.restOp = "GET";
			String result = this.runService(ta);
			System.out.print(result);
			log(E3Logger.INFO,"..." + getQualifiedTestName() + "completed successfully");
		}
		catch(Exception ex)	{
			log(E3Logger.ERROR,"..." + getQualifiedTestName() + "completed with error(s)");
			testlib.writeTrace(ex.toString());
			throw ex;
		}
	}

	@Test
	public void T03JSONCreateModel() throws Exception 
	{
		try {
			E3TestArgs ta = new E3TestArgs("POST",BASE_MODELS_URL);
			ta.bodyArgs = new JSONObject();
			ta.bodyArgs.put(E3BimApiResourcePath.MDF_PROJECT_NAME, "FM");
			ta.bodyArgs.put(E3BimApiResourcePath.MDF_DOMAIN_NAME, "HVAC");
			ta.bodyArgs.put(E3BimApiResourcePath.MDF_MODEL_NAME, TEST_MODEL_NAME_NEW);
			ta.bodyArgs.put(E3BimApiResourcePath.MDF_MODEL_TYPE,"IFC2X3");
			ta.bodyArgs.put(E3BimApiResourcePath.MDF_DESCRIPTION,"Create general test model in test " + getQualifiedTestName());
			String result = this.runService(ta);
			JSONArray jresult = this.makeModelList(result);
			
			assertTrue("Return was not 1 model",jresult.length() == 1);
			JSONObject md = jresult.getJSONObject(0);
			assertMdFieldEqual(md,ta.bodyArgs,E3BimApiResourcePath.MDF_DESCRIPTION);
			assertMdFieldEqual(md,ta.bodyArgs,E3BimApiResourcePath.MDF_DOMAIN_NAME);
			assertMdFieldEqual(md,ta.bodyArgs,E3BimApiResourcePath.MDF_MODEL_NAME);
			assertMdFieldEqual(md,ta.bodyArgs,E3BimApiResourcePath.MDF_MODEL_TYPE);
			
			try {
				result = this.runService(ta);
			}
			catch(Exception ex) {
				assertTrue("Duplicate name check failed",ex.toString().contains("already exists"));
			}
			
			log(E3Logger.INFO,"..." + getQualifiedTestName() + "completed successfully");
		}
		catch(Exception ex)	{
			log(E3Logger.ERROR,"..." + getQualifiedTestName() + "completed with error(s)");
			testlib.writeTrace(ex.toString());
			throw ex;
		}
	}
	
	

	@Test
	public void T04JSONDeleteModel() throws Exception 
	{
		try {
			E3TestArgs ta = this.getTestModel2();
			assertTrue("Test model 2 not found",ta.getModelGuid().length() > 0);
			ta.restOp = "DELETE";
			ta.urlArgs = null;
			ta.bodyArgs = null;
		
			String result = this.runService(ta);
			JSONArray jresult = this.makeModelList(result);
			assertTrue("Return was not 1 model",jresult.length() == 1);
			
			log(E3Logger.INFO,"..." + getQualifiedTestName() + "completed successfully");
		}
		catch(Exception ex)	{
			log(E3Logger.ERROR,"..." + getQualifiedTestName() + "completed with error(s)");
			testlib.writeTrace(ex.toString());
			throw ex;
		}
		finally {
			testlib.stopTrace();			
		}
	}
	

	@SuppressWarnings("static-access")
	@Test
	public void T05JSONUploadModel() throws Exception 
	{
		try {
			E3TestArgs ta = new E3TestArgs("POST",BASE_MODELS_URL);
			ta.urlArgs = new JSONObject();
			ta.urlArgs.put(E3BimApiResourcePath.MDF_PROJECT_NAME, "FM");
			ta.urlArgs.put(E3BimApiResourcePath.MDF_DOMAIN_NAME, "ArK");
			ta.urlArgs.put(E3BimApiResourcePath.MDF_MODEL_NAME, TEST_MODEL_NAME);
			ta.urlArgs.put(E3BimApiResourcePath.MDF_MODEL_TYPE,"IFC2X3");
			if(testlib.useWebService()) {
				ta.urlArgs.put(E3BimApiResourcePath.MUF_MODEL_IS_EXTERNAL,"false");
				ta.set_file_input(testlib.inputPath + "/0000-Referansebygg.ifc");
			} else {
				ta.urlArgs.put(E3BimApiResourcePath.MUF_MODEL_IS_EXTERNAL,"true");
				ta.urlArgs.put(E3BimApiResourcePath.MUF_MODEL_CONTENT,testlib.inputPath + "/0000-Referansebygg.ifc");
			}
			String result = this.runService(ta);
			JSONArray jresult = this.makeModelList(result);
			assertTrue("Return was not 1 model",jresult.length() == 1);
			JSONObject md = jresult.getJSONObject(0);
			assertMdFieldEqual(md,ta.urlArgs,E3BimApiResourcePath.MDF_PROJECT_NAME);
			assertMdFieldEqual(md,ta.urlArgs,E3BimApiResourcePath.MDF_DOMAIN_NAME);
			assertMdFieldEqual(md,ta.urlArgs,E3BimApiResourcePath.MDF_MODEL_NAME);
			assertMdFieldEqual(md,ta.urlArgs,E3BimApiResourcePath.MDF_MODEL_TYPE);
			log(E3Logger.INFO,"..." + getQualifiedTestName() + "completed successfully");
		}
		catch(Exception ex)	{
			log(E3Logger.ERROR,"..." + getQualifiedTestName() + "completed with error(s)");
			testlib.writeTrace(ex.toString());
			throw ex;
		}
	}


	@Test
	public void T11JSONListProjects() throws Exception 
	{
		try {
			//this.initCarrier(testName);
			E3TestArgs ta = new E3TestArgs("GET",BASE_PROJECTS_URL);

			String result = this.runService(ta);
			log(E3Logger.DEBUG,result);
			
			JSONArray jresult = new JSONArray(result);
			int count = jresult.length();
			assertTrue("Did not find enough projects:(",(count > 0));
			String guid = jresult.getJSONObject(0).getJSONObject(E3BimApiResourcePath.MUF_PROJECT_META_DATA).getString(E3BimApiResourcePath.MDF_PROJECT_ID);
			
			assertTrue("Illegal GUID format",guid.length() > 4);
			
			log(E3Logger.INFO,"..." + getQualifiedTestName() + " completed successfully");
		}
		catch(Exception ex)	{
			log(E3Logger.ERROR,"..." + getQualifiedTestName() + " completed with error(s)");
			testlib.writeTrace(ex.toString());
			throw ex;
		}
	}

	@Test
	public void T21JSONListDomains() throws Exception 
	{
		try {
			//this.initCarrier(testName);
			E3TestArgs ta = new E3TestArgs("GET",BASE_DOMAINS_URL);

			String result = this.runService(ta);
			log(E3Logger.DEBUG,result);
			
			JSONArray jresult = new JSONArray(result);
			int count = jresult.length();
			assertTrue("Did not find enough projects:(",(count > 0));
			String guid = jresult.getJSONObject(0).getJSONObject(E3BimApiResourcePath.MUF_DOMAIN_META_DATA).getString(E3BimApiResourcePath.MDF_DOMAIN_ID);
			
			assertTrue("Illegal GUID format",guid.length() > 4);
			
			log(E3Logger.INFO,"..." + getQualifiedTestName() + " completed successfully");
		}
		catch(Exception ex)	{
			log(E3Logger.ERROR,"..." + getQualifiedTestName() + " completed with error(s)");
			testlib.writeTrace(ex.toString());
			throw ex;
		}
	}
	
}
