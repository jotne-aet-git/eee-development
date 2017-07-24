package edmws.webapp.servlets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import org.json.JSONArray;
import org.json.JSONObject;

import edm.edom3.EDMConstants;
import edm.edom3.EDMLogDescription;
import edm.edom3.exception.EdmiException;
import edmws.webapp.exception.E3GeneralException;
import edmws.ws.model.E3FileTransferInfo;
import edmws.ws.model.E3ResourceData;

public abstract class E3ApiActions {

	private static E3Logger logger = E3Logger.getLogger(E3ApiActions.class.getName());
	private void log(int severity,String msg) {
		logger.log(severity, msg);
	}

	private String mApiVersion = null;
	private E3JSONQuery2 mQuery = null; 

	public E3ApiActions(String apiVersion) {
		super();
		this.mApiVersion = apiVersion;
	}

	public String getApiVersion() {
		return mApiVersion;
	}

	public void setApiVersion(String mApiVersion) {
		this.mApiVersion = mApiVersion;
	}

	public abstract IE3ResourcePath getAnalyzer();

	
	public JSONObject analyzeResource(E3AccessUtil carrier,IE3ResourcePath analyzer,String restOp) throws E3GeneralException
	{
		E3ResourceData e3Data = new E3ResourceData();
		String url = carrier.getUrl();
		e3Data.setRestOP(restOp);
		e3Data.setURL(url);
		e3Data.setUrlArgs(carrier.getRequestParameters());
		e3Data.setQueryPath(carrier.getQueryPath());
		 
		JSONObject result = analyzer.analyzeResource(e3Data, carrier);
		return result;
	}

	private void convertCsvToRawXml(String filename) throws IOException
	{
		log(E3Logger.DEBUG,"--- csv2xml: " + filename);
		List<String> rows = new ArrayList<String>();  
	    try(BufferedReader br = new BufferedReader(new FileReader(filename))) {
	        String line = br.readLine();
	        while (line != null) {
	            rows.add(line);
	            line = br.readLine();
	        }
	    }
	    
	    PrintWriter pw = new PrintWriter( new FileWriter(filename));
	    pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	    pw.println("<fileset>");
  	    pw.println("<file name=\"" + filename + "\">");
  	    int nL = 0; 
  	    for(String row : rows) 
    	{
  	    	nL++;
  	    	if (null==row) continue;
  	    	pw.print("<row n=\"" + String.valueOf(nL) + "\" >");
  	    	String sep = ","; // eeE uses ',' in CSV
  	    	if(row.contains(";")) sep = ";";
  	    	String[] cells = row.split(sep);
  	    	int nC = 0;
  	    	for(String cell : cells) 
  	    	{
  	    		nC++;
  	  	    	pw.print("<cell n=\"" + String.valueOf(nC) + "\" >");
  	  	    	pw.print(cell);
  	  	    	pw.print("</cell>");
  	    		
  	    	}
  	    	pw.println("</row>");
    	}
//    	    pw.println("<row n="1"><cell n="1">Col00</cell><cell n="2">Col01</cell><cell n="3">Col02</cell><cell n="4">Col03</cell></row>
//    	    pw.println("<row n="2"><cell n="1">Row01</cell><cell n="2">1.1</cell><cell n="3">2.1</cell><cell n="4">3.1</cell></row>
//    	    pw.println("<row n="3"><cell n="1">Row02</cell><cell n="2">1.2</cell><cell n="3">2.2</cell><cell n="4">3.2</cell></row>
//    	    pw.println("<row n="4"><cell n="1">Row03</cell><cell n="2">1.3</cell><cell n="3">2.3</cell><cell n="4">3.3</cell></row>
  	    pw.println("</file>");
	    pw.println("</fileset>");
	    pw.close();		
	}
	
	public String doRest3(E3AccessUtil carrier,  String restOp)throws IOException, ServletException {
		String result = null;
		try {
			
			IE3ResourcePath analyzer = this.getAnalyzer();
			E3ResourceData e3Data = new E3ResourceData();
			String url = carrier.getUrl();
			e3Data.setRestOP(restOp);
			e3Data.setURL(url);
			e3Data.setUrlArgs(carrier.getRequestParameters());
			e3Data.setQueryPath(carrier.getQueryPath());
			 
			JSONObject jargs = analyzer.analyzeResource(e3Data, carrier);
			
			String eeeOp = jargs.optString(E3Constants.MR_EEEOP,"");
			log(E3Logger.DEBUG,"--- eeeOp: " + eeeOp);
			log(E3Logger.DEBUG,"--- model: " + carrier.getQueryPath().getRepositoryName() + "." + carrier.getQueryPath().getModelName());
			log(E3Logger.DEBUG,"--- query: " + carrier.getQueryPath().getQuerySchemaName() + "." + carrier.getQueryPath().getQueryFunctionName() );
			log(E3Logger.DEBUG,"--- jargs: " + jargs.toString());
			if("" != eeeOp) {
				result = carrier.checkAuthorization();
				if( result == null) {
					result = this.executeE3Operation(carrier, jargs);
					carrier.checkAutoLogout();
				}
			} else {				
				result = carrier.sendError(E3BasicUtils.ERROR_CODE_FAIL, null, "Illegal E3 repo service URL ");
			}
		} catch(E3GeneralException ex) {
			result = carrier.sendError(E3BasicUtils.ERROR_CODE_FAIL, ex, ex.getE3Message());			
		} catch (Exception e) {
			result = carrier.sendError(E3BasicUtils.ERROR_CODE_FAIL, e, "Exception during E3 service operation");
		}
		return result;
	}

	protected String executeE3Operation(E3AccessUtil carrier, JSONObject jargs) throws Exception 
	{
		String jsonOut = null;
		JSONArray jresult = null;

		E3FileTransferInfo transferInfo = null;
		
		E3JSONQuery2 query = this.getQuery2(carrier); 
		this.setLogDescription(query);
		//JSONObject queryArgs = query.prepareJSONArgs2(jargs);
		query.prepareJSONArgs2(jargs);
		try {
			if (query.hasInputFileArgument()){
				transferInfo = carrier.getServerTempFile("upload.ifc");
				String externalLink = jargs.optString(E3BimApiResourcePath.MUF_EXTERNAL_LINK);
				if("" != externalLink ) externalLink = jargs.optString(E3IfcApiResourcePath.IAF_EXTERNAL_LINK);
				E3FileTransfer xfer = new E3FileTransfer();
				if("" != externalLink ) {
					transferInfo.setForeignFileUrl(externalLink);
					log(E3Logger.DEBUG,"--- fetch : " + transferInfo.getForeignFileUrl());
					xfer.doFetch(carrier, transferInfo);
				} else {
					log(E3Logger.DEBUG,"--- upload: " + transferInfo.getFileNameOnClient());
					xfer.doUpload(carrier, transferInfo);
				}
				// neeE hack - convert csv to raw xml 
				String file_type = jargs.optString(E3IfcApiResourcePath.IAF_FILE_TYPE);
				if("csv".equals(file_type)) {
					this.convertCsvToRawXml(transferInfo.getFileNameOnServer());
				}				
				this.prepareSingleFileTransfer(query, jargs, transferInfo);
			}
			else if (query.hasOutputFileArgument()) {
				String guid = jargs.optString(E3BimApiResourcePath.MUF_MODEL_ID,"download.ifc"); // TODO: make neutral field
				transferInfo = carrier.getServerTempFile(guid + ".ifc");
				this.prepareSingleFileTransfer(query, jargs, transferInfo);				
			}
			
			jresult = query.executeJSONQuery2(jargs);
			
			if (query.hasOutputFileArgument()) {
				E3FileTransfer xfer = new E3FileTransfer();
				String result = xfer.doDownload(carrier, transferInfo);
				// only for test / unit test. Addmittedly a little bit dirty
				if (result != null) {
					JSONObject jo = new JSONObject();
					jo.put(E3BimApiResourcePath.MUF_MODEL_CONTENT,result);
					jresult.put(jo);
				}
			}
			
			String pp = carrier.getQueryPath().getPostProcessor();
			if(pp != null && !pp.isEmpty()) {
				jresult = this.postProcessor(pp,carrier, jresult);
			}
			if(jresult != null) jsonOut = jresult.toString();
		}
		catch(Exception ex){
			throw ex;
		}
		finally {
			if(transferInfo != null) transferInfo.deleteTempFiles();
		}

					
		if(jsonOut != null) carrier.sendJsonResponse(jsonOut);
		return jsonOut;
	}
	


	protected abstract JSONArray postProcessor(String pp, E3AccessUtil carrier,JSONArray jresult) throws EdmiException, E3GeneralException;

	protected void prepareSingleFileTransfer(E3JSONQuery2 query,JSONObject queryArgs,E3FileTransferInfo transferInfo) {
		String pnames[] = JSONObject.getNames(queryArgs);
		for(int i0 = 0; i0 < pnames.length; i0++) {
			String pname = pnames[i0];
			if(pname.startsWith(E3JSONQuery2.ARG_PREFIX_INPUT_FILE)) {
				queryArgs.put(pname,transferInfo.getFileNameOnServer());
			}
			if(pname.startsWith(E3JSONQuery2.ARG_PREFIX_OUTPUT_FILE)) {
				queryArgs.put(pname,transferInfo.getFileNameOnServer());
			}
		}		
	}
	
	

	protected E3JSONQuery2 getQuery2(E3AccessUtil carrier) throws EdmiException, E3GeneralException {
		if(this.mQuery == null) {
			mQuery = new E3JSONQuery2(carrier.getEDMContext()); 
			mQuery.setQueryPath(carrier.getQueryPath());
		}
		return mQuery;
	}
	
	
	protected void setLogDescription(E3JSONQuery2 query) {
		String logName = getLogName();
		if(logName == null) logName = this.getClass().getName();
		
	// TODO: make configurable
		if(true) {
			EDMLogDescription elog = query.getLogDescription();
			elog.setLogFileName(getLocalFilenameWithPath(logName 	+ "." + query.getQuerySchemaName() 
																	+ "." + query.getQueryFunctionName() + ".log"));
			long logoptions = elog.getOptions();
			logoptions |= (EDMConstants.LogOptions.FULL_LOG);
			//logoptions |= (EDMConstants.LogOptions.START_LOG);
			elog.setOptions(logoptions);
		}
		// TODO: make configurable
		if(true) {
			EDMLogDescription elog = query.getLogDescription();
			elog.setUserOutputFileName(getLocalFilenameWithPath(logName + "." + query.getQuerySchemaName() 
																		+ "." + query.getQueryFunctionName() + ".uof"));
		}
	}
	
	private static String sLogFileRoot = null;
	public static String getLocalFilenameWithPath(String filename) {
		String root = sLogFileRoot;
		if(root == null) root = System.getProperty("java.io.tmpdir");
		String dirname = new File( root).getPath();
		return dirname + "/" + filename;
	}
	
	public static void setLogFileRoot(String root) {
		sLogFileRoot = root;
	}

	private static String sLogName = null;
	public static void setLogName(String name) {
		sLogName = name;
	}
	public static String getLogName() {
		return sLogName;
	}

}