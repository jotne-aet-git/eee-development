package edmws.webapp.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.json.JSONTokener;

import edm.edom3.EDMContext;
import edm.edom3.EDMInterface;
import edm.edom3.exception.EdmiException;
import edmws.webapp.ServerConfiguration;
import edmws.webapp.exception.E3GeneralException;
import edmws.webapp.exception.E3RuntimeException;
import edmws.ws.model.E3FileTransferInfo;
import edmws.ws.model.E3SessionTable;
import edmws.ws.model.EDMWSSession;
import edmws.ws.model.EDMWSSessionTable;
public class E3AccessUtil implements IE3JSONSource {
	
	private static E3Logger logger = E3Logger.getLogger(E3AccessUtil.class.getName());
	private static void log(int severity,String msg) {
		logger.log(severity, msg);
	}
	

	private final static String myRealm = "eee-repository-services";
	
	EDMWSSession mEdmSession = null;
	boolean wasAutoLogin = false;
	boolean wasBasicAuth = false;
	EDMWSServlet servlet = null;
	HttpServletRequest request = null;
	HttpServletResponse response = null;
	ServletInputStream inputStream = null;
	PrintWriter writer = null;
	String sessionId;	
	//HashMap<String,String> jsonParams;

	private EDMContext mTestEDMContext = null;
	private String mTestURL;
	private JSONObject mTestURLArgs;
	private JSONObject mTestMdIn;
	

	public void setEDMTestContext(EDMContext context,String url, JSONObject urlArgs, JSONObject mdIn) 
	{
		this.mTestEDMContext = context;
		this.mTestURLArgs = urlArgs;
		this.mTestMdIn = mdIn; 
		int ix = url.indexOf('?');
		if (ix > 0) {
			url = url.substring(0,ix);
		}
		this.mTestURL = url;
	}
	
	public void writeEDMTrace(String s) {
		try {
			EDMInterface.getEDMTrace().write(s);
		} catch (EdmiException e) {}
	}
	
	protected EDMContext getEDMContext() {
		if (mEdmSession != null) return mEdmSession.getContext();
		return this.mTestEDMContext;
	}
	
	public E3AccessUtil(EDMWSServlet servlet0,HttpServletRequest request0, HttpServletResponse response0) throws IOException{
		this.mEdmSession = null;
		this.servlet = servlet0;
		this.request = request0;
		this.response = response0;
		this.sessionId = null;
		writer = null;
		if(request != null) inputStream = request.getInputStream();
				
	}
	

	ServerConfiguration getConfiguration()  {
		try {
			return this.servlet.getConfiguration();
		} catch (Exception e) {
			// TODO: this is relevant only for unit test, if not usnit test Ex should be thrown?! 
			// e.printStackTrace();
			return null;
		}
	}
	
	public PrintWriter getWriter() throws IOException {
		if(writer == null) {
			javax.servlet.ServletOutputStream outStream = response.getOutputStream();
			writer = new PrintWriter(outStream);
		}
		return writer;
	}
	
	public ServletInputStream getInputStream() {return this.inputStream;}
	
	private String optConfigParameter(String pName, String defaultValue) {
		String result = defaultValue;
		try {
			// String val = servlet.getServletContext().getInitParameter(pName);
			String val = servlet.getServletConfig().getInitParameter(pName);
			if (val.length() > 0) result = val;
		}
		catch(Exception ex){}
		return result;
	}
	
	private String autologin_user = null;
	private String autologin_group = null;
	private String autologin_password = null;
	private String autologin_sessionid_fake = "AUTO_LOGIN";
	
	/**
	 * Do autologin if allowed and indicated by sessionis
	 * @return true if autologin done
	 * @throws Exception
	 */
	private boolean checkAutoLogin() throws Exception{
		boolean result = false;
		if (this.autologin_user == null) { 
			this.autologin_user = this.optConfigParameter("e3.autologin.username","superuser");
			this.autologin_group = this.optConfigParameter("e3.autologin.groupname","");
			this.autologin_password = this.optConfigParameter("e3.autologin.password","Db1$123");
		}		
		if( 	(autologin_sessionid_fake != null)
			&&  (!autologin_sessionid_fake.isEmpty())
			&&	(autologin_sessionid_fake.equals(sessionId))
			)
		{
			sessionId = servlet.getEDMFunctions().basicLogin(
					autologin_user,
					autologin_group,
					autologin_password, true, null);
			mEdmSession = servlet.getEDMSession(sessionId);
			if (mEdmSession != null){
				wasAutoLogin = true;
				log(E3Logger.WARNING,"Autologin done!");			
			} else {
				result = true;
				log(E3Logger.ERROR,"Autologin failed!");			
			}
		}
		return result;
	}
	
	public void checkAutoLogout() throws ServletException {
        if (this.wasAutoLogin) {
			EDMWSSessionTable sessionTable = servlet.getEDMFunctions().getSessionTable();
			this.mEdmSession.cleanup();
			sessionTable.removeContext(this.mEdmSession.getSessionId());
			this.wasAutoLogin = false;
			log(E3Logger.WARNING,"Autologout done!");			
        }		
	}
	
	
	private String useBasicAuth() throws Exception{
		String result = null;
		sessionId = null;
		Cookie[] cooks = request.getCookies();
		if (cooks != null) { 
			for (int i=0; i < cooks.length; i++){ 
				if ("EDMSESSIONID".equals(cooks[i].getName())) { 
					sessionId = cooks[i].getValue(); break; 
				}
			}
		}
		mEdmSession = sessionId == null ? null : servlet.getEDMSession(sessionId);
		String currUserName = sessionId2userName(sessionId);
		String[] user = new String[]{""}, password = new String[]{""};
	 	boolean baFound = getBasicAuth(user, password);
	 	if (baFound) {
	 		if ((mEdmSession == null) || !currUserName.equals(user[0])) {
	 			// TODO : SHOULD OLD SESSION BE AUTO DELETED IF EXISTS ?
				try { 
					sessionId = null;  
					sessionId = servlet.getEDMFunctions().basicLogin(user[0], "", password[0], true, null);
				} catch (Exception e) {	}
				mEdmSession = sessionId == null ? null : servlet.getEDMSession(sessionId);
				if (mEdmSession == null) { 
					result = setBasicAuthNeeded(myRealm); 
					/*setBasicAuthFailed(response, "EDMWS");*/ 
					return result; 
				}
	 		}
	 	} else {
	 		if (mEdmSession == null) { 
	 			result = setBasicAuthNeeded(myRealm); 
	 			return result; 
	 		}
	 	}
	 	Cookie cook = new Cookie("EDMSESSIONID", sessionId);
	 	cook.setPath("/" + myRealm); cook.setMaxAge(-1); // cook.setHttpOnly(true); - cookie reading by JS is needed for file transfer
	 	response.addCookie(cook);
	 	return result;
	} 
	
		
	private String useParameterAuth() throws Exception{  
		String result = null;
		//sessionId = servlet.getEDMFunctions().basicLogin("superuser", "", "Db1$123", true, null);
		mEdmSession = servlet.getEDMSession(sessionId);
		if (mEdmSession == null) {
			checkAutoLogin(); 
		}
		if ((mEdmSession == null) ) {
			if (sessionId == null) {
				result = sendError(E3Constants.ERROR_CODE_AUTH, null, "Missing EDMSESSIONID - login is required"); 
			}
			else if (mEdmSession == null) {
				result = sendError(E3Constants.ERROR_CODE_AUTH /*ERROR_CODE_SESSION_EXPIRED*/, null, "Session expired or wrong EDMSESSIONID");
			}
		}
		return result;
	}

 	private boolean getBasicAuth( String[] user, String[] password) throws Exception {
	    String s = request.getHeader("Authorization");
	    if ((s == null) || "".equals(s)) return false;
	    if (!s.trim().startsWith("Basic ")) return false; else s = s.trim().substring("Basic ".length());
	    String dec = null;
	    try { 
	    	dec = E3BasicUtils.decode64(s, null); 
	    	if (!s.equals(E3BasicUtils.encode64(dec, null))) throw new Exception("non-iso charset"); 
	    } 
	    catch (Exception retry){
	      try { 
	    	  dec = E3BasicUtils.decode64(s, "UTF-8"); 
	    	  if (!s.equals(E3BasicUtils.encode64(dec, "UTF-8"))) throw new Exception("non-utf8 charset"); 
	      } 
	      catch (Exception ignored){ }
	    }
	    if ((dec == null) || "".equals(dec) || (dec.indexOf(':') == -1)) return false;
	    user[0] = dec.substring(0, dec.indexOf(':'));
	    password[0] = dec.substring(dec.indexOf(':')+1);
	    return true;
    }
 	
	
	public String checkAuthorization()throws IOException, ServletException {
		String result = null;
		mEdmSession = null;
		this.sessionId = null;
		try {
			if(this.isUnitTest()) {
				sessionId = "AUTOLOGON";
				mEdmSession = new EDMWSSession(sessionId);
				mEdmSession.setContext(this.mTestEDMContext);
				return null;
			} 
			
			sessionId = request.getParameter("EDMSESSIONID");
			wasAutoLogin = false;
			wasBasicAuth = "BasicAuth".equals(sessionId);
			if (wasBasicAuth) {
				result = this.useBasicAuth();
			} else { // check session id given in params
				result = this.useParameterAuth();
			}
		} catch (Exception e) {
			result = sendError(E3Constants.ERROR_CODE_FAIL, e, "Unknown error: ");
		}
		return result;
	}
	
	public void sendHtmlResponse(String htmlOut) throws ServletException, IOException {
		this.setNoCache();
		this.response.setStatus(HttpServletResponse.SC_OK);
		this.response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html"); // response.setContentType("application/json");
		if (htmlOut != null) response.getWriter().write(htmlOut);
	}
	
	public void sendJsonResponse(String jsonOut) throws ServletException, IOException {
		if(this.isUnitTest()) {
			log(E3Logger.DEBUG,"sendJsonResponse:" + jsonOut);
			return;
		}
		this.setNoCache();
		this.response.setStatus(HttpServletResponse.SC_OK);
		this.response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain"); // response.setContentType("application/json");
		if (jsonOut != null) response.getWriter().write(jsonOut);
	}

	public String sendError(int code, Exception e, String message) {
		String result = "Error ";
		if (code == 0) code =  HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
		result += code;
		if(response == null) {
			if (message != null) result += (":" + message);
			throw new E3RuntimeException(code,e,result);
		}
		setNoCache();
		response.setStatus(code);
		response.setCharacterEncoding("ISO-8859-1");  
		response.setContentType("text/plain");
        if (message != null) {
        	try { 
        		result = result + " : " + message;
        		response.getWriter().write(message + (e != null ? "\n"+e.toString() : "")); 
        	} 
        	catch (IOException ignored) {
        		
        	}
        }
        if(e != null) {
        	result = result + "\n" + "Exception:  " + e.toString(); // TODO better msg 
        }
        log(E3Logger.ERROR,result);
        return result;
	}

	public void setNoCache() {
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setDateHeader("Expires", 0); // Proxies.		
	}

	private static String sessionId2userName(String sessionId) {
		return ((sessionId == null) || "".equals(sessionId.trim())) ? null : sessionId.substring(1, sessionId.indexOf('_'));
	}
	
	public String getUsername() {
		try {
			return this.mEdmSession.getContext().getUser().getName();
		} catch (Exception e) {
			log(E3Logger.ERROR,e.getMessage());
		}			
		return null;
	}
	
	private boolean requireBasicAuth(String realm, String[] user, String[] password/*, boolean onlyHttps*/) throws Exception {
		//if (onlyHttps && !"https".equalsIgnoreCase(request.getScheme())) { response.sendError(404); return false; } else
		if (!getBasicAuth(user, password)) { 
			setBasicAuthNeeded(realm); 
			return false; 
		}
		else return true;
    }
	

	private String setBasicAuthNeeded(String realm) throws Exception {
		setNoCache();
	    response.addHeader("WWW-Authenticate", "Basic realm=\""+ realm +"\"");
	    response.sendError(401);
	    return "Error: 401 Basic Authentication Needed";
    }
	
	private String setBasicAuthFailed(String realm) throws Exception {
		setNoCache();
	    response.addHeader("WWW-Authenticate", "Basic realm=\""+ realm +"\"");
	    response.sendError(403);
	    return "Error: 403 Basic Authentication Failed";
	}

///////////////////////
//file transfer utils 
///////////////////////

	E3FileTransferInfo getServerTempFile(String name) {
		String username = this.getUsername();
		String filename = username + "_" + name;
		E3FileTransferInfo transferInfo = E3SessionTable.getStaticSessionTable().getFileTransfer(username, filename);
		String localUrl = E3FileTransfer.getLocalFilenameWithPath(filename);
		transferInfo.setFileNameOnServer(localUrl);
		return transferInfo;
	}

	
///////////////////////
// temporary: Query Path Handling 
///////////////////////

	public String getUrl()
	{
		String url = (this.mTestURL != null)? this.mTestURL : request.getPathInfo();/*"/AccessControl/login"*/
		return url;
	}

	
	private CQueryPath mQueryPath = null;
	public CQueryPath getQueryPath() {
		if (mQueryPath == null) {
			mQueryPath = new CQueryPath(request, response, this.getConfiguration());
		}
		return mQueryPath;
	}

	
///////////////////////
//path (URL) decoding 
///////////////////////


	public String getServerURL() {
		String tempUrl = this.request.getRequestURL().toString();
		String tempUri = this.request.getRequestURI();
		String root = tempUrl.substring( 0, tempUrl.indexOf(tempUri));
		return root;
	}

	public String getServletPath() {
		return request.getServletPath(); // "/E3ReposServlet"
	}
	
	public String getPathInfo() {
		return request.getPathInfo(); // "/eee-repos/0.5/models"
	}
	
	public String getParam(String name) {
		return null;
	}

	public JSONObject getRequestParameters() throws E3GeneralException {
		JSONObject result = new JSONObject();
		try {
			if(this.isUnitTest()) {
				if (this.mTestURLArgs != null) result = this.mTestURLArgs;
			} else {
				for (Enumeration<String> pNames = this.request.getParameterNames(); pNames.hasMoreElements();) {
					String pName = pNames.nextElement();
					String pValue = request.getParameter(pName);
					result.put(pName, pValue);
				}
			}
		}
		catch(Exception ex) {
			throw new E3GeneralException(E3BasicUtils.ERROR_CODE_FAIL,ex,"BIMAPI URL parameter retrieve failed");
		}
		return result;
	}


	// JSON request body 
	private JSONObject jsonIn = null;
	/* (non-Javadoc)
	 * @see edmws.webapp.servlets.IE3JSONSource#getJsonIn()
	 */
	@Override
	public JSONObject getJsonIn() {
		if (jsonIn == null) {
			try {
				this.readJsonBody();
				log(E3Logger.DEBUG,"--- jsonin body :" + jsonIn.toString());
			}
			catch(Exception ex) {}
		}
		return jsonIn;
	}
	

	private void readJsonBody() throws Exception{
		if(this.isUnitTest()) {
			this.jsonIn = this.mTestMdIn;
		} else if (Multipart.RequestParser.isMultipartContent(request)) {
			this.jsonIn = new JSONObject();
			log(E3Logger.WARNING,"--- jsonin body in multipart request not supported");
		} else {
			String jsonIn = this.readBody();
			JSONTokener tokener = new JSONTokener(jsonIn); 
			this.jsonIn = new JSONObject(tokener);
			
		}
	}
	
	private String readBody()throws Exception {
		final int bufSize = 8192;
		final String charset = "UTF-8"; // TODO: how to set correct?
		InputStream inStream = request.getInputStream();
		try {
			char[] buffer = new char[bufSize];
			StringBuilder out = new StringBuilder();
			Reader in = new InputStreamReader(inStream, charset);
			for (;;) {
				int rsz = in.read(buffer, 0, buffer.length);
				if (rsz < 0) break;
				out.append(buffer, 0, rsz);
			}
			return out.toString();
		}
		catch (Exception ex) {
			throw ex;
		}
		finally {
			if(inStream != null) inStream.close();
		}
	}

	public String getBaseUrl() {
		String baseUrl = "AUTOTEST";
		if(!this.isUnitTest()) {
			baseUrl = this.getServerURL() 
					+ this.servlet.getServletContext().getContextPath()
					+ "/" + this.servlet.getServletName(); // TODO:probably not correct with other mapping
		}
		return baseUrl;
	}

	public boolean isUnitTest() {
		return (this.mTestURL != null);
	}

}
