
package edmws.bimapi.test;

import java.io.File;

import edm.edom3.EDMConstants;
import edm.edom3.EDMConstants.TraceOptions;
import edm.edom3.EDMDatabase;
import edm.edom3.EDMInterface;
import edm.edom3.EDMLogger;
import edm.edom3.EDMRemoteContext;
import edm.edom3.EDMServer;
import edmws.webapp.servlets.E3Logger;

/**
 * This file is to be cleaned up when AET has the time...
 */
public class E3TestBase extends Thread {
	
	private static E3Logger logger = E3Logger.getLogger(E3TestBase.class.getName());
	private static void log(int severity,String msg) {
		logger.log(severity, msg);
	}
	
	public static String examplesRoot = "O:/edm_dev/EDMeXtensions/edom3/test/";
	public static String    commType			= "TCP";
	public static String    hostName            = "localhost";
	public static String    portName            = "9090";
	public static String    userName			= "superuser";
	public static String    groupName		   	= "";
	public static String    userPass			= "db1";
	public static String    webServerRoot       = "http://localhost:8080/e3-bimapi-a";

	public void initDirectTest() {
		EDMInterface.initMultiThread();
		commType			= "TCP";  
		hostName            = "localhost";
		portName            = "4590";
		userName			= "superuser";
		groupName		   	= "";
		userPass			= "db1";
		webServerRoot 		= "";
	}
	

	/**
	 * 
	 * @param url - url to BIM_API service root, for example ""http://localhost:8080/e3-bimapi-a"
	 */
	public void initWebServiceTest(String url) {
		commType			= "TCP";  
		hostName            = "localhost";
		portName            = "0000";
		userName			= "superuser";
		groupName		   	= "";
		userPass			= "db1";
				
		String tmp = System.getProperty("e3test.webServerRoot");
		if (tmp != null) {
			webServerRoot = tmp;
		}
		if (url != null) {
			webServerRoot = url;			
		}
	}
	
	
	public boolean useWebService(){return !(this.webServerRoot.isEmpty());}

	//	 common parameters	
	
	/**
	 * Default input path for test data files. These files are shared for several samples.
	 */
	public static String    inputPath   		= examplesRoot + "data/ifc2x3/";
	/**
	 * Name main schema used, e.g. IFC2x3_FINAL.
	 */
	protected static String    schemaName    = "ifc2x3_final";
	
	/**
	 * Output path for files generated during sample runs. This includes data files, log files and diagnostic files.
	 */
	private static String    outputPath   		= examplesRoot + "output";
	public String getOutputPath() {return outputPath;}
	
	/**
	 * Path to database. If LOCAL_DB mode, the database is always opened at start and closed at end. If client/server, the database is opened if it is not open already (at least if you are lucky...). Is it is open, this field is not used.
	 */
	public static String    dbPath   			= examplesRoot + "db";
	/**
	 * Name of database. If LOCAL_DB mode, the database is always opened at start and closed at end. If client/server, the database is opened if it is not open already (at least if you are lucky...). Is it is open, this field is not used.
	 */
	public static String    dbName				= "db1";
	/**
	 * Password for database. If LOCAL_DB mode, the database is always opened at start and closed at end. If client/server, the database is opened if it is not open already (at least if you are lucky...). If it is open already, this field is not used.
	 */
	public static String    dbPass				= "db1";
	
	
	
	private EDMRemoteContext m_remoteContext = null; 
	
	/**
	 * Get the default test context. You may modify this code if you want another context initialization.
	 * @return Default test context.
	 * @throws Exception If something goes wrong.
	 */
	protected EDMRemoteContext getRemoteContext() throws Exception 
	{
		File tmp = new File(examplesRoot);
		log(E3Logger.DEBUG,".. Using root dir " + tmp.getAbsolutePath());
		if(m_remoteContext == null) {
	   		m_remoteContext = EDMInterface.createRemoteContext("workshop_context");
	   		m_remoteContext.communication().setAttrBN(EDMConstants.ServerProperties.communicationType,commType);
	   		m_remoteContext.communication().setAttrBN(EDMConstants.ServerProperties.host,hostName);
	   		m_remoteContext.communication().setAttrBN(EDMConstants.ServerProperties.port,portName);
	   		m_remoteContext.login(userName,groupName,userPass);
	   		
	   		// if running on a local database is not open, open it
	   		
	   		if("LOCAL_DB".equalsIgnoreCase(commType)) {
	   			m_remoteContext.login(userName,groupName,userPass);
	   			EDMServer server = m_remoteContext.getServer();
	   			if(!server.isDatabaseOpen()) {
	   				EDMDatabase db = server.getDatabase(dbPath,dbName,dbPass);
	   				if(!db.exists()) db.create();
	   				db.open();
	   			}
	   		}
		}
   		return m_remoteContext;
   }
	
	/**
	 * Called after each sample run to clean up.
	 * @throws Exception If something goes wrong.
	 */
	protected void cleanup() 
	{
		try {
		if(m_remoteContext != null) {
			if("LOCAL_DB".equalsIgnoreCase(commType)) {
				EDMServer server = m_remoteContext.getServer();
				if(server.isDatabaseOpen()) {
					EDMDatabase db = server.getDatabase(dbPath,dbName,dbPass);
					db.close();
				}
			}				
			m_remoteContext.logout();
			m_remoteContext = null;
		}
    	} catch(Exception ex) {}
			
	}

	
    private EDMLogger trace = null;
    public void startTrace(String testName) throws Exception {
    	if (this.useWebService()) {
        	log(E3Logger.INFO,testName + " -- start test");    		
    	} else {
	    	trace = EDMInterface.getEDMTrace();
	    	trace.setLogFileName(this.getOutputPath() + "/"+ testName + "_trace.txt");
	    	long condition 	= TraceOptions.TRACE_ARGS
							| TraceOptions.TRACE_CALLS
							| TraceOptions.TRACE_RETURNS
							;
	    	trace.setOptions(condition);
	    	trace.start(); 
	    	trace.write(testName + " -- start test");
    	}
    }
    
    public void writeTrace(String msg ) throws Exception {
    	if (this.useWebService()) {
        	log(E3Logger.INFO," -- " + msg);
    	} else {
    		if(trace!= null) trace.write(msg);
    	}
    }
    
    public void stopTrace() {
    	try {
	    	if(trace!= null) trace.close();
	    	trace = null;
    	} catch(Exception ex) {}
    }
    
    
}


