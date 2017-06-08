package edmws.bimapi.test;

import static org.junit.Assert.assertTrue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

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


//import edmws.webapp.servlets.E3AccessUtil;
import edmws.webapp.servlets.E3BimApiActions;
import edmws.webapp.servlets.E3BimApiResourcePath;
import edmws.webapp.servlets.E3Logger;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class E3Tests01 extends E3Tests00 {

	private static boolean skipInit = false;

	@Rule public TestName mCurrentTestName = new TestName();

	private String getQualifiedTestName() {
		return (	this.getClass().getName()
				+ "_" 
				+ this.mCurrentTestName.getMethodName()
				);
	}

	protected static final String BASE_PROJECTS_URL = "/eee-repos/0.5/projects";
	protected static final String BASE_DOMAINS_URL = "/eee-repos/0.5/domains";

	private static final String TEST_MODEL_NAME = "E3Tests01";
	private static final String TEST_MODEL_NAME_NEW = "E3Tests01_new";

	
	


	private static E3Logger logger = E3Logger.getLogger(E3Tests01.class.getName());
	
	private static void log(int severity,String msg) {
		logger.log(severity, msg);		
	}

	
	@BeforeClass
	public static void beforeClass() {
		initTestClass();
		testlib.initTest(null);
		if(!skipInit) {
			// since this test unit test the basic functuion used for deleting models, just neglect errors
			try {
				E3TestUtils.createE3TestUtils(testlib).deleteAllTestModels1(TEST_MODEL_NAME);
				E3TestUtils.createE3TestUtils(testlib).deleteAllTestModels1(TEST_MODEL_NAME_NEW);
			} catch (Exception e) {}
		}
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
		//E3RestServlet servlet = new E3RestServlet();  
		//carrier = new E3AccessUtil(servlet,null,null);
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

	
	protected String runBimApiService(E3TestArgs args,Writer wr) throws Exception {
		String result = E3TestUtils.createE3TestUtils(testlib).runE3Service("E3ReposServlet", "edmws.webapp.servlets.E3BimApiActions", args,wr);
		return result;
	}

	protected String runBimApiService(E3TestArgs args) throws Exception {
		String result = E3TestUtils.createE3TestUtils(testlib).runE3Service("E3ReposServlet", "edmws.webapp.servlets.E3BimApiActions", args,null);
		return result;
	}

	
	protected String createProject(String pName,String pDescription) throws Exception {	
		E3TestArgs ta = new E3TestArgs("POST",BASE_PROJECTS_URL);
		ta.bodyArgs = new JSONObject();
		ta.bodyArgs.put("project_name",pName);
		ta.bodyArgs.put("description",pDescription);
		String result = this.runBimApiService(ta);
		return result;
	}


	private JSONArray makeProjectList(String result) throws Exception {
		JSONArray jresult = new JSONArray();
		JSONArray list = new JSONArray(result);
		for(int i0 = 0; i0 < list.length(); i0++) {
			JSONObject m1 = list.getJSONObject(i0);
			JSONObject m2 = m1.optJSONObject("project_meta_data");
			m1 = (null != m2)? m2 : m1;
			jresult.put(m1);
		}
		return jresult;
	}

	private JSONArray makeProjectList() throws Exception {
		E3TestArgs ta = new E3TestArgs("GET",BASE_PROJECTS_URL);
		String result = this.runBimApiService(ta);
		return this.makeProjectList(result);
/*		
		JSONArray jresult = new JSONArray();
		JSONArray list = new JSONArray(result);
		for(int i0 = 0; i0 < list.length(); i0++) {
			JSONObject m1 = list.getJSONObject(i0);
			JSONObject m2 = m1.optJSONObject("project_meta_data");
			m1 = (null != m2)? m2 : m1;
			jresult.put(m1);
		}
		return jresult;
*/		
	}

	
	String getProjectGuidFromName(String pName) throws Exception{
		String pGuid = null;
		JSONArray projects = this.makeProjectList();
		for (int i0 = 0; i0 < projects.length(); i0++) {
			JSONObject po = projects.getJSONObject(i0);
			if (po.getString("project_name").equals(pName)) {
				pGuid = po.getString("project_id");
				break;
			}
		}
		return pGuid;
	}

	
	private String deleteProjectByGuid(String pGuid) throws Exception {	
		E3TestArgs ta = new E3TestArgs("DELETE",BASE_PROJECTS_URL + "/" + pGuid );
		String result = this.runBimApiService(ta);
		return result;
	}




	
	@Test
	public void T01JSONListProjects() throws Exception 
	{
		try {
			//this.initCarrier(testName);
			E3TestArgs ta = new E3TestArgs("GET",BASE_PROJECTS_URL);

			String result = this.runBimApiService(ta);
			log(E3Logger.DEBUG,result);
			
			JSONArray jresult = new JSONArray(result);
			int count = jresult.length();
			assertTrue("Did not find any projects:(",(count > 0));
			
			String refpName = IE3TestBase.TEST_PROJECT_NAME_0;
			String refpGuid = null;
			JSONArray jprojects = this.makeProjectList(result); 
			for (int i0 = 0 ; i0 < jprojects.length(); i0++) {
				JSONObject jp = jprojects.optJSONObject(i0);
				log(E3Logger.DEBUG,"Project:" + jp.toString());
				if(refpName.equals(jp.opt("project_name"))) {
					refpGuid = jp.optString("project_id");
				}
			}
			assertTrue("testdata ref project not found:" + refpName,(refpGuid != null));
			assertTrue("Illegal GUID format",refpGuid.length() > 4);
			
			log(E3Logger.INFO,"..." + getQualifiedTestName() + " completed successfully");
		}
		catch(Exception ex)	{
			log(E3Logger.ERROR,"..." + getQualifiedTestName() + " completed with error(s)");
			testlib.writeTrace(ex.toString());
			throw ex;
		}
	}


	@Test
	public void T02JSONCreateProject() throws Exception 
	{
		try {
			String pName = IE3TestBase.TEST_PROJECT_NAME_2;
			String pDescription = mCurrentTestName.toString();
			
			String pGuid = this.getProjectGuidFromName(pName);
			if(pGuid != null) {
				this.deleteProjectByGuid(pGuid);
			}
			
			String result = this.createProject(pName, pDescription);
			log(E3Logger.DEBUG,"Response:" + result);
			
			pGuid = this.getProjectGuidFromName(pName);
			assertTrue("Test project not created (visibly)", (pGuid != null));			
			
			JSONArray jprojects = this.makeProjectList(result);
			assertTrue("Response was NOT exactly one project",jprojects.length() == 1);
			assertTrue("Wrong name returned: ", jprojects.getJSONObject(0).getString("project_name").equals(pName));
			assertTrue("Wrong description returned: ", jprojects.getJSONObject(0).getString("description").equals(pDescription));
		
			
			log(E3Logger.INFO,"..." + getQualifiedTestName() + " completed successfully");
		}
		catch(Exception ex)	{
			log(E3Logger.ERROR,"..." + getQualifiedTestName() + " completed with error(s)");
			testlib.writeTrace(ex.toString());
			throw ex;
		}
	}
	
	@Test
	public void T03JSONDeleteProject() throws Exception 
	{
		try {
			String pName = IE3TestBase.TEST_PROJECT_NAME_2;
			String pGuid = this.getProjectGuidFromName(pName);
			if(pGuid == null) {
				this.createProject(pName, this.mCurrentTestName.toString());
				pGuid = this.getProjectGuidFromName(pName);
			}
			String result = this.deleteProjectByGuid(pGuid);
			log(E3Logger.DEBUG,"Response:" + result);
			
			JSONArray jprojects = this.makeProjectList(result);
			assertTrue("Response was NOT exactly one project",jprojects.length() == 1);
			assertTrue("Wrong name returned: ", jprojects.getJSONObject(0).getString("project_name").equals(pName));
			assertTrue("Wrong guid returned: ", jprojects.getJSONObject(0).getString("project_id").equals(pGuid));
		
			
			log(E3Logger.INFO,"..." + getQualifiedTestName() + " completed successfully");
		}
		catch(Exception ex)	{
			log(E3Logger.ERROR,"..." + getQualifiedTestName() + " completed with error(s)");
			testlib.writeTrace(ex.toString());
			throw ex;
		}
	}
	
	@Test
	public void T11JSONListDomains() throws Exception 
	{
		try {
			//this.initCarrier(testName);
			E3TestArgs ta = new E3TestArgs("GET",BASE_DOMAINS_URL);

			String result = this.runBimApiService(ta);
			log(E3Logger.DEBUG,result);
			
			JSONArray jresult = new JSONArray(result);
			int count = jresult.length();
			assertTrue("Did not find enough projects:(",(count > 0));
			String guid = jresult.getJSONObject(0).getJSONObject("domain_meta_data").getString("domain_id");
			
			assertTrue("Illegal GUID format",guid.length() > 4);
			
			log(E3Logger.INFO,"..." + getQualifiedTestName() + " completed successfully");
		}
		catch(Exception ex)	{
			log(E3Logger.ERROR,"..." + getQualifiedTestName() + " completed with error(s)");
			testlib.writeTrace(ex.toString());
			throw ex;
		}
	}

	
	
	private JSONArray makeModelList(String result) throws Exception {
		JSONArray jresult = new JSONArray();
		JSONArray list = new JSONArray(result);
		for(int i0 = 0; i0 < list.length(); i0++) {
			JSONObject m1 = list.getJSONObject(i0);
			JSONObject m2 = m1.optJSONObject("model_meta_data");
			m1 = (null != m2)? m2 : m1;
			jresult.put(m1);
		}
		return jresult;
	}

	protected String getModelGuidFromName(String mName) throws Exception {
		String mGuid = null;
		E3TestArgs ta = new E3TestArgs("GET",IE3TestBase.BIMAPI_MODELS_URL);
		String result = this.runBimApiService(ta);
		JSONArray jresult = new JSONArray(result);
		for(int i0 = 0; i0 < jresult.length(); i0++) {
			JSONObject md = jresult.getJSONObject(i0).getJSONObject("model_meta_data");
			String mn = md.getString("model_name");
			if (mn.equals(mName)) {
				mGuid = md.getString("model_id"); 
			}
		}
		return mGuid;
	}
	

	
	protected String createModel(String projectName,String mName, String mDescription) throws Exception 
	{
			E3TestArgs ta = new E3TestArgs("POST",IE3TestBase.BIMAPI_MODELS_URL);
			ta.bodyArgs = new JSONObject();
			ta.bodyArgs.put("project_name",  projectName);
			ta.bodyArgs.put("domain_name", "HVAC");
			ta.bodyArgs.put("model_name", mName);
			ta.bodyArgs.put("model_type","IFC2X3");
			ta.bodyArgs.put("description",mDescription);
			String result = this.runBimApiService(ta);
			return result;
	}
	
	protected String createModel(String mName, String mDescription) throws Exception 
	{
		return this.createModel(IE3TestBase.TEST_PROJECT_NAME_0, mName, mDescription);
	}
	
	protected String deleteModelByGuid(String mGuid) throws Exception	
	{		
		E3TestArgs ta = new E3TestArgs("DELETE",IE3TestBase.BIMAPI_MODELS_URL);
		ta.setModelGuid(mGuid);
		String result = this.runBimApiService(ta);
		return result;
	}

	public void DownloadModelToFile(String modelName,String filename) throws Exception 
	{
		String mGuid = this.getModelGuidFromName(modelName);
		E3TestArgs ta = new E3TestArgs("GET",IE3TestBase.BIMAPI_MODELS_URL + "/" + mGuid);
	    File outFile = new File(filename);
	    BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
		@SuppressWarnings("unused")
		String result = this.runBimApiService(ta,writer);
		log(E3Logger.DEBUG,"   completed writing to file " + filename);			
	    writer.close();						
	}
	
	
	protected String uploadModel(String projectName, String domainName,String schemaName,String modelName,String description,String filename) throws Exception 
	{
		E3TestArgs ta = new E3TestArgs("POST",IE3TestBase.BIMAPI_MODELS_URL);
		ta.urlArgs = new JSONObject();
		ta.urlArgs.put(E3BimApiResourcePath.MDF_PROJECT_NAME, projectName);
		ta.urlArgs.put(E3BimApiResourcePath.MDF_DOMAIN_NAME, domainName);
		ta.urlArgs.put(E3BimApiResourcePath.MDF_MODEL_NAME, modelName);
		ta.urlArgs.put(E3BimApiResourcePath.MDF_MODEL_TYPE,schemaName);
		ta.urlArgs.put(E3BimApiResourcePath.MDF_DESCRIPTION,description);
		if(testlib.useWebService()) {
			ta.urlArgs.put(E3BimApiResourcePath.MUF_MODEL_IS_EXTERNAL,"false");
			ta.set_file_input(filename);
		} else {
			ta.urlArgs.put(E3BimApiResourcePath.MUF_MODEL_IS_EXTERNAL,"true");
			ta.urlArgs.put(E3BimApiResourcePath.MUF_MODEL_CONTENT,filename);
		}
		String result = this.runBimApiService(ta);
		return result;
	}
	
	
	protected String uploadModelIFC2x3(String projectName, String domainName,String modelName,String description,String filename) throws Exception 
	{
		return this.uploadModel(projectName, domainName,"IFC2x3", modelName, description, filename);
	}
	
	protected String uploadModelIFC4(String projectName, String domainName,String modelName,String description,String filename) throws Exception 
	{
		return this.uploadModel(projectName, domainName,"IFC4", modelName, description, filename);
	}
	
	
	@Test
	public void T21JSONListModels() throws Exception 
	{
		try {
			//this.initCarrier(testName);
			E3TestArgs ta = new E3TestArgs("GET",IE3TestBase.BIMAPI_MODELS_URL);

			String result = this.runBimApiService(ta);
			log(E3Logger.DEBUG,result);
			
			JSONArray jresult = new JSONArray(result);
			int count = jresult.length();
			assertTrue("Did not find any models :(",(count > 0));
			String guid = jresult.getJSONObject(0).getJSONObject("model_meta_data").getString("model_id");
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
	public void T22JSONDownloadModel() throws Exception 
	{
		String mName = TEST_MODEL_NAME;
		try {
			String mGuid = this.getModelGuidFromName(mName);
			if(mGuid == null) {
				this.createModel(mName, this.mCurrentTestName.toString());
				mGuid = this.getModelGuidFromName(mName);
			}
			E3TestArgs ta = new E3TestArgs("GET",IE3TestBase.BIMAPI_MODELS_URL + "/" + mGuid);
			String result = this.runBimApiService(ta);
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
	public void T23JSONCreateModel() throws Exception 
	{
		try {
			
			// check if ref project is there, create if not present (necessary for eeE server)
			String pName = IE3TestBase.TEST_PROJECT_NAME_0;
			String pGuid = this.getProjectGuidFromName(pName);
			if (pGuid == null) this.createProject(pName, "Created in " + getQualifiedTestName());
			
			
			E3TestArgs ta = new E3TestArgs("POST",IE3TestBase.BIMAPI_MODELS_URL);
			ta.bodyArgs = new JSONObject();
			ta.bodyArgs.put(E3BimApiResourcePath.MDF_PROJECT_NAME,  IE3TestBase.TEST_PROJECT_NAME_0);
			ta.bodyArgs.put(E3BimApiResourcePath.MDF_DOMAIN_NAME, "HVAC");
			ta.bodyArgs.put(E3BimApiResourcePath.MDF_MODEL_NAME, TEST_MODEL_NAME_NEW);
			ta.bodyArgs.put(E3BimApiResourcePath.MDF_MODEL_TYPE,"IFC2X3");
			ta.bodyArgs.put(E3BimApiResourcePath.MDF_DESCRIPTION,"Create general test model in test " + getQualifiedTestName());
			String result = this.runBimApiService(ta);
			JSONArray jresult = this.makeModelList(result);
			
			assertTrue("Return was not 1 model",jresult.length() == 1);
			JSONObject md = jresult.getJSONObject(0);
			assertMdFieldEqual(md,ta.bodyArgs,E3BimApiResourcePath.MDF_DESCRIPTION);
			assertMdFieldEqual(md,ta.bodyArgs,E3BimApiResourcePath.MDF_DOMAIN_NAME);
			assertMdFieldEqual(md,ta.bodyArgs,E3BimApiResourcePath.MDF_MODEL_NAME);
			assertMdFieldEqual(md,ta.bodyArgs,E3BimApiResourcePath.MDF_MODEL_TYPE);
			
			try {
				result = this.runBimApiService(ta);
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
	public void T24JSONDeleteModel() throws Exception 
	{
		String mName = TEST_MODEL_NAME_NEW;
		try {
			String mGuid = this.getModelGuidFromName(mName);
			if(mGuid == null) {
				this.createModel(mName, this.mCurrentTestName.toString());
				mGuid = this.getModelGuidFromName(mName);
			}
			E3TestArgs ta = new E3TestArgs("DELETE",IE3TestBase.BIMAPI_MODELS_URL + "/" + mGuid);
			String result = this.runBimApiService(ta);
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
	

	@Test
	public void T25JSONUploadModel() throws Exception 
	{
		try {
			String filename = testlib.getInputPathIfc4()+ "/testbase/ED_Arch_A3F1WD06.ifc";
			String result = this.uploadModelIFC4(IE3TestBase.TEST_PROJECT_NAME_0,"test", TEST_MODEL_NAME, this.mCurrentTestName.toString(),filename);

			JSONArray jresult = this.makeModelList(result);
			assertTrue("Return was not 1 model",jresult.length() == 1);

			log(E3Logger.INFO,"..." + getQualifiedTestName() + "completed successfully");
		}
		catch(Exception ex)	{
			log(E3Logger.ERROR,"..." + getQualifiedTestName() + "completed with error(s)");
			testlib.writeTrace(ex.toString());
			throw ex;
		}
	}

	@Test
	public void T26JSONCreateModelCIB() throws Exception 
	{
		String mName = "ArchitecturalTestModel";
		String mGuid = this.getModelGuidFromName(mName);
		if(mGuid != null) this.deleteModelByGuid(mGuid);
		try {
			E3TestArgs ta = new E3TestArgs("POST",IE3TestBase.BIMAPI_MODELS_URL);
			ta.bodyArgs = new JSONObject();
			ta.bodyArgs.put(E3BimApiResourcePath.MDF_PROJECT_NAME, IE3TestBase.TEST_PROJECT_NAME_0);
			ta.bodyArgs.put(E3BimApiResourcePath.MDF_DOMAIN_NAME, "Architecture");
			ta.bodyArgs.put(E3BimApiResourcePath.MDF_MODEL_NAME, mName);
			ta.bodyArgs.put(E3BimApiResourcePath.MDF_MODEL_TYPE,"IFC2X3");
			ta.bodyArgs.put(E3BimApiResourcePath.MDF_DESCRIPTION,"Test model of ISES project");
			String result = this.runBimApiService(ta);
			JSONArray jresult = this.makeModelList(result);
			
			assertTrue("Return was not 1 model",jresult.length() == 1);
			JSONObject md = jresult.getJSONObject(0);
			assertMdFieldEqual(md,ta.bodyArgs,E3BimApiResourcePath.MDF_DESCRIPTION);
			assertMdFieldEqual(md,ta.bodyArgs,E3BimApiResourcePath.MDF_DOMAIN_NAME);
			assertMdFieldEqual(md,ta.bodyArgs,E3BimApiResourcePath.MDF_MODEL_NAME);
			assertMdFieldEqual(md,ta.bodyArgs,E3BimApiResourcePath.MDF_MODEL_TYPE);
		
			try {
				result = this.runBimApiService(ta);
				assertTrue("Did not find duplicate",result.contains("already exists")); 
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
	public void T27JSONMultipartUPload() throws Exception 
	{
		try {
			String filename = testlib.getInputPathIfc4()+ "/testbase/ED_Arch_A3F1WD06.ifc";
			String projectName = IE3TestBase.TEST_PROJECT_NAME_0;
			String modelName = TEST_MODEL_NAME;
			String description = this.mCurrentTestName.toString();
			
			
			E3TestArgs ta = new E3TestArgs("POST",IE3TestBase.BIMAPI_MODELS_URL);
			ta.bodyArgs= new JSONObject();
			ta.bodyArgs.put(E3BimApiResourcePath.MDF_PROJECT_NAME, projectName);
			ta.bodyArgs.put(E3BimApiResourcePath.MDF_DOMAIN_NAME, "ArK");
			ta.bodyArgs.put(E3BimApiResourcePath.MDF_MODEL_NAME, modelName);
			ta.bodyArgs.put(E3BimApiResourcePath.MDF_MODEL_TYPE,"IFC2X3");
			ta.bodyArgs.put(E3BimApiResourcePath.MDF_DESCRIPTION,description);
			if(testlib.useWebService()) {
				ta.bodyArgs.put(E3BimApiResourcePath.MUF_MODEL_IS_EXTERNAL,"false");
				ta.set_file_input(filename);
			} else {
				ta.bodyArgs.put(E3BimApiResourcePath.MUF_MODEL_IS_EXTERNAL,"true");
				ta.bodyArgs.put(E3BimApiResourcePath.MUF_MODEL_CONTENT,filename);
			}
			String result = this.runBimApiService(ta);
			
			JSONArray jresult = this.makeModelList(result);
			assertTrue("Return was not 1 model",jresult.length() == 1);
			log(E3Logger.INFO,"..." + getQualifiedTestName() + "completed successfully");
		}
		catch(Exception ex)	{
			log(E3Logger.ERROR,"..." + getQualifiedTestName() + "completed with error(s)");
			testlib.writeTrace(ex.toString());
			throw ex;
		}
	}
	
}
