package edmws.bimapi.test;

import static org.junit.Assert.assertTrue;

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
import edmws.webapp.servlets.E3IfcApiResourcePath;
import edmws.webapp.servlets.E3Logger;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class E3Tests03 extends E3Tests00{
	
	private static boolean skipInit = true;

	@Rule public TestName mCurrentTestName = new TestName();

	private String getQualifiedTestName() {
		return (	this.getClass().getName()
				+ "_" 
				+ this.mCurrentTestName.getMethodName()
				);
	}

	private static final String TEST_MODEL_NAME = "E3Tests03";
	private static final String TEST_MODEL_NAME_31 = "E3Tests03_T31";
	private static final String BASE_URL = "/ifc-api/0.5/" + E3IfcApiResourcePath.MR_IFCMODEL;
	

	


	private static E3Logger logger = E3Logger.getLogger(E3Tests03.class.getName());
	
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
		return this.runIfcApiService(args, null);
	}


	private void deleteTestModels(String prefix) throws Exception{
		E3TestUtils.createE3TestUtils(testlib).deleteAllTestModels1(prefix);
	}

	private void ESIMAppend(String testModelName,String filename_ifc,String filename_csv,String filename_out ) throws Exception 
	{
			//String testModelName = TEST_MODEL_NAME_32;
			//String filename_ifc = testlib.getInputPathIfc4() + "/ESIM_output/20160926_eeE_TestCase_UD_ifc_A3.ifc";
			//String filename_csv = testlib.getInputPathIfc4() + "/ESIM_output/KPs-as-is_concept.csv";
			String file_type = "csv";
			String merge_function = "E3MergeSIM1";
			
			E3Tests01 helper = new E3Tests01();
			String model_guid = helper.getModelGuidFromName(testModelName);
			if (model_guid != null) {
				helper.deleteModelByGuid(model_guid);
				model_guid = null;
			}			
			if (model_guid == null) {
				String projectName = IE3TestBase.TEST_PROJECT_NAME_0; // "FM";
				helper.uploadModelIFC4(projectName, "HVAC",testModelName, "testModel", filename_ifc);
			}
			model_guid = helper.getModelGuidFromName(testModelName);
			
			E3TestArgs ta = new E3TestArgs("POST",BASE_URL + "/" + model_guid + "/" + E3IfcApiResourcePath.MR_IFCFUNCTION_APPEND_FILE);
			
			ta.urlArgs = new JSONObject();
			ta.urlArgs.put(E3IfcApiResourcePath.IAF_FILE_TYPE, file_type);
			ta.urlArgs.put(E3IfcApiResourcePath.IAF_MERGE_FUNCTION, merge_function);
			ta.bodyArgs = new JSONObject();
			if(testlib.useWebService()) {
				ta.bodyArgs.put(E3BimApiResourcePath.MUF_MODEL_IS_EXTERNAL,"false");
				ta.set_file_input(filename_csv);
			} else {
				ta.bodyArgs.put(E3IfcApiResourcePath.IAF_EXTERNAL_LINK,filename_csv);
			}
			
			JSONArray jresult = new JSONArray(this.runIfcApiService(ta));
			assertTrue("Nothing returned...",jresult.length() > 0);			
			assertTrue("Missing completion message", jresult.toString().contains(merge_function + " interior completed"));
//			log(E3Logger.DEBUG,"--- response:" + jresult.toString(2));
			helper.DownloadModelToFile(testModelName, filename_out);
			log(E3Logger.INFO,"..." + getQualifiedTestName() + " completed successfully");
	}

	
	private void HVACAppend(String testModelName,String filename_ifc,String filename_xml,String filename_out ) throws Exception 
	{
			String file_type = "xml";
			String merge_function = "E3MergeBACS1";
			
			E3Tests01 helper = new E3Tests01();
			String model_guid = helper.getModelGuidFromName(testModelName);
			if (model_guid != null) {
				helper.deleteModelByGuid(model_guid);
				model_guid = null;
			}			
			if (model_guid == null) {
				String projectName = IE3TestBase.TEST_PROJECT_NAME_0; // "FM";
				helper.uploadModelIFC4(projectName, "HVAC",testModelName, "testModel", filename_ifc);
			}
			model_guid = helper.getModelGuidFromName(testModelName);
			
			E3TestArgs ta = new E3TestArgs("POST",BASE_URL + "/" + model_guid + "/" + E3IfcApiResourcePath.MR_IFCFUNCTION_APPEND_FILE);
			
			ta.urlArgs = new JSONObject();
			ta.urlArgs.put(E3IfcApiResourcePath.IAF_FILE_TYPE, file_type);
			ta.urlArgs.put(E3IfcApiResourcePath.IAF_MERGE_FUNCTION, merge_function);
/*			
			if(testlib.useWebService()) {
				ta.urlArgs.put(E3BimApiResourcePath.MUF_MODEL_IS_EXTERNAL,"false");
				ta.set_file_input(filename);
			} else {
				ta.urlArgs.put(E3BimApiResourcePath.MUF_MODEL_IS_EXTERNAL,"true");
				ta.urlArgs.put(E3BimApiResourcePath.MUF_MODEL_CONTENT,filename);
			}
*/			
			
			ta.bodyArgs = new JSONObject();
			if(testlib.useWebService()) {
				ta.bodyArgs.put(E3BimApiResourcePath.MUF_MODEL_IS_EXTERNAL,"false");
				ta.set_file_input(filename_xml);
			} else {
				ta.bodyArgs.put(E3IfcApiResourcePath.IAF_EXTERNAL_LINK,filename_xml);
			}
			
			//JSONArray jresult = new JSONArray(this.runIfcApiService(ta));
			String jsonString = this.runIfcApiService(ta);
			assertTrue("Missing completion message", jsonString.contains("E3MergeBACS1 interior completed"));
/*
			JSONArray jresult = new JSONArray(jsonString);
			assertTrue("Nothing returned...",jresult.length() > 0);
			boolean completed = false;
			for(int i0 = 0; i0 < jresult.length();i0++) 
			{
				JSONObject jo = jresult.getJSONObject(i0);
				JSONArray diagnostics = jo.optJSONArray("diagnostics");
				for(int i1 = 0; i0 < diagnostics.length();i1++) 
				{
					JSONObject idcresult = diagnostics.getJSONObject(i1);
					String message =idcresult.optString("idcmessage");
					if (message.contains(merge_function + "interior completed")) {
						completed = true;
						break;
					}
				}
				if(completed) break;
			}
			assertTrue("Missing completion message",completed);

			assertTrue("Missing completion message", jsonString.contains("E3MergeBACS1 interior completed"));
			log(E3Logger.DEBUG,"--- response:" + jresult.toString(2));
*/			
			helper.DownloadModelToFile(testModelName, filename_out);
			log(E3Logger.INFO,"..." + getQualifiedTestName() + " completed successfully");
	}
	
	
	@Test
	public void T31HVACSAppend() throws Exception 
	{
		try {
			String testModelName = TEST_MODEL_NAME_31;
			E3TestUtils.createE3TestUtils(testlib).deleteAllTestModels1(testModelName);
			String filename_ifc = testlib.getInputPathIfc4_ED2() + "/HVAC_output/SystemDraftBoilerWithPump.ifc";
			String filename_xml = testlib.getInputPathIfc4_ED2() + "/HVAC_output/SystemDraftBoilerWithPump.xml";
			String filename_out = testlib.getOutputPath() + "/HVAC_merge_" + testModelName + ".ifc";
			this.HVACAppend(testModelName, filename_ifc, filename_xml,filename_out);
		}
		catch(Exception ex)	{
			log(E3Logger.ERROR,"..." + getQualifiedTestName() + " completed with error(s):" + ex.toString());
			testlib.writeTrace(ex.toString());
			throw ex;
		}
	}

	//@Test
	public void T32ESIMAppend() throws Exception 
	{
		try {
			String testModelName = "E3Tests03_T32";
			String filename_ifc = testlib.getInputPathIfc4_ED2() + "/ESIM_output/20160926_eeE_TestCase_UD_ifc_A3.ifc";
			String filename_csv = testlib.getInputPathIfc4_ED2() + "/ESIM_output/KPs-as-is_concept.csv";
			String filename_out = testlib.getOutputPath() + "/ESIM_merge_" + testModelName + ".ifc";
			this.ESIMAppend(testModelName, filename_ifc, filename_csv,filename_out);
		}
		catch(Exception ex)	{
			log(E3Logger.ERROR,"..." + getQualifiedTestName() + " completed with error(s):" + ex.toString());
			testlib.writeTrace(ex.toString());
			throw ex;
		}
	}
	
	//@Test
	public void T33ESIMAppend() throws Exception 
	{
		try {
			String testModelName = "E3Tests03_T33";
			String filename_ifc = testlib.getInputPathIfc4_ED2() + "/testbase/20170516_BAM_Pilot_Project_UD_Alternative_1.ifc";
			String filename_csv = testlib.getInputPathIfc4_ED2() + "/testbase/KP-as-is_no_samples_no_units.csv";
			String filename_out = testlib.getOutputPath() + "/ESIM_merge_" + testModelName + ".ifc";
			this.ESIMAppend(testModelName, filename_ifc, filename_csv,filename_out);
		}
		catch(Exception ex)	{
			log(E3Logger.ERROR,"..." + getQualifiedTestName() + " completed with error(s):" + ex.toString());
			testlib.writeTrace(ex.toString());
			throw ex;
		}
	}

	@Test
	public void T35ESIMAppendUD1() throws Exception 
	{
		try {
			String testModelName = "E3Tests03_T35";
			String filename_ifc = testlib.getInputPathIfc4() + "/UD1_35/20170516_BAM_Pilot_Project_UD_Alternative_1.ifc";
			String filename_csv = testlib.getInputPathIfc4() + "/UD1_35/KP-as-is_no_samples_with_units_A1_20170621.csv";
			String filename_out = testlib.getOutputPath() + "/ESIM_merge_" + testModelName + ".ifc";
			this.ESIMAppend(testModelName, filename_ifc, filename_csv,filename_out);
		}
		catch(Exception ex)	{
			log(E3Logger.ERROR,"..." + getQualifiedTestName() + " completed with error(s):" + ex.toString());
			testlib.writeTrace(ex.toString());
			throw ex;
		}
	}

	
	
}
