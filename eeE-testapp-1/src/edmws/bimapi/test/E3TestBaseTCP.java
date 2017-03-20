
package edmws.bimapi.test;

import java.io.File;
import java.io.Writer;

import edm.edom3.EDMConstants;
import edm.edom3.EDMConstants.TraceOptions;
import edm.edom3.EDMDatabase;
import edm.edom3.EDMInterface;
import edm.edom3.EDMLogger;
import edm.edom3.EDMRemoteContext;
import edm.edom3.EDMServer;
import edmws.webapp.servlets.E3AccessUtil;
import edmws.webapp.servlets.E3ApiActions;
import edmws.webapp.servlets.E3Logger;

/**
 * This file is to be cleaned up when AET has the time...
 */
public class E3TestBaseTCP extends Thread implements IE3TestBase {
	
	private static E3Logger logger = E3Logger.getLogger(E3TestBaseTCP.class.getName());
	private static void log(int severity,String msg) {
		logger.log(severity, msg);
	}
	
//	private static String    	schemaName    	= "ifc2x3_final";
	private static String edm_dev_root = "C:/home/aet/dev_tree/trunk/";
	private static String examplesRoot = edm_dev_root + "EDMeXtensions/lm_test/";
	private static String    	inputPath   	= examplesRoot + "data/ifc2x3/";
	private static String    	inputPathIfc4   = examplesRoot + "data/ifc4/";
	private static String    	outputPath   	= examplesRoot + "output";

	public static String    commType			= "TCP";
	public static String    hostName            = "localhost";
	public static String    portName            = "4590";
	public static String    userName			= "superuser";
	public static String    groupName		   	= "";
	public static String    userPass			= "Db1$123";

//	for local context mode 
	public static String    dbPath   			= examplesRoot + "db";
	public static String    dbName				= "db1";
	public static String    dbPass				= "db1";
	

//	 test data parameters	
	
	protected static String    	schemaName    	= "ifc2x3_final";

	
	@Override
	public String getInputPath() {return inputPath;}
	@Override
	public String getInputPathIfc4() {return inputPathIfc4;}
	@Override
	public String getOutputPath() {return outputPath;}
	
	private E3TestBaseTCP(){}
	
	private static E3TestBaseTCP the_instance = null;
	public static IE3TestBase getInstance() {
		if(the_instance == null) the_instance = new E3TestBaseTCP(); 
		return the_instance; 
	}
	
	
	/* (non-Javadoc)
	 * @see edmws.bimapi.test.IE3TestBase#initDirectTest()
	 */
	@Override
	public void initTest(String url) {
		EDMInterface.initMultiThread();
	}
	
	
	/* (non-Javadoc)
	 * @see edmws.bimapi.test.IE3TestBase#useWebService()
	 */
	@Override
	public boolean useWebService(){return false;}



	
	
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
	public void cleanup() 
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
    /* (non-Javadoc)
	 * @see edmws.bimapi.test.IE3TestBase#startTrace(java.lang.String)
	 */
    @Override
	public void startTrace(String testName) throws Exception {
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
    
    /* (non-Javadoc)
	 * @see edmws.bimapi.test.IE3TestBase#writeTrace(java.lang.String)
	 */
    @Override
	public void writeTrace(String msg ) throws Exception {
   		if(trace!= null) trace.write(msg);
    }
    
    /* (non-Javadoc)
	 * @see edmws.bimapi.test.IE3TestBase#stopTrace()
	 */
    @Override
	public void stopTrace() {
    	try {
	    	if(trace!= null) trace.close();
	    	trace = null;
    	} catch(Exception ex) {}
    }
    

    
	public String runE3Service(E3AccessUtil carrier,String servletName, String workerClassName,E3TestArgs args,Writer wr) throws Exception {
		String result;
		carrier.setEDMTestContext(this.getRemoteContext(), args.getUrl(),args.urlArgs,args.bodyArgs);
		E3ApiActions worker = (E3ApiActions) Class.forName(workerClassName).newInstance();
		//E3BimApiActions worker = new E3BimApiActions();
		result = worker.doRest3(carrier, args.restOp);
		return result;
	}
    
    
}


