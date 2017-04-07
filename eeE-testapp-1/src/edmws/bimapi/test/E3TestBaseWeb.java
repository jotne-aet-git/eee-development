
package edmws.bimapi.test;

import java.io.File;
import java.io.Writer;

import edmws.webapp.servlets.E3AccessUtil;
import edmws.webapp.servlets.E3Logger;

/**
 * This file is to be cleaned up when AET has the time...
 */
public class E3TestBaseWeb extends Thread implements IE3TestBase {
	
	private static E3Logger logger = E3Logger.getLogger(E3TestBaseWeb.class.getName());
	private static void log(int severity,String msg) {
		logger.log(severity, msg);
	}
	
/*	
	public static String    commType			= "TCP";
	public static String    hostName            = "localhost";
	public static String    portName            = "4590";
*/	
	public static String    userName			= "superuser";
	public static String    groupName		   	= "";
	public static String    userPass			= "Db1$123";
	public static String    webServerRoot       = "http://localhost:8080/e3-bimapi-a";
//	public static String    webServerRoot       = "http://bim-api.jotne.com:8080/e3-bimapi-a";

//	for local context mode 
/*	
	public static String    dbPath   			= examplesRoot + "db";
	public static String    dbName				= "db1";
	public static String    dbPass				= "db1";
*/	


//	private static String    	schemaName    	= "ifc2x3_final";
//	private static String edm_dev_root = "C:/home/aet/proj_dev/eeE/eee-development/";
	private static String examplesRoot = "C:/home/aet/proj_dev/eeE";
	private static String    	inputPath   	= examplesRoot + "data/ifc2x3/";
	private static String    	inputPathIfc4   = "C:/home/aet/proj_dev/eeE/jotnesvn/swdevelopment/testdata/ED2";
	private static String    	outputPath   	= "C:/home/aet/proj_dev/eeE/jotnesvn/swdevelopment/output";
	
	@Override
	public String getInputPath() {return inputPath;}
	@Override
	public String getInputPathIfc4() {return inputPathIfc4;}
	@Override
	public String getOutputPath() {return outputPath;}
	

	private E3TestBaseWeb(){}

	private static E3TestBaseWeb the_instance = null;
	public static IE3TestBase getInstance() {
		if(the_instance == null) the_instance = new E3TestBaseWeb(); 
		return the_instance; 
	}
	
	
	/* (non-Javadoc)
	 * @see edmws.bimapi.test.IE3TestBase#initDirectTest()
	 */
	@Override
	public void initTest(String url) {
		String tmp = System.getProperty("e3test.webServerRoot");
		if (tmp != null) {
			webServerRoot = tmp;
		}
		tmp = System.getenv("e3test.webServerRoot");
		if (tmp != null) {
			webServerRoot = tmp;
		}
		if (url != null) {
			webServerRoot = url;			
		}
		File outdir = new File(this.getOutputPath());
		if (!outdir.exists()) outdir.mkdirs();
		
		logger.log(E3Logger.INFO, "Using webServerRoot: " + webServerRoot);
	}
	
	
	/* (non-Javadoc)
	 * @see edmws.bimapi.test.IE3TestBase#useWebService()
	 */
	@Override
	public boolean useWebService(){return true;}



	
	
	
	/**
	 * Called after each sample run to clean up.
	 * @throws Exception If something goes wrong.
	 */
	public void cleanup() 
	{
	}

	
    /* (non-Javadoc)
	 * @see edmws.bimapi.test.IE3TestBase#startTrace(java.lang.String)
	 */
    @Override
	public void startTrace(String testName) throws Exception {
       	log(E3Logger.INFO,testName + " -- start test");    		
    }
    
    /* (non-Javadoc)
	 * @see edmws.bimapi.test.IE3TestBase#writeTrace(java.lang.String)
	 */
    @Override
	public void writeTrace(String msg ) throws Exception {
       	log(E3Logger.INFO," -- " + msg);
    }
    
    /* (non-Javadoc)
	 * @see edmws.bimapi.test.IE3TestBase#stopTrace()
	 */
    @Override
	public void stopTrace() {
    }
    

    
	public String runE3Service(E3AccessUtil carrier,String servletName, String workerClassName,E3TestArgs args,Writer wr) throws Exception {
		String result;
		args.setServerUrl(webServerRoot + "/" + servletName);
		result = new E3TestWebRequest().sendGet(args,wr);			
		return result;
	}
    
    
}


