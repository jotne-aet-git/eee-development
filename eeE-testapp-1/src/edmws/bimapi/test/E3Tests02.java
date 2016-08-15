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
import edmws.webapp.servlets.E3IfcApiResourcePath;
import edmws.webapp.servlets.E3Logger;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class E3Tests02 extends E3Tests00{
	
	private static boolean skipInit = true;

	@Rule public TestName mCurrentTestName = new TestName();

	private String getQualifiedTestName() {
		return (	this.getClass().getName()
				+ "_" 
				+ this.mCurrentTestName.getMethodName()
				);
	}

	private static final String TEST_MODEL_NAME = "E3Tests02";
	private static final String TEST_MODEL_NAME_2 = "E3Tests02_Model2";
	private static final String BASE_URL = "/ifc-api/0.5/" + E3IfcApiResourcePath.MR_IFCMODEL;
	

	


	private static E3Logger logger = E3Logger.getLogger(E3Tests02.class.getName());
	
	private static void log(int severity,String msg) {
		logger.log(severity, msg);
	}

	
	@BeforeClass
	public static void beforeClass() throws Exception {
		initTestClass();
		testlib.initTest(null);
		if(!skipInit) {
			E3TestUtils.createE3TestUtils(testlib).deleteAllTestModels1(TEST_MODEL_NAME);
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
		this.deleteTestModels(this.getQualifiedTestName());
//		E3RestServlet servlet = new E3RestServlet();  
//		carrier = new E3AccessUtil(servlet,null,null);
	}

	@After
	public void afterEachTest() throws Exception {
		testlib.stopTrace();
	}

	private String runIfcApiService(E3TestArgs args,Writer wr) throws Exception {
		String result = E3TestUtils.createE3TestUtils(testlib).runE3Service("E3IfcApiServlet", "edmws.webapp.servlets.E3IfcApiActions", args,wr);
		return result;
	}

	private String runIfcApiService(E3TestArgs args) throws Exception {
		String result = E3TestUtils.createE3TestUtils(testlib).runE3Service("E3IfcApiServlet", "edmws.webapp.servlets.E3IfcApiActions", args,null);
		return result;
	}


	private void deleteTestModels(String prefix) throws Exception{
		E3TestUtils.createE3TestUtils(testlib).deleteAllTestModels1(prefix);
	}

	public String loadTestModel(String testModelName) throws Exception{
		E3Tests01 helper = new E3Tests01();
		String guid = helper.getModelGuidFromName(testModelName);
		if (guid == null) {
			String projectName = IE3TestBase.TEST_PROJECT_NAME_0; // "FM";
			String filename = testlib.getInputPath() + "/0000-Referansebygg.ifc";
			helper.uploadModelIFC2x3(projectName, "test",testModelName, "testModel", filename);
		}
		guid = helper.getModelGuidFromName(testModelName);
		return guid;
	}

		
	public String loadCaseModelIFC4(String domainName,String testModelName,String filename,String description) throws Exception{
		E3Tests01 helper = new E3Tests01();
		String projectName = IE3TestBase.CASE_PROJECT_NAME_1;
		String pGuid = helper.getProjectGuidFromName(projectName);
		if (pGuid == null) helper.createProject(projectName, "Case-container");
		String guid = helper.getModelGuidFromName(testModelName);
		if(guid != null) {
			helper.deleteModelByGuid(guid);
			guid = null;
		}
		if (guid == null) {
			guid = helper.uploadModelIFC4(projectName, domainName,testModelName, "testModel", filename);
		}
		guid = helper.getModelGuidFromName(testModelName);
		return guid;
	}
	
	
	@Test
	public void T01JSONListIfcTypes() throws Exception 
	{
		try {
			String ifcType = "ifcbuildingstorey";
			String model_guid =  loadTestModel(TEST_MODEL_NAME);
			E3TestArgs ta = new E3TestArgs("GET",BASE_URL);
			ta.setModelGuid(model_guid);
			ta.setIfcType(ifcType ,null);
			log(E3Logger.DEBUG,"looking for '" + ifcType + "' in model with guid:" + model_guid);
			String result = this.runIfcApiService(ta);
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
			jresult = new JSONArray(this.runIfcApiService(ta));
			assertTrue("Didn't find singlestorey",jresult.length() == 1);
			log(E3Logger.DEBUG,"--- response:" + jresult.toString());
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
			String model_guid = loadTestModel(TEST_MODEL_NAME);
			E3TestArgs ta = new E3TestArgs("GET",BASE_URL);
			ta.setModelGuid(model_guid);
			ta.setIfcType("ifcspace","2MU61$8Yb0MvanC6CZFwZ0"); // TODO better id for test object
			ta.setIfcSubType("ifcproperty");
			JSONArray jresult = new JSONArray(this.runIfcApiService(ta));
			assertTrue("Didn't find any properties",jresult.length() > 0);
			log(E3Logger.DEBUG,"--- response:" + jresult.toString());
			log(E3Logger.INFO,"..." + getQualifiedTestName() + " completed successfully");
		}
		catch(Exception ex)	{
			log(E3Logger.ERROR,"..." + getQualifiedTestName() + " completed with error(s):" + ex.toString());
			testlib.writeTrace(ex.toString());
			throw ex;
		}
	}

	@Test
	public void T11JSONMerge01() throws Exception 
	{
		try {
			String model_guid1 = loadTestModel(TEST_MODEL_NAME);
			String model_guid2 = loadTestModel(TEST_MODEL_NAME_2);
			E3TestArgs ta = new E3TestArgs("POST",BASE_URL + "/" + model_guid1 + "/merge");
			ta.bodyArgs = new JSONObject();
			
			JSONArray jSources = new JSONArray().put(new JSONObject().put("model_id", model_guid2));			
			ta.bodyArgs.put("source_models", jSources);
			
			JSONObject jTarget = new JSONObject().put("model_id",model_guid1);
			ta.bodyArgs.put("target_model", jTarget);

			JSONArray jArguments= new JSONArray();
			jArguments.put(new JSONObject().put("merge_into_new_version",true));			
			ta.bodyArgs.put("arguments", jArguments);

			JSONArray jresult = new JSONArray(this.runIfcApiService(ta));
			assertTrue("Nothing returned...",jresult.length() > 0);
			
			log(E3Logger.DEBUG,"--- response:" + jresult.toString(2));
			log(E3Logger.INFO,"..." + getQualifiedTestName() + " completed successfully");
		}
		catch(Exception ex)	{
			log(E3Logger.ERROR,"..." + getQualifiedTestName() + " completed with error(s):" + ex.toString());
			testlib.writeTrace(ex.toString());
			throw ex;
		}
	}

	

	private final String EEE_ED_A3_ARCH_Filename =  "O:/edm_dev/EDMeXtensions/test/eee-data/ED_ARCH_IFC_A3.ifc";
	private final String EEE_ED_A3_HVAC_Filename_nospaces =  "O:/edm_dev/EDMeXtensions/test/eee-data/ED_HVAC_IFC_A3.ifc";
	private final String EEE_ED_A3_HVAC_Filename_withspaces =  "O:/edm_dev/EDMeXtensions/test/eee-data/ED_HVAC_IFC_A3_Including spaces.ifc";
	private final String EEE_ED_A3_main_Filename =  "O:/edm_dev/EDMeXtensions/test/eee-data/ED_main_A3_";

	public void DownloadModelToFile(String modelName,String filename) throws Exception 
	{
		E3Tests01 helper = new E3Tests01();
		String mGuid = helper.getModelGuidFromName(modelName);
		E3TestArgs ta = new E3TestArgs("GET",IE3TestBase.BIMAPI_MODELS_URL + "/" + mGuid);
	    File outFile = new File(filename);
	    BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
		String result = helper.runBimApiService(ta,writer);
		log(E3Logger.DEBUG,"   completed writing to file " + filename);			
	    writer.close();						
	}

	
	@Test
	public void T12Merge_EDA3HvacNospaces() throws Exception 
	{
		try {
			String model_main_guid = loadCaseModelIFC4("All",this.getQualifiedTestName() + "_main",EEE_ED_A3_HVAC_Filename_nospaces,"eeE Early Design case main");
			String model_arch_guid = loadCaseModelIFC4("Arch",this.getQualifiedTestName() + "_arch",EEE_ED_A3_ARCH_Filename,"eeE Early Design case Arch");
			String model_hvac_guid = loadCaseModelIFC4("HVAC",this.getQualifiedTestName() + "_hvac",EEE_ED_A3_HVAC_Filename_nospaces,"eeE Early Design case HVAC");
			E3TestArgs ta = new E3TestArgs("POST",BASE_URL + "/" + model_main_guid + "/merge");
			ta.bodyArgs = new JSONObject();
			
			JSONArray jSources = new JSONArray().put(new JSONObject().put("model_id", model_arch_guid));			
			ta.bodyArgs.put("source_models", jSources);
			
			JSONObject jTarget = new JSONObject().put("model_id",model_main_guid);
			ta.bodyArgs.put("target_model", jTarget);

			JSONArray jArguments= new JSONArray();
			jArguments.put(new JSONObject().put("merge_into_new_version",true));			
			ta.bodyArgs.put("arguments", jArguments);

			JSONArray jresult = new JSONArray(this.runIfcApiService(ta));
			assertTrue("Nothing returned...",jresult.length() > 0);
			
			log(E3Logger.DEBUG,"--- response:" + jresult.toString(2));
			this.DownloadModelToFile(this.getQualifiedTestName() + "_main",EEE_ED_A3_main_Filename + this.getQualifiedTestName() + ".ifc");
			
			log(E3Logger.INFO,"..." + getQualifiedTestName() + " completed successfully");
		}
		catch(Exception ex)	{
			log(E3Logger.ERROR,"..." + getQualifiedTestName() + " completed with error(s):" + ex.toString());
			testlib.writeTrace(ex.toString());
			throw ex;
		}
	}

	
	@Test
	public void T13Merge_EDA3HvacWithSpaces() throws Exception 
	{
		try {
			String model_main_guid = loadCaseModelIFC4("All",this.getQualifiedTestName() + "_main",EEE_ED_A3_HVAC_Filename_withspaces,"eeE Early Design case main");
			String model_arch_guid = loadCaseModelIFC4("Arch",this.getQualifiedTestName() + "_arch",EEE_ED_A3_ARCH_Filename,"eeE Early Design case Arch");
			String model_hvac_guid = loadCaseModelIFC4("HVAC",this.getQualifiedTestName() + "_hvac",EEE_ED_A3_HVAC_Filename_withspaces,"eeE Early Design case HVAC");
			E3TestArgs ta = new E3TestArgs("POST",BASE_URL + "/" + model_main_guid + "/merge");
			ta.bodyArgs = new JSONObject();
			
			JSONArray jSources = new JSONArray().put(new JSONObject().put("model_id", model_arch_guid));			
			ta.bodyArgs.put("source_models", jSources);
			
			JSONObject jTarget = new JSONObject().put("model_id",model_main_guid);
			ta.bodyArgs.put("target_model", jTarget);

			JSONArray jArguments= new JSONArray();
			jArguments.put(new JSONObject().put("merge_into_new_version",true));			
			ta.bodyArgs.put("arguments", jArguments);

			JSONArray jresult = new JSONArray(this.runIfcApiService(ta));
			assertTrue("Nothing returned...",jresult.length() > 0);
			
			log(E3Logger.DEBUG,"--- response:" + jresult.toString(2));
			this.DownloadModelToFile(this.getQualifiedTestName() + "_main",EEE_ED_A3_main_Filename + this.getQualifiedTestName() + ".ifc");
			log(E3Logger.INFO,"..." + getQualifiedTestName() + " completed successfully");
		}
		catch(Exception ex)	{
			log(E3Logger.ERROR,"..." + getQualifiedTestName() + " completed with error(s):" + ex.toString());
			testlib.writeTrace(ex.toString());
			throw ex;
		}
	}
	
	@Test
	public void T14Merge_EDA3ArchNospaces() throws Exception 
	{
		try {
			String model_main_guid = loadCaseModelIFC4("All",this.getQualifiedTestName() + "_main",EEE_ED_A3_ARCH_Filename,"eeE Early Design case main");
			String model_arch_guid = loadCaseModelIFC4("Arch",this.getQualifiedTestName() + "_arch",EEE_ED_A3_ARCH_Filename,"eeE Early Design case Arch");
			String model_hvac_guid = loadCaseModelIFC4("HVAC",this.getQualifiedTestName() + "_hvac",EEE_ED_A3_HVAC_Filename_nospaces,"eeE Early Design case HVAC");
			E3TestArgs ta = new E3TestArgs("POST",BASE_URL + "/" + model_main_guid + "/merge");
			ta.bodyArgs = new JSONObject();
			
			JSONArray jSources = new JSONArray().put(new JSONObject().put("model_id", model_hvac_guid));			
			ta.bodyArgs.put("source_models", jSources);
			
			JSONObject jTarget = new JSONObject().put("model_id",model_main_guid);
			ta.bodyArgs.put("target_model", jTarget);

			JSONArray jArguments= new JSONArray();
			jArguments.put(new JSONObject().put("merge_into_new_version",true));			
			ta.bodyArgs.put("arguments", jArguments);

			JSONArray jresult = new JSONArray(this.runIfcApiService(ta));
			assertTrue("Nothing returned...",jresult.length() > 0);
			
			log(E3Logger.DEBUG,"--- response:" + jresult.toString(2));
			this.DownloadModelToFile(this.getQualifiedTestName() + "_main",EEE_ED_A3_main_Filename + this.getQualifiedTestName() + ".ifc");
			log(E3Logger.INFO,"..." + getQualifiedTestName() + " completed successfully");
		}
		catch(Exception ex)	{
			log(E3Logger.ERROR,"..." + getQualifiedTestName() + " completed with error(s):" + ex.toString());
			testlib.writeTrace(ex.toString());
			throw ex;
		}
	}

	@Test
	public void T15Merge_EDA3ArchWithspaces() throws Exception 
	{
		try {
			String model_main_guid = loadCaseModelIFC4("All",this.getQualifiedTestName() + "_main",EEE_ED_A3_ARCH_Filename,"eeE Early Design case main");
			String model_arch_guid = loadCaseModelIFC4("Arch",this.getQualifiedTestName() + "_arch",EEE_ED_A3_ARCH_Filename,"eeE Early Design case Arch");
			String model_hvac_guid = loadCaseModelIFC4("HVAC",this.getQualifiedTestName() + "_hvac",EEE_ED_A3_HVAC_Filename_withspaces,"eeE Early Design case HVAC");
			E3TestArgs ta = new E3TestArgs("POST",BASE_URL + "/" + model_main_guid + "/merge");
			ta.bodyArgs = new JSONObject();
			
			JSONArray jSources = new JSONArray().put(new JSONObject().put("model_id", model_hvac_guid));			
			ta.bodyArgs.put("source_models", jSources);
			
			JSONObject jTarget = new JSONObject().put("model_id",model_main_guid);
			ta.bodyArgs.put("target_model", jTarget);

			JSONArray jArguments= new JSONArray();
			jArguments.put(new JSONObject().put("merge_into_new_version",true));			
			ta.bodyArgs.put("arguments", jArguments);

			JSONArray jresult = new JSONArray(this.runIfcApiService(ta));
			assertTrue("Nothing returned...",jresult.length() > 0);
			
			log(E3Logger.DEBUG,"--- response:" + jresult.toString(2));
			this.DownloadModelToFile(this.getQualifiedTestName() + "_main",EEE_ED_A3_main_Filename + this.getQualifiedTestName() + ".ifc");
			log(E3Logger.INFO,"..." + getQualifiedTestName() + " completed successfully");
		}
		catch(Exception ex)	{
			log(E3Logger.ERROR,"..." + getQualifiedTestName() + " completed with error(s):" + ex.toString());
			testlib.writeTrace(ex.toString());
			throw ex;
		}
	}

	@Test
	public void T21JSONListExtractEndpoints() throws Exception 
	{
		try {
			String model_guid1 = loadTestModel(TEST_MODEL_NAME);
			E3TestArgs ta = new E3TestArgs("GET",BASE_URL + "/" + model_guid1 + "/extract");
			ta.bodyArgs = new JSONObject();

			JSONArray jresult = new JSONArray(this.runIfcApiService(ta));
			assertTrue("Nothing returned...",jresult.length() > 0);
			
			log(E3Logger.DEBUG,"--- response:" + jresult.toString(2));
			log(E3Logger.INFO,"..." + getQualifiedTestName() + " completed successfully");
		}
		catch(Exception ex)	{
			log(E3Logger.ERROR,"..." + getQualifiedTestName() + " completed with error(s):" + ex.toString());
			testlib.writeTrace(ex.toString());
			throw ex;
		}
	}
}
