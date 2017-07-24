package edmws.webapp.servlets;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import edmws.ws.model.EDMWSSessionTable;
import edmws.ws.model.FileTransferInfo;

public class E3EDMAccessFunctions {

// UPDATED : EDMFunctions, EDMWSServlet, ExecuteQuery, HttpFileTransfer, EDMWSSessionCleaner, EDMFormalParameter, CEDMFormalParameter, RESTServlet

	public final static int ERROR_CODE_FAIL = HttpServletResponse.SC_INTERNAL_SERVER_ERROR; // 500
	public final static int ERROR_CODE_PARAMS = HttpServletResponse.SC_BAD_REQUEST; // 400
	public final static int ERROR_CODE_AUTH = HttpServletResponse.SC_FORBIDDEN; // 403
	public final static int ERROR_CODE_SESSION_EXPIRED = HttpServletResponse.SC_UNAUTHORIZED; // 401

//	CONTROL PARAMS : "EDMSESSIONID", "queryoptions", "jsonstring", "filemapping"
//      "queryoptions" : JSONDirectStringReturn = 1;
//      "EDMSESSIONID" : LIKE "psuperuser_1_190317_1427382197851", OR "BasicAuth"  


	
    public E3EDMAccessFunctions() {
    }

// EDMWS/REST/AccessControl/login    { "USER":"string", "GROUP":"string", "PASSWORD":"string" }, { "string" }
// EDMWS/REST/AccessControl/logout   { "EDMSESSIONID":"string" }, { "string" }
// EDMWS/REST/AccessControl/createTemporaryFile    { "EDMSESSIONID":"string", "FILENAME":"string", "FILETYPE":"string", "UPLOAD":"boolean" }, { "fileTransferInfo" }
// EDMWS/REST/AccessControl/deleteTemporaryFile    { "EDMSESSIONID":"string", "fileNameOnServer":"string" }, { "string" }

// EDMWS/REST/RepositoryName/ModelName/QEX/QuerySchemaName/QueryFunctionName
// EDMWS/REST/QEX/QuerySchemaName/QueryFunctionName
// { "EDMSESSIONID":"string", "queryoptions":"long", "jsonstring":"json", "filemapping":"json", params... }, { "string" or json }

	private String doLogin(E3AccessUtil carrier) throws IOException, ServletException {
		String result = null;
		// EDMWS/REST/AccessControl/login   { "USER":"string", "GROUP":"string", "PASSWORD":"string" }, { "string" }
		String user = carrier.getParam("USER");
		String group = carrier.getParam( "GROUP");
		String pwd = carrier.getParam( "PASSWORD");
		if ((user == null) || (pwd == null)) { 
			result = carrier.sendError( ERROR_CODE_PARAMS, null, "Wrong user or password");  
			return result; 
		}
		try { 
			carrier.sessionId = carrier.servlet.getEDMFunctions().basicLogin(user, group, pwd, true, null);
		} catch (Exception e) {
			result = carrier.sendError(ERROR_CODE_PARAMS, e, "Wrong login or server fail: ");	
			return result;
		}
		if (carrier.wasBasicAuth) {
		 	Cookie cook = new Cookie("EDMSESSIONID", carrier.sessionId);
		 	cook.setPath("/EDMWS"); cook.setMaxAge(-1); // cook.setHttpOnly(true); - cookie reading by JS is needed for file transfer
		 	carrier.response.addCookie(cook);
		}
		carrier.setNoCache();
		carrier.response.setStatus(HttpServletResponse.SC_OK);
		carrier.response.setCharacterEncoding("UTF-8");
		carrier.response.setContentType("text/plain");
		carrier.response.getWriter().write(carrier.sessionId);
        return result;
	}

	private String doLogout(E3AccessUtil carrier) throws ServletException{
		String result = null;
		// EDMWS/REST/AccessControl/logout    { "EDMSESSIONID":"string" }, { "string" }
		EDMWSSessionTable sessionTable = carrier.servlet.getEDMFunctions().getSessionTable();
		carrier.mEdmSession.cleanup();
		sessionTable.removeContext(carrier.mEdmSession.getSessionId());
		if (carrier.wasBasicAuth) {
			Cookie cook = new Cookie("EDMSESSIONID", carrier.sessionId);  
			cook.setPath("/EDMWS"); cook.setMaxAge(0); 	
			carrier.response.addCookie(cook);
		}
		carrier.setNoCache();
		carrier.response.setStatus(HttpServletResponse.SC_OK);
    	return result;
	}

	private String doCreateTemporaryFile(E3AccessUtil carrier) throws IOException {
		String result = null;
		// EDMWS/REST/AccessControl/createTemporaryFile    { "EDMSESSIONID":"string", "FILENAME":"string", "FILETYPE":"string", "UPLOAD":"boolean" }, { "fileTransferInfo" }
		String fileName = carrier.getParam("FILENAME");	if (fileName == null || fileName.length() == 0) fileName = "temp";
		for (int l=fileName.length(); l < 3; l++) fileName = fileName + "_";
		String fileType = carrier.getParam( "FILETYPE");	if (fileType == null || fileType.length() == 0) fileType = ".txt";
		HashMap<String,String> fileInfo = new HashMap<String,String>();
		String upload = carrier.getParam( "UPLOAD"), operation = (upload != null && upload.equalsIgnoreCase("TRUE")) ? "upload" : "download";
		FileTransferInfo transferInfo = new FileTransferInfo();
		File tempfile = File.createTempFile(fileName, fileType, new File( System.getProperty("java.io.tmpdir") ));
		String filePath = tempfile.getPath();
		transferInfo.fileName = filePath; 
		transferInfo.nBytesTransferred = 0;	
		carrier.mEdmSession.setFileTransferInfo(transferInfo);
		String url = carrier.request.getRequestURL().toString();
		int webAppPos = url.indexOf(carrier.request.getServletPath());
		fileInfo.put("uploadOrDownloadUrl", url.substring(0, webAppPos) + "/HttpFileTransfer?operation=" + operation + "&fileName=" + filePath + "&sessionId=");
		fileInfo.put("progressInfoUrl", url.substring(0, webAppPos) + "/HttpFileTransfer?operation=getProgressInfo&fileName=" + filePath + "&sessionId=");
		fileInfo.put("fileNameOnServer", filePath);
		fileInfo.put("operation", operation);
		String jsonOutFI = E3BasicUtils.jsonEncodeSimpleObj(fileInfo);
		carrier.setNoCache();
		carrier.response.setStatus(HttpServletResponse.SC_OK);
		carrier.response.setCharacterEncoding("UTF-8");
		carrier.response.setContentType("text/plain"); // response.setContentType("application/json");
		carrier.response.getWriter().write(jsonOutFI);
	    return result;
	}
	
	private String doDeleteTemporaryFile(E3AccessUtil carrier) throws ServletException {
		String result = null;
		// EDMWS/REST/AccessControl/deleteTemporaryFile    { "EDMSESSIONID":"string", "fileNameOnServer":"string" }, { "string" }
		String fileName = carrier.getParam("fileNameOnServer");
		if (fileName == null || fileName.length() == 0) { 
			result = carrier.sendError(ERROR_CODE_PARAMS, null, "Invalid parameter fileNameOnServer");	
			return result;
		}
		FileTransferInfo transferInfo = carrier.mEdmSession.getFileTransferInfo(fileName);
		if (transferInfo == null) { 
			result = carrier.sendError(ERROR_CODE_PARAMS, null, "File \"" + fileName + "\" is not linked to this session");	
			return result;
		}
		File f = new File(transferInfo.fileName); 
		f.delete();
		carrier.setNoCache();
		carrier.response.setStatus(HttpServletResponse.SC_OK);
	    return result;
	}

    

	
	public String doEDMAccessFunction(String ACMethod,E3AccessUtil carrier)throws IOException, ServletException {
		String result = null;
		try {
			// Internal methods - access control
			if (ACMethod != null) {
				if ("login".equals(ACMethod)) {
					// EDMWS/REST/AccessControl/login   { "USER":"string", "GROUP":"string", "PASSWORD":"string" }, { "string" }
					result = this.doLogin(carrier);
		            if(result==null) result = "login successful";
				} else if ("logout".equals(ACMethod)) {
					// EDMWS/REST/AccessControl/logout    { "EDMSESSIONID":"string" }, { "string" }
					result = carrier.checkAuthorization();
					if(result == null) result = this.doLogout(carrier);
		            if(result == null) result = "logout successful";
				} else if ("createTemporaryFile".equals(ACMethod)) {
					// EDMWS/REST/AccessControl/createTemporaryFile    { "EDMSESSIONID":"string", "FILENAME":"string", "FILETYPE":"string", "UPLOAD":"boolean" }, { "fileTransferInfo" }
					result = carrier.checkAuthorization();
					if(result == null) result = this.doCreateTemporaryFile(carrier);
		            if(result == null) result = "createTemporaryFile successful";
				} else if ("deleteTemporaryFile".equals(ACMethod)) {
					// EDMWS/REST/AccessControl/deleteTemporaryFile    { "EDMSESSIONID":"string", "fileNameOnServer":"string" }, { "string" }
					result = carrier.checkAuthorization();
					if(result == null) result = this.doDeleteTemporaryFile(carrier);
		            if(result == null) result = "deleteTemporaryFile successful";
				} else {
					result = carrier.sendError(ERROR_CODE_PARAMS, null, "Unknown access control method ");
				}
				return result;
			}

		} catch (Exception e) {
			result = carrier.sendError(ERROR_CODE_FAIL, e, "Uknown error: ");
		}
		return result;
	}

	


	
	
 	

}
