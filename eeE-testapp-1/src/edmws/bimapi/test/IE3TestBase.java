package edmws.bimapi.test;

import java.io.Writer;

import edmws.webapp.servlets.E3AccessUtil;

public interface IE3TestBase {

	public static final String BIMAPI_MODELS_URL = "/eee-repos/0.5/models";
	
	
//	 test data parameters	
	
	
	
	public static final String TEST_PROJECT_NAME_0 = "msm-project";
	public static final String CASE_PROJECT_NAME_1 = "eeEmbedded";
	public static final String TEST_PROJECT_NAME_2 = "test-create-project";

	/**
	 * 
	 * @param url - url to BIM_API service root, for example ""http://localhost:8080/e3-bimapi-a"
	 */
	public abstract void initTest(String url);

	public abstract boolean useWebService();

	/**
	 * Default input path for test data files. These files are shared for several samples.
	 */
	public abstract String getInputPath();
	
	/**
	 * Output path for files generated during sample runs. This includes data files, log files and diagnostic files.
	 */
	public abstract String getOutputPath();

	public abstract void startTrace(String testName) throws Exception;

	public abstract void writeTrace(String msg) throws Exception;

	public abstract void stopTrace();

	
	public abstract String runE3Service(E3AccessUtil carrier,String servletName, String workerClassName,E3TestArgs args,Writer wr) throws Exception;

	public abstract void cleanup();

}